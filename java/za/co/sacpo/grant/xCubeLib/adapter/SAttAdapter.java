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
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.TableRow;
        import android.widget.TextView;

        import java.util.List;

        import za.co.sacpo.grant.R;
        import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
        import za.co.sacpo.grant.xCubeLib.dataObj.SAttObj;
        import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeStudent.attendance.SAttDetailsA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class SAttAdapter extends RecyclerView.Adapter<SAttAdapter.AttHolder> {
    private List<SAttObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    //Todo: Table Width Problem
    public SAttAdapter(List<SAttObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
                .inflate(R.layout.att_row, parent, false);
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

            Labels =this.getLabelFromDb("l_S111_SAtt_details",R.string.l_S111_SAtt_details);
            holder.lblAction.setText(Labels);
            holder.lblAction.setTextColor(res.getColor(R.color.white));
            holder.lblAction.setVisibility(View.VISIBLE);
            holder.btnAction.setVisibility(View.GONE);



            holder.lblAction.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);
            holder.lblAction2.setTextColor(res.getColor(R.color.white));
            holder.lblAction2.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);
            holder.lblDay.setTextColor(res.getColor(R.color.white));
            holder.lblDay.setTypeface(holder.lblDay.getTypeface(), Typeface.BOLD);
            holder.lblLoginTime.setTextColor(res.getColor(R.color.white));
            holder.lblLoginTime.setTypeface(holder.lblLoginTime.getTypeface(), Typeface.BOLD);
            holder.lblLogoutTime.setTextColor(res.getColor(R.color.white));
            holder.lblLogoutTime.setTypeface(holder.lblLogoutTime.getTypeface(), Typeface.BOLD);
            holder.lblSpentTime.setTextColor(res.getColor(R.color.white));
            holder.lblSpentTime.setTypeface(holder.lblSpentTime.getTypeface(), Typeface.BOLD);
            holder.lblStatus.setTextColor(res.getColor(R.color.white));
            holder.lblStatus.setTypeface(holder.lblStatus.getTypeface(), Typeface.BOLD);
        }
        else{

            if((holder.getAdapterPosition()%2)==0){
                //BColor=res.getColor(R.color.row_even);
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }

            if(aObjList.get(holder.getAdapterPosition()).aStatus8.equals("Present")){
                holder.btnAction.setVisibility(View.VISIBLE);

            }
            else{
                holder.btnAction.setVisibility(View.GONE);
                Labels = this.getLabelFromDb("l_205_lbl_dash", R.string.l_205_lbl_dash);
                holder.lblAction.setText(Labels);

                holder.lblAction.setVisibility(View.VISIBLE);
            }
            holder.lblAction.setVisibility(View.GONE);
            //holder.btnAction.setVisibility(View.VISIBLE);

        }
        holder.lblLoginDate.setText(aObjList.get(holder.getAdapterPosition()).aLiDate3);
        holder.lblLoginDate.setBackgroundColor(BColor);

        holder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                String attendance_id = String.valueOf(holder.hItem.aId2);
                String date_input = holder.hItem.aLoDate5;
                inputUri.putString("attendance_id", attendance_id);
                inputUri.putString("date_input", date_input);
                inputUri.putString("generator", "S111");
                Context context = v.getContext();
                Intent intent = new Intent(context, SAttDetailsA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });



        holder.linearLAction.setBackgroundColor(BColor);

        Labels =this.getLabelFromDb("l_S111_Btn_SAtt_details",R.string.l_S111_Btn_SAtt_details);
        holder.btnAction.setText(Labels);


        holder.lblDay.setText(aObjList.get(holder.getAdapterPosition()).aDay9);
        holder.lblDay.setBackgroundColor(BColor);
        holder.lblLoginTime.setText(aObjList.get(holder.getAdapterPosition()).aLiTime4);
        holder.lblLoginTime.setBackgroundColor(BColor);
        holder.lblLogoutTime.setText(aObjList.get(holder.getAdapterPosition()).aLoTime6);
        holder.lblLogoutTime.setBackgroundColor(BColor);
        holder.lblSpentTime.setText(aObjList.get(holder.getAdapterPosition()).aTimeSpent7);
        holder.lblSpentTime.setBackgroundColor(BColor);
        holder.lblStatus.setText(aObjList.get(holder.getAdapterPosition()).aStatus8);
        holder.lblStatus.setBackgroundColor(BColor);
        holder.lblAction2.setText(aObjList.get(holder.getAdapterPosition()).aViewComments11);
        holder.lblAction2.setBackgroundColor(BColor);

        holder.lblLoginDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLiDate3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLoginDate,sToolTip,4);
            }
        });
        holder.lblDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aDay9;
                ((BaseAPC)activityInCall).showTooltip(holder.lblDay,sToolTip,4);
            }
        });
        holder.lblLogoutTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLoTime6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLogoutTime,sToolTip,4);
            }
        });
        holder.lblSpentTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aTimeSpent7;
                ((BaseAPC)activityInCall).showTooltip(holder.lblSpentTime,sToolTip,4);
            }
        });
        holder.lblLoginTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLiTime4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLoginTime,sToolTip,4);
            }
        });
        holder.lblStatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aStatus8;
                ((BaseAPC)activityInCall).showTooltip(holder.lblStatus,sToolTip,4);
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
        public SAttObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblLoginDate,lblAction,lblDay,lblLoginTime,lblLogoutTime,lblSpentTime,lblStatus,lblAction2;
        public final Button btnAction;
        public final LinearLayout linearLAction,linearLAction2;
        public AttHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.tRow);
            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);
            linearLAction2 = (LinearLayout) itemView.findViewById(R.id.linearLAction2);
            lblLoginDate= (TextView) itemView.findViewById(R.id.lblLoginDate);
            btnAction= (Button) itemView.findViewById(R.id.btnAction);
            lblAction= (TextView) itemView.findViewById(R.id.lblAction);
            lblAction2= (TextView) itemView.findViewById(R.id.lblAction2);
            lblDay= (TextView) itemView.findViewById(R.id.lblDay);
            lblLoginTime= (TextView) itemView.findViewById(R.id.lblLoginTime);
            lblLogoutTime= (TextView) itemView.findViewById(R.id.lblLogoutTime);
            lblSpentTime= (TextView) itemView.findViewById(R.id.lblSpentTime);
            lblStatus= (TextView) itemView.findViewById(R.id.lblStatus);
        }
    }
}
