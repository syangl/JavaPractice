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
	//����ģʽ
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
		
		//����
		JTextArea welcome = new JTextArea("��ӭ������������Ϸ�Ծ֣�");
		welcome.setPreferredSize(new Dimension(250,30));
		welcome.setBackground(new Color(203, 148, 3));
		welcome.setFont(new Font("��Բ",Font.BOLD,20));
		welcome.setEditable(false);
		
		linkBar = new JTextArea("����ģʽ�밴'����'��ʼ��Ϸ,��ʼ������Ϸ");
		linkBar.setPreferredSize(new Dimension(400,30));
		linkBar.setBackground(new Color(222,211,140));
		linkBar.setFont(new Font("��Բ",Font.BOLD,17));
		linkBar.setVisible(true);
		linkBar.setEditable(false);
		
		JButton linkB = new JButton("����");
		linkB.setPreferredSize(new Dimension(80, 30));
		linkB.setBackground(new Color(203, 148, 3));
		linkB.setFont(new Font("��Բ",Font.BOLD,18));
		
		JButton withdraw = new JButton("����");
		withdraw.setPreferredSize(new Dimension(80,30));
		withdraw.setFont(new Font("��Բ",Font.BOLD,18));
		withdraw.setBackground(new Color(203, 148, 3));
		
		JButton surrender = new JButton("����");
		surrender.setPreferredSize(new Dimension(80,30));
		surrender.setFont(new Font("��Բ",Font.BOLD,18));
		surrender.setBackground(new Color(203, 148, 3));
		
		JButton reshow = new JButton("����");
		reshow.setPreferredSize(new Dimension(80, 30));
		reshow.setBackground(new Color(203, 148, 3));
		reshow.setFont(new Font("��Բ",Font.BOLD,18));
		
		JButton returnB = new JButton("����");
		returnB.setPreferredSize(new Dimension(80, 30));
		returnB.setBackground(new Color(203, 148, 3));
		returnB.setFont(new Font("��Բ",Font.BOLD,18));
		
		add(welcome);
		add(linkBar);
		add(linkB);
		add(withdraw);
		add(surrender);
		add(reshow);
		add(returnB);

		
		//��ť������
		//����������
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
				//�����������󣬱���ֱ�ӻ���
				if(StartPanel.whetherlocal == true) {
					//����
					ChessModel.getInstance().Withdraw();
				}else if(StartPanel.whetherlocal == false){
					//����
					PlayerSocket.getInstance().withdraw();
				}else {
				}
			}
		});
		
		//����
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
				// ���ӷ�����
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
		//������ͼ
		super.paintComponent(g);
		g.drawImage(new ImageIcon("northback.jpeg").getImage(), 0, 0, 1600, 140, new ImageObserver() {
			@Override
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				return false;
			}
		});
	}

}
