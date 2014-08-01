package com.lks.asciilens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultActivity extends Activity {
    boolean negativePhoto = false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.activity_result);
			
		Intent intent = getIntent();
		Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        negativePhoto = intent.getExtras().getBoolean("negativePhoto");
    	View view = findViewById(R.id.activity_result);
        if(negativePhoto){
        	//change background color to black
        	view.setBackgroundColor(Color.BLACK);
        }
        else{
        	//change background color to white
        	view.setBackgroundColor(Color.WHITE);
        }
		//background thread
		ProcessPhoto task = new ProcessPhoto();
		task.execute(bitmap);
		
		//this is much faster than ProcessPhoto. Could be a race condition in the future


	}
	
	private class ProcessPhoto extends AsyncTask<Bitmap, Void, Bitmap>{
		
		@Override
		protected Bitmap doInBackground(Bitmap... bitmaps){
	        Photo photo = new Photo(bitmaps[0]);
	        photo.processPhoto();
	        //String photoString = photo.getPhotoString();
	        //return photoString;
	        
	        Display display = getWindowManager().getDefaultDisplay();
	        Point size = new Point();
	        display.getSize(size);
	        int screenWidth = size.x;
	        int screenHeight = size.y;
	        
	        Bitmap textBitmap = photo.drawBitmap(screenWidth, screenHeight, negativePhoto);
	        return textBitmap;
	    
	    }
	    
	    protected void onPostExecute(Bitmap bitmap){
	    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.photo_progress_bar);
	    	progressBar.setVisibility(View.INVISIBLE);
	    	TextView loadingMessage = (TextView)findViewById(R.id.loading_message);
	    	loadingMessage.setVisibility(View.INVISIBLE);
	    	ImageView displayPhoto = (ImageView)findViewById(R.id.photoImage);
	    	displayPhoto.setImageBitmap(bitmap);
	    }
	}	
}





