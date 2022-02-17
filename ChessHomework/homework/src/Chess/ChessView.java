package Chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ChessView extends JPanel {
	private int w = ChessControl.getInstance().getWidth(),h = ChessControl.getInstance().getHeight();
	private int unit = 35,revise = 10;
	private int sx = 55,sy = 60,x = 0,y = 0;
	//单例模式
	private ChessView() {};
	private static ChessView instance;
	public static ChessView getInstance(){
		if(instance == null) {
			instance = new ChessView();
			instance.create();
		}
		return instance;
	}
	
	public void create() {
		setLayout(new BorderLayout());
		setBounds(210,140,2500, 1000);
		setVisible(true);
		setBackground(new Color(228,180,111));
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//下棋监听
				x = e.getX();
				y = e.getY();
				int row = (y-sy)/unit;
				int col = (x-sx)/unit;
				if((y-sy)%unit>unit/2) {
					row++;
				}
				if((x-sx)%unit>unit/2) {
					col++;
				}
				if(StartPanel.getInstance().whetherlocal == false) {
					PlayerSocket.getInstance().sendChess(row, col);
				}else {
					ChessControl.getInstance().localUserPutChess(row, col);
				}
				super.mousePressed(e);
			}
		});
		
		
		
		
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//棋盘绘制
		drawChessBackground(g);
		drawChessLine(g);
		drawChess(g);
	}

	public void drawChessBackground(Graphics g) {
		//画棋盘框
		g.drawImage(new ImageIcon("chessback.jpeg").getImage(),3, 0, w/2, h-revise*17,new ImageObserver() {
			@Override
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				return false;
			}
		});
	}

	public void drawChessLine(Graphics g) {
		//画线
		for (int i = 0; i < ChessModel.WIDTH; i++) {
			g.drawLine(sx, sy+i*unit, unit*18+sx, sy+i*unit);
		}
		for (int j = 0; j < ChessModel.WIDTH; j++) {
			g.drawLine(sx+j*unit, sy , sx+j*unit, unit*18+sy);
		}
	}
	
	public void drawChess(Graphics g) {
		//画棋
		while(!ChessModel.getInstance().datalist.isEmpty()) {
			Message tmp1 = new Message(0, 0, 0);
			tmp1 = ChessModel.getInstance().datalist.poll();
			ChessModel.getInstance().helplist.push(tmp1);
			if(tmp1.color == ChessModel.BLACK) {
				g.setColor(new Color(0, 0, 0));
			}else if(tmp1.color == ChessModel.WHITE){
				g.setColor(new Color(255, 255, 255));
			}
			int cheSize = (int) (unit*0.9);
			g.fillOval((tmp1.col*unit+sx-cheSize/2),(tmp1.row*unit+sy-cheSize/2), cheSize,cheSize);
		}
		while(!ChessModel.getInstance().helplist.isEmpty()) {
			Message tmp2 = new Message(0, 0, 0);
			tmp2 = ChessModel.getInstance().helplist.poll();
			ChessModel.getInstance().datalist.push(tmp2);
		}
	}
	//操作代码
	public void updateChess() {
		repaint();
	}

	

	public void outputEnd() {
		//结束
		if(StartPanel.getInstance().whetherlocal == true) {//本地
			LocalEndDialog.getInstance().show();
		}else {//联网
			WebEndDialog.getInstance().show();
		}
	}

	public void surrender() {
		WebEndDialog.getInstance().show();
	}
	
	//拖动改变整个界面各个面板的比例，等比例缩放
	public void reSized(){
		w = ChessControl.getInstance().getWidth();
		h = ChessControl.getInstance().getHeight();
		int max = w>h?w:h;
		unit = (max/2)/21;
		sx = unit+revise*2;
		sy = unit+revise*2;
		EastPanel.getInstance().setBounds(w*5/8, 140,w*3/8-revise, h);
		WestPanel.getInstance().setBounds(0, 10, w/8, h);
		NorthPanel.getInstance().setBounds(0, 0, w, 140);
		getInstance().setBounds(w*5/8-w/2, 140, w/2, h);
		repaint();
	}

}
