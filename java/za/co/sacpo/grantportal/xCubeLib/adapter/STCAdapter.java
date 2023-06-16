package za.co.sacpo.grantportal.xCubeLib.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.dataObj.TCObj;

public class STCAdapter extends RecyclerView.Adapter<STCAdapter.NGHolder> {
    private List<TCObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    boolean full;
    Bitmap bitmap;
    String imageUrl,path;
    public STCAdapter(List<TCObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, String CAId) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.CAId = CAId;


    }
    @Override
    public STCAdapter.NGHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.t_comments_row, parent, false);
        return new STCAdapter.NGHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final STCAdapter.NGHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();

        holder.hItem=aObjList.get(holder.getAdapterPosition());
        holder.lblRef.setText(aObjList.get(holder.getAdapterPosition()).lblRef3);
        holder.get_user_name_c.setText(aObjList.get(holder.getAdapterPosition()).getUserName4);
        String DATA = aObjList.get(holder.getAdapterPosition()).lblRef3;


        String path = aObjList.get(holder.getAdapterPosition()).getImage6;
        if((path==null)||(path.isEmpty()))
        {
            //holder.GetImage.setImageResource(R.drawable.place_holder);
            holder.GetImage.setVisibility(View.GONE);
        }
        else {
            holder.GetImage.setVisibility(View.VISIBLE);
            Picasso.get().load(aObjList.get(holder.getAdapterPosition()).getGetImage6()).resize(120, 60).into(holder.GetImage);


        }
        Spanned Message;
        if (Build.VERSION.SDK_INT >= 24) {
            Message = Html.fromHtml(DATA, Html.FROM_HTML_MODE_LEGACY);
        }
        else {
            Message = Html.fromHtml(DATA);
        }

        holder.lblRef.setText(Message);
        holder.lblDate.setText(aObjList.get(holder.getAdapterPosition()).getlblDate5);



        holder.GetImage.setImageURI(Uri.parse(aObjList.get(holder.getAdapterPosition()).getGetImage6()));
        holder.GetImage.setImageBitmap(bitmap);

      /*  holder.GetImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle inputUri = new Bundle();
                String dateInput = String.valueOf(holder.hItem.aId2);
                inputUri.putString("date_input", dateInput);
                String get_image = String.valueOf(holder.hItem.getImage6);
                inputUri.putString("get_image", get_image);
                inputUri.putString("generator", "114");
                Context context = v.getContext();
                Intent intent = new Intent(context, Fullscreen.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
              }
          });*/

        /*

        holder.GetImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent _intent = new Intent(context, Fullscreen.class);
                Bitmap _bitmap; // your bitmap
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, _bs);
                _intent.putExtra("byteArray", _bs.toByteArray());
                context.startActivity(_intent);
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
        public TCObj.Item hItem;
        public final TextView lblDate,lblRef,get_user_name_c;
        public final CardView lblCardView;
        public final ImageView GetImage;

        public NGHolder(View itemView) {
            super(itemView);
            hView = itemView;
            lblRef= (TextView) itemView.findViewById(R.id.lblRef);

            lblDate= (TextView) itemView.findViewById(R.id.lblDate);
            get_user_name_c= (TextView) itemView.findViewById(R.id.get_user_name_c);

            lblCardView = (CardView)itemView.findViewById(R.id.cardView);
            GetImage = (ImageView)itemView.findViewById(R.id.send_image01);
        }
    }
}