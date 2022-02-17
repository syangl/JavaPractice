package Chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.AncestorListener;

public class EastPanel extends JPanel{
	public JTextArea record;
	public JTextArea message;
	//单例模式
	private EastPanel() {};
	private static EastPanel instance;
	public static EastPanel getInstance(){
		if(instance == null) {
			instance = new EastPanel();
			instance.create();
		}
		return instance;
	}
	
	public void create() {
		setLayout(new GridLayout(3,1));
		//布局
		JPanel downP = new JPanel();
		downP.setBounds(0,0,300,300);
		downP.setBackground(new Color(222,211,140));
		JPanel mp = new JPanel();
		mp.setPreferredSize(new Dimension(100, 400));
		mp.setBackground(new Color(210,250,250));
		mp.setOpaque(false);
		JPanel upP = new JPanel();
		upP.setPreferredSize(new Dimension(300, 400));
		upP.setBackground(new Color(210,250,250));
		
		
		upP.setLayout(new BorderLayout());
		JTextArea title1 = new JTextArea("聊天记录");
		title1.setFont(new Font("幼圆", Font.BOLD, 15));
		title1.setBackground(new Color(222,156,83));
		title1.setPreferredSize(new Dimension(100,30));
		title1.setEditable(false);
		
		record = new JTextArea();
		record.setFont(new Font("幼圆", Font.BOLD, 15));
		record.setBackground(new Color(222,211,140));
		record.setPreferredSize(new Dimension(1000,2000));
		record.setEditable(false);
		JScrollPane scrollup = new JScrollPane(record);
		scrollup.setBackground(new Color(222,156,83));
		scrollup.setWheelScrollingEnabled(true);
		upP.add(title1,BorderLayout.NORTH);
		upP.add(scrollup);
		
		downP.setLayout(new BorderLayout());
		JTextArea title2 = new JTextArea("发送消息框");
		title2.setFont(new Font("幼圆", Font.BOLD, 15));
		title2.setBackground(new Color(222,156,83));
		title2.setPreferredSize(new Dimension(100,30));
		title2.setEditable(false);
		message = new JTextArea();
		message.setFont(new Font("幼圆", Font.BOLD, 15));
		message.setBackground(new Color(222,211,140));
		message.setPreferredSize(new Dimension(1000,2000));
		JScrollPane scrolldown = new JScrollPane(message);
		scrolldown.setBackground(new Color(222,156,83));
		scrolldown.setWheelScrollingEnabled(true);
		JButton send = new JButton("发送");
		send.setFont(new Font("幼圆", Font.BOLD, 20));
		send.setBackground(new Color(222,156,83));
		send.setPreferredSize(new Dimension(30,30));
		downP.add(title2,BorderLayout.NORTH);
		downP.add(scrolldown,BorderLayout.CENTER);
		downP.add(send,BorderLayout.SOUTH);
		
		
		//按钮监听器
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//联网时才能向服务器发送
				if(!StartPanel.getInstance().whetherlocal) {
					PlayerSocket.getInstance().sendMessage();
				}
			}
		});
		
		add(upP);
		add(downP);
		add(mp);
		setBounds(930, 140, 510, 750);
		setBackground(new Color(255,250,198));
		setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//画背景图
		super.paintComponent(g);
		g.drawImage(new ImageIcon("back.jpeg").getImage(),0, 0, 600, 1000, new ImageObserver() {
			@Override
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				return false;
			}
		});
	}
	
	
	
}
