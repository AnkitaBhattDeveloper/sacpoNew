package za.co.sacpo.grant.xCubeLib.adapter;

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

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.dataObj.SAttOutOfRangeObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;

public class SAttOutOffRangeAdapter extends RecyclerView.Adapter<SAttOutOffRangeAdapter.AttHolder> {
    private List<SAttOutOfRangeObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    //Todo: Table Width Problem
    public SAttOutOffRangeAdapter(List<SAttOutOfRangeObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.s_att_out_of_range_row, parent, false);
        return new AttHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AttHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head);
            holder.lblLoginDate.setTextColor(res.getColor(R.color.white));
            holder.lblLoginDate.setTypeface(holder.lblLoginDate.getTypeface(), Typeface.BOLD);


            holder.lblDay.setTextColor(res.getColor(R.color.white));
            holder.lblDay.setTypeface(holder.lblDay.getTypeface(), Typeface.BOLD);
            holder.lblLoginTime.setTextColor(res.getColor(R.color.white));
            holder.lblLoginTime.setTypeface(holder.lblLoginTime.getTypeface(), Typeface.BOLD);

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
        holder.lblLoginDate.setText(aObjList.get(holder.getAdapterPosition()).aDate3);
        holder.lblLoginDate.setBackgroundColor(BColor);


        holder.lblDay.setText(aObjList.get(holder.getAdapterPosition()).aDay4);
        holder.lblDay.setBackgroundColor(BColor);

        holder.lblLoginTime.setText(aObjList.get(holder.getAdapterPosition()).aDisCord5);
        holder.lblLoginTime.setBackgroundColor(BColor);


        holder.lblLoginDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aDate3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLoginDate,sToolTip,4);
            }
        });
        holder.lblDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aDay4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblDay,sToolTip,4);
            }
        });

        holder.lblLoginTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aDisCord5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLoginTime,sToolTip,4);
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
        public SAttOutOfRangeObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblLoginDate,lblDay,lblLoginTime;

        public AttHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.tRow);

            lblLoginDate= (TextView) itemView.findViewById(R.id.lblLoginDate);


            lblDay= (TextView) itemView.findViewById(R.id.lblDay);
            lblLoginTime= (TextView) itemView.findViewById(R.id.lblLoginTime);

        }
    }
}
