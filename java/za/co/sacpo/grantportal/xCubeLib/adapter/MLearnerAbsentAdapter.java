package za.co.sacpo.grantportal.xCubeLib.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MLearnerAbsentObj;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;

public class MLearnerAbsentAdapter extends RecyclerView.Adapter<MLearnerAbsentAdapter.ViewHolder> {
    private List<MLearnerAbsentObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    String temp;
    public MLearnerAbsentAdapter(List<MLearnerAbsentObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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

                while (res.moveToNext())

                {
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
    public MLearnerAbsentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seta_leve__att_layout, parent, false);
        return new MLearnerAbsentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MLearnerAbsentAdapter.ViewHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;


        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);
            holder.lblDate.setTextColor(res.getColor(R.color.white));
            holder.lblDate.setTypeface(holder.lblDate.getTypeface(), Typeface.BOLD);
            holder.lblDay.setTextColor(res.getColor(R.color.white));
            holder.lblDay.setTypeface(holder.lblDay.getTypeface(), Typeface.BOLD);
            holder.lblLoginTime.setTextColor(res.getColor(R.color.white));
            holder.lblLoginTime.setTypeface(holder.lblLoginTime.getTypeface(), Typeface.BOLD);
            holder.lblLogoutTime.setTextColor(res.getColor(R.color.white));
            holder.lblLogoutTime.setTypeface(holder.lblLogoutTime.getTypeface(), Typeface.BOLD);
            holder.lblHoursWorked.setTextColor(res.getColor(R.color.white));
            holder.lblHoursWorked.setTypeface(holder.lblHoursWorked.getTypeface(), Typeface.BOLD);
            holder.lblLearnerComment.setTextColor(res.getColor(R.color.white));
            holder.lblLearnerComment.setTypeface(holder.lblLearnerComment.getTypeface(), Typeface.BOLD);
            holder.lblAttStatus.setTextColor(res.getColor(R.color.white));
            holder.lblAttStatus.setTypeface(holder.lblAttStatus.getTypeface(), Typeface.BOLD);

        }

        else {

            if ((position % 2) == 0) {
                BColor = res.getColor(R.color.row_even);
            } else {
                BColor = res.getColor(R.color.row_odd);
            }
        }


        holder.lblDate.setText(aObjList.get(holder.getAdapterPosition()).FMaDate3);
        holder.lblDate.setBackgroundColor(BColor);
        holder.lblDay.setText(aObjList.get(holder.getAdapterPosition()).FMaDay4);
        holder.lblDay.setBackgroundColor(BColor);
        holder.lblLoginTime.setText(aObjList.get(holder.getAdapterPosition()).FMaLoginTime5);
        holder.lblLoginTime.setBackgroundColor(BColor);
        holder.lblLogoutTime.setText(aObjList.get(holder.getAdapterPosition()).FMaLogoutTime6);
        holder.lblLogoutTime.setBackgroundColor(BColor);
        holder.lblHoursWorked.setText(aObjList.get(holder.getAdapterPosition()).FMaHoursWorked7);
        holder.lblHoursWorked.setBackgroundColor(BColor);
        holder.lblLearnerComment.setText(aObjList.get(holder.getAdapterPosition()).FMaLearnerComment8);
        holder.lblLearnerComment.setBackgroundColor(BColor);
        holder.lblAttStatus.setText(aObjList.get(holder.getAdapterPosition()).FMaAttStatus9);
        holder.lblAttStatus.setBackgroundColor(BColor);



        holder.lblDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).FMaDate3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblDate,sToolTip, 4);
            }
        });


        holder.lblDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).FMaDay4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblDay,sToolTip, 4);
            }
        });

        holder.lblLoginTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).FMaLoginTime5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLoginTime,sToolTip, 4);
            }
        });

        holder.lblLogoutTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).FMaLogoutTime6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLogoutTime,sToolTip, 4);
            }
        });


        holder.lblHoursWorked.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).FMaHoursWorked7;
                ((BaseAPC)activityInCall).showTooltip(holder.lblHoursWorked,sToolTip,4);
            }
        });
        holder.lblLearnerComment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).FMaLearnerComment8;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLearnerComment,sToolTip,4);
            }
        });

        holder.lblAttStatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).FMaAttStatus9;
                ((BaseAPC)activityInCall).showTooltip(holder.lblAttStatus,sToolTip,4);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MLearnerAbsentObj.Item hItem;
        public final TableRow tRow;
        public final TextView  lblDate,lblDay,lblLoginTime,lblLogoutTime ,lblHoursWorked, lblLearnerComment, lblAttStatus;


        public ViewHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.seta_leave_att_Row);

            lblDate= (TextView) itemView.findViewById(R.id.lblDate);
            lblDay= (TextView) itemView.findViewById(R.id.lblDay);
            lblLoginTime= (TextView) itemView.findViewById(R.id.lblLoginTime);
            lblLogoutTime= (TextView) itemView.findViewById(R.id.lblLogoutTime);
            lblHoursWorked= (TextView) itemView.findViewById(R.id.lblHoursWorked);
            lblLearnerComment= (TextView) itemView.findViewById(R.id.lblLearnerComment);
            lblAttStatus= (TextView) itemView.findViewById(R.id.lblAttStatus);



        }
    }
}
