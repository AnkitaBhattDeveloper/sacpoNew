package za.co.sacpo.grant.xCubeLib.baseFramework;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Timer;
import java.util.TimerTask;

import za.co.sacpo.grant.LoginA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.asyncCalls.AlertsData;
import za.co.sacpo.grant.xCubeLib.asyncCalls.FMData;
import za.co.sacpo.grant.xCubeLib.asyncCalls.GAData;
import za.co.sacpo.grant.xCubeLib.asyncCalls.GMData;
import za.co.sacpo.grant.xCubeLib.asyncCalls.GrantData;
import za.co.sacpo.grant.xCubeLib.asyncCalls.MentorData;
import za.co.sacpo.grant.xCubeLib.asyncCalls.SETAMData;
import za.co.sacpo.grant.xCubeLib.asyncCalls.StudentData;
import za.co.sacpo.grant.xCubeLib.asyncCalls.UserData;
import za.co.sacpo.grant.xCubeLib.asyncCalls.VerifierData;
import za.co.sacpo.grant.xCubeLib.dialogs.InternetDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsFMSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsGASession;
import za.co.sacpo.grant.xCubeLib.session.OlumsGMSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsMentorSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsSETAMSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUserSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeLib.asyncCalls.TokenUpdate;
import za.co.sacpo.grant.xCubeLib.session.OlumsVerifierSession;
import za.co.sacpo.grant.xCubeMentor.MVideoA;
import za.co.sacpo.grant.xCubeMentor.announcement.MAnnouncement;
import za.co.sacpo.grant.xCubeMentor.messages.MChatListA;
import za.co.sacpo.grant.xCubeStudent.SVideoA;
import za.co.sacpo.grant.xCubeStudent.announcment.SAnnouncement;
import za.co.sacpo.grant.xCubeStudent.messages.SChatListDA;

public abstract class BaseAPCPrivate extends BaseAPC {

    protected OlumsUserSession userSessionObj;
    protected OlumsUtilitySession utilSessionObj;
    protected OlumsStudentSession studentSessionObj;
    protected OlumsMentorSession mentorSessionObj;
    protected OlumsGrantSession grantSessionObj;
    protected OlumsGASession gASessionObj;
    protected OlumsFMSession fmSession;
    protected OlumsGMSession gMSession;
    protected OlumsVerifierSession verifierSessionObj;
    protected OlumsSETAMSession setaMSessionObj;
    private TokenUpdate mTokenUpdateTask = null;
    private UserData mUserDataTask = null;
    private StudentData mStudentDataTask = null;
    private MentorData mMentorDataTask = null;
    private GAData mGADataTask = null;
    private GMData mGMDataTask = null;
    private VerifierData mVRDataTask = null;
    private SETAMData mSETAMDataTask = null;
    private GrantData mGrantDataTask = null;
    private FMData mFMDataTask = null;
    private AlertsData mAlertDataTask = null;
    protected String userToken;
    final Handler handler = new Handler();
    final int delay = 500; // 1000 milliseconds == 1 second

    protected abstract void bootStrapInit();


