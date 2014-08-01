package com.lks.asciilens;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

	Button button;
	boolean negative = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		button = (Button)findViewById(R.id.button);
		button.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 0);
	}
	
	public void onRadioButtonClicked(View view) {
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    switch(view.getId()) {
	        case R.id.regular_radio:
	            if (checked)
	                negative = false;
	            break;
	        case R.id.negative_radio:
	            if (checked)
	                negative = true;
	            break;
	    }
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == RESULT_OK) {
	    	Bitmap bitmap = null;
	    	try{
	    		bitmap = (Bitmap)data.getExtras().get("data");
	    	}
	    	catch(Exception e){
	    		Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
	    	}
			Intent intent = new Intent(this, ResultActivity.class);
			intent.putExtra("BitmapImage", bitmap);
			intent.putExtra("negativePhoto", negative);
			startActivity(intent);
			
	    } else if (resultCode == RESULT_CANCELED) {
	        Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
	    } else {
	        Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
	    }
	}
}
