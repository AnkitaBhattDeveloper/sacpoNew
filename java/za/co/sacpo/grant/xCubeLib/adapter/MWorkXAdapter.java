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
import za.co.sacpo.grant.xCubeLib.dataObj.MWorkXObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.workX.MAssignedSA;
import za.co.sacpo.grant.xCubeMentor.workX.MEditWorkXA;
import za.co.sacpo.grant.xCubeMentor.workX.MWorkXsDA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class MWorkXAdapter extends RecyclerView.Adapter<MWorkXAdapter.WorkXHolder> {
    private List<MWorkXObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;

    public MWorkXAdapter(List<MWorkXObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
    public WorkXHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_works_row, parent, false);
        return new WorkXHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WorkXHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head);
            holder.lblwEmployerName.setTextColor(res.getColor(R.color.white));
            holder.lblwEmployerName.setTypeface(holder.lblwEmployerName.getTypeface(), Typeface.BOLD);

            Labels =this.getLabelFromDb("l_600_workx_edit",R.string.l_600_workx_edit);
            holder.lblAction.setText(Labels);
            holder.lblAction.setTextColor(res.getColor(R.color.white));
            holder.lblAction.setVisibility(View.VISIBLE);
            holder.btnActionEdit.setVisibility(View.GONE);

            Labels =this.getLabelFromDb("l_600_workx_remove",R.string.l_600_workx_remove);
            holder.lblAction2.setText(Labels);
            holder.lblAction2.setTextColor(res.getColor(R.color.white));
            holder.lblAction2.setVisibility(View.VISIBLE);
            holder.btnActionRemove.setVisibility(View.VISIBLE);

            holder.lblAction.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);
            holder.lblAction2.setTypeface(holder.lblAction2.getTypeface(), Typeface.BOLD);
            holder.lblwWorkstationName.setTextColor(res.getColor(R.color.white));
            holder.lblwWorkstationName.setTypeface(holder.lblwWorkstationName.getTypeface(), Typeface.BOLD);
            holder.lblwStudent.setTextColor(res.getColor(R.color.white));
            holder.lblwStudent.setTypeface(holder.lblwStudent.getTypeface(), Typeface.BOLD);
            holder.lblwLatitude.setTextColor(res.getColor(R.color.white));
            holder.lblwLatitude.setTypeface(holder.lblwLatitude.getTypeface(), Typeface.BOLD);
            holder.lblwLongitude.setTextColor(res.getColor(R.color.white));
            holder.lblwLongitude.setTypeface(holder.lblwLongitude.getTypeface(), Typeface.BOLD);

            holder.lblwStudent.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String sToolTip=aObjList.get(holder.getAdapterPosition()).mWStuent5;
                    ((BaseAPC)activityInCall).showTooltip(holder.lblwStudent,sToolTip,4);
                }
            });

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





            if(aObjList.get(holder.getAdapterPosition()).mWStuent5.equals("0")){

                holder.lblwStudent.setText(aObjList.get(holder.getAdapterPosition()).mWStuent5);


            }else{

                holder.lblwStudent.setTextColor(res.getColor(R.color.row_link));
                holder.lblwStudent.setPaintFlags(holder.lblwStudent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.lblwStudent.getPaint().setUnderlineText(true);

                holder.lblwStudent.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
               /* String sToolTip=aObjList.get(holder.getAdapterPosition()).mWStuent5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblwStudent,sToolTip,4);*/
                        Bundle inputUri = new Bundle();
                        inputUri.putInt("work_x_id", holder.hItem.aId2);
                        inputUri.putString("work_x_name", holder.hItem.mWWorkstation4);
                        Context context = view.getContext();
                        Intent intent = new Intent(context, MAssignedSA.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);
                    }
                });


           }

        }
        holder.lblwEmployerName.setText(aObjList.get(holder.getAdapterPosition()).mWEmpName3);
        holder.lblwEmployerName.setBackgroundColor(BColor);
        holder.btnActionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
               // inputUri.putInt("work_x_id", holder.hItem.aId2);
                String work_x_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("work_x_id", work_x_id);
                String latitude = String.valueOf(holder.hItem.mWLatitude6);
                inputUri.putString("latitude", latitude);
                String longitute = String.valueOf(holder.hItem.mWLongitude7);
                inputUri.putString("longitute", longitute);
                Context context = v.getContext();
                Intent intent = new Intent(context, MEditWorkXA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });

        holder.btnActionRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Bundle inputUri = new Bundle();
                inputUri.putInt("work_x_id", holder.hItem.aId2);
                Context context = v.getContext();
                Intent intent = new Intent(context, MWorkXsDA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });

        holder.linearLAction.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_600_workx_edit",R.string.l_600_workx_edit);
        holder.btnActionEdit.setText(Labels);
        holder.linearLAction2.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_600_workx_remove",R.string.l_600_workx_remove);
        holder.btnActionRemove.setText(Labels);

        holder.linearLAction2.setVisibility(View.GONE);
        holder.lblwEmployerName.setText(aObjList.get(holder.getAdapterPosition()).mWEmpName3);
        holder.lblwEmployerName.setBackgroundColor(BColor);
        holder.lblwWorkstationName.setText(aObjList.get(holder.getAdapterPosition()).mWWorkstation4);
        holder.lblwWorkstationName.setBackgroundColor(BColor);
        holder.lblwStudent.setText(aObjList.get(holder.getAdapterPosition()).mWStuent5);
        holder.lblwStudent.setBackgroundColor(BColor);
        holder.lblwLatitude.setText(aObjList.get(holder.getAdapterPosition()).mWLatitude6);
        holder.lblwLatitude.setBackgroundColor(BColor);
        holder.lblwLongitude.setText(aObjList.get(holder.getAdapterPosition()).mWLongitude7);
        holder.lblwLongitude.setBackgroundColor(BColor);


        holder.lblwEmployerName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mWEmpName3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblwEmployerName,sToolTip,4);
            }
        });
        holder.lblwWorkstationName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mWWorkstation4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblwWorkstationName,sToolTip,4);
            }
        });

           if(aObjList.get(holder.getAdapterPosition()).mWStuent5.equals("0")){

              // Toast.makeText(this,"workstation not assigned",Toast.LENGTH_SHORT).show();
               
        }

       
        holder.lblwLatitude.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mWLatitude6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblwLatitude,sToolTip,4);
            }
        });
        holder.lblwLongitude.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mWLongitude7;
                ((BaseAPC)activityInCall).showTooltip(holder.lblwLongitude,sToolTip,4);
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
        public final TextView lblwEmployerName,lblwWorkstationName,lblwStudent,lblwLatitude,lblwLongitude,lblAction,lblAction2;
        public final Button btnActionEdit,btnActionRemove;
        public final LinearLayout linearLAction,linearLAction2;
        public WorkXHolder(View itemView) {
            super(itemView);
            hView = itemView;

            tRow= (TableRow) itemView.findViewById(R.id.Mentor_work_Row);

            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);
            linearLAction2 = (LinearLayout) itemView.findViewById(R.id.linearLAction2);

            btnActionEdit= (Button) itemView.findViewById(R.id.btnActionEdit);
            btnActionRemove= (Button) itemView.findViewById(R.id.btnActionRemove);

            lblwEmployerName= (TextView) itemView.findViewById(R.id.lblwEmployerName);
            lblwWorkstationName= (TextView) itemView.findViewById(R.id.lblwWorkstationName);
            lblwStudent= (TextView) itemView.findViewById(R.id.lblwStudent);
            lblwLatitude= (TextView) itemView.findViewById(R.id.lblwLatitude);
            lblwLongitude= (TextView) itemView.findViewById(R.id.lblwLongitude);
            lblAction= (TextView) itemView.findViewById(R.id.lblAction);
            lblAction2= (TextView) itemView.findViewById(R.id.lblAction2);
        }
    }
}
