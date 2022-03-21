package np.com.neelayamkandel.journeyjournal.presentation.fragment.home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.presentation.fragment.home.Dashboard.DashboardHelper;
import np.com.neelayamkandel.journeyjournal.presentation.fragment.home.Dashboard.DashboardRecyclerViewAdapter;

public class ViewFragment extends Fragment {
    private NavController navController;


    private void extractElements(View view){
        Context context = requireContext();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        return view;
    }

    public void SetOnItemClickListener() {
//        TODO: Import data
        navController.navigate(R.id.editFragment);
    }
}