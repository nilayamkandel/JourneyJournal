package np.com.neelayamkandel.journeyjournal.presentation.fragment.home.Dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.neelayamkandel.journeyjournal.R;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DashboardRecyclerViewAdapter dashboardRecyclerViewAdapter;

    private void extractElements(View view){
        Context context = requireContext();
        this.linearLayoutManager = new LinearLayoutManager(context);
        this.recyclerView = view.findViewById(R.id.rV);
        this.dashboardRecyclerViewAdapter = new DashboardRecyclerViewAdapter();
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
}