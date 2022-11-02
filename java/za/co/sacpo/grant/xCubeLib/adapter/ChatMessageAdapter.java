package za.co.sacpo.grant.xCubeLib.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.component.CropCircleTransformation;
import za.co.sacpo.grant.xCubeLib.dataObj.ChatMessageObj;


//Class extending RecyclerviewAdapter
public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {

    //user id;
    private int userId;
    private Context context;
    private int SELF = 786;
    private ArrayList<ChatMessageObj> messages;
    private String pdf_url; //= "https://www.adobe.com/content/dam/acom/en/devnet/acrobat/pdfs/pdf_open_parameters.pdf";

    //Constructor
    public
    ChatMessageAdapter(Context context, ArrayList<ChatMessageObj> messages, int userId){
        this.userId = userId;
        this.messages = messages;
        this.context = context;
    }

    //IN this method we are tracking the self message
    @Override
    public int getItemViewType(int position) {
        //getting message object of current position
        ChatMessageObj message = messages.get(position);

        //If its owner  id is  equals to the logged in user id
        if (message.getUsersId() == userId) {
            //Returning self
            return SELF;
        }
        //else returning position
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Creating view
        View itemView;
        //if view type is self
        if (viewType == SELF) {

            //Inflating the layout self
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_thread, parent, false);
        }


        else {
            //else inflating the layout others
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_thread_other, parent, false);
        }



        //returing the view
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //Adding messages to the views
        ChatMessageObj message = messages.get(position);
        //holder.textViewMessage.setText(message.getMessage());
        holder.webViewMessage.loadData(message.getMessage(), "text/html", "UTF-8");
        final WebSettings webSettings = holder.webViewMessage.getSettings();
        Resources res = context.getResources();
        float fontSize = res.getDimension(R.dimen.tiniestTextSize);
        webSettings.setDefaultFontSize((int) fontSize);
        holder.webViewMessage.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        holder.webViewMessage.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        //holder.webViewMessage.setAlpha(0.2f);
        holder.webViewMessage.setBackgroundColor(Color.TRANSPARENT);
        holder.webViewMessage.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
       // holder.textViewTime.setText(message.getName() + ", " + message.getSentAt());
        holder.textViewTime.setText(message.getSentAt());
        holder.webViewMessage.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);
                holder.webview_container.requestLayout();
            }
        });


        String thumbMy = message.getMyImage();
        String thumbOther = message.getImage();
        final String thumbLink = message.getOtherImage();
        final String filepdf = message.getfilepdf();
        final String extension = message.Extension();
        if (!thumbMy.isEmpty() && thumbOther != null) {
            Picasso.get().load(thumbMy).resize(130, 130).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).placeholder(R.mipmap.user).error(R.mipmap.user).transform(new CropCircleTransformation()).into(holder.imageViewMy);
            //    Picasso.get().load(messages.get(position).getMyImage()).resize(120, 60).into(holder.imageViewMy);
        }
        if (thumbOther instanceof String) {
            if (thumbOther instanceof String) {
                if (!thumbOther.isEmpty() && thumbOther != null) {
                    Picasso.get().load(thumbOther).resize(130, 130).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).placeholder(R.mipmap.user).error(R.mipmap.user).transform(new CropCircleTransformation()).into(holder.imageViewOther);
                }
            }
        }
        if (thumbLink instanceof String) {
            if (thumbLink instanceof String) {
                if (!thumbLink.isEmpty() && thumbLink != null) {


                    if (extension.equals("0")) {
                        holder.chat_imageView_imageAttachment_other.setVisibility(View.VISIBLE);
                        holder.btn_attachment.setVisibility(View.GONE);
                        Picasso.get().load(thumbLink).fit().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).
                                placeholder(R.mipmap.user).error(R.mipmap.user).
                                into(holder.chat_imageView_imageAttachment_other);

                        holder.chat_imageView_imageAttachment_other.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                                dialog.setContentView(R.layout.view_image_popup);
                                dialog.setTitle("Image View");


                                Button btn_dismiss = (Button) dialog.findViewById(R.id.btn_dismiss);
                                ImageView iv_getImg = (ImageView) dialog.findViewById(R.id.iv_getImg);


                                Picasso.get().load(thumbLink).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).placeholder(R.mipmap.user).error(R.mipmap.user).into(iv_getImg);
                                btn_dismiss.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();

                                    }
                                });
                                dialog.show();
                            }
                        });

                    } else if (extension.equals("1")) {

                        pdf_url = filepdf;

                        holder.chat_imageView_imageAttachment_other.setVisibility(View.GONE);
                        holder.btn_attachment.setVisibility(View.VISIBLE);
                        holder.btn_attachment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);

                                intent.setDataAndType(Uri.parse("http://docs.google.com/viewer?url=" + pdf_url), "text/html");

                                context.startActivity(intent);
                            }
                        });

                    } else {


                        holder.chat_imageView_imageAttachment_other.setVisibility(View.GONE);
                        holder.btn_attachment.setVisibility(View.GONE);
                    }
                }
            }
        }
    }
    //Log.i("chat_image",message.getOtherImage());


        /* holder.textViewMessage.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onLongClick(View view) {
                holder.our_chat_box.setBackgroundColor(R.color.summer);
                return false;
            }
        });*/


    private Context context() {
        return  null;
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    //Initializing views
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMessage;
        public WebView webViewMessage;
        public TextView textViewTime;
        public LinearLayout our_chat_box,webview_container;
        public Button btn_attachment;
        public ImageView chat_imageView_imageAttachment_other,imageViewMy,imageViewOther;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewMessage = (TextView) itemView.findViewById(R.id.textViewMessage);
            webViewMessage = (WebView) itemView.findViewById(R.id.webViewMessage);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
            webview_container = (LinearLayout) itemView.findViewById(R.id.webview_container);
            our_chat_box = (LinearLayout) itemView.findViewById(R.id.chat_layout_S);
            //chat_imageView_imageAttachment = (ImageView) itemView.findViewById(R.id.chat_imageView_imageAttachment);
            imageViewOther = (ImageView) itemView.findViewById(R.id.imageViewOther);
            btn_attachment = (Button) itemView.findViewById(R.id.btn_attachment);
            imageViewMy = (ImageView) itemView.findViewById(R.id.imageViewMy);
            chat_imageView_imageAttachment_other = (ImageView) itemView.findViewById(R.id.chat_imageView_imageAttachment_other);

        }
    }
}