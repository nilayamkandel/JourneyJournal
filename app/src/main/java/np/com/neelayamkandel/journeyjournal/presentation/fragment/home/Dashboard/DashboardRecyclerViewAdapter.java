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
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import np.com.neelayamkandel.journeyjournal.R;
import np.com.neelayamkandel.journeyjournal.dao.home.JourneyRecyclerDao;

public class DashboardRecyclerViewAdapter extends RecyclerView.Adapter<DashboardRecyclerViewAdapter.DashboardRecyclerViewHolder>{
    private final DashboardHelper dashboardHelper;
    private final ArrayList<JourneyRecyclerDao> journeyRecyclerDaos;
    private Context context;
    private LifecycleOwner owner;

    public DashboardRecyclerViewAdapter(DashboardHelper dashboardHelper, ArrayList<JourneyRecyclerDao> journeyRecyclerDaos, LifecycleOwner owner) {
        this.dashboardHelper = dashboardHelper;
        this.journeyRecyclerDaos = journeyRecyclerDaos;
        this.owner = owner;
    }

    @NonNull
    @Override
    public DashboardRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        Log.d("JJ", "onCreateViewHolder: ");
        return new DashboardRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardRecyclerViewHolder holder, int position) {
        JourneyRecyclerDao journeyDao = journeyRecyclerDaos.get(position);
        if(journeyDao.getJourney().getImageUri()!= null) {
            Glide.with(context).load(journeyDao.getJourney().getImageUri()).into(holder.image);
        }
        else{
            holder.image.setImageResource(R.drawable.img);
        }
        holder.txt_Title.setText(journeyDao.getJourney().getTitle());
        holder.txt_Date.setText(journeyDao.getJourney().getDate());
        Log.d("JJHomeAdapter", "onBindViewHolder:  boring");
    }


    @Override
    public int getItemCount() {
        if(journeyRecyclerDaos != null){
            return journeyRecyclerDaos.size();
        }
        else{
            return 0;
        }
    }

    class DashboardRecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        MaterialTextView txt_Title;
        MaterialTextView txt_Date;

        public DashboardRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            // ToDo: extract data from cardview using id and handle click event

            image = itemView.findViewById(R.id.image);
            txt_Title = itemView.findViewById(R.id.txt_Title);
            txt_Date = itemView.findViewById(R.id.txt_Date);

            itemView.setOnClickListener(view->{
                JourneyRecyclerDao journeyrecycler = journeyRecyclerDaos.get(getAdapterPosition());
                dashboardHelper.SetOnItemClickListener(journeyrecycler);
            });
        }
    }
}
