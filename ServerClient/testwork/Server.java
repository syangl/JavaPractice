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

public class Server extends JFrame{
	static boolean close = false;
	static int iclient = 0;
	static int idex = 0;
	static BufferedReader[] sin = new BufferedReader[1000];
	static BufferedWriter[] sout = new BufferedWriter[1000]; 
	static ServerSocket ss;

	public static void main(String[] args) {
		JFrame sf = new JFrame();
		sf.setLayout(new BorderLayout());
		JPanel north = new JPanel();
		JPanel center = new JPanel();
		JPanel south= new JPanel();

		center.setLayout(new BorderLayout());
		final JPanel centersouth = new JPanel();
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

		JTextArea t1 = new JTextArea("请输入服务端口号:");
		t1.setFont(new Font("黑体", Font.BOLD, 15));
		t1.setEditable(false);
		t1.setBackground(new Color(180, 255, 255));
		t1.setPreferredSize(new Dimension(140, 25));
		final JTextArea t2 = new JTextArea();
		t2.setPreferredSize(new Dimension(80, 25));
		north.add(t1);
		north.add(t2);

		final JButton sentTo = new JButton("发送至");
		sentTo.setFont(new Font("黑体", Font.BOLD, 15));
		sentTo.setBackground(new Color(222, 205, 255));
		centersouth.add(sentTo);

		final JTextArea t5 = new JTextArea("输入端口号启动服务");
		t5.setBackground(new Color(180, 255, 255));
		t5.setFont(new Font("黑体", Font.BOLD, 15));
		t5.setEditable(false);
		south.add(t5,BorderLayout.SOUTH);
		
		final JComboBox<String> boxClients = new JComboBox<>();
		boxClients.setPreferredSize(new Dimension(100,28));
		boxClients.setBackground(new Color(222, 205, 255));
		centersouth.add(boxClients);
	
		final JButton bstart = new JButton("start");
		bstart.setBackground(new Color(222, 205, 255));
		bstart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String b = e.getActionCommand();
				if(b.equals("start")) {
					close = false;
					bstart.setText("stop");
					new Thread() {
						public void run() {
							boxClients.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									JComboBox<String > b = (JComboBox<String>) e.getSource();
									Integer a = new Integer(b.getSelectedIndex()+1);
									idex = a;
								}
							});
							sentTo.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
										String m = message.getText();
										try {
											sout[idex].write(m+'\n');
											sout[idex].flush();
											show.append("向客户端"+idex+"发送: "+m+"\n");
											show.append("成功\n");
										} catch (IOException e1) {
											e1.printStackTrace();
										}
									
								}
							});
							try {
								String text = t2.getText();
								Integer port = new Integer(text);
								ss = new ServerSocket(port);
								show.append("服务器已启动，端口号："+text+'\n');
								t5.setText("启动服务，端口号为"+text);
								while(!close) {
									Socket s = ss.accept();
									new ServerThread(s) {
										public void run() {
											try {
												int cidex = 0;
												iclient++;
												cidex = iclient;
												show.append("接受客户端"+cidex+"的请求"+"\n");
												String cstr = new String("Client"+cidex);
												boxClients.addItem(cstr);

												sin[cidex] = new BufferedReader(new InputStreamReader(s.getInputStream()));
												sout[cidex] = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
												while(!close) {
													String line;
													line = sin[cidex].readLine();
													show.append("接收到客户端: "+cidex+": "+line+"\n");
												}
											} catch (IOException e) {
												e.printStackTrace();
											}
										};
									}.start();
								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}.start();
				}else if(b.equals("stop")) {//
					bstart.setText("start");
					close = true;
					try {
						ss.close();
						show.append("服务器已关闭，连接已断开！\n");
						t5.setText("服务器已关闭，连接已断开！");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		north.add(bstart);

		JTextArea t3 = new JTextArea("需要发送的内容");
		t3.setFont(new Font("黑体", Font.BOLD, 15));
		t3.setBackground(new Color(180, 255, 255));
		t3.setEditable(false);
		center.add(t3,BorderLayout.NORTH);
		
		
		
		JTextArea t4 = new JTextArea("日志");
		t4.setBackground(new Color(180, 255, 255));
		t4.setFont(new Font("黑体", Font.BOLD, 15));
		t4.setEditable(false);
		south.add(t4,BorderLayout.NORTH);
		
		
		
		sf.add(north,BorderLayout.NORTH);
		sf.add(south,BorderLayout.SOUTH);
		sf.add(center,BorderLayout.CENTER);
		
		sf.setBounds(300,100,750,800);
		sf.setDefaultCloseOperation(EXIT_ON_CLOSE);
		sf.setVisible(true);
		sf.setTitle("Server");
	}
}
