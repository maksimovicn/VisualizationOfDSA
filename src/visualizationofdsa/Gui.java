package visualizationofdsa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class Gui {
	
	private JFrame frame;
	private JPanel keyGenPanel;
	private JPanel signPanel;
	private JPanel verifyPanel;
	
	protected String currentMessageSign;
	protected String currentMessage;
	protected String currentMessageVerify;
	protected boolean signed = false;
	
	private String lineR;
	private String lineS;
	
	private static MyDSA myDsa = new MyDSA();

	public Gui() {
		
		frame = new JFrame("DSA Algorithm");
		frame.setSize(620, 750);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		
		initializeKeyGenTab();
		initializeSignFrame();
		initializeVerifyFrame();
		initializeMainFrame();
	}
	
	private void initializeVerifyFrame() {
		
		verifyPanel = new JPanel(new GridLayout(2,1));
		VerifyPanel verifyTempPanel = new VerifyPanel();
		verifyPanel.add(verifyTempPanel);
		
		verifyTempPanel.btnMsg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame showMessage = new JFrame("Message details");
        		showMessage.setResizable(false);
        		showMessage.setSize(600, 300);
        		showMessage.setVisible(true);
        		showMessage.setLayout(null);
        		showMessage.setLocationRelativeTo(frame);
        		JTextArea msgArea = new JTextArea();
        		msgArea.setText(currentMessageVerify);
        		msgArea.setBounds(0,0,600, 225);
        		msgArea.setEditable(false);
        		showMessage.add(msgArea);
        		JButton editMsg = new JButton("Edit");
        		editMsg.setBounds(200, 230, 200, 25);
        		showMessage.add(editMsg);
        		editMsg.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (editMsg.getText().equals("Edit")) {
							msgArea.setEditable(true);
							editMsg.setText("Finish editing");
						} else {
							msgArea.setEditable(false);
							editMsg.setText("Edit");
							currentMessageVerify = msgArea.getText();
						}
					}
				});
			}
		});
		
		verifyTempPanel.btnS.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame sigDetails = new JFrame("Signature details");
				sigDetails.setResizable(false);
				sigDetails.setSize(600, 300);
				sigDetails.setVisible(true);
				sigDetails.setLayout(null);
				sigDetails.setLocationRelativeTo(frame);
        		
                JTextPane explanation = new JTextPane();
        		explanation.setBackground(SystemColor.menu);
        		explanation.setBounds(10, 31, 580, 100);
        		explanation.setEditable(false);
        		StyledDocument docExp = explanation.getStyledDocument();
        		SimpleAttributeSet centerExp = new SimpleAttributeSet();
        		StyleConstants.setAlignment(centerExp, StyleConstants.ALIGN_CENTER);
        		docExp.setParagraphAttributes(0, docExp.getLength(), centerExp, false);
        		sigDetails.add(explanation);
        		explanation.setText("Value s represents part of a signature {r, s} that came with a message for verification!\n"
            			+ "For more details on how the signature is generated visit sign tab!");
        		
        		JTextPane value = new JTextPane();
        		value.setBackground(SystemColor.menu);
        		value.setBounds(10, 131, 580, 100);
        		value.setEditable(false);
        		StyledDocument docVal = value.getStyledDocument();
        		SimpleAttributeSet centerVal = new SimpleAttributeSet();
        		StyleConstants.setAlignment(centerVal, StyleConstants.ALIGN_CENTER);
        		docVal.setParagraphAttributes(0, docVal.getLength(), centerVal, false);
        		sigDetails.add(value);
        		value.setText("Value of s for this message is:\n"
        				+ "s = " + lineS);
			}
		});
		
		verifyTempPanel.btnR.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame sigDetails = new JFrame("Signature details");
				sigDetails.setResizable(false);
				sigDetails.setSize(600, 300);
				sigDetails.setVisible(true);
				sigDetails.setLayout(null);
				sigDetails.setLocationRelativeTo(frame);
        		
                JTextPane explanation = new JTextPane();
        		explanation.setBackground(SystemColor.menu);
        		explanation.setBounds(10, 31, 580, 100);
        		explanation.setEditable(false);
        		StyledDocument docExp = explanation.getStyledDocument();
        		SimpleAttributeSet centerExp = new SimpleAttributeSet();
        		StyleConstants.setAlignment(centerExp, StyleConstants.ALIGN_CENTER);
        		docExp.setParagraphAttributes(0, docExp.getLength(), centerExp, false);
        		sigDetails.add(explanation);
        		explanation.setText("Value r represents part of a signature {r, s} that came with a message for verification!\n"
            			+ "For more details on how the signature is generated visit sign tab!");
        		
        		JTextPane value = new JTextPane();
        		value.setBackground(SystemColor.menu);
        		value.setBounds(10, 131, 580, 100);
        		value.setEditable(false);
        		StyledDocument docVal = value.getStyledDocument();
        		SimpleAttributeSet centerVal = new SimpleAttributeSet();
        		StyleConstants.setAlignment(centerVal, StyleConstants.ALIGN_CENTER);
        		docVal.setParagraphAttributes(0, docVal.getLength(), centerVal, false);
        		sigDetails.add(value);
        		value.setText("Value of r for this message is:\n"
        				+ "r = " + lineR);
			}
		});
		
		verifyTempPanel.btnH.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame showHash = new JFrame("Hash details");
        		showHash.setResizable(false);
        		showHash.setSize(600, 300);
        		showHash.setVisible(true);
        		showHash.setLayout(null);
        		showHash.setLocationRelativeTo(frame);;
        		JTextArea msgArea = new JTextArea();
        		msgArea.setBounds(0,0,600, 300);
        		msgArea.setEditable(false);
        		msgArea.setBackground(SystemColor.menu);
        		showHash.add(msgArea);
        		if (myDsa.p != null && myDsa.hashVal != null) { 
        			//detailsArea.setText(detailsArea.getText() + "H(M) using SHA-1 = " + myDsa.hashVal + "\n");

            		if (currentMessageSign != null)
            			msgArea.setText("Current message is: \n\n" + currentMessage + "\n\nHash value (SHA-1) is " + myDsa.hashVal);
            		else
            			msgArea.setText("The DSA uses SHA-1 hash algorithm!");
        		} else { 
        			//detailsArea.setText(detailsArea.getText() + "Hash algorithm used by DSA is SHA-1" + "\n");
        			msgArea.setText("The DSA uses SHA-1 hash algorithm!");
        		}
			}
		});
		
		verifyTempPanel.btnF3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		verifyTempPanel.btnF4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JPanel downPanel = new JPanel();
		downPanel.setLayout(null);
		
		JTextField msgPath = new JTextField();
		msgPath.setBounds(60, 100, 330, 20);
		downPanel.add(msgPath);
		
		JLabel lblLoadMsgFrom = new JLabel("Load message from file");
		lblLoadMsgFrom.setBounds(60, 75, 308, 14);
		downPanel.add(lblLoadMsgFrom);
		
		JButton btnLoadM = new JButton("Load");
		btnLoadM.setBounds(400, 100, 110, 20);
		btnLoadM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (signed) {
					int confirmResult = JOptionPane.showConfirmDialog(frame,
							"There is already signed message in the clipboard. Are you sure you want to load another signed message?");
					if (confirmResult == JOptionPane.YES_OPTION) {
						//TODO
					} 
				} else {
					signed = false;
					try {
						currentMessageVerify = getFromFileWithRS(msgPath.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		downPanel.add(btnLoadM);
		
		JTextField parPath = new JTextField();
		parPath.setBounds(60, 200, 330, 20);
		downPanel.add(parPath);
		
		JLabel lblLoadParamsFrom = new JLabel("Load parameters from file");
		lblLoadParamsFrom.setBounds(60, 175, 308, 14);
		downPanel.add(lblLoadParamsFrom);
		
		JButton btnLoadPar = new JButton("Load");
		btnLoadPar.setBounds(400, 200, 110, 20);
		btnLoadPar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					BufferedReader br = new BufferedReader(new FileReader(parPath.getText()));
					String line = br.readLine();
					myDsa.p = new BigInteger(line);
					line = br.readLine();
					myDsa.q = new BigInteger(line);
					line = br.readLine();
					myDsa.g = new BigInteger(line);
					line = br.readLine();
					myDsa.x = new BigInteger(line);
					line = br.readLine();
					myDsa.y = new BigInteger(line);
					line = br.readLine();
					myDsa.k = new BigInteger(line);
					myDsa.kInv = myDsa.k.modInverse(myDsa.q);
					JOptionPane.showMessageDialog(frame,
							"Successfully loaded parameters:\n" + "p = " + myDsa.p + "\n" + "q = " + myDsa.q + "\n"
									+ "g = " + myDsa.g + "\n" + "x = " + myDsa.x + "\n" + "y = " + myDsa.y + "\n"
									+ "k = " + myDsa.k);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "There is no file named " + parPath.getText());
				}
			}
		});
		downPanel.add(btnLoadPar);
		
		JButton btnVerify = new JButton("Verify Message");
		btnVerify.setBounds(248, 283, 125, 23);
		downPanel.add(btnVerify);
		
		verifyPanel.add(downPanel);
	}

	private void initializeKeyGenTab() {

		keyGenPanel = new JPanel();
		keyGenPanel.setLayout(null);
		
		JTextArea txtrOpsteInformacijeO = new JTextArea();
		txtrOpsteInformacijeO.setText("About DSA parameters:\n\n"
				+ "-P is a large prime number\n"
				+ "-Q is the prime divisor of P - 1.\n"
				+ "-G is a number calculated based on a random value and P,Q. \n"
				+ "-X is a private key randomly generated and takes value between 0 < X < Q \n"
				+ "-Y is a public key and we can calculate it Y = G^X mod P\n"
				+ "-K is a session key randomly generated unique to each message");
		txtrOpsteInformacijeO.setBounds(10, 11, 580, 137);
		txtrOpsteInformacijeO.setBackground(SystemColor.control);
		txtrOpsteInformacijeO.setEditable(false);
		keyGenPanel.add(txtrOpsteInformacijeO);
		
		JLabel lblP = new JLabel("p:");
		lblP.setBounds(10, 186, 60, 25);
		keyGenPanel.add(lblP);
		
		JTextField parP = new JTextField();
		parP.setBounds(80, 188, 229, 23);
		keyGenPanel.add(parP);
		
		JTextField txtPrimeNumber = new JTextField();
		txtPrimeNumber.setText("prime number");
		txtPrimeNumber.setEditable(false);
		txtPrimeNumber.setBounds(378, 188, 212, 25);
		txtPrimeNumber.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		keyGenPanel.add(txtPrimeNumber);
		txtPrimeNumber.setColumns(10);
		
		JLabel label = new JLabel("q:");
		label.setBounds(10, 265, 60, 25);
		keyGenPanel.add(label);
		
		JTextField parQ = new JTextField();
		parQ.setBounds(80, 267, 229, 23);
		parQ.setEditable(false);
		keyGenPanel.add(parQ);
		
		JLabel label_1 = new JLabel("g:");
		label_1.setBounds(10, 345, 60, 25);
		keyGenPanel.add(label_1);
		
		JTextField parG = new JTextField();
		parG.setBounds(80, 347, 229, 23);
		parG.setEditable(false);
		keyGenPanel.add(parG);
		
		JTextField textFieldQ = new JTextField();
		textFieldQ.setText("prime divisor of p-1");
		textFieldQ.setEditable(false);
		textFieldQ.setColumns(10);
		textFieldQ.setBounds(378, 267, 212, 25);
		textFieldQ.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		keyGenPanel.add(textFieldQ);
		
		JTextField textFieldG = new JTextField();
		textFieldG.setText("h^((p-1)/q) mod q, where h is random value");
		textFieldG.setEditable(false);
		textFieldG.setColumns(10);
		textFieldG.setBounds(378, 347, 212, 25);
		textFieldG.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		keyGenPanel.add(textFieldG);
		
		JTextArea txtKeys = new JTextArea();
		txtKeys.setText("Keys\n");
		txtKeys.setEditable(false);
		txtKeys.setBounds(10, 406, 580, 150);
		txtKeys.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtKeys.setVisible(false);
		txtKeys.setBackground(SystemColor.menu);
		keyGenPanel.add(txtKeys);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(254, 616, 89, 23);

		keyGenPanel.add(btnSave);
		
		JLabel lblSaveAs = new JLabel("Save as...");
		lblSaveAs.setBounds(144, 568, 130, 25);
		keyGenPanel.add(lblSaveAs);
		
		JTextField savePath = new JTextField();
		savePath.setBounds(219, 570, 220, 20);
		keyGenPanel.add(savePath);
		savePath.setColumns(10);

		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveParameters(savePath.getText());
			}
		});

		parP.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String test = parP.getText();
				try {
					myDsa.generateParams(test);
					parP.setText(myDsa.p.toString());
					parQ.setText(myDsa.q.toString());
					parG.setText(myDsa.g.toString());
					txtKeys.setText("Keys\n");
					txtKeys.append("Private key:\n");
					txtKeys.append("x = " + myDsa.x.toString() + "\n\n");
					txtKeys.append("Session key:\n");
					txtKeys.append("k = " + myDsa.k.toString() + "\n\n");
					txtKeys.append("Public key:\n");
					txtKeys.append("y = " + myDsa.y.toString());
					txtKeys.setVisible(true);
				} 
				catch(Exception ex) {
					JOptionPane.showMessageDialog(frame, "Please enter a large number for value p!");
				}
			}
		});
	}
	
	protected void saveParameters(String path) {
		// TODO Auto-generated method stub
		if (path.indexOf('.') == -1) {
			path += ".txt";
		}
		PrintWriter pwout = null;
		try {
			//out = new ObjectOutputStream(new FileOutputStream(path));
			pwout = new PrintWriter(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pwout.println(myDsa.p);
		pwout.println(myDsa.q);
		pwout.println(myDsa.g);
		pwout.println(myDsa.x);
		pwout.println(myDsa.y);
		pwout.print(myDsa.k);
		pwout.close();
		
		JOptionPane.showMessageDialog(frame, "Parameters have been successfully saved as " + path);
	}

	protected void saveMessage(String path, String message) {
		if (path.indexOf('.') == -1) {
			path += ".txt";
		}
		PrintWriter pwout = null;
		try {
			//out = new ObjectOutputStream(new FileOutputStream(path));
			pwout = new PrintWriter(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pwout.print(message);
		pwout.close();

		JOptionPane.showMessageDialog(frame, "Message have been successfully saved as " + path);
	}

	private void initializeSignFrame() {
		
		signPanel = new JPanel();
		signPanel.setLayout(new GridLayout(2, 1));
		SignPanel signPanelTemp = new SignPanel();
		signPanel.add(signPanelTemp);

		JPanel downTempSignPanel = new JPanel();
		downTempSignPanel.setLayout(new GridLayout(1, 2));
		
		signPanelTemp.btnMsg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame showMessage = new JFrame("Message details");
        		showMessage.setResizable(false);
        		showMessage.setSize(600, 300);
        		showMessage.setVisible(true);
        		showMessage.setLayout(null);
        		showMessage.setLocationRelativeTo(frame);
        		JTextArea msgArea = new JTextArea();
        		msgArea.setText(currentMessageSign);
        		msgArea.setBounds(0,0,600, 225);
        		showMessage.add(msgArea);
        		JButton saveAndClose = new JButton("Save message and close");
        		saveAndClose.setBounds(200, 230, 200, 25);
        		showMessage.add(saveAndClose);
        		saveAndClose.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						currentMessageSign = msgArea.getText();
						currentMessage = currentMessageSign;
						showMessage.setVisible(false);
					}
				});
        	    showMessage.addWindowListener(new WindowAdapter() {
        	        public void windowClosing(WindowEvent e) {
        		        try {
        		        	currentMessageSign = msgArea.getText();
    						currentMessage = currentMessageSign;
        			    } catch (Exception e1) {
        			    	e1.printStackTrace();
        			    } 
        	        }
        	     });
				
			}
		});
		
		signPanelTemp.btnH.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame showHash = new JFrame("Hash details");
        		showHash.setResizable(false);
        		showHash.setSize(600, 300);
        		showHash.setVisible(true);
        		showHash.setLayout(null);
        		showHash.setLocationRelativeTo(frame);;
        		JTextArea msgArea = new JTextArea();
        		msgArea.setBounds(0,0,600, 300);
        		msgArea.setEditable(false);
        		msgArea.setBackground(SystemColor.menu);
        		showHash.add(msgArea);
        		if (myDsa.p != null && myDsa.hashVal != null) { 
        			//detailsArea.setText(detailsArea.getText() + "H(M) using SHA-1 = " + myDsa.hashVal + "\n");

            		if (currentMessageSign != null)
            			msgArea.setText("Current message is: \n\n" + currentMessage + "\n\nHash value (SHA-1) is " + myDsa.hashVal);
            		else
            			msgArea.setText("The DSA uses SHA-1 hash algorithm!");
        		} else { 
        			//detailsArea.setText(detailsArea.getText() + "Hash algorithm used by DSA is SHA-1" + "\n");
        			msgArea.setText("The DSA uses SHA-1 hash algorithm!");
        		}
				
			}
		});
		
		signPanelTemp.btnF1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame f1Details = new JFrame("Details of f1");
        		f1Details.setLayout(null);
        		f1Details.setResizable(false);
        		f1Details.setSize(600, 300);
        		f1Details.setVisible(true);
        		f1Details.setLocationRelativeTo(frame);

        		JTextField txtValuesIs = new JTextField();
        		txtValuesIs.setText("Value 's' is a part of the signature calculated:");
        		txtValuesIs.setBounds(25, 11, 246, 25);
        		f1Details.add(txtValuesIs);
        		txtValuesIs.setColumns(10);
        		txtValuesIs.setEditable(false);
        		txtValuesIs.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        		
        		JLabel lblSK = new JLabel("s  =  k  * (Hash(Msg) + x*r))  mod  q");
        		lblSK.setBounds(170, 47, 360, 25);
        		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        		lblSK.setFont(font);
        		f1Details.add(lblSK);
        		
        		JLabel label = new JLabel("-1");
        		label.setBounds(213, 45, 17, 14);
        		font = new Font(Font.SANS_SERIF, Font.BOLD, 8);
        		label.setFont(font);
        		f1Details.add(label);
        		
        		JTextField txtCalculation = new JTextField();
        		txtCalculation.setText("Values:");
        		txtCalculation.setBounds(25, 88, 86, 20);
        		f1Details.add(txtCalculation);
        		txtCalculation.setColumns(10);
        		txtCalculation.setEditable(false);
        		txtCalculation.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        		font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        		
        		JLabel lblK = new JLabel("k");
        		lblK.setBounds(30, 120, 46, 14);
        		f1Details.add(lblK);
        		
        		JLabel label_1 = new JLabel("-1");
        		label_1.setBounds(36, 115, 40, 14);
        		f1Details.add(label_1);
        		
        		JLabel label_2 = new JLabel("=");
        		label_2.setBounds(50, 120, 40, 14);
        		f1Details.add(label_2);
        		
        		JTextField txtKinv = new JTextField();
        		txtKinv.setBounds(114, 120, 476, 14);
        		f1Details.add(txtKinv);
        		txtKinv.setColumns(10);
        		
        		JLabel lblHashmsg = new JLabel("Hash(Msg) =");
        		lblHashmsg.setBounds(30, 140, 86, 14);
        		f1Details.add(lblHashmsg);
        		
        		JTextField txtHash = new JTextField();
        		txtHash.setBounds(114, 140, 476, 14);
        		f1Details.add(txtHash);
        		txtHash.setColumns(10);
        		
        		JLabel lblX = new JLabel("x = ");
        		lblX.setBounds(30, 160, 46, 14);
        		f1Details.add(lblX);
        		
        		JTextField txtX = new JTextField();
        		txtX.setBounds(114, 160, 476, 14);
        		f1Details.add(txtX);
        		txtX.setColumns(10);
        		
        		JLabel lblR = new JLabel("r = ");
        		lblR.setBounds(30, 180, 46, 14);
        		f1Details.add(lblR);
        		
        		JTextField txtR = new JTextField();
        		txtR.setBounds(114, 180, 476, 14);
        		f1Details.add(txtR);
        		txtR.setColumns(10);
        		
        		JLabel lblQ = new JLabel("q = ");
        		lblQ.setBounds(30, 200, 46, 14);
        		f1Details.add(lblQ);
        		
        		JTextField txtQ = new JTextField();
        		txtQ.setBounds(114, 200, 476, 14);
        		f1Details.add(txtQ);
        		txtQ.setColumns(10);
        		
        		JLabel lblS = new JLabel("s = ");
        		lblS.setBounds(30, 230, 46, 14);
        		font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        		lblS.setFont(font);
        		f1Details.add(lblS);
        		
        		JTextField txtS = new JTextField();
        		txtS.setBounds(114, 230, 476, 20);
        		f1Details.add(txtS);
        		txtS.setFont(font);
        		txtS.setColumns(10);
        		
