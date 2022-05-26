import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.io.IOException;


   
    

public class GUI extends JFrame  {

	private JLabel BackGround;;
	private JTextField textFieldCrews;
	private JTextField textFieldWorkTime;

	public static void main(String[] args) {     //running the gui function
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

		 //create the frame with	 
	public GUI() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      //set the frame
		setSize(700,700);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		ImageIcon icon = new ImageIcon("data\\pizzaPic3.jpg");  //set the background
		BackGround = new JLabel("",icon,JLabel.CENTER);
		BackGround.setSize(700, 700);
		add(BackGround);
				
		JLabel headLine= new JLabel("JAVA Pizza & Coffee");  //set headline label
		headLine.setFont(new Font("Arial", Font.BOLD, 25));
		headLine.setBounds(170, 35, 322, 29);
		BackGround.add(headLine);
			
		JLabel lblNumberOfPizzaGuys= new JLabel("Number of Pizza Guys:");                 //set  the label of pizza guys number
		lblNumberOfPizzaGuys.setFont(new Font("Times New Roman ", Font.BOLD, 20));
		lblNumberOfPizzaGuys.setBounds(33, 108, 290, 29);
		BackGround.add(lblNumberOfPizzaGuys );

		JLabel kitchenWorkingTime= new JLabel("Kitchen Workers working time:");     //set  the label of the kitchen workers
		kitchenWorkingTime.setFont(new Font("Times New Roman ", Font.BOLD, 20));
		kitchenWorkingTime.setBounds(340, 108, 350, 29);
		BackGround.add(kitchenWorkingTime);
 
		textFieldCrews = new JTextField();               //set the insert label of the pizza guys number
		textFieldCrews.setText("1");
		textFieldCrews.setBounds(77, 148, 86, 20);
		BackGround.add(textFieldCrews);
		textFieldCrews.setColumns(10);

		textFieldWorkTime = new JTextField();          //set the insert label of the kitchen workers time
		textFieldWorkTime.setText("2");
		textFieldWorkTime.setBounds(397, 148, 86, 20);
		BackGround.add(textFieldWorkTime);
		textFieldWorkTime.setColumns(10);

	//create start and exit button	
	
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numberOfPizzaGuys = 2;
				double timeOfCooking = 1;

				String techsStr = textFieldCrews.getText();
				String timeStr = textFieldWorkTime.getText();

	

				numberOfPizzaGuys = Integer.parseInt(techsStr);
				timeOfCooking = Double.parseDouble(timeStr);

			
				Restaurant rest = new Restaurant(numberOfPizzaGuys,timeOfCooking);
				rest.startWork();	
			}

		
		});
		btnStart.setBounds(74, 281, 89, 23);
		BackGround.add(btnStart);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(397, 281, 89, 23);
		BackGround.add(btnExit);
	}
}