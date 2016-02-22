package com.worldtrack.wialonconnection;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.worldtrack.wialonconnection.data.Credential;
import com.worldtrack.wialonconnection.data.IPSDataMessage;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.net.telnet.TelnetClient;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * Worldtrack 03.07.15.
 */
class WialonRequestor {

    static final String TAG = "WialonOutputTask";
    public static final String RESULT_COMMAND_ARGUMENT = "comand";
    public static final String RESULT_VALUE_ARGUMENT = "value";
//    String ipAddress;
//    int port;
//    String IMEINumber;
//    String password;
    private Credential mCredential;
    private WialonResponseListener mListener;
    private IPSBuilder builder;
    private TelnetClient telnet;
    private InputStream in;
    private PrintStream out;
    private WialonReadTask mWialonReadTask;


    public WialonRequestor(Credential credential)
    {
//        this.ipAddress = ipAddress;
//        this.port = port;
//        this.IMEINumber = IMEINumber;
//        this.password = password;
        mCredential = credential;
        builder = new IPSBuilder();
        mWialonReadTask = new WialonReadTask();
        telnet = new TelnetClient();
    }

    public Credential getmCredential() {
        return mCredential;
    }

    public void setmCredential(Credential mCredential) {
        this.mCredential = mCredential;
    }

    /**
     * Send request without additional params
     * @param requestCode
     */
    public void sendRequest(int requestCode)
    {
        Bundle params = new Bundle();
        params.putInt(WialonRequestService.WIALON_REQUEST_TAG, requestCode);
        new WialonOutputTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
    }

    /**
     * Send request with additional data such as Message, Data etc.
     * @param requestIntent
     */
    public void sendRequest(Intent requestIntent)
    {
        int requestCode = requestIntent.getIntExtra(WialonRequestService.WIALON_REQUEST_TAG,-1);
        if(requestCode!=-1)
        {
            Bundle params = new Bundle();
            params.putInt(WialonRequestService.WIALON_REQUEST_TAG, requestCode);
            switch (requestCode)
            {
                case WialonRequestService.WIALON_REQUEST_MESSAGE:
                {
                    params.putString(WialonRequestService.WIALON_REQUEST_MESSAGE_VALUE_TAG,
                            requestIntent.getStringExtra(WialonRequestService.WIALON_REQUEST_MESSAGE_VALUE_TAG));
                    break;
                }
                case WialonRequestService.WIALON_REQUEST_DATA:
                {
                    params.putByteArray(WialonRequestService.WIALON_REQUEST_DATA_VALUE_TAG,
                            requestIntent.getByteArrayExtra(WialonRequestService.WIALON_REQUEST_DATA_VALUE_TAG));
                    break;
                }
            }
            new WialonOutputTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        }
    }
    public void setResponseListener(WialonResponseListener listener) {
        mListener = listener;
    }
    public void stopReceivingMessages()
    {
        Log.d(TAG,"stopReceivingMessages");
        mWialonReadTask.cancel(true);
    }

    //@TODO: append with all possible commands
    class WialonOutputTask extends AsyncTask<Bundle, Void, Bundle> {

        int connect() {
            try {
                telnet  = new TelnetClient();
                telnet.connect(mCredential.getIpAddress(), mCredential.getPort());
                // Get input and output stream references
                in = telnet.getInputStream();
                out = new PrintStream(telnet.getOutputStream());

                mWialonReadTask = new WialonReadTask();
                mWialonReadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            } catch (Exception e)
            {
                Log.e(TAG, "error while connecting to wialon server");
                e.printStackTrace();
                return WialonResponseListener.UNKNOWN_ERROR;
            }

            return WialonResponseListener.CONNECT_SUCCESSFUL;
        }

        int disconnect() {

            try {
                mWialonReadTask.cancel(true);
                if(telnet!=null&&telnet.isConnected()) telnet.disconnect();
//                in.close();
//                out.close();


            } catch (Exception e) {
                Log.e(TAG, "error while disconnecting from wialon server");
                e.printStackTrace();
                return WialonResponseListener.UNKNOWN_ERROR;
            }
            return WialonResponseListener.DISCONNECT_SUCCESSFUL;
        }

