package visualizationofdsa;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class VerifyPanel extends JPanel {
	
	private JButton btnMsg;
	private JButton btnS;
	private JButton btnR;
	private JButton btnH;
	private JButton btnF3;
	private JButton btnF4;
	private JButton compare;
	
	private Dimension dim = new Dimension(450, 300);
    //public final ArrayList<Shape> shapes;
    
	public VerifyPanel() {
		setLayout(null);
		btnMsg = new JButton("Msg'");
		btnMsg.setBounds(10, 10, 100, 190);
		add(btnMsg);
		
		btnS = new JButton("s'");
		btnS.setBounds(10, 200, 100, 25);
		add(btnS);
		
		btnR = new JButton("r'");
		btnR.setBounds(10, 225, 100, 25);
		add(btnR);
		
		btnH = new JButton("H");
		btnH.setBounds(210, 10, 80, 80);
		add(btnH);
		
		btnF3 = new JButton("f3");
		btnF3.setBounds(210, 140, 80, 80);
		add(btnF3);
		
		btnF4 = new JButton("f4");
		btnF4.setBounds(400, 75, 80, 80);
		add(btnF4);
		
		compare = new JButton("Compare");
		compare.setBounds(470, 225, 100, 25);
		add(compare);
    }
	
	@Override
	protected void paintComponent(Graphics grphcs) {
		super.paintComponent(grphcs);
		grphcs.drawLine(110, 30, 210, 30);// msg to hash
		grphcs.drawLine(210, 30, 205, 25);
		grphcs.drawLine(210, 30, 205, 35);

		grphcs.drawLine(290, 45, 440, 45);// hash to f4
		grphcs.drawLine(440, 45, 440, 75);
		grphcs.drawLine(440, 75, 435, 70);
		grphcs.drawLine(440, 75, 445, 70);
		
		grphcs.drawLine(110, 213, 210, 213);//s' to f3
		grphcs.drawLine(210, 213, 205, 208);
		grphcs.drawLine(210, 213, 205, 218);

		grphcs.drawLine(290, 185, 440, 185);// f3 to f4
		grphcs.drawLine(440, 185, 440, 155);
		grphcs.drawLine(440, 155, 435, 160);
		grphcs.drawLine(440, 155, 445, 160);
		
		grphcs.drawLine(110, 237, 470, 237);//r to compare
		grphcs.drawLine(465, 242, 470, 237);
		grphcs.drawLine(465, 232, 470, 237);
		
		grphcs.drawLine(525, 115, 525, 225);//emp to cmp
		grphcs.drawLine(520, 220, 525, 225);
		grphcs.drawLine(530, 220, 525, 225);
		grphcs.drawLine(480, 115, 525, 115);//f4 to emp
	}

}
