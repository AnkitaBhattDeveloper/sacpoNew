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
import za.co.sacpo.grant.xCubeLib.dataObj.StuFeedbackObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;
import za.co.sacpo.grant.xCubeMentor.messages.MChatA;
import za.co.sacpo.grant.xCubeMentor.notes.MAddNote;
import za.co.sacpo.grant.xCubeMentor.notes.MNoteList;
import za.co.sacpo.grant.xCubeMentor.student.StudentA;

public class MSSuupervisorAdapter extends RecyclerView.Adapter<MSSuupervisorAdapter.ViewHolder> implements BaseAdapter{
    private List<StuFeedbackObj.Item> aObjList;
    public Context baseActivityContext;
    public AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    private BaseAdapter bAI;
    public MSSuupervisorAdapter(List<StuFeedbackObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
        //this.bAI = ((BaseAdapter) baseActivityContext);
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
    public MSSuupervisorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_student_feedback_row, parent, false);
        return new MSSuupervisorAdapter.ViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MSSuupervisorAdapter.ViewHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        int BColorRedLink;
        BColor = res.getColor(R.color.red_link_back_even);
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);

            holder.TxtLearnerName.setTextColor(res.getColor(R.color.white));
            holder.TxtLearnerName.setTypeface(holder.TxtLearnerName.getTypeface(), Typeface.BOLD);

            holder.TxtReportsCounts.setTextColor(res.getColor(R.color.white));
            holder.TxtReportsCounts.setTypeface(holder.TxtReportsCounts.getTypeface(), Typeface.BOLD);

            holder.TxtSupervisorComments.setTextColor(res.getColor(R.color.white));
            holder.TxtSupervisorComments.setTypeface(holder.TxtSupervisorComments.getTypeface(), Typeface.BOLD);

            holder.txtWeekendDate.setTextColor(res.getColor(R.color.white));
            holder.txtWeekendDate.setTypeface(holder.txtWeekendDate.getTypeface(), Typeface.BOLD);



            Labels =this.getLabelFromDb("l_335_txtAction",R.string.l_335_txtAction);
            holder.txtAction.setText(Labels);
            holder.txtAction.setTextColor(res.getColor(R.color.white));
            holder.txtAction.setTypeface(holder.txtAction.getTypeface(), Typeface.BOLD);
            holder.txtAction.setVisibility(View.VISIBLE);
            holder.btnAction.setVisibility(View.GONE);


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

            holder.btnAction.setTextColor(res.getColor(R.color.white));
            holder.btnAction.setTypeface(holder.btnAction.getTypeface(), Typeface.BOLD);
            holder.txtAction.setVisibility(View.GONE);
            holder.btnAction.setVisibility(View.VISIBLE);
            holder.btnAction.setText("DETAILS");


            holder.TxtLearnerName.setTextColor(res.getColor(R.color.row_link));
            holder.TxtLearnerName.setPaintFlags(holder.TxtLearnerName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.TxtLearnerName.getPaint().setUnderlineText(true);

            holder.TxtReportsCounts.setTextColor(res.getColor(R.color.row_link));
            holder.TxtReportsCounts.setPaintFlags(holder.TxtReportsCounts.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.TxtReportsCounts.getPaint().setUnderlineText(true);

            holder.TxtSupervisorComments.setTextColor(res.getColor(R.color.row_link));
            holder.TxtSupervisorComments.setPaintFlags(holder.TxtSupervisorComments.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.TxtSupervisorComments.getPaint().setUnderlineText(true);

            holder.txtWeekendDate.setTextColor(res.getColor(R.color.row_link));
            holder.txtWeekendDate.setPaintFlags(holder.txtWeekendDate.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.txtWeekendDate.getPaint().setUnderlineText(true);

        }
        holder.TxtLearnerName.setText(aObjList.get(holder.getAdapterPosition()).msfLearnerName3);
        holder.TxtLearnerName.setBackgroundColor(BColor);
        holder.TxtLearnerName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                String user_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("user_id", user_id);
                inputUri.putString("student_name", aObjList.get(holder.getAdapterPosition()).msfLearnerName3);
                inputUri.putString("generator", "149");

                Context context = view.getContext();
                Intent intent = new Intent(context, MDashboardDA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });

        holder.TxtReportsCounts.setText(aObjList.get(holder.getAdapterPosition()).msfReportsCounts4);
        holder.TxtReportsCounts.setBackgroundColor(BColor);
        holder.TxtReportsCounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                String student_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("fId", student_id);
                inputUri.putString("fIsGroup", "0");
                inputUri.putString("fName", holder.hItem.msfLearnerName3);
                inputUri.putString("generator", "312");
                Context context = view.getContext();
                Intent intent = new Intent(context, MChatA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });
        holder.TxtSupervisorComments.setText(aObjList.get(holder.getAdapterPosition()).msfSupervisorComments5);
        holder.TxtSupervisorComments.setBackgroundColor(BColor);
        holder.TxtSupervisorComments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                String student_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("student_id", student_id);
                inputUri.putString("generator", "312");
                Context context = view.getContext();
                Intent intent = new Intent(context, StudentA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });


        holder.LinearLayoutAction.setBackgroundColor(BColor);
        Labels = this.getLabelFromDb("l_335_Details", R.string.l_335_Details);
        holder.btnAction.setText(Labels);


        holder.txtWeekendDate.setText(aObjList.get(holder.getAdapterPosition()).msfWeekendDate6);
        holder.txtWeekendDate.setBackgroundColor(BColor);
        holder.txtWeekendDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            String sToolTip=aObjList.get(holder.getAdapterPosition()).msfWeekendDate6;
            ((BaseAPC)activityInCall).showTooltip(holder.txtWeekendDate,sToolTip,4);

        /*Bundle inputUri = new Bundle();
                String user_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("user_id", user_id);
                inputUri.putString("date_input", "");
                inputUri.putString("generator", "149");
                Context context = view.getContext();
                Intent intent = new Intent(context, MAddNote.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
*/


            }
        });



