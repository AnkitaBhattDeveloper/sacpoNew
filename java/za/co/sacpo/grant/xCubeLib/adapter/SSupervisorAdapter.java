package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dataObj.SSupervisorCommentObj;


public class SSupervisorAdapter extends RecyclerView.Adapter<SSupervisorAdapter.NGHolder> {
    private List<SSupervisorCommentObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    boolean full;
    Bitmap bitmap;
    boolean isMentorShow = false;
    ArrayList<String> mentors = new ArrayList<String>();
    public SSupervisorAdapter(List<SSupervisorCommentObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, String CAId) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.CAId = CAId;


    }
    @Override
    public SSupervisorAdapter.NGHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.s_supervisor_comments_row, parent, false);
        return new SSupervisorAdapter.NGHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final SSupervisorAdapter.NGHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();

        holder.hItem=aObjList.get(holder.getAdapterPosition());

        holder.lblRef.setText(aObjList.get(holder.getAdapterPosition()).lblRef3);
        holder.get_user_name_c.setText(aObjList.get(holder.getAdapterPosition()).getUserName4);

        String DATA = aObjList.get(holder.getAdapterPosition()).lblRef3;
        Spanned Message;
        if (Build.VERSION.SDK_INT >= 24) {
            Message = Html.fromHtml(DATA, Html.FROM_HTML_MODE_LEGACY);
        }
        else {
            Message = Html.fromHtml(DATA);
        }
        holder.lblRef.setText(Message);
        holder.lblDate.setText(aObjList.get(holder.getAdapterPosition()).getlblDate5);
        String mNameD = aObjList.get(holder.getAdapterPosition()).getUserName4;
        if(mentors.contains(mNameD)){
            isMentorShow = false;
        }
        else{
            isMentorShow = true;
            mentors.add(mNameD);
        }
        if(isMentorShow){
            holder.mentorNameHead.setVisibility(View.VISIBLE);
        }
        else{
            holder.mentorNameHead.setVisibility(View.GONE);
        }


     /*   holder.GetImage.setImageURI(Uri.parse(aObjList.get(holder.getAdapterPosition()).getImage6));

        holder.GetImage.setImageBitmap(bitmap);
*/
       /* holder.GetImage.setOnClickListener(new View.OnClickListener() {
=======
     //   holder.GetImage.setImageURI(Uri.parse(aObjList.get(holder.getAdapterPosition()).getImage6));

        //holder.GetImage.setImageBitmap(bitmap);

/*
        holder.GetImage.setOnClickListener(new View.OnClickListener() {
>>>>>>> 10db2b2903b0a9f2aff922c7821968f760f03fc1

            @Override
            public void onClick(View v) {

                Bundle inputUri = new Bundle();
                String dateInput = String.valueOf(holder.hItem.aId2);
                inputUri.putString("date_input", dateInput);
                inputUri.putString("generator", "114");
                Context context = v.getContext();
                Intent intent = new Intent(context, Fullscreen.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);

           }
        });*/

       }

    @Override

    public int getItemCount() {
        if(aObjList!=null) {
            return aObjList.size();
        }
        return 0;
    }

    public class NGHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public SSupervisorCommentObj.Item hItem;
        public final TextView lblDate,lblRef,get_user_name_c;
        public final CardView lblCardView;
        public final ImageView GetImage;
        public final LinearLayout mentorNameHead;

        public NGHolder(View itemView) {
            super(itemView);
            hView = itemView;
            lblRef= (TextView) itemView.findViewById(R.id.lblRef);

            lblDate= (TextView) itemView.findViewById(R.id.lblDate);
            get_user_name_c= (TextView) itemView.findViewById(R.id.get_user_name_c);
            mentorNameHead= (LinearLayout) itemView.findViewById(R.id.head_container);
            lblCardView = (CardView)itemView.findViewById(R.id.cardView);
            GetImage = (ImageView)itemView.findViewById(R.id.send_image01);



        }


    }
}