package za.co.sacpo.grant.xCubeLib.baseFramework;


import android.text.TextUtils;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import za.co.sacpo.grant.R;

public abstract class BaseFormAPCPublic extends BaseAPCPublic {
    protected abstract void validateForm();
    protected abstract void FormSubmit();


    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static boolean isValidDepartment(String fname) {
        Boolean result;
        result = fname.length() < 3;
        if (fname.length() > 60) {
            result = true;
        }
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

    public static boolean isValidUsertype(String usertype) {
        Boolean result;
        result = usertype.length() >= 2;
        if (usertype.length() > 20) {
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
        boolean result;
        result = name.length() >= 3;
        if(name.length()>60){
            result = false;
        }
        return result;
    }


    public boolean validateUsername(EditText mNameView, TextInputLayout inputLayoutName) {
        String data = mNameView.getText().toString().trim();
        setCustomError(inputLayoutName,null,mNameView);
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?#|/'<>^*()%!-]");

        printLogs(LogTag,"validateName","DATA : "+data);
        if (data.isEmpty() || regex.matcher(data).find() ) {
            String sMessage = getLabelFromDb("error_A98_105", R.string.error_A98_105);
            setCustomError(inputLayoutName,sMessage,mNameView);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutName,mNameView);
            return true;
        }
    }
    public boolean validateName(EditText mNameView, TextInputLayout inputLayoutName) {
        String data = mNameView.getText().toString().trim();
        setCustomError(inputLayoutName,null,mNameView);
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");

        printLogs(LogTag,"validateName","DATA : "+data);
        if (data.isEmpty() || !isValidName(data) || regex.matcher(data).find() ) {
            String sMessage = getLabelFromDb("error_A98_104", R.string.error_A98_104);
            setCustomError(inputLayoutName,sMessage,mNameView);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutName,mNameView);
            return true;
        }
    }
    public boolean validateEmail(EditText mEmailView,TextInputLayout inputLayoutEmail) {
        String data = mEmailView.getText().toString().trim();
        setCustomError(inputLayoutEmail,null,mEmailView);
        printLogs(LogTag,"validateEmail","DATA : "+data);
        if (data.isEmpty() || !isValidEmail(data)) {
            String sMessage = getLabelFromDb("error_A98_105",R.string.error_A98_105);
            setCustomError(inputLayoutEmail,sMessage,mEmailView);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutEmail,mEmailView);
            return true;
        }
    }
    public boolean validateSubject(EditText mSubjectView,TextInputLayout inputLayoutSubject) {
        String data = mSubjectView.getText().toString().trim();
        setCustomError(inputLayoutSubject,null,mSubjectView);
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>^*()%!-]");
        printLogs(LogTag,"validateSubject","DATA : "+data);
        if (data.isEmpty() || isValidTitleContactUs(data) || regex.matcher(data).find()) {
            String sMessage = getLabelFromDb("error_A98_107",R.string.error_A98_107);
            setCustomError(inputLayoutSubject,sMessage,mSubjectView);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutSubject,mSubjectView);
            return true;
        }
    }
    public boolean validateMessage(EditText mMessageView,TextInputLayout inputLayoutMessage) {
        String data = mMessageView.getText().toString().trim();
        setCustomError(inputLayoutMessage,null,mMessageView);
        printLogs(LogTag,"validateMessage","DATA : "+data);
        if (data.isEmpty() || isValidMessageContent(data)) {
            String sMessage = getLabelFromDb("tt_A98_Message",R.string.tt_A98_Message);
            setCustomError(inputLayoutMessage,sMessage,mMessageView);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutMessage,mMessageView);
            return true;
        }
    }
    public boolean validatePassword(EditText mPasswordView, TextInputLayout inputLayoutPassword) {
        String pass = mPasswordView.getText().toString().trim();
        setCustomError(inputLayoutPassword, null, mPasswordView);
        if (pass.isEmpty()) {
            String sMessage = getLabelFromDb("error_A101_126", R.string.error_A101_126);
            setCustomError(inputLayoutPassword, sMessage, mPasswordView);
            return false;
        } else {
            if (pass.length() < 5) {
                String sMessage = getLabelFromDb("error_A101_127", R.string.error_A101_127);
                setCustomError(inputLayoutPassword, sMessage, mPasswordView);
                return false;
            } else if (pass.length() > 15) {
                String sMessage = getLabelFromDb("error_A101_128", R.string.error_A101_128);
                setCustomError(inputLayoutPassword, sMessage, mPasswordView);
                return false;
            } else {
                setCustomErrorDisabled(inputLayoutPassword, mPasswordView);
                return true;
            }
        }
    }
    public static boolean isValidTitleContactUs(String input) {
        Boolean result;
        result = input.length() <= 12;
       /* if(input.length()<100){
            result = false;
        }*/
        return result;
    }

    public static boolean isValidMessageContent(String input) {
        Boolean result;
        result = input.length() <= 30;
        return result;

    }

}