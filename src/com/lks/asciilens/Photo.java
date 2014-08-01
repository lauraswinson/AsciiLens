package com.lks.asciilens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Typeface;

public class Photo {

    private String photoString;
    private Bitmap bitmap;

    public Photo(Bitmap img){
    	bitmap = img;
    }

    public String getPhotoString(){
    	return photoString;
    }
    
    //add option to make a negative photo
    public Bitmap drawBitmap(int width, int height, boolean negativePhoto){
    	Paint paint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    	paint.setStyle(Paint.Style.FILL);
    	paint.setColor(Color.BLACK);
    	if(negativePhoto){
    		paint.setColor(Color.WHITE);
    		paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    	}

    	//paint.setTextSize(10);
    	//paint.setAntiAlias(true);
    	paint.setTypeface(Typeface.MONOSPACE);
    	//int textSize = determineMaxTextSize(photoString.split("\n")[0], width);
    	setTextSizeForWidth(paint, width, photoString.split("\n")[0]);
    	//paint.setTextSize(textSize);
    	paint.setTextAlign(Align.LEFT);
    	
    	
    	Bitmap textBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

    	Canvas canvas = new Canvas(textBitmap);
    	
    	if(!negativePhoto){
    		canvas.drawColor(0, Mode.CLEAR);
    	}
    	for(String line: photoString.split("\n")){
    		canvas.drawText(line, 0, height, paint);
    	      height += paint.ascent()+paint.descent();
    	}
    	  	
    	return textBitmap;
    }
    
    private static void setTextSizeForWidth(Paint paint, float desiredWidth,
            String text) {

        // Pick a reasonably large value for the test. Larger values produce
        // more accurate results, but may cause problems with hardware
        // acceleration. But there are workarounds for that, too; refer to
        // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
        final float testTextSize = 48f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        // Set the paint for that size.
        paint.setTextSize(desiredTextSize);
    }
    
    public void processPhoto(){
    	for(int i = bitmap.getHeight() -1; i >= 0; i--){
    		photoString += processRow(i) + "\n";
    	}
    }
    
    private String processRow(int row){
    	return processRowHelper(row, 0, bitmap.getWidth() - 1);
    }
    
    private String processRowHelper(int row, int beg, int end){
    	if(beg == end){
    		return getChar(row, beg);
    	}
    	int mid = (end + beg) / 2;
    	return processRowHelper(row, beg, mid) + processRowHelper(row, mid + 1, end);
    }
    
    private String getChar(int i, int j){
    	int colorInt = bitmap.getPixel(j, i);
        int red = Color.red(colorInt);
        int green = Color.green(colorInt);
        int blue = Color.blue(colorInt);
        int charValue  = (red + green + blue) / 3 / 8;
        String value = ".";
        
        switch (charValue) {
        case 0:
            value = "@";
            break;
        case 1:
            value = "#";
            break;
        case 2:
            value = "&";
            break;
        case 3:
            value = "$";
            break;
        case 4:
            value = "%";
            break;
        case 5:
            value = "?";
            break;
        case 6:
            value = "=";
            break;
        case 7:
            value = "+";
            break;
        case 8:
            value = "!";
            break;
        case 9:
            value = "*";
            break;
        case 10:
            value = "\"";
            break;
        case 11:
            value = "~";
            break;
        case 12:
            value = ">";
            break;
        case 13:
            value = "<";
            break;
        case 14:
            value = "}";
            break;
        case 15:
            value = "{";
            break;
        case 16:
            value = "|";
            break;
        case 17:
            value = "\\";
            break;
        case 18:
            value = "/";
            break;
        case 19:
            value = "]";
            break;
        case 20:
            value = "[";
            break;
        case 21:
            value = ")";
            break;
        case 22:
            value = "(";
            break;
        case 23:
            value = "-";
            break;
        case 24:
            value = ";";
            break;
        case 25:
            value = ":";
            break;
        case 26:
            value = "^";
            break;
        case 27:
            value = "_";
            break;
        case 28:
            value = "`";
            break;
        case 29:
            value = "'";
            break;
        case 30:
            value = ",";
            break;
        case 31:
            value = ".";
        }
        return value;
    }
}