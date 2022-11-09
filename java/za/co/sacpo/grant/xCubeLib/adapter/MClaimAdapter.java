package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.dataObj.MClaimObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.claims.MPastClaimA;
import za.co.sacpo.grant.xCubeMentor.claims.MDownloadClaimFormA;
import za.co.sacpo.grant.xCubeMentor.forms.MDownloadSignedFormA;
import za.co.sacpo.grant.xCubeMentor.forms.MUploadSignedFormA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class MClaimAdapter extends RecyclerView.Adapter<MClaimAdapter.ClaimHolder> {

    private List<MClaimObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels,clailExit;
    MPastClaimA baseActivity;
    String m_student_name,month_year19,monthField_18;
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

    public MClaimAdapter(List<MClaimObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, MPastClaimA baseActivity) {
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
            BColor = res.getColor(baseAPC.getTextcolorResourceId("row_head"));
            holder.lblClaimYear.setTextColor(res.getColor(R.color.white));
            holder.lblClaimYear.setTypeface(holder.lblClaimYear.getTypeface(), Typeface.BOLD);

            holder.lblDownCF.setTypeface(holder.lblDownCF.getTypeface(), Typeface.BOLD);
            holder.lblDownCF.setTextColor(res.getColor(R.color.white));
            holder.lblDownCF.setVisibility(View.VISIBLE);
            holder.btnDownCF.setVisibility(View.GONE);

            holder.lblDownloadSignedform.setTypeface(holder.lblDownloadSignedform.getTypeface(), Typeface.BOLD);
            holder.lblDownloadSignedform.setTextColor(res.getColor(R.color.white));
            holder.lblDownloadSignedform.setVisibility(View.VISIBLE);
            holder.btnDownSignForm.setVisibility(View.GONE);


            holder.lblClaimMonth.setTextColor(res.getColor(R.color.white));
            holder.lblClaimMonth.setTypeface(holder.lblClaimMonth.getTypeface(), Typeface.BOLD);
            holder.lblClaimDaysWorked.setTextColor(res.getColor(R.color.white));
            holder.lblClaimDaysWorked.setTypeface(holder.lblClaimDaysWorked.getTypeface(), Typeface.BOLD);
            holder.lblClaimLeave.setTextColor(res.getColor(R.color.white));
            holder.lblClaimLeave.setTypeface(holder.lblClaimLeave.getTypeface(), Typeface.BOLD);
            holder.lblOutRangeCount.setTextColor(res.getColor(R.color.white));
            holder.lblOutRangeCount.setTypeface(holder.lblOutRangeCount.getTypeface(), Typeface.BOLD);
            holder.lblClaimedAmount.setTextColor(res.getColor(R.color.white));
            holder.lblClaimedAmount.setTypeface(holder.lblClaimedAmount.getTypeface(), Typeface.BOLD);
        }
        else{
            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }


            if(aObjList.get(holder.getAdapterPosition()).clasDCFLinkStatus11.equals("0")) {
                holder.lblDownCF.setVisibility(View.VISIBLE);
                holder.btnDownCF.setVisibility(View.GONE);
            }
            else{
                Labels = this.getLabelFromDb("l_205_lbl_download", R.string.l_205_lbl_download);
                holder.btnDownCF.setText(Labels);
                holder.btnDownCF.setVisibility(View.VISIBLE);
                holder.btnDownCF.setBackground(baseActivityContext.getResources().getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
                holder.btnDownCF.setTextColor(res.getColor(baseAPC.getTextcolorResourceId("dashboard_textColor")));
                holder.lblDownCF.setVisibility(View.GONE);


            }
            if(aObjList.get(holder.getAdapterPosition()).claSDCFLinkStatus13.equals("0")) {
                holder.lblDownloadSignedform.setVisibility(View.VISIBLE);
                holder.btnDownSignForm.setVisibility(View.GONE);
            }
            else{
                Labels = this.getLabelFromDb("l_205_lbl_download", R.string.l_205_lbl_download);
                holder.btnDownSignForm.setText(Labels);
                holder.btnDownSignForm.setVisibility(View.VISIBLE);
                holder.btnDownSignForm.setBackground(res.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
                holder.btnDownSignForm.setTextColor(res.getColor(baseAPC.getTextcolorResourceId("dashboard_textColor")));
                holder.lblDownloadSignedform.setVisibility(View.GONE);

            }

        }


        holder.lblClaimYear.setText(aObjList.get(holder.getAdapterPosition()).claYear4);
        holder.lblClaimYear.setBackgroundColor(BColor);

        holder.btnDownCF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                String download_url = holder.hItem.claDCFLink10;
                URL url = null;
                try {
                    url = new URL(download_url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                intent.setData(Uri.parse(String.valueOf(url)));
                context.startActivity(intent);

            }
        });


        holder.btnDownSignForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                String download_url = holder.hItem.claSDCFLink12;
                URL url = null;
                try {
                    url = new URL(download_url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                intent.setData(Uri.parse(String.valueOf(url)));
                context.startActivity(intent);
            }
        });

        holder.lblDownCF.setText(aObjList.get(holder.getAdapterPosition()).claDCFLink10);
        holder.LLDownCF.setBackgroundColor(BColor);


        holder.LLDownLoadSignForm.setBackgroundColor(BColor);
        holder.lblDownloadSignedform.setText(aObjList.get(holder.getAdapterPosition()).claSDCFLink12);

        holder.lblClaimYear.setText(aObjList.get(holder.getAdapterPosition()).claYear4);
        holder.lblClaimYear.setBackgroundColor(BColor);
        holder.lblClaimMonth.setText(aObjList.get(holder.getAdapterPosition()).claMonth5);
        holder.lblClaimMonth.setBackgroundColor(BColor);
        holder.lblClaimDaysWorked.setText(aObjList.get(holder.getAdapterPosition()).claDaysWorked8);
        holder.lblClaimDaysWorked.setBackgroundColor(BColor);
        holder.lblClaimLeave.setText(aObjList.get(holder.getAdapterPosition()).claLeave9);
        holder.lblClaimLeave.setBackgroundColor(BColor);
        holder.lblOutRangeCount.setText(aObjList.get(holder.getAdapterPosition()).claOutRange7);
        holder.lblOutRangeCount.setBackgroundColor(BColor);
        holder.lblClaimedAmount.setText(aObjList.get(holder.getAdapterPosition()).claAmount6);
        holder.lblClaimedAmount.setBackgroundColor(BColor);

    }

    private void popup (final View view, final String monthField_18,final String downloadFileStatus15,final String uploadFileStatus16,final String claim_signed_file20) {

        PopupMenu popup = new PopupMenu(baseActivityContext, view);
        popup.getMenuInflater().inflate(R.menu.popup_menu,popup.getMenu());


        Boolean hideOne = false;
        Boolean hideTwo = false;
        Boolean hideThree = false;
        if(downloadFileStatus15.equals("1")){
            hideOne=true;
        }
        if(claim_signed_file20.equals("1")){
            hideThree=true;
        }
        popup.getMenu().findItem(R.id.one).setVisible(false);
        popup.getMenu().findItem(R.id.two).setVisible(true);
        popup.getMenu().findItem(R.id.three).setVisible(false);
        if (hideOne.equals(true)) {
            popup.getMenu().findItem(R.id.one).setVisible(true);
        }
        if (hideThree.equals(true)) {
            popup.getMenu().findItem(R.id.three).setVisible(true);
        } if (hideTwo.equals(true)) {
            popup.getMenu().findItem(R.id.two).setVisible(true);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.one:
                        //do do
                        // Toast.makeText(baseActivityContext, "one:"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();

                        Bundle inputUri = new Bundle();
                        inputUri.putString("stipend_id", monthField_18);
                        inputUri.putString("generator", "126");
                        inputUri.putString("m_student_name", baseActivity.getStudentName());
                        inputUri.putString("student_id",baseActivity.getStudentId());
                        Context context = view.getContext();
                        Intent intent = new Intent(context, MDownloadClaimFormA.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);

                        break;

                    case R.id.two:
                        //do do
                        //Toast.makeText(baseActivityContext, "two:"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();

                        Bundle inputUri3 = new Bundle();
                        inputUri3.putString("stipend_id", monthField_18);
                        inputUri3.putString("generator", "126");
                        inputUri3.putString("m_student_name", baseActivity.getStudentName());
                        inputUri3.putString("student_id",baseActivity.getStudentId());
                        Context context3 = view.getContext();
                        Intent intent3 = new Intent(context3, MUploadSignedFormA.class);
                        intent3.putExtras(inputUri3);
                        context3.startActivity(intent3);

                        break;


                    case R.id.three:


                        Bundle inputUri2 = new Bundle();
                        inputUri2.putString("generator", "126");
                        inputUri2.putString("m_student_name", baseActivity.getStudentName());
                        inputUri2.putString("stipend_id", monthField_18);
                        inputUri2.putString("student_id",baseActivity.getStudentId());
                        Context context2 = view.getContext();
                        Intent intent2= new Intent(context2, MDownloadSignedFormA.class);
                        intent2.putExtras(inputUri2);
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
        public MClaimObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblDownCF,lblClaimYear,lblClaimMonth,lblClaimDaysWorked,lblClaimLeave,lblOutRangeCount,lblClaimedAmount, lblDownloadSignedform;
        public final Button btnDownCF,btnDownSignForm;
        public final LinearLayout LLDownCF,LLDownLoadSignForm;
        public ClaimHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.claim_Row);
            LLDownCF = (LinearLayout) itemView.findViewById(R.id.LLDownCF);
            LLDownLoadSignForm = (LinearLayout) itemView.findViewById(R.id.LLDownLoadSignForm);

            lblClaimYear= (TextView) itemView.findViewById(R.id.lblClaimYear);
            lblClaimMonth= (TextView) itemView.findViewById(R.id.lblClaimMonth);
            lblClaimedAmount= (TextView) itemView.findViewById(R.id.lblClaimedAmount);
            lblOutRangeCount= (TextView) itemView.findViewById(R.id.lblOutRangeCount);
            lblClaimDaysWorked= (TextView) itemView.findViewById(R.id.lblClaimDaysWorked);
            lblClaimLeave= (TextView) itemView.findViewById(R.id.lblClaimLeave);

            lblDownCF= (TextView) itemView.findViewById(R.id.lblDownCF);
            lblDownloadSignedform= (TextView) itemView.findViewById(R.id.lblDownloadSignedform);

            btnDownCF= (Button) itemView.findViewById(R.id.btnDownCF);
            btnDownSignForm= (Button) itemView.findViewById(R.id.btnDownSignForm);
        }
    }
}
