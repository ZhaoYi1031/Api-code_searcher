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
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.ScrollPane;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class App {

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
	private JTextArea cntPatterns;
	private JPanel panel;
	private JTextArea textHead;
	private JScrollPane scrollPaneLeft;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App swing = new App();
					swing.frame.setVisible(true);
					swing.frame.revalidate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public App() {
		initialize();
	}

	public static Button getButton(String name){
		Button button = new Button(name);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0){
				System.out.println("HASAKI");
			}
		});
		
		return button;
	}
	
	public static JTextArea getJTextAreaMin(String name){
		JTextArea jtextArea = new JTextArea(name);
		jtextArea.setLineWrap(true);
		jtextArea.setColumns(4);
		return jtextArea;
	}
	
	public static JTextArea getJTextArea(String name){
		JTextArea jtextArea = new JTextArea(name);
		jtextArea.setLineWrap(true);
		jtextArea.setColumns(19);
		/*button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0){
				System.out.println("HASAKI");
			}
		});*/
		return jtextArea;
	}
	
	
	private void initialize() {
		ApiWork aw0 = new ApiWork();
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 865, 700);
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
		btnSearch.setBounds(641, 40, 40, 40);
		frame.getContentPane().add(btnSearch);
		
		inputQuery = new JTextField();
		inputQuery.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { //keyTyped
//				String queryString = inputQuery.getText();
//				String selectedItem = comboBox.getSelectedItem().toString();
//				String ans_recommend = aw0.search4(queryString, selectedItem);
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
					 
//						public void actionPerformed(ActionEvent e) 
//							String queryString = inputQuery.getText();
							
						panel.removeAll();
						
						//开始进行代码的推荐
						
						ArrayList<String> usages = aw0.search3(queryString, 10);//choose the most 10usages
							
						Hierarchical hi = new Hierarchical();
						String clusterAns = hi.searchWork(usages, aw0.scores);
						Map mapElements = new HashMap();
							
						System.out.println("usage="+clusterAns);
							
						if (clusterAns != null && clusterAns != "")	{
							boolean used[] = new boolean[15];
							
							Arrays.fill(used, false);
							int cnt = 0;
							for (int i = 0; i < 10; ++i){
								if (hi.belong[i] >=0 && hi.belong[i] < 10 && !used[hi.belong[i]]){
									used[hi.belong[i]] = true;
									cnt ++;
								}
							}
							cntPatterns.setText(String.valueOf(cnt));
							List<S1> s1Set = new ArrayList<S1>();
					       	S1 ss;
							for (int i = 0; i < 10; ++i)
							if (used[i]){
								ss = new S1(i, hi.scoreAll[i]);
								s1Set.add(ss);
							}
							int len = s1Set.size();
						     //对容器进行排序的函数
						    Collections.sort(s1Set);
					       	Iterator<S1> it = s1Set.iterator();
					       	while(it.hasNext()){
					       		System.out.println();
					       		S1 ss1 = it.next();
//					       		System.out.println(ss1.getX()+"    "+ss1.getY());
					       		panel.add(getButton(String.valueOf((int)ss1.getY())));
					       		String us = "";
					       		for (int i = 0; i < 10; ++i){
					       			if (hi.belong[i] == ss1.getX()){
					       				String tmp = hi.uss.get(i);
					       				tmp = tmp.substring(0, Math.min(36, tmp.length()));
					       				us = us + tmp  + "\n";
					       			}
					       		}
					       		panel.add(getJTextArea(String.valueOf(us)));
//					       		panel.add(getButton(String.valueOf(us))); //String.valueOf(clusterAns)
								panel.revalidate();
					       	}
						}

						
				 }
			}
		});
		inputQuery.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		inputQuery.setBounds(89, 45, 338, 26);
		frame.getContentPane().add(inputQuery);
		inputQuery.setColumns(10);
		
