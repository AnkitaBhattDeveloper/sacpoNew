package za.co.sacpo.grantportal.xCubeLib.services;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grantportal.xCubeLib.session.OlumsFirebaseSession;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUserSession;
import za.co.sacpo.grantportal.xCubeLib.dataObj.TokenObject;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;


public class OlumsInstanceIdService extends FirebaseMessagingService {

    private static final String TAG = OlumsInstanceIdService.class.getSimpleName();
    private RequestQueue queue;
    private TokenObject tokenObject;
    private OlumsFirebaseSession mySharedPreference;
    private OlumsUserSession userSessionObj;
    /*@Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d(TAG, "Token Value: " + refreshedToken);
        sendTheRegisteredTokenToWebServer(refreshedToken);
    }*/

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //Log.d("NEW_TOKEN",s);
    }
    private void sendTheRegisteredTokenToWebServer(final String token){
        queue = Volley.newRequestQueue(getApplicationContext());
        mySharedPreference = new OlumsFirebaseSession(getApplicationContext());
        userSessionObj = new OlumsUserSession(getApplicationContext());
        int u_id = userSessionObj.getUserId();
        String UPDATE_URL = URLHelper.DOMAIN_BASE_URL+URLHelper.TOKEN_UPDATE+"/id/"+u_id;
        //Log.i(TAG,UPDATE_URL);
        //Log.i(TAG,"token"+token);
        mySharedPreference.clearAllSubscriptions();
        StringRequest stringPostRequest = new StringRequest(Request.Method.PUT, UPDATE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                tokenObject = gson.fromJson(response, TokenObject.class);
                if (null == tokenObject) {
                    //Toast.makeText(getApplicationContext(), "Can't send a token to the server", Toast.LENGTH_LONG).show();
                    mySharedPreference.saveNotificationSubscription(false);
                } else {
                    //Toast.makeText(getAX  JpplicationContext(), "Token successfully send to server", Toast.LENGTH_LONG).show();
                    mySharedPreference.saveNotificationSubscription(true);
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put(URLHelper.TOKEN_TO_SERVER_KEY, token);
            return params;
        }
    };
    queue.add(stringPostRequest);
    }
}