package np.com.neelayamkandel.journeyjournal.presentation.fragment.home;

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

import com.google.android.material.textfield.TextInputLayout;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.presentation.activity.HomeActivity;
import np.com.neelayamkandel.journeyjournal.viewmodel.CreateViewModel;

public class CreateFragment extends Fragment {
    private Button create_btnSave;
    private TextInputLayout create_Title;
    private TextInputLayout create_Date;
    private TextInputLayout create_Description;
    private CreateViewModel createViewModel;
    private NavController navController;

    private void handleButtonTrigger(){
        create_btnSave.setOnClickListener(event->{
            createViewModel.validateSaveCredentials(create_Title.getEditText().getText().toString().trim());
            Intent intent = new Intent(requireActivity(), HomeActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }

    private void extractElements(View view){
        create_Title = view.findViewById(R.id.create_Title);
        create_Date = view.findViewById(R.id.create_Date);
        create_Description = view.findViewById(R.id.create_Description);
        create_btnSave = view.findViewById(R.id.create_btnSave);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViewModel();
    }

    private void bindViewModel() {
        createViewModel = new ViewModelProvider(CreateFragment.this)
                .get(CreateViewModel.class);
    }

    private void observeMutableLiveData(){
        observeIsTitleEmpty();
    }

    private void observeIsTitleEmpty() {
        createViewModel.isTitleEmpty.observe(
                getViewLifecycleOwner(), helperViewModel -> {
                    if(helperViewModel.isSuccess()){
                        create_Title.setError(helperViewModel.getMessage());
                    }
                    else{
                        create_Title.setError(null);
                    }

                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
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