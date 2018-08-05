package buaa.act;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Container;
import java.awt.ScrollPane;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class test {

	private JFrame frame;
	private JTextField inputQuery;
	private JTextArea txtCode;
	private JComboBox comboBox;
	private JTextField lblCode;
	private JButton btnSearch;
	private JTextField lblDoc;
	private JTextArea txtDoc;
	private JTextField lblUsage;
	private JTextArea txtUsage;
	private JTextField txtQuery;
	private JButton btnNewButton;
	private JTextField outputRecommend;
	private int queryId;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App swing = new App();
//					swing.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public test() {
		initialize();
	}

	private void initialize() {
		ApiWork aw0 = new ApiWork();
		
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 680, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnSearch = new JButton("");
		btnSearch.setContentAreaFilled(false);
		btnSearch.setOpaque(true);
		btnSearch.setIcon(new ImageIcon(App.class.getResource("/buaa/search.png")));
	    btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				queryId = 0; //第一个查询的
				
				String queryString = inputQuery.getText();
				String[] ans_test = new String[5];
				String selectedItem = comboBox.getSelectedItem().toString();
				System.out.println(selectedItem);	
				
				ans_test = aw0.search2(queryString, selectedItem, queryId);
				System.out.println("Usage is:"+ans_test[0]); //usage
				System.out.println("Sentence is:"+ans_test[1]); //sentence
				System.out.println("Doc is:"+ans_test[2]); //doc
				System.out.println("Code is:"+ans_test[3]); //code
				
				if (ans_test[2] == null || ans_test[2].equals("")){
					JOptionPane.showMessageDialog(null, "No answer.\nPlease reinput a query.");
				}
			
				txtUsage.setText(ans_test[0]);
//				txtSentence.setText(ans_test[1]);
				txtDoc.setText(ans_test[2]);
				txtCode.setText(ans_test[3]); //buffer.toString()
				
				System.out.println("-----------------------");
				
//				System.out.println(Class.getResource("/"));
			}
		});
		btnSearch.setBounds(486, 30, 40, 40);
		frame.getContentPane().add(btnSearch);
		
		inputQuery = new JTextField();
		inputQuery.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { //keyTyped
//				String queryString = inputQuery.getText();
//				
//				String selectedItem = comboBox.getSelectedItem().toString();
//				
//				String ans_recommend = aw0.search4(queryString, selectedItem);
//				
//				outputRecommend.setText(ans_recommend);
				
				
				String queryString = inputQuery.getText();
				String[] ans_test = new String[5];
				String selectedItem = comboBox.getSelectedItem().toString();
				System.out.println(selectedItem);	
				
				String ans_recommend = aw0.search4(queryString, selectedItem);
				
				outputRecommend.setText(ans_recommend);
				
				ans_test = aw0.search2(queryString, selectedItem, queryId);
				System.out.println("Usage is:"+ans_test[0]); //usage
				System.out.println("Sentence is:"+ans_test[1]); //sentence
				System.out.println("Doc is:"+ans_test[2]); //doc
				System.out.println("Code is:"+ans_test[3]); //code
				
				if (ans_test[2] == null || ans_test[2].equals("")){
//					JOptionPane.showMessageDialog(null, "No answer.\nPlease reinput a query.");
				}
			
				txtUsage.setText(ans_test[0]);
//				txtSentence.setText(ans_test[1]);
				txtDoc.setText(ans_test[2]);
				txtCode.setText(ans_test[3]); //buffer.toString()
				
				System.out.println("-----------------------");
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				 if(e.getKeyCode() == KeyEvent.VK_ENTER){ //判断按下的键是否是回车键
					 	queryId = 0; //第一个查询的
					 
						String queryString = inputQuery.getText();
						String[] ans_test = new String[5];
						String selectedItem = comboBox.getSelectedItem().toString();
						System.out.println(selectedItem);	
						
						ans_test = aw0.search2(queryString, selectedItem, queryId);
						System.out.println("Usage is:"+ans_test[0]); //usage
						System.out.println("Sentence is:"+ans_test[1]); //sentence
						System.out.println("Doc is:"+ans_test[2]); //doc
						System.out.println("Code is:"+ans_test[3]); //code
						
						if (ans_test[2] == null || ans_test[2].equals("")){
//							JOptionPane.showMessageDialog(null, "No answer.\nPlease reinput a query.");
						}
					
						txtUsage.setText(ans_test[0]);
//						txtSentence.setText(ans_test[1]);
						txtDoc.setText(ans_test[2]);
						txtCode.setText(ans_test[3]); //buffer.toString()
						
						System.out.println("-----------------------");
					 
				 }
			}
		});
		inputQuery.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		inputQuery.setBounds(95, 39, 219, 26);
		frame.getContentPane().add(inputQuery);
		inputQuery.setColumns(10);
		
