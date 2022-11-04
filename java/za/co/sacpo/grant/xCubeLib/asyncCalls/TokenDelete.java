package za.co.sacpo.grant.xCubeLib.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

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
        try
        {
            FirebaseInstanceId.getInstance().deleteInstanceId();
            //Log.i("OSG-"+TAG,"TOKEN DELETE");
            return true;
        } catch (IOException e)
        {
            //Log.i("OSG-"+TAG,"TOKEN DELETE Error -"+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    @Override
    protected void onPostExecute(final Boolean success) {
    }
    @Override
    protected void onCancelled() {
    }


}
