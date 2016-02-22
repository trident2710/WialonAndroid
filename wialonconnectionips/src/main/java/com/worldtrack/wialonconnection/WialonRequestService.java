package com.worldtrack.wialonconnection;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.worldtrack.wialonconnection.data.Credential;

import org.apache.commons.lang3.SerializationUtils;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 * Using for making IPS protocol requests to the Wialon Server
 */
public class WialonRequestService extends Service implements WialonResponseListener {

    public static final String TAG = "WialonRequestService";
    final int CHECK_CONNECTION_TIMEOUT = 5000;

    public static final String WIALON_CREDENTIAL_TAG = "WialonCredential";
    public static final String WIALON_REQUEST_TAG = "WialonActionRequest";
    public static final String WIALON_REQUEST_MESSAGE_VALUE_TAG = "WialonActionMessage";
    public static final String WIALON_REQUEST_DATA_VALUE_TAG = "WialonActionData";
    public static final int WIALON_REQUEST_LOGIN = 0x100;
    public static final int WIALON_REQUEST_DATA = 0x101;
    public static final int WIALON_REQUEST_PING = 0x102;
    public static final int WIALON_REQUEST_SHORT_DATA = 0x103; //@todo: not supported, add support
    public static final int WIALON_REQUEST_BLACK_BOX = 0x104; //@todo: not supported, add support
    public static final int WIALON_REQUEST_MESSAGE = 0x105;
    public static final int WIALON_REQUEST_IMAGE = 0x106; //@todo: not supported, add support
    public static final int WIALON_REQUEST_CONNECT = 0x107;
    public static final int WIALON_REQUEST_DISCONNECT = 0x108;
    public static final int WIALON_REQUEST_UNKNOWN = 0x109;

    public static final String WIALON_RESPONSE_TAG = "WialonActionResponse";
    public static final int WIALON_RESPONSE_CONNECTED = 0x1800;
    public static final int WIALON_RESPONSE_DISCONNECTED = 0x1801;


    private boolean mConnected = false;
    private WialonRequestor mWialonRequestor;
    private Credential mCredential;

    Handler checkConnectionHandler = new Handler();
    Runnable checkConnectionRunnable = new Runnable() {
        @Override
        public void run() {

            if(isConnected()){
                Log.d(TAG, "sending ping request...");
                mWialonRequestor.sendRequest(WIALON_REQUEST_PING);
            }
            else
            {
                Intent intent = new Intent(TAG);
                intent.putExtra(WIALON_RESPONSE_TAG,WIALON_RESPONSE_DISCONNECTED);
                sendBroadcast(intent);

                Log.d(TAG,"connection with wialon lost trying to reconnect...");
                mWialonRequestor.sendRequest(WIALON_REQUEST_DISCONNECT);
                mWialonRequestor.sendRequest(WIALON_REQUEST_CONNECT);
            }
            setConnected(false);
            checkConnectionHandler.postDelayed(this,CHECK_CONNECTION_TIMEOUT);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) throws RuntimeException{
        Log.d(TAG, "lifecycle onStartCommand()");
        byte[] data = intent.getByteArrayExtra(WIALON_CREDENTIAL_TAG);
        if(data!=null)
        {
            try {
                mCredential = (Credential)SerializationUtils.deserialize(data);
                mWialonRequestor = new WialonRequestor(mCredential);
                mWialonRequestor.setResponseListener(this);
                mWialonRequestor.sendRequest(WIALON_REQUEST_CONNECT);
                setConnected(false);
            } catch (Exception e)
            {
                e.printStackTrace();
                stopSelf();
                throw new RuntimeException("unable to get Credential from intent extra");
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public  void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "lifecycle onCreate()");

        registerReceiver(mBroadcastReciever,new IntentFilter(TAG));
    }
    @Override
    public  void onDestroy()
    {
        Log.d(TAG, "lifecycle onDestroy()");
        unregisterReceiver(mBroadcastReciever);
        checkConnectionHandler.removeCallbacks(checkConnectionRunnable);
        checkConnectionHandler = null;
        if(mWialonRequestor!=null)
        mWialonRequestor.stopReceivingMessages();
        mWialonRequestor = null;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    BroadcastReceiver mBroadcastReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(TAG)&&intent.getExtras().getByteArray(WIALON_CREDENTIAL_TAG)!=null)
            {
                Log.d(TAG,"credential changed");
                byte[] data = intent.getByteArrayExtra(WIALON_CREDENTIAL_TAG);
                mCredential = (Credential)SerializationUtils.deserialize(data);
                if(mWialonRequestor!=null)
                {
                    Log.d(TAG,"requestor not null");
                    mWialonRequestor.sendRequest(WIALON_REQUEST_DISCONNECT);
                    setConnected(false);
                    checkConnectionHandler.removeCallbacks(checkConnectionRunnable);
                    mWialonRequestor.setmCredential(mCredential);
                    mWialonRequestor.sendRequest(WIALON_REQUEST_CONNECT);
                } else
                {
                    Log.d(TAG,"requestor null");
                    mCredential = (Credential)SerializationUtils.deserialize(data);
                    mWialonRequestor = new WialonRequestor(mCredential);
                    mWialonRequestor.setResponseListener(WialonRequestService.this);
                    mWialonRequestor.sendRequest(WIALON_REQUEST_CONNECT);
                }
            } else
            {
                if(isConnected() &&intent.getAction().equals(TAG)&&mWialonRequestor!=null)
                {
                    Log.d(TAG,"send request");
                    mWialonRequestor.sendRequest(intent);
                } else Log.e(TAG,"unable to process request");
            }
        }
    };