/* TxtLearnerName,TxtReportsCounts,,txtWeekendDate ,txtAction;*/
        holder.TxtReportsCounts.setText(aObjList.get(holder.getAdapterPosition()).msfReportsCounts4);
        holder.TxtReportsCounts.setBackgroundColor(BColor);
        holder.TxtReportsCounts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            String sToolTip=aObjList.get(holder.getAdapterPosition()).msfReportsCounts4;
            ((BaseAPC)activityInCall).showTooltip(holder.TxtReportsCounts,sToolTip,4);
            }
        });

        holder.TxtSupervisorComments.setText(aObjList.get(holder.getAdapterPosition()).msfSupervisorComments5);
        holder.TxtSupervisorComments.setBackgroundColor(BColor);
        holder.TxtSupervisorComments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).msfSupervisorComments5;
                ((BaseAPC)activityInCall).showTooltip(holder.TxtSupervisorComments,sToolTip,4);
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
        public StuFeedbackObj.Item hItem;
        public final TableRow tRow;
        public final TextView TxtLearnerName,TxtReportsCounts,TxtSupervisorComments,txtWeekendDate ,txtAction;
        public final Button btnAction;
        public final LinearLayout LinearLayoutAction;
        public ViewHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.student_feedback_Row);

            TxtLearnerName= (TextView) itemView.findViewById(R.id.TxtLearnerName);
            TxtReportsCounts= (TextView) itemView.findViewById(R.id.TxtReportsCounts);
            TxtSupervisorComments= (TextView) itemView.findViewById(R.id.TxtSupervisorComments);
            txtWeekendDate= (TextView) itemView.findViewById(R.id.txtWeekendDate);
            txtAction= (TextView) itemView.findViewById(R.id.txtAction);

            btnAction= (Button) itemView.findViewById(R.id.btnAction);
            LinearLayoutAction = (LinearLayout) itemView.findViewById(R.id.LinearLayoutAction);

        }
    }
}
