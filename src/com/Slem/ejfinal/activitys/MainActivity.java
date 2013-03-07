package com.Slem.ejfinal.activitys;

import com.Slem.ejfinal.R;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	//creo las pestañas para que se pueda cambiar entre lista y mapa
		TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();
		
		//Pongo la tab de lista como principal y le asigno su activity 
		TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
		Intent lista = new Intent(this, ListaActivity.class);
		spec.setContent(lista);
		spec.setIndicator("Lista");
		tabs.addTab(spec);
		
		//asigno la pestaña de mapa con su activity
		Intent aleatorio = new Intent(this, MapaActivity.class);
		spec=tabs.newTabSpec("mitab2");
		spec.setContent(aleatorio);
		spec.setIndicator("Mapa");
		tabs.addTab(spec);
	}

	
	
	
	
	
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		
		case R.id.About:
			AlertDialog dialogAbout = null;
			final AlertDialog.Builder builder;

			LayoutInflater li = LayoutInflater.from(this);
			View view = li.inflate(R.layout.acercade, null);

			builder = new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher)
					.setTitle(getString(R.string.app_name))
					.setPositiveButton("Ok", null).setView(view);

			dialogAbout = builder.create();
			dialogAbout.show();
			
			
			return true;
		
		
		default:
			return super.onOptionsItemSelected(item);
		}
		}
	
	
	

}
