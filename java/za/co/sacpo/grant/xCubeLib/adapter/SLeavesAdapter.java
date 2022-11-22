package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.dataObj.SLeavesObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeStudent.leaves.SEditLeaveA;
import za.co.sacpo.grant.xCubeStudent.leaves.SLMotivationA;
import za.co.sacpo.grant.xCubeStudent.leaves.SLSupervisorCommentsA;
import za.co.sacpo.grant.xCubeStudent.leaves.SLeavesDA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class SLeavesAdapter extends RecyclerView.Adapter<SLeavesAdapter.LeavesHolder> {
    private List<SLeavesObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    SLeavesDA baseActivity;
    BaseAPC baseAPC = new BaseAPC() {
        @Override
        protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {

        }

        @Override
        protected void initializeViews() {

        }

        @Override
        protected void initializeListeners() {

        }

        @Override
        protected void initializeInputs() {

        }

        @Override
        protected void initializeLabels() {

        }

        @Override
        protected void setLayoutXml() {

        }

        @Override
        protected void verifyVersion() {

        }

        @Override
        protected void fetchVersionData() {

        }

        @Override
        protected void internetChangeBroadCast() {

        }
    };

    public SLeavesAdapter(List<SLeavesObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, SLeavesDA baseActivity) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.baseActivity = baseActivity;
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
    public LeavesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leave_row, parent, false);
        return new LeavesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LeavesHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(baseAPC.getTextcolorResourceId("row_head"));

            holder.lblMotivation.setTextColor(res.getColor(R.color.white));
            holder.lblMotivation.setVisibility(View.VISIBLE);
            holder.btnMotivation.setVisibility(View.GONE);
            holder.lblMotivation.setTypeface(holder.lblMotivation.getTypeface(), Typeface.BOLD);

            holder.lblLeaveMonth.setTypeface(holder.lblLeaveMonth.getTypeface(), Typeface.BOLD);
            holder.lblLeaveMonth.setTextColor(res.getColor(R.color.white));
            holder.lblLeaveFrom.setTypeface(holder.lblLeaveFrom.getTypeface(), Typeface.BOLD);
            holder.lblLeaveFrom.setTextColor(res.getColor(R.color.white));
            holder.lblLeaveTo.setTypeface(holder.lblLeaveTo.getTypeface(), Typeface.BOLD);
            holder.lblLeaveTo.setTextColor(res.getColor(R.color.white));
            holder.lblAnnualLeave.setTypeface(holder.lblAnnualLeave.getTypeface(), Typeface.BOLD);
            holder.lblAnnualLeave.setTextColor(res.getColor(R.color.white));
            holder.lblSickLeave.setTypeface(holder.lblSickLeave.getTypeface(), Typeface.BOLD);
            holder.lblSickLeave.setTextColor(res.getColor(R.color.white));
            holder.lblOtherPaidLeave.setTypeface(holder.lblOtherPaidLeave.getTypeface(), Typeface.BOLD);
            holder.lblOtherPaidLeave.setTextColor(res.getColor(R.color.white));
            holder.lblUnpaidLeave.setTypeface(holder.lblUnpaidLeave.getTypeface(), Typeface.BOLD);
            holder.lblUnpaidLeave.setTextColor(res.getColor(R.color.white));
            holder.lblSApproval.setTypeface(holder.lblSApproval.getTypeface(), Typeface.BOLD);
            holder.lblSApproval.setTextColor(res.getColor(R.color.white));
            holder.lblSComments.setTypeface(holder.lblSComments.getTypeface(), Typeface.BOLD);
            holder.lblSComments.setTextColor(res.getColor(R.color.white));
            holder.lblUploads.setTypeface(holder.lblUploads.getTypeface(), Typeface.BOLD);
            holder.lblUploads.setTextColor(res.getColor(R.color.white));
            holder.lblEdit.setTypeface(holder.lblEdit.getTypeface(), Typeface.BOLD);
            holder.lblEdit.setTextColor(res.getColor(R.color.white));
            holder.lblRemove.setTypeface(holder.lblRemove.getTypeface(), Typeface.BOLD);
            holder.lblRemove.setTextColor(res.getColor(R.color.white));

            holder.lblRemove.setVisibility(View.VISIBLE);
            holder.btnRemove.setVisibility(View.GONE);

            holder.lblEdit.setVisibility(View.VISIBLE);
            holder.btnEdit.setVisibility(View.GONE);

            holder.lblUploads.setVisibility(View.VISIBLE);
            holder.btnUploads.setVisibility(View.GONE);
            holder.lblSComments.setVisibility(View.VISIBLE);
            holder.btnSComments.setVisibility(View.GONE);

        }
        else{
            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
        }
        holder.btnMotivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle inputUri = new Bundle();
                String motivation = String.valueOf(holder.hItem.aLeMotivation11);
                inputUri.putString("motivation", motivation);
                inputUri.putString("generator", "S115");
                String dated =  holder.hItem.aLeFromDate4+"/"+holder.hItem.aLeMonth3+ " - "+holder.hItem.aLeToDate5+"/"+holder.hItem.aLeMonth3;
                inputUri.putString("dated", dated);
                Context context = v.getContext();
                Intent intent = new Intent(context, SLMotivationA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);

            }
        });
        holder.btnSComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                String scomments = String.valueOf(holder.hItem.aLeSComment18);
                inputUri.putString("scomments", scomments);
                inputUri.putString("generator", "S115");
                String dated =  holder.hItem.aLeFromDate4+"/"+holder.hItem.aLeMonth3+ " - "+holder.hItem.aLeToDate5+"/"+holder.hItem.aLeMonth3;
                inputUri.putString("dated", dated);
                Context context = v.getContext();
                Intent intent = new Intent(context, SLSupervisorCommentsA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });
        holder.btnUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                String download_url = holder.hItem.aLeIsDocumentPath14;
                URL url = null;
                try {
                    url = new URL(URLHelper.DOMAIN_URL+""+download_url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                intent.setData(Uri.parse(String.valueOf(url)));
                context.startActivity(intent);
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                String leave_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("generator", "S115");
                inputUri.putString("leave_id", leave_id);
                Context context = view.getContext();
                Intent intent = new Intent(context, SEditLeaveA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String att_ids = String.valueOf(holder.hItem.aId2);
                baseActivity.removeLeave(att_ids);
            }
        });
        holder.linearLMotivation.setBackgroundColor(BColor);
        holder.linearLSComments.setBackgroundColor(BColor);
        holder.linearLUploads.setBackgroundColor(BColor);
        holder.linearLEdit.setBackgroundColor(BColor);
        holder.linearLRemove.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_S115_SLeave_Btn_details",R.string.l_S115_SLeave_Btn_details);
        holder.btnMotivation.setText(Labels);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.btnMotivation.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
            holder.btnMotivation.setTextColor(baseActivityContext.getResources().getColor(baseAPC.getTextcolorResourceId("btn_textColor")));
            holder.btnSComments.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
            holder.btnSComments.setTextColor(baseActivityContext.getResources().getColor(baseAPC.getTextcolorResourceId("btn_textColor")));
            holder.btnUploads.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
            holder.btnUploads.setTextColor(baseActivityContext.getResources().getColor(baseAPC.getTextcolorResourceId("btn_textColor")));
            holder.btnEdit.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
            holder.btnEdit.setTextColor(baseActivityContext.getResources().getColor(baseAPC.getTextcolorResourceId("btn_textColor")));
            holder.btnRemove.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
            holder.btnRemove.setTextColor(baseActivityContext.getResources().getColor(baseAPC.getTextcolorResourceId("btn_textColor")));

        }

        holder.btnSComments.setText(Labels);
        Labels =this.getLabelFromDb("l_S115_SLeave_Btn_edit",R.string.l_S115_SLeave_Btn_edit);
        holder.btnEdit.setText(Labels);
        Labels =this.getLabelFromDb("l_S115_SLeave_Btn_remove",R.string.l_S115_SLeave_Btn_remove);
        holder.btnRemove.setText(Labels);
        Labels =this.getLabelFromDb("l_S115_SLeave_Btn_downloads",R.string.l_S115_SLeave_Btn_downloads);
        holder.btnUploads.setText(Labels);

        holder.lblLeaveMonth.setText(aObjList.get(holder.getAdapterPosition()).aLeMonth3);
        holder.lblLeaveMonth.setBackgroundColor(BColor);
        holder.lblLeaveFrom.setText(aObjList.get(holder.getAdapterPosition()).aLeFromDate4);
        holder.lblLeaveFrom.setBackgroundColor(BColor);
        holder.lblLeaveTo.setText(aObjList.get(holder.getAdapterPosition()).aLeToDate5);
        holder.lblLeaveTo.setBackgroundColor(BColor);
        holder.lblAnnualLeave.setText(aObjList.get(holder.getAdapterPosition()).aLeAnnual6);
        holder.lblAnnualLeave.setBackgroundColor(BColor);
        holder.lblSickLeave.setText(aObjList.get(holder.getAdapterPosition()).aLeSick7);
        holder.lblSickLeave.setBackgroundColor(BColor);
        holder.lblOtherPaidLeave.setText(aObjList.get(holder.getAdapterPosition()).aLeOPaid8);
        holder.lblOtherPaidLeave.setBackgroundColor(BColor);
        holder.lblUnpaidLeave.setText(aObjList.get(holder.getAdapterPosition()).aLeUnPaid9);
        holder.lblUnpaidLeave.setBackgroundColor(BColor);
        holder.lblSApproval.setText(aObjList.get(holder.getAdapterPosition()).aLeSaStatus10);

        if(aObjList.get(holder.getAdapterPosition()).aLeSaStatus10.equalsIgnoreCase("pending")){
            holder.lblSApproval.setBackgroundColor(res.getColor(R.color.red_link));
        }
        else if(aObjList.get(holder.getAdapterPosition()).aLeSaStatus10.equalsIgnoreCase("APPROVED")){
            holder.lblSApproval.setBackgroundColor(res.getColor(R.color.yellow));
        }
        else {
            holder.lblSApproval.setBackgroundColor(BColor);
        }

        holder.lblMotivation.setText(aObjList.get(holder.getAdapterPosition()).aLeMotivation11);
        holder.lblMotivation.setBackgroundColor(BColor);

        holder.lblSComments.setText(aObjList.get(holder.getAdapterPosition()).aLeSComment18);
        holder.lblSComments.setBackgroundColor(BColor);

        holder.lblUploads.setText(aObjList.get(holder.getAdapterPosition()).aLeIsDocumentPath14);
        holder.lblUploads.setBackgroundColor(BColor);

        holder.lblEdit.setText(aObjList.get(holder.getAdapterPosition()).aLeEditBtn16);
        holder.lblEdit.setBackgroundColor(BColor);

        holder.lblRemove.setText(aObjList.get(holder.getAdapterPosition()).aLeRemoveBtn17);
        holder.lblRemove.setBackgroundColor(BColor);





        holder.lblLeaveMonth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLeMonth3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLeaveMonth,sToolTip,4);
            }
        });
        holder.lblLeaveFrom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLeFromDate4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLeaveFrom,sToolTip,4);
            }
        });
        holder.lblLeaveTo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLeToDate5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLeaveTo,sToolTip,4);
            }
        });

        holder.lblAnnualLeave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLeAnnual6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblAnnualLeave,sToolTip,4);
            }
        });
        if(holder.getAdapterPosition()>0) {

            int is_btn_motivation = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aLeMotivationBtn15);
            if (is_btn_motivation == 1) {
                holder.lblMotivation.setVisibility(View.GONE);
                holder.btnMotivation.setVisibility(View.VISIBLE);
            } else {
                holder.lblMotivation.setVisibility(View.VISIBLE);
                holder.btnMotivation.setVisibility(View.GONE);
                holder.lblMotivation.setText("-");
            }
            int is_btn_remove = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aLeRemoveBtn17);
            if (is_btn_remove == 1) {
                holder.lblRemove.setVisibility(View.GONE);
                holder.btnRemove.setVisibility(View.VISIBLE);
            } else {
                holder.lblRemove.setVisibility(View.VISIBLE);
                holder.btnRemove.setVisibility(View.GONE);
                holder.lblRemove.setText("-");
            }

            int is_btn_uploads = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aLeIsDocument13);
            if (is_btn_uploads == 1) {
                holder.lblUploads.setVisibility(View.GONE);
                holder.btnUploads.setVisibility(View.VISIBLE);
            } else {
                holder.lblUploads.setVisibility(View.VISIBLE);
                holder.btnUploads.setVisibility(View.GONE);
                holder.lblUploads.setText("-");
            }

            int is_btn_scomments = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aLeIsSupervisorComm12);
            if (is_btn_scomments == 1) {
                holder.lblSComments.setVisibility(View.GONE);
                holder.btnSComments.setVisibility(View.VISIBLE);
            } else {
                holder.lblSComments.setVisibility(View.VISIBLE);
                holder.btnSComments.setVisibility(View.GONE);
                holder.lblSComments.setText("-");
            }

            int is_btn_edit = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aLeEditBtn16);

            if (is_btn_edit == 1) {
                holder.lblEdit.setVisibility(View.GONE);
                holder.btnEdit.setVisibility(View.VISIBLE);
            } else {
                holder.lblEdit.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.GONE);
                holder.lblEdit.setText("-");
            }
        }
    }

    @Override
    public int getItemCount() {
        if(aObjList!=null) {
            return aObjList.size();
        }
        return 0;
    }

    public class LeavesHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public SLeavesObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblSComments,lblUploads,lblEdit,lblRemove,lblLeaveMonth,lblLeaveFrom,lblLeaveTo,lblAnnualLeave,lblMotivation,lblSickLeave,lblOtherPaidLeave,lblUnpaidLeave,lblSApproval;
        public final Button btnMotivation, btnRemove,btnEdit,btnUploads,btnSComments;
        public final LinearLayout linearLMotivation,linearLRemove,linearLEdit,linearLUploads,linearLSComments;
        public LeavesHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.tRowL);
            lblLeaveMonth= (TextView) itemView.findViewById(R.id.lblLeaveMonth);
            lblLeaveFrom= (TextView) itemView.findViewById(R.id.lblLeaveFrom);
            lblLeaveTo= (TextView) itemView.findViewById(R.id.lblLeaveTo);
            lblAnnualLeave= (TextView) itemView.findViewById(R.id.lblAnnualLeave);


            lblSickLeave= (TextView) itemView.findViewById(R.id.lblSickLeave);
            lblOtherPaidLeave= (TextView) itemView.findViewById(R.id.lblOtherPaidLeave);
            lblUnpaidLeave= (TextView) itemView.findViewById(R.id.lblUnpaidLeave);
            lblSApproval= (TextView) itemView.findViewById(R.id.lblSApproval);

            lblMotivation= (TextView) itemView.findViewById(R.id.lblMotivation);
            linearLMotivation = (LinearLayout) itemView.findViewById(R.id.linearLMotivation);
            btnMotivation= (Button) itemView.findViewById(R.id.btnMotivation);
            btnRemove= itemView.findViewById(R.id.btnRemove);
            lblRemove= itemView.findViewById(R.id.lblRemove);
            linearLRemove= itemView.findViewById(R.id.linearLRemove);
            btnEdit= itemView.findViewById(R.id.btnEdit);
            lblEdit= itemView.findViewById(R.id.lblEdit);
            linearLEdit= itemView.findViewById(R.id.linearLEdit);
            btnUploads= itemView.findViewById(R.id.btnUploads);
            lblUploads= itemView.findViewById(R.id.lblUploads);
            linearLUploads= itemView.findViewById(R.id.linearLUploads);
            lblSComments= itemView.findViewById(R.id.lblSComments);
            linearLSComments = itemView.findViewById(R.id.linearLSComments);
            btnSComments= itemView.findViewById(R.id.btnSComments);
        }
    }
}
