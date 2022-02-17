package homework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.text.AttributedCharacterIterator;
import java.util.Stack;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class MyFrame extends JFrame {
	static MyPanel southPanel = new MyPanel();
	static JLabel southtext = new JLabel();
	static int x1=0,y1=0,x2=0,y2=0;	
	static void re() {
		southtext.setText("Rleased  "+"x1 = "+x1+"  "+"y1 = "+y1+"  "+"x2 = "+x2+"  "+"y2 = "+y2);
		southtext.setVisible(true);
	}
	
	public static void main(String[] args) {
		MyFrame paintFrame = new MyFrame();
		paintFrame.setLayout(new BorderLayout());
		MyPanel centerPanel = new MyPanel();
		centerPanel.setBackground(new Color(255,255,255));
		centerPanel.setBorder(new LineBorder(new Color(0,0,0), 1));
		MyPanel northPanel = new MyPanel();
		northPanel.setBorder(new LineBorder(new Color(0,0,0), 1));
		MyPanel westPanel = new MyPanel();
		westPanel.setBorder(new LineBorder(new Color(0,0,0), 1));
		JMenuBar menu = new JMenuBar();
		menu.setBackground(new Color(138,247,187));
		menu.setBorder(new LineBorder(new Color(0,0,0), 1));
		JMenu me1 = new JMenu("File");me1.setFont(new Font("宋体",Font.BOLD,17));
		JMenu me2 = new JMenu("Edit");me2.setFont(new Font("宋体",Font.BOLD,17));
		JMenu me3 = new JMenu("Help");me3.setFont(new Font("宋体",Font.BOLD,17));
		menu.add(me1);
		menu.add(me2);
		menu.add(me3);

		Color[] bcolors = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.BLACK,Color.WHITE};
		Color[] scolors = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.BLACK,Color.WHITE};
		Integer[] sizes = new Integer[91];
		for (int i = 0; i < sizes.length; i++) {
			sizes[i]=i+10;
		}
		JComboBox<Color> boxground = new JComboBox<>(bcolors);
		JComboBox<Color> shapecolor = new JComboBox<>(scolors);
		JComboBox<Integer> size = new JComboBox<>(sizes);
		MyComboBoxRender render = new MyComboBoxRender();
		boxground.setRenderer(render);
		shapecolor.setRenderer(render);
		
		

		JTextArea[] margin = new JTextArea[5];
		for (int i = 0; i < 5; i++) {
			margin[i] = new JTextArea();
			margin[i].setBackground(new Color(170,247,187));
			margin[i].setEditable(false);
			
		}
		JTextArea text = new JTextArea("");	
		text.setBorder(new LineBorder(new Color(0,0,0), 1));
		text.setFont(new Font("Calibri Light",Font.BOLD,25));
		JTextArea boxt = new JTextArea("Background color");
		JTextArea shapet = new JTextArea("Shape color");
		JTextArea textt = new JTextArea(" White Down Text");
		JTextArea sizet = new JTextArea(" Choose TextSize");
		JTextArea leftt = new JTextArea("  Choose"+"\n"+"  Shapes");
		leftt.setEditable(false);
		leftt.setBorder(new LineBorder(new Color(215,240,240), 2));
		textt.setEditable(false);
		textt.setBorder(new LineBorder(new Color(0,0,0), 1));
		shapet.setEditable(false);
		shapet.setBorder(new LineBorder(new Color(0,0,0), 1));
		boxt.setEditable(false);
		boxt.setBorder(new LineBorder(new Color(0,0,0), 1));
		sizet.setEditable(false);
		sizet.setBorder(new LineBorder(new Color(0,0,0), 1));
		leftt.setBackground(new Color(170,247,187));
		leftt.setFont(new Font("Calibri Light",Font.BOLD,20));
		textt.setBackground(new Color(170,247,187));
		textt.setFont(new Font("Calibri Light",Font.BOLD,20));
		shapet.setBackground(new Color(170,247,187));
		shapet.setFont(new Font("Calibri Light",Font.BOLD,20));
		boxt.setBackground(new Color(170,247,187));
		boxt.setFont(new Font("Calibri Light",Font.BOLD,20));
		sizet.setBackground(new Color(170,247,187));
		sizet.setFont(new Font("Calibri Light",Font.BOLD,20));
		
		JCheckBox check = new JCheckBox();
		check.setBackground(new Color(170,247,187));
		check.setText("Fill the region");
		check.setFont(new Font("Calibri Light",Font.BOLD,20));
		
		northPanel.setPreferredSize(new Dimension(1000,70));
		northPanel.setBackground(new Color(215,240,240));
		northPanel.setLayout(new GridLayout(2,6,0,0));
		northPanel.add(shapet);
		northPanel.add(shapecolor);
		northPanel.add(margin[0]);
		northPanel.add(textt);
		northPanel.add(text);
		northPanel.add(margin[1]);
		northPanel.add(boxt);
		northPanel.add(boxground);
		northPanel.add(margin[2]);
		northPanel.add(sizet);
		northPanel.add(size);
		northPanel.add(check);
		
		southPanel.setBackground(new Color(170,247,187));
		southPanel.setPreferredSize(new Dimension(1000,40));
		southPanel.setLayout(new BorderLayout());
		southPanel.add(southtext,BorderLayout.WEST);
		southPanel.setBorder(new LineBorder(new Color(0,0,0), 2));
		
		westPanel.setBackground(new Color(210,230,230));
		westPanel.setPreferredSize(new Dimension(90,700));
		westPanel.setLayout(new GridLayout(8,1,3,3));
		westPanel.add(leftt);
		
		check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBox ch = (JCheckBox) e.getSource();
				if(ch.isSelected()) {
					centerPanel.select = true;
				}else {
					centerPanel.select = false;
				}
			}
		});
		
		boxground.setPreferredSize(new Dimension(50,30));
		boxground.setBackground(new Color(255,255,255));
		boxground.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<Color> b = (JComboBox<Color>) e.getSource();
				Color c = (Color) b.getSelectedItem();
				centerPanel.setBackground(c);
				}
		});
		
		shapecolor.setBackground(new Color(255,255,255));
		shapecolor.setPreferredSize(new Dimension(50,30));
		shapecolor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<Color> b = (JComboBox<Color>) e.getSource();
				Color c = (Color) b.getSelectedItem();
				centerPanel.color = c; 
			}
		});
		
		size.setBackground(new Color(255,255,255));
		size.setPreferredSize(new Dimension(50,30));
		size.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<Integer> n = (JComboBox<Integer>) e.getSource();
				Integer m = (Integer)n.getSelectedItem();
				centerPanel.tsize = m;
			}
		});
		
		ImageIcon[] imageshape = new ImageIcon[12];
		imageshape[0] = new ImageIcon("rac.png");imageshape[6] = new ImageIcon("racd.png");
		imageshape[1] = new ImageIcon("oval.png");imageshape[7] = new ImageIcon("ovald.png");
		imageshape[2] = new ImageIcon("cir.png");imageshape[8] = new ImageIcon("cird.png");
		imageshape[3] = new ImageIcon("line.png");imageshape[9] = new ImageIcon("lined.png");
		imageshape[4] = new ImageIcon("text.png");imageshape[10] = new ImageIcon("textd.png");
		imageshape[5] = new ImageIcon("clear.png");imageshape[11] = new ImageIcon("cleard.png");
		JButton[] b = new JButton[6];
		
		for (int i = 0; i < b.length; i++) {
			Integer n = i+1;
			b[i]= new JButton();
			b[i].setText(n.toString());
			b[i].setFont(new Font("宋体",Font.BOLD,0));
			b[i].setBorder(BorderFactory.createRaisedBevelBorder());
			b[i].setBorder(BorderFactory.createRaisedBevelBorder());
			b[i].setBackground(new Color(255,255,255));
			b[i].setIcon(imageshape[i]);
			b[i].setPreferredSize(new Dimension(20,20));
			b[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String bb = e.getActionCommand();
					if(bb.equals("1")) {
						b[0].setBorder(BorderFactory.createLoweredBevelBorder());
						b[0].setIcon(imageshape[6]);
						for (int j = 0; j < b.length; j++) {
							if(j != 0) {
								b[j].setBorder(BorderFactory.createRaisedBevelBorder());
								b[j].setIcon(imageshape[j]);
							}
						}
					}else if(bb.equals("2")) {
						b[1].setBorder(BorderFactory.createLoweredBevelBorder());
						b[1].setIcon(imageshape[7]);
						for (int j = 0; j < b.length; j++) {
							if(j != 1) {
								b[j].setBorder(BorderFactory.createRaisedBevelBorder());
								b[j].setIcon(imageshape[j]);
							}
						}
					}else if(bb.equals("3")) {
						b[2].setBorder(BorderFactory.createLoweredBevelBorder());
						b[2].setIcon(imageshape[8]);
						for (int j = 0; j < b.length; j++) {
							if(j != 2) {
								b[j].setBorder(BorderFactory.createRaisedBevelBorder());
								b[j].setIcon(imageshape[j]);
							}
						}
					}else if(bb.equals("4")) {
						b[3].setBorder(BorderFactory.createLoweredBevelBorder());
						b[3].setIcon(imageshape[9]);
						for (int j = 0; j < b.length; j++) {
							if(j != 3) {
								b[j].setBorder(BorderFactory.createRaisedBevelBorder());
								b[j].setIcon(imageshape[j]);
							}
						}
					}else if(bb.equals("5")) {
						b[4].setBorder(BorderFactory.createLoweredBevelBorder());
						b[4].setIcon(imageshape[10]);
						for (int j = 0; j < b.length; j++) {
							if(j != 4) {
								b[j].setBorder(BorderFactory.createRaisedBevelBorder());
								b[j].setIcon(imageshape[j]);
							}
						}
					}else if(bb.equals("6")) {
						b[5].setBorder(BorderFactory.createLoweredBevelBorder());
						b[5].setIcon(imageshape[11]);
						for (int j = 0; j < b.length; j++) {
							if(j != 5) {
								b[j].setBorder(BorderFactory.createRaisedBevelBorder());
								b[j].setIcon(imageshape[j]);
							}
						}
					}
					String str = e.getActionCommand();
					String tmp =  text.getText();
					centerPanel.s = str;
					centerPanel.strt = tmp;
					if(str.equals("6")) {
						centerPanel.clear();
						centerPanel.repaint();
					}
				}	
			});
			westPanel.add(b[i]);
		}
		westPanel.add(margin[4]);
				
		paintFrame.add(centerPanel,BorderLayout.CENTER);
		paintFrame.add(northPanel,BorderLayout.NORTH);
		paintFrame.add(westPanel,BorderLayout.WEST);
		paintFrame.add(southPanel,BorderLayout.SOUTH);
		
		paintFrame.setBackground(new Color(255,255,250));
		paintFrame.setBounds(250, 100, 1000, 700);
		paintFrame.setJMenuBar(menu);
		paintFrame.setIconImage(new ImageIcon("painticon.jpeg").getImage());
		paintFrame.setTitle("MyPaint Tool");
		paintFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		paintFrame.setVisible(true);
	}

	

}