    @Override
    public void onConnected(int connectionAnswer) {
        switch (connectionAnswer)
        {
            case WialonRequestService.CONNECT_SUCCESSFUL:
            {
                Log.d(TAG, "connected to server");
                mWialonRequestor.sendRequest(WIALON_REQUEST_LOGIN);

                Intent intent = new Intent(TAG);
                intent.putExtra(WIALON_RESPONSE_TAG, WIALON_RESPONSE_CONNECTED);
                sendBroadcast(intent);

                break;
            }
            case WialonRequestService.UNKNOWN_ERROR:
            {
                Log.d(TAG,"connect unknown error");
                setConnected(false);
                break;
            }
        }
    }

    @Override
    public void onDisconnected(int disconnectionAnswer) {
        switch (disconnectionAnswer)
        {
            case WialonRequestService.DISCONNECT_SUCCESSFUL:
            {
                Log.d(TAG, "disconnected from server");
                break;
            }
            case WialonRequestService.UNKNOWN_ERROR:
            {
                Log.d(TAG, "disconnect unknown error");
                break;
            }
        }
        setConnected(false);
    }

    @Override
    public void onLoginAnswer(int loginAnswer) {
        switch (loginAnswer)
        {
            case WialonRequestService.LOGIN_INCORRECT_PASSWORD:
            {
                mWialonRequestor.sendRequest(WIALON_REQUEST_DISCONNECT);
                setConnected(false);
                Toast.makeText(this,getResources().getString(R.string.server_response_login_password_incorrect),Toast.LENGTH_LONG).show();
                Log.d(TAG,"login incorrect password");

                break;
            }
            case WialonRequestService.LOGIN_SUCCESSFUL:
            {
                Intent intent = new Intent(TAG);
                intent.putExtra(WIALON_RESPONSE_TAG,LOGIN_SUCCESSFUL);
                sendBroadcast(intent);

                mWialonRequestor.sendRequest(WIALON_REQUEST_PING);
                setConnected(true);
                checkConnectionHandler.removeCallbacks(checkConnectionRunnable);
                checkConnectionHandler.postDelayed(checkConnectionRunnable, REQUEST_TIMEOUT);
                //Toast.makeText(this,getResources().getString(R.string.server_response_login_successful),Toast.LENGTH_LONG).show();
                Log.d(TAG,"login successful");
                break;
            }
            case WialonRequestService.LOGIN_UNSUCCESSFUL:
            {
                mWialonRequestor.sendRequest(WIALON_REQUEST_DISCONNECT);
                setConnected(false);
                Toast.makeText(this,getResources().getString(R.string.server_response_login_unsuccessful),Toast.LENGTH_LONG).show();
                Log.d(TAG,"login unsuccessful");
                break;
            }
            case WialonRequestService.REQUEST_TIMEOUT:
            {
                mWialonRequestor.sendRequest(WIALON_REQUEST_DISCONNECT);
                Toast.makeText(this,getResources().getString(R.string.server_response_login_request_timeout),Toast.LENGTH_LONG).show();
                Log.d(TAG,"login request timeout");
                setConnected(false);
                break;
            }
            case WialonRequestService.UNKNOWN_ERROR:
            {
                mWialonRequestor.sendRequest(WIALON_REQUEST_DISCONNECT);
                Toast.makeText(this,getResources().getString(R.string.server_response_login_unknown_error),Toast.LENGTH_LONG).show();
                Log.d(TAG,"login unknown error");
                setConnected(false);
                break;
            }
        }

    }

