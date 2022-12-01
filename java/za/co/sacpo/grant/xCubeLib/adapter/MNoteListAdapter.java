package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.dataObj.MNoteObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.notes.MDeleteNoteA;
import za.co.sacpo.grant.xCubeMentor.notes.MEditNotelistA;
import za.co.sacpo.grant.xCubeMentor.notes.MNoteList;


public class MNoteListAdapter extends RecyclerView.Adapter<MNoteListAdapter.ServeyHolder> {
    private List<MNoteObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    MNoteList baseActivity;
    boolean isCATShow = false;
    ArrayList<String> cats = new ArrayList<String>();
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

    public MNoteListAdapter(List<MNoteObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall,MNoteList baseActivity) {
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
    public MNoteListAdapter.ServeyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_list_row, parent, false);
        return new MNoteListAdapter.ServeyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MNoteListAdapter.ServeyHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
      //  int BColor;
        holder.txtDescription.setText(aObjList.get(holder.getAdapterPosition()).description3);
        holder.txtAddDate.setText(aObjList.get(holder.getAdapterPosition()).add_date4);
        holder.txtAddDate.setTextColor(baseActivityContext.getResources().getColor(baseAPC.getTextcolorResourceId("dashboard_textColor")));
        holder.txtnote10.setText(aObjList.get(holder.getAdapterPosition()).note_11);
        holder.txtnote10.setTextColor(baseActivityContext.getResources().getColor(baseAPC.getTextcolorResourceId("dashboard_textColor")));


        Labels =this.getLabelFromDb("l_222_btn_edit",R.string.l_222_btn_edit);
        holder.btnEdit.setText(Labels);
        holder.btnEdit.setBackground(baseActivityContext.getResources().getDrawable(baseAPC.getDrwabaleResourceId("themed_button_action")));

        Labels =this.getLabelFromDb("l_222_btn_delete",R.string.l_222_btn_delete);
        holder.btnDelete.setText(Labels);
        holder.btnDelete.setBackground(baseActivityContext.getResources().getDrawable(baseAPC.getDrwabaleResourceId("themed_button_action")));

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                String n_id = String.valueOf(holder.hItem.cell_no7);
                String description = String.valueOf(holder.hItem.description3);
                inputUri.putString("n_id", n_id);
                String grant_id = String.valueOf(holder.hItem.grant_id11);
                inputUri.putString("grant_id", grant_id);
                String user_id = String.valueOf(holder.hItem.user_id12);
                inputUri.putString("user_id", user_id);
                inputUri.putString("student_id",baseActivity.getStudentId());
                inputUri.putString("student_name", baseActivity.getStudentName());
                inputUri.putString("description", description);
                inputUri.putString("category_id", aObjList.get(holder.getAdapterPosition()).note_10);
                inputUri.putString("generator", "174");
                Context context = view.getContext();
                Intent intent = new Intent(context, MEditNotelistA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });
    holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                String n_id = String.valueOf(holder.hItem.cell_no7);
                inputUri.putString("n_id", n_id);
                inputUri.putString("generator", "174");
                String description = String.valueOf(holder.hItem.description3);
                inputUri.putString("description", description);
                String grant_id = String.valueOf(holder.hItem.grant_id11);
                inputUri.putString("grant_id", grant_id);
                String user_id = String.valueOf(holder.hItem.user_id12);
                inputUri.putString("user_id", user_id);
                String date = String.valueOf(holder.hItem.add_date4);
                inputUri.putString("student_id",baseActivity.getStudentId());
                inputUri.putString("student_name", baseActivity.getStudentName());
                inputUri.putString("date", date);
                Context context = view.getContext();
                Intent intent = new Intent(context, MDeleteNoteA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });

        String mCatD = aObjList.get(holder.getAdapterPosition()).note_10;
        if(cats.contains(mCatD)){
            isCATShow = false;
        }
        else{
            isCATShow = true;
            cats.add(mCatD);
        }
        if(isCATShow){
            holder.head_container.setVisibility(View.VISIBLE);
        }
        else{
            holder.head_container.setVisibility(View.GONE);
        }


/*        public final TextView h_Note,h_date,h_add_by,h_duration,h_note_for,h_note_from,h_cell_no;
 */


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
        public MNoteObj.Item hItem;

        public final TextView txtDescription, txtAddDate, txtnote10;
        public final Button btnEdit, btnDelete;

        public final LinearLayout head_container;
        public ServeyHolder(View itemView) {
            super(itemView);
            hView = itemView;
            txtDescription= (TextView) itemView.findViewById(R.id.txtDescription);
            txtAddDate= (TextView) itemView.findViewById(R.id.txtAddDate);
            txtnote10= (TextView) itemView.findViewById(R.id.txtnote10);
            btnEdit= (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete= (Button) itemView.findViewById(R.id.btnDelete);
            head_container =(LinearLayout) itemView.findViewById(R.id.head_container);



        }
    }
}
