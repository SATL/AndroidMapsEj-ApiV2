package com.Slem.ejfinal.BD;

import android.provider.BaseColumns;

public class BaseDatos {
	
	public static final String DB_NAME = "lugares.db";
	public static final int DB_VERSION = 1;
	
	
	private BaseDatos () {}

	
	public static final class Posts implements BaseColumns {
		
		private Posts() {}
		
	    public static final String DEFAULT_SORT_ORDER = "_ID DESC";
    	
	    public static final String NOMBRE_TABLA = "lugares";
		
		public static final String _ID = "_id";
		public static final String NOMBRE = "nombre";
		public static final String DESCRIPCION = "descripcion";
		public static final String LATITUD="latitud";
		public static final String LONGITUD="longitud";
		public static final String FOTO="uri";
		
		
		
	}
}
