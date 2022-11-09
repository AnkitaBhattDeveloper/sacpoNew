package za.co.sacpo.grant.xCubeLib.baseFramework;


import com.google.android.material.textfield.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;

public abstract class BaseFormAPCPrivate extends BaseAPCPrivate {
    protected abstract void validateForm();
    protected abstract void FormSubmit();

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

    public static boolean isValidBranchName(String input){
        Boolean result;
        result = input.length() >= 5;
        return result;
    }
    public static boolean isValidBankName(String input){
        Boolean result;
        result = input.length() >= 5;
        return result;
    }
    public static boolean isValidBranchCode(String input){
        Boolean result;
        result = input.length() >= 5;
        return result;
    }
    public static boolean isValidFName(String fname) {
        Boolean result;
        result = fname.length() >= 3;
        if (fname.length() > 60) {
            result = false;
        }
        return result;
    }
    public static boolean isValidLName(String lname) {
        Boolean result;
        result = lname.length() >= 3;
        if (lname.length() > 60) {
            result = false;
        }
        return result;
    }
    public static boolean isValidMobile(String mobile) {
        Boolean result;
        result = mobile.length() >= 9;
        if (mobile.length() > 12) {
            result = false;
        }
        return result;
    }
    public static boolean isValidNumber(int number) {
        Boolean result;
        result = number > 0;
        return result;
    }
    public static boolean isValidName(String name) {
        Boolean result;
        result = name.length() >= 3;
        if(name.length()>60){
            result = false;
        }
        return result;
    }
    public static boolean isValidTitleContactUs(String input) {
        Boolean result;
        result = input.length() <= 5;
        if(input.length()<100){
            result = false;
        }
        return result;
    }

    public static boolean departmentName(String input) {
        Boolean result;
        result = input.length() <= 5;
        if(input.length()<100){
            result = false;
        }
        return result;
    }

    public static boolean isValidTraining(String input) {
        boolean result;
        result = input.length() <= 20;
        if(input.length()<100){
            result = false;
        }
        return result;
    }

    public static boolean isValidMessageContent(String input) {
        boolean result;
        result = input.length() <= 20;
        return result;

    }
    public static boolean isValidReason(String input) {
        boolean result;
        result = input.length() <= 10;
        return result;

    }
    public static boolean isValidMessageComment(String input) {
        boolean result;
        result = input.length() <= 10;
        return result;

    }

    protected void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    protected void setCustomErrorDisabled(TextInputLayout viewL, EditText mEditView) {
        viewL.setErrorEnabled(false);
        mEditView.setBackgroundResource(R.drawable.input_boder_profile_1);
    }
    protected void setCustomError(TextInputLayout viewL,String msg,EditText mEditView) {
        mEditView.setError(msg,null);
        mEditView.setBackgroundResource(R.drawable.input_error_profile);
        mEditView.requestFocus();
    }
    public void spinnerError(String Title, String Message) {
        String sTitle = Title;
        String sMessage = Message;
        String sButtonLabelClose = "Close";
        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
    }
    public boolean isSpinnerValid(int Value){
        return Value > 0;
    }
}