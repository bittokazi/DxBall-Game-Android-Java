package us.bitto.kazi.dxballgame;


import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.os.PowerManager;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class GameMain extends Activity implements Runnable {
	SurfaceHolder surfaceHolder;
	PowerManager.WakeLock mWakeLock;
	GameView gameview;
	Canvas canvas;
	SurfaceView sf;
	Thread thread;
	
	int VIEW_WIDTH;
	int VIEW_HEIGHT;
	boolean init_h_w=true;
	boolean run=true;
	Bitmap bg;
	ImageButton b1;
	ImageButton b2;
	boolean game_view=false;
	Context context;
	GameMain ma;
	boolean highscore=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.game_main);
        context=this.getApplicationContext();
        ma=this;
        //setContentView(R.layout.game_main);
        //gameview=new GameView(this.getApplicationContext(), this);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //this.setContentView(gameview);
        init();
        //this.start();
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_main, menu);
        return true;
    }
    
    @Override
	public void onBackPressed() {
    	if(game_view) {

				gameview.bbcd.run=false;
				gameview.cd.run=false;

			this.setContentView(R.layout.game_main);
			game_view=false;
			init();
		}
		else if(highscore) {
			highscore=false;
			b1.setVisibility(View.VISIBLE);
			b2.setVisibility(View.VISIBLE);
		}
		else {
			System.exit(0);
		}
	}
    
    public void init() {
		sf=(SurfaceView) this.findViewById(R.id.surfaceView1);
		surfaceHolder = sf.getHolder();
        b1=(ImageButton) this.findViewById(R.id.imageButton1);
        b2=(ImageButton) this.findViewById(R.id.imageButton2);
        b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				game_view=true;
				gameview=new GameView(context, ma);
		        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		        setContentView(gameview);
			}
		});
        b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				highscore=true;
				b1.setVisibility(View.GONE);
				b2.setVisibility(View.GONE);
			}
		});
	}
    private void init_h_w(int w, int h) {
		VIEW_WIDTH = w;
        VIEW_HEIGHT = h;
        init_h_w=false;  
        
        
        InputStream is;
		Bitmap bmpImage = null;
		try {
			is = this.getAssets().open("images/bg1.png");
			bmpImage = BitmapFactory.decodeStream(is);
			bmpImage = Bitmap.createScaledBitmap(bmpImage, this.VIEW_WIDTH, this.VIEW_HEIGHT, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bg=bmpImage;
    }
    public void draw() {
    	if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            if(init_h_w) init_h_w(canvas.getWidth(), canvas.getHeight());
            
            canvas.drawBitmap(bg, 0, 0, null);
            //if(this.highscore) this.draw_highscore();
            surfaceHolder.unlockCanvasAndPost(canvas);
		}
    }
    @Override
	public void run() {
		while(run) {
			if(!game_view) draw();
		}
	}
	public void start() {
		thread=new Thread(this, "frontpage");
		thread.start();
	}
}
