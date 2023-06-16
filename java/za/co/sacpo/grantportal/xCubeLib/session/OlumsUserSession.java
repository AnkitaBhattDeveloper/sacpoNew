package za.co.sacpo.grantportal.xCubeLib.session;
import android.content.Context;
import android.content.SharedPreferences;

public class OlumsUserSession {
    private SharedPreferences prefs;
    private Context context;
    public static final String OLUMS_USER_SESSION= "OSPU" ;
    public static final String U_ID = "U_ID" ;
    public static final String U_TOKEN = "TOKEN" ;
    public static final String U_EMAIL = "U_EMAIL";
    public static final String U_P_FNAME = "U_P_FNAME" ;
    public static final String U_P_SURNAME = "U_P_SURNAME";
    public static final String U_P_IMAGE_THUMB = "U_P_IMAGE_THUMB" ;
    public static final String U_TYPE_ID = "U_TYPE_ID" ;
    public static final String U_MOBILE = "U_MOBILE" ;
    public static final String U_MOBILE_STATUS = "U_MOBILE_STATUS";
    public static final String U_STATUS = "U_STATUS" ;
    public static final String U_EMAIL_VERIFIED = "U_IS_EMAIL_VERIFIED" ;
    public static final String U_TYPE_NAME = "U_TYPE_NAME" ;
    public static final String U_LAST_LOGIN = "U_LAST_LOGIN" ;
    public static final String HAS_SESSION= "is_login";

    public static final String B_NAME= "bankname";
    public static final String B_BRANCHCODE= "branchcode";
    public static final String B_BRANCH_NAME= "branch_name";
    public static final String B_ACCOUNT_NUMBER= "account_number";
    public static final String B_HOLDER_NAME= "account_holdername";

    /*menter sesion*/
    public static final String M_DATE= "date";
    public static final String M_HOLIDAYS= "holiday";

    /*Add Leave*/


    public static final String U_START_DATE= "Start_date";
    public static final String U_END_DATE= "End_date";
    public static final String U_LEAVE_REASON= "Leave_Reason";
    public static final String U_LEAVE_TYPE= "Leave_Type";

   /* Add work station*/


    public OlumsUserSession(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(OLUMS_USER_SESSION, Context.MODE_PRIVATE);
    }

    public int getUserId(){
        return prefs.getInt(U_ID,0);
    }
    public void setUserId(int value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putInt(U_ID, value);
        edits.apply();
    }

    public String getToken(){
        return prefs.getString(U_TOKEN,null);
    }
    public void setToken(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_TOKEN, value);
        edits.apply();
    }

    /*Edit Bank Details*/

    public String getUserBAccount(){
        return prefs.getString(B_ACCOUNT_NUMBER,null);
    }
    public void setUserBAccount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toUpperCase();
        edits.putString(B_ACCOUNT_NUMBER, newVal);
        edits.apply();
    }
    public String getUserBBranchName(){
        return prefs.getString(B_BRANCH_NAME,null);
    }
    public void setUserBBranchName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toUpperCase();
        edits.putString(B_BRANCH_NAME, newVal);
        edits.apply();
    }
    public String getUserAccHolderName(){
        return prefs.getString(B_HOLDER_NAME,null);
    }
    public void setUserAccHolderName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toUpperCase();
        edits.putString(B_HOLDER_NAME, newVal);
        edits.apply();
    }
    public String getUserBranchCode(){
        return prefs.getString(B_BRANCHCODE,null);
    }
    public void setUserBranchCode(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toUpperCase();
        edits.putString(B_BRANCHCODE, newVal);
        edits.apply();
    }
    public String getUserBankName(){
        return prefs.getString(B_NAME,null);
    }
    public void setUserBankName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toUpperCase();
        edits.putString(B_NAME, newVal);
        edits.apply();
    }



    public String getUserEmail(){
        return prefs.getString(U_EMAIL,null);
    }
    public void setUserEmail(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toLowerCase();
        edits.putString(U_EMAIL, newVal);
        edits.apply();
    }
    public String getUserFName(){
        return prefs.getString(U_P_FNAME,null);
    }
    public void setUserFName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toUpperCase();
        edits.putString(U_P_FNAME, newVal);
        edits.apply();
    }
    public String getUserSName(){
        return prefs.getString(U_P_SURNAME,null);
    }
    public void setUserSName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toUpperCase();
        edits.putString(U_P_SURNAME, newVal);
        edits.apply();
    }
    public String getUserThumb(){
        return prefs.getString(U_P_IMAGE_THUMB,null);
    }
    public void setUserThumb(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_P_IMAGE_THUMB, value);
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
    public String getUserMobile(){
        return prefs.getString(U_MOBILE,null);
    }
    public void setUserMobile(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_MOBILE, value);
        edits.apply();
    }
    public String getUserMobileStatus(){
        return prefs.getString(U_MOBILE_STATUS,null);
    }
    public void setUserMobileStatus(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_MOBILE_STATUS, value);
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
    public String getuEmailVerified(){
        return prefs.getString(U_EMAIL_VERIFIED,null);
    }
    public void setEmailVerified(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_EMAIL_VERIFIED, value);
        edits.apply();
    }
    public String getEmailVerified(){
        return prefs.getString(U_EMAIL_VERIFIED,null);
    }
    public void setUserTypeName(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_TYPE_NAME, value);
        edits.apply();
    }
    public String getUserTypeName(){
        return prefs.getString(U_TYPE_NAME,null);
    }
    public void setUserLastLogin(String value){
        SharedPreferences.Editor edits = prefs.edit();
        String newVal = value.toUpperCase();
        edits.putString(U_LAST_LOGIN, newVal);
        edits.apply();
    }
    public String getUserLastLogin(){
        return prefs.getString(U_LAST_LOGIN,null);
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

    public void setDate(String date) {
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(M_DATE, date);
        edits.apply();

    }

    /*add leave*/
    public String getStartDate(){
        return prefs.getString(U_START_DATE,null);
    }
    public void setStartDate(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_START_DATE, value);
        edits.apply();
    }

    public String getEndDate(){
        return prefs.getString(U_END_DATE,null);
    }
    public void setEndDate(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_END_DATE, value);
        edits.apply();
    }

   public String getLeaveType(){
        return prefs.getString(U_LEAVE_TYPE,null);
    }
    public void setLeaveType(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_LEAVE_TYPE, value);
        edits.apply();
    }

   public String getLeaveReason(){
        return prefs.getString(U_LEAVE_REASON,null);
    }
    public void setLeaveReason(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(U_LEAVE_REASON, value);
        edits.apply();
    }


    public void setHolidays(String holiday) {
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(M_HOLIDAYS, holiday);
        edits.apply();


      /*  userSessionObj.setUserBankName(dataM.getString("bankname"));
        userSessionObj.setUserBranchCode(dataM.getString("branchcode"));
        userSessionObj.setUserBBranchName(dataM.getString("=branch_name"));
        userSessionObj.setUserBAccount(dataM.getString("account_number"));
        userSessionObj.setUserAccHolderName(dataM.getString("account_holdername"));
        userSessionObj.setUserType(dataM.getString("r_role_id"));*/


    }
}
