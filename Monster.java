
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import java.util.*;

@SuppressWarnings("serial")
public class Monster extends JPanel{
	private String sequence;

	
	public Monster(String s) {
		sequence = s;
		setMaximumSize(new Dimension(100, 100));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		drawLegsAndArms(sequence, g);
		drawTorso(sequence, g);
		drawEars(sequence, g);
		drawHead(sequence, g);
		drawEyes(sequence, g);
		drawMouth(sequence, g);
		drawFeetandHands(sequence, g);
	}

	public void drawEars(String s, Graphics g) {
		g.setColor(getColor(s.charAt(0)));
		g.fillOval(35, 5, 10, 10);
		g.fillOval(55, 5, 10, 10);
	}

	public void drawHead(String s, Graphics g) {
		char shape = s.charAt(1);
		g.setColor(getColor(s.charAt(9)));

		switch(shape) {
			case 'R':
				g.fillOval(40, 10, 20, 20);
				break;
			case 'G':
				g.fillOval(40, 10, 20, 27);
				break;
			case 'B':
				g.fillRect(40, 10, 20, 27);
				break;
			default:
				g.fillOval(40, 10, 20, 20);
				break;
		}
	}

	public void drawEyes(String s, Graphics g) {
		g.setColor(getColor(s.charAt(3)));
		g.fillOval(46, 14, 2, 2);
		g.fillOval(52, 14, 2, 2);
	}

	public void drawMouth(String s, Graphics g) {
		g.setColor(Color.BLACK);
		char shape = s.charAt(4);
		switch(shape) {
			case 'R':
				g.drawLine(47, 20, 53, 20);
				break;
			case 'G':
				g.fillOval(47, 18, 6, 3);
				break;
			case 'B':
				g.fillRect(47, 18, 6, 3);
				break;
			default:
				g.fillOval(49, 18, 4, 4);
				break;
		}

	}

	public void drawLegsAndArms(String s, Graphics g) {
		g.setColor(getColor(s.charAt(5)));
		g.fillRect(40, 48, 6, 37);
		g.fillRect(54, 48, 6, 37);

		g.fillRect(28, 28, 4, 30);
		g.fillRect(28, 28, 40, 4);
		g.fillRect(68, 28, 4, 30);
	}

	public void drawTorso(String s, Graphics g) {
		g.setColor(getColor(s.charAt(8)));
		char roundOrSquare = s.charAt(6);
		char longWideEven = s.charAt(7);//G=wide, R&Y=long, B=even
		switch(roundOrSquare) {
			case 'R': case 'G':
				if(longWideEven=='G')
					g.fillOval(32, 25, 36, 28);
				if(longWideEven=='B')
					g.fillOval(33, 25, 34, 34);
				else
					g.fillOval(35, 25, 30, 36);
				break;
			default:
				if(longWideEven=='G')
					g.fillRect(32, 25, 36, 28);
				if(longWideEven=='B')
					g.fillRect(33, 25, 34, 34);
				else
					g.fillRect(35, 25, 30, 36);
				break;
		}
		
	}

	public void drawFeetandHands(String s, Graphics g) {
		g.setColor(getColor(s.charAt(9)));
		g.fillOval(36, 83, 7, 4);
		g.fillOval(57, 83, 7, 4);
		g.fillOval(27, 56, 5, 5);
		g.fillOval(68, 56, 5, 5);

	}

	private Color getColor(char c) {
		switch(c) {
			case 'R':
				return Color.RED;
			case 'G':
				return Color.GREEN;
			case 'B':
				return Color.BLUE;
			default:
				return Color.YELLOW;	
		}
	}
}