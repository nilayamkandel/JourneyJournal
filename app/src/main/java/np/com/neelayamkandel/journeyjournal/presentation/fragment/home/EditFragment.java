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
import np.com.neelayamkandel.journeyjournal.viewmodel.EditViewModel;

public class EditFragment extends Fragment {
    private Button ep_btnEdit;
    private Button ep_btnSave;
    private TextInputLayout edit_Title;
    private TextInputLayout edit_Date;
    private TextInputLayout edit_Description;
    private NavController navController;
    private EditViewModel editViewModel;

    private void handleButtonTrigger(){
        ep_btnSave.setOnClickListener(event->{
            editViewModel.validateSaveCredentials(edit_Title.getEditText().getText().toString().trim());
            Intent intent = new Intent(requireActivity(), HomeActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }
     private void extractElements(View view){
        edit_Title = view.findViewById(R.id.edit_Title);
        edit_Date =  view.findViewById(R.id.edit_Date);
        edit_Description = view.findViewById(R.id.edit_Description);
        ep_btnEdit = view.findViewById(R.id.ep_btnEdit);
        ep_btnSave = view.findViewById(R.id.ep_btnSave);
     }

    private void bindViewModel() {
        editViewModel = new ViewModelProvider(EditFragment.this)
                .get(EditViewModel.class);
    }

    private void observeMutableLiveData(){
        observeIsTitleEmpty();
    }

    private void observeIsTitleEmpty() {
        editViewModel.isTitleEmpty.observe(
                getViewLifecycleOwner(), helperViewModel -> {
                    if(helperViewModel.isSuccess()){
                        edit_Title.setError(helperViewModel.getMessage());
                    }
                    else{
                        edit_Title.setError(null);
                    }

                });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_edit, container, false);
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