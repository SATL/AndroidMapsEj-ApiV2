package com.Slem.ejfinal.activitys;


import java.io.FileNotFoundException;
import java.io.InputStream;

import android.net.Uri;
import android.os.Bundle;


import com.Slem.ejfinal.R;
import com.Slem.ejfinal.BD.BaseDatos.Posts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;

import android.content.ContentValues;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditSite extends Activity {
	protected static final int IMAGE_PICK = 0;
	Uri uri = Uri.parse("content://com.Slem.ejfinal.bd/site");
	public ContentValues values = new ContentValues();
	String ID;
	Uri selectedImage;
	
	
	
	
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
		
		//al presionar guardar se realizan los cambios y actualiza la base de datos
		guardar.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String nombre=name.getText().toString();
				values.put(Posts.NOMBRE, nombre);
				values.put(Posts.DESCRIPCION, description.getText().toString());
				values.put(Posts.LATITUD, lat.getText().toString());
				values.put(Posts.LONGITUD, longi.getText().toString());
				getContentResolver().update(uri, values,
						Posts._ID + " = ? ", new String[]{
						String.valueOf(ID)}); 
				Toast.makeText(EditSite.this, nombre +" guardado con exito", Toast.LENGTH_LONG).show();

				finish();
			}
			
		});
		
		Button eliminar=(Button) findViewById(R.id.delete);
		eliminar.setOnClickListener(new OnClickListener(){
			//Elimina el item de la base de datos
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			getContentResolver().delete(uri, Posts._ID + " = ? ", new String[]{
						String.valueOf(ID)});
			Toast.makeText(EditSite.this, name.getText().toString() +" eliminado con exito", Toast.LENGTH_LONG).show();

			finish();
			}
			
		});		
		
		//al presionar sobre la imagen se lanza un intent para seleccionar una
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
	@SuppressLint("NewApi")
	@Override
	protected void onStart() {
		super.onStart();
		Bundle extras = getIntent().getExtras();
		
		//obtiene el i
		int position=extras.getInt("position");
					
		
		EditText name= (EditText) findViewById(R.id.editname);
		EditText description=(EditText) findViewById(R.id.editdescription);
		ImageView image=(ImageView) findViewById(R.id.image);
		
		EditText lat= (EditText) findViewById(R.id.editlat);
		EditText Long=(EditText) findViewById(R.id.editlong);
			
		
			Uri uri  = Uri.parse("content://com.Slem.ejfinal.bd/site");
			String[] projection=new String[]{
					com.Slem.ejfinal.BD.BaseDatos.Posts._ID, // 0
					com.Slem.ejfinal.BD.BaseDatos.Posts.NOMBRE,
					Posts.DESCRIPCION,
					Posts.FOTO,// 1
					Posts.LATITUD,
					Posts.LONGITUD
			};
			
			
			ContentResolver cr =getContentResolver();
			
			Cursor cur = cr.query(uri,
			        projection, //Columnas a devolver
			        null,       //Condición de la query
			        null,       //Argumentos variables de la query
			        null); 
			cur.moveToPosition(position);
			
			//Busca los datos en la base de datos
			int colNombre = cur.getColumnIndex(Posts.NOMBRE);
		    int colDesc=cur.getColumnIndex(Posts.DESCRIPCION);
		    int colFoto=cur.getColumnIndex(Posts.FOTO);
		    int colLat=cur.getColumnIndex(Posts.LATITUD);
		    int colLong=cur.getColumnIndex(Posts.LONGITUD);
		    int idcol=cur.getColumnIndex(Posts._ID);
			
			String nombre=cur.getString(colNombre);
			String descripcion=cur.getString(colDesc);
			String imagen=cur.getString(colFoto);
			String Latitud=cur.getString(colLat);
			String Lng=cur.getString(colLong);
			ID=cur.getString(idcol);
			cur.close();
			
			selectedImage=Uri.parse(imagen);
			//asigna los datos 
			name.setText(nombre.toString());
			description.setText(descripcion.toString());
			image.setImageURI(selectedImage);
			lat.setText(Latitud);
			Long.setText(Lng);
			
		}
		
		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_site, menu);
		return true;
	}
	
	
	//guarda el uri de la imagen seleccionada
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
