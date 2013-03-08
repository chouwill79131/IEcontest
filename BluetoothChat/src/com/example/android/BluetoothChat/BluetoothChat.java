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
import android.view.View;
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

	int[] point = new int[9000];
	int[] ppi = new int[100];
	String readMessage = "";

	public static int var = 125;
	String toJS = "[";
	String forpeaks = "12321232";
	String test50 = "[0.21,0.182,0.206,0.182,0.198,0.182,0.202,0.178,0.202,0.186,0.21,0.206,0.274,0.266,0.322,0.282,0.318,0.262,0.282,0.226,0.246,0.198,0.214,0.182,0.226,0.19,0.222,0.202,0.234,0.21,0.246,0.21,0.238,0.202,0.218,0.194,0.214,0.17,0.206,0.182,0.202,0.182,0.198,0.174,0.206,0.162,0.202,0.206,0.286,0.278,0.278]";
	String test200 = "[0.21,0.17,0.206,0.182,0.21,0.186,0.206,0.186,0.21,0.182,0.206,0.178,0.206,0.182,0.206,0.178,0.21,0.182,0.21,0.182,0.202,0.182,0.202,0.182,0.198,0.178,0.198,0.182,0.218,0.182,0.21,0.182,0.202,0.182,0.202,0.182,0.206,0.182,0.206,0.178,0.206,0.182,0.206,0.178,0.202,0.182,0.202,0.186,0.206,0.186,0.206,0.186,0.214,0.186,0.21,0.186,0.214,0.198,0.23,0.206,0.238,0.218,0.262,0.23,0.274,0.242,0.294,0.254,0.306,0.266,0.33,0.274,0.322,0.278,0.322,0.278,0.322,0.282,0.338,0.282,0.326,0.282,0.334,0.278,0.318,0.27,0.314,0.27,0.306,0.262,0.302,0.254,0.29,0.25,0.282,0.238,0.27,0.238,0.266,0.226,0.258,0.222,0.25,0.218,0.246,0.21,0.234,0.206,0.23,0.198,0.222,0.198,0.218,0.19,0.214,0.19,0.222,0.186,0.214,0.182,0.226,0.19,0.206,0.186,0.226,0.19,0.222,0.19,0.214,0.19,0.218,0.194,0.23,0.198,0.222,0.198,0.226,0.202,0.226,0.202,0.23,0.202,0.23,0.198,0.234,0.198,0.234,0.21,0.238,0.21,0.238,0.21,0.242,0.214,0.246,0.214,0.238,0.214,0.242,0.21,0.238,0.21,0.238,0.206,0.238,0.21,0.254,0.206,0.234,0.202,0.238,0.198,0.222,0.198,0.218,0.198,0.218,0.194,0.242,0.194,0.218,0.19,0.222,0.19,0.214,0.19,0.218,0.186,0.21,0.17,0.206,0.186,0.206,0.178,0.206,0.182,0.206,0.182,0.206,0.182,0.202,0.182,0.202,0.178,0.202,0.158,0.202,0.182,0.206,0.182,0.198,0.178,0.202,0.178,0.198,0.174,0.206,0.174,0.202,0.174,0.206,0.174,0.198,0.174,0.206,0.17,0.19,0.17,0.198,0.162,0.19,0.17,0.194,0.178,0.202,0.186,0.226,0.198,0.23,0.206,0.25,0.226,0.266,0.242,0.286,0.25,0.298,0.262,0.314,0.278,0.278]";
	String test125 = "[0.17,0.182,0.186,0.186,0.182,0.178,0.182,0.178,0.182,0.182,0.182,0.182,0.178,0.182,0.182,0.182,0.182,0.182,0.182,0.178,0.182,0.178,0.182,0.186,0.186,0.186,0.186,0.186,0.198,0.206,0.218,0.23,0.242,0.254,0.266,0.274,0.278,0.278,0.282,0.282,0.282,0.278,0.27,0.27,0.262,0.254,0.25,0.238,0.238,0.226,0.222,0.218,0.21,0.206,0.198,0.198,0.19,0.19,0.186,0.182,0.19,0.186,0.19,0.19,0.19,0.194,0.198,0.198,0.202,0.202,0.202,0.198,0.198,0.21,0.21,0.21,0.214,0.214,0.214,0.21,0.21,0.206,0.21,0.206,0.202,0.198,0.198,0.198,0.194,0.194,0.19,0.19,0.19,0.186,0.17,0.186,0.178,0.182,0.182,0.182,0.182,0.178,0.158,0.182,0.182,0.178,0.178,0.174,0.174,0.174,0.174,0.174,0.17,0.17,0.162,0.17,0.178,0.186,0.198,0.206,0.226,0.242,0.25,0.262,0.278,0.278]";
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	private static final int REQUEST_ENABLE_BT = 3;
	private TextView mTitle;
	private ListView mConversationView;
	private EditText mOutEditText;
	private Button mSendButton;
	private String mConnectedDeviceName = null;
	private ArrayAdapter<String> mConversationArrayAdapter;
	private StringBuffer mOutStringBuffer;
	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothChatService mChatService = null;
	WebView myWebView;
	TextView myResult;
	StringBuilder sb = new StringBuilder();
	

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
			return;
		}
		// ***************
		myWebView = (WebView) this.findViewById(R.id.webView1);
		myWebView.getSettings().setJavaScriptEnabled(true);
