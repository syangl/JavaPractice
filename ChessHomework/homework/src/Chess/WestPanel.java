package Chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class WestPanel extends JPanel{
	//µ¥ÀýÄ£Ê½
	private WestPanel() {};
	private static WestPanel instance;
	public static WestPanel getInstance(){
		if(instance == null) {
			instance = new WestPanel();
			instance.create();
		}
		return instance;
	}
	
	public void create() {
//		setLayout();
		setBounds(0, 10, 210, 1000);
		setBackground(new Color(255,250,198));
		setVisible(true);
	}	
	
	@Override
	protected void paintComponent(Graphics g) {
		//»­±³¾°Í¼
		super.paintComponent(g);
		g.drawImage(new ImageIcon("back.jpeg").getImage(), 0, 10, 210, 1000, new ImageObserver() {
			@Override
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				return false;
			}
		});
	}
	
}
