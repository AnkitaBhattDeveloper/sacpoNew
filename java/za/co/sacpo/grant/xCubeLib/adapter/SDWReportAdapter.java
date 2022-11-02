package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dataObj.SDReportObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeStudent.feedback.SFeedbackDA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class SDWReportAdapter extends RecyclerView.Adapter<SDWReportAdapter.Holder> {
    private List<SDReportObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;

    public SDWReportAdapter(List<SDReportObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_weekly_report_summary, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;

        holder.imageButton_Reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Bundle inputUri = new Bundle();
            String id = String.valueOf(holder.hItem.aId2);
            inputUri.putString("id", id);
            String date_input = String.valueOf(holder.hItem.aCount5);
            inputUri.putString("date_input", date_input);
            inputUri.putString("generator", "103");
            Context context = v.getContext();
            Intent intent = new Intent(context,SFeedbackDA.class);
            intent.putExtras(inputUri);
            context.startActivity(intent);
            }
        });
        holder.txtMonth.setText(aObjList.get(holder.getAdapterPosition()).aMonth3);
        holder.txtYear.setText(aObjList.get(holder.getAdapterPosition()).aYear4);
        holder.txtCount.setText(aObjList.get(holder.getAdapterPosition()).aCount5);

    }

    @Override
    public int getItemCount() {
        if(aObjList!=null) {
            return aObjList.size();
        }
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public final View hView;
        public SDReportObj.Item hItem;
        public final TextView txtCount,txtYear,txtMonth;
        public final LinearLayout tRow;
        public final ImageButton imageButton_Reports;
        public Holder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow = (LinearLayout) itemView.findViewById(R.id.Rv_Dash_stat_WeeklyReports);
            txtYear= (TextView) itemView.findViewById(R.id.txtYearR);
            txtMonth= (TextView) itemView.findViewById(R.id.txtMonthR);
            txtCount= (TextView) itemView.findViewById(R.id.txtCountR);

            imageButton_Reports= (ImageButton) itemView.findViewById(R.id.imageButton_ReportsR);
        }
    }
}
