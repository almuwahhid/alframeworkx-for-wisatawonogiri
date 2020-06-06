package lib.gmsframeworkx.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lib.gmsframeworkx.SuperUser.RequestHandler;

/**
 * Created by root on 3/9/18.
 */

public class GmsRequest {

    public interface OnPostRequest{
        void onPreExecuted();

        void onSuccess(JSONObject response);

        void onFailure(String error);

        Map<String, String> requestParam();

        Map<String, String> requestHeaders();
    }

    public interface OnPostRawRequest{
        void onPreExecuted();

        void onSuccess(JSONObject response);

        void onFailure(String error);

        String requestParam();

        Map<String, String> requestHeaders();
    }


    public interface OnDownloadRequest{
        void onPreExecuted();

        void onSuccess(byte[] response, InputStreamVolleyRequest i);

        void onFailure(String error);

        Map<String, String> requestParam();

        Map<String, String> requestHeaders();
    }
    public interface OnGetRequest{
        void onPreExecuted();

        void onSuccess(JSONObject response);

        void onFailure(String error);

        Map<String, String> requestParam();

        Map<String, String> requestHeaders();
    }
    public interface OnMultipartRequest{
        Map<String, VolleyMultipartRequest.DataPart> requestData();

        void onPreExecuted();

        void onSuccess(JSONObject response);

        void onFailure(String error);

        Map<String, String> requestHeaders();

        Map<String, String> requestParam();

    }

    public static InputStreamVolleyRequest request;

