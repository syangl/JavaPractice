package testwork;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Client extends JFrame{
	private static BufferedReader in;
	private static BufferedWriter out;
	static boolean issend = false;
	public static void main(String[] args) {
		JFrame sf = new JFrame();
		sf.setLayout(new BorderLayout());
		JPanel north = new JPanel();
		JPanel center = new JPanel();
		JPanel south= new JPanel();

		center.setLayout(new BorderLayout());
		JPanel centersouth = new JPanel();
		centersouth.setPreferredSize(new Dimension(600, 50));
		centersouth.setBackground(new Color(180, 255, 255));
		center.add(centersouth,BorderLayout.SOUTH);
		north.setLayout(new FlowLayout());
		north.setPreferredSize(new Dimension(600,50));
		south.setLayout(new BorderLayout());
		south.setPreferredSize(new Dimension(600,300));
		center.setPreferredSize(new Dimension(600,300));
		north.setBackground(new Color(180, 255, 255));
		south.setBackground(new Color(255,255,255));
		center.setBackground(new Color(255, 255,255));

		final JTextArea message = new JTextArea();
		message.setBackground(new Color(238, 255, 255));
		message.setFont(new Font("黑体", Font.BOLD, 15));
		message.setPreferredSize(new Dimension(600,50));
		center.add(message);
		
		final JTextArea show = new JTextArea();
		show.setEditable(false);
		show.setBackground(new Color(238, 255, 255));
		show.setFont(new Font("黑体", Font.BOLD, 15));
		show.setPreferredSize(new Dimension(600,50));
		south.add(show);
		
		
		JTextArea t1 = new JTextArea("请输入服务器地址：");
		t1.setFont(new Font("黑体", Font.BOLD, 15));
		t1.setEditable(false);
		t1.setBackground(new Color(180, 255, 255));
		t1.setPreferredSize(new Dimension(140, 25));
		north.add(t1);
		final JTextArea t2 = new JTextArea();
		t2.setPreferredSize(new Dimension(80, 25));
		north.add(t2);
		
		final JTextArea t6 = new JTextArea("端口号:");
		t6.setFont(new Font("黑体", Font.BOLD, 15));
		t6.setEditable(false);
		t6.setBackground(new Color(180, 255, 255));
		t6.setPreferredSize(new Dimension(60, 25));
		north.add(t6);
		final JTextArea t7 = new JTextArea();
		t7.setPreferredSize(new Dimension(60, 25));
		north.add(t7);
		
		
		JTextArea t3 = new JTextArea("需要发送的信息");
		t3.setFont(new Font("黑体", Font.BOLD, 15));
		t3.setBackground(new Color(180, 255, 255));
		t3.setEditable(false);
		center.add(t3,BorderLayout.NORTH);

		final JTextArea t5 = new JTextArea("请输入服务器地址及端口号以连接");
		t5.setFont(new Font("黑体", Font.BOLD, 15));
		t5.setBackground(new Color(180, 255, 255));
		t5.setEditable(false);
		south.add(t5,BorderLayout.SOUTH);
		
		final JButton sentTo = new JButton("发送");
		sentTo.setFont(new Font("黑体", Font.BOLD, 15));
		sentTo.setBackground(new Color(222, 205, 255));
		centersouth.add(sentTo);
		String[] cstr = {""};

		JButton link = new JButton("连接");
		link.setFont(new Font("黑体", Font.BOLD, 15));
		link.setPreferredSize(new Dimension(70, 25));
		link.setBackground(new Color(222, 205, 255));
		north.add(link);
		link.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					public void run() {
						sentTo.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
									String m = message.getText();
									try {
										out.write(m+'\n');
										out.flush();
										show.append("向服务器发送: "+m+"\n");
										show.append("成功\n");
									} catch (IOException e1) {
										e1.printStackTrace();
									}
							}
						});
						try {
							String textport = t7.getText();
							String textip = t2.getText();
							Integer port = new Integer(textport);
							Socket c = new Socket(textip,port);
							t5.setText("连接服务器"+textip+":"+port+"成功！");
							show.append("连接服务器"+textip+":"+port+"成功！\n");
							in = new BufferedReader(new InputStreamReader(c.getInputStream()));
							out = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));
							while(true) {
								String line = in.readLine();
								show.append("接收到服务器消息: "+line+"\n");
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					};
				}.start();
			}
		});
		
		JTextArea t4 = new JTextArea("日志");
		t4.setFont(new Font("黑体", Font.BOLD, 15));
		t4.setBackground(new Color(180, 255, 255));
		t4.setEditable(false);
		south.add(t4,BorderLayout.NORTH);
		
		sf.add(north,BorderLayout.NORTH);
		sf.add(south,BorderLayout.SOUTH);
		sf.add(center,BorderLayout.CENTER);
		
		sf.setBounds(300,100,750,800);
		sf.setDefaultCloseOperation(EXIT_ON_CLOSE);
		sf.setVisible(true);
		sf.setTitle("Client");
	}
}
