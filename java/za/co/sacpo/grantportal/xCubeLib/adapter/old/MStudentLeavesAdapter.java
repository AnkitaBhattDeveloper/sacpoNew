package za.co.sacpo.grantportal.xCubeLib.adapter.old;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.model.MStudentLeave;

/**
 * Created by xcube-06 on 8/8/18.
 */

public class MStudentLeavesAdapter extends RecyclerView.Adapter<MStudentLeavesAdapter.LeaveHolder> {

    private List<MStudentLeave> mStudentLeaveList;

    public MStudentLeavesAdapter(List<MStudentLeave> mStudentLeaveList)
    {

        this.mStudentLeaveList = mStudentLeaveList;
    }



    @Override
    public MStudentLeavesAdapter.LeaveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_leave_listing, parent, false);
        return new MStudentLeavesAdapter.LeaveHolder(itemView);
     }
    @Override
    public void onBindViewHolder(MStudentLeavesAdapter.LeaveHolder holder, int position) {
        MStudentLeave mStudentLeave=mStudentLeaveList.get(position);
        holder.txDate.setText(mStudentLeave.getDate());
        holder.txLeaveReasons.setText(mStudentLeave.getReasons());
        holder.txLeaveType.setText(mStudentLeave.getType());

    }

    @Override
    public int getItemCount() {

        if (mStudentLeaveList!=null)
        {
            return mStudentLeaveList.size();
        }
        return 0;
    }

       public class LeaveHolder extends RecyclerView.ViewHolder {
           TextView lblLeavetype,lblLeaveReasons,lblLeaveDate;
           TextView txLeaveType,txDate,txLeaveReasons;
        public LeaveHolder(View itemView) {
            super(itemView);
            lblLeavetype = (TextView) itemView.findViewById(R.id.lblLeavetype);
            lblLeaveReasons = (TextView) itemView.findViewById(R.id.lblLeaveReasons);
            lblLeaveDate = (TextView) itemView.findViewById(R.id.lblLeaveDate);
            txLeaveType = (TextView) itemView.findViewById(R.id.txLeaveType);
            txDate = (TextView) itemView.findViewById(R.id.txDate);
            txLeaveReasons = (TextView) itemView.findViewById(R.id.txLeaveReasons);

        }
    }
}
