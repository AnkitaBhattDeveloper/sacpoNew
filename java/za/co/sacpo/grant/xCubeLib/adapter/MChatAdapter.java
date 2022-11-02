package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.component.CropCircleTransformation;
import za.co.sacpo.grant.xCubeLib.dataObj.SChatObj;
import za.co.sacpo.grant.xCubeMentor.messages.MChatA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class MChatAdapter extends RecyclerView.Adapter<MChatAdapter.AttHolder> {
    private List<SChatObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    //Todo: Table Width Problem
    public MChatAdapter(List<SChatObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
    }
    @Override
    public AttHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_head_row, parent, false);
        return new AttHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AttHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        String isGroup = aObjList.get(holder.getAdapterPosition()).aFIsGroup7;
        if(isGroup.equals("1")){
            holder.tRow.setBackgroundColor(res.getColor(R.color.row_group));
            holder.imgUserImage.setVisibility(View.VISIBLE);
            holder.imgUserImage.setImageResource(R.mipmap.ic_launcher_user_group);
            holder.layout_two.setVisibility(View.VISIBLE);

        }
        else{
            holder.imgUserImage.setVisibility(View.VISIBLE);
            holder.tRow.setBackgroundColor(res.getColor(R.color.row_one_o_one));
            holder.imgUserImage.setImageResource(R.mipmap.ic_launcher_user_icon);
            if(!TextUtils.isEmpty(aObjList.get(holder.getAdapterPosition()).aFImage4)) {
                Picasso.get().load(aObjList.get(holder.getAdapterPosition()).aFImage4).placeholder(R.mipmap.ic_launcher_user_icon).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.mipmap.ic_launcher_user_icon).transform(new CropCircleTransformation()).into(holder.imgUserImage);

            }
        }
        holder.txtUsername.setText(aObjList.get(holder.getAdapterPosition()).aFName3);
        holder.txtUserRole.setText(aObjList.get(holder.getAdapterPosition()).aFRole5);
        holder.txtInsitutionInfo.setText(aObjList.get(holder.getAdapterPosition()).aFInstitution10);
        holder.txtLastMessageTime.setText(aObjList.get(holder.getAdapterPosition()).aFLastChatTime9);
        if(aObjList.get(holder.getAdapterPosition()).aFUnreaChatCount8.equals("0")) {
            holder.imgChatCountsBack.setVisibility(View.GONE);
            holder.txtChatCounts.setText("");
        }
        else{
            holder.txtChatCounts.setText(aObjList.get(holder.getAdapterPosition()).aFUnreaChatCount8);
            holder.imgChatCountsBack.setVisibility(View.VISIBLE);
        }

        holder.tRow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                String fId = String.valueOf(holder.hItem.aId2);
                String fIsGroup = String.valueOf(holder.hItem.aFIsGroup7);
                String fName = String.valueOf(holder.hItem.aFName3);
                String fImage = String.valueOf(holder.hItem.aFImage4);

                inputUri.putString("fId", fId);
                inputUri.putString("fName", fName);
                inputUri.putString("fIsGroup",fIsGroup);
                inputUri.putString("fImage",fImage);
                inputUri.putString("generator", "406");
                inputUri.putString("user_type", "user_type");
                Context context = view.getContext();
                Intent intent = new Intent(context, MChatA.class);
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

    public class AttHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public SChatObj.Item hItem;
        public final LinearLayout tRow,layout_two;
        public final ImageView imgUserImage,imgChatCountsBack;
        public final TextView txtUsername,txtUserRole,txtInsitutionInfo,txtLastMessageTime,txtChatCounts;
        public AttHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (LinearLayout) itemView.findViewById(R.id.tRowC);
            layout_two= (LinearLayout) itemView.findViewById(R.id.chat_view_divider);
            imgUserImage= (ImageView) itemView.findViewById(R.id.imgUserImage);
            txtUsername= (TextView) itemView.findViewById(R.id.user_name);
            txtUserRole= (TextView) itemView.findViewById(R.id.role_name);
            txtInsitutionInfo= (TextView) itemView.findViewById(R.id.i_info);
            txtLastMessageTime= (TextView) itemView.findViewById(R.id.last_chatTime);
            txtChatCounts= (TextView) itemView.findViewById(R.id.chat_count);
            imgChatCountsBack= (ImageView) itemView.findViewById(R.id.chat_count_back);

        }
    }
}