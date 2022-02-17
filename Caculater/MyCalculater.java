package homework;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Stack;

public class MyCalculater extends JFrame implements ActionListener{
	String result = new String("");
	String str = new String();
	String pre = new String("start");
	JLabel label = new JLabel("0");
	
	Stack nu = new Stack();
	Stack op = new Stack();
		
	public void calculate(String btn) {
		if(btn == "1"||btn == "2"||btn == "3"
		||btn == "4"||btn == "5"||btn == "6"
		||btn == "7"||btn == "8"||btn == "9"			
		||btn == "0"||btn == "."||btn == "+"||btn == "-"
		||btn == "*"||btn == "/"||btn == "("||btn == ")"||btn == "="){	
			if(btn == "1"||btn == "2"||btn == "3"
			||btn == "4"||btn == "5"||btn == "6"
			||btn == "7"||btn == "8"||btn == "9"			
			||btn == "0"||btn == ".") {
				result+=btn;
				str+=btn;
				label.setText(str);
				pre = btn;
			}
			else if(btn == "+"||btn == "-"||btn == "*"||btn == "/"||btn == "("||btn == ")"||btn == "=") {
				if(btn != "=") {
					str+=btn;
					label.setText(str);
				}
				
				if(pre == "1"||pre == "2"||pre == "3"
						||pre == "4"||pre == "5"||pre == "6"
						||pre == "7"||pre == "8"||pre == "9"			
						||pre == "0"||pre == ".") {
					double num = Double.parseDouble(result);
					nu.push(num);
					result = "";
				}
				pre = btn;
				
				if((btn == "("||op.empty())&&btn != "=") {
					op.push(btn);
				}else if(btn == ")"&&!op.empty()) {
					while (true) {
						
						String tmp = new String();
						tmp = (String)op.pop();
						if(tmp == "(") {
							break;
						}
						double a, b;
						a = (double)nu.pop();
						b = (double)nu.pop();
						if(tmp == "+") {
							double m = a+b;
							nu.push(m);
						}else if(tmp == "-") {
							double m = b-a;
							nu.push(m);
						}else if(tmp == "*") {
							double m = a*b;
							nu.push(m);
						}else if(tmp == "/") {
							double m = b/a;
							nu.push(m);
						}
						
					}
					
				}else if((btn == "+"||btn == "-"||btn == "*"||btn == "/")&&!op.empty()){
					String tmp = new String();
					tmp = (String)op.peek();
					if (btn == "*"||btn == "/") {
						if(tmp == "+"||tmp == "-"||tmp == "(") {
							op.push(btn);
						}else {
							tmp = (String)op.pop();
							double a, b;
							a = (double)nu.pop();
							b = (double)nu.pop();
							if(tmp == "*") {
								double m = a*b;
								nu.push(m);
							}else {
								double m = b/a;
								nu.push(m);
							}
							op.push(btn);
						}
					}else {
						if(tmp == "(") {
							op.push(btn);
						}else {
							tmp = (String)op.pop();
							double a, b;
							a = (double)nu.pop();
							b = (double)nu.pop();
							if(tmp == "+") {
								double m = a+b;
								nu.push(m);
							}else if(tmp == "-") {
								double m = b-a;
								nu.push(m);
							}else if(tmp == "*") {
								double m = a*b;
								nu.push(m);
							}else if(tmp == "/") {
								double m = b/a;
								nu.push(m);
							}
							op.push(btn);
						}
					}
					
				}else if(btn == "="){
					while(!op.empty()) {
						String tmp = new String();
						tmp = (String)op.pop();
						double a, b;
						a = (double)nu.pop();
						b = (double)nu.pop();
						if(tmp == "+") {
							double m = a+b;
							nu.push(m);
						}else if(tmp == "-") {
							double m = b-a;
							nu.push(m);
						}else if(tmp == "*") {
							double m = a*b;
							nu.push(m);
						}else if(tmp == "/") {
							double m = b/a;
							nu.push(m);
						}
					}
					double number = (double)nu.pop();
					Double Number = number;
					str = Number.toString();
					label.setText(str);
					str="";
					result="";
					
				}
				
			}
			
		}else if(btn == "1/x"){			
			double num2 = Double.parseDouble(result);
			Double Num2 = 1/num2;
			label.setText(Num2.toString());
		}else if(btn == "x^2"){			
			double num3 = Double.parseDouble(result);
			Double Num3 = num3*num3;
			label.setText(Num3.toString());
					
		}else if(btn == "x^(1/2)"){			
			double num3 = Double.parseDouble(result);
			Double Num3 = Math.pow(num3, 0.5);
			label.setText(Num3.toString());
					
		}else if(btn == "C"){			
			label.setText("0");
			str = "";
			result = "";
			pre = "start";
			while(!nu.empty()) {
				nu.pop();
			}
			while(!op.empty()) {
				op.pop();
			}
			
		}else if(btn == "Delete"){			
			if(op.empty()||(String)op.peek()=="(") {
				String tmp = new String();
				tmp = result;
				result = "";
				for (int i = 0; i < tmp.length()-1; i++) {
					result+=tmp.charAt(i);
				}
				str = result;
				if(str == "") {
					label.setText("0");
				}else {
					label.setText(str);
				}
			}
		}else if(btn == "+/-"){			
			double num3 = Double.parseDouble(result);
			Double Num3 = -num3;
			label.setText(Num3.toString());
			result = Num3.toString();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String btn = e.getActionCommand();
		calculate(btn);
	}
	
	public MyCalculater(int x, int y, int w, int h, Color color) {
		super("Calculater");

		label.setBackground(new Color(245,255,255));
		label.setFont(new Font(null, Font.PLAIN, 60));
		
		JButton []b = new JButton[11];//grey button
		for (int i = 0; i < 11; i++) {
			b[i] = new JButton();
			b[i].setBackground(new Color(242,242,242));
			b[i].setFont(new Font(null, Font.PLAIN, 25));
		}
		b[1].setText("(");b[2].setText(")");
		b[3].setText("C");b[4].setText("Delete");		
		b[5].setText("1/x");b[6].setText("x^2");
		b[7].setText("x^(1/2)");b[8].setText("/");
		b[9].setText("*");b[10].setText("-");
		b[0].setText("+");
		for (int i = 0; i < 11; i++) {
			b[i].addActionListener(this);
		}
		JButton []bb = new JButton[13];//white button
		for (int i = 1; i < bb.length; i++) {
			bb[i] = new JButton();
			bb[i].setBackground(new Color(255,255,255));
			bb[i].setFont(new Font(null, Font.BOLD, 25));
		}	
		bb[0] = new JButton();
		bb[0].setText("=");
		bb[0].setBackground(new Color(17, 119, 221));
		bb[0].setForeground(new Color(255,255,255));
		bb[0].setFont(new Font(null,Font.PLAIN,35));
		bb[1].setText("7");bb[2].setText("8");bb[3].setText("9");
		bb[4].setText("4");bb[5].setText("5");bb[6].setText("6");
		bb[7].setText("1");bb[8].setText("2");bb[9].setText("3");
		bb[10].setText("+/-");bb[11].setText("0");bb[12].setText(".");
		for (int i = 0; i < 13; i++) {
			bb[i].addActionListener(this);
		}

		
		setLayout(new BorderLayout());//JFrame layout
		
		JPanel panel1 = new JPanel();//panel1
		panel1.setBackground(new Color(245,255,255));
		panel1.setPreferredSize(new Dimension(300, 175));
		panel1.setLayout(new BorderLayout());//p1layout
		
		panel1.add(label, BorderLayout.CENTER);
		add(panel1,BorderLayout.NORTH);
		
		JPanel panel2 = new JPanel();//panel2
		panel2.setBackground(new Color(255,255,255));
		panel2.setLayout(new GridLayout(6, 4, 3, 3));//p2layout
		for (int i = 1; i <= 8; i++) {
			panel2.add(b[i]);
		}
		panel2.add(bb[1]);panel2.add(bb[2]);panel2.add(bb[3]);panel2.add(b[9]);
		panel2.add(bb[4]);panel2.add(bb[5]);panel2.add(bb[6]);panel2.add(b[10]);
		panel2.add(bb[7]);panel2.add(bb[8]);panel2.add(bb[9]);panel2.add(b[0]);
		panel2.add(bb[10]);panel2.add(bb[11]);panel2.add(bb[12]);panel2.add(bb[0]);
		add(panel2,BorderLayout.CENTER);
		

		setBounds(x, y, w, h);
		setBackground(color);
		setVisible(true);
		setJMenuBar(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		MyCalculater frame = new MyCalculater(400, 75, 700, 800, null);
	}

}



