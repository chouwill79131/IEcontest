package com.example.android.BluetoothChat;

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

}
