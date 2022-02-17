package Chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class WithdrawDialog extends JDialog{
	//单例模式
	private WithdrawDialog() {};
	private static WithdrawDialog instance;
	public static WithdrawDialog getInstance(){
		if(instance == null) {
			instance = new WithdrawDialog();
			instance.create();
		}
		instance.setLocationRelativeTo(ChessControl.getInstance());
		return instance;
	}
	public void create() {
		setLayout(new BorderLayout());

		JButton agree = new JButton("同意");
		agree.setPreferredSize(new Dimension(100, 30));
		agree.setBackground(new Color(222,156,83));
		JButton disagree = new JButton("不同意");
		disagree.setPreferredSize(new Dimension(100, 30));
		disagree.setBackground(new Color(222,156,83));

		//监听器
		//同意
		agree.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerSocket.getInstance().agreeWithdraw();
				dispose();
			}
		});

		//不同意
		disagree.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerSocket.getInstance().disagreeWithdraw();
				dispose();
			}
		});

		//布局
		JPanel southd = new JPanel();
		southd.setLayout(new GridLayout(1,5));
		southd.setPreferredSize(new Dimension(500, 30));
		southd.setBackground(new Color(222,211,140));
		JTextArea m1 = new JTextArea();
		JTextArea m2 = new JTextArea();
		JTextArea m3 = new JTextArea();
		m1.setPreferredSize(new Dimension(90,30));
		m1.setEditable(false);
		m1.setBackground(new Color(222,211,140));
		m2.setPreferredSize(new Dimension(120,30));
		m2.setEditable(false);
		m2.setBackground(new Color(222,211,140));
		m3.setPreferredSize(new Dimension(120,30));
		m3.setEditable(false);
		m3.setBackground(new Color(222,211,140));
		southd.add(m1);
		southd.add(disagree);
		southd.add(m3);
		southd.add(agree);
		southd.add(m2);

		JTextArea overText = new JTextArea("   对方请求悔棋");
		overText.setFont(new Font("幼圆",Font.BOLD,50));
		overText.setPreferredSize(new Dimension(70,30));
		overText.setBackground(new Color(222,211,140));
		overText.setEditable(false);

		add(overText,BorderLayout.CENTER);
		add(southd,BorderLayout.SOUTH);

		setVisible(true);
		setTitle("请求悔棋");
		setBounds(500, 300, 500, 200);
		setIconImage(new ImageIcon("wuziqi.jpeg").getImage());

	}
}
