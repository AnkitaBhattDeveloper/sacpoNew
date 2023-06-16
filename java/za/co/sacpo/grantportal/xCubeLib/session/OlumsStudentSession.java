package za.co.sacpo.grantportal.xCubeLib.session;
import android.content.Context;
import android.content.SharedPreferences;

public class OlumsStudentSession {
    private SharedPreferences prefs;
    private Context context;
    public static final String OLUMS_STUDENT_SESSION= "OSPUS" ;
    public static final String U_GRANT_ID = "U_GRANT_ID" ;
    public static final String IS_ACTIVE_GRANT = "IS_GRANT_ACTIVE" ;
    public static final String STUDENT_S_NO = "S_S_NO" ;
    public static final String STUDENT_ID_NO = "S_ID_NO" ;
    public static final String STUDENT_Q_NAME = "S_Q_NAME" ;
    public static final String STUDENT_I_NAME = "S_I_NAME" ;
    public static final String M_GRANT_ID = "M_GRANT_ID";
    private String ATT_ID;

    public OlumsStudentSession(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(OLUMS_STUDENT_SESSION, Context.MODE_PRIVATE);
    }
    public  String getGrantId(){
        return prefs.getString(U_GRANT_ID,null);
    }
    public String getMGrantId(){
        return prefs.getString(M_GRANT_ID,null);
    }
    public void setGrantId(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_GRANT_ID, value);
        edits.apply();
    }
    public void setMGrantId(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(M_GRANT_ID, value);
        edits.apply();
    }
    public boolean getIsActiveGrant(){
        return prefs.getBoolean(IS_ACTIVE_GRANT,false);
    }
    public void setIsActiveGrant(Boolean value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putBoolean(IS_ACTIVE_GRANT,value);
        edits.apply();
    }
    public String getStudentSNo(){
        return prefs.getString(STUDENT_S_NO,null);
    }
    public void setStudentSNo(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(STUDENT_S_NO, value);
        edits.apply();
    }
    public String getStudentIdNo(){
        return prefs.getString(STUDENT_ID_NO,null);
    }
    public void setStudentIdNo(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(STUDENT_ID_NO, value);
        edits.apply();
    }
    public String getStudentQName(){
        return prefs.getString(STUDENT_Q_NAME,null);
    }
    public void setStudentQName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(STUDENT_Q_NAME, value);
        edits.apply();
    }
    public String getStudentIName(){
        return prefs.getString(STUDENT_I_NAME,null);
    }
    public void setStudentIName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(STUDENT_I_NAME, value);
        edits.apply();
    }
    public void clearStudentSession(){
        prefs.edit().clear().apply();
    }


}
