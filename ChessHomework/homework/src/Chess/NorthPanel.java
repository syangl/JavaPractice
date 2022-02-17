package Chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class NorthPanel extends JPanel{
	static JTextArea linkBar;
	//单例模式
	private NorthPanel() {};
	private static NorthPanel instance;
	public static NorthPanel getInstance(){
		if(instance == null) {
			instance = new NorthPanel();
			instance.create();
		}
		return instance;
	}

	public void create() {
		setLayout(new FlowLayout());
		
		//布局
		JTextArea welcome = new JTextArea("欢迎来到五子棋游戏对局！");
		welcome.setPreferredSize(new Dimension(250,30));
		welcome.setBackground(new Color(203, 148, 3));
		welcome.setFont(new Font("幼圆",Font.BOLD,20));
		welcome.setEditable(false);
		
		linkBar = new JTextArea("联网模式请按'连接'开始游戏,或开始本地游戏");
		linkBar.setPreferredSize(new Dimension(400,30));
		linkBar.setBackground(new Color(222,211,140));
		linkBar.setFont(new Font("幼圆",Font.BOLD,17));
		linkBar.setVisible(true);
		linkBar.setEditable(false);
		
		JButton linkB = new JButton("连接");
		linkB.setPreferredSize(new Dimension(80, 30));
		linkB.setBackground(new Color(203, 148, 3));
		linkB.setFont(new Font("幼圆",Font.BOLD,18));
		
		JButton withdraw = new JButton("悔棋");
		withdraw.setPreferredSize(new Dimension(80,30));
		withdraw.setFont(new Font("幼圆",Font.BOLD,18));
		withdraw.setBackground(new Color(203, 148, 3));
		
		JButton surrender = new JButton("认输");
		surrender.setPreferredSize(new Dimension(80,30));
		surrender.setFont(new Font("幼圆",Font.BOLD,18));
		surrender.setBackground(new Color(203, 148, 3));
		
		JButton reshow = new JButton("复盘");
		reshow.setPreferredSize(new Dimension(80, 30));
		reshow.setBackground(new Color(203, 148, 3));
		reshow.setFont(new Font("幼圆",Font.BOLD,18));
		
		JButton returnB = new JButton("返回");
		returnB.setPreferredSize(new Dimension(80, 30));
		returnB.setBackground(new Color(203, 148, 3));
		returnB.setFont(new Font("幼圆",Font.BOLD,18));
		
		add(welcome);
		add(linkBar);
		add(linkB);
		add(withdraw);
		add(surrender);
		add(reshow);
		add(returnB);

		
		//按钮监听器
		//返回主界面
		returnB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChessModel.getInstance().reset();
				StartFrame.getInstance().setLocationRelativeTo(ChessControl.getInstance());
				StartFrame.getInstance().show();
				ChessControl.getInstance().dispose();
			}
		});
		
		
		withdraw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//联机发送请求，本地直接悔棋
				if(StartPanel.whetherlocal == true) {
					//本地
					ChessModel.getInstance().Withdraw();
				}else if(StartPanel.whetherlocal == false){
					//联网
					PlayerSocket.getInstance().withdraw();
				}else {
				}
			}
		});
		
		//认输
		surrender.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(StartPanel.whetherlocal == false){
					PlayerSocket.getInstance().surrender();
				}else {
				}
			}
		});
		
		
		reshow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChessModel.getInstance().reshow();
			}
		});
		
		
		linkB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 连接服务器
				if(!StartPanel.getInstance().whetherlocal) {
					PlayerSocket.getInstance();
				}
			}
		});
		
		
		setBounds(0, 0, 1600, 140);
		setBackground(new Color(255,243,140));
		setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//画背景图
		super.paintComponent(g);
		g.drawImage(new ImageIcon("northback.jpeg").getImage(), 0, 0, 1600, 140, new ImageObserver() {
			@Override
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				return false;
			}
		});
	}

}
