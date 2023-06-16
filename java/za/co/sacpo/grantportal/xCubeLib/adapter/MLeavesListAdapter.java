package za.co.sacpo.grantportal.xCubeLib.adapter;

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
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MLeavesListObj;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
import za.co.sacpo.grantportal.xCubeMentor.leaves.MSLeaveListA;
import za.co.sacpo.grantportal.xCubeMentor.leaves.MSLeaveDetailListA;

public class MLeavesListAdapter extends RecyclerView.Adapter<MLeavesListAdapter.LeavesHolder> {
    private List<MLeavesListObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    MSLeaveListA baseActivity;
    public MLeavesListAdapter(List<MLeavesListObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, MSLeaveListA baseActivity) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.baseActivity = baseActivity;
    }
    public void gotoApprovedList(Context context){
        Bundle inputUri = new Bundle();
        inputUri.putString("student_id", baseActivity.getStudentId());
        inputUri.putString("m_student_name",baseActivity.getStudentName());
        inputUri.putString("generator", "M410");
        inputUri.putString("type", "a");
        Intent intent = new Intent(context, MSLeaveDetailListA.class);
        intent.putExtras(inputUri);
        context.startActivity(intent);
    }
    public void gotoPendingList(Context context){
        Bundle inputUri = new Bundle();
        inputUri.putString("student_id", baseActivity.getStudentId());
        inputUri.putString("m_student_name",baseActivity.getStudentName());
        inputUri.putString("generator", "M410");
        inputUri.putString("type", "p");
        Intent intent = new Intent(context, MSLeaveDetailListA.class);
        intent.putExtras(inputUri);
        context.startActivity(intent);
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
    public MLeavesListAdapter.LeavesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_leave_list_row, parent, false);
        return new MLeavesListAdapter.LeavesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MLeavesListAdapter.LeavesHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);

            Labels=this.getLabelFromDb("t_head_M410_AP",R.string.t_head_M410_AP);
            holder.tvC1R1C1.setText(Labels);
            Labels=this.getLabelFromDb("t_head_M410_PL",R.string.t_head_M410_PL);
            holder.tvC2R1C1.setText(Labels);
            Labels=this.getLabelFromDb("t_head_M410_TAL",R.string.t_head_M410_TAL);
            holder.tvC1R2C1.setText(Labels);
            Labels=this.getLabelFromDb("t_head_M410_TSL",R.string.t_head_M410_TSL);
            holder.tvC1R2C2.setText(Labels);
            Labels=this.getLabelFromDb("t_head_M410_TOPL",R.string.t_head_M410_TOPL);
            holder.tvC1R2C3.setText(Labels);
            Labels=this.getLabelFromDb("t_head_M410_TUL",R.string.t_head_M410_TUL);
            holder.tvC1R2C4.setText(Labels);
            Labels=this.getLabelFromDb("t_head_M410_PAL",R.string.t_head_M410_PAL);
            holder.tvC2R2C1.setText(Labels);
            Labels=this.getLabelFromDb("t_head_M410_PSL",R.string.t_head_M410_PSL);
            holder.tvC2R2C2.setText(Labels);
            Labels=this.getLabelFromDb("t_head_M410_POPL",R.string.t_head_M410_POPL);
            holder.tvC2R2C3.setText(Labels);
            Labels=this.getLabelFromDb("t_head_M410_PUL",R.string.t_head_M410_PUL);
            holder.tvC2R2C4.setText(Labels);

            holder.tvC1R2C1.setTypeface(holder.tvC1R2C1.getTypeface(), Typeface.BOLD);
            holder.tvC1R2C2.setTypeface(holder.tvC1R2C2.getTypeface(), Typeface.BOLD);
            holder.tvC1R2C3.setTypeface(holder.tvC1R2C3.getTypeface(), Typeface.BOLD);
            holder.tvC1R2C4.setTypeface(holder.tvC1R2C4.getTypeface(), Typeface.BOLD);
            holder.tvC2R2C1.setTypeface(holder.tvC2R2C1.getTypeface(), Typeface.BOLD);
            holder.tvC2R2C2.setTypeface(holder.tvC2R2C2.getTypeface(), Typeface.BOLD);
            holder.tvC2R2C3.setTypeface(holder.tvC2R2C3.getTypeface(), Typeface.BOLD);
            holder.tvC2R2C4.setTypeface(holder.tvC2R2C4.getTypeface(), Typeface.BOLD);
        }
        else{
            int BWhite = res.getColor(R.color.white);
            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
                //BColorRedLink=res.getColor(R.color.red_link_back_even);

                //BColorD = res.getDrawable(R.drawable.backcell_data_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
                //BColorRedLink=res.getColor(R.color.red_link_back_odd);
                //BColorD = res.getDrawable(R.drawable.backcell_data_odd);
            }

            holder.tvC1R2C1.setBackgroundColor(BColor);
            holder.tvC1R2C2.setBackgroundColor(BColor);
            holder.tvC1R2C3.setBackgroundColor(BColor);
            holder.tvC1R2C4.setBackgroundColor(BColor);
            holder.tvC2R2C1.setBackgroundColor(BColor);
            holder.tvC2R2C2.setBackgroundColor(BColor);
            holder.tvC2R2C3.setBackgroundColor(BColor);
            holder.tvC2R2C4.setBackgroundColor(BColor);

            holder.tvC1R2C1.setText(aObjList.get(holder.getAdapterPosition()).tal3);
            holder.tvC1R2C2.setText(aObjList.get(holder.getAdapterPosition()).tsl4);
            holder.tvC1R2C3.setText(aObjList.get(holder.getAdapterPosition()).topl5);
            holder.tvC1R2C4.setText(aObjList.get(holder.getAdapterPosition()).tul6);
            holder.tvC2R2C1.setText(aObjList.get(holder.getAdapterPosition()).pal7);
            holder.tvC2R2C2.setText(aObjList.get(holder.getAdapterPosition()).psl8);
            holder.tvC2R2C3.setText(aObjList.get(holder.getAdapterPosition()).popl9);
            holder.tvC2R2C4.setText(aObjList.get(holder.getAdapterPosition()).pul10);
            holder.tvC1R1C1.setVisibility(View.GONE);
            holder.tvC2R1C1.setVisibility(View.GONE);

            holder.tvC1R2C1.setTextColor(res.getColor(R.color.row_link));
            holder.tvC1R2C1.setPaintFlags(holder.tvC1R2C1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC1R2C1.getPaint().setUnderlineText(true);

            holder.tvC1R2C2.setTextColor(res.getColor(R.color.row_link));
            holder.tvC1R2C2.setPaintFlags(holder.tvC1R2C2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC1R2C2.getPaint().setUnderlineText(true);

            holder.tvC1R2C3.setTextColor(res.getColor(R.color.row_link));
            holder.tvC1R2C3.setPaintFlags(holder.tvC1R2C3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC1R2C3.getPaint().setUnderlineText(true);

            holder.tvC1R2C4.setTextColor(res.getColor(R.color.row_link));
            holder.tvC1R2C4.setPaintFlags(holder.tvC1R2C4.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC1R2C4.getPaint().setUnderlineText(true);


            holder.tvC2R2C1.setTextColor(res.getColor(R.color.row_link));
            holder.tvC2R2C1.setPaintFlags(holder.tvC2R2C1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC2R2C1.getPaint().setUnderlineText(true);

            holder.tvC2R2C2.setTextColor(res.getColor(R.color.row_link));
            holder.tvC2R2C2.setPaintFlags(holder.tvC2R2C2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC2R2C2.getPaint().setUnderlineText(true);

            holder.tvC2R2C3.setTextColor(res.getColor(R.color.row_link));
            holder.tvC2R2C3.setPaintFlags(holder.tvC2R2C3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC2R2C3.getPaint().setUnderlineText(true);

            holder.tvC2R2C4.setTextColor(res.getColor(R.color.row_link));
            holder.tvC2R2C4.setPaintFlags(holder.tvC2R2C4.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.tvC2R2C4.getPaint().setUnderlineText(true);

            holder.tvC1R2C1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    gotoApprovedList(view.getContext());
                }
            });
            holder.tvC1R2C2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    gotoApprovedList(view.getContext());
                }
            });
            holder.tvC1R2C3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    gotoApprovedList(view.getContext());
                }
            });
            holder.tvC1R2C4.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    gotoApprovedList(view.getContext());
                }
            });
            holder.tvC2R2C1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    gotoPendingList(view.getContext());
                }
            });
            holder.tvC2R2C2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    gotoPendingList(view.getContext());
                }
            });
            holder.tvC2R2C3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    gotoPendingList(view.getContext());
                }
            });
            holder.tvC2R2C4.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    gotoPendingList(view.getContext());
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        if(aObjList!=null) {
            return aObjList.size();
        }
        return 0;
    }

    public class LeavesHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MLeavesListObj.Item hItem;
        public final TableRow tRow;
        public final TextView tvC1R1C1,tvC2R1C1,tvC1R2C1,tvC1R2C2,tvC1R2C3,tvC1R2C4,tvC2R2C1,tvC2R2C2,tvC2R2C3,tvC2R2C4;
        public LeavesHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.leave_list_Row);
            tvC1R1C1= itemView.findViewById(R.id.tvC1R1C1);
            tvC2R1C1= itemView.findViewById(R.id.tvC2R1C1);
            tvC1R2C1= itemView.findViewById(R.id.tvC1R2C1);
            tvC1R2C2= itemView.findViewById(R.id.tvC1R2C2);
            tvC1R2C3= itemView.findViewById(R.id.tvC1R2C3);
            tvC1R2C4= itemView.findViewById(R.id.tvC1R2C4);
            tvC2R2C1= itemView.findViewById(R.id.tvC2R2C1);
            tvC2R2C2= itemView.findViewById(R.id.tvC2R2C2);
            tvC2R2C3= itemView.findViewById(R.id.tvC2R2C3);
            tvC2R2C4= itemView.findViewById(R.id.tvC2R2C4);

        }
    }
}
