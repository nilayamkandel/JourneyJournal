package np.com.neelayamkandel.journeyjournal.presentation.fragment.home.Dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.dao.auth.registration.Registration;
import np.com.neelayamkandel.journeyjournal.dao.home.JourneyRecyclerDao;
import np.com.neelayamkandel.journeyjournal.frameworks.firebase.FirebaseDbImpl;

public class DashboardFragment extends Fragment implements  DashboardHelper {
    private String TAG = "J_" + DashboardFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DashboardRecyclerViewAdapter dashboardRecyclerViewAdapter;
    private NavController navController;
    private FirebaseUser firebaseUser;
    private ArrayList<JourneyRecyclerDao> journeyRecyclerDaos= new ArrayList<>();
    private FirebaseDbImpl databaseImpl = new FirebaseDbImpl();

    private void extractElements(View view){
        Context context = requireContext();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        this.linearLayoutManager = new LinearLayoutManager(context);
        this.recyclerView = view.findViewById(R.id.rV);
        this.databaseImpl.FetchJourney(firebaseUser.getUid());
        this.dashboardRecyclerViewAdapter = new DashboardRecyclerViewAdapter(this,journeyRecyclerDaos, getViewLifecycleOwner() );

    }
    private void ObserveMutableLiveData(){
        databaseImpl.Fetchjourney.observe(getViewLifecycleOwner(), journeyRecyclerDaos -> {
            this.dashboardRecyclerViewAdapter = new DashboardRecyclerViewAdapter(
                    this,journeyRecyclerDaos, getViewLifecycleOwner() );
            this.recyclerView.setAdapter(dashboardRecyclerViewAdapter);
        });
    }

    private void extractElementsFromIntent(){
        if(requireActivity().getIntent()!= null){
            firebaseUser = requireActivity().getIntent().getParcelableExtra("USER");
            Log.d(TAG, "extractElementsFromIntent: " + firebaseUser.getUid());
        }
    }

    private void initListElement(){
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
        this.recyclerView.setAdapter(this.dashboardRecyclerViewAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        this.extractElementsFromIntent();
        this.extractElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initListElement();
        this.ObserveMutableLiveData();
    }
    @Override
    public void SetOnItemClickListener(JourneyRecyclerDao journeyrecycler) {
        Bundle bundle = new Bundle();
        Log.d(TAG, "SetOnItemClickListener: " + journeyrecycler.getUuid());
        bundle.putSerializable("JOURNEYRECYCLERDAO",journeyrecycler);
        navController.navigate(R.id.viewFragment, bundle);
    }
}