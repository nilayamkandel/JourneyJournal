package np.com.neelayamkandel.journeyjournal.presentation.fragment.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.dao.auth.registration.RegistrationForm;
import np.com.neelayamkandel.journeyjournal.presentation.activity.HomeActivity;
import np.com.neelayamkandel.journeyjournal.viewmodel.RegisterViewModel;


public class RegisterFragment extends Fragment {
    private Button Register;
    private TextView Login;
    private TextInputLayout rp_tfUsername;
    private TextInputLayout rp_tfEmail;
    private TextInputLayout rp_tfPassword;
    private TextInputLayout rp_tfConfirmPassword;
    private NavController navController;
    private RegisterViewModel registerViewModel;

    private void handleButtonTrigger(){
        Register.setOnClickListener(event->{
//            Intent intent = new Intent(requireActivity(), HomeActivity.class);
//            startActivity(intent);
//            requireActivity().finish();
            registerViewModel.registrationValidation(new RegistrationForm(
                    rp_tfUsername.getEditText().getText().toString().trim(),
                    rp_tfEmail.getEditText().getText().toString().trim(),
                    rp_tfPassword.getEditText().getText().toString().trim(),
                    rp_tfConfirmPassword.getEditText().getText().toString().trim()
            ),getViewLifecycleOwner());
        });
        registerViewModel.getIsRegistrationSuccess().observe(getViewLifecycleOwner(),successHelper -> {
            Toast.makeText(getContext(),successHelper.getMessage(),Toast.LENGTH_LONG).show();
            if(successHelper.isSuccess()){
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });
        Login.setOnClickListener(event->navController.navigate(R.id.action_registerFragment_to_loginFragment));
    }

    private void extractElements(View view) {
        Register = view.findViewById(R.id.rp_btnRegister);
        Login = view.findViewById(R.id.tv_login);
        rp_tfUsername = view.findViewById(R.id.rp_tfUsername);
        rp_tfEmail = view.findViewById(R.id.rp_tfEmail);
        rp_tfPassword = view.findViewById(R.id.rp_tfPassword);
        rp_tfConfirmPassword = view.findViewById(R.id.rp_tfConfirmPassword);
    }

    private void observeMutableLiveData() {
        observeIsDisplayNameEmpty();
        observeIsEmailEmpty();
        observeIsPasswordEmpty();
        observeIsConfirmPasswordEmpty();
    }

    private void observeIsDisplayNameEmpty() {
        registerViewModel.isDisplayNameEmpty.observe(
                getViewLifecycleOwner(), helperViewModel -> {
                    if(helperViewModel.isSuccess()){
                        rp_tfUsername.setError(helperViewModel.getMessage());
                    }
                    else{
                        rp_tfUsername.setError(null);
                    }

                });

    }

    private void observeIsEmailEmpty() {
        registerViewModel.isEmailEmpty.observe(
                getViewLifecycleOwner(), helperViewModel -> {
                    if(helperViewModel.isSuccess()){
                        rp_tfEmail.setError(helperViewModel.getMessage());
                    }
                    else{
                        rp_tfEmail.setError(null);
                    }

                });
    }

    private void observeIsPasswordEmpty() {
        registerViewModel.isPasswordEmpty.observe(
                getViewLifecycleOwner(), helperViewModel -> {
                    if(helperViewModel.isSuccess()){
                        rp_tfPassword.setError(helperViewModel.getMessage());
                    }
                    else{
                        rp_tfPassword.setError(null);
                    }

                });
    }

    private void observeIsConfirmPasswordEmpty() {
        registerViewModel.isConfirmPasswordEmpty.observe(
                getViewLifecycleOwner(), helperViewModel -> {
                    if(helperViewModel.isSuccess()){
                        rp_tfConfirmPassword.setError(helperViewModel.getMessage());
                    }
                    else{
                        rp_tfConfirmPassword.setError(null);
                    }

                });
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
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