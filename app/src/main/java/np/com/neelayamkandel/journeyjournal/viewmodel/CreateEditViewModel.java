package np.com.neelayamkandel.journeyjournal.viewmodel;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import np.com.neelayamkandel.journeyjournal.dao.helper.SuccessHelper;
import np.com.neelayamkandel.journeyjournal.dao.home.JourneyDao;
import np.com.neelayamkandel.journeyjournal.frameworks.firebase.FirebaseDbImpl;
import np.com.neelayamkandel.journeyjournal.model.home.JourneyModel;

public class CreateEditViewModel extends AndroidViewModel {
    public final MutableLiveData<HelperViewModel> isTitleEmpty = new MutableLiveData<>();
    public FirebaseDbImpl firebaseDbImpl = new FirebaseDbImpl();
    public CreateEditViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<JourneyModel> isAddSuccess = new MutableLiveData<>();
    public MutableLiveData<JourneyModel> isUpdateSuccess = new MutableLiveData<>();

    public void validateSaveCredentials(JourneyDao journeyDao, Uri Image, Context context,
                                        LifecycleOwner Owner, boolean Camera, Bitmap bitmap){

        if(journeyDao.getTitle().isEmpty()){
            isTitleEmpty.setValue(new HelperViewModel(true, "Title field is empty"));
            return;
        } else{
            isTitleEmpty.setValue(new HelperViewModel(false));
        }
        if(!journeyDao.getTitle().isEmpty())
        {
            firebaseDbImpl.AddJourney(journeyDao, Image, context, Owner, Camera, bitmap);
            firebaseDbImpl.Addjourney.observe(Owner,journeyModel->isAddSuccess.postValue(journeyModel));
        }
    }

    public void validateUpdateCredentials(String JourneyId, JourneyDao journeyDao, Uri Image, Context context,
                                          LifecycleOwner Owner, boolean Camera, Bitmap bitmap){
        if(journeyDao.getTitle().isEmpty()){
            isTitleEmpty.setValue(new HelperViewModel(true, "Title field is empty"));
            return;
        } else{
            isTitleEmpty.setValue(new HelperViewModel(false));
        }
        if(!journeyDao.getTitle().isEmpty())
        {
            firebaseDbImpl.UpdateJourney(JourneyId, journeyDao, Image, context,Owner,Camera, bitmap);
            firebaseDbImpl.Updatejourney.observe(Owner,journeyModel->isUpdateSuccess.postValue(journeyModel));
        }
    }

}
