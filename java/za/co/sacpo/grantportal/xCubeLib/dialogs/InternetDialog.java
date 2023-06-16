package za.co.sacpo.grantportal.xCubeLib.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;

public class InternetDialog {
    public static boolean isNetDialogShowing = false;
    public static androidx.appcompat.app.AlertDialog internetDialog;
    public static boolean isInternetReceiver;

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public static void removeInternetDialog() {
        if ((internetDialog != null) && (internetDialog.isShowing())) {
            internetDialog.dismiss();
            isNetDialogShowing = false;
            internetDialog = null;
        }
    }
    public static void showInternetDialog(final Context context, final AppCompatActivity activityClass, String title, String message, String threeGButton, String wifiButton, String tryAgainButton) {
        isNetDialogShowing = true;
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setPositiveButton(threeGButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                if(isNetworkConnected(context)){
                    removeInternetDialog();
                }
                else {
                    Intent newIntent = new Intent("android.settings.SETTINGS");
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(newIntent);
                }
            }
        }).setNeutralButton(wifiButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                if(isNetworkConnected(context)){
                    removeInternetDialog();
                }
                else {
                    Intent newIntent = new Intent("android.settings.WIFI_SETTINGS");
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(newIntent);
                }
            }
        }).setNegativeButton(tryAgainButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                if(isNetworkConnected(context)){
                    removeInternetDialog();
                }
                else {
                    removeInternetDialog();
                    activityClass.finish();
                }
            }
        });
        internetDialog = localBuilder.create();
        internetDialog.show();
    }

}