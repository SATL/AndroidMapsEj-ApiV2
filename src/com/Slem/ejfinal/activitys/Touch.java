package com.Slem.ejfinal.activitys;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class Touch implements OnTouchListener {

	 // matrices para el zoom y el movimiento
	 Matrix matrix = new Matrix();
	 Matrix savedMatrix = new Matrix();

	 // Estados en los que puede estar
	 static final int NONE = 0;
	 static final int DRAG = 1;
	 static final int ZOOM = 2;
	 int mode = NONE;

	 // para el zoom
	 PointF start = new PointF();
	 PointF mid = new PointF();
	 float oldDist = 1f;

	 
	 @Override
	 public boolean onTouch(View v, MotionEvent event) {
	  ImageView view = (ImageView) v;
	  // para el log
	 
	  dumpEvent(event);

	  // se cogen los eventos touch
	  switch (event.getAction() & MotionEvent.ACTION_MASK) {
	 
	  case MotionEvent.ACTION_DOWN:
	   savedMatrix.set(matrix);
	   start.set(event.getX(), event.getY());
	   mode = DRAG;
	   break;
	
	  case MotionEvent.ACTION_POINTER_DOWN:
	   oldDist = spacing(event);
	   if (oldDist > 10f) {
	    savedMatrix.set(matrix);
	    midPoint(mid, event);
	    mode = ZOOM;
	   }
	   break;
	  
	  case MotionEvent.ACTION_UP:
	  case MotionEvent.ACTION_POINTER_UP:
	   mode = NONE;
	   break;
	 
	  case MotionEvent.ACTION_MOVE:
	   if (mode == DRAG) {
	    // ...    
	    matrix.set(savedMatrix);
	    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);    
	   } else if (mode == ZOOM) {
	    float newDist = spacing(event);
	    if (newDist > 10f) {
	     matrix.set(savedMatrix);
	     float scale = newDist / oldDist;
	     matrix.postScale(scale, scale, mid.x, mid.y);
	    }
	   }
	   break;
	  }

	  view.setImageMatrix(matrix);
	  return true; 
	 }

	 /**  LogCat  */
	 @SuppressWarnings("deprecation")
	private void dumpEvent(MotionEvent event) {
	  String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
	    "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
	  StringBuilder sb = new StringBuilder();
	  int action = event.getAction();
	  int actionCode = action & MotionEvent.ACTION_MASK;
	  sb.append("event ACTION_").append(names[actionCode]);
	  if (actionCode == MotionEvent.ACTION_POINTER_DOWN
	    || actionCode == MotionEvent.ACTION_POINTER_UP) {
	   sb.append("(pid ").append(
	     action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
	   sb.append(")");
	  }
	  sb.append("[");
	  for (int i = 0; i < event.getPointerCount(); i++) {
	   sb.append("#").append(i);
	   sb.append("(pid ").append(event.getPointerId(i));
	   sb.append(")=").append((int) event.getX(i));
	   sb.append(",").append((int) event.getY(i));
	   if (i + 1 < event.getPointerCount())
	    sb.append(";");
	  }
	  sb.append("]");
	 }

	 /**Espacio entre los dedos*/
	 @SuppressLint("FloatMath")
	private float spacing(MotionEvent event) {
	  float x = event.getX(0) - event.getX(1);
	  float y = event.getY(0) - event.getY(1);
	  return FloatMath.sqrt(x * x + y * y);
	 }

	 /** calcula el punto medio de los dos puntos */
	 private void midPoint(PointF point, MotionEvent event) {
	  float x = event.getX(0) + event.getX(1);
	  float y = event.getY(0) + event.getY(1);
	  point.set(x / 2, y / 2);
	 }
	}
