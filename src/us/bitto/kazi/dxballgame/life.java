package us.bitto.kazi.dxballgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class life {
	float X;
	float Y;
	float Width;
	float Height;
	Bitmap image;
	public float getX() {
		return X;
	}
	public void setX(float x) {
		X = x;
	}
	public float getY() {
		return Y;
	}
	public void setY(float y) {
		Y = y;
	}
	public float getWidth() {
		return Width;
	}
	public void setWidth(float width) {
		Width = width;
	}
	public float getHeight() {
		return Height;
	}
	public void setHeight(float height) {
		Height = height;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public void draw(Canvas canvas) {
		canvas.drawBitmap(image, X, Y, null);
	}
}
