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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dataObj.SDLeaveObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeStudent.leaves.SLeavesDA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class SDLeaveAdapter extends RecyclerView.Adapter<SDLeaveAdapter.Holder> {
    private List<SDLeaveObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;

    public SDLeaveAdapter(List<SDLeaveObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.r_s_leave_summary, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem = aObjList.get(holder.getAdapterPosition());
        int BColor;

        holder.imageButton_details_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Bundle inputUri = new Bundle();
            String Leave_type = String.valueOf(holder.hItem.aId2);
            inputUri.putString("Leave_type", Leave_type);
            inputUri.putString("generator", "103");
            Context context = v.getContext();
            Intent intent = new Intent(context, SLeavesDA.class);
            intent.putExtras(inputUri);
            context.startActivity(intent);

            }
        });
        holder.txtType.setText(aObjList.get(holder.getAdapterPosition()).aYear4);
        holder.txtCount.setText(aObjList.get(holder.getAdapterPosition()).aCount5);
        //holder.txtType.setText(aObjList.get(holder.getAdapterPosition()).aType6);
    }

    @Override
    public int getItemCount() {
        if(aObjList!=null) {
            return aObjList.size();
        }
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public final View hView;
        public SDLeaveObj.Item hItem;
        public final TextView txtCount,txtType;
        //,txtType
        public final LinearLayout tRow;
        public final ImageButton imageButton_details_leave;
        public Holder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow = (LinearLayout) itemView.findViewById(R.id.tRowLave);
            txtCount= (TextView) itemView.findViewById(R.id.txtCount);
            txtType= (TextView) itemView.findViewById(R.id.txtType);
            imageButton_details_leave= (ImageButton) itemView.findViewById(R.id.imageButton_details_leave);
        }
    }
}
