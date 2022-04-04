package np.com.neelayamkandel.journeyjournal.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;

import java.io.Serializable;

import np.com.neelayamkandel.journeyjournal.frameworks.firebase.FirebaseAuthImpl;
import np.com.neelayamkandel.journeyjournal.presentation.activity.HomeActivity;
import np.com.neelayamkandel.journeyjournal.presentation.fragment.auth.LoginFragment;

public class CommonViewModel extends AndroidViewModel {
    private String TAG =  "J_" + CommonViewModel.class.getSimpleName();
    private FirebaseAuthImpl firebaseAuth = new FirebaseAuthImpl();
    public CommonViewModel(@NonNull Application application) {
        super(application);
    }

    public void CacheHandler(Activity activity, LifecycleOwner lifecycleOwner){

        firebaseAuth.getIsLoginSuccess().observe(lifecycleOwner, userProfileModel -> {
            Log.d(TAG, "CacheHandler: " + userProfileModel.getLoginProfile().getFirebaseUser().getPhotoUrl());
            if(userProfileModel != null){
                Intent intent = new Intent(activity, HomeActivity.class);
                intent.putExtra("USER", userProfileModel.getLoginProfile().getFirebaseUser());
                intent.putExtra("PROFILE", userProfileModel.getLoginProfile().getRegistration());
                if(userProfileModel.getLoginProfile().getFirebaseUser().getPhotoUrl() != null){
                    intent.putExtra("IMAGE",userProfileModel.getLoginProfile().getFirebaseUser().getPhotoUrl().toString());
                }
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }
}
