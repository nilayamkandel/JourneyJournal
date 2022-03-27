package np.com.neelayamkandel.journeyjournal.frameworks.firebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import np.com.neelayamkandel.journeyjournal.dao.auth.login.Login;
import np.com.neelayamkandel.journeyjournal.dao.auth.login.LoginProfile;
import np.com.neelayamkandel.journeyjournal.dao.auth.registration.Registration;
import np.com.neelayamkandel.journeyjournal.dao.auth.registration.RegistrationForm;
import np.com.neelayamkandel.journeyjournal.dao.helper.SuccessHelper;
import np.com.neelayamkandel.journeyjournal.model.auth.RegistrationModel;
import np.com.neelayamkandel.journeyjournal.model.auth.UserProfileModel;
import np.com.neelayamkandel.journeyjournal.viewmodel.HelperViewModel;

public class FirebaseAuthImpl {
    private String TAG =  "J_" + FirebaseAuthImpl.class.getSimpleName();

    public MutableLiveData<UserProfileModel> getIsLoginSuccess() {
        return isLoginSuccess;
    }

    private final  MutableLiveData<UserProfileModel> isLoginSuccess = new MutableLiveData<>();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseDbImpl database = new FirebaseDbImpl();
    private final FirebaseStorageImpl storage = new FirebaseStorageImpl();
    private final MutableLiveData<SuccessHelper> isUpdateSuccess = new MutableLiveData<>();

    public FirebaseAuthImpl(){
        if (auth.getCurrentUser() != null) {
            database.getDatabase().getReference("Account")
                    .child(auth.getCurrentUser().getUid())
            .get().addOnCompleteListener(userData->{
                Log.d(TAG, "FirebaseAuthImpl: " + userData.isSuccessful());
                UserProfileModel userProfileModel = new UserProfileModel(true, "Welcome Back!!");
                userProfileModel.setLoginProfile(new LoginProfile(
                        userData.getResult().getValue(Registration.class),
                        auth.getCurrentUser()
                ));
                isLoginSuccess.postValue(userProfileModel);
            });
        }
    }

    public MutableLiveData<RegistrationModel> createUser(RegistrationForm registrationForm){
        MutableLiveData<RegistrationModel> isRegistrationSuccess = new MutableLiveData<>();
        //step-1 create user with email
        auth.createUserWithEmailAndPassword(registrationForm.getEmail(), registrationForm.getPassword())
                .addOnCompleteListener(createData->{
                    //step 2 success condition check
                    if(createData.isSuccessful()){
                        // step-3 enter in database
                        database.getDatabase()
                                .getReference("Account")
                                .child(auth.getCurrentUser().getUid())
                                .setValue(new Registration(registrationForm.getDisplayName(),registrationForm.getEmail()))
                                .addOnCompleteListener(addUser->{
                        //step-4 success check and redirection
                                    if(addUser.isSuccessful()){
                                        RegistrationModel registrationModel = new RegistrationModel(true,"User Registered Successfully");
                                        registrationModel.setRegistrationForm(registrationForm);
                                        isRegistrationSuccess.postValue(registrationModel);
                                    }else {
                        isRegistrationSuccess.postValue(new RegistrationModel(false, createData.getException().getMessage()));
                                    }
                                });
                    }
                    else{
                        isRegistrationSuccess.postValue(new RegistrationModel(false, createData.getException().getMessage()));
                    }
                });
        return isRegistrationSuccess;
    }

    public MutableLiveData<UserProfileModel> login(Login login){
        Log.d(TAG, "login: "+login.getEmail());
    //step-1 call firebase auth login method

        auth.signInWithEmailAndPassword(login.getEmail(), login.getPassword())
                .addOnCompleteListener(loginData->{
                    Log.d(TAG, "login: loginData" + loginData.isSuccessful());
                    if(loginData.isSuccessful()){
                        //step-2 fetch user details from db
                        database.getDatabase()
                                .getReference("Account")
                                .child(loginData.getResult().getUser().getUid())
                                .get()
                                .addOnCompleteListener(profileData->{
                                    Log.d(TAG, "login: profileData " + profileData.isSuccessful());
                                    if(profileData.isSuccessful()){

                                        UserProfileModel userProfileModel = new UserProfileModel(true, "Login Success!!");
                                        userProfileModel.setLoginProfile(new LoginProfile(
                                                profileData.getResult().getValue(Registration.class),
                                                loginData.getResult().getUser()

                                        ));
                                        isLoginSuccess.postValue(userProfileModel);
                                    }
                                    else{
                                        isLoginSuccess.postValue(new UserProfileModel(false, profileData.getException().getMessage()));
                                    }
                                });
                    }
                    else{
                        isLoginSuccess.postValue(new UserProfileModel(false, loginData.getException().getMessage()));
                    }
                });

        return isLoginSuccess;
    }

    public MutableLiveData<HelperViewModel> forgetPassword(String email){
        MutableLiveData<HelperViewModel> isResetSuccess = new MutableLiveData<>();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(status->{
            if (status.isSuccessful()) {
                isResetSuccess.postValue(new HelperViewModel(true, "Please set your Email"));
            }

            else{
                isResetSuccess.postValue(new HelperViewModel(false, status.getException().getMessage()));
            }
        });
        return isResetSuccess;
    }

    public void Logout(){
        auth.signOut();
        isLoginSuccess.postValue(new UserProfileModel(false));
    }

    public MutableLiveData<SuccessHelper> UpdateProfile(FirebaseUser firebaseUser, Registration registration, Uri Image, Context context, LifecycleOwner lifecycleOwner, boolean Camera, Bitmap bitmap){
        //step-1 upload image
        if(Image != null || bitmap != null) {
            if (Camera) {
                storage.UploadBitmap(bitmap, "Images/Profile/", firebaseUser.getEmail(), context);
            } else {
                storage.UploadUri(Image, "Images/Profile/", firebaseUser.getEmail(), context);
            }

            //step -2 check image upload success

            storage.getIsUploadSuccess().observe(lifecycleOwner, successHelper -> {
                if (successHelper.isSuccess()) {
                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(successHelper.getMessage())).build();
                    firebaseUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            database.getDatabase().getReference("Account")
                                    .child(firebaseUser
                                            .getUid())
                                    .setValue(registration)
                                    .addOnCompleteListener(status -> {
                                        if (status.isSuccessful()) {
                                            isUpdateSuccess.postValue(new SuccessHelper(true, "Profile updated successfully"));
                                        } else {
                                            isUpdateSuccess.postValue(new SuccessHelper(false, status.getException().getMessage()));
                                        }
                                    });
                        } else {
                            isUpdateSuccess.postValue(new SuccessHelper(false, task.getException().getMessage()));
                        }
                    });
                } else {
                    isUpdateSuccess.postValue(new SuccessHelper(false, successHelper.getMessage()));
                }

            });
        }
        else{
            database.getDatabase().getReference("Account")
                    .child(firebaseUser
                            .getUid())
                    .setValue(registration)
                    .addOnCompleteListener(status -> {
                        if (status.isSuccessful()) {
                            isUpdateSuccess.postValue(new SuccessHelper(true, "Profile updated successfully"));
                        } else {
                            isUpdateSuccess.postValue(new SuccessHelper(false, status.getException().getMessage()));
                        }
                    });
        }
        //step- 3 update profile
        return isUpdateSuccess;
    }
}

