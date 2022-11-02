package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.dataObj.SClaimObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeStudent.claims.SDownCFormA;
import za.co.sacpo.grant.xCubeStudent.claims.SDownUnSignCFormA;
import za.co.sacpo.grant.xCubeStudent.claims.SSubmitClaim;
import za.co.sacpo.grant.xCubeStudent.claims.SUpCFormA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class SClaimAdapter extends RecyclerView.Adapter<SClaimAdapter.ClaimHolder> {
    private List<SClaimObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels,clailExit;

    public SClaimAdapter(List<SClaimObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
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
    public ClaimHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sclaim_row, parent, false);
        return new ClaimHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ClaimHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head);
            holder.lblClaimDate.setTextColor(res.getColor(R.color.white));
            holder.lblClaimDate.setTypeface(holder.lblClaimDate.getTypeface(), Typeface.BOLD);

            Labels =this.getLabelFromDb("l_605_SClaim_submit",R.string.l_605_SClaim_submit);
            holder.lblUserApproval.setText(Labels);
            holder.lblUserApproval.setTextColor(res.getColor(R.color.white));
            holder.lblUserApproval.setVisibility(View.VISIBLE);
            holder.btnUserApproval.setVisibility(View.GONE);

            Labels =this.getLabelFromDb("l_605_SClaim_download",R.string.l_605_SClaim_download);
            holder.lblActionForm.setText(Labels);
            holder.lblActionForm.setTextColor(res.getColor(R.color.white));
            holder.lblActionForm.setVisibility(View.VISIBLE);
            holder.btnActionForm.setVisibility(View.GONE);

            holder.lblUserApproval.setTypeface(holder.lblUserApproval.getTypeface(), Typeface.BOLD);
            holder.lblActionForm.setTypeface(holder.lblActionForm.getTypeface(), Typeface.BOLD);
            

            holder.lblClaimMonth.setTextColor(res.getColor(R.color.white));
            holder.lblClaimMonth.setTypeface(holder.lblClaimMonth.getTypeface(), Typeface.BOLD);
            holder.lblClaimDaysWorked.setTextColor(res.getColor(R.color.white));
            holder.lblClaimDaysWorked.setTypeface(holder.lblClaimDaysWorked.getTypeface(), Typeface.BOLD);
            holder.lblClaimPLeave.setTextColor(res.getColor(R.color.white));
            holder.lblClaimPLeave.setTypeface(holder.lblClaimPLeave.getTypeface(), Typeface.BOLD);
            holder.lblClaimUPLeave.setTextColor(res.getColor(R.color.white));
            holder.lblClaimUPLeave.setTypeface(holder.lblClaimUPLeave.getTypeface(), Typeface.BOLD);
            holder.lblClaimedAmount.setTextColor(res.getColor(R.color.white));
            holder.lblClaimedAmount.setTypeface(holder.lblClaimedAmount.getTypeface(), Typeface.BOLD);
             holder.lblSApproval.setTextColor(res.getColor(R.color.white));
            holder.lblSApproval.setTypeface(holder.lblSApproval.getTypeface(), Typeface.BOLD);
             holder.lblAApproval.setTextColor(res.getColor(R.color.white));
            holder.lblAApproval.setTypeface(holder.lblAApproval.getTypeface(), Typeface.BOLD);
            //holder.lblSApproval.setText("SUPERVISOR APPROVAL");
            //holder.lblAApproval.setText("MANAGEMENT APPROVAL");



        }
        else{
            holder.lblUserApproval.setVisibility(View.GONE);
            holder.btnUserApproval.setVisibility(View.VISIBLE);
             holder.lblActionForm.setVisibility(View.GONE);
            holder.btnActionForm.setVisibility(View.VISIBLE);

            if(!aObjList.get(holder.getAdapterPosition()).claDate3.equals("")) {

                holder.btnUserApproval.setVisibility(View.VISIBLE);
                holder.btnActionForm.setVisibility(View.VISIBLE);
            }



            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }




        }


        holder.lblClaimDate.setText(aObjList.get(holder.getAdapterPosition()).claDate3);
        holder.lblClaimDate.setBackgroundColor(BColor);

        holder.btnUserApproval.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if(aObjList.get(holder.getAdapterPosition()).studentStatus9.equals("1")){
                    AlertDialog.Builder alertbox = new  AlertDialog.Builder(view.getRootView().getContext());
                    alertbox.setMessage("BUTTON DISABLED");
                    alertbox.setTitle("Warning");
                    alertbox.setIcon(R.mipmap.ic_launcher_alert_icon);

                    alertbox.setNeutralButton("DOWNLOAD SIGN STIPEND CLAIM MUST BE SUBMITTED ON THE 15TH OF EACH MONTH ",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {

                                }
                            });
                    alertbox.show();

                }
                else {
                    Bundle inputUri = new Bundle();

                    String stipend_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("stipend_id", stipend_id);

                    String month_year = String.valueOf(holder.hItem.claMonth4);
                    inputUri.putString("month_year", month_year);

                    String clamDate = String.valueOf(holder.hItem.claDate3);
                    inputUri.putString("clamDate", clamDate);

                    Context context = view.getContext();
                    Intent intent = new Intent(context, SSubmitClaim.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);


                }
            }
        });

        //SUBMIT CLAIM BY STUDNET
        holder.btnActionForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(view,aObjList.get(holder.getAdapterPosition()).aId2,aObjList.get(holder.getAdapterPosition()).claimDownloadStatus15,aObjList.get(holder.getAdapterPosition()).claimUploadStatus16);


            }
        });

        holder.linearLActionUserApproval.setBackgroundColor(BColor);
        if(aObjList.get(holder.getAdapterPosition()).studentStatus9.equals("1")) {
            Labels = this.getLabelFromDb("l_605_S_APPROVAL_SUBMITTED", R.string.l_605_S_APPROVAL_SUBMITTED);
            holder.btnUserApproval.setBackground(res.getDrawable(R.drawable.themed_small_button_disabled));
            holder.btnUserApproval.setTextColor(res.getColor(R.color.white));
        }

        else{
            Labels = this.getLabelFromDb("l_605_S_APPROVAL_NEW", R.string.l_605_S_APPROVAL_NEW);
        }

        holder.btnUserApproval.setText(Labels);
        holder.linearActionForm.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_605_downlode_form",R.string.l_605_downlode_form);
        holder.btnActionForm.setText(Labels);
        

        holder.lblClaimMonth.setText(aObjList.get(holder.getAdapterPosition()).claMonth4);
        holder.lblClaimMonth.setBackgroundColor(BColor);
        holder.lblClaimDaysWorked.setText(aObjList.get(holder.getAdapterPosition()).claDaysWorked5);
        holder.lblClaimDaysWorked.setBackgroundColor(BColor);
        holder.lblClaimPLeave.setText(aObjList.get(holder.getAdapterPosition()).paidLeaveCount6);
        holder.lblClaimPLeave.setBackgroundColor(BColor);
        holder.lblClaimUPLeave.setText(aObjList.get(holder.getAdapterPosition()).unpaidLeaveCount7);
        holder.lblClaimUPLeave.setBackgroundColor(BColor);
        holder.lblClaimedAmount.setText(aObjList.get(holder.getAdapterPosition()).amount8);
        holder.lblClaimedAmount.setBackgroundColor(BColor);

        holder.lblSApproval.setText(aObjList.get(holder.getAdapterPosition()).mentorStatusText13);
        holder.lblAApproval.setText(aObjList.get(holder.getAdapterPosition()).adminStatusText14);

        holder.lblSApproval.setBackgroundColor(BColor);
        holder.lblAApproval.setBackgroundColor(BColor);

        holder.lblClaimDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).claDate3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblClaimDate,sToolTip, 4);
            }
        });
        holder.lblClaimMonth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).claMonth4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblClaimMonth,sToolTip,4);
            }
        });
        holder.lblClaimDaysWorked.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).claDaysWorked5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblClaimDaysWorked,sToolTip,4);
            }
        });
        holder.lblClaimPLeave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).paidLeaveCount6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblClaimPLeave,sToolTip,4);
            }
        });
        holder.lblClaimUPLeave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).unpaidLeaveCount7;
                ((BaseAPC)activityInCall).showTooltip(holder.lblClaimUPLeave,sToolTip,4);
            }
        });
        holder.lblClaimedAmount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).amount8;
                ((BaseAPC)activityInCall).showTooltip(holder.lblClaimedAmount,sToolTip,4);
            }
        });

        holder.lblSApproval.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mentorStatusText13;
                ((BaseAPC)activityInCall).showTooltip(holder.lblSApproval,sToolTip,4);
            }
        });

        holder.lblAApproval.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).adminStatusText14;
                ((BaseAPC)activityInCall).showTooltip(holder.lblAApproval,sToolTip,4);
            }
        });
    }

    private void popup (final View view, final String stipend_id,final String downloadFileStatus15,final String uploadFileStatus16) {

        PopupMenu popup = new PopupMenu(baseActivityContext, view);
        popup.getMenuInflater().inflate(R.menu.popup_menu,popup.getMenu());


        Boolean hideOne = false;
        Boolean hideTwo = false;
        Boolean hideThree = false;
        if(downloadFileStatus15.equals("1")){
            hideOne=true;
        }
        if(uploadFileStatus16.equals("1")){
            hideThree=true;
        }
        popup.getMenu().findItem(R.id.one).setVisible(false);
        popup.getMenu().findItem(R.id.two).setVisible(false);
        popup.getMenu().findItem(R.id.three).setVisible(false);
        if (hideOne) {
            popup.getMenu().findItem(R.id.one).setVisible(true);
        }
        if (hideTwo) {
            popup.getMenu().findItem(R.id.two).setVisible(true);
        }
        if (hideThree) {
            popup.getMenu().findItem(R.id.three).setVisible(true);
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.one:
                        //do do
                        // Toast.makeText(baseActivityContext, "one:"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();

                        Bundle inputUri = new Bundle();
                        inputUri.putString("stipend_id", stipend_id);
                        inputUri.putString("generator", "126");
                        Context context = view.getContext();
                        Intent intent = new Intent(context, SDownUnSignCFormA.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);

                        break;

                    case R.id.two:
                        //do do
                        //Toast.makeText(baseActivityContext, "two:"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();

                        Bundle inputUri3 = new Bundle();
                        inputUri3.putString("stipend_id", stipend_id);
                        inputUri3.putString("generator", "126");
                        Context context3 = view.getContext();
                        Intent intent3 = new Intent(context3, SUpCFormA.class);
                        intent3.putExtras(inputUri3);
                        context3.startActivity(intent3);


                        break;


                    case R.id.three:

                        Context context2 = view.getContext();
                        Bundle inputUri2 = new Bundle();
                        inputUri2.putString("stipend_id", stipend_id);
                        inputUri2.putString("generator", "126");
                        Intent intent2 = new Intent(context2, SDownCFormA.class);
                        context2.startActivity(intent2);

                        break;


                    default:
                        break;


                }

                return false;
            }
        });

        popup.show();
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
        public SClaimObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblUserApproval,lblClaimDate,lblClaimMonth,lblClaimDaysWorked,lblClaimPLeave,lblClaimUPLeave,lblClaimedAmount,
                lblSApproval,lblAApproval,lblActionForm;
        public final Button btnUserApproval,btnActionForm;
        public final LinearLayout linearLActionUserApproval,linearActionForm;
        public ClaimHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.claim_Row);

            linearLActionUserApproval = (LinearLayout) itemView.findViewById(R.id.linearLActionUserApproval);
            linearActionForm = (LinearLayout) itemView.findViewById(R.id.linearActionForm);
            lblClaimDate= (TextView) itemView.findViewById(R.id.lblClaimDate);
            lblClaimMonth= (TextView) itemView.findViewById(R.id.lblClaimMonth);
            lblClaimDaysWorked= (TextView) itemView.findViewById(R.id.lblClaimDaysWorked);
            lblClaimPLeave= (TextView) itemView.findViewById(R.id.lblClaimPLeave);
            lblClaimUPLeave= (TextView) itemView.findViewById(R.id.lblClaimUPLeave);
            lblClaimedAmount= (TextView) itemView.findViewById(R.id.lblClaimedAmount);
            lblSApproval= (TextView) itemView.findViewById(R.id.lblSApproval);
            lblAApproval= (TextView) itemView.findViewById(R.id.lblAApproval);
            lblUserApproval= (TextView) itemView.findViewById(R.id.lblUserApproval);
            lblActionForm= (TextView) itemView.findViewById(R.id.lblActionForm);
            btnUserApproval= (Button) itemView.findViewById(R.id.btnUserApproval);
            btnActionForm= (Button) itemView.findViewById(R.id.btnActionForm);
         }
       }

      }
