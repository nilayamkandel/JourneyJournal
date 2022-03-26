package np.com.neelayamkandel.journeyjournal.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.model.auth.UserProfileModel;

public class HomeActivity extends AppCompatActivity {
    private String TAG =  "J_" + HomeActivity.class.getSimpleName();
    private UserProfileModel userProfileModel;
    private Toolbar toolbar;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private FloatingActionButton floating_action_button;
    public void extractElementFromIntent(){
        if(getIntent()!= null){
           userProfileModel = getIntent().getParcelableExtra("USERPROFILE");
            if(userProfileModel != null){
                Log.d(TAG, "extractElementFromIntent: " + userProfileModel
                        .getLoginProfile()
                        .getFirebaseUser()
                        .getUid());
            }
        }

    }

    private void extractElement() {
        userProfileModel = null;
        toolbar = findViewById(R.id.home_toolbar);
        floating_action_button = findViewById(R.id.floating_action_button);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            // Extract App Bar Configuration
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
            // Setting Navigation UI with tool bar
            NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        }
    }

    private void setUpComponents() {
        // Step 3: Show view Based on Navigation Argument
        this.showViewsBasedOnNavigationArguments();
    }

    private void buttonTriggerEvents(){
        floating_action_button.setOnClickListener(event->{
            navController.navigate(R.id.createFragment);
        });
    }

    private void showViewsBasedOnNavigationArguments() {
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (bundle == null) return;
            // Condition 1: Show Fab Based on Argument passed from safe args
            if(bundle.getBoolean(getString(R.string.args_show_fab))){
                // Hide Fab on True
                floating_action_button.setVisibility(View.VISIBLE);
            }else{
               // Show Fab on False
                floating_action_button.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        int menuItem = item.getItemId();
        if (menuItem == R.id.profile) {
             navController.navigate(R.id.profileFragment);
        } else if (menuItem == R.id.logout) {
           startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        extractElementFromIntent();
        extractElement();
        // Step 3: Setup Components
        setUpComponents();
        // Step 4: Handle Button Trigger events
        buttonTriggerEvents();

    }


}