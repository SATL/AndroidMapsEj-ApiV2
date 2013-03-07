package com.Slem.ejfinal.activitys;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


import com.Slem.ejfinal.R;
import com.Slem.ejfinal.BD.BaseDatos.Posts;



@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ListaActivity extends ListActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		//asigo las columnas, el cursor, la uri y el adapter para la lista
		final String[] columnas = new String[] { com.Slem.ejfinal.BD.BaseDatos.Posts._ID, // 0
				com.Slem.ejfinal.BD.BaseDatos.Posts.NOMBRE, // 1
				com.Slem.ejfinal.BD.BaseDatos.Posts.DESCRIPCION, // 2
				Posts.FOTO// 3
		};
		Uri uri = Uri.parse("content://com.Slem.ejfinal.bd/site");
		Cursor cursor = managedQuery(uri, columnas, null, null, null
				+ " DESC");
		cursor.setNotificationUri(getContentResolver(), uri);
		startManagingCursor(cursor);
		String[] camposDb = new String[] { com.Slem.ejfinal.BD.BaseDatos.Posts.NOMBRE, 
				com.Slem.ejfinal.BD.BaseDatos.Posts.DESCRIPCION,
				Posts.FOTO};
		int[] camposView = new int[] { R.id.nombre, R.id.descripcion, R.id.imagen
				 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.item, cursor, camposDb, camposView);
		setListAdapter(adapter);
		
		ListView lista=(ListView) findViewById(android.R.id.list);
		
		//Al clickear a un item se lanza la avtivity para ver los detalles 
		lista.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int id,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(ListaActivity.this, OnClick.class);
				
				//pongo como parametro el nombre ya que me daba error con el id y asi uso el mismo extra en el mapa
				String title = ((TextView)arg1.findViewById(R.id.nombre)).getText().toString();
				intent.putExtra("nombre", title.toString());
				
				startActivity(intent);	
			}
			
		});
		
			
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_list, menu);
		return true;
	}
	
	

	
	
	
		
	
		
	
}
