package us.bitto.kazi.dxballgame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnTouchListener;


public class GameView extends View implements Runnable, SensorEventListener, OnTouchListener  {
	Thread t;
	Context context;
	Activity activity;
	static int VIEW_WIDTH;
	static int VIEW_HEIGHT;
	boolean init_h_w;
	static boolean stick;
	static boolean shoot;
	
	private Canvas canvas;
    private Activity a;
    private SurfaceHolder surfaceHolder;
    SensorManager mSensorManager;
    Sensor mAccelerometer;
    Paddle paddle;
    Ball ball;
    ArrayList<Brick> brick;
    ArrayList<PowerUp> power_up;
    ArrayList<Ball> balls;
    ArrayList<bullet> bullets;
    Bitmap img_pu_fireball[];
    public static boolean game_over;
    public boolean run=true;
    public CollisionDetector cd;
    public BallBrickCollisionDetector bbcd;
    int total_level;
    int current_level;
    InputStream is;
    ArrayList<life> Life;
    public static int life_no;
    public static long new_life_time=3;
    public static int score;
	
	public GameView(Context context, Activity activity) {
		super(context);
		this.context=context;
		this.activity=activity;
		init_h_w=true;
		this.game_over=false;
		stick=true;
		shoot=false;
		this.total_level=4;
		this.current_level=1;
		new_life_time=3;
		this.life_no=2;
		this.score=0;
		this.setOnTouchListener(this);
		
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		//mSensorManager.registerListener((SensorEventListener) this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
		
		//surfaceHolder = getHolder();
		
		
		img_pu_fireball=new Bitmap[7];
		//this.start();
	}
	
