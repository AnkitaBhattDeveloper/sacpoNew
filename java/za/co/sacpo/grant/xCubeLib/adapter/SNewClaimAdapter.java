package za.co.sacpo.grant.xCubeLib.adapter;

import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dataObj.SNewClaimObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;

import za.co.sacpo.grant.xCubeStudent.claims.SPastClaimDA;
import za.co.sacpo.grant.xCubeStudent.forms.SUploadFormA;


public class SNewClaimAdapter extends RecyclerView.Adapter<SNewClaimAdapter.ClaimHolder> {
    private List<SNewClaimObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels,clailExit;
    SPastClaimDA activity = new SPastClaimDA();
    ProgressDialog mProgressDialog;
    ProgressBar mProgressView;
    TextView tv_progress;
LinearLayout ll_download_progress;
    public SNewClaimAdapter(List<SNewClaimObj.Item> aObjList, Context baseActivityContext, AppCompatActivity activityInCall, LinearLayout ll_download_progress, ProgressBar mProgressView, TextView tv_progress) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.ll_download_progress = ll_download_progress;
        this.mProgressView = mProgressView;
        this.tv_progress = tv_progress;
    }

    public String getLabelFromDb(String inputLabel,int resId){
        String ValueLabel =baseActivityContext.getString(resId);
        dbSetaObj = DbHelper.getInstance(baseActivityContext);
        Cursor res = dbSetaObj.getDataValueByName(inputLabel);
        if(res.getCount() > 0) {
            try {
                while (res.moveToNext()) {
                    ValueLabel = res.getString(0);
                }
            }
            finally {
                if (res != null && !res.isClosed()) {
                    res.close();
                    dbSetaObj.close();
                }
            }
        }
        return ValueLabel;
    }

    @Override
    public SNewClaimAdapter.ClaimHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.s_new_claim_row, parent, false);
        return new SNewClaimAdapter.ClaimHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SNewClaimAdapter.ClaimHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head);
            holder.lblClaimMonth4.setTextColor(res.getColor(R.color.white));
            holder.lblClaimMonth4.setTypeface(holder.lblClaimMonth4.getTypeface(), Typeface.BOLD);



            holder.lblAction3.setTextColor(res.getColor(R.color.white));
            holder.lblAction3.setVisibility(View.VISIBLE);
            holder.btnAction3.setVisibility(View.GONE);
            holder.lblAction3.setTypeface(holder.lblAction3.getTypeface(), Typeface.BOLD);
            holder.linearAction3.setBackgroundColor(BColor);

            holder.lblAction.setTextColor(res.getColor(R.color.white));
            holder.lblAction.setVisibility(View.VISIBLE);
            holder.btnAction.setVisibility(View.GONE);
            holder.lblAction.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);
            holder.linearAction.setBackgroundColor(BColor);

    
            holder.lblAction2.setTextColor(res.getColor(R.color.white));
            holder.lblAction2.setVisibility(View.VISIBLE);
            holder.lblAction2.setTypeface(holder.lblAction2.getTypeface(), Typeface.BOLD);
            holder.btnAction2.setVisibility(View.GONE);
            holder.linearAction2.setBackgroundColor(BColor);

            holder.lblDate5.setTextColor(res.getColor(R.color.white));
            holder.lblDate5.setTypeface(holder.lblDate5.getTypeface(), Typeface.BOLD);

            holder.lblClaimAmount6.setTextColor(res.getColor(R.color.white));
            holder.lblClaimAmount6.setTypeface(holder.lblClaimAmount6.getTypeface(), Typeface.BOLD);

            holder.lblClaimYear3.setTextColor(res.getColor(R.color.white));
            holder.lblClaimYear3.setTypeface(holder.lblClaimYear3.getTypeface(), Typeface.BOLD);

            holder.lblSupervisorStatus7.setTextColor(res.getColor(R.color.white));
            holder.lblSupervisorStatus7.setTypeface(holder.lblSupervisorStatus7.getTypeface(), Typeface.BOLD);

            holder.lblGrantAdminStatus8.setTextColor(res.getColor(R.color.white));
            holder.lblGrantAdminStatus8.setTypeface(holder.lblGrantAdminStatus8.getTypeface(), Typeface.BOLD);


     
        }
        else{


            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
            holder.linearAction.setBackgroundColor(BColor);
            holder.linearAction2.setBackgroundColor(BColor);
            holder.linearAction3.setBackgroundColor(BColor);





        }




        holder.lblClaimMonth4.setText(aObjList.get(holder.getAdapterPosition()).claimMonth4);
        holder.lblClaimMonth4.setBackgroundColor(BColor);

        holder.lblDate5.setText(aObjList.get(holder.getAdapterPosition()).date5);
        holder.lblDate5.setBackgroundColor(BColor);

        holder.lblClaimAmount6.setText(aObjList.get(holder.getAdapterPosition()).claimAmount6);
        holder.lblClaimAmount6.setBackgroundColor(BColor);

        holder.lblClaimYear3.setText(aObjList.get(holder.getAdapterPosition()).claimYear3);
        holder.lblClaimYear3.setBackgroundColor(BColor);

        holder.lblAction.setText(aObjList.get(holder.getAdapterPosition()).downloadUSCF11);
        holder.lblAction2.setText(aObjList.get(holder.getAdapterPosition()).isUSCF15);
        holder.lblAction3.setText(aObjList.get(holder.getAdapterPosition()).downloadSCF14);

        holder.lblSupervisorStatus7.setText(aObjList.get(holder.getAdapterPosition()).supervisorStatus7);
        holder.lblSupervisorStatus7.setBackgroundColor(BColor);

        holder.lblGrantAdminStatus8.setText(aObjList.get(holder.getAdapterPosition()).grantAdminStatus8);
        holder.lblGrantAdminStatus8.setBackgroundColor(BColor);
        if(holder.getAdapterPosition()!=0){
            int dusStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).isDownloadUSCF12);
            int uStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).isUSCF15);
            int dsStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).isDownloadSCF13);

            holder.lblAction.setText(aObjList.get(holder.getAdapterPosition()).downloadUSCF11);
            holder.lblAction2.setText(aObjList.get(holder.getAdapterPosition()).isUSCF15);
            holder.lblAction3.setText(aObjList.get(holder.getAdapterPosition()).downloadSCF14);

            if(dusStatus==1){
                holder.lblAction.setVisibility(View.GONE);
                holder.btnAction.setVisibility(View.VISIBLE);
            }
            else{
                holder.btnAction.setVisibility(View.GONE);
                holder.lblAction.setVisibility(View.VISIBLE);
                holder.lblAction.setText("-");
            }
            if(uStatus==1){
                holder.btnAction2.setVisibility(View.VISIBLE);
                holder.lblAction2.setVisibility(View.GONE);
            }
            else{
                holder.btnAction2.setVisibility(View.GONE);
                holder.lblAction2.setVisibility(View.VISIBLE);
                holder.lblAction2.setText("-");
            }
            if(dsStatus==1){
                holder.btnAction3.setVisibility(View.VISIBLE);
                holder.lblAction3.setVisibility(View.GONE);
            }
            else{
                holder.btnAction3.setVisibility(View.GONE);
                holder.lblAction3.setVisibility(View.VISIBLE);
                holder.lblAction3.setText("-");
            }


            
            
            
            int sStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).isPendingSupervisorStatus9);
            if(sStatus!=1){
                holder.lblSupervisorStatus7.setBackgroundColor(res.getColor(R.color.red_link));
            }
            int gStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).isPendingGrantAdminStatus10);
            if(gStatus!=0){
                holder.lblGrantAdminStatus8.setBackgroundColor(res.getColor(R.color.red_link));
            }
        }
        Labels = this.getLabelFromDb("b_S126_downloadus", R.string.b_S126_downloadus);
        holder.btnAction.setText(Labels);
        Labels = this.getLabelFromDb("b_S126_upload", R.string.b_S126_upload);
        holder.btnAction2.setText(Labels);
        Labels = this.getLabelFromDb("b_S126_downloads", R.string.b_S126_downloads);
        holder.btnAction3.setText(Labels);

        holder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                String downloadPath =  holder.hItem.downloadUSCF11;
                /*Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                URL url = null;
                try {
                    url = new URL(downloadPath);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                intent.setData(Uri.parse(String.valueOf(url)));
                context.startActivity(intent);*/
                activity.downloadPDF(context,downloadPath,ll_download_progress,mProgressView,tv_progress);

            }
        });

        holder.btnAction3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                String downloadPath =  holder.hItem.downloadSCF14;
                activity.downloadPDF(context,downloadPath,ll_download_progress,mProgressView,tv_progress);
                /*Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                URL url = null;
                try {
                    url = new URL(downloadPath);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                intent.setData(Uri.parse(String.valueOf(url)));
                context.startActivity(intent);*/
            }
        });

        holder.btnAction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                String s_m_s_id = holder.hItem.aId2;
                inputUri.putString("s_m_s_id", s_m_s_id);
                inputUri.putString("generator", "S126");
                Context context = view.getContext();
                Intent intent = new Intent(context, SUploadFormA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(aObjList!=null) {
            return aObjList.size();
        }
        return 0;
    }
    public class ClaimHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public SNewClaimObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblAction,lblClaimMonth4,lblDate5,lblClaimAmount6,lblClaimYear3,lblSupervisorStatus7,lblGrantAdminStatus8, lblAction3,lblAction2;
         public final LinearLayout linearAction3,linearAction,linearAction2;

        Button btnAction3,btnAction2,btnAction;
        public ClaimHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.newClaimRow);
            linearAction3 = (LinearLayout) itemView.findViewById(R.id.linearAction3);
            linearAction = (LinearLayout) itemView.findViewById(R.id.linearAction);
            linearAction2 = (LinearLayout) itemView.findViewById(R.id.linearAction2);

            lblClaimMonth4= (TextView) itemView.findViewById(R.id.lblClaimMonth4);
            lblDate5= (TextView) itemView.findViewById(R.id.lblDate5);
            lblClaimAmount6= (TextView) itemView.findViewById(R.id.lblClaimAmount6);
            lblClaimYear3= (TextView) itemView.findViewById(R.id.lblClaimYear3);
            lblSupervisorStatus7= (TextView) itemView.findViewById(R.id.lblSupervisorStatus7);
            lblGrantAdminStatus8= (TextView) itemView.findViewById(R.id.lblGrantAdminStatus8);

            btnAction3= (Button) itemView.findViewById(R.id.btnAction3);
            lblAction3= (TextView) itemView.findViewById(R.id.lblAction3);
            
            lblAction2= (TextView) itemView.findViewById(R.id.lblAction2);            
            btnAction2= (Button) itemView.findViewById(R.id.btnAction2);
            
            btnAction= (Button) itemView.findViewById(R.id.btnAction);
            lblAction= (TextView) itemView.findViewById(R.id.lblAction);            
        }
    }
}