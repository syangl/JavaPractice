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
	
	//����ģʽ
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
		//������panel��frame,�ȱ���������������panel
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
		setTitle("�������ս");
	}
	
	//���ƴ���
	public void localUserPutChess(int row, int col) {

		//����ֱ����
		boolean success = 
				ChessModel.getInstance().putChess(row,col,color);//putChess���жϵ��λ���ǲ��������ˣ��ɹ������ά����
		if(success) {//����ɹ�
			Message m = new Message(row, col, color);
			ChessModel.getInstance().datalist.push(m);//������
			color=-color;
			int winner = ChessModel.getInstance().whoWin(row, col, -color);
			switch(winner) {
			case ChessModel.BLACK:
				isOver = true;//����Ӯ
				winColor  = ChessModel.BLACK;
				break;
			case ChessModel.WHITE:
				isOver = true;//����Ӯ
				winColor  = ChessModel.WHITE;
				break;
			case ChessModel.SPACE:
				isOver = false;
				break;
			}
			if(!isOver) {//ûӮ
				ChessView.getInstance().updateChess();//������
			}else {//Ӯ��
				ChessView.getInstance().updateChess();//���һ��
				if(winColor==ChessModel.getInstance().BLACK) {
					localWinName = new String("�ڷ�");
				}else if (winColor==ChessModel.getInstance().WHITE) {
					localWinName = new String("�׷�");
				}
				winColor = ChessModel.getInstance().SPACE;
				ChessView.getInstance().outputEnd();//��������
				color = ChessModel.BLACK;
			}
		}
	}
	
	//��������
	public void webUserPutChess(int row,int col, int color){
		boolean success = 
				ChessModel.getInstance().putChess(row,col,color);//putChess���жϵ��λ���ǲ��������ˣ��ɹ������ά����
		if(success) {//����ɹ�
			Message m = new Message(row, col, color);
			ChessModel.getInstance().datalist.push(m);//������
			int winner = ChessModel.getInstance().whoWin(row, col, color);
			switch(winner) {
			case ChessModel.BLACK:
				isOver = true;//����Ӯ
				winColor  = ChessModel.BLACK;
				break;
			case ChessModel.WHITE:
				isOver = true;//����Ӯ
				winColor  = ChessModel.WHITE;
				break;
			case ChessModel.SPACE:
				isOver = false;
				break;
			}
			if(!isOver) {//ûӮ
				ChessView.getInstance().updateChess();//������
			}else {//Ӯ��
				ChessView.getInstance().updateChess();//���һ��
				if(winColor==ChessModel.getInstance().BLACK) {
					webWinName = new String("�ڷ�");
				}else if (winColor==ChessModel.getInstance().WHITE) {
					webWinName = new String("�׷�");
				}
				winColor = ChessModel.getInstance().SPACE;
				ChessView.getInstance().outputEnd();//��������
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