//		myWebView.getSettings().setSupportZoom(true);
//		myWebView.getSettings().setBuiltInZoomControls(true);
		myWebView.loadUrl("file:///android_asset/main.html");
		myWebView.addJavascriptInterface(new JavaScriptHandler(this),
				"MyHandler");
		// Button btnSet = (Button) this.findViewById(R.id.btnCalc);
		// btnSet.setOnClickListener(new View.OnClickListener() {

		// Button btnFFT = (Button) this.findViewById(R.id.btnfft);
		// btnFFT.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// try {
		// super.wait(2000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
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
				Pattern MacPat = Pattern.compile("(\\d{3,4})");
				Matcher matcher = MacPat.matcher(readMessage);
				while (matcher.find()) {
					value = (matcher.group(1));
					if (i < 9000) {
						point[i] = Integer.parseInt(value);
						i++;
						if (i == 9000) {
							findpeak(point);
							Toast.makeText(getApplicationContext(),findpeak(point)+"" , 5000).show();
							// show(point);
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
			System.out.print(point[a] + ", ");
		}
		System.out.println("");
	}
	public static void show(double[] point) {
		for (int a = 0; a < point.length; a++) {
			System.out.print(point[a] + ", ");
		}
		System.out.println("");
	}

	public static double findpeak(int[] point) {
		// find peak bigger than 1500 and separate by at least 50
		int count = 0;
		int[] peak = new int[200];
		int b = 0;
		// show(point);
		// index of point is from 0 to 999
		for (int a = 0; a < point.length - 2; a++) {
			if (point[a + 2] - point[a + 1] < 0 & point[a + 1] - point[a] > 0
					& point[a + 1] > 500) {
				if (a + 1 - peak[count] < 50) {
				} else {
					count++;
					peak[count] = a + 1;
					b = a + 1;
				}
			}
		}
		
		// show(peak);
		peakdiff(peak, count);
		return peakdiff(peak, count);
	}

	public static double peakdiff(int[] peak, int count) {
		int[] ppi = new int[count];
		for (int a = 0; a < peak.length - 1; a++) {
			if (peak[a + 1] <= peak[a]) {
			} else {
				ppi[a] = peak[a + 1] - peak[a];
			}
		}
		
		show(ppi);
		int X = 0;
		for (int a : ppi) {
			X += a;
		}
		int N = X;
		System.out.println(N + "	number of peak");
		Complex[] C = new Complex[8192];
		int j = 0;
		int i = 0;
		for (int value : ppi) {
			for (int k = 0; k < ppi[j]; k++) {
				C[i] = new Complex(value, 0);
				i++;
				if (i == 8192) {
					break;
				}
			}
			if (i == 8192) {
				break;
			}
			j++;
		}

		// System.out.println(C.length + "	C.length");
		// show(C, "C");
		// if (true) {
		// return;
		// }
		Complex[] y = FFT.fft(C);
		double[] abs = new double[N];
		abs = sqr(y);
		double LF = 0;
		double HF = 0;
		for(int lf = 4; lf<14; lf++){
			LF += abs[lf];
		}
		LF = (LF*0.11)/10;
		for(int hf = 14; hf<37; hf++){
			HF += abs[hf];
		}
		HF = (HF*0.25)/23;
		return LF/HF;
//		System.out.println(LF/HF+"	LF/HF");
//		show(abs);
//		show(y, "y");
	}

	public static void show(Complex[] x, String title) {
		System.out.println("-------------------");
		for (int i = 0; i < x.length; i++) {
			System.out.println(x[i]);
		}
		System.out.println("complex");
	}

	public static class FFT {
		// compute the FFT of x[], assuming its length is a power of 2
		public static Complex[] fft(Complex[] C) {
			int N = C.length;
			// base case
			if (N == 1)
				return new Complex[] { C[0] };
			if (N % 2 != 0) {
				throw new RuntimeException("N is not a power of 2");
			}

			// fft of even terms
			Complex[] even = new Complex[N / 2];
			for (int k = 0; k < N / 2; k++) {
				even[k] = C[2 * k];
			}

			Complex[] q = fft(even); // !!!

			// fft of odd terms
			Complex[] odd = even; // reuse the array
			for (int k = 0; k < N / 2; k++) {
				odd[k] = C[2 * k + 1];
			}
			Complex[] r = fft(odd);

			// combine
			Complex[] y = new Complex[N];
			for (int k = 0; k < N / 2; k++) {
				double kth = -2 * k * Math.PI / N;
				Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
				y[k] = q[k].plus(wk.times(r[k]));
				y[k + N / 2] = q[k].minus(wk.times(r[k]));
			}
			
			return y;
		}
	}
	public static double[] sqr(Complex[] b) {
		int i = 0;
		double[] abs = new double[8192];
		for (Complex a : b) {
			abs[i] = Math.sqrt(Math.pow(a.re, 2) + Math.pow(a.im, 2));
			i++;
		}
		return abs;
	}
	public static class Complex {
		private final double re; // the real part
		private final double im; // the imaginary part

		// create a new object with the given real and imaginary parts
		public Complex(double real, double imag) {
			re = real;
			im = imag;
		}

		

		public String toString() {
			if (im == 0)
				return re + "";
			if (re == 0)
				return im + "i";
			if (im < 0)
				return re + " - " + (-im) + "i";
			return re + " + " + im + "i";
		}

		public Complex plus(Complex b) {
			Complex a = this; // invoking object
			double real = a.re + b.re;
			double imag = a.im + b.im;
			return new Complex(real, imag);
		}

		// return a new Complex object whose value is (this - b)
		public Complex minus(Complex b) {
			Complex a = this;
			double real = a.re - b.re;
			double imag = a.im - b.im;
			return new Complex(real, imag);
		}

		public Complex times(Complex b) {
			Complex a = this;
			double real = a.re * b.re - a.im * b.im;
			double imag = a.re * b.im + a.im * b.re;
			return new Complex(real, imag);
		}

		public Complex sin() {
			return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re)
					* Math.sinh(im));
		}

		// return a new Complex object whose value is the complex cosine of this
		public Complex cos() {
			return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re)
					* Math.sinh(im));
		}

	};

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
