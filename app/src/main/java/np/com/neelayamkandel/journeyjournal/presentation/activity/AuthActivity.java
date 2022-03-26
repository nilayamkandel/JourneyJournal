package np.com.neelayamkandel.journeyjournal.presentation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.frameworks.firebase.FirebaseAuthImpl;
import np.com.neelayamkandel.journeyjournal.model.auth.UserProfileModel;

public class AuthActivity extends AppCompatActivity {
    private FirebaseAuthImpl firebaseAuth = new FirebaseAuthImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        firebaseAuth.getIsLoginSuccess().observe(this, userProfileModel -> {
            if(userProfileModel != null){
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("USER", userProfileModel.getLoginProfile().getFirebaseUser());
                intent.putExtra("PROFILE", (Serializable) userProfileModel.getLoginProfile().getRegistration());
                startActivity(intent);
                finish();
            }
        });
    }
}