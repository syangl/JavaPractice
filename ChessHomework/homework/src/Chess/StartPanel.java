package Chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class StartPanel extends JPanel{
	//��ť�����Ƿ�������Ӱ��ChessControlִ������ģʽ����Ӧ��������
	public static boolean whetherlocal = true;
	//����ģʽ
	private StartPanel() {};
	private static StartPanel instance;
	public static StartPanel getInstance(){
		if(instance == null) {
			instance = new StartPanel();
			instance.create();
		}
		return instance;
	}

	public void create() {
		setLayout(new BorderLayout());

		//���沼��
		JTextArea marginw = new JTextArea("");
		marginw.setPreferredSize(new Dimension(355,300));
		marginw.setBackground(new Color(253,217,123));
		marginw.setEditable(false);
		JTextArea marginn = new JTextArea("");
		marginn.setPreferredSize(new Dimension(1600,100));
		marginn.setBackground(new Color(253,217,123));
		marginn.setEditable(false);
		JTextArea title = new JTextArea("*�������ս*");
		title.setFont(new Font("��Բ",Font.BOLD,100));
		title.setPreferredSize(new Dimension(1600,300));
		title.setBackground(new Color(253,217,123));
		title.setEditable(false);
		JPanel settext = new JPanel();
		settext.setLayout(new BorderLayout());
		settext.add(marginn,BorderLayout.NORTH);
		settext.add(marginw,BorderLayout.WEST);
		settext.add(title,BorderLayout.CENTER);
		settext.setBackground(new Color(253,217,123));
		getInstance().add(settext,BorderLayout.NORTH);
		
		JPanel inside = new JPanel();
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(500,1000));
		west.setVisible(true);
		west.setBackground(new Color(254,238,197));
		JPanel east = new JPanel();
		east.setPreferredSize(new Dimension(500,1000));
		east.setVisible(true);
		east.setBackground(new Color(254,238,197));
		inside.setLayout(new GridLayout(5,1,10,10));
		inside.setBounds(150,75,800, 1000);
		inside.setBackground(new Color(254,238,197));
		JButton localstart = new JButton("��ʼ������Ϸ");
		localstart.setFont(new Font("����",Font.BOLD,22));
		localstart.setPreferredSize(new Dimension(70,30));
		localstart.setBackground(new Color(203, 148, 3));
		JButton webstart = new JButton("��ʼ������Ϸ");
		webstart.setFont(new Font("����",Font.BOLD,22));
		webstart.setPreferredSize(new Dimension(70,30));
		webstart.setBackground(new Color(203, 148, 3));
		JButton gameexit = new JButton("�˳���Ϸ");
		gameexit.setFont(new Font("����",Font.BOLD,22));
		gameexit.setPreferredSize(new Dimension(70,30));
		gameexit.setBackground(new Color(203, 148, 3));
		inside.add(localstart);
		inside.add(webstart);
		inside.add(gameexit);
		getInstance().add(west,BorderLayout.WEST);
		getInstance().add(east,BorderLayout.EAST);
		getInstance().add(inside,BorderLayout.CENTER);
		//��ť������
		localstart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ���ش����л� 
				whetherlocal = true;
				ChessControl.getInstance().setLocationRelativeTo(getInstance());
				StartFrame.getInstance().jump();
			}
		});
		
		webstart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ���������л�              
				whetherlocal = false;
				ChessControl.getInstance().setLocationRelativeTo(getInstance());
				StartFrame.getInstance().jump();
			}
		});
		
		gameexit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		//StartPanel
		setBounds(70,10,1300, 900);
		setBackground(new Color(255,243,140));
		setVisible(true);
	}
}
