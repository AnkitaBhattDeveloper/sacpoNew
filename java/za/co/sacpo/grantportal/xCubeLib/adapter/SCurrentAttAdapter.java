package za.co.sacpo.grantportal.xCubeLib.adapter;

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

        import za.co.sacpo.grantportal.R;
        import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPC;
        import za.co.sacpo.grantportal.xCubeLib.dataObj.SCurrentAttObj;
        import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
        import za.co.sacpo.grantportal.xCubeStudent.attendance.SAttPostCommentA;
import za.co.sacpo.grantportal.xCubeStudent.attendance.SAttVCommentA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class SCurrentAttAdapter extends RecyclerView.Adapter<SCurrentAttAdapter.AttHolder> {
    private List<SCurrentAttObj.Item> aObjList;
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

    //Todo: Table Width Problem
    public SCurrentAttAdapter(List<SCurrentAttObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
                .inflate(R.layout.current_att_row, parent, false);
        return new AttHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AttHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
          //  BColor = res.getColor(R.color.row_head_1);
            BColor = res.getColor(baseAPC.getTextcolorResourceId("row_head"));
            holder.lblLoginDate.setTextColor(res.getColor(R.color.white));
            holder.lblLoginDate.setTypeface(holder.lblLoginDate.getTypeface(), Typeface.BOLD);

            holder.lblAction.setTextColor(res.getColor(R.color.white));
            holder.lblAction.setVisibility(View.VISIBLE);
            holder.btnAction.setVisibility(View.GONE);



            holder.lblAction.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);
            holder.lblDistanceFrmWorkstation.setTextColor(res.getColor(R.color.white));
            holder.lblDistanceFrmWorkstation.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);
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

            if(aObjList.get(holder.getAdapterPosition()).aAttendanceStatus8.equals("Present")){
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
        holder.lblLoginDate.setText(aObjList.get(holder.getAdapterPosition()).aDate3);
        holder.lblLoginDate.setBackgroundColor(BColor);


        holder.linearLAction.setBackgroundColor(BColor);
        holder.lblDay.setText(aObjList.get(holder.getAdapterPosition()).aDay4);
            holder.lblDay.setBackgroundColor(BColor);
            holder.lblLoginTime.setText(aObjList.get(holder.getAdapterPosition()).aSignIn5);
            holder.lblLoginTime.setBackgroundColor(BColor);
            holder.lblLogoutTime.setText(aObjList.get(holder.getAdapterPosition()).aSignOut6);
            holder.lblLogoutTime.setBackgroundColor(BColor);
            holder.lblSpentTime.setText(aObjList.get(holder.getAdapterPosition()).aHoursWorked7);
            holder.lblSpentTime.setBackgroundColor(BColor);
            holder.lblStatus.setText(aObjList.get(holder.getAdapterPosition()).aAttendanceStatus8);
            holder.lblStatus.setBackgroundColor(BColor);
            holder.lblDistanceFrmWorkstation.setText(aObjList.get(holder.getAdapterPosition()).aDistanceFromWorkstation9);
            holder.lblDistanceFrmWorkstation.setBackgroundColor(BColor);
            holder.lblAction.setText(aObjList.get(holder.getAdapterPosition()).aCommentLink10);
            holder.lblAction.setBackgroundColor(BColor);
        if(holder.getAdapterPosition()>0) {
            int aLoginStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aSignInColor11);
            int aLogoutStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aSignOutColor12);
            if (aLoginStatus == 1) {
                if((holder.getAdapterPosition()%2)==0) {
                    holder.lblLoginTime.setBackgroundResource(R.drawable.blue_border_even);
                }
                else{
                    holder.lblLoginTime.setBackgroundResource(R.drawable.blue_border_odd);
                }
            } else if (aLoginStatus == 2) {
                if((holder.getAdapterPosition()%2)==0) {
                    holder.lblLoginTime.setBackgroundResource(R.drawable.red_border_even);
                }
                else{
                    holder.lblLoginTime.setBackgroundResource(R.drawable.red_border_odd);
                }
            }
            if (aLogoutStatus == 1) {
                if((holder.getAdapterPosition()%2)==0) {
                    holder.lblLogoutTime.setBackgroundResource(R.drawable.blue_border_even);
                }
                else{
                    holder.lblLogoutTime.setBackgroundResource(R.drawable.blue_border_odd);
                }
            } else if (aLogoutStatus == 2) {
                if((holder.getAdapterPosition()%2)==0) {
                    holder.lblLogoutTime.setBackgroundResource(R.drawable.red_border_even);
                }
                else{
                    holder.lblLogoutTime.setBackgroundResource(R.drawable.red_border_odd);
                }
            }

            int aCommentStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aCommentLink10);
            if (aCommentStatus == 1) {
                Labels = this.getLabelFromDb("l_S104_view_comment", R.string.l_S104_view_comment);

            } else {
                Labels = this.getLabelFromDb("l_S104_post_comment", R.string.l_S104_post_comment);
            }
            holder.btnAction.setOnClickListener(new View.OnClickListener() {
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
            });
        }
        holder.btnAction.setText(Labels);
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
        holder.lblLogoutTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aSignOut6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLogoutTime,sToolTip,4);
            }
        });
        holder.lblSpentTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aHoursWorked7;
                ((BaseAPC)activityInCall).showTooltip(holder.lblSpentTime,sToolTip,4);
            }
        });
        holder.lblLoginTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aSignIn5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLoginTime,sToolTip,4);
            }
        });
        holder.lblStatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aAttendanceStatus8;
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
        public SCurrentAttObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblLoginDate,lblAction,lblDay,lblLoginTime,lblLogoutTime,lblSpentTime,lblStatus,lblDistanceFrmWorkstation;
        public final Button btnAction;
        public final LinearLayout linearLAction;
        public AttHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.tRow);
            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);
            lblLoginDate= (TextView) itemView.findViewById(R.id.lblLoginDate);
            btnAction= (Button) itemView.findViewById(R.id.btnAction);
            lblAction= (TextView) itemView.findViewById(R.id.lblAction);
            lblDistanceFrmWorkstation= (TextView) itemView.findViewById(R.id.lblDistanceFrmWorkstation);
            lblDay= (TextView) itemView.findViewById(R.id.lblDay);
            lblLoginTime= (TextView) itemView.findViewById(R.id.lblLoginTime);
            lblLogoutTime= (TextView) itemView.findViewById(R.id.lblLogoutTime);
            lblSpentTime= (TextView) itemView.findViewById(R.id.lblSpentTime);
            lblStatus= (TextView) itemView.findViewById(R.id.lblStatus);
        }
    }
}
