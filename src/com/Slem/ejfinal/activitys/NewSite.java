package com.Slem.ejfinal.activitys;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.net.Uri;
import android.os.Bundle;


import com.Slem.ejfinal.R;
import com.Slem.ejfinal.BD.BaseDatos.Posts;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class NewSite extends Activity {
	protected static final int IMAGE_PICK = 0;
	Uri uri = Uri.parse("content://com.Slem.ejfinal.bd/site");
	public ContentValues values = new ContentValues();
	 Uri selectedImage=null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_site);
				
		final EditText name= (EditText) findViewById(R.id.editname);
		final EditText description=(EditText) findViewById(R.id.editdescription);
		final EditText lat=(EditText) findViewById(R.id.editlat);
		final EditText longi=(EditText) findViewById(R.id.editlong);
		final ImageView image=(ImageView) findViewById(R.id.image);
		Button guardar=(Button) findViewById(R.id.save);
		Button eliminar=(Button) findViewById(R.id.delete);
		eliminar.setVisibility(View.GONE);
		guardar.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//comprobacion de que ningun campo este vacio
				if(name.getText().toString()=="" || description.getText().toString()=="" || selectedImage==null){
					Toast.makeText(NewSite.this, "No puede quedar ningun espacio en blanco (Click en la imagen para seleccionar)", Toast.LENGTH_LONG).show();
				}
				
				//Si no esta vacio lo agrega a la base de datos
				else{
				String nombre=name.getText().toString();
				values.put(Posts.NOMBRE, nombre);
				values.put(Posts.DESCRIPCION, description.getText().toString());
				values.put(Posts.LATITUD, lat.getText().toString());
				values.put(Posts.LONGITUD, longi.getText().toString());
				getContentResolver().insert(uri, values);
				finish();
			}
			}
		});
		
		
		image.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityForResult(intent, IMAGE_PICK);
			}
			
		});
		
		
	}
	
	protected void onStart() {
		super.onStart();
		
		//obtiene los datos del mapa
		Bundle extras = getIntent().getExtras();
				
		EditText Lat=(EditText) findViewById(R.id.editlat);
		EditText Longitud=(EditText) findViewById(R.id.editlong);
		
		Double latitud=extras.getDouble("Lat");
		Double lng=extras.getDouble("Lng");
		
		Lat.setText(latitud.toString());
		Longitud.setText(lng.toString());
		
		
		}
		
		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_site, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

	    switch(requestCode) { 
	    case IMAGE_PICK:
	        if(resultCode == RESULT_OK){  
	           selectedImage = imageReturnedIntent.getData();
	            values.put(Posts.FOTO, selectedImage.toString());
	            InputStream imageStream = null;
				try {
					imageStream = getContentResolver().openInputStream(selectedImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
	            
	            ImageView imagen=(ImageView) findViewById(R.id.image);
	            imagen.setImageBitmap(yourSelectedImage);
	            	                 
	        }
	    }
	}
	
	
	

}
