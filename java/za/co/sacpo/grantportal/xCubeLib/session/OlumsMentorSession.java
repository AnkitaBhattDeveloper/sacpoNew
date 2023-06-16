package za.co.sacpo.grantportal.xCubeLib.session;
import android.content.Context;
import android.content.SharedPreferences;

public class OlumsMentorSession {
    private SharedPreferences prefs;
    private Context context;
    public static final String OLUMS_MENTOR_SESSION= "OSPUM" ;
    public static final String M_HOST_EMPLOYER = "M_HOST_EMPLOYER" ;
    public static final String M_HOST_EMPLOYER_SDL = "M_HOST_EMPLOYER_SDL" ;

    public OlumsMentorSession(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(OLUMS_MENTOR_SESSION, Context.MODE_PRIVATE);
    }

    public String getHostEmployer(){
        return prefs.getString(M_HOST_EMPLOYER,null);
    }
    public void setHostEmployer(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(M_HOST_EMPLOYER, value);
        edits.apply();
    }
    public String getEmployerSDL(){
        return prefs.getString(M_HOST_EMPLOYER_SDL,null);
    }
    public void setEmployerSDL(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(M_HOST_EMPLOYER_SDL, value);
        edits.apply();
    }
    public void clearMentorSession(){
        prefs.edit().clear().apply();
    }
}
