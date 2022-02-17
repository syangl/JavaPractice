package Chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.print.attribute.standard.MediaPrintableArea;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class StartFrame extends JFrame{
	//单例模式
	private StartFrame() {};
	private static StartFrame instance;
	public static StartFrame getInstance(){
		if(instance == null) {
			instance = new StartFrame();
			instance.create();
		}
		return instance;
	}
	
	public void create(){
		setBackground(getBackground());
		setLayout(new BorderLayout());
		
		//加面板
		setContentPane(StartPanel.getInstance());
		
		//startframe
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(50,20,1274, 830);
		setVisible(true);
		setIconImage(new ImageIcon("wuziqi.jpeg").getImage());
		setTitle("五子棋对战");		
	}
	
	
	public static void main(String[] args){
		//开始界面
		StartFrame.getInstance();
	}

	public void jump() {
		//窗口切换
		this.dispose();
		ChessControl.getInstance().show();
	}
}
