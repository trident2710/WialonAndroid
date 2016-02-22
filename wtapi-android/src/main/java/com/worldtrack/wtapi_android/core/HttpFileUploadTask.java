package com.worldtrack.wtapi_android.core;

import android.os.AsyncTask;
import android.util.Log;

import com.worldtrack.wtapi_android.callback.other.RequestTaskCallback;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Worldtrack 04.09.15.
 */
public class HttpFileUploadTask extends AsyncTask<Void,Void,String> {
    String TAG = "HttpFileUpload";
    RequestTaskCallback callback;
    private MultiValueMap<String, Object> formData;
    String[] request;

    public HttpFileUploadTask(Object fileToUpload,String[] url,RequestTaskCallback callback)
    {
        this.formData = new LinkedMultiValueMap<String, Object>();
        formData.add("icon_file",fileToUpload);
        this.request = url;
        this.callback = callback;
    }


    @Override
    protected String doInBackground(Void... params) {
        try {
            // The URL for making the POST request

            HttpHeaders requestHeaders = new HttpHeaders();

            // Sending multipart/form-data
            requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Populate the MultiValueMap being serialized and headers in an HttpEntity object to use for the request
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
                    formData, requestHeaders);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate(true);

            String[] p = Arrays.copyOfRange(request, 1, request.length);
            // Make the network request, posting the message, expecting a String in response from the server
            ResponseEntity<String> response = restTemplate.exchange(request[0], HttpMethod.POST, requestEntity,
                    String.class,p);

            // Return the response body to display to the user
            return response.getBody();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return null;
    }



    @Override
    protected void onPostExecute(String result) {
        callback.onResult(result);
    }
}
