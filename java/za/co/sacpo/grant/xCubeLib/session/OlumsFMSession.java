package za.co.sacpo.grant.xCubeLib.session;
import android.content.Context;
import android.content.SharedPreferences;

public class OlumsFMSession {
    private SharedPreferences prefs;
    private Context context;
    public static final String OLUMS_GA_SESSION= "OSPUGA" ;
    public static final String GA_LEAD_EMPLOYER = "GA_LEAD_EMPLOYER" ;
    public static final String GA_LEAD_EMPLOYER_SDL = "GA_LEAD_EMPLOYER_SDL" ;

    public OlumsFMSession(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(OLUMS_GA_SESSION, Context.MODE_PRIVATE);
    }

    public String getFMLeadEmployer(){
        return prefs.getString(GA_LEAD_EMPLOYER,null);
    }
    public void setFMLeadEmployer(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(GA_LEAD_EMPLOYER, value);
        edits.apply();
    }
    public String getFMLeadEmployerSdl(){
        return prefs.getString(GA_LEAD_EMPLOYER_SDL,null);
    }
    public void setFMLeadEmployerSdl(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(GA_LEAD_EMPLOYER_SDL, value);
        edits.apply();
    }
    public void clearFMSession(){
        prefs.edit().clear().apply();
    }
}
