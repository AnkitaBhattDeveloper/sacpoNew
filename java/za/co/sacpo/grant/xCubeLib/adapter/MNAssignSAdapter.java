package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Paint;
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
import za.co.sacpo.grant.xCubeLib.dataObj.MNAssignSObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.student.StudentA;
import za.co.sacpo.grant.xCubeMentor.workX.MChangeSWorkXA;

public class MNAssignSAdapter extends RecyclerView.Adapter<MNAssignSAdapter.WorkXHolder> {
    private List<MNAssignSObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;

    public MNAssignSAdapter(List<MNAssignSObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
    public MNAssignSAdapter.WorkXHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_wx_non_assign_row, parent, false);
        return new MNAssignSAdapter.WorkXHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MNAssignSAdapter.WorkXHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head);

            Labels =this.getLabelFromDb("l_603_MxNonAssignStudent_action",R.string.l_603_MxNonAssignStudent_action);
            holder.lblAction.setText(Labels);
            holder.lblAction.setTextColor(res.getColor(R.color.white));
            holder.lblAction.setVisibility(View.VISIBLE);

            holder.lblAction.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);

            holder.btnActionEdit.setVisibility(View.GONE);

            holder.lbl_stu_name4.setTextColor(res.getColor(R.color.white));
            holder.lbl_stu_name4.setTypeface(holder.lbl_stu_name4.getTypeface(), Typeface.BOLD);

            holder.lbl_Email8.setTextColor(res.getColor(R.color.white));
            holder.lbl_Email8.setTypeface(holder.lbl_Email8.getTypeface(), Typeface.BOLD);

            holder.lbl_stu_name4.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String sToolTip=aObjList.get(holder.getAdapterPosition()).mwX_student_name4;
                    ((BaseAPC)activityInCall).showTooltip(holder.lbl_stu_name4,sToolTip,4);
                }
            });

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
            holder.lbl_stu_name4.setTextColor(res.getColor(R.color.row_link));
            holder.lbl_stu_name4.setPaintFlags(holder.lbl_stu_name4.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.lbl_stu_name4.getPaint().setUnderlineText(true);

            holder.lbl_stu_name4.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String student_id = String.valueOf(holder.hItem.mwXStu_User_id3);
                    inputUri.putString("student_id", student_id);
                    inputUri.putString("generator", "304");
                    Context context = view.getContext();
                    Intent intent = new Intent(context, StudentA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });

        }



        holder.btnActionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                String student_id = String.valueOf(holder.hItem.mwXStu_User_id3);
                inputUri.putString("student_id", student_id);
                String mwX_student_name4 = String.valueOf(holder.hItem.mwX_student_name4);
                inputUri.putString("mwX_student_name4", mwX_student_name4);
                inputUri.putString("generator", "304");
                Context context = v.getContext();
                Intent intent = new Intent(context, MChangeSWorkXA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });



        holder.linearLAction.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_603_MxNonAssignStudent_changeWorkstation",R.string.l_603_MxNonAssignStudent_changeWorkstation);
        holder.btnActionEdit.setText(Labels);



        holder.lbl_stu_name4.setText(aObjList.get(holder.getAdapterPosition()).mwX_student_name4);
        holder.lbl_stu_name4.setBackgroundColor(BColor);

        holder.lbl_Email8.setText(aObjList.get(holder.getAdapterPosition()).mwX_Student_Emaill8);
        holder.lbl_Email8.setBackgroundColor(BColor);

        holder.lbl_stu_name4.setText(aObjList.get(holder.getAdapterPosition()).mwX_student_name4);
        holder.lbl_stu_name4.setBackgroundColor(BColor);

        holder.lbl_Email8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mwX_Student_Emaill8;
                ((BaseAPC)activityInCall).showTooltip(holder.lbl_Email8,sToolTip,4);
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
        public MNAssignSObj.Item hItem;
        public final TableRow tRow;
        public final TextView lbl_stu_name4,lbl_Email8,lblAction;
        public final Button btnActionEdit;
        public final LinearLayout linearLAction;
        public WorkXHolder(View itemView) {
            super(itemView);
            hView = itemView;

            tRow= (TableRow) itemView.findViewById(R.id.work_Row);

            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);

            btnActionEdit= (Button) itemView.findViewById(R.id.btnActionEdit);
            lbl_stu_name4= (TextView) itemView.findViewById(R.id.lbl_stu_name4);
            lbl_Email8= (TextView) itemView.findViewById(R.id.lbl_Email8);
            lblAction= (TextView) itemView.findViewById(R.id.lblAction);
        }
    }
}
