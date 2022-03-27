package np.com.neelayamkandel.journeyjournal.presentation.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.dao.home.JourneyDao;
import np.com.neelayamkandel.journeyjournal.presentation.activity.HomeActivity;
import np.com.neelayamkandel.journeyjournal.viewmodel.CreateEditViewModel;

public class CreateFragment extends Fragment {
    private static final int CameraRequestCode = 102;
    private static final int GalleryRequestCode = 105;
    private Button create_btnSave;
    private TextInputLayout create_Title;
    private TextInputLayout create_Date;
    private TextInputLayout create_Description;
    private FloatingActionButton cp_gallery;
    private FloatingActionButton cp_camera;
    private ImageView create_image;
    private CreateEditViewModel createEditViewModel;
    private NavController navController;
    private FirebaseUser firebaseuser;
    private Uri uri;
    private Bitmap image;
    private boolean camera = false;

    private void handleButtonTrigger(){
        create_btnSave.setOnClickListener(event->{
            createEditViewModel.validateSaveCredentials(
                    new JourneyDao(
                            firebaseuser.getUid(), null, create_Title.getEditText().getText().toString().trim(),
                            create_Date.getEditText().getText().toString().trim(),create_Description.getEditText().getText().toString().trim()
                    ),uri, getContext(), getViewLifecycleOwner(),camera, image
            );
//            Intent intent = new Intent(requireActivity(), HomeActivity.class);
//            startActivity(intent);
//            requireActivity().finish();
        });
        cp_gallery.setOnClickListener(event->{
            camera  = false;
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, GalleryRequestCode);
        });
        cp_camera.setOnClickListener(event->{
            camera  = true;
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera, CameraRequestCode);
        });
    }

    public void extractElementFromIntent(){
        if(requireActivity().getIntent()!= null) {
            firebaseuser = requireActivity().getIntent().getParcelableExtra("USER");
    }
}

    private void extractElements(View view){
        create_Title = view.findViewById(R.id.create_Title);
        cp_camera = view.findViewById(R.id.cp_camera);
        cp_gallery = view.findViewById(R.id.cp_gallery);
        create_image = view.findViewById(R.id.create_image);
        create_Date = view.findViewById(R.id.create_Date);
        create_Description = view.findViewById(R.id.create_Description);
        create_btnSave = view.findViewById(R.id.create_btnSave);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CameraRequestCode){
            if(resultCode == Activity.RESULT_OK){
                image = (Bitmap) data.getExtras().get("data");
                uri = null;
                create_image.setImageBitmap(image);
            }
        }

        if(requestCode == GalleryRequestCode){
            if(resultCode == Activity.RESULT_OK){
                uri = data.getData();
                image = null;
                create_image.setImageURI(uri);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViewModel();
    }

    private void bindViewModel() {
        createEditViewModel = new ViewModelProvider(CreateFragment.this)
                .get(CreateEditViewModel.class);
    }

    private void observeMutableLiveData(){
        observeIsTitleEmpty();
        createEditViewModel.isAddSuccess.observe(getViewLifecycleOwner(),journeyModel -> {
            Toast.makeText(getContext(),journeyModel.getMessage(),Toast.LENGTH_LONG).show();
            if(journeyModel.isSuccess()){
                navController.navigate(R.id.dashboardFragment);
            }
        });

    }

    private void observeIsTitleEmpty() {
        createEditViewModel.isTitleEmpty.observe(
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
        this.extractElementFromIntent();
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