    public void checkInternetConnection(){
        handler.postDelayed(new Runnable() {
            public void run() {
                LinearLayout ll_netinfo = findViewById(R.id.ll_netinfo);
                if (isOnline == false) {
                    if(!(ll_netinfo == null)){
                        ll_netinfo.setVisibility(View.VISIBLE);
                        // cv_netinfo.setVisibility(View.VISIBLE);
                        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        TextView tv_net = findViewById(R.id.tv_net);
                        ImageView iv_net = findViewById(R.id.iv_net);
                        if (cm.getActiveNetworkInfo() != null) {
                            tv_net.setText("Online");
                            iv_net.setImageResource(R.drawable.interview_accept_btn);
                        } else {
                            tv_net.setText("Offline");
                            iv_net.setImageResource(R.drawable.interview_reject_btn);
                        }
                        handler.postDelayed(this, delay);
                    }else{
                        String sTitle = getString(R.string.dialog_no_internet);
                        String sMessage = getString(R.string.dialog_no_inter_message);
                        String sButtonLabel3g = getString(R.string.dialog_enable_internet);
                        String buttonLabelWifi= getString(R.string.dialog_enable_wifi);
                        String buttonLabelTryAgain= getString(R.string.dialog_try_again);
                        InternetDialog.showInternetDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabel3g,buttonLabelWifi,buttonLabelTryAgain);

                    }

                }else{

                    ll_netinfo.setVisibility(View.GONE);
                    //  cv_netinfo.setVisibility(View.GONE);
                }
            }
        }, delay);

    }



    public void validateLogin(Context baseApcContext, AppCompatActivity activityIn) {
        userSessionObj = new OlumsUserSession(baseApcContext);
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        final int u_id = userSessionObj.getUserId();
        boolean session_available;
        printLogs(LogTag, "validateLogin", "SESSION USER ID : " + u_id);
        if (userSessionObj.getHasSession()) {
            if (u_id > 0) {
                printLogs(LogTag, "validateLogin", "LOGIN : TRUE ");
                session_available = true;
            } else {
                session_available = false;
            }
        } else {
            session_available = false;
        }
        if (!session_available) {
            Intent intent = new Intent(baseApcContext, LoginA.class);
            startActivity(intent);
            activityIn.finish();
        }
    }

    public void syncToken(Context baseApcContext, AppCompatActivity activityIn) {
        userSessionObj = new OlumsUserSession(baseApcContext);
        String userToken = userSessionObj.getToken();
        final int u_id = userSessionObj.getUserId();
        final String token = FirebaseInstanceId.getInstance().getToken();
        if (TextUtils.isEmpty(userToken)) {
            mTokenUpdateTask = new TokenUpdate(u_id, token, baseApcContext);
            mTokenUpdateTask.execute((Void) null);
        } else if (!userToken.equals(token)) {
            mTokenUpdateTask = new TokenUpdate(u_id, token, baseApcContext);
            mTokenUpdateTask.execute((Void) null);
        } else if (userToken.compareTo("null") == 0) {
            mTokenUpdateTask = new TokenUpdate(u_id, token, baseApcContext);
            mTokenUpdateTask.execute((Void) null);
        }
    }

    public void syncUserData(Context baseApcContext, AppCompatActivity activityIn) {
        userSessionObj = new OlumsUserSession(baseApcContext);
        String userToken = userSessionObj.getToken();
        mUserDataTask = new UserData(userToken, baseApcContext);
        mUserDataTask.execute((Void) null);
    }



    public void syncMentorData(Context baseApcContext, AppCompatActivity activityIn) {
        userSessionObj = new OlumsUserSession(baseApcContext);
        String userToken = userSessionObj.getToken();
        mMentorDataTask = new MentorData(userToken, baseApcContext);
        mMentorDataTask.execute((Void) null);
    }

    public void syncStudentData(Context baseApcContext, AppCompatActivity activityIn) {
        userSessionObj = new OlumsUserSession(baseApcContext);
        String userToken = userSessionObj.getToken();
        mStudentDataTask = new StudentData(userToken, baseApcContext);
        mStudentDataTask.execute((Void) null);
    }

    public void syncGrantData(Context baseApcContext, AppCompatActivity activityIn) {
        userSessionObj = new OlumsUserSession(baseApcContext);
        String userToken = userSessionObj.getToken();
        mGrantDataTask = new GrantData(userToken, baseApcContext);
        mGrantDataTask.execute((Void) null);
    }

    public void syncGAData(Context baseApcContext, AppCompatActivity activityIn) {
        userSessionObj = new OlumsUserSession(baseApcContext);
        String userToken = userSessionObj.getToken();
        mGADataTask = new GAData(userToken, baseApcContext);
        mGADataTask.execute((Void) null);
    }

    public void syncGMData(Context baseApcContext, AppCompatActivity activityIn) {
        userSessionObj = new OlumsUserSession(baseApcContext);
        String userToken = userSessionObj.getToken();
        mGMDataTask = new GMData(userToken, baseApcContext);
        mGMDataTask.execute((Void) null);
    }
    public void syncSVData(Context baseApcContext, AppCompatActivity activityIn) {
        userSessionObj = new OlumsUserSession(baseApcContext);
        String userToken = userSessionObj.getToken();
        mVRDataTask = new VerifierData(userToken, baseApcContext);
        mVRDataTask.execute((Void) null);
    }

    public void syncSETAMData(Context baseApcContext, AppCompatActivity activityIn) {
        userSessionObj = new OlumsUserSession(baseApcContext);
        String userToken = userSessionObj.getToken();
        mSETAMDataTask = new SETAMData(userToken, baseApcContext);
        mSETAMDataTask.execute((Void) null);
    }

    public void syncFMData(Context baseApcContext, AppCompatActivity activityIn) {
        userSessionObj = new OlumsUserSession(baseApcContext);
        String userToken = userSessionObj.getToken();
        mFMDataTask = new FMData(userToken, baseApcContext);
        mFMDataTask.execute((Void) null);
    }

    public boolean clearSession(Context context) {
        userSessionObj = new OlumsUserSession(context);
        userSessionObj.clearUserSession();
        userSessionObj.setHasSession(false);
        studentSessionObj = new OlumsStudentSession(context);
        studentSessionObj.clearStudentSession();
        mentorSessionObj = new OlumsMentorSession(context);
        mentorSessionObj.clearMentorSession();

        gASessionObj = new OlumsGASession(context);
        gASessionObj.clearGASession();

        fmSession = new OlumsFMSession(context);
        fmSession.clearFMSession();

        gMSession = new OlumsGMSession(context);
        gMSession.clearGMSession();

        setaMSessionObj = new OlumsSETAMSession(context);
        setaMSessionObj.clearSETAMSession();


        verifierSessionObj = new OlumsVerifierSession(context);
        verifierSessionObj.clearVerifierSession();
        //Intent intent = new Intent(context,UserListActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //context.startActivity(intent);
        return true;
    }

    public void syncAlerts(final Context baseApcContext, final AppCompatActivity activityIn, final String generator) {
        userSessionObj = new OlumsUserSession(baseApcContext);
        final String userToken = userSessionObj.getToken();

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask backGroundTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            mAlertDataTask = new AlertsData(userToken, baseApcContext);
                            mAlertDataTask.execute((Void) null);
                            updateFooterNotifications(baseApcContext, activityIn, generator);
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(backGroundTask, 0, 100000);
    }

    public void updateFooterNotifications(final Context baseApcContext, final AppCompatActivity activityIn, final String ActivityId) {
        //TODO:PENDING LINEAR LAYOUT ID -Footer Layout..and Color of footer icon when count is zero
        FrameLayout frameAlertMessageCount = findViewById(R.id.frameAlertMessageCount);
        frameAlertMessageCount.setVisibility(View.VISIBLE);


        FrameLayout frameNotificationCount = findViewById(R.id.frameNotificationCount);
        frameNotificationCount.setVisibility(View.VISIBLE);

        TextView cMessage = findViewById(R.id.alertMessageCount);
        TextView cNotification = findViewById(R.id.alertNotificationCount);
        TextView cSignIn = findViewById(R.id.alertSignInCount);
        TextView cSignOut = findViewById(R.id.alertSignOutCount);
        TextView cPoll = findViewById(R.id.alertPollCount);
        TextView cAttApp = findViewById(R.id.alertAttAppCount);
        TextView cClaimApp = findViewById(R.id.alertClaimAppCount);

        ImageView iMessage = findViewById(R.id.alertMessageImage);
        ImageView iNotification = findViewById(R.id.alertNotificationImage);
        ImageView iSignIn = findViewById(R.id.alertSignInImage);
        ImageView iSignOut = findViewById(R.id.alertSignOutImage);
        ImageView iPoll = findViewById(R.id.alertPollImage);
        ImageView iAttApp = findViewById(R.id.alertAttAppImage);
        ImageView iClaimApp = findViewById(R.id.alertClaimAppImage);
        ImageView video_cam_image = findViewById(R.id.video_cam_image);

        iMessage.setImageResource(getDrwabaleResourceId("chat"));
        iNotification.setImageResource(getDrwabaleResourceId("ic_launcher_bell"));
        video_cam_image.setImageResource(getDrwabaleResourceId("ic_launcher_video_footer"));

        cMessage.setText(utilSessionObj.getChatCount());
        cNotification.setText(utilSessionObj.getNotificationCount());
        cSignIn.setText(utilSessionObj.getSignInCount());
        cSignOut.setText(utilSessionObj.getSignOutCount());
        cPoll.setText(utilSessionObj.getPollCount());
        cAttApp.setText(utilSessionObj.getAttApprovalCount());
        cClaimApp.setText(utilSessionObj.getClaimApprovalCount());

        if (utilSessionObj.getChatCount().equals("0")) {
            frameAlertMessageCount.setVisibility(View.GONE);
            //iMessage.setImageResource(R.mipmap.ic_launcher_un_chat);
        }
        if (utilSessionObj.getNotificationCount().equals("0")) {
            //iNotification.setImageResource(R.mipmap.ic_launcher_notification_footer);
            frameNotificationCount.setVisibility(View.GONE);
        }
        if (utilSessionObj.getPollCount().equals("0")) {
            //iPoll.setImageResource(R.mipmap.ic_launcher_un_feedback);
        }
        if (utilSessionObj.getSignInCount().equals("0")) {
            //iSignIn.setImageResource(R.mipmap.un_signin);
        }
        if (utilSessionObj.getSignOutCount().equals("0")) {
            //iSignOut.setImageResource(R.mipmap.un_signout);
        }

        if (utilSessionObj.getAttApprovalCount().equals("0")) {
            //iAttApp.setImageResource(R.mipmap.un_attendance);
        }
        if (utilSessionObj.getClaimApprovalCount().equals("0")) {
            //iClaimApp.setImageResource(R.mipmap.un_claim);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    public void callFooter(final Context baseApcContext, final AppCompatActivity activityIn, final String ActivityId) {
        userSessionObj = new OlumsUserSession(baseApcContext);
        String userType = userSessionObj.getUserType();

        LinearLayout mMessageContainer = (LinearLayout) findViewById(R.id.alertMessageContainer);
        LinearLayout mNotificationContainer = (LinearLayout) findViewById(R.id.alertNotificationContainer);

        LinearLayout mPollContainer = (LinearLayout) findViewById(R.id.alertPollContainer);

        LinearLayout mAttContainer = (LinearLayout) findViewById(R.id.alertAttAppContainer);
        LinearLayout mClaimContainer = (LinearLayout) findViewById(R.id.alertClaimAppContainer);

        LinearLayout mSignInContainer = (LinearLayout) findViewById(R.id.alertSignInContainer);
        LinearLayout mSignOutContainer = (LinearLayout) findViewById(R.id.alertSignOutContainer);

        LinearLayout video_cam_container = (LinearLayout) findViewById(R.id.video_cam_container);
        LinearLayout help_cam_container = (LinearLayout) findViewById(R.id.help_cam_container);
        TextView activity_title = findViewById(R.id.activityIdTitle);
        activity_title.setText("REF " + ActivityId);
        activity_title.setTextColor(getColor(R.color.sacpocolorPrimary));
        activity_title.setBackground(getDrawable(R.drawable.white_circle_bg));
        activity_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showInfo(v, ActivityId);
                if (!isLive) {
                    showLogs(v, ActivityId);
                }
                else{
                    showInfo(v, ActivityId);
                }
            }
        });

        if (userType.equals("2")) {
            mAttContainer.setVisibility(View.GONE);
            mClaimContainer.setVisibility(View.GONE);
            mSignInContainer.setVisibility(View.GONE);
            mSignOutContainer.setVisibility(View.GONE);
            video_cam_container.setVisibility(View.VISIBLE);
            help_cam_container.setVisibility(View.GONE);
            mMessageContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    inBundle = new Bundle();
                    Intent intent = new Intent(baseApcContext, SChatListDA.class);
                    inBundle.putString("generator", ActivityId);
                    intent.putExtras(inBundle);
                    startActivity(intent);
                    finish();
                }
            });

            mNotificationContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inBundle = new Bundle();
                    Intent intent = new Intent(baseApcContext, SAnnouncement.class);
                    inBundle.putString("generator", ActivityId);
                    intent.putExtras(inBundle);
                    startActivity(intent);
                    finish();
                }
            });
            video_cam_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inBundle = new Bundle();
                    Intent intent = new Intent(baseApcContext, SVideoA.class);
                    inBundle.putString("generator", ActivityId);
                    intent.putExtras(inBundle);
                    startActivity(intent);
                    finish();
                }
            });

            help_cam_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showHelp(v, ActivityId);
                }
            });

        }
        else if (userType.equals("5")) {
            mAttContainer.setVisibility(View.GONE);
            mClaimContainer.setVisibility(View.GONE);
            mSignInContainer.setVisibility(View.GONE);
            mSignOutContainer.setVisibility(View.GONE);
            video_cam_container.setVisibility(View.VISIBLE);
            help_cam_container.setVisibility(View.GONE);
            mMessageContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inBundle = new Bundle();
                    Intent intent = new Intent(baseApcContext, MChatListA.class);
                    inBundle.putString("generator", ActivityId);
                    intent.putExtras(inBundle);
                    startActivity(intent);
                    finish();
                }
            });

            mNotificationContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inBundle = new Bundle();
                    Intent intent = new Intent(baseApcContext, MAnnouncement.class);
                    inBundle.putString("generator", ActivityId);
                    intent.putExtras(inBundle);
                    startActivity(intent);
                    finish();
                }
            });
            video_cam_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inBundle = new Bundle();
                    Intent intent = new Intent(baseApcContext, MVideoA.class);
                    inBundle.putString("generator", ActivityId);
                    intent.putExtras(inBundle);
                    startActivity(intent);
                    finish();
                }
            });

            help_cam_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showHelp(v, ActivityId);
                }
            });
        }
    }
}


