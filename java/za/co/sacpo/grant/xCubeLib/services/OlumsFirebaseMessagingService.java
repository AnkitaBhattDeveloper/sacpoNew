package za.co.sacpo.grant.xCubeLib.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.component.NotificationHandler;


public class OlumsFirebaseMessagingService extends FirebaseMessagingService {
    public String PUSH_NOTIFICATION ="pushnotification";
    private static final String TAG = OlumsFirebaseMessagingService.class.getSimpleName();
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        if (remoteMessage.getData().size() > 0) {
            //Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            if (data.containsKey("click_action")) {
                Bundle arguments = new Bundle();
                int id =0;
                id = Integer.parseInt(data.get("item_id"));

                arguments.putInt("item_id",id );
               // ClickActionHelper.startActivity(data.get("click_action"), arguments, getApplicationContext());
            }
            if(data.containsKey("push_chat")){
                String titleH = data.get("fromName");
                //String message = data.get("message");
                String message = data.get("message_raw");
                String id = data.get("id");
                String type = data.get("type");
                String f_type = data.get("f_type");
                String gid = data.get("gid");

                String image = data.get("image_link");
                String pdf = data.get("image_link");
                String ext = data.get("file_type");
                if(f_type.isEmpty()){
                    f_type="1";
                }
                sendChatNotification(message,titleH,id,type,f_type,gid,image,pdf,ext);

            }
        }
        if (remoteMessage.getNotification() != null) {
            //Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            //Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getTitle());
            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            sendNotification(message,title);
        }


    }
    private void sendNotification(String message,String title) {
        Intent intent = new Intent("");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
    private void sendChatNotification(String message, String title, String id,String type,String f_type,String gid,String image,String pdf,String ext) {
        //Creating a broadcast intent
        Intent pushNotification = new Intent(PUSH_NOTIFICATION);
        //Adding notification data to the intent
        pushNotification.putExtra("message", message);
        pushNotification.putExtra("name", title);
        pushNotification.putExtra("id", id);
        pushNotification.putExtra("type", type);
        pushNotification.putExtra("gid", gid);
        pushNotification.putExtra("f_type", f_type);
        pushNotification.putExtra("image", image);
        pushNotification.putExtra("pdf", pdf);
        pushNotification.putExtra("ext", ext);
        //String f_type = data.get("f_type");
        //Log.i("ofm-t",title);
        //Log.i("ofm-m",message);
        //Log.i("ofm-i",id);
        //Log.i("ofm-t",type);
        NotificationHandler notificationHandler = new NotificationHandler(getApplicationContext());
        if (!NotificationHandler.isAppIsInBackground(getApplicationContext())) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
        } else {
            sendNotification(message,title);
            //notificationHandler.showNotificationMessage(title, message);
        }
    }
}