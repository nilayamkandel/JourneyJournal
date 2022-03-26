package np.com.neelayamkandel.journeyjournal.frameworks.firebase;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import np.com.neelayamkandel.journeyjournal.dao.auth.login.Login;
import np.com.neelayamkandel.journeyjournal.dao.auth.login.LoginProfile;
import np.com.neelayamkandel.journeyjournal.dao.auth.registration.Registration;
import np.com.neelayamkandel.journeyjournal.dao.auth.registration.RegistrationForm;
import np.com.neelayamkandel.journeyjournal.model.auth.RegistrationModel;
import np.com.neelayamkandel.journeyjournal.model.auth.UserProfileModel;
import np.com.neelayamkandel.journeyjournal.presentation.fragment.auth.LoginFragment;
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

    public FirebaseAuthImpl(){
        if (auth.getCurrentUser() != null) {
            database.getDatabase().getReference("Account")
                    .child(auth.getCurrentUser().getUid())
            .get().addOnCompleteListener(userData->{
                UserProfileModel userProfileModel = new UserProfileModel(true, "Welcome Back!!");
                userProfileModel.setLoginProfile(new LoginProfile(
                        userData.getResult().getValue(Registration.class),
                        auth.getCurrentUser()
                ));

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
}

