package Chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class IntermediaryServer extends JFrame{
	private static int count=1;
	private static final int BLACK_PLAYER=1,WHITE_PLAYER=2,WATCHER = 1;
	private static final int PORT = 8000;
	private static ServerSocket server;
	private static BufferedReader[] sin = new BufferedReader[3];
	private static BufferedWriter[] sout = new BufferedWriter[3];
	private static BufferedReader[] win = new BufferedReader[2];
	private static BufferedWriter[] wout = new BufferedWriter[2];
	private static Socket socket;
	private boolean flag = true;
	
	//单例模式
	private IntermediaryServer() {};
	private static IntermediaryServer instance;
	public static IntermediaryServer getInstance(){
		if(instance == null) {
			instance = new IntermediaryServer();
			instance.create();
		}
		return instance;
	}
	private void createFrame(){
		//提示服务器已经加载窗口
				setLayout(new BorderLayout());
				
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(1,4));
				JTextArea tip = new JTextArea("    官方游戏平台服务器配置完毕，已启动！");
				tip.setPreferredSize(new Dimension(500,300));
				tip.setBackground(new Color(222,211,140));
				tip.setEditable(false);
				tip.setFont(new Font("幼圆", Font.BOLD, 25));
				JButton Yes = new JButton("确认");
				Yes.setBackground(new Color(222,156,83));
				Yes.setFont(new Font("幼圆", Font.BOLD, 20));
				JButton shutdown = new JButton("关闭服务器");
				shutdown.setBackground(new Color(222,156,83));
				shutdown.setFont(new Font("幼圆", Font.BOLD, 20));
				JTextArea t1 = new JTextArea();
				JTextArea t3 = new JTextArea();
				t1.setPreferredSize(new Dimension(90,30));
				t1.setEditable(false);
				t1.setBackground(new Color(222,211,140));
				t3.setPreferredSize(new Dimension(120,30));
				t3.setEditable(false);
				t3.setBackground(new Color(222,211,140));
				
				getContentPane().add(tip,BorderLayout.CENTER);
				panel.add(t1);
				panel.add(Yes);
				panel.add(shutdown);
				panel.add(t3);
				add(panel,BorderLayout.SOUTH);
				
				Yes.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setExtendedState(ICONIFIED);
					}
				});

				shutdown.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				
	}
	//单例构造serversocket
	private void create() {
		createFrame();
		try {
			server = new ServerSocket(PORT);
			new Thread() {

				public void run() {
					while(true) {
						try {
							socket = server.accept();
							//接收消息,为玩家和旁观者建立通道
							if(sin[BLACK_PLAYER]==null) {
								sin[BLACK_PLAYER] = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								sout[BLACK_PLAYER] = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
								if(sin[WHITE_PLAYER]==null) {
									sout[BLACK_PLAYER].write("正在寻找对局中...\n");
									sout[BLACK_PLAYER].flush();
								}
							}else if (sin[WHITE_PLAYER]==null) {
								sin[WHITE_PLAYER] = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								sout[WHITE_PLAYER] = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
								if(sin[BLACK_PLAYER]==null) {
									sout[WHITE_PLAYER].write("正在寻找对局中...\n");
									sout[WHITE_PLAYER].flush();
								}
							}else if(win[WATCHER]==null) {
								win[WATCHER] = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								wout[WATCHER] = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
								sout[WHITE_PLAYER].write("有玩家正在观战！\n");
								sout[BLACK_PLAYER].write("有玩家正在观战！\n");
								sout[WHITE_PLAYER].flush();
								sout[BLACK_PLAYER].flush();
								watcher();
							}
							
							if(sin[BLACK_PLAYER]!=null&&sin[WHITE_PLAYER]!=null&&flag == true) {
								flag = false;
								sout[BLACK_PLAYER].write("对局已找到，你是黑方！\n");
								sout[BLACK_PLAYER].flush();
								sout[WHITE_PLAYER].write("对局已找到，你是白方！\n");
								sout[WHITE_PLAYER].flush();
								conversion();
							}
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
			}.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(500,200,600, 300);
		setVisible(true);
		setIconImage(new ImageIcon("server.jpeg").getImage());
		setTitle("五子棋对战");		
		
	}
	
	public void conversion() {
		//中转
		//接收黑，转给白
		new Thread() {
			public void run() {
				while(true) {
					try {
						String line = new String();
						line = sin[BLACK_PLAYER].readLine();
						if(line.charAt(0)==':') {//:是聊天信息
							//转发消息给WHITE_PLAYER
							sout[WHITE_PLAYER].write("黑棋玩家发送消息\n"+line+'\n');
							sout[WHITE_PLAYER].flush();
							if(wout[WATCHER]!=null) {
								wout[WATCHER].write("黑棋玩家发送消息\n"+line+'\n');
								wout[WATCHER].flush();
							}
						}
						else if(line.charAt(0)=='#') {//#为下棋指令消息
							if(count%2!=0) {//奇数黑棋
								sout[BLACK_PLAYER].write('#'+",bb,"+line+'\n');//允许码
								sout[BLACK_PLAYER].flush();
								count++;
								//下棋消息发给WHITE_PLAYER
								sout[WHITE_PLAYER].write("%,bb,"+line+'\n');//%码为同步对方下的棋
								sout[WHITE_PLAYER].flush();
								if(wout[WATCHER]!=null) {
									wout[WATCHER].write("%,bb,"+line+'\n');
									wout[WATCHER].flush();
								}
							}else {
								sout[BLACK_PLAYER].flush();
							}
						}
						else if(line.charAt(0)=='~') {//~为请求悔棋消息
							sout[WHITE_PLAYER].write('~'+line+'\n');
							sout[WHITE_PLAYER].flush();
						}else if(line.charAt(0)=='|') {
							sout[WHITE_PLAYER].write('|'+line+'\n');
							sout[WHITE_PLAYER].flush();
							if(wout[WATCHER]!=null) {
								wout[WATCHER].write("|,bb,"+line+'\n');
								wout[WATCHER].flush();
							}
						}else if(line.charAt(0)=='^') {
							sout[WHITE_PLAYER].write('^'+line+'\n');
							sout[WHITE_PLAYER].flush();
						}else if(line.charAt(0)=='?') {//?为请求认输消息
							sout[WHITE_PLAYER].write('?'+line+'\n');
							sout[WHITE_PLAYER].flush();
						}
						else if(line.charAt(0)=='@') {
							sout[WHITE_PLAYER].write("@b"+line+'\n');
							sout[WHITE_PLAYER].flush();
							if(wout[WATCHER]!=null) {
								wout[WATCHER].write("@b"+line+'\n');
								wout[WATCHER].flush();
							}
						}else if(line.charAt(0)=='!') {
							sout[WHITE_PLAYER].write('!'+line+'\n');
							sout[WHITE_PLAYER].flush();
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
		
		//接收白，转给黑
		new Thread() {
			public void run() {
				while(true) {
					try {
						String line = new String();
						line = sin[WHITE_PLAYER].readLine();
						//转发消息给BLACK_PLAYER
						if(line.charAt(0)==':') {//:是聊天信息
							//转发消息给BLACK_PLAYER
							sout[BLACK_PLAYER].write("白棋玩家发送消息\n"+line+'\n');
							sout[BLACK_PLAYER].flush();
							if(wout[WATCHER]!=null) {
								wout[WATCHER].write("白棋玩家发送消息\n"+line+'\n');
								wout[WATCHER].flush();
							}
						}
						else if(line.charAt(0)=='#') {//#为下棋指令消息
							if(count%2==0) {//偶数白棋
								sout[WHITE_PLAYER].write('#'+",ww,"+line+'\n');//允许码
								sout[WHITE_PLAYER].flush();
								count++;
								//下棋消息发给BLACK_PLAYER
								sout[BLACK_PLAYER].write("%,ww,"+line+'\n');//%码为同步对方下的棋
								sout[BLACK_PLAYER].flush();
								if(wout[WATCHER]!=null) {
									wout[WATCHER].write("%,ww,"+line+'\n');
									wout[WATCHER].flush();
								}
							}else {
								sout[WHITE_PLAYER].flush();
							}
						}
						else if(line.charAt(0)=='~') {//~为请求悔棋消息
							sout[BLACK_PLAYER].write('~'+line+'\n');
							sout[BLACK_PLAYER].flush();
						}else if(line.charAt(0)=='|') {
							sout[BLACK_PLAYER].write('|'+line+'\n');
							sout[BLACK_PLAYER].flush();
							if(wout[WATCHER]!=null) {
								wout[WATCHER].write("|,ww,"+line+'\n');
								wout[WATCHER].flush();
							}
						}else if(line.charAt(0)=='^') {
							sout[BLACK_PLAYER].write('^'+line+'\n');
							sout[BLACK_PLAYER].flush();
						}else if(line.charAt(0)=='?') {//?为请求认输消息
							sout[BLACK_PLAYER].write('?'+line+'\n');
							sout[BLACK_PLAYER].flush();
						}
						else if(line.charAt(0)=='@') {//@为同意认输
							sout[BLACK_PLAYER].write("@w"+line+'\n');
							sout[BLACK_PLAYER].flush();
							if(wout[WATCHER]!=null) {
								wout[WATCHER].write("@w"+line+'\n');
								wout[WATCHER].flush();
							}
						}else if(line.charAt(0)=='!') {//!为不同意认输
							sout[BLACK_PLAYER].write('!'+line+'\n');
							sout[BLACK_PLAYER].flush();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
		
	}
	
	public void watcher() {
		new Thread() {
			public void run() {
				try {
					wout[WATCHER].write("$$"+'\n');
					wout[WATCHER].flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
		
	public static void main(String[] args) {
		//中央服务器是平台指定提供的，用户端不应该修改，和客户端游戏的启动独立
		getInstance();
	}
}



















