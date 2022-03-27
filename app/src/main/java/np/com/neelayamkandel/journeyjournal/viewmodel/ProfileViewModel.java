package np.com.neelayamkandel.journeyjournal.viewmodel;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

import np.com.neelayamkandel.journeyjournal.dao.auth.registration.Registration;
import np.com.neelayamkandel.journeyjournal.dao.helper.SuccessHelper;
import np.com.neelayamkandel.journeyjournal.frameworks.firebase.FirebaseAuthImpl;
import np.com.neelayamkandel.journeyjournal.model.auth.UserProfileModel;

public class ProfileViewModel extends AndroidViewModel {
    private final FirebaseAuthImpl firebaseAuth = new FirebaseAuthImpl();

    public MutableLiveData<SuccessHelper> getIsProfileMaintainSuccess() {
        return isProfileMaintainSuccess;
    }

    private final MutableLiveData<SuccessHelper> isProfileMaintainSuccess = new MutableLiveData<>();


    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public void UpdateProfile(FirebaseUser firebaseUser, Registration registration, Uri Image, Context context, LifecycleOwner lifecycleOwner, boolean Camera, Bitmap bitmap){
       firebaseAuth.UpdateProfile(firebaseUser, registration, Image, context, lifecycleOwner, Camera, bitmap).observe(lifecycleOwner,updateProfile->{
           isProfileMaintainSuccess.postValue(updateProfile);
       });
    }
}
