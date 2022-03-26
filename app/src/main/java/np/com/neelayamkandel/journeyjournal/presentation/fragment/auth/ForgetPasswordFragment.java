package np.com.neelayamkandel.journeyjournal.presentation.fragment.auth;

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
import np.com.neelayamkandel.journeyjournal.viewmodel.ForgetPasswordViewModel;
import np.com.neelayamkandel.journeyjournal.viewmodel.LoginViewModel;


public class ForgetPasswordFragment extends Fragment {
    private TextView BackLogin;
    private Button fpReset_btn;
    private TextInputLayout fp_tfEmail;
    private NavController navController;
    private ForgetPasswordViewModel forgetPasswordViewModel;

    private void handleButtonTrigger() {
        BackLogin.setOnClickListener(event -> navController.navigate(R.id.action_forgetPasswordFragment_to_loginFragment));
        fpReset_btn.setOnClickListener(event->{
            forgetPasswordViewModel.validateResetCredentials(fp_tfEmail.getEditText().getText().toString().trim(), getViewLifecycleOwner());
        });

    }

    private void extractElements(View view) {
        BackLogin = view.findViewById(R.id.back_to_login);
        fpReset_btn = view.findViewById(R.id.fpReset_btn);
        fp_tfEmail = view.findViewById(R.id.fp_tfEmail);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViewModel();
    }

    private void bindViewModel() {
        forgetPasswordViewModel = new ViewModelProvider(this)
                .get(ForgetPasswordViewModel.class);
    }

    private void observeMutableLiveData(){
        observeIsEmailEmpty();
        observeIsResetSuccess();
    }

    private void observeIsResetSuccess() {
        forgetPasswordViewModel.getIsResetSuccess().observe(
                getViewLifecycleOwner(), helperViewModel -> {
                    Toast.makeText(requireContext(), helperViewModel.getMessage(), Toast.LENGTH_LONG).show();
                    if(helperViewModel.isSuccess()){
                        navController.navigate(R.id.action_forgetPasswordFragment_to_loginFragment);
                    }
                }
        );
    }

    private void observeIsEmailEmpty() {
        forgetPasswordViewModel.getIsEmailEmpty().observe(
                getViewLifecycleOwner(), helperViewModel -> {
                    if(helperViewModel.isSuccess()){
                        fp_tfEmail.setError(helperViewModel.getMessage());
                        fp_tfEmail.requestFocus();
                    }
                    else{
                        fp_tfEmail.setError(null);
                    }

                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        this.extractElements(view);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        this.handleButtonTrigger();
    }
}