package com.example.facebook_photo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.example.android.BluetoothChat.JavaScriptHandler;
import com.example.android.BluetoothChat.R;
import com.example.facebook_photo.Utility;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;



public class MainActivity extends Activity {

	public static final String APP_ID = "530215667020151";
	public String[] permission = {""}; 
	final static int AUTHORIZE_ACTIVITY_RESULT_CODE = 0;
	final static int PICK_EXISTING_PHOTO_RESULT_CODE = 1;
	
	@SuppressWarnings("deprecation")
	public Facebook mFacebook = new Facebook(APP_ID);
	ProgressDialog dialog;
	private Handler mHandler;
	
	ImageView bmImage;
 	Bitmap bmScreen;
 	View screen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		postFB();
//		((Button)findViewById(R.id.button1)).setOnClickListener(post);
		SessionStore.restore(mFacebook, this);
		
	}
	
	

    @SuppressWarnings("deprecation")
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	
    	mFacebook.authorizeCallback(requestCode, resultCode, data);
        switch (requestCode) {
        /*
         * if this is the activity result from authorization flow, do a call
         * back to authorizeCallback Source Tag: login_tag
         */
            case AUTHORIZE_ACTIVITY_RESULT_CODE: {
                mFacebook.authorizeCallback(requestCode, resultCode, data);
                break;
            }
            /*
             * if this is the result for a photo picker from the gallery, upload
             * the image after scaling it. You can use the Utility.scaleImage()
             * function for scaling
             */
            case PICK_EXISTING_PHOTO_RESULT_CODE: {
                if (resultCode == Activity.RESULT_OK) {
                    Uri photoUri = data.getData();
                    if (photoUri != null) {
                        Bundle params = new Bundle();
                        try {
                            params.putByteArray("photo",
                                    Utility.scaleImage(getApplicationContext(), photoUri));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        params.putString("caption", "iFeeling");
                        AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(mFacebook);
                        mAsyncRunner.request("me/photos", params, "POST",
                                new PhotoUploadListener(), null);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Error selecting image from the gallery.", Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No image selected for upload.",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
	
		
		@SuppressWarnings("deprecation")
		public void postFB() {
			
			System.out.println(!(this.mFacebook).isSessionValid());
			// TODO Auto-generated method stub
			if (!(this.mFacebook).isSessionValid()){
			
				Toast.makeText(MainActivity.this, "Authorizing", Toast.LENGTH_SHORT).show();
				mFacebook.authorize(MainActivity.this, permission , new LoginDialogListener());		
				
			}else{
				
				Toast.makeText(MainActivity.this, "Has Valid Session", Toast.LENGTH_SHORT).show();
				screen = (View)findViewById(R.id.screen);
				screen.setDrawingCacheEnabled(true);
				bmScreen = screen.getDrawingCache();
				saveImage(bmScreen);
				
				Intent intent = new Intent(Intent.ACTION_PICK, (MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
                startActivityForResult(intent, PICK_EXISTING_PHOTO_RESULT_CODE);	
			}
		}
	
	 protected void saveImage(Bitmap bmScreen2) {
	        // TODO Auto-generated method stub

	        // String fname = "Upload.png";
//	        File saved_image_file = new File(
//	                Environment.getExternalStorageDirectory()
//	                        + "/captured_Bitmap.png");
		 File saved_image_file = new File("sdcard/Pictures" + "/captured_facebook.jpg");
	        if (saved_image_file.exists())
	            saved_image_file.delete();
	        try {
	            FileOutputStream out = new FileOutputStream(saved_image_file);
	            bmScreen2.compress(Bitmap.CompressFormat.JPEG, 100, out);
	            out.flush();
	            out.close();
	            MediaStore.Images.Media.insertImage(getContentResolver()
	            		,saved_image_file.getAbsolutePath(),
	            		saved_image_file.getName(),
	            		saved_image_file.getName());
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 }
	
	public class  LoginDialogListener implements  DialogListener {

		@Override
		public void onComplete(Bundle values) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
			SessionStore.save(mFacebook, MainActivity.this);
		}

		@Override
		public void onFacebookError(FacebookError e) {
			// TODO Auto-generated method stub
			Toast.makeText( MainActivity.this, "Something went wrong. Please try again. onFacebookError", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onError(DialogError e) {
			// TODO Auto-generated method stub
			Toast.makeText( MainActivity.this, "Something went wrong. Please try again. onError", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			Toast.makeText( MainActivity.this, "Something went wrong. Please try again. onCancel", Toast.LENGTH_LONG).show();
		}
	}
	
    public class PhotoUploadListener extends BaseRequestListener {

        @Override
        public void onComplete(final String response, final Object state) {
            dialog.dismiss();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    new UploadPhotoResultDialog(MainActivity.this, "Upload Photo executed", response)
                            .show();
                }
            });
        }

        public void onFacebookError(FacebookError error) {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "Facebook Error: " + error.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    
    
    
    
	
}
