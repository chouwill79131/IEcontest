package com.example.android.BluetoothChat;

import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: kiichi
 * Date: 3/15/12
 * Time: 6:55 PM
 * To change this template use File | Settings | File Templates.
 *
 * Reference:
 *
 * Call Java
 * http://developer.android.com/guide/webapps/webview.html
 *
 * Call Javascript
 * http://developer.android.com/resources/articles/using-webviews.html
 *
 */
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