    @Override
    public void onShortDataAnswer(int shortDataAnswer) {
        Log.d(TAG,"short data answer");
        //not supported yet

    }

    @Override
    public void onDataAnswer(int dataAnswer) {
        Log.d(TAG,"data answer");
        switch (dataAnswer)
        {

            case WialonRequestService.DATA_STRUCTURE_PACKAGE_ERROR:
            {
                Log.e(TAG, "data structure package error");
                break;
            }
            case WialonRequestService.DATA_STRUCTURE_INCORRECT_TIME:
            {
                Log.e(TAG, "data structure incorrect time error");
                break;
            }
            case WialonRequestService.DATA_SUCCESSFUL:
            {
                Log.i(TAG, "data successfull");
                break;
            }
            case WialonRequestService.DATA_GET_COORDINATES_ERROR:
            {
                Log.e(TAG, "data get coordinates error");
                break;
            }
            case WialonRequestService.DATA_GET_SCH_ERROR:
            {
                Log.e(TAG, "data get SCH error");
                break;
            }
            case WialonRequestService.DATA_GET_SATELLITE_COUNT_ERROR:
            {
                Log.e(TAG, "data get satellite count error");
                break;
            }
            case WialonRequestService.DATA_GET_IO_ERROR:
            {
                Log.e(TAG, "data get IO error");
                break;
            }
            case WialonRequestService.DATA_GET_ADC_ERROR:
            {
                Log.e(TAG, "data adc error");
                break;
            }
            case WialonRequestService.DATA_GET_ADDITIONAL_PARAMETERS_ERROR:
            {
                Log.e(TAG, "data additional parameters error");
                break;
            }

        }
    }

    @Override
    public void onPingAnswer(int pingAnswer) {
        switch (pingAnswer)
        {
            case WialonRequestService.PING_ANSWER:
            {
                Log.d(TAG,"ping answer");
                setConnected(true);
                break;
            }
            case WialonRequestService.REQUEST_TIMEOUT:
            {
                Log.d(TAG,"ping request timeout");
                setConnected(false);
                break;
            }
            case WialonRequestService.UNKNOWN_ERROR:
            {
                Log.d(TAG,"ping unknown error");
                setConnected(false);
                break;
            }
        }
    }

    @Override
    public void onBlackBoxAnswer(int blackBoxAnswer) {
        Log.d(TAG,"black answer");
    }

    @Override
    public void onMessageAnswer(int messageAnswer) {
        Log.d(TAG,"message answer");
        switch (messageAnswer)
        {
            case WialonRequestService.MESSAGE_SUCCESSFUL:
            {
                Log.d(TAG,"message successful");
                break;
            }
            case WialonRequestService.MESSAGE_UNSUCCESSFUL:
            {
                Log.d(TAG,"message unsuccessful");
                break;
            }
            case WialonRequestService.REQUEST_TIMEOUT:
            {
                Log.d(TAG,"message request timeout");
                setConnected(false);
                break;
            }
            case WialonRequestService.UNKNOWN_ERROR:
            {
                Log.d(TAG,"message unknown error");
                setConnected(false);
                break;
            }
        }
    }

    @Override
    public void onImageAnswer(int imageAnswer) {
        Log.d(TAG,"image answer");
    }

    @Override
    public void onUnkownError() {
        Log.e(TAG,"unknown error occur");
        setConnected(false);
    }

    private synchronized boolean isConnected() {
        if(!isOnline()) setConnected(false);
        return mConnected;
    }

    private synchronized void setConnected(boolean mConnected) {
        this.mConnected = mConnected;
    }
    // checking network connection on device
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
