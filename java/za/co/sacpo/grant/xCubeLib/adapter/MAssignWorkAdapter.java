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
import za.co.sacpo.grant.xCubeLib.dataObj.MAssginWorkObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.workX.MAddWorksA;
import za.co.sacpo.grant.xCubeMentor.workX.MChangeSWorkXA;

public class MAssignWorkAdapter extends RecyclerView.Adapter<MAssignWorkAdapter.ViewHolder> {
    private List<MAssginWorkObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    String temp;
    public MAssignWorkAdapter(List<MAssginWorkObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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

                while (res.moveToNext())

                {
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
    public MAssignWorkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_assign_work, parent, false);
        return new MAssignWorkAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MAssignWorkAdapter.ViewHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor,CColor;


        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);


            holder.lblLearnerName.setTextColor(res.getColor(R.color.white));
            holder.lblLearnerName.setTypeface(holder.lblLearnerName.getTypeface(), Typeface.BOLD);
            holder.lblEmail.setTextColor(res.getColor(R.color.white));
            holder.lblEmail.setTypeface(holder.lblEmail.getTypeface(), Typeface.BOLD);
            holder.lblCellNo.setTextColor(res.getColor(R.color.white));
            holder.lblCellNo.setTypeface(holder.lblCellNo.getTypeface(), Typeface.BOLD);
            holder.lblGrantName.setTextColor(res.getColor(R.color.white));
            holder.lblGrantName.setTypeface(holder.lblGrantName.getTypeface(), Typeface.BOLD);
            holder.lblStartDate.setTextColor(res.getColor(R.color.white));
            holder.lblStartDate.setTypeface(holder.lblStartDate.getTypeface(), Typeface.BOLD);
            holder.lblEndDate.setTextColor(res.getColor(R.color.white));
            holder.lblEndDate.setTypeface(holder.lblEndDate.getTypeface(), Typeface.BOLD);


            Labels =this.getLabelFromDb("l_246_Action",R.string.l_246_Action);
            holder.lblAction1.setText(Labels);
            holder.btnAction1.setVisibility(View.GONE);
            holder.lblAction1.setVisibility(View.VISIBLE);
            holder.lblAction1.setTextColor(res.getColor(R.color.white));
            holder.lblAction1.setTypeface(holder.lblAction1.getTypeface(), Typeface.BOLD);

            holder.linearLAction1.setBackgroundColor(BColor);


            Labels =this.getLabelFromDb("l_246_Action2",R.string.l_246_Action2);
            holder.lblAction2.setText(Labels);
            holder.btnAction2.setVisibility(View.GONE);
            holder.lblAction2.setVisibility(View.VISIBLE);
            holder.lblAction2.setTextColor(res.getColor(R.color.white));
            holder.lblAction2.setTypeface(holder.lblAction2.getTypeface(), Typeface.BOLD);


