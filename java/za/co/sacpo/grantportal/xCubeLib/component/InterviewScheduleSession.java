package za.co.sacpo.grantportal.xCubeLib.component;

/**
 * Created by xtech on 10/4/17.
 */
import android.content.Context;
import android.content.SharedPreferences;
public class InterviewScheduleSession {


    private SharedPreferences prefs;
    private Context context;
    public static final String OLUMS_USER_SESSION= "ou" ;
    public static final String U_ID = "U_ID" ;
    public static final String DATE = "DATE";
    public static final String TIME = "TIME" ;
    public static final String VENUE = "VENUE" ;
    public static final String CONTACT_PERSON = "contact_person";
    public static final String INSTRUCTIONS = "INSTRUCTIONS";
    public static final String U_EMAIL = "U_EMAIL";
    //public static final String U_P_IMAGE_THUMB = "U_P_IMAGE_THUMB" ;
    public static final String U_TYPE_ID = "U_TYPE_ID" ;
    // public static final String U_Mobile = "U_MOBILE" ;
    public static final String U_STATUS = "U_STATUS" ;
    public static final String U_COLLEGE_2_UNIVERSITY_1 = "U_COLLEGE_2_UNIVERSITY_1" ;
    public static final String U_COLLEGE_ID = "U_COLLEGE_ID";
    public static final String U_UNIVERSITY_ID = "U_UNIVERSITY_ID" ;
    public static final String U_QUALIFICATION_ID = "U_QUALIFICATION_ID" ;
    public static final String U_TOKEN = "TOKEN" ;
    public static final String HAS_SESSION= "is_login";
    public static final String CAN_APPLY= "CAN_APPLY" ;
    public InterviewScheduleSession(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(OLUMS_USER_SESSION, Context.MODE_PRIVATE);
    }
    public void setUserId(int value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putInt(U_ID, value);
        edits.apply();
    }
    public int getUserId(){
        return prefs.getInt(U_ID,0);
    }
    public void setToken(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_TOKEN, value);
        edits.apply();
    }
    public String getCanApply(){
        return prefs.getString(CAN_APPLY,null);
    }
    public void setCanApply(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toLowerCase();
        edits.putString(CAN_APPLY, newVal);
        edits.apply();
    }
    public String getToken(){
        return prefs.getString(U_TOKEN,null);
    }
    public void setUserEmail(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toLowerCase();
        edits.putString(U_EMAIL, newVal);
        edits.apply();
    }
    public String getDate(){
        return prefs.getString(DATE,null);
    }
    public void setDate(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toUpperCase();
        edits.putString(DATE, newVal);
        edits.apply();
    }
    public String getUserType(){
        return prefs.getString(U_TYPE_ID,null);
    }
    public void setUserType(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toUpperCase();
        edits.putString(U_TYPE_ID, newVal);
        edits.apply();
    }
    public String getTime(){
        return prefs.getString(TIME,null);
    }
    public void setTime(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(TIME, value);
        edits.apply();
    }

    public String getUserQualification(){
        return prefs.getString(U_QUALIFICATION_ID,null);
    }
    public void setUserQualification(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_QUALIFICATION_ID, value);
        edits.apply();
    }

    public String getUserStatus(){
        return prefs.getString(U_STATUS,null);
    }
    public void setUserStatus(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_STATUS, value);
        edits.apply();
    }

    public String getVenue(){
        return prefs.getString(VENUE,null);
    }
    public void setVenue(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toUpperCase();
        edits.putString(VENUE, newVal);
        edits.apply();
    }

    public String getContectPerson(){
        return prefs.getString(CONTACT_PERSON,null);
    }
    public void setContectPerson(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(CONTACT_PERSON, value);
        edits.apply();
    }
    public String getInstructions(){
        return prefs.getString(INSTRUCTIONS,null);
    }
    public void setInstruction(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(INSTRUCTIONS, value);
        edits.apply();
    }
    public String getuCollege2University1(){
        return prefs.getString(U_COLLEGE_2_UNIVERSITY_1,null);
    }
    public void setuCollegeId(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_COLLEGE_ID, value);
        edits.apply();
    }
    public String getuCollegeId(){
        return prefs.getString(U_COLLEGE_ID,null);
    }
    public void setuUniversityId(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_UNIVERSITY_ID, value);
        edits.apply();
    }
    public String getuUniversityId(){
        return prefs.getString(U_UNIVERSITY_ID,null);
    }

    public boolean getHasSession(){
        return prefs.getBoolean(HAS_SESSION,false);
    }
    public void setHasSession(Boolean value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putBoolean(HAS_SESSION,value);
        edits.apply();
    }
    public void clearUserSession(){
        prefs.edit().clear().apply();
    }
}
