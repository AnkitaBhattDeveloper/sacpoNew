package za.co.sacpo.grant.xCubeLib.session;
import android.content.Context;
import android.content.SharedPreferences;

public class OlumsSETAMSession {
    private SharedPreferences prefs;
    private Context context;
    public static final String OLUMS_SETA_M_SESSION= "OSPUSM" ;
    public static final String SM_SETA_NAME = "SM_SETA_NAME" ;
    public static final String SM_SETA_ID = "SM_SETA_ID" ;

    public OlumsSETAMSession(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(OLUMS_SETA_M_SESSION, Context.MODE_PRIVATE);
    }

    public String getSmSetaName(){
        return prefs.getString(SM_SETA_NAME,null);
    }
    public void setSmSetaName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(SM_SETA_NAME, value);
        edits.apply();
    }
    public String getSmSetaId(){
        return prefs.getString(SM_SETA_ID,null);
    }
    public void setSmSetaId(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(SM_SETA_ID, value);
        edits.apply();
    }
    public void clearSETAMSession(){
        prefs.edit().clear().apply();
    }
}
