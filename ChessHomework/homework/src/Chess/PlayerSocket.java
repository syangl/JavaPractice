package Chess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class PlayerSocket {
	private static Socket player;
	private static BufferedReader pin;
	private static BufferedWriter pout;
	static boolean WATCHER = false;
	//单例模式
	private PlayerSocket() {};
	private static PlayerSocket instance;
	public static PlayerSocket getInstance(){
		if(instance == null) {
			instance = new PlayerSocket();
			instance.create();
		}
		return instance;
	}
	//单例构造socket
	private void create() {
		try {
			player = new Socket("localhost",8000);
			EastPanel.getInstance().record.append("已连接游戏服务器！\n");
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			pin = new BufferedReader(new InputStreamReader(player.getInputStream()));
			pout = new BufferedWriter(new OutputStreamWriter(player.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		//接收消息
		new Thread() {
			public void run() {
				while(true) {
					try {
						String line = new String();
						line = pin.readLine();
						//识别信息类别
						if(line.charAt(0) != '#'&&line.charAt(0) != '!'&&line.charAt(0) != '~'
								&&line.charAt(0)!='^'&&line.charAt(0)!='%'&&line.charAt(0)!='|'
								&&line.charAt(0)!='@'&&line.charAt(0)!='?'&&line.charAt(0)!='$') {//是聊天信息
							EastPanel.getInstance().record.append(line+'\n');
						}else if(line.charAt(0) == '#'){//可以下棋
							String[] array = line.split(",");
							Integer Row = new Integer(array[3]);
							Integer Col = new Integer(array[4]);
							int color = 0;
							if(array[1].equals("bb")) {
								color = ChessModel.getInstance().getBlack();
							}else if(array[1].equals("ww")) {
								color = ChessModel.getInstance().getWhite();
							}
							ChessControl.getInstance().webUserPutChess(Row, Col, color);
						}else if(line.charAt(0) == '~') {//悔棋请求
							WithdrawDialog.getInstance().show();
						}else if(line.charAt(0) == '|') {//可以悔棋
							WithdrawAgreeDialog.getInstance().show();
						}else if(line.charAt(0) == '^') {//不可以悔棋
							WithdrawDisagreeDialog.getInstance().show();
						}else if(line.charAt(0) == '?') {//认输请求
							SurrenderDialog.getInstance().show();
						}else if(line.charAt(0) == '@') {//可以认输
							if(line.charAt(1)=='w') {
								ChessControl.getInstance().webWinName = "白色";
							}else if(line.charAt(1)=='b'){
								ChessControl.getInstance().webWinName = "黑色";
							}
							SurrenderAgreeDialog.getInstance().show();
						}else if(line.charAt(0) == '!') {//不可以认输
							SurrenderDisagreeDialog.getInstance().show();
						}else if(line.charAt(0) == '%') {//同步对方下的棋
							String[] array = line.split(",");
							Integer Row = new Integer(array[3]);
							Integer Col = new Integer(array[4]);
							int color = 0;
							if(array[1].equals("bb")) {
								color = ChessModel.getInstance().getBlack();
							}else if(array[1].equals("ww")) {
								color = ChessModel.getInstance().getWhite();
							}
							ChessControl.getInstance().webUserPutChess(Row, Col, color);
						}else if(line.charAt(0) == '$') {
							WatchModeDialog.getInstance().show();
							EastPanel.getInstance().record.append("你正在旁观对局！\n");
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
		
	}
	
	//发送消息
	public void sendMessage() {
		new Thread() {
			public void run() {
				String sendM = new String(EastPanel.getInstance().message.getText());
				try {
					pout.write(':'+sendM+'\n');//规定:为聊天信息
					pout.flush();
					EastPanel.getInstance().record.append("我的消息："+'\n');
					EastPanel.getInstance().record.append(sendM+'\n');
					EastPanel.getInstance().message.setText("");
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	//发送下棋请求
	public void sendChess(int row, int col){
		new Thread() {
			public void run() {
				Integer r = new Integer(row);
				Integer c = new Integer(col);
				String chessM = new String(r.toString()+","+c.toString()+",");
				try {
					pout.write('#'+","+chessM+'\n');//规定#为下棋请求
					pout.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	public void withdraw(){
		new Thread() {
			public void run() {
				try {
					String tmp = new String("tmp");
					pout.write('~'+tmp+'\n');
					pout.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	public void agreeWithdraw(){
		new Thread() {
			public void run() {
				try {
					String tmp = new String("tmp");
					pout.write('|'+tmp+'\n');
					pout.flush();
					ChessModel.getInstance().Withdraw();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	public void disagreeWithdraw(){
		new Thread() {
			public void run() {
				try {
					String tmp = new String("tmp");
					pout.write('^'+tmp+'\n');
					pout.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	public void surrender() {
		new Thread() {
			public void run() {
				try {
					String tmp = new String("tmp");
					pout.write('?'+tmp+'\n');
					pout.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	public void agreeSurrender() {
		new Thread() {
			public void run() {
				try {
					String tmp = new String("tmp");
					pout.write('@'+tmp+'\n');
					pout.flush();
					ChessControl.getInstance().webWinName = "你已";
					ChessView.getInstance().outputEnd();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	public void disagreeSurrender() {
		new Thread() {
			public void run() {
				try {
					String tmp = new String("tmp");
					pout.write('!'+tmp+'\n');
					pout.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
}

















