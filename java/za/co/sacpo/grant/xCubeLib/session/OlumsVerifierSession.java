package za.co.sacpo.grant.xCubeLib.session;
import android.content.Context;
import android.content.SharedPreferences;

public class OlumsVerifierSession {
    private SharedPreferences prefs;
    private Context context;
    public static final String OLUMS_VERIFI_SESSION= "OSPUGA" ;
    public static final String VERIFIER_LEAD_EMPLOYER = "VERIFIER_LEAD_EMPLOYER" ;
    public static final String VERIFIER_LEAD_EMPLOYER_SDL = "VERIFIER_LEAD_EMPLOYER_SDL" ;

    public OlumsVerifierSession(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(OLUMS_VERIFI_SESSION, Context.MODE_PRIVATE);
    }

    public String getVerifierLeadEmployer(){
        return prefs.getString(VERIFIER_LEAD_EMPLOYER,null);
    }
    public void setVerifierLeadEmployer(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(VERIFIER_LEAD_EMPLOYER, value);
        edits.apply();
    }
    public String getVerifierLeadEmployerSdl(){
        return prefs.getString(VERIFIER_LEAD_EMPLOYER_SDL,null);
    }
    public void setVerifierLeadEmployerSdl(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(VERIFIER_LEAD_EMPLOYER_SDL, value);
        edits.apply();
    }
    public void clearVerifierSession(){
        prefs.edit().clear().apply();
    }
}