//		frame.getContentPane().add(usageAns);
		
		JScrollPane scrollPaneCode = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCode.setBounds(461, 323, 379, 286);
		frame.getContentPane().add(scrollPaneCode);
		
		txtCode = new JTextArea();
		scrollPaneCode.setViewportView(txtCode);
		txtCode.setBackground(Color.LIGHT_GRAY);
		txtCode.setLineWrap(true);
		
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		comboBox.setBounds(461, 45, 132, 27);
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
		lblCode.setBounds(283, 413, 130, 26);
		frame.getContentPane().add(lblCode);
		lblCode.setColumns(10);
		lblCode.setHorizontalAlignment(JTextField.CENTER);
		
		lblDoc = new JTextField();
		lblDoc.setText("Doc");
		lblDoc.setOpaque(true);
		lblDoc.setForeground(Color.BLACK);
		lblDoc.setEditable(false);
		lblDoc.setColumns(10);
		lblDoc.setBounds(283, 152, 130, 26);
		lblDoc.setHorizontalAlignment(JTextField.CENTER);
		
		frame.getContentPane().add(lblDoc);
		
		txtDoc = new JTextArea();
		txtDoc.setLineWrap(true);
		txtDoc.setBackground(Color.LIGHT_GRAY);
		txtDoc.setBounds(461, 136, 376, 40);
		frame.getContentPane().add(txtDoc);
		
		lblUsage = new JTextField();
		lblUsage.setText("Usage");
		lblUsage.setOpaque(true);
		lblUsage.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsage.setForeground(Color.BLACK);
		lblUsage.setEditable(false);
		lblUsage.setColumns(10);
		lblUsage.setBounds(283, 228, 130, 26);
		frame.getContentPane().add(lblUsage);
		
		JScrollPane scrollPaneUsage = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneUsage.setBounds(461, 211, 377, 72);
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
		txtQuery.setBounds(18, 45, 60, 26);
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
		btnNewButton.setBounds(283, 630, 183, 29);
		frame.getContentPane().add(btnNewButton);
		
		outputRecommend = new JTextField();
		outputRecommend.setOpaque(true);
		outputRecommend.setHorizontalAlignment(SwingConstants.CENTER);
		outputRecommend.setForeground(Color.BLACK);
		outputRecommend.setEditable(false);
		outputRecommend.setColumns(10);
		outputRecommend.setBounds(89, 77, 338, 26);
		frame.getContentPane().add(outputRecommend);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(234, 454));
//		panel.setBounds(17, 147, 234, 454);
//		frame.getContentPane().add(panel);
		panel.setLayout(new FlowLayout());
		
		scrollPaneLeft = new JScrollPane(panel);
		scrollPaneLeft.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneLeft.setToolTipText("ToolBar");
		scrollPaneLeft.setBounds(17, 147, 234, 454);
//		scrollPaneLeft.add(panel);
		scrollPaneLeft.setViewportView(panel);
		
		frame.getContentPane().add(scrollPaneLeft);
//		this.panel.add(scrollPaneLeft);
		
		
		JTextArea txtrPatterns = new JTextArea();
		txtrPatterns.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtrPatterns.setText("Usage Patterns:");
		txtrPatterns.setBounds(18, 119, 132, 16);
		frame.getContentPane().add(txtrPatterns);
		
		cntPatterns = new JTextArea();
		cntPatterns.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		cntPatterns.setBounds(155, 120, 40, 16);
		frame.getContentPane().add(cntPatterns);
		
		textHead = new JTextArea();
		textHead.setEditable(false);
		textHead.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 24));
		textHead.setForeground(UIManager.getColor("Button.background"));
		textHead.setText("   API-Code Searcher");
		textHead.setBounds(0, 0, 865, 32);
		textHead.setOpaque(true);

		textHead.setBackground(SystemColor.textHighlight);
		frame.getContentPane().add(textHead);
		
//		JScrollPane scrollPaneCode = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		scrollPaneCode.setBounds(511, 312, 379, 260);
//		frame.getContentPane().add(scrollPaneCode);
//		
//		txtCode = new JTextArea();
//		scrollPaneCode.setViewportView(txtCode);
//		txtCode.setBackground(Color.LIGHT_GRAY);
//		txtCode.setLineWrap(true);
		
		
		/*txtUsage = new JTextArea();
		txtUsage.setLineWrap(true);
		txtUsage.setBackground(Color.LIGHT_GRAY);
		txtUsage.setBounds(227, 287, 331, 39);
		frame.getContentPane().add(txtUsage);*/
	}
}

//采用实现Comparable接口的方法实现排序
class S1 implements Comparable{
	int x;
	double y;
	S1(int x, double y){
		this.x = x;
		this.y = y;
	}
	//实现排序方法
	@Override
	public int compareTo(Object o) {
		S1 obj = (S1) o;
		return Double.compare(obj.y, y);
	}
	
	public int getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
}