//		frame.getContentPane().add(usageAns);
		
		JScrollPane scrollPaneCode = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneCode.setBounds(227, 312, 379, 260);
		frame.getContentPane().add(scrollPaneCode);
		
		txtCode = new JTextArea();
		scrollPaneCode.setViewportView(txtCode);
		txtCode.setBackground(Color.LIGHT_GRAY);
		txtCode.setLineWrap(true);
		
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		comboBox.setBounds(326, 40, 132, 27);
		comboBox.addItem("Doc");
		comboBox.addItem("Code");
		comboBox.addItem("Usage");
//		comboBox.addItem("Sentence");
		frame.getContentPane().add(comboBox);
		
		lblCode = new JTextField();
		lblCode.setOpaque(true);
		lblCode.setForeground(Color.BLACK);
		lblCode.setEditable(false);
		lblCode.setText("Code");
		lblCode.setBounds(35, 338, 130, 26);
		frame.getContentPane().add(lblCode);
		lblCode.setColumns(10);
		lblCode.setHorizontalAlignment(JTextField.CENTER);
		
		lblDoc = new JTextField();
		lblDoc.setText("Doc");
		lblDoc.setOpaque(true);
		lblDoc.setForeground(Color.BLACK);
		lblDoc.setEditable(false);
		lblDoc.setColumns(10);
		lblDoc.setBounds(35, 133, 130, 26);
		lblDoc.setHorizontalAlignment(JTextField.CENTER);
		
		frame.getContentPane().add(lblDoc);
		
		txtDoc = new JTextArea();
		txtDoc.setLineWrap(true);
		txtDoc.setBackground(Color.LIGHT_GRAY);
		txtDoc.setBounds(227, 118, 376, 40);
		frame.getContentPane().add(txtDoc);
		
		lblUsage = new JTextField();
		lblUsage.setText("Usage");
		lblUsage.setOpaque(true);
		lblUsage.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsage.setForeground(Color.BLACK);
		lblUsage.setEditable(false);
		lblUsage.setColumns(10);
		lblUsage.setBounds(35, 222, 130, 26);
		frame.getContentPane().add(lblUsage);
		
		JScrollPane scrollPaneUsage = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneUsage.setBounds(229, 203, 377, 72);
		frame.getContentPane().add(scrollPaneUsage);
		
		txtUsage = new JTextArea();
		scrollPaneUsage.setViewportView(txtUsage);
		txtUsage.setBackground(Color.LIGHT_GRAY);
		txtUsage.setLineWrap(true);
		
		txtQuery = new JTextField();
		txtQuery.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtQuery.setBorder(null);
		txtQuery.setDisabledTextColor(Color.WHITE);
		txtQuery.setIgnoreRepaint(true);
		txtQuery.setSelectionColor(Color.WHITE);
		txtQuery.setOpaque(true);
		txtQuery.setText("Query:");
		txtQuery.setHorizontalAlignment(SwingConstants.CENTER);
		txtQuery.setForeground(Color.BLACK);
		txtQuery.setEditable(false);
		txtQuery.setColumns(10);
		txtQuery.setBounds(35, 39, 60, 26);
		frame.getContentPane().add(txtQuery);
		
		btnNewButton = new JButton("Next");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String queryString = inputQuery.getText();
				String[] ans_test = new String[5];
				String selectedItem = comboBox.getSelectedItem().toString();
				System.out.println(selectedItem);	
				
				queryId ++;
				
				ans_test = aw0.search2(queryString, selectedItem, queryId);
				System.out.println("Usage is:"+ans_test[0]); //usage
				System.out.println("Sentence is:"+ans_test[1]); //sentence
				System.out.println("Doc is:"+ans_test[2]); //doc
				System.out.println("Code is:"+ans_test[3]); //code
				
				if (ans_test[2] == null || ans_test[2].equals("")){
					JOptionPane.showMessageDialog(null, "No answer.\nPlease reinput a query.");
				}
			
				txtUsage.setText(ans_test[0]);
//				txtSentence.setText(ans_test[1]);
				txtDoc.setText(ans_test[2]);
				txtCode.setText(ans_test[3]); //buffer.toString()
				
				System.out.println("-----------------------");
			}
		});
		btnNewButton.setBounds(246, 612, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		outputRecommend = new JTextField();
		outputRecommend.setOpaque(true);
		outputRecommend.setHorizontalAlignment(SwingConstants.CENTER);
		outputRecommend.setForeground(Color.BLACK);
		outputRecommend.setEditable(false);
		outputRecommend.setColumns(10);
		outputRecommend.setBounds(95, 77, 179, 24);
		frame.getContentPane().add(outputRecommend);
		
		/*txtUsage = new JTextArea();
		txtUsage.setLineWrap(true);
		txtUsage.setBackground(Color.LIGHT_GRAY);
		txtUsage.setBounds(227, 287, 331, 39);
		frame.getContentPane().add(txtUsage);*/
	}
}
