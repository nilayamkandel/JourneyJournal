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

import com.google.android.material.textfield.TextInputLayout;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.presentation.fragment.home.Dashboard.DashboardHelper;
import np.com.neelayamkandel.journeyjournal.presentation.fragment.home.Dashboard.DashboardRecyclerViewAdapter;

public class ViewFragment extends Fragment implements  DashboardHelper {
    private NavController navController;
    private Button view_btnEdit;
    private Button view_btnDelete;
    private TextInputLayout view_Title;
    private TextInputLayout view_Date;
    private TextInputLayout view_Description;


    private void handleButtonTrigger() {
        view_btnEdit.setOnClickListener(event->navController.navigate(R.id.editFragment));
    }

    private void extractElements(View view){
        view_btnEdit = view.findViewById(R.id.view_btnEdit);
        view_btnDelete = view.findViewById(R.id.view_btnDelete);
        view_Title = view.findViewById(R.id.view_Title);
        view_Date = view.findViewById(R.id.view_Title);
        view_Description = view.findViewById(R.id.view_Title);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        this.extractElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        this.handleButtonTrigger();
    }

    public void SetOnItemClickListener() {
//        TODO: Import data
        navController.navigate(R.id.editFragment);
    }
}