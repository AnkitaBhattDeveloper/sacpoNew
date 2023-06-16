package za.co.sacpo.grantportal.xCubeLib.adapter;

import android.content.res.Resources;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MTCObj;

/**
 * Created by xcube-06 on 8/8/18.
 */

public class MTCAdapter extends RecyclerView.Adapter<MTCAdapter.CommentHolder> {


    private List<MTCObj> mCommentsList;

    public MTCAdapter(List<MTCObj> mCommentsList) {
        this.mCommentsList = mCommentsList;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_listing_row, parent, false);
        return new CommentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {

        MTCObj mComments=mCommentsList.get(position); Resources res = holder.itemView.getContext().getResources();
        holder.hItem=mCommentsList.get(position);

    }

    @Override
    public int getItemCount() {
        if (mCommentsList!=null)
        {
            return mCommentsList.size();
        }
        return 0;
    }

    public class CommentHolder extends RecyclerView.ViewHolder {

        public Object hItem;

        public final TextView lblRef_m,get_user_name_c_m,lblDate_m,get_text_comment_m;
        public final ImageView get_user_image_c_m;


        public CommentHolder(View itemView) {
            super(itemView);

            lblRef_m = (TextView) itemView.findViewById(R.id.lblRef_m);
            get_user_name_c_m = (TextView) itemView.findViewById(R.id.get_user_name_c_m);
            lblDate_m = (TextView) itemView.findViewById(R.id.lblDate_m);
            get_text_comment_m = (TextView) itemView.findViewById(R.id.get_text_comment_m);
            get_user_image_c_m = (ImageView) itemView.findViewById(R.id.get_user_image_c_m);
        }
    }
}
