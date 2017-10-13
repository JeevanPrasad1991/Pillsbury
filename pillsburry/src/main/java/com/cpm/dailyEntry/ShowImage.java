package com.cpm.dailyEntry;


import com.cpm.pillsbury.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowImage extends Activity {
	public String imagepath = "";
	private ImageView viewimage;
	public 	String ImageName="";
	public TextView ImgName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.editimages_promotion);
		
		viewimage= (ImageView) findViewById(R.id.imageView1) ;
		ImgName =(TextView)findViewById(R.id.showHeader);
		
		Intent intent = getIntent();
		imagepath= intent.getStringExtra("Image");
		ImageName=intent.getStringExtra("Name");
		String str= "/mnt/sdcard/westernDigital_Images/"+imagepath;
		
		
		BitmapFactory.Options options;
	    options = new BitmapFactory.Options();
	    options.inSampleSize = 8; 
	    Bitmap b = BitmapFactory.decodeFile(str, options);
	      //viewimage.setImageBitmap(b);
	      ImgName.setText(ImageName);
	      viewimage
			.setImageBitmap(Bitmap.createScaledBitmap(
					BitmapFactory.decodeFile(str, options), 500,
					500, true));

		
		
	
	}

}
