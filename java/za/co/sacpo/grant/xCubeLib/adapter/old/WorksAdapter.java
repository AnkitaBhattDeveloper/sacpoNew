package za.co.sacpo.grant.xCubeLib.adapter.old;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.model.MWorks;

/**
 * Created by xcube-06 on 8/8/18.
 */

public class WorksAdapter extends RecyclerView.Adapter<WorksAdapter.WorksHolder> {

    private List<MWorks> mWorksList;

    public WorksAdapter(List<MWorks> mWorksList) {
        this.mWorksList = mWorksList;

    }

    @Override
    public WorksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.works_row_listing, parent, false);
        return new WorksAdapter.WorksHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WorksHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(mWorksList!=null)
        {
            return mWorksList.size();
        }

        return 0;
    }

    public class WorksHolder extends RecyclerView.ViewHolder {

        TextView txlabel;

        public WorksHolder(View itemView) {
            super(itemView);

              }

              }
          }
