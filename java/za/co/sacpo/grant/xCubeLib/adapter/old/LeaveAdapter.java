package za.co.sacpo.grant.xCubeLib.adapter.old;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.model.MStudentLeave;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.LeaveHolder> {
    private List<MStudentLeave> mStudentLeaveList;

    public LeaveAdapter(List<MStudentLeave> mStudentLeaveList) {
        this.mStudentLeaveList = mStudentLeaveList;
    }
    @Override
    public LeaveAdapter.LeaveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leave_row_listing, parent, false);
        return new LeaveAdapter.LeaveHolder(itemView);
    }
    @Override
    public void onBindViewHolder(LeaveAdapter.LeaveHolder holder, int position) {
        MStudentLeave mLeave= mStudentLeaveList.get(position);
       /* holder.lblLeaveDate.setText(mLeave.getDate);
        holder.lblLeaveReasons.setText(mLeave.getDate);
        holder.lblLeavetype.setText(mLeave.getDate);*/

    }
    @Override
    public int getItemCount() {
        if (mStudentLeaveList !=null)
        {
            return mStudentLeaveList.size();
        }
        return 0;
    }

    public class LeaveHolder extends RecyclerView.ViewHolder {
        TextView lblLeaveReasons,lblLeavetype,lblLeaveDate;

        public LeaveHolder(View itemView) {
            super(itemView);


        }
    }
}
