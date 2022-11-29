package za.co.sacpo.grant.xCubeLib.baseFramework;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import za.co.sacpo.grant.LoginA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.asyncCalls.IsUpdateApp;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.dialogs.InternetDialog;
import za.co.sacpo.grant.xCubeLib.component.TooltipWindow;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;


import za.co.sacpo.grant.xCubeLib.session.OlumsUserSession;


import za.co.sacpo.grant.xCubeMentor.MDashboardDA;

import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public abstract class BaseAPC extends AppCompatActivity {
    protected String versionNew;
    protected String versionName;
    protected int versionCode;
    protected String KEY_STATUS = "s";
    protected String KEY_DATA = "m";
    private IsUpdateApp mIsUpdateAppTask = null;
    public static boolean isInternetReceiver;
    protected Context baseApcContext;
    protected AppCompatActivity activityIn;
    protected String LogTag,CAId,LogString;
    protected DbHelper dbSetaObj;
    protected PopupWindow mPopupWindow;
    protected Bundle inBundle;
    protected Bundle outBundle;
    protected OlumsUserSession userSessionObj;
    protected Boolean isLive = false;//Logs Show If False
    protected Boolean isOnline = false;


    protected abstract void setBaseApcContextParent(Context cnt,AppCompatActivity ain,String lt,String cAId);
    protected abstract void initializeViews();
    protected abstract void initializeListeners();
    protected abstract void initializeInputs();
    protected abstract void initializeLabels();
    protected abstract void setLayoutXml();
    protected abstract void verifyVersion();
    protected abstract void fetchVersionData();
    protected abstract void internetChangeBroadCast();

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    public BroadcastReceiver IChangeReceiver = new BroadcastReceiver() {
        public void onReceive(Context pContext, Intent pIntent) {
            ConnectivityManager cm = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null) {
                InternetDialog.removeInternetDialog();
            }
            else{
                if(isOnline){
                    String sTitle = getString(R.string.dialog_no_internet);
                    String sMessage = getString(R.string.dialog_no_inter_message);
                    String sButtonLabel3g = getString(R.string.dialog_enable_internet);
                    String buttonLabelWifi= getString(R.string.dialog_enable_wifi);
                    String buttonLabelTryAgain= getString(R.string.dialog_try_again);
                    InternetDialog.showInternetDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabel3g,buttonLabelWifi,buttonLabelTryAgain);

                }else{
                       // Toast.makeText(pContext, "Running in Offline Mode...", Toast.LENGTH_SHORT).show();

                }
                       }
            while (InternetDialog.isNetDialogShowing) {
                return;
            }
        }
    };

    public void registerBroadcastIC(){
        try {
            printLogs(LogTag, "registerBroadcast", "init");
            IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
            registerReceiver(IChangeReceiver, filter);
            isInternetReceiver = true;
            printLogs(LogTag, "registerBroadcast", "exit");

        }
        catch (Exception e){
            printLogs(LogTag, "registerBroadcast", "Exception "+e.getMessage());

        }
    }
    public void unregisterBroadcastIC(){
        printLogs(LogTag, "unregisterBroadcast", "init");
        try {
            if (isInternetReceiver) {
                printLogs(LogTag, "unregisterBroadcast", "isInternetReceiver");
                isInternetReceiver = false;
                unregisterReceiver(IChangeReceiver);
            }
        } catch (Exception e) {
            printLogs(LogTag, "unregisterBroadcast", "Exception " + e.getMessage());
        }
    }
  public void setAppTheme(){
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          Field resourceField = null;
          int resourceId = 0;
          try {
              resourceField = R.style.class.getDeclaredField("AppTheme_"+URLHelper.PORTAL_ID);
              //Here we are getting the String id in R file...But you can change to R.drawable or any other resource you want...
              resourceId  = resourceField.getInt(resourceField);
              setTheme(resourceId);
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
  }
    public void setAppLogo(){
        ImageView imageView = findViewById(R.id.headLogo);
        Field resourceField = null;
        int resourceId = 0;
        try {
            resourceField = R.drawable.class.getDeclaredField("app_logo_"+URLHelper.PORTAL_ID);
            //Here we are getting the String id in R file...But you can change to R.drawable or any other resource you want...
            resourceId  = resourceField.getInt(resourceField);
            imageView.setBackgroundResource(resourceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getStyleResourceId(String string){
            Field resourceField = null;
            int resourceId = 0;
            try {
                resourceField = R.style.class.getDeclaredField(string+"_"+URLHelper.PORTAL_ID);
                //Here we are getting the String id in R file...But you can change to R.drawable or any other resource you want...
                resourceId  = resourceField.getInt(resourceField);
                setTheme(resourceId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resourceId;
    }
    public int getStringResourceId(String string){
        Field resourceField = null;
        int resourceId = 0;
        try {
            resourceField = R.string.class.getDeclaredField(string);
            //Here we are getting the String id in R file...But you can change to R.drawable or any other resource you want...
            resourceId  = resourceField.getInt(resourceField);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resourceId;
    }
    public int getTextcolorResourceId(String string){
        Field resourceField = null;
        int resourceId = 0;
        try {
            resourceField = R.color.class.getDeclaredField(string+"_"+URLHelper.PORTAL_ID);
            //Here we are getting the String id in R file...But you can change to R.drawable or any other resource you want...
            resourceId  = resourceField.getInt(resourceField);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceId;
    }

    public int getDrwabaleResourceId(String name){
        Field resourceField = null;
        int resourceId=0;
        try {
               resourceField = R.drawable.class.getDeclaredField(name+"_"+URLHelper.PORTAL_ID);
            //Here we are getting the String id in R file...But you can change to R.drawable or any other resource you want...
            resourceId = resourceField.getInt(resourceField);
            //Here you can use it as usual
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceId;
    }

    public String getLabelFromDb(String inputLabel,int resId){
        String ValueLabel =baseApcContext.getString(resId);
        dbSetaObj = DbHelper.getInstance(this);
        Cursor res = dbSetaObj.getDataValueByName(inputLabel);
        if(res.getCount() > 0) {
            try {
                while (res.moveToNext()) {
                    ValueLabel = res.getString(0);
                }
            }
            finally {
                if (!res.isClosed()) {
                    res.close();
                    dbSetaObj.close();
                }
            }
        }
        return ValueLabel;
    }
    public void printLogs(String tag,String funcs,String msg){
        if(isLive==false) {
            Log.i("OSG-"+tag+"__"+funcs,msg);
            LogString = LogString+"TAG - "+tag+"<br/> FUNCTION - "+funcs+"<br/> DATA - "+msg+"<br/><br/><br/><br/>";
            //Toast.makeText(baseApcContext, "", Toast.LENGTH_SHORT).show();
        }
    }

    public String getHelpTitleFromDb(String ActivityId,int type){
        String Title ="";
        dbSetaObj = DbHelper.getInstance(this);
        Cursor res = dbSetaObj.getTitleByActivity(ActivityId,type);
        if(res.getCount() > 0) {
            try {
                while (res.moveToNext()) {
                    Title = res.getString(0);
                }
            }
            finally {
                if (res != null && !res.isClosed()) {
                    res.close();
                    dbSetaObj.close();
                }
            }
        }
        else{
            Cursor resD = dbSetaObj.getTitleByActivity("0",type);
            if(resD.getCount() > 0) {
                try {
                    while (resD.moveToNext()) {
                        Title = resD.getString(0);
                    }
                }
                finally {
                    if (resD != null && !resD.isClosed()) {
                        resD.close();
                        dbSetaObj.close();
                    }
                }
            }
        }
        return Title;
    }
    public String getHelpContentFromDb(String ActivityId,int type){
        String Content ="";
        dbSetaObj = DbHelper.getInstance(this);
        Cursor res = dbSetaObj.getContentByActivity(ActivityId,type);
        if(res.getCount() > 0) {
            try {
                while (res.moveToNext()) {
                    Content = res.getString(0);
                }
            }
            finally {
                if (res != null && !res.isClosed()) {
                    res.close();
                    dbSetaObj.close();
                }
            }
        }
        else{
            Cursor resD = dbSetaObj.getContentByActivity("0",type);
            if(resD.getCount() > 0) {
                try {
                    while (resD.moveToNext()) {
                        Content = resD.getString(0);
                    }
                }
                finally {
                    if (resD != null && !resD.isClosed()) {
                        resD.close();
                        dbSetaObj.close();
                    }
                }
            }
        }
        return Content;
    }

    public void syncUpdates(Context baseApcContext,AppCompatActivity activityIn){
        int versionCode=1;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
            //binding.tvVersionCode.setText("v" + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionNew = String.valueOf(versionCode);
        //versionNew="5";
        printLogs(LogTag,"syncUpdates","versionName "+versionName+" VersionCode "+versionCode + " NewVersion "+versionNew);

        mIsUpdateAppTask = new IsUpdateApp(versionNew, baseApcContext);
        mIsUpdateAppTask.execute((Void) null);
    }

    public void showTooltip(TextView ttInputView, String sToolTip,int inputCase ){
        TooltipWindow tipWindow;
        switch(inputCase){
            case 1:
                tipWindow = new TooltipWindow(activityIn, TooltipWindow.DRAW_LEFT, sToolTip);
                tipWindow.showToolTip(ttInputView, TooltipWindow.DRAW_ARROW_DEFAULT_CENTER, false);
                break;
            case 2:
                tipWindow = new TooltipWindow(activityIn, TooltipWindow.DRAW_RIGHT, sToolTip);
                tipWindow.showToolTip(ttInputView, TooltipWindow.DRAW_ARROW_DEFAULT_CENTER, false);
                break;
            case 3:
                tipWindow = new TooltipWindow(activityIn, TooltipWindow.DRAW_TOP, sToolTip);
                tipWindow.showToolTip(ttInputView, TooltipWindow.DRAW_ARROW_DEFAULT_CENTER, false);
                break;
            case 4:
                tipWindow = new TooltipWindow(activityIn, TooltipWindow.DRAW_BOTTOM, sToolTip);
                tipWindow.showToolTip(ttInputView, TooltipWindow.DRAW_ARROW_DEFAULT_CENTER, false);
                break;
        }

        //Toast.makeText(getApplicationContext(),sToolTip, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogString="";
       /* IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(InternetChangeReceiver, filter);
        InternetDialog.isInternetReceiver = true;*/
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)

    protected void showProgress(final boolean show,final View contentView,final View loaderView) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            contentView.setVisibility(show ? View.GONE : View.VISIBLE);
            contentView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    contentView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            loaderView.setVisibility(show ? View.VISIBLE : View.GONE);
            loaderView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loaderView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            loaderView.setVisibility(show ? View.VISIBLE : View.GONE);
            contentView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public Long longDateFromString(String string_date){
        Calendar c = Calendar.getInstance();
        long milliseconds = c.get(Calendar.MILLISECOND);
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = f.parse(string_date);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
    public int getMonthOfString(String month){
        int outMonth =0;
        switch (month){
            case "jan":
            case "january":
                outMonth =0;
                break;
            case "feb":
            case "february":
                outMonth =1;
                break;
            case "mar":
            case "march":
                outMonth =2;
                break;
            case "apr":
            case "april":
                outMonth =3;
                break;
            case "may":
                outMonth =4;
                break;
            case "jun":
            case "june":
                outMonth =5;
                break;
            case "jul":
            case "july":
                outMonth =6;
                break;
            case "aug":
            case "august":
                outMonth =7;
                break;
            case "sep":
            case "september":
                outMonth =8;
                break;
            case "oct":
            case "october":
                outMonth =9;
                break;
            case "nov":
            case "november":
                outMonth =10;
                break;
            case "dec":
            case "december":
                outMonth =11;
                break;
        }
        return outMonth;
    }
    public Date dateFromString(String dateString){
        Date outputDate=null;
        //String dtStart = "2010-10-15T09:27:37Z";
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            outputDate = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDate;

    }
    public Date dateFromStringForCL(String dateString){
        Date outputDate=null;
        //String dtStart = "2010-10-15T09:27:37Z";
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            outputDate = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDate;

    }
    public String stringFromDateForCL(Date dDate) {

        String dateTime = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
        dateTime = dateFormat.format(dDate);
        return dateTime;
    }

    public String stringFromDate(Date dDate) {

        String dateTime = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateTime = dateFormat.format(dDate);
        return dateTime;
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

    public void showInfo(View V,String ActivityId){
        mPopupWindow.setContentView(getLayoutInflater().inflate(R.layout.popup_window, null, false));
        String infoString = "<![CDATA[<html><table width='100%'>";
        infoString += "<tr><td>VERSION</td><td>"+versionName+"</td></tr>";
        infoString += "<tr><td>OS VERSION</td><td>"+System.getProperty("os.version")+"</td></tr>";
        infoString += "<tr><td>OS SUB VERSION</td><td>"+android.os.Build.VERSION.INCREMENTAL +"</td></tr>";
        infoString += "<tr><td>DEVICE</td><td>"+android.os.Build.DEVICE+"</td></tr>";
        infoString += "<tr><td>MODEL</td><td>"+android.os.Build.MODEL+"</td></tr>";
        infoString += "<tr><td>PRODUCT</td><td>"+android.os.Build.PRODUCT+"</td></tr>";
        infoString += "<tr><td>RELEASE</td><td>"+ android.os.Build.VERSION.RELEASE +"</td></tr>";
        infoString += "<tr><td>BRAND</td><td>"+  android.os.Build.BRAND +"</td></tr>";
        infoString += "<tr><td>DISPLAY</td><td>"+  android.os.Build.DISPLAY +"</td></tr>";
        infoString += "<tr><td>CPU ABI</td><td>"+  android.os.Build.CPU_ABI +"</td></tr>";
        infoString += "<tr><td>CPU ABI 2</td><td>"+  android.os.Build.CPU_ABI2 +"</td></tr>";
        infoString += "<tr><td>UNKNOWN</td><td>"+  android.os.Build.UNKNOWN +"</td></tr>";
        infoString += "<tr><td>HARDWARE</td><td>"+  android.os.Build.HARDWARE +"</td></tr>";
        infoString += "<tr><td>BUILD ID</td><td>"+  android.os.Build.ID +"</td></tr>";
        infoString += "<tr><td>MANUFACTURER</td><td>"+  android.os.Build.MANUFACTURER +"</td></tr>";
        infoString += "<tr><td>SERIAL</td><td>"+  android.os.Build.SERIAL +"</td></tr>";
        infoString += "<tr><td>USER</td><td>"+  android.os.Build.USER +"</td></tr>";
        infoString += "<tr><td>HOST</td><td>"+  android.os.Build.HOST +"</td></tr>";
        infoString += "<tr><td>TAGS</td><td>"+  android.os.Build.TAGS +"</td></tr>";
        infoString += "<tr><td>TIME</td><td>"+  android.os.Build.TIME +"</td></tr>";
        infoString += "<tr><td>TYPE</td><td>"+  android.os.Build.TYPE +"</td></tr>";
        infoString += "<tr><td>BOARD</td><td>"+  android.os.Build.BOARD +"</td></tr>";
        infoString += "<tr><td>BOOTLOADER</td><td>"+  android.os.Build.BOOTLOADER+"</td></tr>";
        infoString += "</table></body></html>]]>";
        String htmlAsString = infoString;
        Spanned Message;
        if (Build.VERSION.SDK_INT >= 24) {
            Message = Html.fromHtml(htmlAsString, Html.FROM_HTML_MODE_LEGACY);
        }
        else {
            Message = Html.fromHtml(htmlAsString);
        }
        WebView wv_information;
        wv_information = mPopupWindow.getContentView().findViewById(R.id.wv_information);
        final WebSettings webSettingsF = wv_information.getSettings();
        Resources res = getResources();
        float fontSize = res.getDimension(R.dimen.tiniestTextSize);
        webSettingsF.setDefaultFontSize((int)fontSize);
        wv_information.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv_information.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        String Title = "DEVICE INFORMATION";
        ((TextView) mPopupWindow.getContentView().findViewById(R.id.message_head)).setText(Title);
        ((TextView) mPopupWindow.getContentView().findViewById(R.id.message_text)).setText(Message);
        wv_information.loadData(Message.toString(), "text/html", "UTF-8");
        wv_information.setBackgroundColor(Color.TRANSPARENT);
        wv_information.loadUrl("javascript:document.body.style.setProperty(\"color\", \"white\");");
        ((TextView) mPopupWindow.getContentView().findViewById(R.id.message_text)).setVisibility(View.GONE);
        ((VideoView) mPopupWindow.getContentView().findViewById(R.id.message_video)).setVisibility(View.GONE);
        //mPopupWindow.showAtLocation(findViewById(R.id.Popupwindow), Gravity.CENTER, 0, 0);
        Button btnClose = (Button) mPopupWindow.getContentView().findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.showAtLocation(V, Gravity.CENTER, 0, 0);
    }
    public void showLogs(View V,String ActivityId){
        mPopupWindow.setContentView(getLayoutInflater().inflate(R.layout.popup_window, null, false));
        String htmlAsString = LogString;
        Spanned Message;
        if (Build.VERSION.SDK_INT >= 24) {
            Message = Html.fromHtml(htmlAsString, Html.FROM_HTML_MODE_LEGACY);
        }
        else {
            Message = Html.fromHtml(htmlAsString);
        }

        WebView wv_information;
        wv_information = mPopupWindow.getContentView().findViewById(R.id.wv_information);
        wv_information.setVisibility(View.GONE);
        String Title = "DEBUG";
        ((TextView) mPopupWindow.getContentView().findViewById(R.id.message_head)).setText(Title);
        ((TextView) mPopupWindow.getContentView().findViewById(R.id.message_text)).setText(Message);
        ((TextView) mPopupWindow.getContentView().findViewById(R.id.message_text)).setVisibility(View.VISIBLE);
        ((VideoView) mPopupWindow.getContentView().findViewById(R.id.message_video)).setVisibility(View.GONE);
        //mPopupWindow.showAtLocation(findViewById(R.id.Popupwindow), Gravity.CENTER, 0, 0);
        Button btnClose = (Button) mPopupWindow.getContentView().findViewById(R.id.btn_close);



        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.showAtLocation(V, Gravity.CENTER, 0, 0);
    }
    public void showHelp(View V,String ActivityId){
        mPopupWindow.setContentView(getLayoutInflater().inflate(R.layout.popup_window, null, false));
        String htmlAsString = getHelpContentFromDb(ActivityId,1);
        Spanned Message;
        if (Build.VERSION.SDK_INT >= 24) {
            Message = Html.fromHtml(htmlAsString, Html.FROM_HTML_MODE_LEGACY);
        }
        else {
            Message = Html.fromHtml(htmlAsString);
        }
        String h_id = ActivityId;
        //String h_id = "1";
        String url_api = URLHelper.DOMAIN_URL+"/guest/help-article-android/h_id/"+h_id;
        Log.w("WebView", "loadUrl"+url_api);
        WebView webView;
        webView = mPopupWindow.getContentView().findViewById(R.id.wv_information);
        try {
            webView.setWebViewClient(new WebViewClient());
            //webView.setWebChromeClient(new MyWebChromeClient( url_api ));
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);

            webView.loadUrl(url_api);
        } catch (Exception e) {
            Log.w("WebView", "loadUrl"+url_api, e);
        }
        webView.setBackgroundColor(Color.TRANSPARENT);

        String Title = getHelpTitleFromDb(ActivityId,1);
        ((TextView) mPopupWindow.getContentView().findViewById(R.id.message_head)).setText(Title);
        ((TextView) mPopupWindow.getContentView().findViewById(R.id.message_head)).setVisibility(View.GONE);
        ((TextView) mPopupWindow.getContentView().findViewById(R.id.message_text)).setText(Message);
        ((TextView) mPopupWindow.getContentView().findViewById(R.id.message_text)).setVisibility(View.GONE);
        ((VideoView) mPopupWindow.getContentView().findViewById(R.id.message_video)).setVisibility(View.GONE);
        //mPopupWindow.showAtLocation(findViewById(R.id.Popupwindow), Gravity.CENTER, 0, 0);
        Button btnClose = (Button) mPopupWindow.getContentView().findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.showAtLocation(V, Gravity.CENTER, 0, 0);
    }

    public void showHelpVideo(View V,String ActivityId) throws URISyntaxException {
        mPopupWindow.setContentView(getLayoutInflater().inflate(R.layout.popup_window, null, false));

        String Title = getHelpTitleFromDb(ActivityId,2);
        ((TextView) mPopupWindow.getContentView().findViewById(R.id.message_head)).setText(Title);
        ((TextView) mPopupWindow.getContentView().findViewById(R.id.message_text)).setVisibility(View.GONE);
        final VideoView vvd = ((VideoView) mPopupWindow.getContentView().findViewById(R.id.message_video));
        vvd.setVisibility(View.VISIBLE);
        String videoUrl = "http://demo.setaportal.co.za/uploads/help/video/201.mp4";
        Uri videoUri = Uri.parse(videoUrl);
        vvd.setVideoURI(videoUri);
        vvd.setVideoPath(videoUrl);
        MediaController mediaController = new MediaController(baseApcContext);
        mediaController.setAnchorView(vvd);
        WebView webView;
        webView = mPopupWindow.getContentView().findViewById(R.id.wv_information);
        webView.setVisibility(View.GONE);
        vvd.setMediaController(mediaController);
        vvd.setOnPreparedListener(new MediaPlayer.OnPreparedListener()  {
            @Override
            public void onPrepared(MediaPlayer mp) {


            }
        });
        vvd.requestFocus();
        vvd.start();


        //mPopupWindow.showAtLocation(findViewById(R.id.Popupwindow), Gravity.CENTER, 0, 0);
        Button btnClose = (Button) mPopupWindow.getContentView().findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.showAtLocation(V, Gravity.CENTER, 0, 0);
    }

    protected void initMenusCustom(String ActivityId,Context baseApcContext, AppCompatActivity activityIn){

        View popupView = getLayoutInflater().inflate(R.layout.popup_window, null);

        mPopupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.getContentView().setFocusable(true);
        mPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void backRedirectHandler(final Context baseApcContext, final AppCompatActivity activityIn,final String ActivityId){
        userSessionObj = new OlumsUserSession(baseApcContext);
        if (userSessionObj.getHasSession()) {
            String userType = userSessionObj.getUserType();

            if(userType.equals("2")){
                Intent intent = new Intent(baseApcContext,SDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","SDashboardDA");
                startActivity(intent);
                finish();
            }
            else if(userType.equals("5")){
                Intent intent = new Intent(baseApcContext,MDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","MDashboardDA");
                startActivity(intent);
                finish();
            }
        }
        else{
            Intent intent = new Intent(baseApcContext, LoginA.class);
            printLogs(LogTag,"onOptionsItemSelected","LoginA");
            startActivity(intent);
            finish();
        }

    }
}