/*        		if (myDsa.p != null && myDsa.hashVal != null) {
        			calculationSpace.setText("(" + myDsa.kInv + " * (" + giveMeExp(myDsa.hashVal) + " + " 
        					+ myDsa.x + " * " + myDsa.r + " )) mod " + myDsa.q + " = " + myDsa.s + "\n");
        			txtCalculation.setVisible(true);
        		}*/
			}
		});
		
		signPanelTemp.btnF2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame f2Details = new JFrame("Details of f2");
        		f2Details.setLayout(null);
        		f2Details.setResizable(false);
        		f2Details.setSize(600, 300);
        		f2Details.setVisible(true);
        		f2Details.setLocationRelativeTo(frame);

				JTextField txtValuesIs = new JTextField();
				txtValuesIs.setText("Value 'r' is a part of the signature calculated:");
				txtValuesIs.setBounds(25, 11, 246, 25);
				f2Details.add(txtValuesIs);
				txtValuesIs.setColumns(10);
				txtValuesIs.setEditable(false);
				txtValuesIs.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				
				JLabel lblSK = new JLabel("r  =  (g   mod p)  mod  q");
				lblSK.setBounds(187, 47, 360, 25);
				Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
				lblSK.setFont(font);
				f2Details.add(lblSK);
				
				JLabel label = new JLabel("k");
				label.setBounds(233, 47, 17, 14);
				font = new Font(Font.SANS_SERIF, Font.BOLD, 10);
				label.setFont(font);
				f2Details.add(label);
				
				JTextField txtCalculation = new JTextField();
				txtCalculation.setText("Values:");
				txtCalculation.setBounds(25, 95, 86, 20);
				f2Details.add(txtCalculation);
				txtCalculation.setColumns(10);
				txtCalculation.setEditable(false);
				txtCalculation.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				
				JLabel lblG = new JLabel("g = ");
				lblG.setBounds(35, 125, 46, 14);
				f2Details.add(lblG);
				
				JTextField txtG = new JTextField();
				txtG.setBounds(60, 125, 500, 14);
				f2Details.add(txtG);
				txtG.setColumns(10);
				
				
				JLabel lblK = new JLabel("k = ");
				lblK.setBounds(35, 150, 46, 14);
				f2Details.add(lblK);

				JTextField txtK = new JTextField();
				txtK.setBounds(60, 150, 500, 14);
				f2Details.add(txtK);
				txtK.setColumns(10);
				
				JLabel lblP = new JLabel("p = ");
				lblP.setBounds(35, 175, 46, 14);
				f2Details.add(lblP);
				
				JTextField txtP = new JTextField();
				txtP.setBounds(60, 175, 500, 14);
				f2Details.add(txtP);
				txtP.setColumns(10);
				
				JLabel lblQ = new JLabel("q = ");
				lblQ.setBounds(35, 200, 46, 14);
				f2Details.add(lblQ);
				
				JTextField txtQ = new JTextField();
				txtQ.setBounds(60, 200, 500, 14);
				f2Details.add(txtQ);
				txtQ.setColumns(10);
				
				JLabel lblR = new JLabel("r = ");
				lblR.setBounds(35, 240, 46, 14);
				font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
				lblR.setFont(font);
				f2Details.add(lblR);
				
				JTextField txtR = new JTextField();
				txtR.setBounds(60, 240, 500, 20);
				txtR.setFont(font);
				f2Details.add(txtR);
				txtR.setColumns(10);

			}
		});
		
		signPanelTemp.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                
                JFrame parDetails = new JFrame("Parameter details");
                parDetails.setResizable(false);
                parDetails.setSize(600, 300);
                parDetails.setVisible(false);
                parDetails.setLayout(null);
                parDetails.setLocationRelativeTo(frame);
        		
                JTextPane explanation = new JTextPane();
        		explanation.setBackground(SystemColor.menu);
        		explanation.setBounds(10, 31, 580, 100);
        		explanation.setEditable(false);
        		StyledDocument docExp = explanation.getStyledDocument();
        		SimpleAttributeSet centerExp = new SimpleAttributeSet();
        		StyleConstants.setAlignment(centerExp, StyleConstants.ALIGN_CENTER);
        		docExp.setParagraphAttributes(0, docExp.getLength(), centerExp, false);
        		parDetails.add(explanation);
        		
        		JTextPane value = new JTextPane();
        		value.setBackground(SystemColor.menu);
        		value.setBounds(10, 131, 580, 100);
        		value.setEditable(false);
        		StyledDocument docVal = value.getStyledDocument();
        		SimpleAttributeSet centerVal = new SimpleAttributeSet();
        		StyleConstants.setAlignment(centerVal, StyleConstants.ALIGN_CENTER);
        		docVal.setParagraphAttributes(0, docVal.getLength(), centerVal, false);
        		parDetails.add(value);
        		
                switch (giveMePressedParameter(me.getX(), me.getY())) {
	                case 's':
	                	explanation.setText("Value s represents part of a signature {r, s} that goes with a message for verification!\n"
	                			+ "For more details click the f1 button on the scheme!");
	                	if (myDsa.s != null) {
	                		value.setText("Current value of s is:\n"
	                				+ "s = " + myDsa.s);
	                	}
	                	parDetails.setVisible(true);
	                	break;
	                case 'p':
	                	explanation.setText("Value p is one of three domain parameters. {p, q, g} Togheter, they are global public key, known to everybody.\n"
	                			+ "P is a large prime number!");
	                	if (myDsa.p != null) {
	                		value.setText("Current value of p is:\n"
	                				+ "p = " + myDsa.p);
	                	}
	                	parDetails.setVisible(true);
	                	break;
	                case 'q':
	                	explanation.setText("Value q is one of three domain parameters. {p, q, g} Togheter, they are global public key, known to everybody.\n"
	                			+ "Q is a prime divisor of p - 1");
	                	if (myDsa.q != null) {
	                		value.setText("Current value of q is:\n"
	                				+ "q = " + myDsa.q);
	                	}
	                	parDetails.setVisible(true);
	                	break;
	                case 'g':
	                	explanation.setText("Value g is one of three domain parameters. {p, q, g} Togheter, they are global public key, known to everybody.\n"
	                			+ "G is number calculated based on a random value and other two domain parameters p and q.");
	                	if (myDsa.g != null) {
	                		value.setText("Current value of g is:\n"
	                				+ "g = " + myDsa.g);
	                	}
	                	parDetails.setVisible(true);
	                	break;
                	case 'x':
	                	explanation.setText("Value x is the private key known only to signer of the message.\n"
	                			+ "In order algorithm to work, x must be x < q!");
	                	if (myDsa.x != null) {
	                		value.setText("Current value of x is:\n"
	                				+ "x = " + myDsa.x);
	                	}
	                	parDetails.setVisible(true);
                		break;
                	case 'k':
                		explanation.setText("Value k is a session key. Random number unique to each message.\n"
	                			+ "It is known to signer and it is to keep as a private information!");
	                	if (myDsa.k != null) {
	                		value.setText("Current value of k is:\n"
	                				+ "k = " + myDsa.k);
	                	}
	                	parDetails.setVisible(true);
                		break;
                	case 'r':
	                	explanation.setText("Value r represents part of a signature {r, s} that goes with a message for verification!\n"
	                			+ "For more details click the f2 button on the scheme!");
	                	if (myDsa.r != null) {
	                		value.setText("Current value of s is:\n"
	                				+ "r = " + myDsa.r);
	                	}
	                	parDetails.setVisible(true);
                		break;
                	default:
	                	parDetails.setVisible(false);
                		break;	
                }
            }
        });

