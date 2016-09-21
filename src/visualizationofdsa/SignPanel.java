package visualizationofdsa;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;

public class SignPanel extends JPanel {
	
	public JButton btnMsg;
	public JButton btnH;
	public JButton btnF1;
	public JButton btnF2;

	private Shape rectMsg = new Rectangle2D.Double(10, 10, 100, 200);
	private Shape rectHash = new Rectangle2D.Double(170, 70, 80, 80);
	private Shape rectF1 = new Rectangle2D.Double(350, 70, 80, 80);
	private Shape rectF2 = new Rectangle2D.Double(170, 210, 80, 80);
    private Dimension dim = new Dimension(450, 300);
    public final ArrayList<Shape> shapes;

	public SignPanel() {
        shapes = new ArrayList<>();
        shapes.add(rectMsg);
        shapes.add(rectHash);
        shapes.add(rectF1);
        shapes.add(rectF2);
        setLayout(null);
        btnMsg = new JButton("Msg");
        btnMsg.setBounds(10, 10, 100, 200);
        add(btnMsg);

        btnH = new JButton("H");
        btnH.setBounds(170, 70, 80, 80);
        add(btnH);

        btnF1 = new JButton("f1");
        btnF1.setBounds(350, 70, 80, 80);
        add(btnF1);

        btnF2 = new JButton("f2");
        btnF2.setBounds(170, 210, 80, 80);
        add(btnF2);
        
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawLine(110, 110, 170, 110);//msg to hash
        grphcs.drawLine(170, 110, 165, 105);
        grphcs.drawLine(170, 110, 165, 115);
        grphcs.drawString("Msg", 59, 109);//Msg
        
        grphcs.drawLine(250, 110, 350, 110);//hash to f1
        grphcs.drawLine(350, 110, 345, 105);
        grphcs.drawLine(350, 110, 345, 115);
        grphcs.drawString("H", 209, 109);//Hash
        
        grphcs.drawLine(250, 250, 500, 250);//f2 - r
        grphcs.drawLine(500, 250, 495, 245);
        grphcs.drawLine(500, 250, 495, 255);
        grphcs.drawString("f1", 389, 109);//f1
        grphcs.drawString("r", 505, 255);
        
        grphcs.drawLine(410, 150, 410, 250);//r to f1
        grphcs.drawLine(405, 155, 410, 150);
        grphcs.drawLine(415, 155, 410, 150);
        grphcs.drawString("f2", 209, 249);//f2
        
        grphcs.drawLine(430, 110, 500, 110);//f1 - s
        grphcs.drawLine(500, 110, 495, 115);
        grphcs.drawLine(500, 110, 495, 105);
        grphcs.drawString("s", 505, 115);
        
        grphcs.drawString("p", 110, 245);//p
        grphcs.drawLine(118, 240, 170, 240);
        grphcs.drawLine(170, 240, 165, 235);
        grphcs.drawLine(170, 240, 165, 245);
        
        grphcs.drawString("q", 110, 265);//q
        grphcs.drawLine(118, 260, 170, 260);
        grphcs.drawLine(170, 260, 165, 255);
        grphcs.drawLine(170, 260, 165, 265);
        
        grphcs.drawString("g", 110, 285);//g
        grphcs.drawLine(118, 280, 170, 280);
        grphcs.drawLine(170, 280, 165, 275);
        grphcs.drawLine(170, 280, 165, 285);
        
        grphcs.drawString("x", 380, 20);//x
        grphcs.drawLine(381, 23, 381, 70);
        grphcs.drawLine(376, 65, 381, 70);
        grphcs.drawLine(386, 65, 381, 70);
        
        grphcs.drawString("q", 400, 20);//q
        grphcs.drawLine(401, 23, 401, 70);
        grphcs.drawLine(396, 65, 401, 70);
        grphcs.drawLine(406, 65, 401, 70);

        grphcs.drawString("k", 210, 177);//k
        grphcs.drawLine(211, 180, 211, 210);
        grphcs.drawLine(206, 205, 211, 210);
        grphcs.drawLine(216, 205, 211, 210);
        
        grphcs.drawLine(211, 190, 380, 190);//k to empty
        grphcs.drawLine(380, 190, 380, 150);//empty to f1
        grphcs.drawLine(375, 155, 380, 150);
        grphcs.drawLine(385, 155, 380, 150);
        
        
        //Graphics2D g2d = (Graphics2D) grphcs;
        //for (Shape s : shapes) {
          //  g2d.draw(s);
        //}
    }

    @Override
    public Dimension getPreferredSize() {
        return dim;
    }
}
