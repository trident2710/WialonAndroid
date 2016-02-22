package com.worldtrack.wialonconnection.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Worldtrack 16.07.15.
 */
public class IPSDataMessage implements Serializable{

    protected final String DATA_INTEGER = "1";
    protected final String DATA_DOUBLE = "2";
    protected final String DATA_STRING = "3";

    private String date = "NA";
    private String time = "NA";
    private String lat1 = "NA";
    private String lat2 = "NA";
    private String lon1 = "NA";
    private String lon2 = "NA";
    private String speed = "NA";
    private String course = "NA";
    private String height = "NA";
    private String sats = "NA";
    private String hdop = "NA";
    private String inputs = "NA";
    private String outputs = "NA";
    private String ibutton = "NA";
    private String adc = "";
    private ArrayList<String[]> params = null;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLat1() {
        return lat1;
    }

    public void setLat1(String lat1) {
        this.lat1 = lat1;
    }

    public String getLat2() {
        return lat2;
    }

    public void setLat2(String lat2) {
        this.lat2 = lat2;
    }

    public String getLon1() {
        return lon1;
    }

    public void setLon1(String lon1) {
        this.lon1 = lon1;
    }

    public String getLon2() {
        return lon2;
    }

    public void setLon2(String lon2) {
        this.lon2 = lon2;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSats() {
        return sats;
    }

    public void setSats(String sats) {
        this.sats = sats;
    }

    public String getHdop() {
        return hdop;
    }

    public void setHdop(String hdop) {
        this.hdop = hdop;
    }

    public String getInputs() {
        return inputs;
    }

    public void setInputs(String inputs) {
        this.inputs = inputs;
    }

    public String getOutputs() {
        return outputs;
    }

    public void setOutputs(String outputs) {
        this.outputs = outputs;
    }

    public String getIbutton() {
        return ibutton;
    }

    public void setIbutton(String ibutton) {
        this.ibutton = ibutton;
    }

    public ArrayList<String[]> getParams() {
        return params;
    }

    public void setParams(ArrayList<String[]> params) {
        this.params = params;
    }

    public IPSDataMessage(String date, String time, String lat1, String lat2,
                          String lon1, String lon2, String speed, String course,
                          String height, String sats, String hdop, String inputs,
                          String outputs, String ibutton, ArrayList<String[]> params) {
        this.date = (date == null)?"NA":date;
        this.time = (time == null)?"NA":time;
        this.lat1 = (lat1 == null)?"NA":lat1;
        this.lat2 = (lat2 == null)?"NA":lat2;
        this.lon1 = (lon1 == null)?"NA":lon1;
        this.lon2 = (lon2 == null)?"NA":lon2;
        this.speed = (speed == null)?"NA":speed;
        this.course = (course == null)?"NA":course;
        this.height = (height == null)?"NA":height;
        this.sats = (sats == null)?"NA":sats;
        this.hdop = (hdop == null)?"NA":hdop;
        this.inputs = (inputs == null)?"NA":inputs;
        this.outputs = (outputs == null)?"NA":outputs;
        this.ibutton = (ibutton ==null)?"NA":ibutton;
        this.params = params;
    }



    @Override
    public String toString()
    {
        return toIPSProtocolString();
    }
    public String toIPSProtocolString()
    {
        String body = "#D#";
        body+=date+";";
        body+=time+";";
        body+=lat1+";";
        body+=lat2+";";
        body+=lon1+";";
        body+=lon2+";";
        body+=speed+";";
        body+=course+";";
        body+=height+";";
        body+=sats+";";
        body+=hdop+";";
        body+=inputs+";";
        body+=outputs+";";
        body+=adc+";";
        body+=ibutton+";";
        body+=paramsToIPSProtocolString();
        body+="\r\n";
        return body;

    }
    private String paramsToIPSProtocolString()
    {
        String buf = "";
        if(params!=null)
        for (String[] p : params)
        {
            for (int i = 0;i<3;i++)
            {
                buf+=p[i];
                if(i<p.length-1) buf+=":";else buf+=",";
            }
        }
        return buf;
    }
}
