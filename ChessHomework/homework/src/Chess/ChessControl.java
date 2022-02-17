package Chess;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ChessControl extends JFrame{
	private int color = ChessModel.BLACK;
	private boolean isOver = false;
	private static int winColor = ChessModel.SPACE;
	String localWinName = "";
	String webWinName = "";
	
	public int getWinColor(){
		return winColor;
	}
	
	//单例模式
	private ChessControl() {};
	private static ChessControl instance;
	public static ChessControl getInstance(){
		if(instance == null) {
			instance = new ChessControl();
			instance.create();
		}
		return instance;
	}
	public void create() {
		setLayout(new BorderLayout());
		//创建先panel后frame,先北，后东西，后中央panel
		add(NorthPanel.getInstance(),BorderLayout.NORTH);
		add(WestPanel.getInstance(),BorderLayout.WEST);
		add(EastPanel.getInstance(),BorderLayout.EAST);
		add(ChessView.getInstance(),BorderLayout.CENTER);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ChessView.getInstance().reSized();
			}
		
		});
		//frame
		setBackground(getBackground());
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(50,20,1274, 830);
		setVisible(true);
		setIconImage(new ImageIcon("wuziqi.jpeg").getImage());
		setTitle("五子棋对战");
	}
	
	//控制代码
	public void localUserPutChess(int row, int col) {

		//本地直接下
		boolean success = 
				ChessModel.getInstance().putChess(row,col,color);//putChess会判断点击位置是不是有棋了，成功就填二维数组
		if(success) {//下棋成功
			Message m = new Message(row, col, color);
			ChessModel.getInstance().datalist.push(m);//进队列
			color=-color;
			int winner = ChessModel.getInstance().whoWin(row, col, -color);
			switch(winner) {
			case ChessModel.BLACK:
				isOver = true;//黑棋赢
				winColor  = ChessModel.BLACK;
				break;
			case ChessModel.WHITE:
				isOver = true;//白棋赢
				winColor  = ChessModel.WHITE;
				break;
			case ChessModel.SPACE:
				isOver = false;
				break;
			}
			if(!isOver) {//没赢
				ChessView.getInstance().updateChess();//绘制棋
			}else {//赢了
				ChessView.getInstance().updateChess();//最后一步
				if(winColor==ChessModel.getInstance().BLACK) {
					localWinName = new String("黑方");
				}else if (winColor==ChessModel.getInstance().WHITE) {
					localWinName = new String("白方");
				}
				winColor = ChessModel.getInstance().SPACE;
				ChessView.getInstance().outputEnd();//结束弹窗
				color = ChessModel.BLACK;
			}
		}
	}
	
	//联网下载
	public void webUserPutChess(int row,int col, int color){
		boolean success = 
				ChessModel.getInstance().putChess(row,col,color);//putChess会判断点击位置是不是有棋了，成功就填二维数组
		if(success) {//下棋成功
			Message m = new Message(row, col, color);
			ChessModel.getInstance().datalist.push(m);//进队列
			int winner = ChessModel.getInstance().whoWin(row, col, color);
			switch(winner) {
			case ChessModel.BLACK:
				isOver = true;//黑棋赢
				winColor  = ChessModel.BLACK;
				break;
			case ChessModel.WHITE:
				isOver = true;//白棋赢
				winColor  = ChessModel.WHITE;
				break;
			case ChessModel.SPACE:
				isOver = false;
				break;
			}
			if(!isOver) {//没赢
				ChessView.getInstance().updateChess();//绘制棋
			}else {//赢了
				ChessView.getInstance().updateChess();//最后一步
				if(winColor==ChessModel.getInstance().BLACK) {
					webWinName = new String("黑方");
				}else if (winColor==ChessModel.getInstance().WHITE) {
					webWinName = new String("白方");
				}
				winColor = ChessModel.getInstance().SPACE;
				ChessView.getInstance().outputEnd();//结束弹窗
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
