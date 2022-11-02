package za.co.sacpo.grant.xCubeLib.adapter;

import android.annotation.SuppressLint;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAdapter;
import za.co.sacpo.grant.xCubeLib.dataObj.SReportsObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.feedback.MFeedbackA;
import za.co.sacpo.grant.xCubeMentor.feedback.MStudentReports;
import za.co.sacpo.grant.xCubeMentor.notes.MAddNote;
import za.co.sacpo.grant.xCubeMentor.notes.MNoteList;
import za.co.sacpo.grant.xCubeMentor.feedback.MStudentReportsDetailsA;

public class MSReportAdapter extends RecyclerView.Adapter<MSReportAdapter.ViewHolder> implements BaseAdapter{
    private List<SReportsObj.Item> aObjList;
    public Context baseActivityContext;
    public AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels,read_Count7,student_name,student_id;
    private BaseAdapter bAI;

    MStudentReports baseActivity;
    public MSReportAdapter(List<SReportsObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall,MStudentReports baseActivity) {
        //this.bAI = ((BaseAdapter) baseActivityContext);
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.baseActivity = baseActivity;
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
    public MSReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_student_reports_row, parent, false);
        return new MSReportAdapter.ViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MSReportAdapter.ViewHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor,BColorRed;
        int BColorRedLink;
        BColorRed = res.getColor(R.color.red_link);
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head);

            holder.TxtReportTitle.setTextColor(res.getColor(R.color.white));
            holder.TxtReportTitle.setTypeface(holder.TxtReportTitle.getTypeface(), Typeface.BOLD);

            holder.TxtYear.setTextColor(res.getColor(R.color.white));
            holder.TxtYear.setTypeface(holder.TxtYear.getTypeface(), Typeface.BOLD);

            holder.TxtMonth.setTextColor(res.getColor(R.color.white));
            holder.TxtMonth.setTypeface(holder.TxtMonth.getTypeface(), Typeface.BOLD);


            holder.TxtReportNo.setTextColor(res.getColor(R.color.white));
            holder.TxtReportNo.setTypeface(holder.TxtReportNo.getTypeface(), Typeface.BOLD);

            holder.txtAction.setTextColor(res.getColor(R.color.white));
            holder.txtAction.setTypeface(holder.txtAction.getTypeface(), Typeface.BOLD);
            holder.btnAction.setVisibility(View.GONE);
            holder.txtAction.setText(aObjList.get(holder.getAdapterPosition()).msupervisorStatus5);
            holder.LinearLayoutAction.setBackgroundColor(BColor);
        }
        else{
            if((holder.getAdapterPosition()%2)==0){

                BColor=res.getColor(R.color.row_even);
                BColorRedLink=res.getColor(R.color.red_link_back_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
                BColorRedLink=res.getColor(R.color.red_link_back_odd);
            }




            holder.TxtReportNo.setTextColor(res.getColor(R.color.row_link));
            holder.TxtReportNo.setTypeface(holder.TxtReportNo.getTypeface(), Typeface.NORMAL);



            holder.TxtReportTitle.setTextColor(res.getColor(R.color.row_link));
            holder.TxtReportTitle.setPaintFlags(holder.TxtReportTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.TxtReportTitle.getPaint().setUnderlineText(true);


            holder.btnAction.setTextColor(res.getColor(R.color.colorPrimary));
            holder.btnAction.setTypeface(holder.btnAction.getTypeface(), Typeface.BOLD);

            holder.btnAction.setText(aObjList.get(holder.getAdapterPosition()).msupervisorStatus5);
            holder.txtAction.setText(aObjList.get(holder.getAdapterPosition()).msupervisorStatus5);
            int pendingStatus = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).misSupervisorApproved7);
            if(pendingStatus==1){
                holder.txtAction.setVisibility(View.GONE);
                holder.txtAction.setBackgroundColor(BColor);
                holder.btnAction.setVisibility(View.VISIBLE);
                holder.LinearLayoutAction.setBackgroundColor(BColorRed);
            }
            else{
                holder.LinearLayoutAction.setBackgroundColor(BColor);
                holder.txtAction.setVisibility(View.VISIBLE);
                holder.txtAction.setBackgroundColor(BColor);
                holder.btnAction.setVisibility(View.GONE);
            }




            holder.TxtReportTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String report_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_name", baseActivity.getStudentName());
                    inputUri.putString("student_id", baseActivity.getStudentId());
                    inputUri.putString("report_id", report_id);
                    inputUri.putString("generator", "336");
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MStudentReportsDetailsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
            holder.TxtReportNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String report_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_name", baseActivity.getStudentName());
                    inputUri.putString("student_id", baseActivity.getStudentId());
                    inputUri.putString("report_id", report_id);
                    inputUri.putString("generator", "336");
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MStudentReportsDetailsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
            holder.btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String report_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_name", baseActivity.getStudentName());
                    inputUri.putString("student_id", baseActivity.getStudentId());
                    inputUri.putString("report_number",holder.hItem.mReportN6);
                    inputUri.putString("report_id", report_id);
                    inputUri.putString("generator", "336");
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MFeedbackA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });

        }
        holder.TxtReportTitle.setText(aObjList.get(holder.getAdapterPosition()).msfReportTitle3);
        holder.TxtReportTitle.setBackgroundColor(BColor);




      /*  holder.TxtReportTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                String user_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("user_id", user_id);
                inputUri.putString("student_name", aObjList.get(holder.getAdapterPosition()).msfReportName3);
                inputUri.putString("generator", "149");

                Context context = view.getContext();
                Intent intent = new Intent(context, MDashboardDA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });*/

        holder.TxtYear.setText(aObjList.get(holder.getAdapterPosition()).msfYear4);
        holder.TxtYear.setBackgroundColor(BColor);
        holder.TxtYear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).msfYear4;
                ((BaseAPC)activityInCall).showTooltip(holder.TxtYear,sToolTip,4);
            }
        });
     
        holder.TxtMonth.setText(aObjList.get(holder.getAdapterPosition()).month8);
        holder.TxtMonth.setBackgroundColor(BColor);

        holder.TxtMonth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).month8;
                ((BaseAPC)activityInCall).showTooltip(holder.TxtMonth,sToolTip,4);
            }
        });






        holder.TxtReportNo.setText(aObjList.get(holder.getAdapterPosition()).mReportN6);
        holder.TxtReportNo.setBackgroundColor(BColor);
        holder.TxtReportTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            String sToolTip=aObjList.get(holder.getAdapterPosition()).msfReportTitle3;
            ((BaseAPC)activityInCall).showTooltip(holder.TxtReportTitle,sToolTip,4);
            }
        });

    }



    private void popup (final View view, final int userId) {

        PopupMenu popup = new PopupMenu(baseActivityContext, view);
        popup.getMenuInflater().inflate(R.menu.note_menu,popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent;
                String user_id;
                Context context;
                switch (menuItem.getItemId()){


                    case R.id.addNote:

                        Bundle inputUri2 = new Bundle();
                        user_id = String.valueOf(userId);
                        inputUri2.putString("user_id", user_id);
                        inputUri2.putString("date_input", "");
                        inputUri2.putString("generator", "149");
                        context = view.getContext();
                        intent = new Intent(context, MAddNote.class);
                        intent.putExtras(inputUri2);
                        context.startActivity(intent);


                        break;


                    case R.id.notList:

                        Bundle inputUri = new Bundle();
                        user_id = String.valueOf(userId);
                        inputUri.putString("user_id", user_id);
                        inputUri.putString("date_input", "");
                        inputUri.putString("generator", "149");
                        context = view.getContext();
                        intent = new Intent(context, MNoteList.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);


                    default:
                        break;


                }

                return false;
            }
        });

        popup.show();
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
        public SReportsObj.Item hItem;
        public final TableRow tRow;
        public final TextView TxtReportTitle,TxtYear,TxtMonth,TxtReportNo , txtAction;
        public final Button btnAction;
        public final LinearLayout LinearLayoutAction;
        public ViewHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.studentReports_row);

            TxtReportTitle= (TextView) itemView.findViewById(R.id.TxtReportTitle);
            TxtYear= (TextView) itemView.findViewById(R.id.TxtYear);
            TxtMonth= (TextView) itemView.findViewById(R.id.TxtMonth);
            TxtReportNo= (TextView) itemView.findViewById(R.id.TxtReportNo);

            txtAction= (TextView) itemView.findViewById(R.id.txtAction);
            btnAction= (Button) itemView.findViewById(R.id.btnAction);
            LinearLayoutAction = (LinearLayout) itemView.findViewById(R.id.LinearLayoutAction);

        }
    }
}
