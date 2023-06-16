package za.co.sacpo.grantportal.xCubeLib.session;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import za.co.sacpo.grantportal.xCubeLib.dataObj.FormsObj;

public class OlumsUtilitySession {
    private SharedPreferences prefs;
    private Context context;
    public static final String OLUMS_SESSION = "OLUMS_UTIL_SETA_SESSION" ;
    public static final String CUSTOM_VERSION= "C_V";

    public static final String NOTIFICATION_COUNT= "N_C";
    public static final String POLL_COUNT ="P_C";
    public static final String CHAT_COUNT= "C_C";
    public static final String SIGN_IN_COUNT="SETA_S_I_C";
    public static final String SIGN_OUT_COUNT="SETA_S_O_C";
    public static final String ATT_APPROVAL_COUNT="SETA_A_C";
    public static final String CLAIM_APPROVAL_COUNT="SETA_C_C";

    public static final String INTERVIEW_COUNT= "I_C";
    public static final String OFFER_COUNT= "O_C";
    public static final String JOB_COUNT= "J_C";
    public static final String POLL_INPUT= "P_I";
    public static final String BURSARY_COUNT="B_I";
    public static final String POLL_INPUT_ANSWERS= "P_I_A";
    public static final String POLL_INPUT_INDEPENDENT="P_I_I";
    public static final String POLL_INPUT_I_ANSWERS="P_I_A_I";
    public static final String DISCUSSION_COUNT ="D_C";
    public static final String RAISED_COUNT ="R_C";
    public static final String COMM_COUNT ="C_C";
    public static final String INTERVIEW_INVITSION ="I_I";
    public static final String IS_UPDATED="is_updated";
    public static final String IS_DOWN="is_down";
    public static final String DOWN_MESSAGE="down_message";
    public static final String DOWN_USER_TYPE_ID="down_user_type_id";
    public static final String IS_GOOGLE_PLAY_UPDATED="app_updated_on_google_play";

    public static final String FORMSDATA= "formsData";
    public OlumsUtilitySession(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(OLUMS_SESSION, Context.MODE_PRIVATE);
    }
    public void setFormsdata(String Value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(FORMSDATA,Value);
        edits.apply();
    }
    public FormsObj getFormsdata(){
        Gson gson = new Gson();
        String output = prefs.getString(FORMSDATA,null);
        return gson.fromJson(output, FormsObj.class);
    }
    public void setSignInCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(SIGN_IN_COUNT, value);
        edits.apply();
    }
    public String getSignInCount(){
        return prefs.getString(SIGN_IN_COUNT,null);
    }
    public void setSignOutCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(SIGN_OUT_COUNT, value);
        edits.apply();
    }
    public String getSignOutCount(){
        return prefs.getString(SIGN_OUT_COUNT,null);
    }
    public void setAttApprovalCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(ATT_APPROVAL_COUNT, value);
        edits.apply();
    }
    public String getAttApprovalCount(){
        return prefs.getString(ATT_APPROVAL_COUNT,null);
    }
    public void setClaimApprovalCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(CLAIM_APPROVAL_COUNT, value);
        edits.apply();
    }
    public String getClaimApprovalCount(){
        return prefs.getString(CLAIM_APPROVAL_COUNT,null);
    }

    public void setInterviewInvitaion(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(INTERVIEW_INVITSION, value);
        edits.apply();
    }
    public String getInterviewInvitaion(){
        return prefs.getString(INTERVIEW_INVITSION,null);
    }

    public void setPollInputI(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(POLL_INPUT_INDEPENDENT, value);
        edits.apply();
    }
    public String getPollInputI(){
        return prefs.getString(POLL_INPUT_INDEPENDENT,null);
    }

    public void setPollInputIA(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(POLL_INPUT_I_ANSWERS, value);
        edits.apply();
    }
    public String getPollInputIA(){
        return prefs.getString(POLL_INPUT_I_ANSWERS,null);
    }

    public void setPollInputA(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(POLL_INPUT_ANSWERS, value);
        edits.apply();
    }
    public String getPollInputA(){
        return prefs.getString(POLL_INPUT_ANSWERS,null);
    }
    public void setPollInput(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(POLL_INPUT, value);
        edits.apply();
    }
    public String getPollInput(){
        return prefs.getString(POLL_INPUT,null);
    }
    public void setNotificationCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(NOTIFICATION_COUNT, value);
        edits.apply();
    }
    public String getNotificationCount(){
        return prefs.getString(NOTIFICATION_COUNT,null);
    }
    public void setChatCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(CHAT_COUNT, value);
        edits.apply();
    }
    public String getPollCount(){
        return prefs.getString(POLL_COUNT,null);
    }
    public void setPollCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(POLL_COUNT, value);
        edits.apply();
    }
    public String getChatCount(){
        return prefs.getString(CHAT_COUNT,null);
    }
    public void setInterviewCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(INTERVIEW_COUNT, value);
        edits.apply();
    }
    public String getInterviewCount(){
        return prefs.getString(INTERVIEW_COUNT,null);
    }
    public void setOfferCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(OFFER_COUNT, value);
        edits.apply();
    }
    public String getOfferCount(){
        return prefs.getString(OFFER_COUNT,null);
    }
    public void setBursaryCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(BURSARY_COUNT, value);
        edits.apply();
    }
    public String getBursaryCount(){
        return prefs.getString(BURSARY_COUNT,null);
    }

    public void setNewJobCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(JOB_COUNT, value);
        edits.apply();
    }
    public String getNewJobCount(){
        return prefs.getString(JOB_COUNT,null);
    }

    public void setDiscussionCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(DISCUSSION_COUNT, value);
        edits.apply();
    }
    public String getDiscussionCount(){
        return prefs.getString(DISCUSSION_COUNT,null);
    }


    public void setRaisedCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(RAISED_COUNT, value);
        edits.apply();
    }
    public String getRaisedCount(){
        return prefs.getString(RAISED_COUNT,null);
    }

    public void setCommCount(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(COMM_COUNT, value);
        edits.apply();
    }
    public String getCommCount(){
        return prefs.getString(COMM_COUNT,null);
    }

    public Boolean getIsUpdateRequired(){
        return prefs.getBoolean(IS_UPDATED,false);
    }
    public void setIsUpdateRequired(Boolean value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putBoolean(IS_UPDATED, value);
        edits.apply();
    }
    public Boolean getIsGooglePlayUpdateRequired(){
        return prefs.getBoolean(IS_GOOGLE_PLAY_UPDATED,false);
    }
    public void setIsGooglePlayUpdateRequired(Boolean value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putBoolean(IS_GOOGLE_PLAY_UPDATED, value);
        edits.apply();
    }
    public Boolean getIsAppDown(){
        return prefs.getBoolean(IS_DOWN,false);
    }
    public void setIsAppDown(Boolean value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putBoolean(IS_DOWN, value);
        edits.apply();
    }
    public String getDownMessage(){
        return prefs.getString(DOWN_MESSAGE,"");
    }
    public void setDownMessage(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(DOWN_MESSAGE, value);
        edits.apply();
    }
    public String getDownUserTypeId(){
        return prefs.getString(DOWN_USER_TYPE_ID,"");
    }
    public void setDownUserTypeId(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(DOWN_USER_TYPE_ID, value);
        edits.apply();
    }



    public void setCustomVersion(String value){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(CUSTOM_VERSION, value);
        edits.apply();
    }
    public String getCustomVersion(){
        return prefs.getString(CUSTOM_VERSION,null);
    }
}
