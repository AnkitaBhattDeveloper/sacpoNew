package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dataObj.SChatListObj;
import za.co.sacpo.grant.xCubeStudent.messages.SChatA;
import za.co.sacpo.grant.xCubeStudent.messages.SChatHeadsA;

public class SChatListAdapter extends RecyclerView.Adapter<SChatListAdapter.ChatHolder> {
    private List<SChatListObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;

    //Todo: Table Width Problem
    public SChatListAdapter(List<SChatListObj.Item> aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
    }

    @Override
    public SChatListAdapter.ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_row, parent, false);
        return new SChatListAdapter.ChatHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SChatListAdapter.ChatHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem = aObjList.get(holder.getAdapterPosition());
        String isGroup = aObjList.get(holder.getAdapterPosition()).mcDirect5;
        holder.Txt_user_name.setText(aObjList.get(holder.getAdapterPosition()).mcGroup6);
        holder.Txt_chat_count.setText(aObjList.get(holder.getAdapterPosition()).mcChatCount4);
        holder.chat_count_container.setVisibility(View.GONE);
        int intChatCount = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).mcChatCount4);
        if(intChatCount>0){
            holder.chat_count_container.setVisibility(View.VISIBLE);

        }

        if (isGroup.equals("1")) {
            holder.tRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String fId = String.valueOf(holder.hItem.aId2);
                    String fIsGroup = String.valueOf(holder.hItem.mcDirect5);
                    String fName = String.valueOf(holder.hItem.mcUserName3);
                    inputUri.putString("fId", fId);
                    inputUri.putString("fName", fName);
                    inputUri.putString("fIsGroup",fIsGroup);

                    Context context = view.getContext();

                    Intent intent = new Intent(context, SChatA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
        } else {
            holder.tRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String fId = String.valueOf(holder.hItem.aId2);
                    String fIsGroup = String.valueOf(holder.hItem.mcGroup6);
                    String fName = String.valueOf(holder.hItem.mcUserName3);


                    inputUri.putString("user_type", fId);
                    inputUri.putString("generator", "431");

                    Context context = view.getContext();
                    Intent intent = new Intent(context, SChatHeadsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (aObjList != null) {
            return aObjList.size();
        }
        return 0;
    }
    public class ChatHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public SChatListObj.Item hItem;

        public final LinearLayout tRow, layout_two;
        public final TextView Txt_user_name, Txt_chat_count;
        public final ImageView chat_count_back;
        public final FrameLayout chat_count_container;
        public ChatHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow = (LinearLayout) itemView.findViewById(R.id.m_ChatList_Row);
            layout_two = (LinearLayout) itemView.findViewById(R.id.chat_view_divider);

            Txt_user_name = (TextView) itemView.findViewById(R.id.Txt_user_name);
            Txt_chat_count = (TextView) itemView.findViewById(R.id.Txt_chat_count);
            chat_count_back = (ImageView) itemView.findViewById(R.id.chat_count_back);
            chat_count_container = (FrameLayout)itemView.findViewById(R.id.chat_count_container);
        }
    }
}