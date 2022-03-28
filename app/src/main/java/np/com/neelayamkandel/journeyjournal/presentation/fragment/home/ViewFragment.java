package np.com.neelayamkandel.journeyjournal.presentation.fragment.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.dao.home.JourneyDao;
import np.com.neelayamkandel.journeyjournal.dao.home.JourneyRecyclerDao;
import np.com.neelayamkandel.journeyjournal.presentation.fragment.home.Dashboard.DashboardHelper;
import np.com.neelayamkandel.journeyjournal.presentation.fragment.home.Dashboard.DashboardRecyclerViewAdapter;

public class ViewFragment extends Fragment {
    private NavController navController;
    private Button view_btnEdit;
    private Button view_btnDelete;
    private TextInputLayout view_Title;
    private ImageView view_image;
    private TextInputLayout view_Date;
    private TextInputLayout view_Description;
    private JourneyRecyclerDao journeyRecyclerDao;
    private Button view_btnSave;
    private String TAG = "J_" + ViewFragment.class.getSimpleName();


    private void handleButtonTrigger() {

    }

    private void extractElementsFromIntent(Bundle bundle){
        if(requireActivity().getIntent()!= null){
            journeyRecyclerDao = (JourneyRecyclerDao) bundle.getSerializable("JOURNEYRECYCLERDAO");
            Log.d(TAG, "extractElementsFromIntent: " + journeyRecyclerDao.getUuid());
        }
    }

    private void extractElements(View view){
        view_btnSave= view.findViewById(R.id.view_btnSave);
        view_image= view.findViewById(R.id.view_image);
        view_btnDelete = view.findViewById(R.id.view_btnDelete);
        view_Title = view.findViewById(R.id.view_Title);
        view_Date = view.findViewById(R.id.view_Date);
        view_Description = view.findViewById(R.id.view_Description);
    }

    private  void PopulateData(){
        JourneyDao journeyDao = journeyRecyclerDao.getJourney();
        if(journeyDao.getImageUri()!= null){
            Glide.with(requireContext()).load(journeyDao.getImageUri()).into(view_image);
        }
            else{
            view_image.setImageResource(R.drawable.img);
            }
            view_Title.getEditText().setText(journeyDao.getTitle());
            view_Date.getEditText().setText(journeyDao.getDate());
            view_Description.getEditText().setText(journeyDao.getDescription());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

}