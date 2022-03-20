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
import android.widget.TextView;

import np.com.neelayamkandel.journeyjournal.R;


public class ForgetPasswordFragment extends Fragment {
    private TextView BackLogin;
    private NavController navController;

    private void handleButtonTrigger() {
        BackLogin.setOnClickListener(event -> navController.navigate(R.id.action_forgetPasswordFragment_to_loginFragment));
    }

    private void extractElements(View view) {
        BackLogin = view.findViewById(R.id.back_to_login);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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