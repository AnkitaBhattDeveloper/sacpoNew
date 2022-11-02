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
import android.widget.Toast;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.dataObj.MStipendClaimObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.claims.MApproveA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class MCStipendAdapter extends RecyclerView.Adapter<MCStipendAdapter.ClaimHolder> {
    private List<MStipendClaimObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;

    public MCStipendAdapter(List<MStipendClaimObj.Item> aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
    }

    public String getLabelFromDb(String inputLabel, int resId) {
        String ValueLabel = baseActivityContext.getString(resId);
        dbSetaObj = DbHelper.getInstance(baseActivityContext);
        Cursor res = dbSetaObj.getDataValueByName(inputLabel);
        if (res.getCount() > 0) {
            try {
                while (res.moveToNext()) {
                    ValueLabel = res.getString(0);
                }
            } finally {
                if (res != null && !res.isClosed()) {
                    res.close();
                    dbSetaObj.close();
                }
            }
        }
        return ValueLabel;
    }

    @Override
    public ClaimHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.m_stipend_row, parent, false);
        return new ClaimHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ClaimHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem = aObjList.get(holder.getAdapterPosition());
        int BColor;
        if (position == 0) {
            BColor = res.getColor(R.color.row_head);


            Labels = this.getLabelFromDb("l_190_s_lblAction", R.string.l_190_s_lblAction);
            holder.lblAction.setText(Labels);
            holder.lblAction.setTextColor(res.getColor(R.color.white));
            holder.lblAction.setTypeface(holder.lblDate.getTypeface(), Typeface.BOLD);
            holder.lblAction.setVisibility(View.VISIBLE);
            holder.btnAction.setVisibility(View.GONE);



            holder.lblDate.setTextColor(res.getColor(R.color.white));
            holder.lblDate.setTypeface(holder.lblDate.getTypeface(), Typeface.BOLD);
            holder.lblLeave.setTextColor(res.getColor(R.color.white));
            holder.lblLeave.setTypeface(holder.lblLeave.getTypeface(), Typeface.BOLD);
            holder.lblAmount.setTextColor(res.getColor(R.color.white));
            holder.lblAmount.setTypeface(holder.lblAmount.getTypeface(), Typeface.BOLD);

            } else {
            holder.lblAction.setVisibility(View.GONE);
            holder.btnAction.setVisibility(View.VISIBLE);

            if ((position % 2) == 0) {
                BColor = res.getColor(R.color.row_even);
            } else {
                BColor = res.getColor(R.color.row_odd);
            }

            holder.btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(baseActivityContext, "clicked", Toast.LENGTH_SHORT).show();
                }
            });


        }


        holder.lblDate.setText(aObjList.get(holder.getAdapterPosition()).stiDate3);
        holder.lblDate.setBackgroundColor(BColor);

        holder.lblLeave.setText(aObjList.get(holder.getAdapterPosition()).stileave4);
        holder.lblLeave.setBackgroundColor(BColor);
        holder.lblAmount.setText(aObjList.get(holder.getAdapterPosition()).stiAmount5);
        holder.lblAmount.setBackgroundColor(BColor);




        holder.linearLAction.setBackgroundColor(BColor);

        Labels = this.getLabelFromDb("l_190_s_btnAction", R.string.l_190_s_btnAction);
        holder.btnAction.setText(Labels);




        holder.lblDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sToolTip = aObjList.get(holder.getAdapterPosition()).stiDate3;
                ((BaseAPC) activityInCall).showTooltip(holder.lblDate, sToolTip, 4);
            }
        });
        holder.lblLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sToolTip = aObjList.get(holder.getAdapterPosition()).stileave4;
                ((BaseAPC) activityInCall).showTooltip(holder.lblLeave, sToolTip, 4);
            }
        });
        holder.lblAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sToolTip = aObjList.get(holder.getAdapterPosition()).stiAmount5;
                ((BaseAPC) activityInCall).showTooltip(holder.lblAmount, sToolTip, 4);
            }
        });
      holder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle inputUri = new Bundle();
                String stipend_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("stipend_id", stipend_id);
              /*  inputUri.putString("m_student_name", baseActivity.getStudentName());
                inputUri.putString("student_id",baseActivity.getStudentId());*/
                Context context = view.getContext();
                Intent intent = new Intent(context, MApproveA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
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

       public class ClaimHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MStipendClaimObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblDate,lblLeave,lblAmount,lblAction;
        public final Button btnAction;
        public final LinearLayout linearLAction;
        public ClaimHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.mStipend_row);

            btnAction= (Button) itemView.findViewById(R.id.btnAction);
            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);
            
            lblDate= (TextView) itemView.findViewById(R.id.lblDate);
            lblLeave= (TextView) itemView.findViewById(R.id.lblLeave);
            lblAmount= (TextView) itemView.findViewById(R.id.lblAmount);
            lblAction= (TextView) itemView.findViewById(R.id.lblAction);


        }
    }
}
