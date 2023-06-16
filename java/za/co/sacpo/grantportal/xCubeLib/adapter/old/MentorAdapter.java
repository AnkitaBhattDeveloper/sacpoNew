package za.co.sacpo.grantportal.xCubeLib.adapter.old;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.model.MentorData;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorHolder> {

    private Context context;
    private List<MentorData> mentorDataList;
    public MentorAdapter(Context context) {
        this.context = context;
        this.mentorDataList = mentorDataList;
    }
    @Override
    public MentorHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.mentor_row_list,parent,false);
        return new MentorHolder(v);
    }
    @Override
    public void onBindViewHolder(MentorHolder mentorHolder, int i) {
    }
    @Override
    public int getItemCount() {
        return 20;
    }
    public class MentorHolder extends RecyclerView.ViewHolder {
        TextView txMentorApprovedBy,txVerified,txMenDate;
        public MentorHolder(View itemView) {
            super(itemView);

            txMentorApprovedBy = (TextView) itemView.findViewById(R.id.txMentorApprovedBy);
            txVerified = (TextView) itemView.findViewById(R.id.txVerified);
            txMenDate = (TextView) itemView.findViewById(R.id.txMenDate);

        }
    }
}
