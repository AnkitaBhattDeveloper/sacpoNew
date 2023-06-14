package za.co.sacpo.grant;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.BuildConfig;

import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

//import za.co.sacpo.grant.BuildConfig;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;


public class AppUpdatedA extends BaseAPC {
    private String ActivityId="A404";
    private TextView lblView,v_info;
    public Button mGooglePlayButton;
    public WebView wv_information;
    protected OlumsUtilitySession utilSessionObj;
    public LinearLayout informationContainer,buttonContainer;
    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }


    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        informationContainer = findViewById(R.id.informationContainer);
        buttonContainer = findViewById(R.id.buttonContainer);
        mGooglePlayButton = (Button) findViewById(R.id.btnGooglePlay);
        wv_information = findViewById(R.id.wv_information);
        final WebSettings webSettingsF = wv_information.getSettings();
        Resources res = getResources();
        float fontSize = res.getDimension(R.dimen.very_small_text);
        webSettingsF.setDefaultFontSize((int)fontSize);
        wv_information.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv_information.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        v_info = findViewById(R.id.v_info);
    }

    @Override
    protected void initializeListeners() {
        mGooglePlayButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            final String appPackageName = BuildConfig.APPLICATION_ID;
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
            }
        });
    }

    @Override
    protected void initializeInputs() {
        buttonContainer.setVisibility(View.GONE);
        informationContainer.setVisibility(View.GONE);
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        if (utilSessionObj.getIsGooglePlayUpdateRequired() == true) {
            buttonContainer.setVisibility(View.VISIBLE);
            informationContainer.setVisibility(View.GONE);
            if(utilSessionObj.getIsAppDown()==true) {
                buttonContainer.setVisibility(View.GONE);
                String message = utilSessionObj.getDownMessage();
                wv_information.loadData(message, "text/html", "UTF-8");
                informationContainer.setVisibility(View.VISIBLE);
            }
        }
        else{
            if(utilSessionObj.getIsAppDown()==true) {
                buttonContainer.setVisibility(View.GONE);
                String message = utilSessionObj.getDownMessage();
                wv_information.loadData(message, "text/html", "UTF-8");
                informationContainer.setVisibility(View.VISIBLE);
            }
        }
        v_info.setText("VERSION : "+versionName);
    }

    @Override
    protected void initializeLabels() {
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("i_A404_update_message",R.string.i_A404_update_message);
        lblView = (TextView)findViewById(R.id.iUpdateMessage);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("b_A404_market",R.string.b_A404_market);
        mGooglePlayButton.setText(Label);
        mGooglePlayButton.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));

    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_app_updated);
        printLogs(LogTag,"setLayoutXml","a_app_updated");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        setLayoutXml();
        setAppLogo();
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        if(isConnected) {
            registerBroadcastIC();
           fetchVersionData();
            verifyVersion();
            initializeViews();
            initializeListeners();
            initializeLabels();
            initializeInputs();
            printLogs(LogTag,"onCreate","exitConnected");
        }
    }
    protected void fetchVersionData(){
    }
    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }
    protected void verifyVersion(){
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterBroadcastIC();
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerBroadcastIC();
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterBroadcastIC();
    }
    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastIC();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastIC();
    }

}