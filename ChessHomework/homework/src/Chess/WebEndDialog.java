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

public class WebEndDialog extends JDialog{
	JTextArea overText;
	//����ģʽ
		private WebEndDialog() {};
		private static WebEndDialog instance;
		public static WebEndDialog getInstance(){
			if(instance == null) {
				instance = new WebEndDialog();
				instance.create();
			}
			instance.setLocationRelativeTo(ChessControl.getInstance());
			instance.overText.setText(ChessControl.getInstance().webWinName+"ʤ��,��Ϸ������");
			return instance;
		}
		
		public void create() {
			setLayout(new BorderLayout());
			
			JButton exitB = new JButton("���س�����");
			exitB.setPreferredSize(new Dimension(100, 30));
			exitB.setBackground(new Color(222,156,83));
			JButton reshow = new JButton("����");
			reshow.setPreferredSize(new Dimension(100, 30));
			reshow.setBackground(new Color(222,156,83));
			JButton restartB = new JButton("���¿�ʼ");
			restartB.setPreferredSize(new Dimension(100, 30));
			restartB.setBackground(new Color(222,156,83));
			
			//������
				//frame2���٣�frame1����
				exitB.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ChessModel.getInstance().reset();
						ChessControl.getInstance().dispose();
						getInstance().dispose();
						StartFrame.getInstance().setLocationRelativeTo(ChessControl.getInstance());
						StartFrame.getInstance().show();
					}
				});

				//���¿�ʼ
			 	restartB.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ChessModel.getInstance().reset();
						getInstance().dispose();
					}
				});

				//����
			 	reshow.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						while(!ChessModel.getInstance().datalist.isEmpty()) {
							Message tmp = new Message(0, 0, 0);
							tmp = ChessModel.getInstance().datalist.poll();
							ChessModel.getInstance().reshowlist.push(tmp);
						}
						//Dialog��reshow��һ����reset������̣�northPanel�ſ�ʼ��һ��reshow��ť����һ��
						ChessModel.getInstance().reset();
						getInstance().dispose();
					}
				});
			
			//����
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
			southd.add(reshow);
			southd.add(restartB);
			southd.add(exitB);
			southd.add(m2);
			
			overText = new JTextArea(ChessControl.getInstance().webWinName+"ʤ��,��Ϸ������");
			overText.setFont(new Font("��Բ",Font.BOLD,50));
			overText.setPreferredSize(new Dimension(70,30));
			overText.setBackground(new Color(222,211,140));
			overText.setEditable(false);
			
			add(overText,BorderLayout.CENTER);
			add(southd,BorderLayout.SOUTH);

			setVisible(true);
			setTitle("��Ϸ����");
			setBounds(500, 300, 500, 200);
			setIconImage(new ImageIcon("wuziqi.jpeg").getImage());
			
		}
}
