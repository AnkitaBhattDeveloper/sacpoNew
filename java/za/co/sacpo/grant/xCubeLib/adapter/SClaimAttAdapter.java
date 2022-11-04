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
        import za.co.sacpo.grant.xCubeLib.dataObj.SClaimAttObj;
        import za.co.sacpo.grant.xCubeLib.db.DbHelper;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class SClaimAttAdapter extends RecyclerView.Adapter<SClaimAttAdapter.AttHolder> {
    private List<SClaimAttObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    //Todo: Table Width Problem
    public SClaimAttAdapter(List<SClaimAttObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
                .inflate(R.layout.claim_att_row, parent, false);
        return new AttHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AttHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);
            holder.lblLoginDate.setTextColor(res.getColor(R.color.white));
            holder.lblLoginDate.setTypeface(holder.lblLoginDate.getTypeface(), Typeface.BOLD);
            holder.lblLoginDate.setBackgroundColor(BColor);

            holder.lblDistanceFrmWorkstation.setTextColor(res.getColor(R.color.white));
            holder.lblDistanceFrmWorkstation.setTypeface(holder.lblDistanceFrmWorkstation.getTypeface(), Typeface.BOLD);
            holder.lblDistanceFrmWorkstation.setBackgroundColor(BColor);
            holder.lblDay.setTextColor(res.getColor(R.color.white));
            holder.lblDay.setTypeface(holder.lblDay.getTypeface(), Typeface.BOLD);
            holder.lblDay.setBackgroundColor(BColor);
            holder.lblLoginTime.setTextColor(res.getColor(R.color.white));
            holder.lblLoginTime.setTypeface(holder.lblLoginTime.getTypeface(), Typeface.BOLD);
            holder.lblLoginTime.setBackgroundColor(BColor);
            holder.lblLogoutTime.setTextColor(res.getColor(R.color.white));
            holder.lblLogoutTime.setTypeface(holder.lblLogoutTime.getTypeface(), Typeface.BOLD);
            holder.lblLogoutTime.setBackgroundColor(BColor);
            holder.lblSpentTime.setTextColor(res.getColor(R.color.white));
            holder.lblSpentTime.setTypeface(holder.lblSpentTime.getTypeface(), Typeface.BOLD);
            holder.lblSpentTime.setBackgroundColor(BColor);
            holder.lblALeave.setTextColor(res.getColor(R.color.white));
            holder.lblALeave.setTypeface(holder.lblALeave.getTypeface(), Typeface.BOLD);
            holder.lblALeave.setBackgroundColor(BColor);
            holder.lblSLeave.setTextColor(res.getColor(R.color.white));
            holder.lblSLeave.setTypeface(holder.lblSLeave.getTypeface(), Typeface.BOLD);
            holder.lblSLeave.setBackgroundColor(BColor);
            holder.lblOPLeave.setTextColor(res.getColor(R.color.white));
            holder.lblOPLeave.setTypeface(holder.lblOPLeave.getTypeface(), Typeface.BOLD);
            holder.lblOPLeave.setBackgroundColor(BColor);
            holder.lblUPLeave.setTextColor(res.getColor(R.color.white));
            holder.lblUPLeave.setTypeface(holder.lblUPLeave.getTypeface(), Typeface.BOLD);
            holder.lblUPLeave.setBackgroundColor(BColor);
            holder.lblLoginDate.setText(aObjList.get(holder.getAdapterPosition()).aDate3);
            holder.lblDay.setText(aObjList.get(holder.getAdapterPosition()).aDay4);
            holder.lblLoginTime.setText(aObjList.get(holder.getAdapterPosition()).aSignIn5);
            holder.lblLogoutTime.setText(aObjList.get(holder.getAdapterPosition()).aSignOut6);
            holder.lblSpentTime.setText(aObjList.get(holder.getAdapterPosition()).aHoursWorked7);
            holder.lblALeave.setText(aObjList.get(holder.getAdapterPosition()).aLeave8);
            holder.lblDistanceFrmWorkstation.setText(aObjList.get(holder.getAdapterPosition()).aDistanceFromWorkstation9);
            holder.lblSLeave.setText(aObjList.get(holder.getAdapterPosition()).aSLeave10);
            holder.lblOPLeave.setText(aObjList.get(holder.getAdapterPosition()).aOPLeave11);
            holder.lblUPLeave.setText(aObjList.get(holder.getAdapterPosition()).aUPLeave12);
        }
        else{

            if((holder.getAdapterPosition()%2)==0){
                //BColor=res.getColor(R.color.row_even);
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
            holder.lblLoginDate.setText(aObjList.get(holder.getAdapterPosition()).aDate3);
            holder.lblLoginDate.setBackgroundColor(BColor);
            holder.lblLoginDate.setTextColor(res.getColor(R.color.black_three));
            holder.lblDay.setTextColor(res.getColor(R.color.black_three));
            holder.lblLoginTime.setTextColor(res.getColor(R.color.black_three));
            holder.lblLogoutTime.setTextColor(res.getColor(R.color.black_three));
            holder.lblSpentTime.setTextColor(res.getColor(R.color.black_three));
            holder.lblALeave.setTextColor(res.getColor(R.color.black_three));
            holder.lblDistanceFrmWorkstation.setTextColor(res.getColor(R.color.black_three));
            holder.lblSLeave.setTextColor(res.getColor(R.color.black_three));
            holder.lblOPLeave.setTextColor(res.getColor(R.color.black_three));
            holder.lblUPLeave.setTextColor(res.getColor(R.color.black_three));

            holder.lblDay.setText(aObjList.get(holder.getAdapterPosition()).aDay4);
            holder.lblDay.setBackgroundColor(BColor);
            holder.lblLoginTime.setText(aObjList.get(holder.getAdapterPosition()).aSignIn5);
            holder.lblLoginTime.setBackgroundColor(BColor);
            holder.lblLogoutTime.setText(aObjList.get(holder.getAdapterPosition()).aSignOut6);
            holder.lblLogoutTime.setBackgroundColor(BColor);
            holder.lblSpentTime.setText(aObjList.get(holder.getAdapterPosition()).aHoursWorked7);
            holder.lblSpentTime.setBackgroundColor(BColor);
            holder.lblALeave.setText(aObjList.get(holder.getAdapterPosition()).aLeave8);
            holder.lblALeave.setBackgroundColor(BColor);
            holder.lblDistanceFrmWorkstation.setText(aObjList.get(holder.getAdapterPosition()).aDistanceFromWorkstation9);
            holder.lblDistanceFrmWorkstation.setBackgroundColor(BColor);

            holder.lblSLeave.setText(aObjList.get(holder.getAdapterPosition()).aSLeave10);
            holder.lblSLeave.setBackgroundColor(BColor);
            holder.lblOPLeave.setText(aObjList.get(holder.getAdapterPosition()).aOPLeave11);
            holder.lblOPLeave.setBackgroundColor(BColor);
            holder.lblUPLeave.setText(aObjList.get(holder.getAdapterPosition()).aUPLeave12);
            holder.lblUPLeave.setBackgroundColor(BColor);
        }

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
        public SClaimAttObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblLoginDate,lblDay,lblLoginTime,lblLogoutTime,lblSpentTime,lblALeave,lblSLeave,lblOPLeave,lblUPLeave,lblDistanceFrmWorkstation;
        public AttHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.tRow);
            lblLoginDate= (TextView) itemView.findViewById(R.id.lblLoginDate);
            lblDistanceFrmWorkstation= (TextView) itemView.findViewById(R.id.lblDistanceFrmWorkstation);
            lblDay= (TextView) itemView.findViewById(R.id.lblDay);
            lblLoginTime= (TextView) itemView.findViewById(R.id.lblLoginTime);
            lblLogoutTime= (TextView) itemView.findViewById(R.id.lblLogoutTime);
            lblSpentTime= (TextView) itemView.findViewById(R.id.lblSpentTime);
            lblALeave= (TextView) itemView.findViewById(R.id.lblALeave);
            lblSLeave= (TextView) itemView.findViewById(R.id.lblSLeave);
            lblOPLeave= (TextView) itemView.findViewById(R.id.lblOPLeave);
            lblUPLeave= (TextView) itemView.findViewById(R.id.lblUPLeave);
        }
    }
}
