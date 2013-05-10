package com.example.android.BluetoothChat;

import com.example.facebook_photo.MainActivity;

import android.util.Log;

public class JavaScriptHandler {
	BluetoothChat parentActivity;
    public JavaScriptHandler(BluetoothChat activity)  {
        parentActivity = activity;
    }
    public void setResult(int val){
        Log.v("mylog","JavaScriptHandler.setResult is called : " + val);
        this.parentActivity.changeText(val+"");
    }
    
    public void show_record(){
        this.parentActivity.callJavaScriptFunctionshow_record();
    }
    
    public void connect(){
        this.parentActivity.connect();
    }
    
    public void disconnect(){
        this.parentActivity.disconnect();
    }
    
    public void postFB(){
        this.parentActivity.postFB();
    }
    
    
}
