package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.dataObj.MDCenterObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.upload.MDocumentCenterA;
import za.co.sacpo.grant.xCubeMentor.upload.MUploadClaimFormA;

public class MDCenterAdapter extends RecyclerView.Adapter<MDCenterAdapter.AttHolder> {
    private List<MDCenterObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    MDocumentCenterA baseActivity;
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
    //Todo: Table Width Problem
    public MDCenterAdapter(List<MDCenterObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall,MDocumentCenterA baseActivity) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.baseActivity=baseActivity;
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
    public AttHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_d_center_row, parent, false);
        return new AttHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MDCenterAdapter.AttHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(baseAPC.getTextcolorResourceId("row_head"));

            holder.lblYear.setBackgroundColor(BColor);
            holder.lblMonth.setBackgroundColor(BColor);
            holder.lblUploadClaimForm.setBackgroundColor(BColor);
            holder.lblDownloadSignedClaimForm.setBackgroundColor(BColor);
            holder.lblDownloadAttendanceRegister.setBackgroundColor(BColor);
            holder.lblDownloadClaimForm.setBackgroundColor(BColor);

            holder.lblDownloadAttendanceRegister.setVisibility(View.VISIBLE);
            holder.btnDownloadAttendanceRegister.setVisibility(View.GONE);
            holder.lblDownloadAttendanceRegister.setTypeface(holder.lblDownloadAttendanceRegister.getTypeface(), Typeface.BOLD);
            holder.lblDownloadAttendanceRegister.setTextColor(res.getColor(R.color.white));

            holder.lblDownloadClaimForm.setTextColor(res.getColor(R.color.white));
            holder.lblDownloadClaimForm.setVisibility(View.VISIBLE);
            holder.btnDownloadClaimForm.setVisibility(View.GONE);
            holder.lblDownloadClaimForm.setTypeface(holder.lblDownloadClaimForm.getTypeface(), Typeface.BOLD);

            holder.lblDownloadSignedClaimForm.setVisibility(View.VISIBLE);
            holder.btnDownloadSignedClaimForm.setVisibility(View.GONE);
            holder.lblDownloadSignedClaimForm.setTypeface(holder.lblDownloadSignedClaimForm.getTypeface(), Typeface.BOLD);
            holder.lblDownloadSignedClaimForm.setTextColor(res.getColor(R.color.white));

            holder.lblUploadClaimForm.setTextColor(res.getColor(R.color.white));
            holder.lblUploadClaimForm.setVisibility(View.VISIBLE);
            holder.btnUploadClaimForm.setVisibility(View.GONE);
            holder.lblUploadClaimForm.setTypeface(holder.lblUploadClaimForm.getTypeface(), Typeface.BOLD);

            holder.lblYear.setTextColor(res.getColor(R.color.white));
            holder.lblYear.setTypeface(holder.lblYear.getTypeface(), Typeface.BOLD);

            holder.lblMonth.setTextColor(res.getColor(R.color.white));
            holder.lblMonth.setTypeface(holder.lblMonth.getTypeface(), Typeface.BOLD);

            holder.lblYear.setText(aObjList.get(holder.getAdapterPosition()).aYear3);
            holder.lblMonth.setText(aObjList.get(holder.getAdapterPosition()).aMonth4);
            holder.lblUploadClaimForm.setText(aObjList.get(holder.getAdapterPosition()).aUploadSignedClaimForm9);
            holder.lblDownloadSignedClaimForm.setText(aObjList.get(holder.getAdapterPosition()).aDownloadSignedClaimForm10);
            holder.lblDownloadAttendanceRegister.setText(aObjList.get(holder.getAdapterPosition()).aDownloadAttRegister5);
            holder.lblDownloadClaimForm.setText(aObjList.get(holder.getAdapterPosition()).aDownloadUnsignedClaimForm7);
        }
        else{

            if((holder.getAdapterPosition()%2)==0){
                //BColor=res.getColor(R.color.row_even);
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
            holder.LLDownloadAttendanceRegister.setBackgroundColor(BColor);
            holder.LLDownloadClaimForm.setBackgroundColor(BColor);
            holder.LLDownloadSignedClaimForm.setBackgroundColor(BColor);
            holder.LLUploadClaimForm.setBackgroundColor(BColor);

            holder.lblYear.setText(aObjList.get(holder.getAdapterPosition()).aYear3);
            holder.lblYear.setBackgroundColor(BColor);

            holder.lblMonth.setText(aObjList.get(holder.getAdapterPosition()).aMonth4);
            holder.lblMonth.setBackgroundColor(BColor);

            holder.lblDownloadClaimForm.setText(aObjList.get(holder.getAdapterPosition()).aDownloadAttRegister5);
            holder.lblDownloadClaimForm.setBackgroundColor(BColor);

            holder.lblDownloadSignedClaimForm.setText(aObjList.get(holder.getAdapterPosition()).aDownloadSignedClaimForm10);
            holder.lblDownloadSignedClaimForm.setBackgroundColor(BColor);


            holder.lblUploadClaimForm.setText(aObjList.get(holder.getAdapterPosition()).aUploadSignedClaimForm9);
            holder.lblUploadClaimForm.setBackgroundColor(BColor);

            holder.lblDownloadAttendanceRegister.setText(aObjList.get(holder.getAdapterPosition()).aDownloadAttRegister5);
            holder.lblDownloadAttendanceRegister.setBackgroundColor(BColor);


            if(holder.getAdapterPosition()>0) {
                int aDownloadStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aIsDownloadAttRegister6);
                if (aDownloadStatus == 1) {
                    holder.lblDownloadAttendanceRegister.setVisibility(View.GONE);
                    holder.btnDownloadAttendanceRegister.setVisibility(View.VISIBLE);
                    holder.btnDownloadAttendanceRegister.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
                    holder.btnDownloadAttendanceRegister.setText("DOWNLOAD");

                } else {
                    holder.lblDownloadAttendanceRegister.setVisibility(View.VISIBLE);
                    holder.btnDownloadAttendanceRegister.setVisibility(View.GONE);
                    holder.lblDownloadAttendanceRegister.setText("-");
                }

                int aDownloadStatusS = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aIsDownloadSignedClaimForm11);
                if (aDownloadStatusS == 1) {
                    holder.lblDownloadSignedClaimForm.setVisibility(View.GONE);
                    holder.btnDownloadSignedClaimForm.setVisibility(View.VISIBLE);
                    holder.btnDownloadSignedClaimForm.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
                    holder.btnDownloadSignedClaimForm.setText("DOWNLOAD");

                } else {
                    holder.lblDownloadSignedClaimForm.setVisibility(View.VISIBLE);
                    holder.btnDownloadSignedClaimForm.setVisibility(View.GONE);
                    holder.lblDownloadSignedClaimForm.setText("-");
                }

                int aDownloadStatusu = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aIsDownloadUnsignedClaimForm8);
                if (aDownloadStatusu == 1) {
                    holder.lblDownloadClaimForm.setVisibility(View.GONE);
                    holder.btnDownloadClaimForm.setVisibility(View.VISIBLE);
                    holder.btnDownloadClaimForm.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
                    holder.btnDownloadClaimForm.setText("DOWNLOAD");

                } else {
                    holder.lblDownloadClaimForm.setVisibility(View.VISIBLE);
                    holder.btnDownloadClaimForm.setVisibility(View.GONE);
                    holder.lblDownloadClaimForm.setText("-");
                }

                int aUploadStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aUploadSignedClaimForm9);
                if (aUploadStatus == 1) {
                    holder.lblUploadClaimForm.setVisibility(View.GONE);
                    holder.btnUploadClaimForm.setVisibility(View.VISIBLE);
                    holder.btnUploadClaimForm.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
                    holder.btnUploadClaimForm.setText("UPLOAD");

                } else {
                    holder.lblUploadClaimForm.setVisibility(View.VISIBLE);
                    holder.btnUploadClaimForm.setVisibility(View.GONE);
                    holder.lblUploadClaimForm.setText("-");
                }

            }
            holder.btnUploadClaimForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle inputUri = new Bundle();

                    String monthN = String.valueOf(holder.hItem.aMonthNumber12);
                    String year = String.valueOf(holder.hItem.aYear3);
                    String month = String.valueOf(holder.hItem.aMonth4);
                    String MonthYearDisplay = month+"-"+year;
                    String MonthYearData = monthN+"-"+year;

                    inputUri.putString("month_year_display", MonthYearDisplay);
                    inputUri.putString("month_year_data",String.valueOf(holder.hItem.aId2));
                    inputUri.putString("m_student_name", baseActivity.getStudentName());
                    inputUri.putString("student_id", baseActivity.getStudentId());
                    inputUri.putString("generator", "M199");
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MUploadClaimFormA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);

                }
            });

            holder.btnDownloadSignedClaimForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    String download_url = holder.hItem.aDownloadSignedClaimForm10;
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

            holder.btnDownloadClaimForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    String download_url = holder.hItem.aIsDownloadUnsignedClaimForm8;
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

            holder.btnDownloadAttendanceRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    String download_url = holder.hItem.aDownloadAttRegister5;
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

        }



    }

    @Override
    public int getItemCount() {
        if(aObjList!=null) {
            return aObjList.size();
        }
        return 0;
    }

    public class AttHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MDCenterObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblDownloadAttendanceRegister,lblDownloadClaimForm,lblYear,lblMonth , lblUploadClaimForm , lblDownloadSignedClaimForm;
        public final Button btnDownloadAttendanceRegister,btnDownloadClaimForm,btnUploadClaimForm,btnDownloadSignedClaimForm;
        public final LinearLayout LLDownloadAttendanceRegister,LLDownloadClaimForm,LLUploadClaimForm,LLDownloadSignedClaimForm ;
        public AttHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.tRow);
            LLDownloadAttendanceRegister = (LinearLayout) itemView.findViewById(R.id.LLDownloadAttendanceRegister);
            LLDownloadClaimForm = (LinearLayout) itemView.findViewById(R.id.LLDownloadClaimForm);
            LLUploadClaimForm = (LinearLayout) itemView.findViewById(R.id.LLUploadClaimForm);
            LLDownloadSignedClaimForm = (LinearLayout) itemView.findViewById(R.id.LLDownloadSignedClaimForm);
            

            lblDownloadAttendanceRegister= (TextView) itemView.findViewById(R.id.lblDownloadAttendanceRegister);
            lblDownloadClaimForm= (TextView) itemView.findViewById(R.id.lblDownloadClaimForm);
            lblDownloadSignedClaimForm= (TextView) itemView.findViewById(R.id.lblDownloadSignedClaimForm);
            lblUploadClaimForm= (TextView) itemView.findViewById(R.id.lblUploadClaimForm);


            btnDownloadAttendanceRegister= (Button) itemView.findViewById(R.id.btnDownloadAttendanceRegister);
            btnDownloadClaimForm= (Button) itemView.findViewById(R.id.btnDownloadClaimForm);
            btnUploadClaimForm= (Button) itemView.findViewById(R.id.btnUploadClaimForm);
            btnDownloadSignedClaimForm= (Button) itemView.findViewById(R.id.btnDownloadSignedClaimForm);

            
            lblYear= (TextView) itemView.findViewById(R.id.lblYear);
            lblMonth= (TextView) itemView.findViewById(R.id.lblMonth);
            
        }
    }
}
