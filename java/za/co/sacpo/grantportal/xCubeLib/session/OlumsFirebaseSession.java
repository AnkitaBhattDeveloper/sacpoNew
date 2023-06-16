package za.co.sacpo.grantportal.xCubeLib.session;
import android.content.Context;
import android.content.SharedPreferences;

public class OlumsFirebaseSession {
    private SharedPreferences prefs;
    private Context context;
    public static final String FIREBASE_CLOUD_MESSAGING = "fcm";
    public static final String SET_NOTIFY = "set_notify";
    public static final String OLUMS_SESSION = "OLUMS_SESSION" ;
    public static final String OLUMS_USER_SESSION= "OLUMS_USER_SESSION" ;
    public static final String DOMAIN_INITIAL_URL = "D_I_U";
    public static final String U_ID = "U_ID" ;

    public OlumsFirebaseSession(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(FIREBASE_CLOUD_MESSAGING, Context.MODE_PRIVATE);
    }
    public void saveNotificationSubscription(boolean value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putBoolean(SET_NOTIFY, value);
        edits.apply();
    }
    public boolean hasUserSubscribeToNotification(){
        return prefs.getBoolean(SET_NOTIFY, false);
    }
    public void clearAllSubscriptions(){
        prefs.edit().clear().apply();
    }
}
