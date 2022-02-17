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
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LocalEndDialog extends JDialog{
	JTextArea overText;
	//单例模式
	private LocalEndDialog() {};
	private static LocalEndDialog instance;
	public static LocalEndDialog getInstance(){
		if(instance == null) {
			instance = new LocalEndDialog();
			instance.create();
		}
		instance.overText.setText(ChessControl.getInstance().localWinName+"胜利,游戏结束！");
		instance.setLocationRelativeTo(ChessControl.getInstance());
		return instance;
	}
	
	public void create() {
		setLayout(new BorderLayout());
		
		JButton exitB = new JButton("返回初界面");
		exitB.setPreferredSize(new Dimension(100, 30));
		exitB.setBackground(new Color(222,156,83));
		JButton restartB = new JButton("重新开始");
		restartB.setPreferredSize(new Dimension(100, 30));
		restartB.setBackground(new Color(222,156,83));
		JButton reshow = new JButton("复盘");
		reshow.setPreferredSize(new Dimension(100, 30));
		reshow.setBackground(new Color(222,156,83));
		
		//监听器
			//frame2销毁，frame1出现
			exitB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ChessModel.getInstance().reset();
					ChessControl.getInstance().dispose();
					getInstance().dispose();
					StartFrame.getInstance().setLocationRelativeTo(ChessControl.getInstance());
					StartFrame.getInstance().show();
				}
			});
			//重新开始
		 	restartB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ChessModel.getInstance().reset();
					getInstance().dispose();
				}
			});
		 	
		 	//复盘
		 	reshow.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					while(!ChessModel.getInstance().datalist.isEmpty()) {
						Message tmp = new Message(0, 0, 0);
						tmp = ChessModel.getInstance().datalist.poll();
						ChessModel.getInstance().reshowlist.push(tmp);
					}
					ChessModel.getInstance().reset();
					getInstance().dispose();
				}
			});
		
		//布局
		JPanel southd = new JPanel();
		southd.setLayout(new GridLayout(1,5));
		southd.setPreferredSize(new Dimension(500, 30));
		southd.setBackground(new Color(222,211,140));
		JTextArea m1 = new JTextArea();
		JTextArea m2 = new JTextArea();
		m1.setPreferredSize(new Dimension(90,30));
		m1.setEditable(false);
		m1.setBackground(new Color(222,211,140));
		m2.setPreferredSize(new Dimension(120,30));
		m2.setEditable(false);
		m2.setBackground(new Color(222,211,140));
		southd.add(m1);
		southd.add(restartB);
		southd.add(reshow);
		southd.add(exitB);
		southd.add(m2);
		
		overText = new JTextArea(ChessControl.getInstance().localWinName+"胜利,游戏结束！");
		overText.setFont(new Font("幼圆",Font.BOLD,50));
		overText.setPreferredSize(new Dimension(70,30));
		overText.setBackground(new Color(222,211,140));
		overText.setEditable(false);
		
		add(overText,BorderLayout.CENTER);
		add(southd,BorderLayout.SOUTH);

		setVisible(true);
		setTitle("游戏结束");
		setBounds(500, 300, 500, 200);
		setIconImage(new ImageIcon("wuziqi.jpeg").getImage());
		
	}
}
