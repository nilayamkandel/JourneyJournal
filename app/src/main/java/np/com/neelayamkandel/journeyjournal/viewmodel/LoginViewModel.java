package np.com.neelayamkandel.journeyjournal.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    final MutableLiveData<Boolean> isEmailOrPasswordEmpty = new MutableLiveData<>();
}
