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
import za.co.sacpo.grant.xCubeLib.dataObj.MWorkXObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.workX.MEditWorkXA;
import za.co.sacpo.grant.xCubeMentor.workX.MRemoveWorkXA;

public class MWorkXStuListAdapter extends RecyclerView.Adapter<MWorkXStuListAdapter.WorkXHolder> {
    private List<MWorkXObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;

    public MWorkXStuListAdapter(List<MWorkXObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
    public MWorkXStuListAdapter.WorkXHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_wx_stu_row, parent, false);
        return new MWorkXStuListAdapter.WorkXHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MWorkXStuListAdapter.WorkXHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head);
            holder.lblwLearnerName.setTextColor(res.getColor(R.color.white));
            holder.lblwLearnerName.setTypeface(holder.lblwLearnerName.getTypeface(), Typeface.BOLD);

            Labels =this.getLabelFromDb("l_602_workxStudentList_action",R.string.l_602_workxStudentList_action);
            holder.lblAction.setText(Labels);
            holder.lblAction.setTextColor(res.getColor(R.color.white));
            holder.lblAction.setVisibility(View.VISIBLE);
            holder.btnActionEdit.setVisibility(View.GONE);

            Labels =this.getLabelFromDb("l_602_workxStudentList_action",R.string.l_602_workxStudentList_action);
            holder.lblAction2.setText(Labels);
            holder.lblAction2.setTextColor(res.getColor(R.color.white));
            holder.lblAction2.setVisibility(View.VISIBLE);
            holder.btnActionRemove.setVisibility(View.GONE);

            holder.lblAction.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);
            holder.lblAction2.setTypeface(holder.lblAction2.getTypeface(), Typeface.BOLD);
            holder.lblwWorksUniverse.setTextColor(res.getColor(R.color.white));
            holder.lblwWorksUniverse.setTypeface(holder.lblwWorksUniverse.getTypeface(), Typeface.BOLD);
            holder.lblwQualification.setTextColor(res.getColor(R.color.white));
            holder.lblwQualification.setTypeface(holder.lblwQualification.getTypeface(), Typeface.BOLD);
            holder.lblwEmail.setTextColor(res.getColor(R.color.white));
            holder.lblwEmail.setTypeface(holder.lblwEmail.getTypeface(), Typeface.BOLD);
            holder.lblwMobile.setTextColor(res.getColor(R.color.white));
            holder.lblwMobile.setTypeface(holder.lblwMobile.getTypeface(), Typeface.BOLD);

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
        holder.lblwLearnerName.setText(aObjList.get(holder.getAdapterPosition()).mWEmpName3);
        holder.lblwLearnerName.setBackgroundColor(BColor);
        holder.btnActionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                inputUri.putInt("wrkstat_id", holder.hItem.aId2);
                Context context = v.getContext();
                Intent intent = new Intent(context, MEditWorkXA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        }); holder.btnActionRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                inputUri.putInt("wrkstat_id", holder.hItem.aId2);
                Context context = v.getContext();
                Intent intent = new Intent(context, MRemoveWorkXA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });

        holder.linearLAction.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_602_workxStudentList_edit",R.string.l_602_workxStudentList_edit);
        holder.btnActionEdit.setText(Labels);
        holder.linearLAction2.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_602_workxStudentList_remove",R.string.l_602_workxStudentList_remove);
        holder.btnActionRemove.setText(Labels);

        holder.lblwLearnerName.setText(aObjList.get(holder.getAdapterPosition()).mWEmpName3);
        holder.lblwLearnerName.setBackgroundColor(BColor);
        holder.lblwWorksUniverse.setText(aObjList.get(holder.getAdapterPosition()).mWWorkstation4);
        holder.lblwWorksUniverse.setBackgroundColor(BColor);
        holder.lblwQualification.setText(aObjList.get(holder.getAdapterPosition()).mWStuent5);
        holder.lblwQualification.setBackgroundColor(BColor);
        holder.lblwEmail.setText(aObjList.get(holder.getAdapterPosition()).mWLatitude6);
        holder.lblwEmail.setBackgroundColor(BColor);
        holder.lblwMobile.setText(aObjList.get(holder.getAdapterPosition()).mWLongitude7);
        holder.lblwMobile.setBackgroundColor(BColor);

        holder.lblwLearnerName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mWEmpName3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblwLearnerName,sToolTip,4);
            }
        });
        holder.lblwWorksUniverse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mWWorkstation4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblwWorksUniverse,sToolTip,4);
            }
        });
        holder.lblwQualification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mWStuent5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblwQualification,sToolTip,4);
            }
        });
        holder.lblwEmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mWLatitude6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblwEmail,sToolTip,4);
            }
        });
        holder.lblwMobile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mWLongitude7;
                ((BaseAPC)activityInCall).showTooltip(holder.lblwMobile,sToolTip,4);
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

    public class WorkXHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MWorkXObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblwRegistration,lblwLearnerNo,lblwXName, lblwLearnerName,lblwWorksUniverse,lblwQualification,lblwEmail,lblwMobile,lblAction,lblAction2;
        public final Button btnActionEdit,btnActionRemove;
        public final LinearLayout linearLAction,linearLAction2;
        public WorkXHolder(View itemView) {
            super(itemView);
            hView = itemView;

            tRow= (TableRow) itemView.findViewById(R.id.work_Row);

            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);
            linearLAction2 = (LinearLayout) itemView.findViewById(R.id.linearLAction2);

            btnActionEdit= (Button) itemView.findViewById(R.id.btnActionEdit);
            btnActionRemove= (Button) itemView.findViewById(R.id.btnActionRemove);

            lblwLearnerName= (TextView) itemView.findViewById(R.id.lblwLearnerName);
            lblwWorksUniverse= (TextView) itemView.findViewById(R.id.lblwWorksUniverse);
            lblwQualification= (TextView) itemView.findViewById(R.id.lblwQualification);
            lblwEmail= (TextView) itemView.findViewById(R.id.lblwEmail);
            lblwMobile= (TextView) itemView.findViewById(R.id.lblwMobile);
            lblwRegistration= (TextView) itemView.findViewById(R.id.lblwRegistration);
            lblwLearnerNo= (TextView) itemView.findViewById(R.id.lblwLearnerNo);
            lblwXName= (TextView) itemView.findViewById(R.id.lblwXName);

            lblAction= (TextView) itemView.findViewById(R.id.lblAction);
            lblAction2= (TextView) itemView.findViewById(R.id.lblAction2);
        }
    }
}
