/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.BluetoothChat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BluetoothChat extends Activity {
	// Debugging
	private static final String TAG = "BluetoothChat";
	private static final boolean D = true;

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	public int count = 0;
	public int n = 0; // 計算陣列內有幾個元素
	public int i = 0; // 取樣計數
	public int row = 0;
	public int fine = 0;
	public int peakcount = 0;
	public int p1;
	public int p2;
	public int p3;
	
	int[] point = new int[1000];
	String readMessage = "";

	public static int var = 125;
	String toJS = "[";
	String test50 = "[0.21,0.182,0.206,0.182,0.198,0.182,0.202,0.178,0.202,0.186,0.21,0.206,0.274,0.266,0.322,0.282,0.318,0.262,0.282,0.226,0.246,0.198,0.214,0.182,0.226,0.19,0.222,0.202,0.234,0.21,0.246,0.21,0.238,0.202,0.218,0.194,0.214,0.17,0.206,0.182,0.202,0.182,0.198,0.174,0.206,0.162,0.202,0.206,0.286,0.278,0.278]";
	String test200 = "[0.21,0.17,0.206,0.182,0.21,0.186,0.206,0.186,0.21,0.182,0.206,0.178,0.206,0.182,0.206,0.178,0.21,0.182,0.21,0.182,0.202,0.182,0.202,0.182,0.198,0.178,0.198,0.182,0.218,0.182,0.21,0.182,0.202,0.182,0.202,0.182,0.206,0.182,0.206,0.178,0.206,0.182,0.206,0.178,0.202,0.182,0.202,0.186,0.206,0.186,0.206,0.186,0.214,0.186,0.21,0.186,0.214,0.198,0.23,0.206,0.238,0.218,0.262,0.23,0.274,0.242,0.294,0.254,0.306,0.266,0.33,0.274,0.322,0.278,0.322,0.278,0.322,0.282,0.338,0.282,0.326,0.282,0.334,0.278,0.318,0.27,0.314,0.27,0.306,0.262,0.302,0.254,0.29,0.25,0.282,0.238,0.27,0.238,0.266,0.226,0.258,0.222,0.25,0.218,0.246,0.21,0.234,0.206,0.23,0.198,0.222,0.198,0.218,0.19,0.214,0.19,0.222,0.186,0.214,0.182,0.226,0.19,0.206,0.186,0.226,0.19,0.222,0.19,0.214,0.19,0.218,0.194,0.23,0.198,0.222,0.198,0.226,0.202,0.226,0.202,0.23,0.202,0.23,0.198,0.234,0.198,0.234,0.21,0.238,0.21,0.238,0.21,0.242,0.214,0.246,0.214,0.238,0.214,0.242,0.21,0.238,0.21,0.238,0.206,0.238,0.21,0.254,0.206,0.234,0.202,0.238,0.198,0.222,0.198,0.218,0.198,0.218,0.194,0.242,0.194,0.218,0.19,0.222,0.19,0.214,0.19,0.218,0.186,0.21,0.17,0.206,0.186,0.206,0.178,0.206,0.182,0.206,0.182,0.206,0.182,0.202,0.182,0.202,0.178,0.202,0.158,0.202,0.182,0.206,0.182,0.198,0.178,0.202,0.178,0.198,0.174,0.206,0.174,0.202,0.174,0.206,0.174,0.198,0.174,0.206,0.17,0.19,0.17,0.198,0.162,0.19,0.17,0.194,0.178,0.202,0.186,0.226,0.198,0.23,0.206,0.25,0.226,0.266,0.242,0.286,0.25,0.298,0.262,0.314,0.278,0.278]";
	String test125 = "[0.17,0.182,0.186,0.186,0.182,0.178,0.182,0.178,0.182,0.182,0.182,0.182,0.178,0.182,0.182,0.182,0.182,0.182,0.182,0.178,0.182,0.178,0.182,0.186,0.186,0.186,0.186,0.186,0.198,0.206,0.218,0.23,0.242,0.254,0.266,0.274,0.278,0.278,0.282,0.282,0.282,0.278,0.27,0.27,0.262,0.254,0.25,0.238,0.238,0.226,0.222,0.218,0.21,0.206,0.198,0.198,0.19,0.19,0.186,0.182,0.19,0.186,0.19,0.19,0.19,0.194,0.198,0.198,0.202,0.202,0.202,0.198,0.198,0.21,0.21,0.21,0.214,0.214,0.214,0.21,0.21,0.206,0.21,0.206,0.202,0.198,0.198,0.198,0.194,0.194,0.19,0.19,0.19,0.186,0.17,0.186,0.178,0.182,0.182,0.182,0.182,0.178,0.158,0.182,0.182,0.178,0.178,0.174,0.174,0.174,0.174,0.174,0.17,0.17,0.162,0.17,0.178,0.186,0.198,0.206,0.226,0.242,0.25,0.262,0.278,0.278]";
	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	private static final int REQUEST_ENABLE_BT = 3;

	// Layout Views
	private TextView mTitle;
	private ListView mConversationView;
	private EditText mOutEditText;
	private Button mSendButton;

	// Name of the connected device
	private String mConnectedDeviceName = null;
	// Array adapter for the conversation thread
	private ArrayAdapter<String> mConversationArrayAdapter;
	// String buffer for outgoing messages
	private StringBuffer mOutStringBuffer;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the chat services
	private BluetoothChatService mChatService = null;

	WebView myWebView;
	TextView myResult;

	StringBuilder sb = new StringBuilder();
	float out;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");

		// Set up the window layout
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);

		// Set up the custom title
		mTitle = (TextView) findViewById(R.id.title_left_text);
		mTitle.setText(R.string.app_name);
		mTitle = (TextView) findViewById(R.id.title_right_text);

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available",
					Toast.LENGTH_LONG).show();
			// finish();
			return;
		}
		// ***************
		
		myResult = (TextView) this.findViewById(R.id.myResult);
		myWebView = (WebView) this.findViewById(R.id.webView1);
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.getSettings().setSupportZoom(true);
		myWebView.getSettings().setBuiltInZoomControls(true);
		myWebView.loadUrl("file:///android_asset/index.html");
		myWebView.addJavascriptInterface(new JavaScriptHandler(this),
				"MyHandler");

		// Button btnSet = (Button) this.findViewById(R.id.btnCalc);
		// btnSet.setOnClickListener(new View.OnClickListener() {
		String readMessage1 = "1234\n5678\n9101\n1213\n99";
		String readMessage2 = "99\n8888\n7777\n6666\n5555\n";
		//
		// public void onClick(View view) {
		// String value1 = "";
		// String value2 = "";
		// Pattern MacPat = Pattern.compile("(\\d{1,4})\n");
		// Matcher matcher = MacPat.matcher(readMessage1);
		// Pattern MacPat1 = Pattern.compile("(\\d{1,4})");
		// Matcher matcher1 = MacPat1.matcher(readMessage2);
		// while (matcher.find()) {
		// value = (matcher.group(1));
		// System.out.println(value+":1");
		// System.out.println(matcher.group(2)+":2");
		// }
		// }
		// });

	}

	// *******************
	public void changeText(String someText) {
		myWebView
				.loadUrl("javascript:document.getElementById('test0').innerHTML = '<i>"
						+ someText + "</i>'");
	}

	public void callJavaScriptFunctionAndGetResultBack(String string) {
		myWebView
				.loadUrl("javascript:window.MyHandler.setResult( addSomething("
						+ string + ") )");
	}

	@Override
	public void onStart() {
		super.onStart();
		if (D)
			Log.e(TAG, "++ ON START ++");

		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else {
			if (mChatService == null)
				setupChat();
		}
	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		if (D)
			Log.e(TAG, "+ ON RESUME +");

		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
		if (mChatService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
				// Start the Bluetooth chat services
				mChatService.start();
			}
		}
	}

	private void setupChat() {
		Log.d(TAG, "setupChat()");

		// Initialize the array adapter for the conversation thread
		mConversationArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.message);
		mConversationView = (ListView) findViewById(R.id.in);
		mConversationView.setAdapter(mConversationArrayAdapter);

		// Initialize the compose field with a listener for the return key
		// mOutEditText.setOnEditorActionListener(mWriteListener);

		// Initialize the send button with a listener that for click events

		// Initialize the BluetoothChatService to perform bluetooth connections
		mChatService = new BluetoothChatService(this, mHandler);

		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
		if (D)
			Log.e(TAG, "- ON PAUSE -");
	}

	@Override
	public void onStop() {
		super.onStop();
		if (D)
			Log.e(TAG, "-- ON STOP --");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Stop the Bluetooth chat services
		if (mChatService != null)
			mChatService.stop();
		if (D)
			Log.e(TAG, "--- ON DESTROY ---");
	}

	private void ensureDiscoverable() {
		if (D)
			Log.d(TAG, "ensure discoverable");
		if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	/**
	 * Sends a message.
	 * 
	 * @param message
	 *            A string of text to send.
	 */
	private void sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
					.show();
			return;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothChatService to write
			byte[] send = message.getBytes();
			mChatService.write(send);

			// Reset out string buffer to zero and clear the edit text field
			mOutStringBuffer.setLength(0);
			mOutEditText.setText(mOutStringBuffer);
		}
	}

	// The action listener for the EditText widget, to listen for the return key
	private TextView.OnEditorActionListener mWriteListener = new TextView.OnEditorActionListener() {
		public boolean onEditorAction(TextView view, int actionId,
				KeyEvent event) {
			// If the action is a key-up event on the return key, send the
			// message
			if (actionId == EditorInfo.IME_NULL
					&& event.getAction() == KeyEvent.ACTION_UP) {
				String message = view.getText().toString();
				sendMessage(message);
			}
			if (D)
				Log.i(TAG, "END onEditorAction");
			return true;
		}
	};

	// The Handler that gets information back from the BluetoothChatService
	private final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				if (D)
					Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BluetoothChatService.STATE_CONNECTED:
					mTitle.setText(R.string.title_connected_to);
					mTitle.append(mConnectedDeviceName);
					mConversationArrayAdapter.clear();
					break;
				case BluetoothChatService.STATE_CONNECTING:
					mTitle.setText(R.string.title_connecting);
					break;
				case BluetoothChatService.STATE_LISTEN:
				case BluetoothChatService.STATE_NONE:
					mTitle.setText(R.string.title_not_connected);
					break;
				}
				break;
			case MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				String writeMessage = new String(writeBuf);
				mConversationArrayAdapter.add("Me:  " + writeMessage);
				break;

			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				// msg.arg1 : length to read
				readMessage = new String(readBuf, 0, msg.arg1);
				// System.out.println(readMessage);
				String value = "";
				Pattern MacPat = Pattern.compile("(\\d{1,4})");
				Matcher matcher = MacPat.matcher(readMessage);
				while (matcher.find()) {
					value = (matcher.group(1));

					if (i < 1000) {
						point[i] = Integer.parseInt(value);
						i++;
						if (i == 1000) {
							findpeak(point);
//							show(point);
//							System.out.println(point[999]);
						}
					} else {
						break;
					}

					n++;
					toJS += value;
					if (n % 100 == 0) {
						toJS += "," + value + "]";
						callJavaScriptFunctionAndGetResultBack(toJS);
						// System.out.println(toJS);
						toJS = "[";
						n++;
					} else {
						toJS += ",";
					}
				}
				// callJavaScriptFunctionAndGetResultBack(readMessage);
				// ////////////////////////////////////////
				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			}

		}
	};

	public static void show(int[] point) {
		for (int a = 0; a < point.length; a++) {
			System.out.println(point[a] + ", ");
		}
		System.out.println("");
	}

	public static void findpeak(int[] point) {
		int count = 0;
		int[] peak = new int[1000];
		//index of point is from 0 to 999
		for (int a = 0; a < point.length-2; a++) {
			
			if(point[a+2] - point[a+1] < 0 & point[a+1] - point[a] > 0){
				peak[count] = a;
				show(peak);
				count++;
			}
		}
	}

	public static void peakdiff(int[] peak) {
		for (int a = 0; a < peak.length; a++) {
			System.out.println(peak[a] + ", ");
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (D)
			Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE_SECURE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				connectDevice(data, true);
			}
			break;
		case REQUEST_CONNECT_DEVICE_INSECURE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				connectDevice(data, false);
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				setupChat();
			} else {
				// User did not enable Bluetooth or an error occured
				Log.d(TAG, "BT not enabled");
				Toast.makeText(this, R.string.bt_not_enabled_leaving,
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	private void connectDevice(Intent data, boolean secure) {
		// Get the device MAC address
		String address = data.getExtras().getString(
				DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		// Get the BLuetoothDevice object
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		// Attempt to connect to the device
		mChatService.connect(device, secure);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent serverIntent = null;
		switch (item.getItemId()) {
		case R.id.secure_connect_scan:
			// Launch the DeviceListActivity to see devices and do scan
			serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
			return true;
		case R.id.insecure_connect_scan:
			// Launch the DeviceListActivity to see devices and do scan
			serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent,
					REQUEST_CONNECT_DEVICE_INSECURE);
			return true;
		case R.id.discoverable:
			// Ensure this device is discoverable by others
			ensureDiscoverable();
			return true;
		}
		return false;
	}

}
