package us.bitto.kazi.dxballgame;

import java.util.ArrayList;

import android.util.Log;

public class CollisionDetector implements Runnable {
	Thread t;
	ArrayList<Ball> balls;
	Paddle p;
	public boolean run;
	public CollisionDetector(ArrayList<Ball> balls, Paddle p) {
		this.balls=balls;
		this.p=p;
		run=true;
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
			for(int i=0; i<this.balls.size(); i++) {
				Ball b=this.balls.get(i);
				if(b.isStick()) continue;
				
				
				if(p.getPaddle_x()<b.getBall_center_x()-b.getSpeed_x() && p.getPaddle_x()+p.getPaddle_width()>b.getBall_center_x()-b.getSpeed_x()) {
					if(p.getPaddle_y()<b.getBall_center_y()+b.getBall_height()+b.getSpeed_y()+b.getSpeed_y() && p.getPaddle_y()+p.getPaddle_height()>b.getBall_center_y()+b.getBall_height()+b.getSpeed_y()) {
						b.setBall_center_y(p.getPaddle_y()-b.getBall_height()-1);
						float p_div=p.getPaddle_width()/5;
						if(b.getBall_center_x()+b.getBall_width()<p.getPaddle_x()+(p_div*1)) {
							if(b.getSpeed_x()>0) b.setSpeed_x(-b.getSpeed_x());
						}
						else if(b.getBall_center_x()+b.getBall_width()>p.getPaddle_x()+(p_div*1) && b.getBall_center_x()+b.getBall_width()<p.getPaddle_x()+(p_div*2)) {
							if(b.getSpeed_x()==3 || b.getSpeed_x()==-3) {
								b.setSpeed_x(b.getSpeed_x()*2);
							}
							else {
								b.setSpeed_x(b.getSpeed_x()/2);
							}
						}
						else if(b.getBall_center_x()>p.getPaddle_x()+(p_div*3) && b.getBall_center_x()<p.getPaddle_x()+(p_div*4)) {
							if(b.getSpeed_x()==3 || b.getSpeed_x()==-3) {
								b.setSpeed_x(b.getSpeed_x()*2);
							}
							else {
								b.setSpeed_x(b.getSpeed_x()/2);
							}
						}
						else if(b.getBall_center_x()>p.getPaddle_x()+(p_div*4) && b.getBall_center_x()<p.getPaddle_x()+(p_div*5)) {
							if(b.getSpeed_x()<0) b.setSpeed_x(-b.getSpeed_x());
						}
						else if(b.getBall_center_x()+(b.getBall_width()/2)>p.getPaddle_x()+(p_div*2) && b.getBall_center_x()+(b.getBall_width()/2)<p.getPaddle_x()+(p_div*3)) {
							if(b.getSpeed_x()>3 || b.getSpeed_x()<-3) {
								b.setSpeed_x(b.getSpeed_x()/2);
							}
						}
						b.setSpeed_y(-b.getSpeed_y());
						if (GameView.stick) b.setStick(true);
					}
				}
				else if(p.getPaddle_x()<b.getBall_center_x()+b.getBall_width()+b.getSpeed_x() && p.getPaddle_x()+p.getPaddle_width()>b.getBall_center_x()+b.getBall_width()+b.getSpeed_x()) {
					if(p.getPaddle_y()<b.getBall_center_y()+b.getBall_height()+b.getSpeed_y() && p.getPaddle_y()+p.getPaddle_height()>b.getBall_center_y()+b.getBall_height()+b.getSpeed_y()) {
						b.setBall_center_y(p.getPaddle_y()-b.getBall_height()-1);
						float p_div=p.getPaddle_width()/5;
						if(b.getBall_center_x()+b.getBall_width()<p.getPaddle_x()+(p_div*1)) {
							if(b.getSpeed_x()>0) b.setSpeed_x(-b.getSpeed_x());
						}
						else if(b.getBall_center_x()+b.getBall_width()>p.getPaddle_x()+(p_div*1) && b.getBall_center_x()+b.getBall_width()<p.getPaddle_x()+(p_div*2)) {
							if(b.getSpeed_x()==3 || b.getSpeed_x()==-3) {
								b.setSpeed_x(b.getSpeed_x()*2);
							}
							else {
								b.setSpeed_x(b.getSpeed_x()/2);
							}
						}
						else if(b.getBall_center_x()>p.getPaddle_x()+(p_div*3) && b.getBall_center_x()<p.getPaddle_x()+(p_div*4)) {
							if(b.getSpeed_x()==3 || b.getSpeed_x()==-3) {
								b.setSpeed_x(b.getSpeed_x()*2);
							}
							else {
								b.setSpeed_x(b.getSpeed_x()/2);
							}
						}
						else if(b.getBall_center_x()>p.getPaddle_x()+(p_div*4) && b.getBall_center_x()<p.getPaddle_x()+(p_div*5)) {
							if(b.getSpeed_x()<0) b.setSpeed_x(-b.getSpeed_x());
						}
						else if(b.getBall_center_x()+(b.getBall_width()/2)>p.getPaddle_x()+(p_div*2) && b.getBall_center_x()+(b.getBall_width()/2)<p.getPaddle_x()+(p_div*3)) {
							if(b.getSpeed_x()>3 || b.getSpeed_x()<-3) {
								b.setSpeed_x(b.getSpeed_x()/2);
							}
						}
						b.setSpeed_y(-b.getSpeed_y());
						if (GameView.stick) b.setStick(true);
					}
				}
				
				
				
				/*if(p.getPaddle_x()<b.getBall_center_x() && p.getPaddle_x()+p.getPaddle_width()>b.getBall_center_x()) {
					if(p.getPaddle_y()<b.getBall_center_y()+b.getBall_height()+b.getSpeed_y()+b.getSpeed_y() && p.getPaddle_y()+p.getPaddle_height()>b.getBall_center_y()+b.getBall_height()+b.getSpeed_y()) {
						b.setSpeed_y(-b.getSpeed_y());
						if (GameView.stick) b.setStick(true);
					}
				}
				else if(p.getPaddle_x()<b.getBall_center_x()+b.getBall_width()+b.getSpeed_x() && p.getPaddle_x()+p.getPaddle_width()>b.getBall_center_x()+b.getBall_width()+b.getSpeed_x()) {
					if(p.getPaddle_y()<b.getBall_center_y()+b.getBall_height()+b.getSpeed_y() && p.getPaddle_y()+p.getPaddle_height()>b.getBall_center_y()+b.getBall_height()+b.getSpeed_y()) {
						b.setSpeed_y(-b.getSpeed_y());
						if (GameView.stick) b.setStick(true);
					}
				}*/
				/*if(!balls.get(i).isStick()) balls.get(i).update(GameView.VIEW_WIDTH, GameView.VIEW_HEIGHT);
	        	if(!balls.get(i).isEnabled()) {
	        		if(balls.size()==1) GameView.game_over=true;
	        		else {
		        		balls.remove(i);
		        		i--;
	        		}
	        	}*/
			}
		}
	}
	public void start() {
		t=new Thread(this,"collision");
		t.start();
	}
}
