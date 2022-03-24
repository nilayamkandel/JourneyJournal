package np.com.neelayamkandel.journeyjournal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class CreateViewModel extends AndroidViewModel {
    public final MutableLiveData<HelperViewModel> isTitleEmpty = new MutableLiveData<>();

    public CreateViewModel(@NonNull Application application) {
        super(application);
    }

    public void validateSaveCredentials(String title){
        if(title.isEmpty()){
            isTitleEmpty.setValue(new HelperViewModel(true, "Title field is empty"));
            return;
        } else{
            isTitleEmpty.setValue(new HelperViewModel(false));
        }
    }
}
