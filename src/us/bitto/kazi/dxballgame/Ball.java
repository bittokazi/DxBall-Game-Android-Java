package us.bitto.kazi.dxballgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Ball {
	String ball_type;
	int ball_size;
	boolean enabled;
	float ball_center_x;
	float ball_center_y;
	boolean image;
	int radious;
	Bitmap ball_image;
	Bitmap fire_ball_image[][];
	Bitmap normal_ball;
	long startTime;
	int fireBall_frame=0;
	int fireBall_direction=0;
	
	int ball_height;
	int ball_width;
	
	float speed;
	float speed_x;
	float speed_y;
	boolean stick;
	public boolean isStick() {
		return stick;
	}
	public void setStick(boolean stick) {
		this.stick = stick;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getSpeed_x() {
		return speed_x;
	}
	public void setSpeed_x(float speed_x) {
		this.speed_x = speed_x;
	}
	public float getSpeed_y() {
		return speed_y;
	}
	public void setSpeed_y(float speed_y) {
		this.speed_y = speed_y;
	}
	public void Ball() {
		
	}
	public String getBall_type() {
		return ball_type;
	}
	public void setBall_type(String ball_type) {
		this.ball_type = ball_type;
	}
	public int getBall_size() {
		return ball_size;
	}
	public void setBall_size(int ball_size) {
		this.ball_size = ball_size;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public float getBall_center_x() {
		return ball_center_x;
	}
	public void setBall_center_x(float ball_center_x) {
		this.ball_center_x = ball_center_x;
	}
	public float getBall_center_y() {
		return ball_center_y;
	}
	public void setBall_center_y(float ball_center_y) {
		this.ball_center_y = ball_center_y;
	}
	public boolean isImage() {
		return image;
	}
	public void setImage(boolean image) {
		this.image = image;
	}
	public int getRadious() {
		return radious;
	}
	public void setRadious(int radious) {
		this.radious = radious;
	}
	public Bitmap getNormal_ball() {
		return normal_ball;
	}
	public void setNormal_ball(Bitmap normal_ball) {
		this.normal_ball = normal_ball;
	}
	public Bitmap getBall_image() {
		return ball_image;
	}
	public void setBall_image(Bitmap ball_image) {
		this.ball_image = ball_image;
	}
	public void setFireBall(Bitmap bmp) {
		fire_ball_image=new Bitmap[4][8];
		for(int i=0; i<4; i++) {
			for(int j=0; j<8; j++) {
				fire_ball_image[i][j]=Bitmap.createBitmap(bmp, j*63, i*31, 63, 31);
				fire_ball_image[i][j]=Bitmap.createScaledBitmap(fire_ball_image[i][j], ball_width, ball_height, false);
			}
			//fire_ball_image[i]=Bitmap.createBitmap(bmp, i*60, 0, 60, bmp.getHeight());
			//fire_ball_image[i]=Bitmap.createScaledBitmap(fire_ball_image[i], 40, 40, false);
		}
		
		if(this.getSpeed_x()<0 && this.getBall_center_y()<0) {
			fireBall_direction=0;
		}
		else if(this.getSpeed_x()>0 && this.getBall_center_y()<0) {
			fireBall_direction=1;
		}
		else if(this.getSpeed_x()>0 && this.getBall_center_y()>0) {
			fireBall_direction=2;
		}
		else if(this.getSpeed_x()<0 && this.getBall_center_y()>0) {
			fireBall_direction=3;
		}
		
		this.startTime=System.currentTimeMillis();
		fireBall_frame=0;
		this.setBall_image(fire_ball_image[fireBall_direction][fireBall_frame]);
		fireBall_frame++;
	}
	public void draw(Canvas canvas) {
		canvas.drawBitmap(getBall_image(), getBall_center_x(), getBall_center_y(), null);
	}
	public int getBall_height() {
		return ball_height;
	}
	public void setBall_height(int ball_height) {
		this.ball_height = ball_height;
	}
	public int getBall_width() {
		return ball_width;
	}
	public void setBall_width(int ball_width) {
		this.ball_width = ball_width;
	}
	public void update(int w, int h) {
		if(this.getBall_center_y()+this.getSpeed_y()>0 && this.getBall_center_y()+this.getSpeed_y()+40<h) {
			this.setBall_center_y(this.getBall_center_y()+this.getSpeed_y());
		}
		else {
			if (this.getBall_center_y()+this.getSpeed_y()+40>=h) this.setEnabled(false);
			else this.setSpeed_y(-this.getSpeed_y());
		}
		
		if(this.getBall_center_x()+this.getSpeed_x()>0 && this.getBall_center_x()+this.getSpeed_x()+40<w) {
			this.setBall_center_x(this.getBall_center_x()+this.getSpeed_x());
		}
		else {
			this.setSpeed_x(-this.getSpeed_x());
		}
		
		if(this.getBall_type().equals("fireball")) {
			if(this.startTime+100<System.currentTimeMillis()) {
				if(fireBall_frame<4) {
					this.setBall_image(fire_ball_image[fireBall_direction][fireBall_frame]);
					fireBall_frame++;
				}
				else {
					fireBall_frame=0;
					this.setBall_image(fire_ball_image[fireBall_direction][fireBall_frame]);
					fireBall_frame++;
				}
				this.startTime=System.currentTimeMillis();
			}
		}
		if(this.getSpeed_x()<0 && this.getSpeed_y()<0) {
			fireBall_direction=0;
		}
		else if(this.getSpeed_x()>0 && this.getSpeed_y()<0) {
			fireBall_direction=1;
		}
		else if(this.getSpeed_x()>0 && this.getSpeed_y()>0) {
			fireBall_direction=2;
		}
		else if(this.getSpeed_x()<0 && this.getSpeed_y()>0) {
			fireBall_direction=3;
		}
	}
}
