package homework;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class MyComboBoxRender extends JLabel implements ListCellRenderer<Color>{
	public MyComboBoxRender() {
		setOpaque(true); 
		setPreferredSize(new Dimension(30,30));
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Color> list, Color value, int index,
			boolean isSelected, boolean cellHasFocus) {
		Color c = value;
		setBackground(c);
		
		return this;
	}
	
	
}
