package com.worldtrack.wialonconnection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.worldtrack.wialonconnection.data.IPSDataMessage;

/**
 * Worldtrack 07.07.15.
 */
class IPSBuilder
{
    public IPSBuilder()
    {

    }
    public String buildLoginRequest(String IMEINumber,String password)
    {
        String request = "";
        String body = "";
        request+="#L#";
        body+=IMEINumber;
        body+=";";
        if(password==null) body+="NA"; else body+=password;
        request+=body;
        request+="\r\n";
        Log.d(WialonRequestService.TAG,request);
        return request;
    }
    public String buildDataRequest(IPSDataMessage data)
    {
        return data.toIPSProtocolString();
    }
    public String buildPingRequest()
    {
        return "#P#\r\n";
    }
    public String buildMessageRequest(String message)
    {
        return "#M#"+message+"\r\n";
    }


    //@TODO: support all answers
    public synchronized Bundle parseResponse(String value) throws Exception{
        if(value==null)
        {
            return errorResponse();
        }
        try {
            String[] res = value.split("#");
            switch (res[1]) {
                case "AL": return loginResponse(res[2]);
                case "AP": return pingResponse();
                case "AM": return messageResponse(res[2]);
                case "AD": return dataResponse(res[2]);
                default:   return errorResponse();
            }
        } catch (Exception e){
            e.printStackTrace();
            return errorResponse();
        }
    }

    Bundle loginResponse(@NonNull String value)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(WialonRequestor.RESULT_COMMAND_ARGUMENT, WialonRequestService.WIALON_REQUEST_LOGIN);
        switch (value) {
            case "0": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.LOGIN_UNSUCCESSFUL);
                break;
            }
            case "1": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.LOGIN_SUCCESSFUL);
                break;
            }
            case "01": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.LOGIN_INCORRECT_PASSWORD);
                break;
            }
            default: {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.UNKNOWN_ERROR);
            }
        }
        return bundle;
    }
    Bundle dataResponse(@NonNull String value)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(WialonRequestor.RESULT_COMMAND_ARGUMENT, WialonRequestService.WIALON_REQUEST_DATA);
        switch (value) {
            case "-1": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.DATA_STRUCTURE_PACKAGE_ERROR);
                break;
            }
            case "0": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.DATA_STRUCTURE_INCORRECT_TIME);
                break;
            }
            case "1": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.DATA_SUCCESSFUL);
                break;
            }
            case "10": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.DATA_GET_COORDINATES_ERROR);
                break;
            }
            case "11": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.DATA_GET_SCH_ERROR);
                break;
            }
            case "12": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.DATA_GET_SATELLITE_COUNT_ERROR);
                break;
            }
            case "13": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.DATA_GET_IO_ERROR);
                break;
            }
            case "14": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.DATA_GET_ADC_ERROR);
                break;
            }
            case "15": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.DATA_GET_ADDITIONAL_PARAMETERS_ERROR);
                break;
            }
            default: {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.UNKNOWN_ERROR);
            }
        }
        return bundle;
    }
    Bundle pingResponse()
    {
        Bundle bundle = new Bundle();
        bundle.putInt(WialonRequestor.RESULT_COMMAND_ARGUMENT, WialonRequestService.WIALON_REQUEST_PING);
        bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.PING_ANSWER);
        return bundle;
    }
    Bundle messageResponse(@NonNull String value)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(WialonRequestor.RESULT_COMMAND_ARGUMENT, WialonRequestService.WIALON_REQUEST_MESSAGE);
        switch (value) {
            case "0": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.MESSAGE_UNSUCCESSFUL);
                break;
            }
            case "1": {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.MESSAGE_SUCCESSFUL);
                break;
            }
            default: {
                bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.UNKNOWN_ERROR);
            }
        }
        return bundle;
    }
    Bundle errorResponse()
    {
        Bundle bundle = new Bundle();
        bundle.putInt(WialonRequestor.RESULT_COMMAND_ARGUMENT, WialonRequestService.WIALON_REQUEST_UNKNOWN);
        bundle.putInt(WialonRequestor.RESULT_VALUE_ARGUMENT, WialonResponseListener.UNKNOWN_ERROR);
        return bundle;
    }
}
