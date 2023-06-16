package za.co.sacpo.grantportal.xCubeLib.adapter;

import android.content.Context;
        import android.content.Intent;
        import android.content.res.Resources;
        import android.database.Cursor;
        import android.graphics.Typeface;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.RecyclerView;

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

import za.co.sacpo.grantportal.R;
        import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grantportal.xCubeLib.dataObj.DCenterObj;
        import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
import za.co.sacpo.grantportal.xCubeStudent.upload.DownloadCenterA;
import za.co.sacpo.grantportal.xCubeStudent.upload.SUploadDocumentA;
import za.co.sacpo.grantportal.xCubeStudent.upload.UploadMultipleDocsA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class DCenterAdapter extends RecyclerView.Adapter<DCenterAdapter.AttHolder> {
    private List<DCenterObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
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

    public DCenterAdapter(List<DCenterObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
    public AttHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.d_center_row, parent, false);
        return new AttHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AttHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0) {
            BColor = res.getColor(baseAPC.getTextcolorResourceId("row_head"));
            holder.lblAction1.setTextColor(res.getColor(R.color.white));
            holder.lblAction1.setVisibility(View.VISIBLE);
            holder.btnAction1.setVisibility(View.GONE);
            holder.lblAction1.setTypeface(holder.lblAction1.getTypeface(), Typeface.BOLD);
            holder.lblAction2.setTextColor(res.getColor(R.color.white));
            holder.lblAction2.setVisibility(View.VISIBLE);
            holder.btnAction2.setVisibility(View.GONE);
            holder.lblAction2.setTypeface(holder.lblAction2.getTypeface(), Typeface.BOLD);

            holder.lblAction3.setTextColor(res.getColor(R.color.white));
            holder.lblAction3.setVisibility(View.VISIBLE);
            holder.btnAction3.setVisibility(View.GONE);
            holder.lblAction3.setTypeface(holder.lblAction3.getTypeface(), Typeface.BOLD);

            holder.lblName.setTextColor(res.getColor(R.color.white));
            holder.lblName.setTypeface(holder.lblName.getTypeface(), Typeface.BOLD);
        }else if(holder.getAdapterPosition()==1){
            BColor=res.getColor(R.color.row_odd);
         /*   holder.lblAction1.setTextColor(res.getColor(R.color.white));
            holder.lblAction1.setVisibility(View.VISIBLE);
            holder.btnAction1.setVisibility(View.GONE);
            holder.lblAction1.setTypeface(holder.lblAction1.getTypeface(), Typeface.BOLD);*/
            holder.lblAction2.setTextColor(res.getColor(R.color.white));
            holder.lblAction2.setVisibility(View.GONE);
            holder.lblAction2.setTypeface(holder.lblAction2.getTypeface(), Typeface.BOLD);
            holder.btnAction2.setVisibility(View.VISIBLE);
            holder.btnAction2.setText("UPLOAD");
            holder.btnAction2.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
            holder.btnAction2.setTextColor(baseActivityContext.getResources().getColor(baseAPC.getTextcolorResourceId("btn_textColor")));


            holder.btnAction2.setOnClickListener(view -> {
                Bundle inputUri = new Bundle();
             //   inputUri.putString("document_id", String.valueOf(holder.hItem.aId2));
              //  inputUri.putString("document_name", String.valueOf(holder.hItem.aName3));
             //   inputUri.putString("documentType", String.valueOf(holder.hItem.aUploadType8));
                inputUri.putString("generator", "S193");
                Context context = view.getContext();
                Intent intent = new Intent(context, UploadMultipleDocsA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            });


/*            holder.lblAction3.setTextColor(res.getColor(R.color.white));
            holder.lblAction3.setVisibility(View.VISIBLE);
            holder.btnAction3.setVisibility(View.GONE);
            holder.lblAction3.setTypeface(holder.lblAction3.getTypeface(), Typeface.BOLD);*/

        }
        else{

            if((holder.getAdapterPosition()%2)==0){
                //BColor=res.getColor(R.color.row_even);
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
        }

        holder.linearLAction1.setBackgroundColor(BColor);
        holder.linearLAction2.setBackgroundColor(BColor);
        holder.linearLAction3.setBackgroundColor(BColor);
        holder.lblName.setText(aObjList.get(holder.getAdapterPosition()).aName3);
        holder.lblName.setBackgroundColor(BColor);

        holder.lblAction1.setText(aObjList.get(holder.getAdapterPosition()).aPrevious5);
        holder.lblAction1.setBackgroundColor(BColor);

        holder.lblAction2.setText(aObjList.get(holder.getAdapterPosition()).aIsUpload7);
        holder.lblAction2.setBackgroundColor(BColor);

        holder.lblAction3.setText(aObjList.get(holder.getAdapterPosition()).aDownload10);
        holder.lblAction3.setBackgroundColor(BColor);
        if(holder.getAdapterPosition()>1) {
            Labels = this.getLabelFromDb("l_S104_view_comment", R.string.l_S104_view_comment);
            int aPreviousStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aIsPrevious6);
            if (aPreviousStatus == 1) {
                holder.lblAction1.setVisibility(View.GONE);
                holder.btnAction1.setVisibility(View.VISIBLE);
                holder.btnAction1.setText(aObjList.get(holder.getAdapterPosition()).aPrevious5);
                holder.btnAction1.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
                holder.btnAction1.setTextColor(baseActivityContext.getResources().getColor(baseAPC.getTextcolorResourceId("btn_textColor")));


            } else {
                holder.lblAction1.setVisibility(View.VISIBLE);
                holder.lblAction1.setText("-");
                holder.btnAction1.setVisibility(View.GONE);
            }
            int aUploadStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aIsUpload7);
            if (aUploadStatus == 1) {
                holder.lblAction2.setVisibility(View.GONE);
                holder.btnAction2.setVisibility(View.VISIBLE);
                holder.btnAction2.setText("UPLOAD");
                holder.btnAction2.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
                holder.btnAction2.setTextColor(baseActivityContext.getResources().getColor(baseAPC.getTextcolorResourceId("btn_textColor")));

            } else {
                holder.lblAction2.setVisibility(View.GONE);
                holder.btnAction2.setText("REUPLOAD");
                holder.btnAction2.setVisibility(View.VISIBLE);
                holder.btnAction2.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
                holder.btnAction2.setTextColor(baseActivityContext.getResources().getColor(baseAPC.getTextcolorResourceId("btn_textColor")));

            }
            int aDownloadStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aIsDownload9);
            if (aDownloadStatus == 1) {
                holder.lblAction3.setVisibility(View.GONE);
                holder.btnAction3.setVisibility(View.VISIBLE);
                holder.btnAction3.setText("DOWNLOAD");
                holder.btnAction3.setBackground(baseActivityContext.getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
                holder.btnAction3.setTextColor(baseActivityContext.getResources().getColor(baseAPC.getTextcolorResourceId("btn_textColor")));


            } else {
                holder.lblAction3.setVisibility(View.VISIBLE);
                holder.lblAction3.setText("-");
                holder.btnAction3.setVisibility(View.GONE);
            }


            /*holder.btnAction3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle inputUri = new Bundle();
                    String attendanceId = String.valueOf(holder.hItem.aId2);
                    int aCommentStatus = Integer.parseInt(holder.hItem.aCommentLink10);
                    String attendanceDate = holder.hItem.aDate3;
                    inputUri.putString("attendanceId", attendanceId);
                    inputUri.putString("generator", "S104");
                    Context context = v.getContext();
                    if (aCommentStatus == 1) {
                        Intent intent = new Intent(context, SAttVCommentA.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);
                    }
                    else {
                        inputUri.putString("attendanceDate", attendanceDate);
                        Intent intent = new Intent(context, SAttPostCommentA.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);
                    }

                }
            });*/
            if (holder.hItem.aUploadType8.equals("9") || holder.hItem.aUploadType8.equals("10") || holder.hItem.aUploadType8.equals("11") || holder.hItem.aUploadType8.equals("12")) {
                holder.btnAction3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle inputUri = new Bundle();
                        String doc_type = String.valueOf(holder.hItem.aUploadType8);
                        String doc_name = String.valueOf(holder.hItem.aName3);
                        inputUri.putString("doc_type", doc_type);
                        inputUri.putString("doc_name", doc_name);
                        inputUri.putString("generator", "S193");
                        Context context = view.getContext();
                        Intent intent = new Intent(context, DownloadCenterA.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);
                    }
                });
            } else {
                holder.btnAction3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        String download_url = holder.hItem.aDownload10;
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
            }

            holder.btnAction2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle inputUri = new Bundle();
                    inputUri.putString("document_id", String.valueOf(holder.hItem.aId2));
                    inputUri.putString("document_name", String.valueOf(holder.hItem.aName3));
                    inputUri.putString("documentType", String.valueOf(holder.hItem.aUploadType8));
                    inputUri.putString("generator", "S193");
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SUploadDocumentA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
        }
        holder.lblName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aName3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblName,sToolTip,4);
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

    public class AttHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public DCenterObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblAction1,lblAction2,lblAction3,lblName;
        public final Button btnAction1,btnAction2,btnAction3;
        public final LinearLayout linearLAction1,linearLAction2,linearLAction3;
        public AttHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.tRow);
            linearLAction1 = (LinearLayout) itemView.findViewById(R.id.linearLAction1);
            btnAction1= (Button) itemView.findViewById(R.id.btnAction1);
            lblAction1= (TextView) itemView.findViewById(R.id.lblAction1);
            linearLAction2 = (LinearLayout) itemView.findViewById(R.id.linearLAction2);
            btnAction2= (Button) itemView.findViewById(R.id.btnAction2);
            lblAction2= (TextView) itemView.findViewById(R.id.lblAction2);
            linearLAction3 = (LinearLayout) itemView.findViewById(R.id.linearLAction3);
            btnAction3= (Button) itemView.findViewById(R.id.btnAction3);
            lblAction3= (TextView) itemView.findViewById(R.id.lblAction3);
            lblName= (TextView) itemView.findViewById(R.id.lblName);
        }
    }
}
