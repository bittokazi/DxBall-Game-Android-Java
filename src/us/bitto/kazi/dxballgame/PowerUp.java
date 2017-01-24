package us.bitto.kazi.dxballgame;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class PowerUp implements Runnable {
	Thread t;
	float power_up_x;
	float power_up_y;
	int power_up_height;
	int power_up_width;
	boolean enabled;
	boolean image;
	String power_up_type;
	boolean run;
	Bitmap view_Image;
	long Powerup_time;
	long Powerup_time_up;
	Ball ball;
	Paddle paddle;
	boolean detect_col;
	boolean active_power;
	Context context;
	ArrayList<Ball> balls;
	ArrayList<bullet> bullets;
	
	public PowerUp(Ball b, Paddle p, ArrayList<Ball> balls, Context context, ArrayList<bullet> bullets) {
		ball=b;
		paddle=p;
		run=true;
		detect_col=true;
		active_power=false;
		this.balls=balls;
		this.context=context;
		this.bullets=bullets;
	}
	@Override
	public void run() {
		while(run && !GameView.game_over) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(this.getPower_up_type().equals("fireball")) {
				this.fire_ball();
			}
			else if(this.getPower_up_type().equals("splitball")) {
				this.split_ball();
			}
			else if(this.getPower_up_type().equals("expand")) {
				this.expand_paddle();
			}
			else if(this.getPower_up_type().equals("shrink")) {
				this.shrink_paddle();
			}
			else if(this.getPower_up_type().equals("magnet")) {
				this.magnet_paddle();
			}
			else if(this.getPower_up_type().equals("shoot")) {
				this.bullet_paddle();
			}
			else if(this.getPower_up_type().equals("dead")) {
				this.dead_ball();
			}
		}
		Log.d("fireballexit", "fireballexit");
	}
	public void start() {
		t=new Thread(this,"powerUp");
		t.start();
	}
	public Bitmap getView_Image() {
		return view_Image;
	}
	public void setView_Image(Bitmap view_Image) {
		this.view_Image = view_Image;
	}
	public void draw(Canvas canvas) {
		if(canvas.getHeight()>this.getPower_up_y() && image) {
			canvas.drawBitmap(this.getView_Image(), this.getPower_up_x(), this.getPower_up_y(), null);
		}
	}
	public void update(Canvas canvas) {
		if(canvas.getHeight()+4>this.getPower_up_y()+4) {
			this.setPower_up_y(this.getPower_up_y()+4);
		}
	}
	public float getPower_up_x() {
		return power_up_x;
	}
	public void setPower_up_x(float power_up_x) {
		this.power_up_x = power_up_x;
	}
	public float getPower_up_y() {
		return power_up_y;
	}
	public void setPower_up_y(float power_up_y) {
		this.power_up_y = power_up_y;
	}
	public int getPower_up_height() {
		return power_up_height;
	}
	public void setPower_up_height(int power_up_height) {
		this.power_up_height = power_up_height;
	}
	public int getPower_up_width() {
		return power_up_width;
	}
	public void setPower_up_width(int power_up_width) {
		this.power_up_width = power_up_width;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isImage() {
		return image;
	}
	public void setImage(boolean image) {
		this.image = image;
	}
	public String getPower_up_type() {
		return power_up_type;
	}
	public void setPower_up_type(String power_up_type) {
		this.power_up_type = power_up_type;
	}
	
	public void detect() {
		if(paddle.getPaddle_x()<this.getPower_up_x() && paddle.getPaddle_x()+paddle.getPaddle_width()>this.getPower_up_x()) {
			if(paddle.getPaddle_y()<this.getPower_up_y()+this.getPower_up_height()+4 && paddle.getPaddle_y()+paddle.getPaddle_height()>this.getPower_up_y()+this.getPower_up_height()+4) {
				if(this.power_up_type.equals("fireball")) this.fire_ball_init();
				else if(this.power_up_type.equals("splitball")) this.split_ball_init(); 
				else if(this.power_up_type.equals("expand")) this.expand_paddle_init();
				else if(this.power_up_type.equals("shrink")) this.shrink_paddle_init();
				else if(this.power_up_type.equals("magnet")) this.magnet_paddle_init();
				else if(this.power_up_type.equals("shoot")) this.bullet_paddle_init();
				else if(this.power_up_type.equals("dead")) this.dead_ball_init();
			}
		}
		else if(paddle.getPaddle_x()<this.getPower_up_x()+this.getPower_up_width() && paddle.getPaddle_x()+paddle.getPaddle_width()>this.getPower_up_x()+this.getPower_up_width()) {
			if(paddle.getPaddle_y()<this.getPower_up_y()+this.getPower_up_height()+4 && paddle.getPaddle_y()+paddle.getPaddle_height()>this.getPower_up_y()+this.getPower_up_height()+4) {
				if(this.power_up_type.equals("fireball")) this.fire_ball_init();
				else if(this.power_up_type.equals("splitball")) this.split_ball_init();
				else if(this.power_up_type.equals("expand")) this.expand_paddle_init();
				else if(this.power_up_type.equals("shrink")) this.shrink_paddle_init();
				else if(this.power_up_type.equals("magnet")) this.magnet_paddle_init();
				else if(this.power_up_type.equals("shoot")) this.bullet_paddle_init();
				else if(this.power_up_type.equals("dead")) this.dead_ball_init();
			}
		}
	}
	public void fire_ball() {
		if(detect_col) detect();
		if(this.active_power) {
			Log.d("fireballactive", "fireballactive");
			if(System.currentTimeMillis()>Powerup_time+5000) {
				Log.d("fireballremove", "fireballremove");
				ball.setBall_type("normal");
				ball.setBall_height(40);
				ball.setBall_width(40);
				ball.setBall_image(ball.getNormal_ball());
				image=false;
				run=false;
				this.setEnabled(false);
			}
		}
	}
	public void fire_ball_init() {
		if(!ball.getBall_type().equals("fireball")) {
			Log.d("fireball", "fireball");
			ball.setBall_type("fireball");
			ball.setBall_height(60);
			ball.setBall_width(60);
			Powerup_time=System.currentTimeMillis();
			detect_col=false;
			image=false;
			active_power=true;
		}
		else {
			run=false;
			detect_col=false;
			image=false;
			this.setEnabled(false);
		}
	}
	
	
	public void split_ball_init() {
		InputStream is;
		Bitmap bmpImage = null;
		
		Ball b=new Ball();
		b.setBall_type("normal");
		b.setImage(true);
		b.setStick(false);
		b.setBall_center_x(this.ball.getBall_center_x());
		b.setBall_center_y(this.ball.getBall_center_y());
		b.setSpeed(this.ball.getSpeed());
		b.setSpeed_x(-this.ball.getSpeed_x());
		b.setSpeed_y(this.ball.getSpeed_y());
		b.setBall_height(60);
		b.setBall_width(60);
		try {
			is = context.getAssets().open("images/fireball2.png");
			bmpImage = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		b.setFireBall(bmpImage);
		
		b.setBall_height(40);
		b.setBall_width(40);
		try {
			is = context.getAssets().open("images/ball.png");
			bmpImage = BitmapFactory.decodeStream(is);
			bmpImage = Bitmap.createScaledBitmap(bmpImage, b.getBall_width(), b.getBall_height(), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		b.setBall_image(bmpImage);
		b.setNormal_ball(bmpImage);
		b.setEnabled(true);
		balls.add(b);
		run=false;
		detect_col=false;
		image=false;
		this.setEnabled(false);
	}
	public void split_ball() {
		if(detect_col) detect();
	}
	public void expand_paddle() {
		if(detect_col) detect();
	}
	public void expand_paddle_init() {
		if(this.paddle.getPaddle_width()<400) {
			this.paddle.setPaddle_width(this.paddle.getPaddle_width()+100);
		}
		
		run=false;
		detect_col=false;
		image=false;
		this.setEnabled(false);
	}
	public void shrink_paddle() {
		if(detect_col) detect();
	}
	public void shrink_paddle_init() {
		if(this.paddle.getPaddle_width()>200) {
			this.paddle.setPaddle_width(this.paddle.getPaddle_width()-100);
		}
		
		run=false;
		detect_col=false;
		image=false;
		this.setEnabled(false);
	}
	public void magnet_paddle() {
		if(detect_col) detect();
	}
	public void magnet_paddle_init() {
		GameView.stick=true;
		
		run=false;
		detect_col=false;
		image=false;
		this.setEnabled(false);
	}
	public void bullet_paddle() {
		if(detect_col) detect();
		
		if(this.active_power) {
			if(System.currentTimeMillis()>Powerup_time+5000) {;
				GameView.shoot=false;
			
				image=false;
				run=false;
				this.setEnabled(false);
			}
			else {

			}
		}	
	}
	public void bullet_paddle_init() {
		GameView.shoot=true;
		
		Powerup_time=System.currentTimeMillis();
		detect_col=false;
		image=false;
		active_power=true;
	}
	
	
	
	public void dead_ball() {
		if(detect_col) detect();
	}
	public void dead_ball_init() {
		if(GameView.life_no>1) {
			if(GameView.new_life_time==0) {
				GameView.life_no--;
				GameView.new_life_time=System.currentTimeMillis()+3000;
			}
		}
		else {
			GameView.life_no--;
			GameView.game_over=true;
		}
		run=false;
		detect_col=false;
		image=false;
		this.setEnabled(false);
	}
}
