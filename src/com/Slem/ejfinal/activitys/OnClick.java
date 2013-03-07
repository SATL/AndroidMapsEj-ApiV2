package com.Slem.ejfinal.activitys;

import android.net.Uri;
import android.os.Bundle;
import com.Slem.ejfinal.R;
import com.Slem.ejfinal.BD.BaseDatos.Posts;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OnClick extends Activity {
	public int position;
	public double LAT;
	public double LNG;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_on_click);
					}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		//obtengo el nombre del sitio
		Bundle extras = getIntent().getExtras();
		String name=extras.getString("nombre");
		
		//asigno los datos del sitio
		SetValues(name);
		
		//boton para editar
		Button Editar=(Button) findViewById(R.id.edit);
		Editar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(OnClick.this, EditSite.class);
				intent.putExtra("position", position);
				startActivity(intent);
				
			}
			
		});
		
		
		
		
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_on_click, menu);
		return true;
	}
	
	public void SetValues(String title){
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
		
		if (cur.moveToFirst())
		{
			
		    		 
		    int colNombre = cur.getColumnIndex(Posts.NOMBRE);
		    int colDesc=cur.getColumnIndex(Posts.DESCRIPCION);
		    int colFoto=cur.getColumnIndex(Posts.FOTO);
		    int colLat=cur.getColumnIndex(Posts.LATITUD);
		    int colLong=cur.getColumnIndex(Posts.LONGITUD);
		    		 
		    do
		    {
		        String nombre=cur.getString(colNombre);
		        String descripcion=cur.getString(colDesc);
		        final String foto=cur.getString(colFoto);
		       
		        LAT=cur.getDouble(colLat);
		        LNG=cur.getDouble(colLong);
		       
		        String lat="Lat: "+cur.getString(colLat);
		        String lng="  Lng: "+cur.getString(colLong);
		        int pos=cur.getPosition();
		        
		        //busco un sitio con ese nombre y cuando lo encuentra asigna los datos
		        if(title.equals(nombre)){
		        	position= pos;
		        	
		        	TextView name=(TextView) findViewById(R.id.name);
		        	TextView description=(TextView) findViewById(R.id.desc);
		        	TextView Lat=(TextView) findViewById(R.id.latitud);
		        	TextView Lng=(TextView) findViewById(R.id.longitud);
		        	ImageView img=(ImageView) findViewById(R.id.img);
		        	
		        	name.setText(nombre);
		        	description.setText(descripcion);
		        	Lat.setText(lat);
		        	Lng.setText(lng);
		        	Uri uriImage=Uri.parse(foto);
		        	img.setImageURI(uriImage);
		        	
		        	
		        	//Al hacer click en la imagen se lanza una activity para poder verla en grande ( con multitouch y zoom)
		        	img.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent=new Intent(OnClick.this, ImagenClick.class);
							intent.putExtra("uri", foto);
							startActivity(intent);
						}
		        		
		        	});
		        	
		        }
		        
		 
		    } while (cur.moveToNext());
		}
		
	}

}
