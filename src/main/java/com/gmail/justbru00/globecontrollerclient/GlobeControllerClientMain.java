package com.gmail.justbru00.globecontrollerclient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Copyright 2018 Justin "JustBru00" Brubaker.
 * 
 * @author Justin Brubaker
 *
 */
public class GlobeControllerClientMain {

	private static JFrame frame;
	private JButton btnBothOff;
	private JButton btnBlueOn;
	private JButton btnBlueOff;
	private JButton btnWhiteOn;
	private JButton btnWhiteOff;
	private JButton btnBothOn;
	
	private static BufferedReader in;
    private static PrintWriter out;
    private static JLabel status;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new GlobeControllerClientMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void connectToServer() {
		// Get the server address from a dialog box.
        String serverAddress = JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:");
        
     // Make connection and initialize streams
        Socket socket;
		try {
			socket = new Socket(serverAddress, 2018);
			 in = new BufferedReader(
		                new InputStreamReader(socket.getInputStream()));
		     out = new PrintWriter(socket.getOutputStream(), true);
		        
		     String welcome = in.readLine();
		     
		     status.setText("->" + welcome);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Couldn't connect to server!", "Failed to connect to server!", JOptionPane.ERROR_MESSAGE);
		}
       
	}
	
	public void sendCommand(String cmd) {
		out.println(cmd);
		
		String response = "";
		try {
			response = in.readLine();
			if (response == null || response.equals("")) {
				status.setText("RECEIVED NOTHING FROM SERVER");
			}
		} catch (IOException e) {
			status.setText("AN ERROR OCCUCRED");
		}
		
		status.setText("-> " + response);
	}

	/**
	 * Create the application.
	 */
	public GlobeControllerClientMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg) {
				// Window has been resized
				
			}
		});
		frame.setBounds(100, 100, 450, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel topAreaPanel = new JPanel();
		mainPanel.add(topAreaPanel);
		topAreaPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		topAreaPanel.add(panel);
		
		status = new JLabel("NOT YET CONNECTED");
		status.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel.add(status);
		
		JPanel panel_1 = new JPanel();
		topAreaPanel.add(panel_1);
		
		JButton btnDisconnect = new JButton("DISCONNECT");
		btnDisconnect.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to disconnect?");
				
				if (choice == JOptionPane.OK_OPTION) {
					sendCommand("DISCONNECT");
				}
			}
		});
		panel_1.add(btnDisconnect);
		
		JPanel panel_2 = new JPanel();
		topAreaPanel.add(panel_2);
		
		JButton btnConnect = new JButton("CONNECT");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectToServer();
			}
		});
		btnConnect.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel_2.add(btnConnect);
		
		JPanel bottomAreaPanel = new JPanel();
		mainPanel.add(bottomAreaPanel);
		
		JPanel bluePanel = new JPanel();
		bottomAreaPanel.add(bluePanel);
		bluePanel.setLayout(new BoxLayout(bluePanel, BoxLayout.Y_AXIS));
		
		JPanel blueOnPanel = new JPanel();
		bluePanel.add(blueOnPanel);
		
		btnBlueOn = new JButton("BLUE ON");
		btnBlueOn.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnBlueOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendCommand("BLUEON");				
			}
		});
		btnBlueOn.setPreferredSize(new Dimension(200, 200));
		blueOnPanel.add(btnBlueOn);
		
		JPanel blueOffPanel = new JPanel();
		bluePanel.add(blueOffPanel);
		
		btnBlueOff = new JButton("BLUE OFF");
		btnBlueOff.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnBlueOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendCommand("BLUEOFF");
			}
		});
		btnBlueOff.setPreferredSize(new Dimension(200, 200));
		blueOffPanel.add(btnBlueOff);
		
		JPanel specialPanel = new JPanel();
		bottomAreaPanel.add(specialPanel);
		specialPanel.setLayout(new BoxLayout(specialPanel, BoxLayout.Y_AXIS));
		
		JPanel blueonwhiteoffPanel = new JPanel();
		specialPanel.add(blueonwhiteoffPanel);
		
		JButton btnBlueOnwhiteOff = new JButton("BLUE ON/WHITE OFF");
		btnBlueOnwhiteOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendCommand("BLUEONWHITEOFF");
			}
		});
		btnBlueOnwhiteOff.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnBlueOnwhiteOff.setPreferredSize(new Dimension(350, 200));
		blueonwhiteoffPanel.add(btnBlueOnwhiteOff);
		
		JPanel blueoffwhiteonPanel = new JPanel();
		specialPanel.add(blueoffwhiteonPanel);
		
		JButton btnBlueOffwhiteOn = new JButton("BLUE OFF/WHITE ON");
		btnBlueOffwhiteOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendCommand("BLUEOFFWHITEON");
			}
		});
		btnBlueOffwhiteOn.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnBlueOffwhiteOn.setPreferredSize(new Dimension(350, 200));
		blueoffwhiteonPanel.add(btnBlueOffwhiteOn);
		
		JPanel whitePanel = new JPanel();
		bottomAreaPanel.add(whitePanel);
		whitePanel.setLayout(new BoxLayout(whitePanel, BoxLayout.Y_AXIS));
		
		JPanel whiteOnPanel = new JPanel();
		whitePanel.add(whiteOnPanel);
		
		btnWhiteOn = new JButton("WHITE ON");
		btnWhiteOn.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnWhiteOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendCommand("WHITEON");
			}
		});
		btnWhiteOn.setPreferredSize(new Dimension(200, 200));
		whiteOnPanel.add(btnWhiteOn);
		
		JPanel whiteOffPanel = new JPanel();
		whitePanel.add(whiteOffPanel);
		
		btnWhiteOff = new JButton("WHITE OFF");
		btnWhiteOff.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnWhiteOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendCommand("WHITEOFF");
			}
		});
		btnWhiteOff.setPreferredSize(new Dimension(200, 200));
		whiteOffPanel.add(btnWhiteOff);
		
		JPanel bothPanel = new JPanel();
		bottomAreaPanel.add(bothPanel);
		bothPanel.setLayout(new BoxLayout(bothPanel, BoxLayout.X_AXIS));
		
		JPanel bothOnPanel = new JPanel();
		bothPanel.add(bothOnPanel);
		
		btnBothOn = new JButton("BOTH ON");
		btnBothOn.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnBothOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendCommand("BOTHON");
			}
		});
		btnBothOn.setPreferredSize(new Dimension(200, 200));
		bothOnPanel.add(btnBothOn);
		
		JPanel bothOffPanel = new JPanel();
		bothPanel.add(bothOffPanel);
		
		btnBothOff = new JButton("BOTH OFF");
		btnBothOff.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnBothOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendCommand("BOTHOFF");
			}
		});
		btnBothOff.setPreferredSize(new Dimension(200, 200));
		bothOffPanel.add(btnBothOff);
		
	}

}
