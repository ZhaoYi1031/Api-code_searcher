package buaa.act;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Hierarchical {
private double[][] matrix;
private int[] belong; 
private int dimension;// 数据维度

private int n;

	public void work(){
		
	}

	private class Node {
		String[] attributes;
		public Node() {
			attributes = new String[11];//double[100];
	 	}
	}
	
	ArrayList<Double> scores;
	private ArrayList<Node> arraylist;
	
	private class Model {
		int x = 0;
		int y = 0;
		double value = 0;
	}
	
	private Model minModel = new Model();
	
	private double getDistance(Node one, Node two) { //计算两个字符串的距离// 计算两点间的欧氏距离
		double val = 0;
		String s1, s2;
		s1 = one.attributes[0];
		s2 = two.attributes[0];
		String[] w1 = new String[10010];// = s1.split(" ");
		String[] w2 = new String[10010];// = s2.split(" ");
		int pos = 0, lastpos, cnt1 = 0, cnt2 = 0;
		while (pos < s1.length()){
			while (pos < s1.length() && !Character.isLetter(s1.charAt(pos)) ){
				pos++;
			}
			lastpos = pos;
			while (pos < s1.length() && Character.isLetter(s1.charAt(pos)) ){
				pos++;
			}
			w1[cnt1++] = s1.substring(lastpos, pos);
		}
		pos = 0;
		while (pos < s2.length()){
			while (pos < s2.length() && !Character.isLetter(s2.charAt(pos)) ){
				pos++;
			}
			lastpos = pos;
			while (pos < s2.length() && Character.isLetter(s2.charAt(pos)) ){
				pos++;
			}
			w2[cnt2++] = s2.substring(lastpos, pos);
		}
		Set<String> h1 = new HashSet<String>(); 
		Set<String> h2 = new HashSet<String>(); 
		int l1 = w1.length, l2 = w2.length;
		int tot=0;
		StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < cnt1; ++i){ //l1
			tmp.setLength( 0 );//相当于清空
			for (int j = i; j < cnt1; ++j){ //l1
				if (j == i){
					tmp.append(w1[j]);// = w1[j];
				}else{
					tmp.append(" "+w1[j]);//tmp = tmp + " " + w1[j];
				}
				try{
					h1.add(tmp.toString());
				}catch (Exception e){
					System.out.println("-------------");
					System.out.println(s1);
					System.out.println(tmp.toString());
					System.exit(0);
				}
			}
		}
		for (int i = 0; i < cnt2; ++i){ //l2
			tmp.setLength(0);
			for (int j = i; j < cnt2; ++j){ //l2
				if (j == i){
					tmp.append(w2[j]);
				}else{
					tmp.append(""+w2[j]);
				}
				h2.add(tmp.toString());
			}
		}
		
		Set<String> v1 = new HashSet<String>(); 
		Set<String> v2 = new HashSet<String>(); 
		
		v1.clear();
	    v1.addAll(h1);
	    v1.retainAll(h2);
	    int cnt_1=0;
	    v2.clear();
	    v2.addAll(h1);
	    v2.addAll(h2);
	    int tot1 = 0, tot2 = 0;
	    for (String i: v1){
	    	tot1 += i.replace(" ","").length();
	    }
	    for (String i: v2){
	    	tot2 += i.replace(" ","").length();
	    }
	    Double t = (Double) ((Double)(1.0*tot1)*(Double)(1.0*tot1)/((Double)(1.0*tot2)*(Double)(1.0*tot2)));
	    return Math.sqrt(1-t);
	}
	
	private void loadMatrix() {// 将输入数据转化为矩阵
		for (int i = 0; i < matrix.length; ++i) {
			for (int j = i + 1; j < matrix.length; ++j) {
				double distance = getDistance(arraylist.get(i), arraylist.get(j));
				matrix[i][j] = distance;
			}
		}
	}
	
	private Model findMinValueOfMatrix(double[][] matrix) {// 找出矩阵中距离最近的两个簇
		Model model = new Model();
		double min = 0x7fffffff;
		model.value = min;
		for (int i = 0; i < matrix.length; ++i) {
			if (findRoot(i) != i)
				continue;
			for (int j = i + 1; j < matrix.length; ++j) {
				if (findRoot(j) != j)
					continue;
				if (min > matrix[i][j] && matrix[i][j] != -1) {
					min = matrix[i][j];
					model.x = i;
					model.y = j;
					model.value = matrix[i][j];
				}
			}
		}
		return model;
	 }

		
	 private int findRoot(int x){
		 if (belong[x] == x){
			 return x;
		 }
		 return findRoot(belong[x]);
	 }
	 private double mymax(double p, double q){
		 return (p>q)?p:q;
	 }
	 private void rootMerge(int x, int y){
		 for (int i = 0; i < n; ++i){
			 matrix[y][i] = mymax(matrix[x][i], matrix[y][i]);
		 }
	 }
	 private void merge(int x, int y){
		int fx, fy;
		fx = findRoot(x);
		fy = findRoot(y);
		if (fx < fy){
			belong[fy] = fx;
			rootMerge(fy, fx);
		}else{
			belong[fx] = fy;
			rootMerge(fx, fy);
		}
	 }
	
	 private String processHierarchical(String path, double threshold) {
		 try {
			 PrintStream out = new PrintStream(path);
			 while (true) { //凝聚层次聚类迭代
				 out.println("Matrix update as below: ");
				 for (int i = 0; i < matrix.length; ++i) {// 输出每次迭代更新的矩阵
				 for (int j = 0; j < matrix.length - 1; ++j) {
					 	out.print(new DecimalFormat("#.0000").format(matrix[i][j]) + " ");
				 	}
				 	out.println(new DecimalFormat("#.0000").format(matrix[i][matrix.length - 1]));
				 }
				 System.out.println();
				 minModel = findMinValueOfMatrix(matrix);
//				 System.out.println("minDis="+minModel.value);
				 if (minModel.value >= threshold) {// 当找不出距离最近的两个簇时，迭代结束
					 break;
				 }
				 out.println("Combine " + (minModel.x + 1) + " " + (minModel.y + 1));
//				 System.out.println("!!!Combine " + (minModel.x + 1) + " " + (minModel.y + 1));
				 
				 matrix[minModel.x][minModel.y] = matrix[minModel.y][minModel.x] = -1;
				 merge(minModel.x, minModel.y);
				 
				 out.println("The distance is: " + minModel.value);
			 	}
			 
			 Double[] scoreAll = new Double[110];
			 
//			 Arrays.fill(scoreAll, 0);
			 for (int i = 0; i < n; ++i){
				 scoreAll[i] = 0.0;
//				 System.out.print("i="+i+":  ");
				 for (int j = 0; j < n; ++j){
					 if (findRoot(j) == i){
//						 if (j!=i)
//							 System.out.print("      ");
//						 System.out.println("~~"+findRoot(j));
						 int r = findRoot(j);
						 scoreAll[r] = scoreAll[r]+scores.get(j);
//						 System.out.println("      "+arraylist.get(j).attributes[0]+';');
					 }
				 }
//				 System.out.println("");
			 }
			 
			 Double maxD = 0.0;
			 int maxbelong = -1, maxi = -1;
			 for (int i = 0; i < n; ++i){
//				 System.out.println("i="+i+"va="+scoreAll[i]);
				 if (scoreAll[i] > maxD){
					 maxbelong = i;
					 maxD = scoreAll[i];
				 }
			 }
//			 System.out.println("maxD = "+ maxD + " maxbelong="+ maxbelong);
			 maxD = 0.0;
			 for (int i = 0; i < n; ++i){
				 if (findRoot(i) == maxbelong){
					 if (scores.get(i) > maxD){
						 maxD = scores.get(i);
						 maxi = i;
					 }
				 }
			 }
			 
//			 System.out.println("maxD = "+ maxD + "maxi=" + maxi);
//			 System.out.println("$$$$$String$$$$$="+arraylist.get(maxi).attributes[0]);
			 
			 out.close();
//			 System.out.println("Please check results in: " + path);
			 return arraylist.get(maxi).attributes[0];
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		return "";
	 	}
	 
	 public String searchWork(ArrayList<String> arraylist, ArrayList<Double> scoresTmp){
		 this.scores = scoresTmp;
		 ArrayList<Node> nodes = new ArrayList<Node>();
		 int siz = arraylist.size();
		 for (int i = 0; i < siz; ++i){
			 Node node = new Node();
			 node.attributes[0] = arraylist.get(i);
//			 System.out.println(arraylist.get(i));
			 nodes.add(node);
		 }
//		 Hierarchical hi = new Hierarchical();
		 this.arraylist = nodes;
		 inputWork();//nodes);
		 return processHierarchical("outProcess.txt", 0.999);
		 //0.9999:BLEU=19.92（378个）
		 //0.999999 好像很烂
		 //0.99 BLEU=19.02(400个）
		 //0.999 BLEU=19.93(400)
	 }
	 
	 public void inputWork(){//ArrayList<Node> arraylist){
			 n = arraylist.size();
//			 System.out.println("n="+n);
			 matrix = new double[n][n];//double
			 belong = new int[n];
			 for (int i = 0; i < n; ++i){
				 belong[i] = i;
			 }
			 
			 loadMatrix();
	 }
	 
	 public void setInput(String path) {
		 try {
			 BufferedReader br = new BufferedReader(new FileReader(path));
			 String str;
			 String[] strArray;
			 arraylist = new ArrayList<Node>();
			 int cnt = 0;
			 while ((str = br.readLine()) != null) {
//				 System.out.println(str);
//				 strArray = str.split(",");
//				 dimension = strArray.length;
//				 System.out.println("dimension="+dimension);
				 Node node = new Node();
//				 for (int i = 0; i < dimension; ++i) {
//					 node.attributes[i] = Double.parseDouble(strArray[i]);
//				 }
				 node.attributes[0] = str;
				 arraylist.add(node);
				 if (++cnt>10){
					 break;
				 }
			 }
//			 System.exit(0);
			 /*n = arraylist.size();
			 matrix = new double[n][n];//double
			 belong = new int[n];
			 for (int i = 0; i < n; ++i){
				 belong[i] = i;
			 }
			 
			 loadMatrix();*/
			 inputWork();//arraylist);
			 br.close();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
	
	 public void printOutput(String path) {
		 processHierarchical(path, 0.999);
	 }
	
//	 public static void main(String[] args) {
//		 Hierarchical hi = new Hierarchical();
//		 hi.setInput("/Users/Mr.ZY/GitHub/api-cluster/AgglomerativeCluster/usage_data/test.txt");//"hierarchical.txt");
//		 hi.printOutput("hierarchical_results.txt");
//	 }
}