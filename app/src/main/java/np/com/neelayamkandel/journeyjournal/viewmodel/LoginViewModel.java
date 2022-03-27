package np.com.neelayamkandel.journeyjournal.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import np.com.neelayamkandel.journeyjournal.dao.auth.login.Login;
import np.com.neelayamkandel.journeyjournal.frameworks.firebase.FirebaseAuthImpl;
import np.com.neelayamkandel.journeyjournal.model.home.UserProfileModel;

public class LoginViewModel extends AndroidViewModel {
    private String TAG =  "J_" + LoginViewModel.class.getSimpleName();
    private final FirebaseAuthImpl firebaseAuth = new FirebaseAuthImpl();
    public MutableLiveData<HelperViewModel> getIsEmailEmpty() {
        return isEmailEmpty;
    }
    public MutableLiveData<HelperViewModel> getIsPasswordEmpty() {
        return isPasswordEmpty;
    }
    public final MutableLiveData<HelperViewModel> isEmailEmpty = new MutableLiveData<>();
    public final MutableLiveData<HelperViewModel> isPasswordEmpty = new MutableLiveData<>();
    public MutableLiveData<UserProfileModel> getIsLoginSuccess() {
        return isLoginSuccess;
    }
    public final MutableLiveData<UserProfileModel> isLoginSuccess = new MutableLiveData<>();


    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void validateLoginCredentials(String email, String password, LifecycleOwner owner){
        if (email.isEmpty()) {
            isEmailEmpty.setValue(new HelperViewModel(true,"Email field is empty"));
            return;
        } else {
            isEmailEmpty.setValue(new HelperViewModel(false));
        }
        if (password.isEmpty()) {
            isPasswordEmpty.setValue(new HelperViewModel(true,"Password field is empty"));
            return;
        } else if(password.length() <= 6 && password.length() <= 20) {
            isPasswordEmpty.setValue(new HelperViewModel(true,"Password must have 6-20 characters"));
            return;
        }
        else {
            isPasswordEmpty.setValue(new HelperViewModel(false));
        }

        if(!email.isEmpty() && !password.isEmpty()){
            Log.d(TAG, "validateLoginCredentials: " + email);
            firebaseAuth.login(new Login(email, password)).observe(owner, sucessDataset->{
                Log.d(TAG, "validateLoginCredentials: " + sucessDataset.isSuccess());
                isLoginSuccess.postValue(sucessDataset);
            });

        }
    }

}
