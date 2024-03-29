package np.com.neelayamkandel.journeyjournal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import np.com.neelayamkandel.journeyjournal.dao.auth.registration.Registration;
import np.com.neelayamkandel.journeyjournal.dao.auth.registration.RegistrationForm;
import np.com.neelayamkandel.journeyjournal.dao.helper.SuccessHelper;
import np.com.neelayamkandel.journeyjournal.frameworks.firebase.FirebaseAuthImpl;

public class RegisterViewModel extends AndroidViewModel {
    private final FirebaseAuthImpl firebaseAuth = new FirebaseAuthImpl();
    public final MutableLiveData<HelperViewModel> isDisplayNameEmpty = new MutableLiveData<>();
        public MutableLiveData<HelperViewModel> getIsDisplayNameEmpty() {
            return isDisplayNameEmpty;
        }

    public MutableLiveData<HelperViewModel> getIsEmailEmpty() {
        return isEmailEmpty;
    }

    public MutableLiveData<HelperViewModel> getIsPasswordEmpty() {
        return isPasswordEmpty;
    }

    public MutableLiveData<HelperViewModel> getIsConfirmPasswordEmpty() {
        return isConfirmPasswordEmpty;
    }


    public final MutableLiveData<HelperViewModel> isEmailEmpty = new MutableLiveData<>();
    public final MutableLiveData<HelperViewModel> isPasswordEmpty = new MutableLiveData<>();
    public final MutableLiveData<HelperViewModel> isConfirmPasswordEmpty = new MutableLiveData<>();


    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<SuccessHelper> getIsRegistrationSuccess() {
        return isRegistrationSuccess;
    }

    private final MutableLiveData<SuccessHelper> isRegistrationSuccess = new MutableLiveData<>();

    public void registrationValidation(RegistrationForm registrationForm, LifecycleOwner owner) {
        if (registrationForm.getDisplayName().isEmpty()) {
            isDisplayNameEmpty.setValue(new HelperViewModel(true, "Username field is empty"));
            return;
        } else {
            isDisplayNameEmpty.setValue(new HelperViewModel(false));
        }
        if (registrationForm.getEmail().isEmpty()) {
            isEmailEmpty.setValue(new HelperViewModel(true, "Email field is empty"));
            return;
        } else {
            isEmailEmpty.setValue(new HelperViewModel(false));
        }
        if (registrationForm.getPassword().isEmpty()) {
            isPasswordEmpty.setValue(new HelperViewModel(true, "Password field is empty"));
            return;
        } else if (registrationForm.getPassword().length() <= 6 && registrationForm.getPassword().length() <= 20) {
            isPasswordEmpty.setValue(new HelperViewModel(true, "Password must have 6-20 characters"));
            return;
        } else {
            isPasswordEmpty.setValue(new HelperViewModel(false));
        }

        //todo validation check
        if (registrationForm.getDisplayName() != null && registrationForm.getEmail() != null && registrationForm.getPassword() != null && registrationForm.getConfirmPassword() != null) {
            firebaseAuth.createUser(registrationForm).observe(owner, successDataSet -> isRegistrationSuccess.postValue(new SuccessHelper(successDataSet.isSuccess(), successDataSet.getMessage())));
        }

    }
}
