package np.com.neelayamkandel.journeyjournal.presentation.fragment.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import np.com.neelayamkandel.journeyjournal.R;


public class LandingFragment extends Fragment {
    private Button SignUp, Login;
    private NavController navController;


    private void handleButtonTrigger(){
        SignUp.setOnClickListener(event->navController.navigate(R.id.action_landingFragment_to_registerFragment));
        Login.setOnClickListener(event->navController.navigate(R.id.action_landingFragment_to_loginFragment));
    }

    private void extractElements(View view){
        SignUp = view.findViewById(R.id.btn_Signup);
        Login = view.findViewById(R.id.btn_login);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Step 1: Bind View Model using ViewModel Provider

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_landing, container, false);
        // Step 2: Extract Elements from Views (register, Login)
        this.extractElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Step 3: Initialize Navigation Controller
        navController = Navigation.findNavController(view);
        // Step 4: Initialize Button Click Events
        this.handleButtonTrigger();
    }
}