    public static void POSTDownload(String URL, final Context context, final OnDownloadRequest onPostRequest){

        request = new InputStreamVolleyRequest(Request.Method.POST, URL,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        try {
                            onPostRequest.onSuccess(response, request);
                        }catch (NullPointerException e){

                        }
                    }
                } ,new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO handle the error
                error.printStackTrace();
                onPostRequest.onFailure(error.toString());
            }
        }, onPostRequest.requestParam());
        RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack());
        mRequestQueue.add(request);
        onPostRequest.onPreExecuted();
    }

    public static void GET(String URL, final Context context, final OnGetRequest onGetRequest){
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                URL, "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onGetRequest.onSuccess(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GmsStatic.showGoTroError(context, error);
                String errorCode = "1012";
                if (error instanceof NetworkError) {
                    errorCode = "1012";
                } else if (error instanceof ServerError) {
                    errorCode = "503";
                } else if (error instanceof NoConnectionError) {
                    errorCode = "1019";
                } else if (error instanceof TimeoutError) {
                    errorCode = "522";
                }
                onGetRequest.onFailure(errorCode);
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return onGetRequest.requestHeaders();
            }

        };
        request.setShouldCache(false);
        RequestHandler.getInstance().addToRequestQueue(request, GmsStatic.DateInMilis()+URL);
        onGetRequest.onPreExecuted();
    }

    public static void POST(final String URL, final Context context, final OnPostRequest onPostRequest){
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("gmsResponse "+URL, "onResponse: "+response);
                            JSONObject jsonObject = new JSONObject(response);
                            onPostRequest.onSuccess(jsonObject);
                        } catch (JSONException e) {
                            onPostRequest.onFailure(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GmsStatic.showGoTroError(context, error);
                String errorCode = "1012";
                if (error instanceof NetworkError) {
                    errorCode = "1012";
                } else if (error instanceof ServerError) {
                    errorCode = "503";
                } else if (error instanceof NoConnectionError) {
                    errorCode = "1019";
                } else if (error instanceof TimeoutError) {
                    errorCode = "522";
                }
                onPostRequest.onFailure(errorCode);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param;
                param = onPostRequest.requestParam();
                Log.d("gmsParams "+URL, "requestParam: "+param);
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return onPostRequest.requestHeaders();
            }
        };
        RequestHandler.getInstance().addToRequestQueue(request, GmsStatic.DateInMilis()+URL);
        onPostRequest.onPreExecuted();
    }

    public static void POSTFormData(final String URL, final Context context, final OnPostRequest onPostRequest){
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("gmsResponse "+URL, "onResponse: "+response);
                            JSONObject jsonObject = new JSONObject(response);
                            onPostRequest.onSuccess(jsonObject);
                        } catch (JSONException e) {
                            onPostRequest.onFailure(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GmsStatic.showGoTroError(context, error);
                String errorCode = "1012";
                if (error instanceof NetworkError) {
                    errorCode = "1012";
                } else if (error instanceof ServerError) {
                    errorCode = "503";
                } else if (error instanceof NoConnectionError) {
                    errorCode = "1019";
                } else if (error instanceof TimeoutError) {
                    errorCode = "522";
                }
                onPostRequest.onFailure(errorCode);
            }
        }) {
            @Override
            public String getBodyContentType() {
                Map<String, String> pars = new HashMap<String, String>();
                pars.put("Content-Type", "application/x-www-form-urlencoded");
                //return pars;
                return "application/x-www-form-urlencoded";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param;
                param = onPostRequest.requestParam();
                Log.d("gmsParams "+URL, "requestParam: "+param);
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return onPostRequest.requestHeaders();
            }
        };
        RequestHandler.getInstance().addToRequestQueue(request, GmsStatic.DateInMilis()+URL);
        onPostRequest.onPreExecuted();
    }

    public static void POSTRaw(final String URL, final Context context, final OnPostRawRequest onPostRequest){
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("gmsResponse "+URL, "onResponse: "+response);
                            JSONObject jsonObject = new JSONObject(response);
                            onPostRequest.onSuccess(jsonObject);
                        } catch (JSONException e) {
                            onPostRequest.onFailure(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GmsStatic.showGoTroError(context, error);
                String errorCode = "1012";
                if (error instanceof NetworkError) {
                    errorCode = "1012";
                } else if (error instanceof ServerError) {
                    errorCode = "503";
                } else if (error instanceof NoConnectionError) {
                    errorCode = "1019";
                } else if (error instanceof TimeoutError) {
                    errorCode = "522";
                }
                onPostRequest.onFailure(errorCode);
            }
        }) {
            /*@Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param;
                param = onPostRequest.requestParam();
                Log.d("gmsParams "+URL, "requestParam: "+param);
                return param;
            }*/

            @Override
            public byte[] getBody() throws AuthFailureError {
                String param = onPostRequest.requestParam();
                return param.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return onPostRequest.requestHeaders();
            }
        };
        RequestHandler.getInstance().addToRequestQueue(request, GmsStatic.DateInMilis()+URL);
        onPostRequest.onPreExecuted();
    }

    public static void POSTMultipart(final String URL, final Context context, final OnMultipartRequest onPostRequest){
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    onPostRequest.onSuccess(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GmsStatic.showGoTroError(context, error);
                NetworkResponse networkResponse = error.networkResponse;
                Log.d("gmsResponseFailure ", "onResponse: "+URL);
                String errorCode = "1012";
                if (error instanceof NetworkError) {
                    errorCode = "1012";
                } else if (error instanceof ServerError) {
                    errorCode = "503";
                } else if (error instanceof NoConnectionError) {
                    errorCode = "1019";
                } else if (error instanceof TimeoutError) {
                    errorCode = "522";
                }
                onPostRequest.onFailure(errorCode);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return onPostRequest.requestHeaders();
            }

            @Override
            protected Map<String, String> getParams() {
                return onPostRequest.requestParam();
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                return onPostRequest.requestData();
            }
        };
//        multipartRequest.setRetryPolicy(new DefaultRetryPolicy( 30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        multipartRequest.setShouldCache(false);
        RequestHandler.getInstance().addToRequestQueue(multipartRequest, GmsStatic.DateInMilis()+URL);
        onPostRequest.onPreExecuted();
    }
}
