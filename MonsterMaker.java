
import javax.swing.JApplet;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import java.util.*;

import java.io.*;
import javafx.stage.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.image.*;
import javax.imageio.ImageIO;


public class MonsterMaker extends JFrame {
	
	/*public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					JLabel label = new JLabel("Hello World");
				}
			})
		}
	}*/
	private BufferedImage scanPic;
	private String sequence;

	public MonsterMaker(){
		super("Monster Maker");
		super.setLayout(new GridLayout(9, 1, 3, 3));
        setMinimumSize(new Dimension(550, 850));
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

 
        //setLayout(new GridLayout(2, 0));
        JPanel bttnLbl = new JPanel(new GridLayout(3, 2));
        bttnLbl.setMinimumSize(new Dimension(320, 20));
        /*JPanel monsters = new JPanel(new FlowLayout(FlowLayout.LEFT));
        monsters.setSize(400, 300);
        monsters.setBackground(Color.WHITE);*/


		JButton uploadB = 
			new JButton("Upload a picture of my DNA strand");
		JButton sequenceB = 
			new JButton("Sequence my strand of DNA!");
		JButton makeMonsterB = 
			new JButton("Show me what my monster looks like!");

		JLabel uploadedL = new JLabel("");
		JLabel sequenceL = new JLabel("");

		uploadB.setForeground(Color.BLACK);
		uploadB.setBackground(Color.WHITE);
		sequenceB.setForeground(Color.BLACK);
		sequenceB.setBackground(Color.WHITE);
		makeMonsterB.setForeground(Color.BLACK);
		makeMonsterB.setBackground(Color.WHITE);

		Border line = new BevelBorder(1);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        uploadB.setBorder(compound);
        sequenceB.setBorder(compound);
        makeMonsterB.setBorder(compound);

        bttnLbl.add(uploadB);
        bttnLbl.add(uploadedL);
        bttnLbl.add(sequenceB);
        bttnLbl.add(sequenceL);
        bttnLbl.add(makeMonsterB);

        add(bttnLbl);

        setVisible(true);

		uploadB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView());
				String[] fileFormats = {"png", "jpg", "gif"};
				FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG, JPG, and GIF files", fileFormats);
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					uploadedL.setText("Scan recieved!");
					try {
						scanPic = ImageIO.read(chooser.getSelectedFile());	
					} catch(IOException e) {
						uploadedL.setText("Invalid file. Please pick another.");
					}
				}
			}
		});

		sequenceB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				sequence = new String(makeSequence(scanPic));
				sequenceL.setText(sequence);
			}
			
		});

		makeMonsterB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				Monster mon = new Monster(sequence);
				add(mon);
				uploadedL.setText("");
				sequenceL.setText("");

			}
		});


	}

	private char[] makeSequence(BufferedImage s) {
		int[] rgbSequence = handleImage(s);
		char[] sequence = new char[10];
		for(int i = 0; i < 10; i++) {
			sequence[i] = decideColor(rgbSequence[i]);
		}
		return sequence;
	}

	private int[] handleImage(BufferedImage s) {
		int h = s.getHeight();
		int w = s.getWidth();

		int[][] pixelColors = new int[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				pixelColors[i][j] = s.getRGB(i*w/10 + w/20, j*h/10 + h/20);
			}
		}

		int[] colorSequence = new int[10];
		if(avghorizstdev(pixelColors) <= avgvertstdev(pixelColors)) {
			for(int i = 0; i < 10; i++) {
				int sum = 0;
				for(int j = 0; j < 10; j++) {
					sum += pixelColors[i][j];
				}
				colorSequence[i] = sum/10;
			}
		} else {
			for(int i = 0; i < 10; i++) {
				int sum = 0;
				for(int j = 0; j < 10; j++) {
					sum += pixelColors[j][i];
				}
				colorSequence[i] = sum/10;
			}
		}
		return colorSequence;
	}

	private double avghorizstdev(int[][] pixels) {
		double[] stdevs = new double[10];

		for(int i = 0; i < 10; i++) {
			int rowSum = 0;
			for(int j = 0; j < 10; j++) {
				rowSum += pixels[i][j];
			}
			double avg = rowSum/10;
			double totMeanDevSquared = 0;
			for(int k = 0; k < 10; k++) {
				totMeanDevSquared += Math.pow((pixels[i][k] - avg), 2);
			}
			stdevs[i] = Math.sqrt(totMeanDevSquared/9);
		}

		double sumstdevs = 0;
		for(int x = 0; x < 10; x++) {
			sumstdevs += stdevs[x];
		}

		return sumstdevs;
	}

	private double avgvertstdev(int[][] pixels) {
		double[] stdevs = new double[10];

		for(int i = 0; i < 10; i++) {
			int rowSum = 0;
			for(int j = 0; j < 10; j++) {
				rowSum += pixels[j][i];
			}
			double avg = rowSum/10;
			double totMeanDevSquared = 0;
			for(int k = 0; k < 10; k++) {
				totMeanDevSquared += Math.pow((pixels[k][i] - avg), 2);
			}
			stdevs[i] = Math.sqrt(totMeanDevSquared/9);
		}

		double sumstdevs = 0;
		for(int x = 0; x < 10; x++) {
			sumstdevs += stdevs[x];
		}

		return sumstdevs;
	}

	private char decideColor(int rgbVal) {
		Color c = Color.decode("" + rgbVal);
		int red = c.getRed();
		int green = c.getGreen();
		int blue = c.getBlue();
		/*int red = Color.red(rgbVal);
		int blue = Color.blue(rgbVal);
		int green = Color.green(rgbVal);*/

		if(Math.abs(red-green) < 50 && blue < 100) {
			return 'Y';
		}

		if(red > blue) {
			if(red > green) {
				return 'R';
			} else {
				return 'G';
			}
		} else {
			return 'B';
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MonsterMaker();
			}
		});		
	}
}