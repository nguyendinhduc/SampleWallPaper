package com.ducnd.exercise23_wallpaper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class MyWallpaper extends WallpaperService {
	private boolean visible;
	private int widthScreen;
	private int heighScreen;
	private Paint p = new Paint();

	private ManagerImageRun mRunBitmap;

	@Override
	public Engine onCreateEngine() {
		WindowManager window = (WindowManager) getSystemService(WINDOW_SERVICE);
		DisplayMetrics display = new DisplayMetrics();
		window.getDefaultDisplay().getMetrics(display);
		widthScreen = display.widthPixels;
		heighScreen = display.heightPixels;
		mRunBitmap = new ManagerImageRun(getBaseContext(), widthScreen,
				heighScreen);

		return new MyEngine();
	}

	private class MyEngine extends WallpaperService.Engine {
		private static final String TAG = "StartEngine";
		private Canvas mCanvas;
		private int x1 = 0, x2;
		private int y1 = 0, y2;
		private Handler handler = new Handler();
		private Runnable run = new Runnable() {

			@Override
			public void run() {
				draw();

			}
		};

		public void onCreate(SurfaceHolder surfaceHolder) {
			Log.i(TAG, " onCreate");
			if (surfaceHolder == null)
				Log.i(TAG, " surfaceHolder is null ");
			else
				Log.i(TAG, " surfaceHolder is not null ");
		};

		@Override
		public void onVisibilityChanged(boolean visible) {
			// TODO Auto-generated method stub
			super.onVisibilityChanged(visible);
			Log.i(TAG, " onVisibilityChanged");
			MyWallpaper.this.visible = visible;
			if (visible) {
				handler.post(run);
			} else {
				handler.removeCallbacks(run);
			}

		}

		@Override
		public void onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			super.onTouchEvent(event);
			Log.i(TAG, "my ontouch");
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				x1 = (int) event.getX();
				y1 = (int) event.getY();
			} else if (event.getAction() == MotionEvent.ACTION_UP) {

				x2 = (int) event.getX();
				y2 = (int) event.getY();
				mRunBitmap.changeOrientation(x1, y1, x2, y2);
			}
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub

			super.onSurfaceDestroyed(holder);

			visible = false;
			handler.removeCallbacks(run);
		}

		public void drawStar(Canvas canvas) {

		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			super.onSurfaceCreated(holder);
			Log.i(TAG, "onSurfaceCreated");
		}

		public void draw() {
			SurfaceHolder holder = getSurfaceHolder();

			if (holder != null) {
				mCanvas = holder.lockCanvas();
			}
			if (mCanvas == null)
				return;

			mRunBitmap.updateMove(widthScreen, heighScreen);
			mRunBitmap.move(mCanvas, p);
			holder.unlockCanvasAndPost(mCanvas);
			if (visible) {
				handler.removeCallbacks(run);
				handler.postDelayed(run, 5);
			} else {
				handler.removeCallbacks(run);
			}
		}
	}
}
