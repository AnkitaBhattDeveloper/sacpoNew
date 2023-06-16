package za.co.sacpo.grantportal.xCubeLib.adapter.old;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.model.MAttendence;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class AttendenceAdapter extends RecyclerView.Adapter<AttendenceAdapter.AttendenceHolder> {
    private List<MAttendence> mAttendenceList;

    public AttendenceAdapter(List<MAttendence> mAttendenceList) {
        this.mAttendenceList = mAttendenceList;
    }
    @Override
    public AttendenceHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendence_row_listing, parent, false);
        return new AttendenceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AttendenceHolder holder, int position) {
        MAttendence mAttendence=mAttendenceList.get(position);

       /* holder.lblAccountHolderName.setText(mBankDetails.getDate);
        holder.lblBankName.setText(mAttendence.getDate);
        holder.lblBankBranchName.setText(mAttendence.getDate);
        holder.lblBankBranchCode.setText(mAttendence.getDate);
        holder.lblBankDate .setText(mAttendence.getDate);
        */
    }

    @Override
    public int getItemCount() {

        if(mAttendenceList!=null)
        {
            return mAttendenceList.size();
        }

        return 0;
    }

    public class AttendenceHolder extends RecyclerView.ViewHolder {

        TextView lblAccountHolderName,lblBankName,lblBankBranchName,lblBankBranchCode,lblBankDate;
        public AttendenceHolder(View itemView) {
            super(itemView);
            lblAccountHolderName= (TextView) itemView.findViewById(R.id.lblAccountHolderName);
            lblBankName= (TextView) itemView.findViewById(R.id.lblBankName);
            lblBankBranchName= (TextView) itemView.findViewById(R.id.lblBankBranchName);
            lblBankBranchCode= (TextView) itemView.findViewById(R.id.lblBankBranchCode);
            lblBankDate= (TextView) itemView.findViewById(R.id.lblBankDate);
        }
    }
}
