package np.com.neelayamkandel.journeyjournal.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import np.com.neelayamkandel.journeyjournal.R;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NavHostFragment navHostFragment;
    private NavController navController;

    private void extractElement() {
        toolbar = findViewById(R.id.home_toolbar);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            // Extract App Bar Configuration
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
            // Setting Navigation UI with tool bar
            NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        }
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
        extractElement();
    }


}