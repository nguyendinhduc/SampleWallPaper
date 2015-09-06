package com.ducnd.exercise23_wallpaper;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;



public class ManagerImageRun {

	private Bitmap mBitmap;
	private int x, y;
	public static final int ORIENTATION_UP = 0;
	public static final int ORIENTATION_DOWN = 1;
	public static final int ORIENTATION_LEFT = 2;
	public static final int ORIENTATION_RIGHT = 3;
	private static final String TAG = "ManagerImageRun";
	private Random rd = new Random();
	private int orientation = ORIENTATION_DOWN;
	private int withBitmap, heighBitmap;

	public ManagerImageRun(Context context, int widthScreen, int heighScreen) {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), R.drawable.rs, o);
		int width = o.outWidth;
		if ( width > widthScreen ) {
			int widthRatio = Math.round((float)width/(float)widthScreen);
			o.inSampleSize = widthRatio;
		}
		o.inJustDecodeBounds = false;
		
		mBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.rs, o);
		
		withBitmap = mBitmap.getWidth();
		heighBitmap = mBitmap.getHeight();
		Log.i(TAG, "width bitmap: " + mBitmap.getWidth());
		Log.i(TAG, "heigh bitmap: " + mBitmap.getHeight());
		Log.i(TAG, "width screen: " + widthScreen);
		Log.i(TAG, "heigh screen: " + heighScreen);
		
		int deltalWidth = widthScreen - mBitmap.getWidth();
		int dentaHeigh = heighScreen - mBitmap.getHeight();

		x = -rd.nextInt(Math.abs(deltalWidth));
		y = -rd.nextInt(Math.abs(dentaHeigh));
	}

	public void move(Canvas mCanvas, Paint p) {
		switch (orientation) {
		case ORIENTATION_DOWN:
			y-=10;;
			break;
		case ORIENTATION_UP:
			y+=10;
			break;
		case ORIENTATION_LEFT:
			x+=10;
			break;
		case ORIENTATION_RIGHT:
			x-=10;
			break;
		default:
			break;
		}
		mCanvas.drawBitmap(mBitmap, x, y, p);
	}

	public void updateMove(int widthScreen, int heighScreen) {
		if ((orientation == ORIENTATION_DOWN && heighBitmap + y <= heighScreen)
				|| (orientation == ORIENTATION_UP && y >= 0)
				|| (orientation == ORIENTATION_LEFT && x >= 0)
				|| (orientation == ORIENTATION_RIGHT && withBitmap + x <= widthScreen))
			rdOrientation();
	}

	private void rdOrientation() {
		ArrayList<Integer> arrOrientation = new ArrayList<Integer>();
		arrOrientation.add(ORIENTATION_DOWN);
		arrOrientation.add(ORIENTATION_LEFT);
		arrOrientation.add(ORIENTATION_RIGHT);
		arrOrientation.add(ORIENTATION_UP);
		arrOrientation.remove(orientation);

		orientation = arrOrientation.get(rd.nextInt(3));

	}
	public void changeOrientation(int x1, int y1, int x2, int y2) {
		int deltaX = x1-x2;
		int deltaY = y1-y2;
		if ( Math.abs(deltaX) > Math.abs(deltaY) ) {
			if ( x1 < x2 ) orientation = ORIENTATION_LEFT;
			else orientation = ORIENTATION_RIGHT;
		}
		else {
			if ( y1 < y2 ) orientation = ORIENTATION_UP;
			else orientation = ORIENTATION_UP;
		}
	}
	public int getOrientation() {
		return orientation;
	}
	
}
