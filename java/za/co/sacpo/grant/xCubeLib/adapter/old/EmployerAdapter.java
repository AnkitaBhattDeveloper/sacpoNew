package za.co.sacpo.grant.xCubeLib.adapter.old;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import za.co.sacpo.grant.R;

public class EmployerAdapter extends RecyclerView.Adapter<EmployerAdapter.EmployerHolder> {

    private Context mContext;

    public EmployerAdapter(Context mContext){
        this.mContext = mContext;

    }

    @Override
    public EmployerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.employee_row_list,parent,false);
        return new EmployerHolder(v);
    }

    @Override
    public void onBindViewHolder(EmployerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class EmployerHolder extends RecyclerView.ViewHolder{
            TextView txEmployerApprovedAmount, txEmployerApprovedBy, txEmployerDate;
        public EmployerHolder(View itemView) {
            super(itemView);

            txEmployerApprovedAmount = (TextView) itemView.findViewById(R.id.txEmployerApprovedAmount);
            txEmployerApprovedBy = (TextView) itemView.findViewById(R.id.txEmployerApprovedBy);
            txEmployerDate = (TextView) itemView.findViewById(R.id.txEmployerDate);
        }
    }
}
