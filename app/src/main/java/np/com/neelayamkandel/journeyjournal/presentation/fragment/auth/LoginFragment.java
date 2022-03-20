package np.com.neelayamkandel.journeyjournal.presentation.fragment.auth;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.presentation.activity.HomeActivity;

public class LoginFragment extends Fragment {
    private Button  Login;
    private TextView Register;
    private TextView ForgetPassword;
    private NavController navController;

    private void handleButtonTrigger(){
        Register.setOnClickListener(event->navController.navigate(R.id.action_loginFragment_to_registerFragment));
        Login.setOnClickListener(event->{
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
}