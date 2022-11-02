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
import za.co.sacpo.grant.xCubeLib.dataObj.MAssignSObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.workX.MAssignedSA;
import za.co.sacpo.grant.xCubeMentor.workX.MChangeSWorkXA;

public class MAssignSAdapter extends RecyclerView.Adapter<MAssignSAdapter.WorkXHolder> {
    private List<MAssignSObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    MAssignedSA baseActivity;
    public MAssignSAdapter(List<MAssignSObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, MAssignedSA baseActivity) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.baseActivity  = baseActivity;
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
    public MAssignSAdapter.WorkXHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_wx_assign_row, parent, false);
        return new MAssignSAdapter.WorkXHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MAssignSAdapter.WorkXHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head);
            holder.lblwxAssignStuName.setTextColor(res.getColor(R.color.white));
            holder.lblwxAssignStuName.setTypeface(holder.lblwxAssignStuName.getTypeface(), Typeface.BOLD);

            Labels =this.getLabelFromDb("l_601_workxAssign_action",R.string.l_601_workxAssign_action);
            holder.lblAction.setText(Labels);
            holder.lblAction.setTextColor(res.getColor(R.color.white));
            holder.btnActionEdit.setVisibility(View.GONE);

            holder.lblAction.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);

            holder.lblwxAssignStuName.setTextColor(res.getColor(R.color.white));
            holder.lblwxAssignStuName.setTypeface(holder.lblwxAssignStuName.getTypeface(), Typeface.BOLD);
        }
        else{

            holder.lblAction.setVisibility(View.GONE);
            holder.btnActionEdit.setVisibility(View.VISIBLE);
            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
        }
        holder.lblwxAssignStuName.setText(aObjList.get(holder.getAdapterPosition()).mSLearnerName3);
        holder.lblwxAssignStuName.setBackgroundColor(BColor);
        holder.btnActionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();

                inputUri.putInt("s_id", holder.hItem.aId2);
                inputUri.putString("generator", "303");
                String student_id = String.valueOf(holder.hItem.mwxStudentid8);
                inputUri.putString("student_id", student_id);
                String mWorkstation_name = String.valueOf(holder.hItem.mwxStatus5);
                inputUri.putString("mWorkstation_name", mWorkstation_name);
                String mwX_student_name4 = String.valueOf(holder.hItem.mSLearnerName3);
                inputUri.putString("mwX_student_name4", mwX_student_name4);
                inputUri.putString("work_x_id", baseActivity.getWorkXId());
                inputUri.putString("work_x_name",baseActivity.getWorkXName());
                Context context = v.getContext();
                Intent intent = new Intent(context, MChangeSWorkXA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });

        holder.linearLAction.setBackgroundColor(BColor);
        holder.btnActionEdit.setText("Change Workstation");

        holder.lblwxAssignStuName.setText(aObjList.get(holder.getAdapterPosition()).mSLearnerName3);
        holder.lblwxAssignStuName.setBackgroundColor(BColor);

        holder.lblwxAssignStuName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mSLearnerName3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblwxAssignStuName,sToolTip,4);
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
        public MAssignSObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblwxAssignStuName,lblAction;
        public final Button btnActionEdit;
        public final LinearLayout linearLAction;
        public WorkXHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.work_Row);
            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);
            btnActionEdit= (Button) itemView.findViewById(R.id.btnActionEdit);
            lblwxAssignStuName= (TextView) itemView.findViewById(R.id.lblwxAssignStuName);
            lblAction= (TextView) itemView.findViewById(R.id.lblAction);
        }
    }
}