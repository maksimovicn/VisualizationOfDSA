package visualizationofdsa;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class SignCanvas extends Canvas {
	
	@Override
	public void paint(Graphics g) {
		//g.drawRect(10, 10, 60, 80);
		g.setColor(Color.WHITE);
		g.fillRect(10, 10, 60, 80);//Message
		g.setColor(Color.BLACK);
		g.drawLine(70, 50, 150, 50);
		g.drawString("M", 40, 50);
		g.drawRect(150, 30, 40, 40);//Hash
		g.drawString("H", 170, 50);
		//g.drawLine(170, 70, 170, 130);
		g.drawRect(150, 130, 40, 40);//f2
		g.drawLine(190, 50, 270, 50);
		g.drawString("f2", 170, 150);
		g.drawRect(270, 30, 40, 40);//f1
		g.drawString("f1", 290, 55);
	}
	
	public void mouseClicked(MouseEvent e)
	{
	    Point point = e.getPoint();
	    Rectangle mess = new Rectangle(10, 10, 60, 80);
	    if (mess.contains(point)) {
	    	System.out.println("Ubo sam ga");
	    } else {
	    	System.out.println("Nisam");
	    }
	}

}
