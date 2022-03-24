package np.com.neelayamkandel.journeyjournal.presentation.fragment.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.presentation.fragment.home.Dashboard.DashboardHelper;
import np.com.neelayamkandel.journeyjournal.presentation.fragment.home.Dashboard.DashboardRecyclerViewAdapter;

public class ViewFragment extends Fragment implements  DashboardHelper {
    private NavController navController;
    private Button view_btnEdit;

    private void extractElements(View view){
        Context context = requireContext();
    }

    private void handleButtonTrigger() {
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
    }

    public void SetOnItemClickListener() {
//        TODO: Import data
        navController.navigate(R.id.editFragment);
    }
}