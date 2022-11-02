package za.co.sacpo.grant.xCubeLib.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Log;

public class GpsDialog {

    public static boolean isGpsDialogShowing = false;
    public static androidx.appcompat.app.AlertDialog gpsDialog;


    public static void removeGpsDialog() {
        Log.i("OSG","removeGpsDialog"+" GpsDialog init");
        if ((gpsDialog != null) && (gpsDialog.isShowing())) {
            gpsDialog.dismiss();
            Log.i("OSG","removeGpsDialog"+" GpsDialog REMOVE");
            isGpsDialogShowing = false;
            gpsDialog = null;
        }
    }
    public static void showGpsDialog(final Context context, final AppCompatActivity activityClass, String title, String message, String gpsButton) {
        isGpsDialogShowing = true;
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setPositiveButton(gpsButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
              //  Intent newIntent = new Intent("android.settings.SECURITY_SETTINGS");
                //newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Intent newIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(newIntent);

            }
        }).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeGpsDialog();
            }
        });
        gpsDialog = localBuilder.create();
        gpsDialog.show();
    }

}