            holder.linearLAction2.setBackgroundColor(BColor);



        }

        else {

                if ((position % 2) == 0) {
                    BColor = res.getColor(R.color.row_even);
                    CColor = res.getColor(R.color.color_background_redE);
                } else {
                    BColor = res.getColor(R.color.row_odd);
                    CColor = res.getColor(R.color.color_background_redO);
                }



                if(aObjList.get(holder.getAdapterPosition()).MaBgColor9.equals("1")){

                    holder.lblAction1.setVisibility(View.GONE);
                    holder.btnAction1.setVisibility(View.VISIBLE);
                    holder.btnAction1.setText(aObjList.get(holder.getAdapterPosition()).MabtnName10);

                    Labels =this.getLabelFromDb("l_246_BtnAction",R.string.l_246_BtnAction);
                    holder.btnAction2.setText(Labels);
                    holder.btnAction2.setVisibility(View.VISIBLE);
                    holder.btnActionSecondBtn.setVisibility(View.GONE);
                    holder.lblAction2.setVisibility(View.GONE);

                    holder.linearLAction2.setBackgroundColor(BColor);


                }else{



                    holder.lblAction1.setVisibility(View.VISIBLE);
                    holder.btnAction1.setVisibility(View.GONE);
                    holder.lblAction1.setText(aObjList.get(holder.getAdapterPosition()).MabtnName10);


                    Labels =this.getLabelFromDb("l_246_BtnAction2",R.string.l_246_BtnAction2);
                    holder.btnActionSecondBtn.setText(Labels);
                    holder.btnActionSecondBtn.setVisibility(View.VISIBLE);
                    holder.btnAction2.setVisibility(View.GONE);
                    holder.lblAction2.setVisibility(View.GONE);

                    holder.linearLAction2.setBackgroundColor(CColor);





                }




            }



        holder.btnAction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle inputUri = new Bundle();
                String student_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("student_id", student_id);
                String workstation_id = String.valueOf(holder.hItem.mWorkstaionid11);
                inputUri.putString("workstation_id", workstation_id);
                String mwX_student_name4 = String.valueOf(holder.hItem.MaLearnerName3);
                inputUri.putString("mwX_student_name4", mwX_student_name4);
                inputUri.putString("generator", "246");
                Context context = v.getContext();
                Intent intent = new Intent(context, MChangeSWorkXA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });

        holder.btnActionSecondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                String student_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("student_id", student_id);
                inputUri.putString("generator", "246");
                Context context = v.getContext();
                Intent intent = new Intent(context, MAddWorksA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });







        holder.lblLearnerName.setText(aObjList.get(holder.getAdapterPosition()).MaLearnerName3);
        holder.lblLearnerName.setBackgroundColor(BColor);
        holder.lblEmail.setText(aObjList.get(holder.getAdapterPosition()).MaEmail4);
        holder.lblEmail.setBackgroundColor(BColor);
        holder.lblCellNo.setText(aObjList.get(holder.getAdapterPosition()).MaCellNo5);
        holder.lblCellNo.setBackgroundColor(BColor);
        holder.lblGrantName.setText(aObjList.get(holder.getAdapterPosition()).MaGrantName6);
        holder.lblGrantName.setBackgroundColor(BColor);
        holder.lblStartDate.setText(aObjList.get(holder.getAdapterPosition()).MaStartDate7);
        holder.lblStartDate.setBackgroundColor(BColor);
        holder.lblEndDate.setText(aObjList.get(holder.getAdapterPosition()).MaEndDate8);
        holder.lblEndDate.setBackgroundColor(BColor);



        holder.linearLAction1.setBackgroundColor(BColor);





        holder.lblLearnerName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).MaLearnerName3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLearnerName,sToolTip, 4);
            }
        });


        holder.lblEmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).MaEmail4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblEmail,sToolTip, 4);
            }
        });

        holder.lblCellNo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).MaCellNo5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblCellNo,sToolTip, 4);
            }
        });

        holder.lblGrantName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).MaGrantName6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblGrantName,sToolTip, 4);
            }
        });


        holder.lblStartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).MaStartDate7;
                ((BaseAPC)activityInCall).showTooltip(holder.lblStartDate,sToolTip,4);
            }
        });
        holder.lblEndDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).MaEndDate8;
                ((BaseAPC)activityInCall).showTooltip(holder.lblEndDate,sToolTip,4);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MAssginWorkObj.Item hItem;
        public final TableRow tRow;
        public final LinearLayout linearLAction1,linearLAction2;
        public final TextView  lblLearnerName,lblEmail,lblCellNo,lblGrantName ,lblStartDate, lblEndDate,lblAction1,lblAction2;
        public final Button btnAction1,btnAction2,btnActionSecondBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.mAW_Row);

            lblLearnerName= (TextView) itemView.findViewById(R.id.lblLearnerName);
            lblEmail= (TextView) itemView.findViewById(R.id.lblEmail);
            lblCellNo= (TextView) itemView.findViewById(R.id.lblCellNo);
            lblGrantName= (TextView) itemView.findViewById(R.id.lblGrantName);
            lblStartDate= (TextView) itemView.findViewById(R.id.lblStartDate);
            lblEndDate= (TextView) itemView.findViewById(R.id.lblEndDate);


            linearLAction1=  itemView.findViewById(R.id.linearLAction1);
            linearLAction2=  itemView.findViewById(R.id.linearLAction2);

            lblAction1=  itemView.findViewById(R.id.lblAction1);
            btnAction1=  itemView.findViewById(R.id.btnAction1);

            lblAction2=  itemView.findViewById(R.id.lblAction2);
            btnAction2=  itemView.findViewById(R.id.btnAction2);
            btnActionSecondBtn=  itemView.findViewById(R.id.btnActionSecondBtn);




        }
    }
}
