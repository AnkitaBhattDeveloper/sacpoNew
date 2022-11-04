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
import za.co.sacpo.grant.xCubeLib.dataObj.SMPollObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.workX.MWorkXsDA;

public class MServeyListAdapter extends RecyclerView.Adapter<MServeyListAdapter.ServeyHolder> {
    private List<SMPollObj.Item> aObjList;
    private Context baseActivityContext;
    protected DbHelper dbSetaObj;
    String Labels;
    private AppCompatActivity activityInCall;

    public MServeyListAdapter(List<SMPollObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
    public MServeyListAdapter.ServeyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poll_listing_row, parent, false);
        return new MServeyListAdapter.ServeyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MServeyListAdapter.ServeyHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);

            holder.lblPoll_1.setTextColor(res.getColor(R.color.white));
            holder.lblPoll_1.setTypeface(holder.lblPoll_1.getTypeface(), Typeface.BOLD);

            Labels =this.getLabelFromDb("l_615_surveylist_Action",R.string.l_615_surveylist_Action);
            holder.lblAction.setText(Labels);
            holder.lblAction.setTextColor(res.getColor(R.color.white));
            holder.lblAction.setVisibility(View.VISIBLE);
            holder.btnActionEdit.setVisibility(View.GONE);

            Labels =this.getLabelFromDb("l_615_surveylist_Action",R.string.l_615_surveylist_Action);
            holder.lblAction2.setText(Labels);
            holder.lblAction2.setTextColor(res.getColor(R.color.white));
            holder.lblAction2.setVisibility(View.VISIBLE);

            holder.btnActionRemove.setVisibility(View.GONE);

            holder.lblAction.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);
            holder.lblAction2.setTypeface(holder.lblAction2.getTypeface(), Typeface.BOLD);

            holder.lblPoll_1.setTextColor(res.getColor(R.color.white));
            holder.lblPoll_1.setTypeface(holder.lblPoll_1.getTypeface(), Typeface.BOLD);

            holder.lblPoll_2.setTextColor(res.getColor(R.color.white));
            holder.lblPoll_2.setTypeface(holder.lblPoll_2.getTypeface(), Typeface.BOLD);

            holder.lblPoll_3.setTextColor(res.getColor(R.color.white));
            holder.lblPoll_3.setTypeface(holder.lblPoll_3.getTypeface(), Typeface.BOLD);

             holder.lblPoll_4.setTextColor(res.getColor(R.color.white));
             holder.lblPoll_4.setTypeface(holder.lblPoll_4.getTypeface(), Typeface.BOLD);

             holder.lblPoll_5.setTextColor(res.getColor(R.color.white));
             holder.lblPoll_5.setTypeface(holder.lblPoll_5.getTypeface(), Typeface.BOLD);

             holder.lblPoll_6.setTextColor(res.getColor(R.color.white));
             holder.lblPoll_6.setTypeface(holder.lblPoll_6.getTypeface(), Typeface.BOLD);


        }

        else{
            holder.lblAction.setVisibility(View.GONE);
            holder.btnActionEdit.setVisibility(View.VISIBLE);
            holder.lblAction2.setVisibility(View.GONE);
            holder.btnActionRemove.setVisibility(View.VISIBLE);
            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
        }

        holder.btnActionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                inputUri.putInt("menter_servy_id", holder.hItem.aId2);
                Context context = v.getContext();
                Intent intent = new Intent(context, MWorkXsDA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });

        holder.linearLAction.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_615_surveylist_Details",R.string.l_615_surveylist_Details);
        holder.btnActionEdit.setText(Labels);
        holder.linearLAction2.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_615_surveylist_Details",R.string.l_615_surveylist_Details);
        holder.btnActionRemove.setText(Labels);

        holder.lblPoll_1.setText(aObjList.get(holder.getAdapterPosition()).poll_3);
        holder.lblPoll_1.setBackgroundColor(BColor);


        holder.lblPoll_2.setText(aObjList.get(holder.getAdapterPosition()).poll_4);
        holder.lblPoll_2.setBackgroundColor(BColor);


        holder.lblPoll_3.setText(aObjList.get(holder.getAdapterPosition()).poll_5);
        holder.lblPoll_3.setBackgroundColor(BColor);

        holder.lblPoll_4.setText(aObjList.get(holder.getAdapterPosition()).poll_6);
        holder.lblPoll_4.setBackgroundColor(BColor);

        holder.lblPoll_5.setText(aObjList.get(holder.getAdapterPosition()).poll_7);
        holder.lblPoll_5.setBackgroundColor(BColor);

        holder.lblPoll_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).poll_3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblPoll_1,sToolTip,4);
            }
        });

        holder.lblPoll_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).poll_4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblPoll_2,sToolTip,4);
            }
        });

        holder.lblPoll_3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).poll_5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblPoll_3,sToolTip,4);
            }
        });

        holder.lblPoll_4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).poll_6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblPoll_4,sToolTip,4);
            }
        });
      holder.lblPoll_5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).poll_7;
                ((BaseAPC)activityInCall).showTooltip(holder.lblPoll_5,sToolTip,4);
            }
        });

         holder.lblPoll_6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).poll_8;
                ((BaseAPC)activityInCall).showTooltip(holder.lblPoll_6,sToolTip,4);
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

    public class ServeyHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public SMPollObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblPoll_1,lblPoll_2, lblPoll_3,lblPoll_4,lblPoll_5,lblAction,lblAction2;
        public final TextView lblPoll_6;
        public final Button btnActionEdit,btnActionRemove;
        public final LinearLayout linearLAction,linearLAction2;
        public ServeyHolder(View itemView) {
            super(itemView);
            hView = itemView;

            tRow= (TableRow) itemView.findViewById(R.id.work_Row);

            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);
            linearLAction2 = (LinearLayout) itemView.findViewById(R.id.linearLAction2);
            btnActionEdit= (Button) itemView.findViewById(R.id.btnActionEdit);
            btnActionRemove= (Button) itemView.findViewById(R.id.btnActionRemove);

            lblPoll_1= (TextView) itemView.findViewById(R.id.lblPoll_1);
            lblPoll_2= (TextView) itemView.findViewById(R.id.lblPoll_2);
            lblPoll_3= (TextView) itemView.findViewById(R.id.lblPoll_3);
            lblPoll_4= (TextView) itemView.findViewById(R.id.lblPoll_4);
            lblPoll_5= (TextView) itemView.findViewById(R.id.lblPoll_5);
            lblPoll_6= (TextView) itemView.findViewById(R.id.lblPoll_6);

            lblAction= (TextView) itemView.findViewById(R.id.lblAction);
            lblAction2= (TextView) itemView.findViewById(R.id.lblAction2);
        }
    }
}
