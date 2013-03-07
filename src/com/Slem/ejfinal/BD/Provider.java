package com.Slem.ejfinal.BD;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class Provider extends ContentProvider {
	/*
	 * Espacio de nombres (se debe usar el mismo para definir el content provider
	 * en el Manifest
	 */
	
	public static final Uri CONTENT_URI = Uri.parse("content://com.Slem.ejfinal.bd");

	private static final int SITE = 1;
	private static final int SITE_ID = 2;

	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.Slem.ejfinal.bd", "site", SITE);
		uriMatcher.addURI("com.Slem.ejfinal.bd", "site/#", SITE_ID);
	}

	private SQLiteDatabase BaseDatos;
	
	
	@Override
	public boolean onCreate() {
		Context context = getContext();
		SQLHelper dbHelper = new SQLHelper(context);
		BaseDatos = dbHelper.getWritableDatabase();
		return (BaseDatos == null) ? false : true;
	}
	
	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		
		case SITE:
			return "vnd.android.cursor.dir/vnd.Slem.ejfinal.bd.site";
			
		case SITE_ID:
			return "vnd.android.cursor.item/vnd.Slem.ejfinal.bd.site";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public int delete(Uri uri, String where, String[] whereargs) {
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case SITE:
			count = BaseDatos.delete(com.Slem.ejfinal.BD.BaseDatos.Posts.NOMBRE_TABLA, where, whereargs);
			break;
		case SITE_ID:
			String id = uri.getPathSegments().get(1);
			count = BaseDatos.delete(com.Slem.ejfinal.BD.BaseDatos.Posts.NOMBRE_TABLA, com.Slem.ejfinal.BD.BaseDatos.Posts._ID + " = " + id
					+ (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""),
					whereargs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	
	/**
	 * insert
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowID = BaseDatos.replace(com.Slem.ejfinal.BD.BaseDatos.Posts.NOMBRE_TABLA, "", values);
		
		// si todo ha ido ok devolvemos su Uri
		if (rowID > 0) {
			Uri baseUri = Uri.parse("content://com.Slem.ejfinal.bd/site");
			Uri _uri = ContentUris.withAppendedId(baseUri, rowID);
			
			getContext().getContentResolver().notifyChange(_uri, null);
			getContext().getContentResolver().notifyChange(baseUri, null);
			
			return _uri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}



	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
		sqlBuilder.setTables(com.Slem.ejfinal.BD.BaseDatos.Posts.NOMBRE_TABLA);

		if (uriMatcher.match(uri) == SITE_ID) {
			sqlBuilder.appendWhere(com.Slem.ejfinal.BD.BaseDatos.Posts._ID + " = " + uri.getPathSegments().get(1));
		}

		if (sortOrder == null || sortOrder == "") {
			sortOrder = com.Slem.ejfinal.BD.BaseDatos.Posts.DEFAULT_SORT_ORDER;
		}

		Cursor c = sqlBuilder.query(BaseDatos, projection, selection,
				selectionArgs, null, null, sortOrder);

		// Registramos los cambios para que se enteren nuestros observers
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case SITE:
			count = BaseDatos.update(com.Slem.ejfinal.BD.BaseDatos.Posts.NOMBRE_TABLA, values, selection,
					selectionArgs);
			break;
		case SITE_ID:
			count = BaseDatos.update(com.Slem.ejfinal.BD.BaseDatos.Posts.NOMBRE_TABLA, values, com.Slem.ejfinal.BD.BaseDatos.Posts._ID
					+ " = "
					+ uri.getPathSegments().get(1)
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
					+ ')' : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
