package visualizationofdsa;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class StartingScreen {

	//Main frame of a starting screen. Only operation on the frame is to start the visualization!
	private JFrame frame;
	
	public StartingScreen() throws IOException {
		initializeMainFrame();
	}
	
	
	private void initializeMainFrame() throws IOException {
		frame = new JFrame("DSA Algorithm");
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		JButton button = new JButton("Start");
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Gui();
				frame.setVisible(false);
			}
		});
		
		JTextPane headline = new JTextPane();
		headline.setText("Visualization of DSA algorithm");	
		headline.setEditable(false);
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
		headline.setFont(font);
		headline.setBackground(frame.getBackground());
		StyledDocument doc = headline.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		JTextArea description = new JTextArea();
		description.setEditable(false);
		description.setText(getFromFile("description.txt"));
		description.setBackground(frame.getBackground());
		description.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		JScrollPane scrollDescription = new JScrollPane(description);
		scrollDescription.setWheelScrollingEnabled(true);
		scrollDescription.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		scrollDescription.setPreferredSize(new Dimension(400, 20));

		JTextArea explanation = new JTextArea();
		explanation.setEditable(false);
		explanation.setText(getFromFile("explanation.txt"));
		explanation.setBackground(frame.getBackground());
		explanation.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		JScrollPane scrollExplanation = new JScrollPane(explanation);
		scrollExplanation.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		scrollExplanation.setWheelScrollingEnabled(true);
		scrollExplanation.setPreferredSize(new Dimension(20, 200));
		scrollExplanation.setBackground(frame.getBackground());

		ImageIcon image = new ImageIcon("etflogo.png");
		JLabel label = new JLabel("", image, JLabel.CENTER);
		label.setPreferredSize(new Dimension(160, 100));
		JPanel tempPanel = new JPanel(new BorderLayout());
		tempPanel.add(headline, BorderLayout.NORTH);
		tempPanel.add(scrollDescription, BorderLayout.LINE_START);
		tempPanel.add(label, BorderLayout.LINE_END);
		tempPanel.add(scrollExplanation, BorderLayout.SOUTH);
		frame.setLayout(new BorderLayout());
		frame.add(tempPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1,3));
		buttonPanel.add(new JLabel());
		buttonPanel.add(button);
		buttonPanel.add(new JLabel());
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.setVisible(true);

	}
	

	private String getFromFile(String path) throws IOException {
		String everything;
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    everything = sb.toString();
		} finally {
		    br.close();
		}
		return everything;
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new StartingScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
