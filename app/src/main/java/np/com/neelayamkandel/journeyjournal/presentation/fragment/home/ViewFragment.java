package np.com.neelayamkandel.journeyjournal.presentation.fragment.home;

import android.app.Activity;
import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.dao.home.JourneyDao;
import np.com.neelayamkandel.journeyjournal.dao.home.JourneyRecyclerDao;
import np.com.neelayamkandel.journeyjournal.frameworks.firebase.FirebaseDbImpl;
import np.com.neelayamkandel.journeyjournal.presentation.fragment.home.Dashboard.DashboardHelper;
import np.com.neelayamkandel.journeyjournal.presentation.fragment.home.Dashboard.DashboardRecyclerViewAdapter;
import np.com.neelayamkandel.journeyjournal.viewmodel.CreateEditViewModel;

public class ViewFragment extends Fragment {
    private static final int CameraRequestCode = 102;
    private static final int GalleryRequestCode = 105;
    private CreateEditViewModel createEditViewModel;
    private NavController navController;
    private FirebaseDbImpl firebaseDb = new FirebaseDbImpl();
    private Button view_btnDelete;
    private FloatingActionButton vp_gallery;
    private FloatingActionButton vp_camera;
    private TextInputLayout view_Title;
    private ImageView view_image;
    private TextInputLayout view_Date;
    private TextInputLayout view_Description;
    private JourneyRecyclerDao journeyRecyclerDao;
    private Button view_btnSave;
    private Button view_btnShare;
    private String TAG = "J_" + ViewFragment.class.getSimpleName();
    private Uri uri;
    private Bitmap image;
    private boolean camera = false;

    private void handleButtonTrigger() {
        view_btnSave.setOnClickListener(event->{
            createEditViewModel.validateUpdateCredentials(
                    journeyRecyclerDao.getUuid(),
                    new JourneyDao(
                            journeyRecyclerDao.getJourney().getUser(),
                            journeyRecyclerDao.getJourney().getImageUri(),
                            view_Title.getEditText().getText().toString().trim(),
                            view_Date.getEditText().getText().toString().trim(),
                            view_Description.getEditText().getText().toString().trim()
                    ),uri, getContext(), getViewLifecycleOwner(),camera, image
            );
        });
        view_btnDelete.setOnClickListener(event->{
            firebaseDb.DeleteJourney(journeyRecyclerDao.getJourney(), journeyRecyclerDao.getUuid(), getViewLifecycleOwner());
            firebaseDb.Deletejourney.observe(getViewLifecycleOwner(), successHelper -> {
                Toast.makeText(getContext(), successHelper.getMessage(), Toast.LENGTH_LONG).show();
                if(successHelper.isSuccess()){
                    navController.navigate(R.id.dashboardFragment);
                }
            });
        });

        vp_gallery.setOnClickListener(event->{
            camera  = false;
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, GalleryRequestCode);
        });
        vp_camera.setOnClickListener(event->{
            camera  = true;
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera, CameraRequestCode);
        });

        view_btnShare.setOnClickListener(event->{
            JourneyDao journeyDao = journeyRecyclerDao.getJourney();
            Intent Share = new Intent(Intent.ACTION_SEND);
            Share.setType("text/plain");
            String data = journeyDao.getTitle() + "\n\n" + journeyDao.getDescription() + "\n\n" + journeyDao.getDate();
            Share.putExtra(Intent.EXTRA_TEXT, data );
            startActivity(Intent.createChooser(Share, "Share Via"));
        });
    }

    private void extractElementsFromIntent(Bundle bundle){
        if(requireActivity().getIntent()!= null){
            journeyRecyclerDao = (JourneyRecyclerDao) bundle.getSerializable("JOURNEYRECYCLERDAO");
            Log.d(TAG, "extractElementsFromIntent: " + journeyRecyclerDao.getUuid());
        }
    }

    private void bindViewModel() {
        createEditViewModel = new ViewModelProvider(this)
                .get(CreateEditViewModel.class);
    }

    private void extractElements(View view){
        view_btnSave= view.findViewById(R.id.view_btnSave);
        vp_camera= view.findViewById(R.id.vp_camera);
        vp_gallery= view.findViewById(R.id.vp_gallery);
        view_image= view.findViewById(R.id.view_image);
        view_btnDelete = view.findViewById(R.id.view_btnDelete);
        view_Title = view.findViewById(R.id.view_Title);
        view_Date = view.findViewById(R.id.view_Date);
        view_Description = view.findViewById(R.id.view_Description);
        view_btnShare = view.findViewById(R.id.view_btnShare);
    }

    private  void PopulateData(){
        JourneyDao journeyDao = journeyRecyclerDao.getJourney();
        if(journeyDao.getImageUri()!= null){
            Glide.with(requireContext()).load(journeyDao.getImageUri()).into(view_image);
        }
            else{
            view_image.setImageResource(R.drawable.cardview);
            }
            view_Title.getEditText().setText(journeyDao.getTitle());
            view_Date.getEditText().setText(journeyDao.getDate());
            view_Description.getEditText().setText(journeyDao.getDescription());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CameraRequestCode){
            if(resultCode == Activity.RESULT_OK){
                image = (Bitmap) data.getExtras().get("data");
                uri = null;
                view_image.setImageBitmap(image);
            }
        }

        if(requestCode == GalleryRequestCode){
            if(resultCode == Activity.RESULT_OK){
                uri = data.getData();
                image = null;
                view_image.setImageURI(uri);
            }
        }
    }

    private void observeMutableLiveData(){
        observeIsTitleEmpty();
        createEditViewModel.isUpdateSuccess.observe(getViewLifecycleOwner(),journeyModel -> {
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
                        view_Title.setError(helperViewModel.getMessage());
                    }
                    else{
                        view_Title.setError(null);
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
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        this.extractElementsFromIntent(getArguments());
        this.extractElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        this.handleButtonTrigger();
        this.PopulateData();
        observeMutableLiveData();
    }

}