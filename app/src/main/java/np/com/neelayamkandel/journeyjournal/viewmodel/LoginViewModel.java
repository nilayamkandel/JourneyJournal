package np.com.neelayamkandel.journeyjournal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends AndroidViewModel {
    public final MutableLiveData<HelperViewModel> isEmailEmpty = new MutableLiveData<>();
    public final MutableLiveData<HelperViewModel> isPasswordEmpty = new MutableLiveData<>();


    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void validateLoginCredentials(String email, String password){
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
    }

}
