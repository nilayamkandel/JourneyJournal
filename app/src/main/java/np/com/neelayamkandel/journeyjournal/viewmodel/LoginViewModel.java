package np.com.neelayamkandel.journeyjournal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends AndroidViewModel {
    public final MutableLiveData<Boolean> isEmail = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }



    public void validateLoginCredentials(String email, String password){
        if (email.isEmpty() || password.isEmpty()) {
            isEmail.setValue(true);
            return;
        } else {
            isEmail.setValue(false);
        }
    }

}
