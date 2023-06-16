package za.co.sacpo.grantportal.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAdapter;
import za.co.sacpo.grantportal.xCubeLib.dataObj.FMAnnounceObj;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
import za.co.sacpo.grantportal.xCubeMentor.announcement.MAnnouncement;
import za.co.sacpo.grantportal.xCubeMentor.announcement.MAnnouncementDetailsA;

public class FMAnnounceAdapter extends RecyclerView.Adapter<FMAnnounceAdapter.AHolder> implements BaseAdapter {
    private List<FMAnnounceObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    MAnnouncement baseActivity;
    protected DbHelper dbSetaObj;
    public FMAnnounceAdapter(List<FMAnnounceObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, String CAId, MAnnouncement baseActivity) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.CAId = CAId;
        this.baseActivity = baseActivity;
    }
    @Override
    public AHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_row, parent, false);
        return new AHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem = aObjList.get(holder.getAdapterPosition());

        holder.lblTitle.setText(aObjList.get(holder.getAdapterPosition()).aNTitle3);
        holder.lblTitle.setText(aObjList.get(holder.getAdapterPosition()).aNTitle3);
        String origStr = aObjList.get(holder.getAdapterPosition()).aSData4;
        holder.lblDate.setText(origStr);
        holder.lblCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                String m_u_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("m_u_id", m_u_id);
                inputUri.putString("student_id",baseActivity.getStudentId());
                inputUri.putString("student_name",baseActivity.getStudentName());
                Context context = v.getContext();
                Intent intent = new Intent(context, MAnnouncementDetailsA.class);
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

    public String getLabelFromDb(String inputLabel, int resId) {
        String ValueLabel = baseActivityContext.getString(resId);
        dbSetaObj = DbHelper.getInstance(baseActivityContext);
        Cursor res = dbSetaObj.getDataValueByName(inputLabel);
        if (res.getCount() > 0) {
            try {

                while (res.moveToNext())

                {
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
    public class AHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public FMAnnounceObj.Item hItem;
        public final TextView lblTitle, lblDate;
        public final CardView lblCardView;
        public final LinearLayout lblHeadView, lblBodyView, lblFooterView;

        public AHolder(View itemView) {
            super(itemView);
            hView = itemView;
            lblTitle = (TextView) itemView.findViewById(R.id.lblTitle);
            lblDate = (TextView) itemView.findViewById(R.id.lblDate);
            lblCardView = (CardView) itemView.findViewById(R.id.cardView);
            lblHeadView = (LinearLayout) itemView.findViewById(R.id.head_container);
            lblBodyView = (LinearLayout) itemView.findViewById(R.id.body_container);
            lblFooterView = (LinearLayout) itemView.findViewById(R.id.footer_container);
        }
    }
}
