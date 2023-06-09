package za.co.sacpo.grant.xCubeLib.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

//import com.google.firebase.iid.FirebaseInstanceId;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

public class TokenDelete extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = TokenDelete.class.getSimpleName();
    private Context contextA;
    public TokenDelete(Context context) {
        contextA = context;
    }
    @Override
    protected void onPreExecute() { }
    @Override
    protected Boolean doInBackground(Void... params) {
        FirebaseMessaging.getInstance().deleteToken();
        //FirebaseInstanceId.getInstance().deleteInstanceId();
        //Log.i("OSG-"+TAG,"TOKEN DELETE");
        return true;
    }
    @Override
    protected void onPostExecute(final Boolean success) {
    }
    @Override
    protected void onCancelled() {
    }


}