        int login() {
            try {
                if(!write(builder.buildLoginRequest(mCredential.getUnitID(), mCredential.getPassword()))) return WialonResponseListener.UNKNOWN_ERROR;
            } catch (Exception e) {
                Log.e(TAG, "error while write login message");
                e.printStackTrace();
                return WialonResponseListener.UNKNOWN_ERROR;
            }
            return WialonResponseListener.SENT_SUCCESSFUL;
        }

        int ping() {
            try {
                if(!write(builder.buildPingRequest())) return WialonResponseListener.UNKNOWN_ERROR;
            } catch (Exception e) {
                Log.e(TAG, "error while write ping message");
                e.printStackTrace();
                return WialonResponseListener.UNKNOWN_ERROR;
            }
            return WialonResponseListener.SENT_SUCCESSFUL;
        }
        int message(String message) {
            try {
                if(!write(builder.buildMessageRequest(message))) return WialonResponseListener.UNKNOWN_ERROR;
            } catch (Exception e) {
                Log.e(TAG, "error while write message message");
                e.printStackTrace();
                return WialonResponseListener.UNKNOWN_ERROR;
            }
            return WialonResponseListener.SENT_SUCCESSFUL;
        }
        int data(IPSDataMessage data) {
            try {
                if(!write(builder.buildDataRequest(data))) return WialonResponseListener.UNKNOWN_ERROR;;
            } catch (Exception e) {
                Log.e(TAG, "error while write data");
                e.printStackTrace();
                return WialonResponseListener.UNKNOWN_ERROR;
            }
            return WialonResponseListener.SENT_SUCCESSFUL;
        }


        public boolean write(String value) {
            try {
                Log.d(TAG,"sending package to server: "+value);
                out.println(value);
                out.flush();
                return true;
            } catch (Exception e) {
                Log.e(TAG, "error while write message");
                e.printStackTrace();
            }
            return false;
        }

        //@TODO: all cases must be implemented
        @Override
        protected Bundle doInBackground(Bundle... params) {
            Bundle res = new Bundle();

            switch (params[0].getInt(WialonRequestService.WIALON_REQUEST_TAG, 0)) {
                case WialonRequestService.WIALON_REQUEST_CONNECT:{
                    res.putInt(RESULT_COMMAND_ARGUMENT,WialonRequestService.WIALON_REQUEST_CONNECT);
                    res.putInt(RESULT_VALUE_ARGUMENT,connect());
                    return res;
                }
                case WialonRequestService.WIALON_REQUEST_DISCONNECT:{
                    mWialonReadTask.cancel(true);
                    res.putInt(RESULT_COMMAND_ARGUMENT,WialonRequestService.WIALON_REQUEST_DISCONNECT);
                    res.putInt(RESULT_VALUE_ARGUMENT,disconnect());
                    return res;
                }
                case WialonRequestService.WIALON_REQUEST_LOGIN:{
                    res.putInt(RESULT_COMMAND_ARGUMENT,WialonRequestService.WIALON_REQUEST_LOGIN);
                    res.putInt(RESULT_VALUE_ARGUMENT, login());
                    return res;
                }
                case WialonRequestService.WIALON_REQUEST_PING:{
                    res.putInt(RESULT_COMMAND_ARGUMENT,WialonRequestService.WIALON_REQUEST_PING);
                    res.putInt(RESULT_VALUE_ARGUMENT, ping());
                    return res;
                }
                case WialonRequestService.WIALON_REQUEST_DATA:{
                    res.putInt(RESULT_COMMAND_ARGUMENT,WialonRequestService.WIALON_REQUEST_DATA);
                    res.putInt(RESULT_VALUE_ARGUMENT, data((IPSDataMessage)SerializationUtils.deserialize(params[0].getByteArray(WialonRequestService.WIALON_REQUEST_DATA_VALUE_TAG))));
                    return res;
                }
                case WialonRequestService.WIALON_REQUEST_SHORT_DATA:{
                    res.putInt(RESULT_COMMAND_ARGUMENT,WialonRequestService.WIALON_REQUEST_DATA);
                    res.putInt(RESULT_VALUE_ARGUMENT, WialonResponseListener.UNKNOWN_ERROR);
                    return res;
                }
                case WialonRequestService.WIALON_REQUEST_BLACK_BOX:{
                    res.putInt(RESULT_COMMAND_ARGUMENT,WialonRequestService.WIALON_REQUEST_BLACK_BOX);
                    res.putInt(RESULT_VALUE_ARGUMENT, WialonResponseListener.UNKNOWN_ERROR);
                    return res;
                }
                case WialonRequestService.WIALON_REQUEST_MESSAGE:{
                    res.putInt(RESULT_COMMAND_ARGUMENT,WialonRequestService.WIALON_REQUEST_MESSAGE);
                    res.putInt(RESULT_VALUE_ARGUMENT, message(params[0].getString(WialonRequestService.WIALON_REQUEST_MESSAGE_VALUE_TAG)));
                    return res;
                }
                case WialonRequestService.WIALON_REQUEST_IMAGE:{
                    res.putInt(RESULT_COMMAND_ARGUMENT,WialonRequestService.WIALON_REQUEST_IMAGE);
                    res.putInt(RESULT_VALUE_ARGUMENT, WialonResponseListener.UNKNOWN_ERROR);
                    return res;
                }
                default: throw new UnsupportedOperationException("Unsupported action, must be one of constants of WialonRequestService.class");
            }
        }

