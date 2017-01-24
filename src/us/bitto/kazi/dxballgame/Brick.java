package us.bitto.kazi.dxballgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Brick {
	float brick_x;
	float brick_y;
	int brick_height;
	int brick_width;
	boolean enabled;
	boolean image;
	float brick_type;
	int brick_level;
	Bitmap Brick_image;
	String power_up;
	public String getPower_up() {
		return power_up;
	}
	public void setPower_up(String power_up) {
		this.power_up = power_up;
	}
	public float getBrick_x() {
		return brick_x;
	}
	public void setBrick_x(float brick_x) {
		this.brick_x = brick_x;
	}
	public float getBrick_y() {
		return brick_y;
	}
	public void setBrick_y(float brick_y) {
		this.brick_y = brick_y;
	}
	public int getBrick_height() {
		return brick_height;
	}
	public void setBrick_height(int brick_height) {
		this.brick_height = brick_height;
	}
	public int getBrick_width() {
		return brick_width;
	}
	public void setBrick_width(int brick_width) {
		this.brick_width = brick_width;
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
	public float getBrick_type() {
		return brick_type;
	}
	public void setBrick_type(float brick_type) {
		this.brick_type = brick_type;
	}
	public int getBrick_level() {
		return brick_level;
	}
	public void setBrick_level(int brick_level) {
		this.brick_level = brick_level;
	}
	public Bitmap getBrick_image() {
		return Brick_image;
	}
	public void setBrick_image(Bitmap brick_image) {
		Brick_image = brick_image;
	}
	public void draw(Canvas canvas) {
		if(image) canvas.drawBitmap(this.getBrick_image(), this.getBrick_x(), this.getBrick_y(), null);
		else {
			Paint paint=new Paint();
			paint.setStrokeWidth(3);
			if (this.getBrick_level()==1) {
				paint.setColor(Color.MAGENTA);
			}
			else if(this.getBrick_level()==2) {
				paint.setColor(Color.LTGRAY);
			}
			canvas.drawRect(this.getBrick_x(), this.getBrick_y(), this.getBrick_x()+this.getBrick_width(), this.getBrick_y()+this.getBrick_height(), paint);
		}
	}
}
