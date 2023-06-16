package za.co.sacpo.grantportal.xCubeLib.session;
import android.content.Context;
import android.content.SharedPreferences;

public class OlumsGMSession {
    private SharedPreferences prefs;
    private Context context;
    public static final String OLUMS_GM_SESSION= "OSPUGA" ;
    public static final String GM_LEAD_EMPLOYER = "GM_LEAD_EMPLOYER" ;
    public static final String GM_LEAD_EMPLOYER_SDL = "GM_LEAD_EMPLOYER_SDL" ;

    public OlumsGMSession(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(OLUMS_GM_SESSION, Context.MODE_PRIVATE);
    }

    public String getGMLeadEmployer(){
        return prefs.getString(GM_LEAD_EMPLOYER,null);
    }
    public void setGMLeadEmployer(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(GM_LEAD_EMPLOYER, value);
        edits.apply();
    }
    public String getGMLeadEmployerSdl(){
        return prefs.getString(GM_LEAD_EMPLOYER_SDL,null);
    }
    public void setGMLeadEmployerSdl(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(GM_LEAD_EMPLOYER_SDL, value);
        edits.apply();
    }
    public void clearGMSession(){
        prefs.edit().clear().apply();
    }
}
