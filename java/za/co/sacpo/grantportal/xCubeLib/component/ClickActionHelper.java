package za.co.sacpo.grantportal.xCubeLib.component;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by vin on 12/9/16.
 */
public class ClickActionHelper {
    public static void startActivity(String className, Bundle extras, Context context){
        Class cls;
        try {
            cls = Class.forName(className);
            Intent i = new Intent(context, cls);
            i.putExtras(extras);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }catch(ClassNotFoundException e){}
    }
}
