package us.bitto.kazi.dxballgame;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BallBrickCollisionDetector implements Runnable {
	Thread t;
	ArrayList<Brick> bricks;
	ArrayList<PowerUp> power_up;
	ArrayList<Ball> balls;
	ArrayList<bullet> bullets;
	Paddle p;
	public boolean run;
	Context context;
	Bitmap img[];
	public BallBrickCollisionDetector(ArrayList<Ball> balls, ArrayList<Brick> bricks, ArrayList<PowerUp> power_up, Paddle p, Context context, Bitmap img[], ArrayList<bullet> bullets) {
		this.balls=balls;
		this.bricks=bricks;
		this.power_up=power_up;
		this.p=p;
		this.context=context;
		this.img=new Bitmap[1];
		this.img=img;
		run=true;
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
			for(int i=0; i<this.bricks.size(); i++) {
				boolean removed=false;
				for(int j=0; j<this.balls.size() && i>-1; j++) {
					Ball b=this.balls.get(j);
					if(bricks.get(i).getBrick_x()<b.getBall_center_x() && bricks.get(i).getBrick_x()+bricks.get(i).getBrick_width()>b.getBall_center_x()) {
						if(bricks.get(i).getBrick_y()<b.getBall_center_y()+b.getSpeed_y() && bricks.get(i).getBrick_y()+bricks.get(i).getBrick_height()>b.getBall_center_y()+b.getSpeed_y()) {
							if(!b.getBall_type().equals("fireball")) b.setSpeed_y(-b.getSpeed_y());
							PowerUp p_up=new PowerUp(b, this.p, this.balls, this.context, this.bullets);
							p_up.setPower_up_x(bricks.get(i).getBrick_x()+40);
							p_up.setPower_up_y(bricks.get(i).getBrick_y());
							p_up.setEnabled(true);
							p_up.setPower_up_width(70);
							p_up.setPower_up_height(70);
							p_up.setImage(true);
							
							if(bricks.get(i).power_up.equals("fireball")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[0]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("splitball")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[1]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("expand")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[2]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("shrink")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[3]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("magnet")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[5]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("shoot")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[4]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("dead")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[6]);
								p_up.start();
								this.power_up.add(p_up);
							}
							
							if(bricks.get(i).getBrick_level()>1) bricks.get(i).setBrick_level((bricks.get(i).getBrick_level()-1));
							else bricks.remove(i);
							GameView.score=GameView.score+3;
							i--;
							removed=true;
							continue;
						}
						else if(bricks.get(i).getBrick_y()<b.getBall_center_y()+b.getBall_height()+b.getSpeed_y() && bricks.get(i).getBrick_y()+bricks.get(i).getBrick_height()>b.getBall_center_y()+b.getBall_height()+b.getSpeed_y()) {
							if(!b.getBall_type().equals("fireball")) b.setSpeed_y(-b.getSpeed_y());
							PowerUp p_up=new PowerUp(b, this.p, this.balls, this.context, this.bullets);
							p_up.setPower_up_x(bricks.get(i).getBrick_x()+40);
							p_up.setPower_up_y(bricks.get(i).getBrick_y());
							p_up.setEnabled(true);
							p_up.setPower_up_width(70);
							p_up.setPower_up_height(70);
							p_up.setImage(true);
							
							if(bricks.get(i).power_up.equals("fireball")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[0]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("splitball")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[1]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("expand")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[2]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("shrink")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[3]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("magnet")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[5]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("shoot")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[4]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("dead")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[6]);
								p_up.start();
								this.power_up.add(p_up);
							}
							
							if(bricks.get(i).getBrick_level()>1) bricks.get(i).setBrick_level((bricks.get(i).getBrick_level()-1));
							else bricks.remove(i);
							GameView.score=GameView.score+3;
							i--;
							removed=true;
							continue;
						}
					}
					else if(bricks.get(i).getBrick_x()<b.getBall_center_x()+b.getBall_width() && bricks.get(i).getBrick_x()+bricks.get(i).getBrick_width()>b.getBall_center_x()+b.getBall_width()) {
						if(bricks.get(i).getBrick_y()<b.getBall_center_y()+b.getSpeed_y() && bricks.get(i).getBrick_y()+bricks.get(i).getBrick_height()>b.getBall_center_y()+b.getSpeed_y()) {
							PowerUp p_up=new PowerUp(b, this.p, this.balls, this.context, this.bullets);
							p_up.setPower_up_x(bricks.get(i).getBrick_x()+40);
							p_up.setPower_up_y(bricks.get(i).getBrick_y());
							p_up.setEnabled(true);
							p_up.setPower_up_width(70);
							p_up.setPower_up_height(70);
							p_up.setImage(true);
							
							
							if(bricks.get(i).power_up.equals("fireball")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[0]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("splitball")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[1]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("expand")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[2]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("shrink")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[3]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("magnet")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[5]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("shoot")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[4]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("dead")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[6]);
								p_up.start();
								this.power_up.add(p_up);
							}
							
							if(bricks.get(i).getBrick_level()>1) bricks.get(i).setBrick_level((bricks.get(i).getBrick_level()-1));
							else bricks.remove(i);
							GameView.score=GameView.score+3;
							i--;
							removed=true;
							if(!b.getBall_type().equals("fireball"))
							b.setSpeed_y(-b.getSpeed_y());
						}
						else if(bricks.get(i).getBrick_y()<b.getBall_center_y()+b.getBall_height()+b.getSpeed_y() && bricks.get(i).getBrick_y()+bricks.get(i).getBrick_height()>b.getBall_center_y()+b.getBall_height()+b.getSpeed_y()) {
							PowerUp p_up=new PowerUp(b, this.p, this.balls, this.context, this.bullets);
							p_up.setPower_up_x(bricks.get(i).getBrick_x()+40);
							p_up.setPower_up_y(bricks.get(i).getBrick_y());
							p_up.setEnabled(true);
							p_up.setPower_up_width(70);
							p_up.setPower_up_height(70);
							p_up.setImage(true);
							
							
							if(bricks.get(i).power_up.equals("fireball")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[0]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("splitball")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[1]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("expand")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[2]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("shrink")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[3]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("magnet")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[5]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("shoot")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[4]);
								p_up.start();
								this.power_up.add(p_up);
							}
							else if(bricks.get(i).power_up.equals("dead")) {
								p_up.setPower_up_type(bricks.get(i).getPower_up());
								p_up.setView_Image(img[6]);
								p_up.start();
								this.power_up.add(p_up);
							}
							
							if(bricks.get(i).getBrick_level()>1) bricks.get(i).setBrick_level((bricks.get(i).getBrick_level()-1));
							else bricks.remove(i);
							GameView.score=GameView.score+3;
							i--;
							removed=true;
							if(!b.getBall_type().equals("fireball"))
							b.setSpeed_y(-b.getSpeed_y());
						}
					}
				}
				if(removed) continue;
					for(int j=0; j<this.bullets.size(); j++) {
						if(this.bullets.get(j).getX()>this.bricks.get(i).getBrick_x()&& this.bullets.get(j).getX()<this.bricks.get(i).getBrick_x()+this.bricks.get(i).getBrick_width()) {
							if(this.bullets.get(j).getY()>this.bricks.get(i).getBrick_y() && this.bullets.get(j).getY()<this.bricks.get(i).getBrick_y()+this.bricks.get(i).getBrick_height()) {
								this.bullets.get(j).setEnable(false);
								this.bricks.remove(i);
								GameView.score=GameView.score+1;
								i--;
								break;
							}
						}
					}
				
			}
		}
		Log.d("exitloop", "exitloop");
	}
	public void start() {
		t=new Thread(this,"ballbrickcollision");
		t.start();
	}
}
