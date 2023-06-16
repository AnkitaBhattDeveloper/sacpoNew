package za.co.sacpo.grantportal.xCubeLib.session;
import android.content.Context;
import android.content.SharedPreferences;

public class OlumsGrantSession {
    private SharedPreferences prefs;
    private Context context;
    public static final String OLUMS_STUDENT_SESSION= "OSPSG" ;
    public static final String G_ID = "G_ID" ;
    public static final String G_START_DATE = "G_START_DATE" ;
    public static final String G_END_DATE= "G_END_DATE" ;
    public static final String G_STATUS= "G_STATUS" ;
    public static final String G_NAME = "G_NAME" ;
    public static final String G_SETA_NAME = "G_SETA_NAME" ;
    public static final String G_HOST_EMP_NAME = "G_HOST_EMP" ;
    public static final String G_HOST_EMP_SDL = "G_HOST_EMP_SDL" ;
    public static final String G_MENTOR_NAME = "G_MENTOR_NAME" ;
    public static final String G_MENTOR_CELL = "G_MENTOR_CELL" ;
    public static final String G_MENTOR_EMAIL = "G_MENTOR_EMAIL" ;
    public static final String G_MENTOR_PIC = "G_MENTOR_PIC" ;
    public static final String G_LEM_NAME = "G_LEM_NAME" ;
    public static final String G_SETA_MANAGER= "G_SETA_MANAGER" ;
    public static final String G_MENTOR_ID = "G_MENTOR_ID" ;
    public static final String G_LEM_ID = "G_LEM_ID" ;
    public static final String G_LEM_MANAGER_ID = "G_LEM_MANAGER_ID" ;
    public static final String G_LEM_MANAGER_NAME = "G_LEM_MANAGER_NAME" ;
    public static final String G_SETA_MANAGER_ID= "G_MANAGER_ID" ;
    public static final String G_MONTHLY_STIPEND = "G_MONTHLY_STIPEND" ;
    public static final String G_DAILY_DEDUCTION = "G_DAILY_DEDUCTION" ;
    public OlumsGrantSession(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(OLUMS_STUDENT_SESSION, Context.MODE_PRIVATE);
    }
    public String getGrantId(){
        return prefs.getString(G_ID,null);
    }
    public void setGrantId(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_ID, value);
        edits.apply();
    }

    public String getGrantSDate(){
        return prefs.getString(G_START_DATE,null);
    }
    public void setGrantSDate(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_START_DATE, value);
        edits.apply();
    }
    public String getGrantEDate(){
        return prefs.getString(G_END_DATE,null);
    }
    public void setGrantEDate(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_END_DATE, value);
        edits.apply();
    }
    public String getGrantStatus(){
        return prefs.getString(G_STATUS,null);
    }
    public void setGrantStatus(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_STATUS, value);
        edits.apply();
    }
    public String getGrantName(){
        return prefs.getString(G_NAME,null);
    }
    public void setGrantName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_NAME, value);
        edits.apply();
    }
    public String getGrantSetaName(){
        return prefs.getString(G_SETA_NAME,null);
    }
    public void setGrantSetaName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_SETA_NAME, value);
        edits.apply();
    }
    public String getGrantHostEmpName(){
        return prefs.getString(G_HOST_EMP_NAME,null);
    }
    public void setGrantHostEmpName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_HOST_EMP_NAME, value);
        edits.apply();
    }
    public String getGrantHostEmpSDL(){
        return prefs.getString(G_HOST_EMP_SDL,null);
    }
    public void setGrantHostEmpSDL(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_HOST_EMP_SDL, value);
        edits.apply();
    }
    public String getGrantMentorName(){

        return prefs.getString(G_MENTOR_NAME,null);
    }
    public void setGrantMentorName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_MENTOR_NAME, value);
        edits.apply();
    }
    public String getGrantMentorEmail(){
        return prefs.getString(G_MENTOR_EMAIL,null);
    }
    public void setGrantMentorEmail(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_MENTOR_EMAIL, value);
        edits.apply();
    }
    public String getGrantMentorPic(){
        return prefs.getString(G_MENTOR_PIC,null);
    }
    public void setGrantMentorPic(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_MENTOR_PIC, value);
        edits.apply();
    }
    public String getGrantMentorCell(){
        return prefs.getString(G_MENTOR_CELL,null);
    }
    public void setGrantMentorCell(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_MENTOR_CELL, value);
        edits.apply();
    }
    public String getGrantLEMName(){
        return prefs.getString(G_LEM_NAME,null);
    }
    public void setGrantLEMName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_LEM_NAME, value);
        edits.apply();
    }
    public String getSETAManagerName(){
        return prefs.getString(G_SETA_MANAGER,null);
    }
    public void setSETAManagerName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_SETA_MANAGER, value);
        edits.apply();
    }
    public String getGrantMentorId(){
        return prefs.getString(G_MENTOR_ID,null);
    }
    public void setGrantMentorId(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_MENTOR_ID, value);
        edits.apply();
    }
    public String getGrantLEMId(){
        return prefs.getString(G_LEM_ID,null);
    }
    public void setGrantLEMId(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_LEM_ID, value);
        edits.apply();
    }
    public String getSETAManagerId(){
        return prefs.getString(G_SETA_MANAGER_ID,null);
    }
    public void setSETAManagerId(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_SETA_MANAGER_ID, value);
        edits.apply();
    }
    public String getLEMManageName(){
        return prefs.getString(G_LEM_MANAGER_NAME,null);
    }
    public void setLEMManageName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_LEM_MANAGER_NAME, value);
        edits.apply();
    }
    public String getLEMManagerId(){
        return prefs.getString(G_LEM_MANAGER_ID,null);
    }
    public void setLEMManagerId(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_LEM_MANAGER_ID, value);
        edits.apply();
    }

    public String getGrantMonthlyStipend(){
        return prefs.getString(G_MONTHLY_STIPEND,null);
    }
    public void setGrantMonthlyStipend(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_MONTHLY_STIPEND, value);
        edits.apply();
    }
    public String getGrantDailyDeduction(){
        return prefs.getString(G_DAILY_DEDUCTION,null);
    }
    public void setGrantDailyDeduction(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(G_DAILY_DEDUCTION, value);
        edits.apply();
    }

    public void clearGrantSession(){
        prefs.edit().clear().apply();
    }
}
