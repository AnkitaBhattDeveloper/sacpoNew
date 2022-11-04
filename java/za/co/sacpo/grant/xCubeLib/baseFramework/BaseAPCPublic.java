package za.co.sacpo.grant.xCubeLib.baseFramework;


import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import za.co.sacpo.grant.xCubeLib.session.OlumsUserSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public abstract class BaseAPCPublic extends BaseAPC {
    protected OlumsUserSession userSessionObj;
    protected OlumsUtilitySession utilSessionObj;
    public void printLogs(String tag,String funcs,String msg){
        if(isLive==false) {
            //Log.i("OSG-" + tag + "__" + funcs, msg);
        }
    }

    protected void validateLogin(Context baseApcContext,AppCompatActivity activityIn){
        userSessionObj = new OlumsUserSession(baseApcContext);
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        final int u_id = userSessionObj.getUserId();
        boolean session_available;
        if (userSessionObj.getHasSession()){
            printLogs("BASEAPCPUBLIC","Validate Login","U ID"+u_id);
            session_available = u_id > 0;
        }
        else{
            session_available = false;
        }
        printLogs("BASEAPCPUBLIC","Validate Login","U ID"+u_id);
        if(session_available){
            switch(userSessionObj.getUserType()){
                case "2":
                    Intent intent = new Intent(baseApcContext,SDashboardDA.class);
                    startActivity(intent);
                    activityIn.finish();
                break;
                case "5":
                    Intent intentM = new Intent(baseApcContext,MDashboardDA.class);
                    startActivity(intentM);
                    activityIn.finish();
                break;
/*
                case "10":
                    Intent intentGA = new Intent(baseApcContext, GADashboardDA.class);
                    startActivity(intentGA);
                    activityIn.finish();
                break;
                case "9":
                    Intent intentGM = new Intent(baseApcContext, GMDashboardA.class);
                    startActivity(intentGM);
                    activityIn.finish();
                break;

                case "11":
                    Intent intentFM = new Intent(baseApcContext, FMDashboardA.class);
                    startActivity(intentFM);
                    activityIn.finish();
                break;

                case "12":
                    Intent intentSM = new Intent(baseApcContext, SETADashboardDA.class);
                    startActivity(intentSM);
                    activityIn.finish();
                break;

                case "13":
                    Intent intentSV = new Intent(baseApcContext, SVDashboardA.class);
                    startActivity(intentSV);
                    activityIn.finish();
                    break;*/
            }
        }
    }
}