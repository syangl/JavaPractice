package homework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;


import javax.swing.JPanel;

class Message{
	int msx,msy,mex,mey,mtsize;
	String ms = new String(),mstrt = new String();
	boolean mselect;
	Color mc = new Color(255, 255, 255);
	public Message(int msx,int msy,int mex,int mey,String ms,String mstrt,boolean mselect,Color mc,int mtsize) {
		this.msx = msx;
		this.msy = msy;
		this.mex = mex;
		this.mey = mey;
		this.ms = ms;
		this.mstrt = mstrt;
		this.mselect = mselect;
		this.mc = mc;
		this.mtsize = mtsize;
	}
}

public class MyPanel extends JPanel{

	int sx=0,sy=0,ex=0,ey=0;
	String s = new String();
	String strt = new String();
	Color color = new Color(0, 0, 0);
	int tsize = 10;
	boolean select = false;
	LinkedList<Message> sta = new LinkedList<Message>();
	LinkedList<Message> help = new LinkedList<Message>();
	public MyPanel() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sx = e.getX();
				sy = e.getY();	
				MyFrame.x1 = sx;
				MyFrame.y1 = sy;
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ex = e.getX();
				ey = e.getY();
				MyFrame.x2 = ex;
				MyFrame.y2 = ey;
				MyFrame.re();
				Message m = new Message(sx,sy,ex,ey,s,strt,select,color,tsize);
				sta.push(m);
				repaint();
			}	
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				ex = e.getX();
				ey = e.getY();
				repaint();
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		while(!sta.isEmpty()) {
			int tsx=0,tsy=0,tex=0,tey=0,ttsize=0;String ts="",tstrt="";boolean tselect=false;Color tcolor;
			tsx = sta.peekLast().msx;
			tsy = sta.peekLast().msy;
			tex = sta.peekLast().mex;
			tey = sta.peekLast().mey;
			ts = sta.peekLast().ms;
			tstrt = sta.peekLast().mstrt;
			tselect = sta.peekLast().mselect;
			ttsize = sta.peekLast().mtsize;
			tcolor = sta.peekLast().mc;
			
			Message tmp = new Message(tsx, tsy, tex, tey, ts, tstrt, tselect, tcolor, ttsize);
			help.push(tmp);
			sta.pollLast();
			
			g.setColor(tcolor);
			g.setFont(new Font("ËÎÌו",Font.BOLD,ttsize));
			if(ts.equals("1")) {
				if(!tselect) {
					g.drawRect(Math.min(tsx,tex), Math.min(tsy,tey),Math.abs(tex-tsx), Math.abs(tey-tsy));
				}else {
					g.fillRect(Math.min(tsx,tex), Math.min(tsy,tey),Math.abs(tex-tsx), Math.abs(tey-tsy));
				}
			}else if (ts.equals("2")) {
				if(!tselect) {
					g.drawOval(Math.min(tsx,tex), Math.min(tsy,tey),Math.abs(tex-tsx), Math.abs(tey-tsy));
				}else {
					g.fillOval(Math.min(tsx,tex), Math.min(tsy,tey),Math.abs(tex-tsx), Math.abs(tey-tsy));
				}
			}else if (ts.equals("3")) {
				int towh=Math.min(Math.abs(tex-tsx),Math.abs(tey-tsy));int tosx=0,tosy=0;
				if(tex>tsx) {
					tosx = tsx;
				}else {
					tosx = tsx-towh;
				}
				if(tey>tsy) {
					tosy = tsy;
				}else {
					tosy = tsy-towh;
				}
				if(!tselect) {
					g.drawOval(tosx,tosy,towh,towh);
				}else {
					g.fillOval(tosx,tosy,towh,towh);
				}
			}else if (ts.equals("4")) {
					g.drawLine(tsx,tsy, tex, tey);
			}else if(ts.equals("5")) {
				g.drawString(tstrt, tex, tey);
			}
		}
		
		while(!help.isEmpty()) {
			sta.push(help.peekLast());
			help.pollLast();
		}
		
		g.setColor(color);
		g.setFont(new Font("ËÎÌו",Font.BOLD,tsize));
		
		if(s.equals("1")) {
			if(!select) {
				g.drawRect(Math.min(sx,ex), Math.min(sy,ey),Math.abs(ex-sx), Math.abs(ey-sy));
			}else {
				g.fillRect(Math.min(sx,ex), Math.min(sy,ey),Math.abs(ex-sx), Math.abs(ey-sy));
			}
		}else if (s.equals("2")) {
			if(!select) {
				g.drawOval(Math.min(sx,ex),Math.min(sy,ey),Math.abs(ex-sx),Math.abs(ey-sy));
			}else {
				g.fillOval(Math.min(sx,ex),Math.min(sy,ey),Math.abs(ex-sx),Math.abs(ey-sy));
			}
		}else if (s.equals("3")) {
			int owh=Math.min(Math.abs(ex-sx),Math.abs(ey-sy));int osx=0,osy=0;
			if(ex>sx) {
				osx = sx;
			}else {
				osx = sx-owh;
			}
			if(ey>sy) {
				osy = sy;
			}else {
				osy = sy-owh;
			}
			if(!select) {
				g.drawOval(osx,osy,owh,owh);
			}else {
				g.fillOval(osx,osy,owh,owh);
			}
		}else if (s.equals("4")) {
				g.drawLine(sx,sy, ex, ey);
		}else if(s.equals("5")) {
			g.drawString(strt, ex, ey);
		}
	}
	
	public void clear() {
		this.s = "";
		this.strt = "";
		this.tsize = 10;
		this.sx = this.sy = this.ex = this.ey = 0;
		while(!sta.isEmpty()) {
			sta.pop();
		}
		while(!help.isEmpty()) {
			help.pop();
		}
	}
}