        @Override
        protected void onPostExecute(Bundle result) {
            super.onPostExecute(result);
            try {
                notifyListener(result);
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }

    }
    class WialonReadTask extends AsyncTask<Void, Bundle, Void> {

        public String read() {
            try {
                StringBuffer sb = new StringBuffer();
                boolean flag = true;
                while (flag)
                {
                    char ch = (char) in.read();
                    while (ch!=65535&&ch!=' '&&ch!='\r'&&ch!='\n') {
                        sb.append(ch);
                        ch = (char) in.read();
                        flag = false;
                    }
                }
                //Log.d(TAG,"Message from Wialon server: "+sb.toString());
                return sb.toString();
            } catch (Exception e) {
                Log.e(TAG,"error while listening new messages");
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected Void doInBackground(Void... params) {
            while (!isCancelled())
            {
                String s = read();
                try {
                    Bundle response = builder.parseResponse(s);
                    publishProgress(response);
                } catch (Exception e)
                {
                    Log.e(TAG, "error while parsing response message from wialon server");
                    e.printStackTrace();
                    sendRequest(WialonRequestService.WIALON_REQUEST_DISCONNECT);
                    sendRequest(WialonRequestService.WIALON_REQUEST_CONNECT);
                    break;


                }

            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Bundle... result)
        {
            super.onProgressUpdate(result);
            notifyListener(result[0]);
        }

    }

    synchronized void notifyListener(Bundle result) throws RuntimeException
    {
        int val = result.getInt(RESULT_VALUE_ARGUMENT,-100);
        int command = result.getInt(RESULT_COMMAND_ARGUMENT,-100);
        if(val==-100||command==-100) throw new RuntimeException("unable to find RESULT_VALUE_ARGUMENT or RESULT_COMMAND_ARGUMENT in bundle");

        if(val!= WialonResponseListener.SENT_SUCCESSFUL)
        if (mListener != null) {
            switch (result.getInt(RESULT_COMMAND_ARGUMENT)) {
                case WialonRequestService.WIALON_REQUEST_CONNECT: {
                    mListener.onConnected(val);
                    break;
                }
                case WialonRequestService.WIALON_REQUEST_DISCONNECT: {
                    mListener.onDisconnected(val);
                    break;
                }
                case WialonRequestService.WIALON_REQUEST_LOGIN: {
                    mListener.onLoginAnswer(val);
                    break;
                }
                case WialonRequestService.WIALON_REQUEST_DATA: {
                    mListener.onDataAnswer(val);
                    break;
                }
                case WialonRequestService.WIALON_REQUEST_SHORT_DATA: {
                    mListener.onShortDataAnswer(val);
                    break;
                }
                case WialonRequestService.WIALON_REQUEST_PING: {
                    mListener.onPingAnswer(val);
                    break;
                }
                case WialonRequestService.WIALON_REQUEST_BLACK_BOX: {
                    mListener.onBlackBoxAnswer(val);
                    break;
                }
                case WialonRequestService.WIALON_REQUEST_MESSAGE: {
                    mListener.onMessageAnswer(val);
                    break;
                }
                case WialonRequestService.WIALON_REQUEST_IMAGE: {
                    mListener.onImageAnswer(val);
                    break;
                }
                case WialonRequestService.WIALON_REQUEST_UNKNOWN: {
                    mListener.onUnkownError();
                    break;
                }
                default:
                    throw new UnsupportedOperationException("Unsupported action, must be one of constants of WialonRequestService.class");
            }
        }
    }


}



