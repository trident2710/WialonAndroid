package com.worldtrack.wtapi_android.core;

import android.os.AsyncTask;
import android.util.Log;

import com.worldtrack.wtapi_android.callback.other.RequestTaskCallback;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Worldtrack 19.08.15.
 */
public class HttpRequestTask extends AsyncTask<Void,Void,String> {

    RequestTaskCallback callback;
    String[] request;

    public HttpRequestTask(String[] request, RequestTaskCallback callback)
    {
        this.request = request;
        this.callback  = callback;
    }
    @Override
    protected String doInBackground(Void... params) {
        for (String r:request)
        {
            Log.d("request:", "" + r);
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String[] p = Arrays.copyOfRange(request, 1, request.length);
        return restTemplate.getForObject(request[0],String.class,p);
    }
    @Override
    protected void onPostExecute(String response) {
        Log.d("response:", "" + response);
        callback.onResult(response);
    }

}
