package np.com.neelayamkandel.journeyjournal.presentation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.viewmodel.CommonViewModel;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        CommonViewModel commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
        commonViewModel.CacheHandler(this, this);

    }

}