	private void init_h_w(int w, int h) {
		VIEW_WIDTH = w;
        VIEW_HEIGHT = h;
        init_h_w=false;
        
        paddle=new Paddle();
        paddle.setPaddle_height(30);
		paddle.setPaddle_width(200);
		paddle.setPaddle_x(300);
		paddle.setPaddle_y(VIEW_HEIGHT-paddle.getPaddle_height());
		paddle.setImage(false);
		Bitmap bmpImage = null;
		/*try {
			is = context.getAssets().open("images/paddle.png");
			bmpImage = BitmapFactory.decodeStream(is);
			bmpImage = Bitmap.createScaledBitmap(bmpImage, paddle.getPaddle_width(), paddle.getPaddle_height(), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		paddle.setPaddle_image(bmpImage);*/
		
		ball=new Ball();
		ball.setBall_type("normal");
		ball.setImage(true);
		ball.setBall_center_x(paddle.getPaddle_x()+100);
		ball.setBall_center_y(paddle.getPaddle_y()-40);
		ball.setSpeed(4);
		ball.setSpeed_x(3);
		ball.setSpeed_y(-3);
		ball.setBall_height(60);
		ball.setBall_width(60);
		ball.setEnabled(true);
		ball.setStick(true);
		try {
			is = context.getAssets().open("images/fireball2.png");
			bmpImage = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ball.setFireBall(bmpImage);
		
		ball.setBall_height(40);
		ball.setBall_width(40);
		try {
			is = context.getAssets().open("images/ball.png");
			bmpImage = BitmapFactory.decodeStream(is);
			bmpImage = Bitmap.createScaledBitmap(bmpImage, ball.getBall_width(), ball.getBall_height(), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ball.setBall_image(bmpImage);
		ball.setNormal_ball(bmpImage);
		balls=new ArrayList<Ball>();
		balls.add(ball);
		
		
		
		//life
		float l_w=this.VIEW_WIDTH-40;
		float l_h=40;
		Life=new ArrayList<life>();
		for(int i=0; i<life_no; i++) {
			life l=new life();
			l.setWidth(40);
			l.setHeight(40);
			l.setX(l_w);
			l.setY(0);
			try {
				is = context.getAssets().open("images/love.png");
				bmpImage = BitmapFactory.decodeStream(is);
				bmpImage = Bitmap.createScaledBitmap(bmpImage, (int) l.getWidth(), (int) l.getHeight(), false);
			} catch (IOException e) {
				e.printStackTrace();
			}
			l.setImage(bmpImage);
			this.Life.add(l);
			l_w=l.getX()-40-10;
		}
		
		//life
		
		
		
		
		brick=new ArrayList<Brick>();
		float h1=40;
		float w1=40;
		
		
		
		create_lvl(Integer.toString(this.current_level));
		
		
		try {
			is = context.getAssets().open("images/power_fireball.png");
			bmpImage = BitmapFactory.decodeStream(is);
			img_pu_fireball[0] = Bitmap.createScaledBitmap(bmpImage, 70, 70, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is = context.getAssets().open("images/split_ball.png");
			bmpImage = BitmapFactory.decodeStream(is);
			img_pu_fireball[1] = Bitmap.createScaledBitmap(bmpImage, 70, 70, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is = context.getAssets().open("images/expand.png");
			bmpImage = BitmapFactory.decodeStream(is);
			img_pu_fireball[2] = Bitmap.createScaledBitmap(bmpImage, 70, 70, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is = context.getAssets().open("images/shrink.png");
			bmpImage = BitmapFactory.decodeStream(is);
			img_pu_fireball[3] = Bitmap.createScaledBitmap(bmpImage, 70, 70, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is = context.getAssets().open("images/shoot.png");
			bmpImage = BitmapFactory.decodeStream(is);
			img_pu_fireball[4] = Bitmap.createScaledBitmap(bmpImage, 70, 70, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is = context.getAssets().open("images/magnet.png");
			bmpImage = BitmapFactory.decodeStream(is);
			img_pu_fireball[5] = Bitmap.createScaledBitmap(bmpImage, 70, 70, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is = context.getAssets().open("images/dead.png");
			bmpImage = BitmapFactory.decodeStream(is);
			img_pu_fireball[6] = Bitmap.createScaledBitmap(bmpImage, 70, 70, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		power_up=new ArrayList<PowerUp>();
		this.bullets=new ArrayList<bullet>();
		cd=new CollisionDetector(balls, paddle);
		cd.start();
		mSensorManager.registerListener((SensorEventListener) this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
		bbcd=new BallBrickCollisionDetector(balls, brick, power_up, paddle, context, img_pu_fireball, this.bullets);
		bbcd.start();
	}
	
	public void onDraw(Canvas canvas) {
		//if (surfaceHolder.getSurface().isValid()) {
            //canvas = surfaceHolder.lockCanvas();
		
            if(init_h_w) init_h_w(canvas.getWidth(), canvas.getHeight());
            if(!game_over && this.brick.size()==0) this.level_up();
            
            canvas.drawColor(Color.BLACK);
            paddle.draw(canvas);
            
            for(int i=0; i<balls.size(); i++) {
            	balls.get(i).draw(canvas);
            }
            
            for(int i=0; i<brick.size(); i++) {
            	brick.get(i).draw(canvas);
            }
            
            
            
            for(int i=0; i<bullets.size(); i++) {
            	if(!bullets.get(i).isEnable()) {
            		bullets.remove(i);
            		i--;
            		continue;
            	}
            	bullets.get(i).draw(canvas);
            }
            for(int i=0; i<power_up.size(); i++) {
            	if(power_up.get(i).isEnabled()==false) {
            		power_up.remove(i);
            		i--;
            		continue;
            	}
            	power_up.get(i).draw(canvas);
            }
            for(int i=0; i<life_no; i++) {
            	Life.get(i).draw(canvas);
            }
            draw_text(canvas);
            
            if(this.new_life_time==0) update(canvas);
            else {
            	if (System.currentTimeMillis()>new_life_time) {
            		this.new_life();
            		this.new_life_time=0;
            	}
            }
           // surfaceHolder.unlockCanvasAndPost(canvas);
        //}
            invalidate();
	}
	
	public void update(Canvas canvas) {
		for(int i=0; i<balls.size() && !this.game_over; i++) {
        	if(!balls.get(i).isStick()) balls.get(i).update(this.VIEW_WIDTH, this.VIEW_HEIGHT);
        	if(!balls.get(i).isEnabled()) {
        		if(balls.size()==1) {
        			if(this.life_no>1) {
        				if(this.new_life_time==0) {
	        				this.life_no--;
	        				this.new_life_time=System.currentTimeMillis()+3000;
	        				break;
        				}
        			}
        			else {
        				this.life_no--;
        				game_over=true;
        			}
        		}
        		else {
	        		balls.remove(i);
	        		i--;
        		}
        	}
        }
		for(int i=0; i<power_up.size() && !this.game_over; i++) {
        	power_up.get(i).update(canvas);
        }
		for(int i=0; i<bullets.size(); i++) {
        	if(!bullets.get(i).isEnable()) {
        		bullets.remove(i);
        		i--;
        		continue;
        	}
        	bullets.get(i).update();
        }
	}

	@Override
	public void run() {
		while(run) {
			//draw();
		}
	}
	public void start() {
		t=new Thread(this,"gameactivity");
		t.start();
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		int type = event.sensor.getType();
        if (type == Sensor.TYPE_ACCELEROMETER && !GameView.game_over && this.new_life_time==0) {
        	float gy = event.values[1];
        	if(gy>1 && (paddle.getPaddle_x()+paddle.getPaddle_width())<VIEW_WIDTH) {
        		paddle.setPaddle_x((paddle.getPaddle_x()+(gy*gy)));
        		if(this.stick) {
        			for(int i=0; i<this.balls.size(); i++) {
        				if(balls.get(i).isStick()) balls.get(i).setBall_center_x(this.balls.get(i).getBall_center_x()+(gy*gy));
        			}
        		}
        	}
        	else if(gy<-1 && paddle.getPaddle_x()>0 && this.new_life_time==0) {
        		paddle.setPaddle_x((paddle.getPaddle_x()-(gy*gy)));
        		if(this.stick) {
        			for(int i=0; i<this.balls.size(); i++) {
        				if(balls.get(i).isStick()) balls.get(i).setBall_center_x(this.balls.get(i).getBall_center_x()-(gy*gy));
        			}
        		}
        	}
        }
	}
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.d("tup","tup");
		switch(event.getAction()) {
        case (MotionEvent.ACTION_DOWN) :
        	for(int i=0; i<balls.size(); i++) {
        		balls.get(i).setStick(false);
        	}
        	if(this.shoot) {
        		bullet bul=new bullet();
        		bul.setX(this.paddle.getPaddle_x());
        		bul.setY(this.paddle.getPaddle_y());
        		bul.setEnable(true);
        		bul.setDx(-3);
        		this.bullets.add(bul);
        		bullet bul1=new bullet();
        		bul1.setX(this.paddle.getPaddle_x()+this.paddle.getPaddle_width());
        		bul1.setY(this.paddle.getPaddle_y());
        		bul1.setEnable(true);
        		bul1.setDx(-3);
        		this.bullets.add(bul1);
        		
        	}
        	Log.d("tup","tup");
    		stick=false;
            break;
        case (MotionEvent.ACTION_MOVE) :
        	break;
        case (MotionEvent.ACTION_UP) :
            break;
        case (MotionEvent.ACTION_CANCEL) :
        	break;
        case (MotionEvent.ACTION_OUTSIDE) :
        	break;
        default :
        	break;
		}
		return true;
	}
	public void create_lvl(String id) {
		float h1=60;
		float w1=40;
		Bitmap bmpImage = null;
		try {
			is = context.getAssets().open("lvl/"+id+".txt");
			BufferedReader reader;
			reader = new BufferedReader(new InputStreamReader(is));
			String line = reader.readLine();
		    while(line != null){
				for(int i=0; i<line.length(); i++) {
					Brick b=new Brick();
					b.setBrick_height(40);
					b.setBrick_width(((this.VIEW_WIDTH-80)/8)-2);
					if(line.charAt(i)=='1' || line.charAt(i)=='2') {
						b.setBrick_x(w1);
						b.setBrick_y(h1);
						if(line.charAt(i)=='1') b.setBrick_level(1);
						else if(line.charAt(i)=='2') b.setBrick_level(2);
						b.setEnabled(true);
						b.setImage(false);
						b.setBrick_type(1);
						int p_up=randInt(0,11);
						
						if(p_up==2) {
							b.setPower_up("fireball");
						}
						else if(p_up==3) {
							b.setPower_up("splitball");
						}
						else if(p_up==4) {
							b.setPower_up("expand");
						}
						else if(p_up==5) {
							b.setPower_up("shrink");
						}
						else if(p_up==6) {
							b.setPower_up("shoot");
						}
						else if(p_up==7) {
							b.setPower_up("magnet");
						}
						else if(p_up==8) {
							b.setPower_up("shoot");
						}
						else if(p_up==9) {
							b.setPower_up("magnet");
						}
						else if(p_up==10 || p_up==11) {
							b.setPower_up("dead");
						}
						else {
							b.setPower_up("");
						}
						try {
							is = context.getAssets().open("images/normal.png");
							bmpImage = BitmapFactory.decodeStream(is);
							bmpImage = Bitmap.createScaledBitmap(bmpImage, b.getBrick_width(), b.getBrick_height(), false);
						} catch (IOException e) {
							e.printStackTrace();
						}
						b.setBrick_image(bmpImage);
						brick.add(b);
						w1=w1+2+b.getBrick_width(); 
					}
					else if(line.charAt(i)=='0') {
						w1=w1+2+b.getBrick_width(); 
					}
				}
				w1=40;
				h1=h1+40+2;
				line = reader.readLine();
			}
			is.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	public void level_up() {
		current_level++;
		if(current_level>total_level) {
			game_over=true;
		}
		else {
			this.brick.clear();
			this.balls.clear();
			this.bullets.clear();
			this.power_up.clear();
			
			
			
			paddle.setPaddle_height(30);
			paddle.setPaddle_width(200);
			paddle.setPaddle_x(300);
			paddle.setPaddle_y(VIEW_HEIGHT-paddle.getPaddle_height());
			this.stick=true;
			
			
			Bitmap bmpImage = null;
			ball=new Ball();
			ball.setBall_type("normal");
			ball.setImage(true);
			ball.setBall_center_x(paddle.getPaddle_x()+100);
			ball.setBall_center_y(paddle.getPaddle_y()-40);
			ball.setSpeed(4);
			ball.setSpeed_x(3);
			ball.setSpeed_y(-3);
			ball.setBall_height(60);
			ball.setBall_width(60);
			ball.setEnabled(true);
			ball.setStick(true);
			try {
				is = context.getAssets().open("images/fireball2.png");
				bmpImage = BitmapFactory.decodeStream(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ball.setFireBall(bmpImage);
			
			ball.setBall_height(40);
			ball.setBall_width(40);
			try {
				is = context.getAssets().open("images/ball.png");
				bmpImage = BitmapFactory.decodeStream(is);
				bmpImage = Bitmap.createScaledBitmap(bmpImage, ball.getBall_width(), ball.getBall_height(), false);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ball.setBall_image(bmpImage);
			ball.setNormal_ball(bmpImage);
			balls.add(ball);
			
			create_lvl(Integer.toString(this.current_level));
			
		}
	}
	public void new_life() {
		this.bullets.clear();
		this.power_up.clear();
		this.balls.clear();
		
		
		paddle.setPaddle_height(30);
		paddle.setPaddle_width(200);
		paddle.setPaddle_x(300);
		paddle.setPaddle_y(VIEW_HEIGHT-paddle.getPaddle_height());
		this.stick=true;
		
		
		Bitmap bmpImage = null;
		ball=new Ball();
		ball.setBall_type("normal");
		ball.setImage(true);
		ball.setBall_center_x(paddle.getPaddle_x()+100);
		ball.setBall_center_y(paddle.getPaddle_y()-40);
		ball.setSpeed(4);
		ball.setSpeed_x(3);
		ball.setSpeed_y(-3);
		ball.setBall_height(60);
		ball.setBall_width(60);
		ball.setEnabled(true);
		ball.setStick(true);
		try {
			is = context.getAssets().open("images/fireball2.png");
			bmpImage = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ball.setFireBall(bmpImage);
		
		ball.setBall_height(40);
		ball.setBall_width(40);
		try {
			is = context.getAssets().open("images/ball.png");
			bmpImage = BitmapFactory.decodeStream(is);
			bmpImage = Bitmap.createScaledBitmap(bmpImage, ball.getBall_width(), ball.getBall_height(), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ball.setBall_image(bmpImage);
		ball.setNormal_ball(bmpImage);
		balls.add(ball);
	}
	
	public void draw_text(Canvas canvas) {
		if(!game_over) {
			Paint textPaint = new Paint();
	        textPaint.setColor(Color.GREEN);
	        textPaint.setTextAlign(Align.CENTER);
	        //textPaint.setTypeface(font);
	        textPaint.setTextSize(40);
	        int xPos = (canvas.getWidth() / 2);
	        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)) ;
	        canvas.drawText("Score: "+score, xPos, 45, textPaint);
	        canvas.drawText("Level: "+this.current_level, 100, 45, textPaint);
		}
		else {
			Paint textPaint = new Paint();
			textPaint.setColor(Color.GREEN);
	        textPaint.setTextAlign(Align.CENTER);
	        //textPaint.setTypeface(font);
	        textPaint.setTextSize(40);
	        canvas.drawText("Level: "+(this.current_level-1), 100, 45, textPaint);
	        textPaint.setTextSize(100);
	        int xPos = (canvas.getWidth() / 2);
	        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)) ;
	        canvas.drawText("Game Over", xPos, yPos-150, textPaint);
	        canvas.drawText("Score: "+score, xPos, yPos-150+120, textPaint);
	        /*if(this.highscore) {
	        	canvas.drawText("HighScore!!!", xPos, yPos-150+120+120, textPaint);
	        }*/
		}
	}
	
	
}
