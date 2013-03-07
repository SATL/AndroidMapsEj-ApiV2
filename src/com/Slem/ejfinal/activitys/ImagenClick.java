package com.Slem.ejfinal.activitys;


import com.Slem.ejfinal.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;

public class ImagenClick extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagen_click);
	}
	
	public void onResume(){
		super.onResume();
		
		//obtiene el uri de la imagen
		Bundle extras = getIntent().getExtras();
		ImageView imgView = (ImageView) findViewById(R.id.ImageView01);
		
		//asigna el touch a la imagen para poder controlarla
		imgView.setOnTouchListener(new Touch());
		String uri = (String) extras.get("uri");
		imgView.setImageURI(Uri.parse(uri));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_imagen_click, menu);
		return true;
	}

}
