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

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.dao.auth.registration.Registration;
import np.com.neelayamkandel.journeyjournal.dao.helper.SuccessHelper;
import np.com.neelayamkandel.journeyjournal.frameworks.firebase.FirebaseAuthImpl;
import np.com.neelayamkandel.journeyjournal.presentation.activity.AuthActivity;
import np.com.neelayamkandel.journeyjournal.presentation.fragment.auth.LoginFragment;
import np.com.neelayamkandel.journeyjournal.viewmodel.LoginViewModel;
import np.com.neelayamkandel.journeyjournal.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {
    private static final int CameraRequestCode = 102;
    private static final int GalleryRequestCode = 105;
    private String TAG = "J_" + ProfileFragment.class.getSimpleName();
    private FirebaseUser firebaseuser;
    private Registration registration;
    private ProfileViewModel profileViewModel;
    private ImageView pp_Img;
    private TextInputLayout pp_email;
    private FloatingActionButton pp_gallery;
    private FloatingActionButton pp_camera;
    private TextInputLayout pp_userName;
    private Button btn_update;
    private Uri uri;
    private String extractImage;
    private Bitmap image;
    private boolean camera = false;
    private FirebaseAuthImpl firebaseAuthImpl= new FirebaseAuthImpl();

    private void handleButtonTrigger(){
        btn_update.setOnClickListener(event->{
        profileViewModel.UpdateProfile(firebaseuser,
                new Registration(pp_userName.getEditText().getText().toString().trim(),
                registration.getEmail()),uri, getContext(), getViewLifecycleOwner(),camera, image);
        });
        pp_gallery.setOnClickListener(event->{
            camera  = false;
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, GalleryRequestCode);
        });
        pp_camera.setOnClickListener(event->{
            camera  = false;
            Intent gallery = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(gallery, CameraRequestCode);
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CameraRequestCode){
            if(resultCode == Activity.RESULT_OK){
                image = (Bitmap) data.getExtras().get("data");
                uri = null;
                pp_Img.setImageBitmap(image);
            }
        }

        if(requestCode == GalleryRequestCode){
            if(resultCode == Activity.RESULT_OK){
                uri = data.getData();
                image = null;
                pp_Img.setImageURI(uri);
            }
        }
    }

    public void extractElementFromIntent(){
        if(requireActivity().getIntent()!= null){
            firebaseuser = requireActivity().getIntent().getParcelableExtra("USER");
            registration = (Registration) requireActivity().getIntent().getSerializableExtra("PROFILE");
            extractImage = requireActivity().getIntent().getStringExtra("IMAGE");
            Log.d(TAG, "extractElementFromIntent: " + firebaseuser.getUid() + " email" + registration.getEmail());
        }
    }


    public void extractElements(View view){
        pp_Img = view.findViewById(R.id.pp_Img);
        pp_gallery = view.findViewById(R.id.pp_gallery);
        pp_camera = view.findViewById(R.id.pp_camera);
        pp_userName = view.findViewById(R.id.pp_userName);
        pp_email = view.findViewById(R.id.pp_email);
        btn_update = view.findViewById(R.id.btn_update);
    }

    public void PopulateData(){
        Log.d(TAG, "PopulateData: " + extractImage);
        if(extractImage!= null){
            Glide.with(requireContext()).load(extractImage).into(pp_Img);
        }
        else{
            pp_Img.setImageResource(R.drawable.user);
        }
        pp_userName.getEditText().setText(registration.getDisplayName());
        pp_email.getEditText().setText(registration.getEmail());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViewModel();
        }

    private void bindViewModel() {
        profileViewModel = new ViewModelProvider(ProfileFragment.this)
                .get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        this.extractElements(view);
        this.extractElementFromIntent();
        this.PopulateData();
        return view;
    }
    private void ObserveMutableLiveData(){
        profileViewModel.getIsDisplayNameEmpty().observe(getViewLifecycleOwner(), helperViewModel -> {
            if(helperViewModel.isSuccess()){
                pp_userName.setError(helperViewModel.getMessage());
                pp_userName.requestFocus();
            }
            else{
                pp_userName.setError(null);
            }
        });
        profileViewModel.getIsProfileMaintainSuccess().observe(getViewLifecycleOwner(), successHelper -> {
            if(successHelper.isSuccess()){
                firebaseAuthImpl.Logout();
                startActivity(new Intent(getContext(), AuthActivity.class));
                getActivity().finish();
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.handleButtonTrigger();
        ObserveMutableLiveData();
    }


}