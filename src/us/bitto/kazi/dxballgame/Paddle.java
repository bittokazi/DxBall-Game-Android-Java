package us.bitto.kazi.dxballgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Paddle {
	float paddle_x;
	float paddle_y;
	float paddle_height;
	float paddle_width;
	boolean image;
	String paddle_type;
	Bitmap paddle_image;
	public float getPaddle_x() {
		return paddle_x;
	}
	public void setPaddle_x(float paddle_x) {
		this.paddle_x = paddle_x;
	}
	public float getPaddle_y() {
		return paddle_y;
	}
	public void setPaddle_y(float paddle_y) {
		this.paddle_y = paddle_y;
	}
	public float getPaddle_height() {
		return paddle_height;
	}
	public void setPaddle_height(float paddle_height) {
		this.paddle_height = paddle_height;
	}
	public float getPaddle_width() {
		return paddle_width;
	}
	public void setPaddle_width(float paddle_width) {
		this.paddle_width = paddle_width;
	}
	public boolean isImage() {
		return image;
	}
	public void setImage(boolean image) {
		this.image = image;
	}
	public String getPaddle_type() {
		return paddle_type;
	}
	public void setPaddle_type(String paddle_type) {
		this.paddle_type = paddle_type;
	}
	public Bitmap getPaddle_image() {
		return paddle_image;
	}
	public void setPaddle_image(Bitmap paddle_image) {
		this.paddle_image = paddle_image;
	}
	
	public void draw(Canvas canvas) {
		Paint paint=new Paint();
		if(GameView.stick) paint.setColor(Color.RED);
		else paint.setColor(Color.BLUE);
		paint.setStrokeWidth(3);		
		if(this.isImage()) canvas.drawBitmap(getPaddle_image(), this.getPaddle_x(), this.getPaddle_y(), null);
		else canvas.drawRect(getPaddle_x(), getPaddle_y(), getPaddle_x()+getPaddle_width(), getPaddle_y()+getPaddle_height(), paint);
		if(GameView.stick) {
			canvas.drawLine(getPaddle_x(), getPaddle_y()-8, getPaddle_x()+getPaddle_width(), getPaddle_y()-8, paint);
		}
		if(GameView.shoot) {
			canvas.drawLine(getPaddle_x(), getPaddle_y()-8, getPaddle_x(), getPaddle_y(), paint);
			canvas.drawLine(getPaddle_x()+getPaddle_width(), getPaddle_y()-8, getPaddle_x()+getPaddle_width(), getPaddle_y(), paint);
		}
	}
}
