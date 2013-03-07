package com.Slem.ejfinal.activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import com.Slem.ejfinal.R;
import com.Slem.ejfinal.BD.BaseDatos.Posts;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


//Me toco usas el api v2
@SuppressLint("NewApi")
public class MapaActivity extends Activity {
	private GoogleMap Mmap;
	GoogleMapOptions options = new GoogleMapOptions();

	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		Mmap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
				
		//Asigno Mylocation para que muestre el lugar donde esta
		Mmap.setMyLocationEnabled(true);
		
			
		//Al mantener pulsado en cualquier sitio del mapa muestra las coordenadas
		Mmap.setOnMapLongClickListener(new OnMapLongClickListener(){
									
			@Override
			public void onMapLongClick(LatLng point) {
				// TODO Auto-generated method stub
				Projection proj = Mmap.getProjection();
		        Point coord = proj.toScreenLocation(point);
		        
		        
		        Toast.makeText(
		                MapaActivity.this,
		                "Click\n" +
		                "Lat: " + point.latitude + "\n" +
		                "Lng: " + point.longitude + "\n" +
		                "X: " + coord.x + " - Y: " + coord.y,
		                Toast.LENGTH_LONG).show();
			}
		});

		//Al pulsar en cualquier sitio del mapa lanza una activity para crear un nuevo sitio y pone las coordenadas
		Mmap.setOnMapClickListener(new OnMapClickListener() {
		    public void onMapClick(LatLng point) {
		        	        
		        Intent intent = new Intent(MapaActivity.this, NewSite.class);
		        intent.putExtra("Lat", point.latitude);
		        intent.putExtra("Lng", point.longitude);
		        startActivity(intent);
		        
		    }
		});
		
		//Carga los marcadores de los sitios
		LoadMarkers();
		
		//para poder ver el titulo del marcador al presionar y que al pulsar la ventana lanza una actividad con los parametros de nombre 
			//y muestra el sitio
		Mmap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
 			@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		Intent intent= new Intent(MapaActivity.this, OnClick.class);
		intent.putExtra("nombre", marker.getTitle());
		startActivity(intent);				
		
	}});
		
				}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_map, menu);
		return true;
	}

	//carga los marcadores
	public void LoadMarkers()
	{
		Uri uri  = Uri.parse("content://com.Slem.ejfinal.bd/site");
		String[] projection=new String[]{
				com.Slem.ejfinal.BD.BaseDatos.Posts._ID, // 0
				com.Slem.ejfinal.BD.BaseDatos.Posts.NOMBRE, // 1
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
		    int colLat = cur.getColumnIndex(Posts.LATITUD);
		    int colLng=cur.getColumnIndex(Posts.LONGITUD);
		   	 
		    		 
		    do
		    {
		    	Mmap.addMarker(new MarkerOptions()
		        .position(new LatLng(cur.getDouble(colLat), cur.getDouble(colLng)))
		        .title(cur.getString(colNombre))
		            
		    			);
		    	
		    	
		 
		    } while (cur.moveToNext());
		}
	}
	public void onResume(){
		super.onResume();
		LoadMarkers();
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.item1:
			LoadMarkers();
			return true;
				default:
			return super.onOptionsItemSelected(item);
		}
		}
	
	

	}
