package za.co.sacpo.grantportal.xCubeLib.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import za.co.sacpo.grantportal.xCubeLib.dataObj.FormsObj;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUserSession;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class CheckFile extends AsyncTask<Void, Void, Boolean> {
    private int user_id;
    private String apiURL,ActivityId,StipendId;
    private static final String TAG = CheckFile.class.getSimpleName();
    private final String KEY_STATUS="s";
    private String KEY_DATA="m";
    private Boolean dataLogCall =false;
    private OlumsUserSession userSessionObj;
    private OlumsUtilitySession utilSessionObj;
    private Context contextA;
    private boolean isLive=true;
    public CheckFile( Context context,String apiURL,String ActivityId,String StipendId) {
        apiURL = apiURL;
        ActivityId = ActivityId;
        StipendId = StipendId;
        contextA = context;
    }
    public void printLogsAsync(String funcs,String msg){
        String tag = this.getClass().getSimpleName();
        if(isLive==false) {
            //Log.i("OSG-"+tag+"__"+funcs,msg);
        }
    }
    @Override
    protected void onPreExecute() { }
    @Override
    protected Boolean doInBackground(Void... params) {
        final FormsObj frmObj = new FormsObj();
        final Gson gson = new Gson();
        String FINAL_URL = apiURL;
        printLogsAsync("doInBackground","URL "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(FINAL_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                printLogsAsync("doInBackground","RESPONSE "+response);
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        if(dataM.length()>0) {
                            String formId = ActivityId+"_"+StipendId;
                            String formUrl =dataM.getString("student_form");
                            frmObj.addItem(frmObj.createItem(formId,formUrl));
                            String stringJson = gson.toJson(frmObj);
                            utilSessionObj.setFormsdata(stringJson);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dataLogCall = false;
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(contextA);
        requestQueue.add(stringRequest);
        return dataLogCall;
    }
    @Override
    protected void onPostExecute(final Boolean success) {

    }
    @Override
    protected void onCancelled() {
    }
}