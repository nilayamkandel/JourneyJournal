package np.com.neelayamkandel.journeyjournal.presentation.fragment.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.presentation.activity.HomeActivity;
import np.com.neelayamkandel.journeyjournal.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {
    private Button  Login;
    private TextView Register;
    private TextInputLayout lp_tfEmail;
    private TextInputLayout lp_tfPassword;
    private TextView ForgetPassword;
    private NavController navController;
    private LoginViewModel loginViewModel;


    private void handleButtonTrigger(){
        Register.setOnClickListener(event->navController.navigate(R.id.action_loginFragment_to_registerFragment));
        Login.setOnClickListener(event->{
            loginViewModel.validateLoginCredentials(lp_tfEmail.getEditText().getText().toString().trim(), lp_tfPassword.getEditText().getText().toString().trim());
            Intent intent = new Intent(requireActivity(), HomeActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
        ForgetPassword.setOnClickListener(event->navController.navigate(R.id.action_loginFragment_to_forgetPasswordFragment));
    }

    private void extractElements(View view){
        Register = view.findViewById(R.id.tv_Registers);
        Login = view.findViewById(R.id.lpLogin_btn);
        ForgetPassword = view.findViewById(R.id.tv_ForgetPwd);
        lp_tfEmail = view.findViewById(R.id.lp_tfEmail);
        lp_tfPassword = view.findViewById(R.id.lp_tfPassword);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViewModel();
    }

    private void bindViewModel() {
        loginViewModel = new ViewModelProvider(LoginFragment.this)
                .get(LoginViewModel.class);
    }

    private void observeMutableLiveData() {
        observeIsEmailEmpty();
        observeIsPasswordEmpty();
    }

    private void observeIsEmailEmpty() {
        loginViewModel.isEmailEmpty.observe(
                getViewLifecycleOwner(), helperViewModel -> {
                    if(helperViewModel.isSuccess()){
                        lp_tfEmail.setError(helperViewModel.getMessage());
                    }
                    else{
                        lp_tfEmail.setError(null);
                    }

                });
    }

    private void observeIsPasswordEmpty() {
        loginViewModel.isPasswordEmpty.observe(
                getViewLifecycleOwner(), helperViewModel -> {
                    if(helperViewModel.isSuccess()){
                        lp_tfPassword.setError(helperViewModel.getMessage());
                    }
                    else{
                        lp_tfPassword.setError(null);
                    }

                });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        this.extractElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        this.handleButtonTrigger();
        observeMutableLiveData();
    }
}