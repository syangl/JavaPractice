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

public class WithdrawDisagreeDialog extends JDialog{
	private WithdrawDisagreeDialog() {};
	private static WithdrawDisagreeDialog instance;
	public static WithdrawDisagreeDialog getInstance(){
		if(instance == null) {
			instance = new WithdrawDisagreeDialog();
			instance.create();
		}
		instance.setLocationRelativeTo(ChessControl.getInstance());
		return instance;
	}
	
	public void create() {
		setLayout(new BorderLayout());

		JButton know = new JButton("ȷ��");
		know.setPreferredSize(new Dimension(100, 30));
		know.setBackground(new Color(222,156,83));
	

		//������
		//ȷ�ϣ��رնԻ���
		know.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		//����
		JPanel southd = new JPanel();
		southd.setLayout(new GridLayout(1,3));
		southd.setPreferredSize(new Dimension(500, 30));
		southd.setBackground(new Color(222,211,140));
		JTextArea m1 = new JTextArea();
		JTextArea m2 = new JTextArea();
		JTextArea m3 = new JTextArea();
		m1.setPreferredSize(new Dimension(200,30));
		m1.setEditable(false);
		m1.setBackground(new Color(222,211,140));
		m2.setPreferredSize(new Dimension(200,30));
		m2.setEditable(false);
		m2.setBackground(new Color(222,211,140));
		southd.add(m1);
		southd.add(know);
		southd.add(m2);

		JTextArea overText = new JTextArea("   �Է���ͬ�����");
		overText.setFont(new Font("��Բ",Font.BOLD,50));
		overText.setPreferredSize(new Dimension(70,30));
		overText.setBackground(new Color(222,211,140));
		overText.setEditable(false);

		add(overText,BorderLayout.CENTER);
		add(southd,BorderLayout.SOUTH);

		setVisible(true);
		setTitle("��ͬ�����");
		setBounds(500, 300, 500, 200);
		setIconImage(new ImageIcon("wuziqi.jpeg").getImage());
	}
}
