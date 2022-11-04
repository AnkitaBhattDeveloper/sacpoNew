package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
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

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.dataObj.TimeSheetObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.time_sheet.MTimeSheetDownload;
import za.co.sacpo.grant.xCubeMentor.time_sheet.MTimeSheetUploade;

public class TimeSheetAdapter extends RecyclerView.Adapter<TimeSheetAdapter.LeavesHolder> {
    private List<TimeSheetObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;

    public TimeSheetAdapter(List<TimeSheetObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
    public TimeSheetAdapter.LeavesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_time_sheet_row, parent, false);
        return new TimeSheetAdapter.LeavesHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final TimeSheetAdapter.LeavesHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);

            holder.lblLearner.setTypeface(holder.lblLearner.getTypeface(), Typeface.BOLD);
            holder.lblLearner.setTextColor(res.getColor(R.color.white));

            holder.lblLearnerId.setTypeface(holder.lblLearnerId.getTypeface(), Typeface.BOLD);
            holder.lblLearnerId.setTextColor(res.getColor(R.color.white));
            holder.lblLeaveDate.setTypeface(holder.lblLeaveDate.getTypeface(), Typeface.BOLD);
            holder.lblLeaveDate.setTextColor(res.getColor(R.color.white));
            holder.lblLeaveType.setTypeface(holder.lblLeaveType.getTypeface(), Typeface.BOLD);
            holder.lblLeaveType.setTextColor(res.getColor(R.color.white));
            holder.lblLeaveDay.setTypeface(holder.lblLeaveDay.getTypeface(), Typeface.BOLD);
            holder.lblLeaveDay.setTextColor(res.getColor(R.color.white));
            holder.lblStatus.setTypeface(holder.lblStatus.getTypeface(), Typeface.BOLD);
            holder.lblStatus.setTextColor(res.getColor(R.color.white));


            Labels =this.getLabelFromDb("h_M401_l_btn_timesheet",R.string.h_M401_l_btn_timesheet);
            holder.lblActionDownload.setText(Labels);
            holder.lblActionDownload.setTypeface(holder.lblActionDownload.getTypeface(), Typeface.BOLD);
            holder.lblActionDownload.setTextColor(res.getColor(R.color.white));
            holder.lblActionDownload.setVisibility(View.VISIBLE);
            holder.btnActionDownload.setVisibility(View.GONE);

        }
        else{

            holder.lblActionDownload.setVisibility(View.GONE);
            holder.btnActionDownload.setVisibility(View.VISIBLE);

            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
        }



        holder.lblLeaveDate.setText(aObjList.get(holder.getAdapterPosition()).aLeDate3);
        holder.lblLeaveDate.setBackgroundColor(BColor);



        holder.lblLearner.setText(aObjList.get(holder.getAdapterPosition()).aLeLearner7);
        holder.lblLearner.setBackgroundColor(BColor);

        holder.lblLeaveType.setText(aObjList.get(holder.getAdapterPosition()).aLeType4);
        holder.lblLeaveType.setBackgroundColor(BColor);
        holder.lblLeaveDay.setText(aObjList.get(holder.getAdapterPosition()).aLeDay6);
        holder.lblLeaveDay.setBackgroundColor(BColor);
        holder.lblStatus.setText(aObjList.get(holder.getAdapterPosition()).aLeStatus5);
        holder.lblStatus.setBackgroundColor(BColor);

        holder.btnActionDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup(view,aObjList.get(holder.getAdapterPosition()).aId2);
            }
        });

        holder.lblLeaveDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLeDate3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLeaveDate,sToolTip,4);
            }
        });
        holder.lblLeaveDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLeDay6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLeaveDay,sToolTip,4);
            }
        });
        holder.lblLeaveType.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLeType4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLeaveType,sToolTip,4);
            }
        });

        holder.lblStatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLeStatus5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblStatus,sToolTip,4);
            }
        });
        holder.lblLearner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLeLearner7;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLearner,sToolTip,4);
            }
        });
        holder.lblActionDownload.setBackgroundColor(BColor);

        Labels =this.getLabelFromDb("h_M401_l_btn_timesheet",R.string.h_M401_l_btn_timesheet);
        holder.btnActionDownload.setText(Labels);

    }

    private void popup(final View view, final int form_id) {


        PopupMenu popup = new PopupMenu(baseActivityContext, view);
        popup.getMenuInflater().inflate(R.menu.time_sheet_popup,popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.download:
                        //do do
                        // Toast.makeText(baseActivityContext, "one:"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                        Bundle inputUri = new Bundle();
                        String Form_id = String.valueOf(form_id);
                        inputUri.putString("form_id", Form_id);
                        inputUri.putString("generator", "form_id");
                        Context context = view.getContext();
                        Intent intent = new Intent(context, MTimeSheetDownload.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);

                        break;

                    case R.id.upload:
                        //do do

                        //Toast.makeText(baseActivityContext, "two:"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                        Context context1 = view.getContext();
                        Intent intent1 = new Intent(context1,MTimeSheetUploade.class);
                        context1.startActivity(intent1);

                        break;


                  /*  case R.id.three:
                        //do do
                        //Toast.makeText(baseActivityContext, "three:"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                        Context context2 = view.getContext();
                        Intent intent2 = new Intent(context2, SDownloadFormA.class);
                        context2.startActivity(intent2);

                        break;*/


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

    public class LeavesHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public TimeSheetObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblActionDownload,lblLeaveDate,lblLeaveType,lblLeaveDay,lblStatus,lblLearner,lblLearnerId;
        public final LinearLayout lADownload;
        public final Button btnActionDownload;
        public LeavesHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.tRowL);
            lblLeaveDate= (TextView) itemView.findViewById(R.id.lblLeaveDate);
            lblLearner= (TextView) itemView.findViewById(R.id.lblLearner);
            lblLearnerId= (TextView) itemView.findViewById(R.id.lblLearnerId);
            lblLeaveType= (TextView) itemView.findViewById(R.id.lblLeaveType);
            lblLeaveDay= (TextView) itemView.findViewById(R.id.lblLeaveDay);
            lblStatus= (TextView) itemView.findViewById(R.id.lblStatus);

            lblActionDownload= (TextView) itemView.findViewById(R.id.lblActionDownload);
            lADownload = (LinearLayout) itemView.findViewById(R.id.lADownload);
            btnActionDownload= (Button) itemView.findViewById(R.id.btnActionDownload);
        }
    }
}