//*****************************************************************************NEW!!!**********************************************************
		JPanel newDownPanel = new JPanel();
		newDownPanel.setLayout(null);
		
		JTextField msgPath = new JTextField();
		msgPath.setBounds(60, 100, 330, 20);
		newDownPanel.add(msgPath);
		
		JLabel lblLoadMsgFrom = new JLabel("Load message from file");
		lblLoadMsgFrom.setBounds(60, 75, 308, 14);
		newDownPanel.add(lblLoadMsgFrom);
		
		JLabel lblSaveMsgTo = new JLabel("Save message as...");
		lblSaveMsgTo.setBounds(60, 75, 308, 14);
		lblSaveMsgTo.setVisible(false);
		newDownPanel.add(lblSaveMsgTo);
		
		JButton btnLoadM = new JButton("Load");
		btnLoadM.setBounds(400, 100, 110, 20);
		newDownPanel.add(btnLoadM);
		btnLoadM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		        try {
		        	currentMessageSign = getFromFile(msgPath.getText());
					currentMessage = currentMessageSign;
		        	signed = false;
		        	JOptionPane.showMessageDialog(frame, "You have successfully loaded your message!");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "There is no file named " + msgPath.getText());
				}
			}
		});
		
		JButton btnSaveM = new JButton("Save");
		btnSaveM.setBounds(400, 100, 110, 20);
		btnSaveM.setVisible(false);
		newDownPanel.add(btnSaveM);
		
		JTextField parPath = new JTextField();
		parPath.setBounds(60, 200, 330, 20);
		newDownPanel.add(parPath);
		
		JLabel lblLoadParamsFrom = new JLabel("Load parameters from file");
		lblLoadParamsFrom.setBounds(60, 175, 308, 14);
		newDownPanel.add(lblLoadParamsFrom);
		
		JButton btnLoadParam = new JButton("Load");
		btnLoadParam.setBounds(400, 200, 110, 20);
		btnLoadParam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					BufferedReader br = new BufferedReader(new FileReader(parPath.getText()));
					String line = br.readLine();
					myDsa.p = new BigInteger(line);
					line = br.readLine();
					myDsa.q = new BigInteger(line);
					line = br.readLine();
					myDsa.g = new BigInteger(line);
					line = br.readLine();
					myDsa.x = new BigInteger(line);
					line = br.readLine();
					myDsa.y = new BigInteger(line);
					line = br.readLine();
					myDsa.k = new BigInteger(line);
					myDsa.kInv = myDsa.k.modInverse(myDsa.q);
					JOptionPane.showMessageDialog(frame,
							"Successfully loaded parameters:\n" + "p = " + myDsa.p + "\n" + "q = " + myDsa.q + "\n"
									+ "g = " + myDsa.g + "\n" + "x = " + myDsa.x + "\n" + "y = " + myDsa.y + "\n"
									+ "k = " + myDsa.k);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "There is no file named " + parPath.getText());
				}
			}
		});
		newDownPanel.add(btnLoadParam);
		
		JButton btnSign = new JButton("Sign Message");
		btnSign.setBounds(250, 283, 118, 23);
		btnSign.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (myDsa.p == null)
					JOptionPane.showMessageDialog(frame,
							"You haven't generated or loaded the parameters for signing the message\n"
									+ "Go back to Keygen tab and generate parameters or load your own parameters!");
				else {
					if (currentMessageSign == null) {
						int confirmResult = JOptionPane.showConfirmDialog(frame,
								"You haven't entered message to be signed!\n"
										+ "Are you sure you want to sign the default message?!");
						if (confirmResult == JOptionPane.YES_OPTION) {
							currentMessageSign = "Auto generated message";
							myDsa.sign(currentMessageSign);
							currentMessageVerify = currentMessageSign;
							currentMessageSign += "\n" + myDsa.s + "\n" + myDsa.r;
							lineS = myDsa.s.toString();
							lineR = myDsa.r.toString();
							signed = true;
							String savePath = JOptionPane.showInputDialog(frame, "Your message have been signed!\n Do you want to save it for further verification?!");
							if (savePath != null) {
								String message = currentMessageSign.replaceAll("\n", System.lineSeparator());
								saveMessage(savePath, message);
							}
							else {
								btnLoadParam.setVisible(false);
								lblLoadParamsFrom.setVisible(false);
								parPath.setVisible(false);
								lblLoadMsgFrom.setVisible(false);
								btnLoadM.setVisible(false);
								lblSaveMsgTo.setVisible(true);
								btnSaveM.setVisible(true);
								btnSign.setVisible(false);
							}
						}
					} else {
						myDsa.sign(currentMessageSign);
						currentMessageVerify = currentMessageSign;
						currentMessageSign += "\n" + myDsa.s + "\n" + myDsa.r;
						lineS = myDsa.s.toString();
						lineR = myDsa.r.toString();
						signed = true;
						String savePath = JOptionPane.showInputDialog(frame, "Your message have been signed!\n Do you want to save it for further verification?!");
						if (savePath != null) {
							String message = currentMessageSign.replaceAll("\n", System.lineSeparator());
							saveMessage(savePath, message);
						}
						else {
							btnLoadParam.setVisible(false);
							lblLoadParamsFrom.setVisible(false);
							parPath.setVisible(false);
							lblLoadMsgFrom.setVisible(false);
							btnLoadM.setVisible(false);
							lblSaveMsgTo.setVisible(true);
							btnSaveM.setVisible(true);
							btnSign.setVisible(false);
						}
					}

				}
			}
		});
		btnSaveM.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String message = currentMessageSign.replaceAll("\n", System.lineSeparator());
				saveMessage(msgPath.getText(), message);
				msgPath.setText("");
				btnLoadParam.setVisible(true);
				lblLoadParamsFrom.setVisible(true);
				parPath.setVisible(true);
				lblLoadMsgFrom.setVisible(true);
				btnLoadM.setVisible(true);
				lblSaveMsgTo.setVisible(false);
				btnSaveM.setVisible(false);
				btnSign.setVisible(true);
				currentMessageSign = "";
			}
		});

		newDownPanel.add(btnSign);
		
		signPanel.add(newDownPanel);
	}
	
	protected char giveMePressedParameter(int x, int y) {
		if (between(x, y, 490, 520, 100, 130))
			return 's';
		if (between(x, y, 95, 125, 230, 255))
			return 'p';
		if (between(x, y, 95, 125, 256, 275) || between(x, y, 390, 420, 10, 40))
			return 'q';
		if (between(x, y, 95, 125, 276, 300))
			return 'g';
		if (between(x, y, 360, 390, 10, 40))
			return 'x';
		if (between(x, y, 190, 230, 160, 200))
			return 'k';
		if (between(x, y, 490, 520, 240, 260))
			return 'r';
		return 'o';
				
	}

	private boolean between(int x, int y, int x1, int x2, int y1, int y2) {
		return x > x1 && x < x2 && y > y1 && y < y2;
	}

	protected String giveMeExp(BigInteger hashVal) {
		// TODO Auto-generated method stub
		BigInteger num = hashVal;
		int exp = 0;
		BigInteger measure = new BigInteger("1000000");
		
		while (num.compareTo(measure) == 1) {
			num = num.divide(new BigInteger("10"));
			exp++;
		}
		
		return num + "E" + exp;
	}

	private void initializeMainFrame() {
		
		JTextPane headline = new JTextPane();
		headline.setText("THE DSA ALGORITHM");
		headline.setEditable(false);
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
		headline.setFont(font);
		StyledDocument doc = headline.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		
		headline.setBackground(frame.getBackground());
		frame.add(headline, BorderLayout.NORTH);
		
		JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabs.add("Keygen", keyGenPanel);
		tabs.add("Sign", signPanel);
		tabs.add("Verify", verifyPanel);
		
		frame.add(tabs);
		frame.setVisible(true);
	    frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	private String getFromFileWithRS(String path) throws IOException {
		String everything;
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    List<String> lines = new ArrayList<String>();
		    
		    while (line != null) {
		    	lines.add(line);
		        line = br.readLine();
		    }
		    
		    int index = 0;
		    while (index < lines.size() - 2) {
		    	sb.append(lines.get(index++));
		        sb.append(System.lineSeparator());
		    }
		    lineS = (lines.get(index++));
		    lineR = (lines.get(index));
		    
		    everything = sb.toString();
		} finally {
		    br.close();
		}
		return everything;
	}

	public static void main(String[] args) {
		
		new Gui();

	}

}
