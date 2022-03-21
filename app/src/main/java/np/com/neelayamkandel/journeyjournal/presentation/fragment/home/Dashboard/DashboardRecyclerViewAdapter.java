package np.com.neelayamkandel.journeyjournal.presentation.fragment.home.Dashboard;
//step-1: accessing cardviewholder and recyclerviewholder.
//step-2: bind cardview on recyclerview
//step3: populate datasource in cardview
//

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import np.com.neelayamkandel.journeyjournal.R;

public class DashboardRecyclerViewAdapter extends RecyclerView.Adapter<DashboardRecyclerViewAdapter.DashboardRecyclerViewHolder>{
    private final DashboardHelper dashboardHelper;

    public DashboardRecyclerViewAdapter(DashboardHelper dashboardHelper) {
        this.dashboardHelper = dashboardHelper;
    }

    @NonNull
    @Override
    public DashboardRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        Log.d("JJ", "onCreateViewHolder: ");
        return new DashboardRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardRecyclerViewHolder holder, int position) {
        holder.img_view.setImageResource(R.drawable.img);
        Log.d("JJHomeAdapter", "onBindViewHolder:  boring");
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class DashboardRecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView img_view;
        public DashboardRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            // ToDo: extract data from cardview using id and handle click event

            img_view = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(view->{
                dashboardHelper.SetOnItemClickListener();
            });
        }
    }
}
