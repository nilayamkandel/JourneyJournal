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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.frameworks.firebase.FirebaseDbImpl;

public class DashboardFragment extends Fragment implements  DashboardHelper {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DashboardRecyclerViewAdapter dashboardRecyclerViewAdapter;
    private NavController navController;
    private FirebaseDbImpl databaseImpl = new FirebaseDbImpl();

    private void extractElements(View view){
        Context context = requireContext();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        this.linearLayoutManager = new LinearLayoutManager(context);
        this.recyclerView = view.findViewById(R.id.rV);
        this.dashboardRecyclerViewAdapter = new DashboardRecyclerViewAdapter(this);
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
        this.extractElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initListElement();
    }
    @Override
    public void SetOnItemClickListener() {
//        TODO: Import data
        navController.navigate(R.id.viewFragment);
    }
}