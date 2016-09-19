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
import java.util.List;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.border.Border;

public class Gui {
	
	private JFrame frame;
	private JPanel keyGenPanel;
	private JPanel signPanel;
	private JPanel verifyPanel;
	//private JTextArea currentPublicKey;
	//private JTextField alias1;
	//private JTextField alias2;
	
	//private JTextField txtPrimeNumber;
	//private JTextField textField;
	//private JTextField textField_1;
	
	//private boolean signed = false;
	protected String currentMessage;
	
	private static MyDSA myDsa = new MyDSA();

	public Gui() {
		
		initializeKeyGenTab();
		initializeSignFrame();
		initializeVerifyFrame();
		initializeMainFrame();
	}
	
	private void initializeVerifyFrame() {
		// TODO Auto-generated method stub
		verifyPanel = new JPanel(new GridLayout(2,1));
		VerifyPanel verifyTempPanel = new VerifyPanel();
		verifyPanel.add(verifyTempPanel);
		
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
		downPanel.add(btnLoadM);
		
		JTextField parPath = new JTextField();
		parPath.setBounds(60, 200, 330, 20);
		downPanel.add(parPath);
		
		JLabel lblLoadParamsFrom = new JLabel("Load parameters from file");
		lblLoadParamsFrom.setBounds(60, 175, 308, 14);
		downPanel.add(lblLoadParamsFrom);
		
		JButton btnLoadPar = new JButton("Load");
		btnLoadPar.setBounds(400, 200, 110, 20);
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
		
		JTextField comboBox = new JTextField();
		comboBox.setBounds(80, 188, 229, 23);
		keyGenPanel.add(comboBox);
		
		txtPrimeNumber = new JTextField();
		txtPrimeNumber.setText("prime number");
		txtPrimeNumber.setEditable(false);
		txtPrimeNumber.setBounds(378, 188, 212, 25);
		keyGenPanel.add(txtPrimeNumber);
		txtPrimeNumber.setColumns(10);
		
		JLabel label = new JLabel("q:");
		label.setBounds(10, 265, 60, 25);
		keyGenPanel.add(label);
		
		JTextField comboBox_1 = new JTextField();
		comboBox_1.setBounds(80, 267, 229, 23);
		keyGenPanel.add(comboBox_1);
		
		JLabel label_1 = new JLabel("g:");
		label_1.setBounds(10, 345, 60, 25);
		keyGenPanel.add(label_1);
		
		JTextField comboBox_2 = new JTextField();
		comboBox_2.setBounds(80, 347, 229, 23);
		keyGenPanel.add(comboBox_2);
		
		textField = new JTextField();
		textField.setText("prime divisor");
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(378, 267, 212, 25);
		keyGenPanel.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setText("h^((p-1)/q) mod q");
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(378, 347, 212, 25);
		keyGenPanel.add(textField_1);
		
		JTextArea txtrPublicKey = new JTextArea();
		txtrPublicKey.setText("Keys:\n");
		txtrPublicKey.setEditable(false);
		txtrPublicKey.setBounds(10, 406, 580, 150);
		keyGenPanel.add(txtrPublicKey);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(254, 616, 89, 23);

		keyGenPanel.add(btnSave);
		
		JLabel lblSaveAs = new JLabel("Save as...");
		lblSaveAs.setBounds(144, 568, 130, 25);
		keyGenPanel.add(lblSaveAs);
		
		JTextField textField_2 = new JTextField();
		textField_2.setBounds(219, 570, 220, 20);
		textField_2.setText("Your path");
		keyGenPanel.add(textField_2);
		textField_2.setColumns(10);

		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveParameters(textField_2.getText());
			}
		});

		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String test = comboBox.getText();
				try {
					myDsa.generateParams(test);
				} 
				catch(Exception ex) {
					JOptionPane.showMessageDialog(frame, "Please enter a number for value p!");
				}
				comboBox.setText(myDsa.p.toString());
				comboBox_1.setText(myDsa.q.toString());
				comboBox_2.setText(myDsa.g.toString());
				txtrPublicKey.setText("Keys:\n");
				txtrPublicKey.append("Private key:\n");
				txtrPublicKey.append("x = " + myDsa.x.toString() + "\n\n");
				txtrPublicKey.append("Session key:\n");
				txtrPublicKey.append("k = " + myDsa.k.toString() + "\n\n");
				txtrPublicKey.append("Public key:\n");
				txtrPublicKey.append("y = " + myDsa.y.toString());
				
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
		
		JTextArea enterMessage = new JTextArea("Unesite poruku za potpis ovde...");
		enterMessage.setBounds(5,0,305,115);
		JPanel enterMessageTempPanel = new JPanel();
		enterMessageTempPanel.setLayout(null);
		enterMessageTempPanel.add(enterMessage);
		JPanel downTempSignPanel = new JPanel();
		downTempSignPanel.setLayout(new GridLayout(1, 2));
		
		//=================================================================================
		//left down panel initialization
		JPanel leftDownTempSignPanel = new JPanel();
		leftDownTempSignPanel.setLayout(null);
		
		JTextArea txtrEnterYourMessage = new JTextArea();
		txtrEnterYourMessage.setText("Enter your message here");
		txtrEnterYourMessage.setBounds(0, 0, 305, 150);
		JScrollPane wrapForEnterMessage = new JScrollPane(txtrEnterYourMessage);
		wrapForEnterMessage.setBounds(0, 0, 305, 150);
		leftDownTempSignPanel.add(wrapForEnterMessage);
		
		JLabel lblLoadMessageFrom = new JLabel("Load message from file");
		lblLoadMessageFrom.setBounds(10, 160, 195, 14);
		leftDownTempSignPanel.add(lblLoadMessageFrom);
		
		JLabel lblSaveMessage = new JLabel("Save signed message");
		lblSaveMessage.setBounds(10, 160, 195, 14);
		lblSaveMessage.setVisible(false);
		leftDownTempSignPanel.add(lblSaveMessage);
		
		JTextField messagePath = new JTextField();
		messagePath.setBounds(10, 180, 195, 20);
		leftDownTempSignPanel.add(messagePath);
		messagePath.setColumns(10);
		
		JButton btnLoadMsg = new JButton("Load");
		btnLoadMsg.setBounds(215, 180, 85, 20);
		btnLoadMsg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		        try {
		        	txtrEnterYourMessage.setText(getFromFile(messagePath.getText()));
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "There is no file named " + messagePath.getText() + "!");
				}
			}
		});
		leftDownTempSignPanel.add(btnLoadMsg);
		
		JButton btnSaveMsg = new JButton("Save");
		btnSaveMsg.setBounds(215, 180, 85, 20);
		btnSaveMsg.setVisible(false);
		leftDownTempSignPanel.add(btnSaveMsg);
		
		JLabel lblLoadParameterFrom = new JLabel("Load parameters from file");
		lblLoadParameterFrom.setBounds(10, 225, 195, 14);
		leftDownTempSignPanel.add(lblLoadParameterFrom);
		
		JTextField paramPath = new JTextField();
		paramPath.setBounds(10, 250, 195, 20);
		leftDownTempSignPanel.add(paramPath);
		paramPath.setColumns(10);
		
		JButton btnLoadPar = new JButton("Load");
		btnLoadPar.setBounds(215, 250, 85, 20);
		btnLoadPar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					BufferedReader br = new BufferedReader(new FileReader(paramPath.getText()));
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
					JOptionPane.showMessageDialog(frame, "Successfully loaded parameters:\n"
							+ "p = " + myDsa.p + "\n"
							+ "q = " + myDsa.q + "\n"
							+ "g = " + myDsa.g + "\n"
							+ "x = " + myDsa.x + "\n"
							+ "y = " + myDsa.y + "\n"
							+ "k = " + myDsa.k);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "There is no file named " + paramPath.getText() + "!");
				}
			}
		});
		leftDownTempSignPanel.add(btnLoadPar);
		
		JButton btnSignMessage = new JButton("Sign message");
		btnSignMessage.setBounds(50, 305, 190, 20);
		btnSignMessage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (myDsa.p == null)
					JOptionPane.showMessageDialog(frame,
							"You haven't generated or loaded the parameters for signing the message\n"
									+ "Go back to Keygen tab and generate parameters or load your own parameters!");
				else {
					if (txtrEnterYourMessage.getText().equals("Enter your message here")) {
						int confirmResult = JOptionPane.showConfirmDialog(frame,
								"You haven't entered message to be signed!\n"
										+ "Are you sure you want to sign the default message?!");
						if (confirmResult == JOptionPane.YES_OPTION) {
							myDsa.sign(txtrEnterYourMessage.getText());
							txtrEnterYourMessage.setText(txtrEnterYourMessage.getText() + "\n" + myDsa.s + "\n" + myDsa.r);
							String savePath = JOptionPane.showInputDialog(frame, "Your message have been signed!\n Do you want to save it for further verification?!");
							if (savePath != null) {
								String message = txtrEnterYourMessage.getText().replaceAll("\n", System.lineSeparator());
								saveMessage(savePath, message);
							}
							else {
								lblLoadMessageFrom.setVisible(false);
								btnLoadMsg.setVisible(false);
								btnSignMessage.setVisible(false);
								lblLoadParameterFrom.setVisible(false);
								btnLoadPar.setVisible(false);
								paramPath.setVisible(false);
								lblSaveMessage.setVisible(true);
								btnSaveMsg.setVisible(true);
							}
						}
					} else {
						myDsa.sign(txtrEnterYourMessage.getText());
						txtrEnterYourMessage.setText(txtrEnterYourMessage.getText() + "\n" + myDsa.s + "\n" + myDsa.r);
						String savePath = JOptionPane.showInputDialog(frame, "Your message have been signed!\n Do you want to save it for further verification?!");
						if (savePath != null) {
							String message = txtrEnterYourMessage.getText().replaceAll("\n", System.lineSeparator());
							saveMessage(savePath, message);
						}
						else {
							lblLoadMessageFrom.setVisible(false);
							btnLoadMsg.setVisible(false);
							btnSignMessage.setVisible(false);
							lblLoadParameterFrom.setVisible(false);
							btnLoadPar.setVisible(false);
							paramPath.setVisible(false);
							lblSaveMessage.setVisible(true);
							btnSaveMsg.setVisible(true);
						}
					}
						
				}
			}
		});
		leftDownTempSignPanel.add(btnSignMessage);
		
		btnSaveMsg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String message = txtrEnterYourMessage.getText().replaceAll("\n", System.lineSeparator());
				saveMessage(messagePath.getText(), message);
				lblLoadMessageFrom.setVisible(true);
				btnLoadMsg.setVisible(true);
				btnSignMessage.setVisible(true);
				lblLoadParameterFrom.setVisible(true);
				btnLoadPar.setVisible(true);
				paramPath.setVisible(true);
				lblSaveMessage.setVisible(false);
				btnSaveMsg.setVisible(false);
			}
		});
		//=================================================================================

		//=================================================================================
		//right down panel initialization
		JPanel rightDownTempSignPanel = new JPanel();
		rightDownTempSignPanel.setLayout(null);
		
		JLabel lblP = new JLabel("p:");
		lblP.setBounds(10, 0, 20, 14);
		rightDownTempSignPanel.add(lblP);
		
		JLabel lblLargePrimeNumber = new JLabel("large prime number");
		lblLargePrimeNumber.setBounds(40, 0, 106, 14);
		rightDownTempSignPanel.add(lblLargePrimeNumber);
		
		JLabel lblQ = new JLabel("q:");
		lblQ.setBounds(10, 20, 20, 14);
		rightDownTempSignPanel.add(lblQ);
		
		JLabel lblPrimeDivisorOf = new JLabel("prime divisor of p-1");
		lblPrimeDivisorOf.setBounds(40, 20, 92, 14);
		rightDownTempSignPanel.add(lblPrimeDivisorOf);
		
		JLabel lblG = new JLabel("g:");
		lblG.setBounds(10, 40, 20, 14);
		rightDownTempSignPanel.add(lblG);
		
		JLabel lblHpqModP = new JLabel("h^((p-1)/q)  mod  p");
		lblHpqModP.setBounds(40, 40, 255, 14);
		rightDownTempSignPanel.add(lblHpqModP);
		
		JLabel lblX = new JLabel("x:");
		lblX.setBounds(10, 60, 20, 14);
		rightDownTempSignPanel.add(lblX);
		
		JLabel lblPrivateKey = new JLabel("private key");
		lblPrivateKey.setBounds(40, 60, 92, 14);
		rightDownTempSignPanel.add(lblPrivateKey);
		
		JLabel lblY = new JLabel("y:");
		lblY.setBounds(10, 80, 20, 14);
		rightDownTempSignPanel.add(lblY);
		
		JLabel lblPublicKey = new JLabel("public key");
		lblPublicKey.setBounds(40, 80, 106, 14);
		rightDownTempSignPanel.add(lblPublicKey);
		
		JLabel lblK = new JLabel("k:");
		lblK.setBounds(10, 100, 20, 14);
		rightDownTempSignPanel.add(lblK);
		
		JLabel lblSessionKeyUnique = new JLabel("session key unique for every message");
		lblSessionKeyUnique.setBounds(40, 100, 255, 14);
		rightDownTempSignPanel.add(lblSessionKeyUnique);
		
		JLabel lblR = new JLabel("r:");
		lblR.setBounds(10, 120, 20, 14);
		rightDownTempSignPanel.add(lblR);
		
		JLabel lblPartOfThe = new JLabel("part of the signature (f2)");
		lblPartOfThe.setBounds(40, 120, 255, 14);
		rightDownTempSignPanel.add(lblPartOfThe);
		
		JLabel lblS = new JLabel("s:");
		lblS.setBounds(10, 140, 20, 14);
		rightDownTempSignPanel.add(lblS);
		
		JLabel lblPartOfThe_1 = new JLabel("part of the signature (f1)");
		lblPartOfThe_1.setBounds(40, 140, 255, 14);
		rightDownTempSignPanel.add(lblPartOfThe_1);
		
		JTextArea detailsArea = new JTextArea();
		detailsArea.setBackground(SystemColor.controlHighlight);
		detailsArea.setBounds(10, 190, 285, 110);
		detailsArea.setEditable(false);
		JScrollPane wrapForDetailsArea = new JScrollPane(detailsArea);
		wrapForDetailsArea.setBounds(10, 190, 285, 110);
		rightDownTempSignPanel.add(wrapForDetailsArea);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(206, 305, 89, 20);
		btnClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				detailsArea.setText("");
			}
		});
		rightDownTempSignPanel.add(btnClear);
		
		JLabel lblClickTheScheme = new JLabel("Click the scheme above for details...");
		lblClickTheScheme.setBounds(10, 170, 285, 14);
		rightDownTempSignPanel.add(lblClickTheScheme);
		//=================================================================================
		
		signPanelTemp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                for (Shape s : signPanelTemp.shapes) {

                    if (s.contains(me.getPoint())) {//check if mouse is clicked within shape
                    	//Check to see if is a message
                    	if (s.contains(90, 120)) {
                    		//detailsArea.setText(detailsArea.getText() + txtrEnterYourMessage.getText() + "\n");
                    		JFrame showMessage = new JFrame("Message details");
                    		showMessage.setResizable(false);
                    		showMessage.setSize(600, 300);
                    		showMessage.setVisible(true);
                    		showMessage.setLayout(null);
                    		showMessage.setLocation(100, 200);
                    		JTextArea msgArea = new JTextArea();
                    		msgArea.setText(currentMessage);
                    		msgArea.setBounds(0,0,600, 225);
                    		showMessage.add(msgArea);
                    		JButton saveAndClose = new JButton("Save message and close");
                    		saveAndClose.setBounds(200, 230, 200, 25);
                    		showMessage.add(saveAndClose);
                    		saveAndClose.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent arg0) {
									// TODO Auto-generated method stub
									currentMessage = msgArea.getText();
									showMessage.setVisible(false);
								}
							});
                    	    showMessage.addWindowListener(new WindowAdapter() {
                    	        public void windowClosing(WindowEvent e) {
                    		        try {
                    		        	currentMessage = msgArea.getText();
                    			    } catch (Exception e1) {
                    			    	e1.printStackTrace();
                    			    } 
                    	        }
                    	     });
                    	}
	
                    	//hash
                    	if (s.contains(180, 80)) {
                    		JFrame showHash = new JFrame("Hash details");
                    		showHash.setResizable(false);
                    		showHash.setSize(600, 300);
                    		showHash.setVisible(true);
                    		showHash.setLayout(null);
                    		showHash.setLocation(100, 200);
                    		JTextArea msgArea = new JTextArea();
                    		msgArea.setBounds(0,0,600, 300);
                    		msgArea.setEditable(false);
                    		showHash.setBounds(0,0,600, 300);
                    		showHash.add(msgArea);
                    		if (myDsa.p != null && myDsa.hashVal != null) { 
                    			//detailsArea.setText(detailsArea.getText() + "H(M) using SHA-1 = " + myDsa.hashVal + "\n");

                        		if (currentMessage != null)
                        			msgArea.setText("Current message is: \n" + currentMessage + "\n\nHash value (SHA-1) is " + myDsa.hashVal);
                        		else
                        			msgArea.setText("The DSA uses SHA-1 hash algorithm!");
                    		} else { 
                    			//detailsArea.setText(detailsArea.getText() + "Hash algorithm used by DSA is SHA-1" + "\n");
                    			msgArea.setText("The DSA uses SHA-1 hash algorithm!");
                    		}
                    	}
                    	
                    	//f1
                    	if (s.contains(360, 80)) {
                    		JFrame f1Details = new JFrame("Details of f1");
                    		f1Details.setLayout(null);
                    		f1Details.setResizable(false);
                    		f1Details.setSize(600, 300);
                    		f1Details.setVisible(true);
                    		f1Details.setLocation(100, 200);

                    		JTextField txtValuesIs = new JTextField();
                    		txtValuesIs.setText("Value 's' is a part of the signature calculated:");
                    		txtValuesIs.setBounds(10, 11, 231, 25);
                    		f1Details.add(txtValuesIs);
                    		txtValuesIs.setColumns(10);
                    		txtValuesIs.setEditable(false);
                    		txtValuesIs.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                    		
                    		JLabel lblSK = new JLabel("k  * (Hash(Msg) + x*r))  mod  q  =  s");
                    		lblSK.setBounds(170, 47, 360, 25);
                    		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
                    		lblSK.setFont(font);
                    		f1Details.add(lblSK);
                    		
                    		JLabel label = new JLabel("-1");
                    		label.setBounds(178, 45, 17, 14);
                    		font = new Font(Font.SANS_SERIF, Font.BOLD, 8);
                    		label.setFont(font);
                    		f1Details.add(label);
                    		
                    		JTextField txtCalculation = new JTextField();
                    		txtCalculation.setText("Calculation:");
                    		txtCalculation.setBounds(10, 96, 86, 20);
                    		f1Details.add(txtCalculation);
                    		txtCalculation.setColumns(10);
                    		txtCalculation.setEditable(false);
                    		txtCalculation.setVisible(false);
                    		txtCalculation.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                    		
                    		JTextField calculationSpace = new JTextField();
                    		calculationSpace.setBounds(30, 127, 580, 50);
                    		font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
                    		calculationSpace.setFont(font);
                    		f1Details.add(calculationSpace);
                    		calculationSpace.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                    		calculationSpace.setEditable(false);
                    		calculationSpace.setColumns(10);
                    		
                    		if (myDsa.p != null && myDsa.hashVal != null) {
                    			calculationSpace.setText("(" + myDsa.kInv + " * (" + giveMeExp(myDsa.hashVal) + " + " 
                    					+ myDsa.x + " * " + myDsa.r + " )) mod " + myDsa.q + " = " + myDsa.s + "\n");
                    			txtCalculation.setVisible(true);
                    		}

                    	}
                    	//f2
                    	if (s.contains(190, 220))
                    		if (myDsa.p != null)
                    			detailsArea.setText(detailsArea.getText() + "(" + myDsa.g + "^" + myDsa.k + " mod "
                    					+ myDsa.p + ") mod " + myDsa.q + " = " + myDsa.r + "\n");
                    		else
                    			detailsArea.setText(detailsArea.getText() + "r = (g^k mod p) mod q \n");
                    }
                }
            }
        });
		
		//downTempSignPanel.add(leftDownTempSignPanel);
		//downTempSignPanel.add(rightDownTempSignPanel);
		
		//signPanel.add(downTempSignPanel);
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
		        	currentMessage = getFromFile(msgPath.getText());
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
					if (currentMessage == null) {
						int confirmResult = JOptionPane.showConfirmDialog(frame,
								"You haven't entered message to be signed!\n"
										+ "Are you sure you want to sign the default message?!");
						if (confirmResult == JOptionPane.YES_OPTION) {
							currentMessage = "Auto generated message";
							myDsa.sign(currentMessage);
							currentMessage += "\n" + myDsa.s + "\n" + myDsa.r;
							String savePath = JOptionPane.showInputDialog(frame, "Your message have been signed!\n Do you want to save it for further verification?!");
							if (savePath != null) {
								String message = currentMessage.replaceAll("\n", System.lineSeparator());
								saveMessage(savePath, message);
							}
							else {
//								lblLoadMessageFrom.setVisible(false);
//								btnLoadMsg.setVisible(false);
//								btnSignMessage.setVisible(false);
//								lblLoadParameterFrom.setVisible(false);
//								btnLoadPar.setVisible(false);
//								paramPath.setVisible(false);
//								lblSaveMessage.setVisible(true);
//								btnSaveMsg.setVisible(true);
								btnLoadParam.setVisible(false);
								lblLoadParamsFrom.setVisible(false);
								parPath.setVisible(false);
								lblLoadMsgFrom.setVisible(false);
								btnLoadM.setVisible(false);
								lblSaveMsgTo.setVisible(true);
								btnSaveM.setVisible(true);
							}
						}
					} else {
						myDsa.sign(currentMessage);
						currentMessage += "\n" + myDsa.s + "\n" + myDsa.r;
						String savePath = JOptionPane.showInputDialog(frame, "Your message have been signed!\n Do you want to save it for further verification?!");
						if (savePath != null) {
							String message = currentMessage.replaceAll("\n", System.lineSeparator());
							saveMessage(savePath, message);
						}
						else {
//							lblLoadMessageFrom.setVisible(false);
//							btnLoadMsg.setVisible(false);
//							btnSignMessage.setVisible(false);
//							lblLoadParameterFrom.setVisible(false);
//							btnLoadPar.setVisible(false);
//							paramPath.setVisible(false);
//							lblSaveMessage.setVisible(true);
//							btnSaveMsg.setVisible(true);
							btnLoadParam.setVisible(false);
							lblLoadParamsFrom.setVisible(false);
							parPath.setVisible(false);
							lblLoadMsgFrom.setVisible(false);
							btnLoadM.setVisible(false);
							lblSaveMsgTo.setVisible(true);
							btnSaveM.setVisible(true);
						}
					}

				}
			}
		});
		btnSaveM.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String message = currentMessage.replaceAll("\n", System.lineSeparator());
				saveMessage(msgPath.getText(), message);
				msgPath.setText("");
				btnLoadParam.setVisible(true);
				lblLoadParamsFrom.setVisible(true);
				parPath.setVisible(true);
				lblLoadMsgFrom.setVisible(true);
				btnLoadM.setVisible(true);
				lblSaveMsgTo.setVisible(false);
				btnSaveM.setVisible(false);
			}
		});

		newDownPanel.add(btnSign);
		
		signPanel.add(newDownPanel);
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
		
		frame = new JFrame("DSA Algorithm");
		frame.setSize(620, 750);
		frame.setLocation(100, 0);
		frame.setLayout(new BorderLayout());
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
	

	public static void main(String[] args) {
		
		new Gui();

	}

}
