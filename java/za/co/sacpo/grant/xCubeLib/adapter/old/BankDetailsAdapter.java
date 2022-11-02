package za.co.sacpo.grant.xCubeLib.adapter.old;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.model.MBankDetails;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class BankDetailsAdapter extends RecyclerView.Adapter<BankDetailsAdapter.BankHolder> {
    private List<MBankDetails> mBankDetailsList;

    public BankDetailsAdapter(List<MBankDetails> mBankDetailsList) {
        this.mBankDetailsList = mBankDetailsList;
    }
    @Override
    public BankDetailsAdapter.BankHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_listing, parent, false);
       return new BankDetailsAdapter.BankHolder(itemView);
    }

    @Override
        public void onBindViewHolder(BankHolder holder, int position) {
        MBankDetails mBankDetails=mBankDetailsList.get(position);

       /* holder.lblAccountHolderName.setText(mBankDetails.getDate);
        holder.lblBankName.setText(mBankDetails.getDate);
        holder.lblBankBranchName.setText(mBankDetails.getDate);
        holder.lblBankBranchCode.setText(mBankDetails.getDate);
        holder.lblBankDate .setText(mBankDetails.getDate);
        */
    }

    @Override
    public int getItemCount() {

        if(mBankDetailsList!=null)
        {
            return mBankDetailsList.size();
        }

        return 0;
    }

    public class BankHolder extends RecyclerView.ViewHolder {

        TextView lblAccountHolderName,lblBankName,lblBankBranchName,lblBankBranchCode,lblBankDate;
        public BankHolder(View itemView) {
            super(itemView);
            lblAccountHolderName= (TextView) itemView.findViewById(R.id.lblAccountHolderName);
            lblBankName= (TextView) itemView.findViewById(R.id.lblBankName);
            lblBankBranchName= (TextView) itemView.findViewById(R.id.lblBankBranchName);
            lblBankBranchCode= (TextView) itemView.findViewById(R.id.lblBankBranchCode);
            lblBankDate= (TextView) itemView.findViewById(R.id.lblBankDate);
        }
    }
}
