package np.com.neelayamkandel.journeyjournal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import np.com.neelayamkandel.journeyjournal.frameworks.firebase.FirebaseAuthImpl;

public class ForgetPasswordViewModel extends AndroidViewModel {
    private final FirebaseAuthImpl firebaseAuth = new FirebaseAuthImpl();

    public MutableLiveData<HelperViewModel> getIsResetSuccess() {
        return isResetSuccess;
    }

    public final MutableLiveData<HelperViewModel> isResetSuccess = new MutableLiveData<>();
    public final MutableLiveData<HelperViewModel> isEmailEmpty = new MutableLiveData<>();

    public MutableLiveData<HelperViewModel> getIsEmailEmpty() {
        return isEmailEmpty;
    }

    public ForgetPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public void validateResetCredentials(String email,  LifecycleOwner owner){
        if (email.isEmpty()) {
            isEmailEmpty.setValue(new HelperViewModel(true,"Email field is empty"));
            return;
        } else {
            isEmailEmpty.setValue(new HelperViewModel(false));
        }


        if(!email.isEmpty()){
            firebaseAuth.forgetPassword(email).observe(owner, helperViewModel -> isResetSuccess.postValue(helperViewModel));
        }
    }

}
