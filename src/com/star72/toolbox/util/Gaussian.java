package com.star72.toolbox.util;

import java.util.Scanner;
//import java.lang.Math;
//高斯消元法  **********经我验证，呵呵，正确
public class Gaussian{
	public static void main(String[] args){
		int test=0;
		do{
			int m,n;
			Scanner in=new Scanner(System.in);
			System.out.println("输入方程组个数(m)及未知数个数(n)");
			System.out.println("m=");
			m=in.nextInt();
			System.out.println("n=");
			n=in.nextInt();
			double [][]A=new double[m][n];
			double []B=new double[m];
			String []X=new String[n];
			
		    getA ( A );
		    getB ( B );
		    getX ( X );
		    turnZero ( A, B, X);
		    turnBack ( A, B );
		    printAnswer ( B, X );
		    System.out.println("go on test? 0/1");
		    test=in.nextInt();
	    }while( test==1);


			   
	}
			
	//得到系数矩阵
	public static void getA( double[][] Arr){
		Scanner input=new Scanner(System.in);
		System.out.println("一行一行地输入系数");
		for(int i=0; i<Arr.length ; i++)
			for(int j=0; j<Arr[i].length ; j++)
					Arr[i][j]= input.nextDouble();
					
		}
		//得到常数组
	public static void getB( double[] Arr){
		Scanner in=new Scanner(System.in);
		System.out.println("一行一行地输入常数(b1,b2...)");
		for(int i=0;i<Arr.length;i++){
			  Arr[i]=in.nextDouble();
			}
		}
		//得到系数矩阵
	public static void getX(String[] Arr){
		Scanner in=new Scanner(System.in);
		System.out.println("顺序输入未知数（x1,x2...）");
		for(int i=0;i<Arr.length;i++){
			  Arr[i]=in.next();
			}
		}
		//选出最大主元 ，从a[k][k]以下查找，记录所在位置
	public static void  chooseBiggest(double[][] Arr,int k,int[] index  ){
			double max=Arr[k][k];
			for(int i=k;i<Arr.length;i++ ) 
				for(int j=k;j<Arr[i].length;j++){
					if( Math.abs(Arr[i][j])>max)
						max=Arr[i][j];
						index[0]=i;
						index[1]=j;
					}	
		}
		//将系数矩阵的主元a[row][col]移到a[k][k]位置，
	public static void changeA(double[][] ArrA,int k,int row,int col){

		double[] temp=new double[ArrA.length];
		for(int i=0;i<ArrA.length;i++){
			temp[i]=ArrA[i][k];
			ArrA[i][k]=ArrA[i][col];
			ArrA[i][col]=temp[i];
			}
		for(int i=0;i<ArrA[row].length;i++){
			temp[i]=ArrA[k][i];
			ArrA[k][i]=ArrA[row][i];
			ArrA[row][i]=temp[i];
			}
	
		}
		//交换常数,交换b[k]和b[row]
		public static void changeB(double[] Arr,int k,int row){
			double temp=Arr[k];
			Arr[k]=Arr[row];
			Arr[row]=temp;
			}
			//交换未知数,交换x[k]和x[col]
		public static void changeX(String []Arr,int k,int col){
			String temp=Arr[k];
			Arr[k]=Arr[col];
			Arr[col]=temp;
			}
		// 将增广矩阵化为上三角矩阵
		public static void turnZero(double[][] ArrA,double[] ArrB,String[] ArrX){
			int row,col;
			double temp;
			
			
			for(int k=0;k<ArrA.length-1;k++){
				int []index=new int [2];
				chooseBiggest(ArrA, k, index);
				row=index[0];
				col=index[1];
				changeA(ArrA , k, row, col);
				changeB(ArrB, k,row);
				changeX(ArrX, k,col);
				for(int i=k+1;i<ArrA.length;i++) {
					temp=ArrA[i][k]/ArrA[k][k];
					ArrB[i]=ArrB[i]-temp*ArrB[k];
					for(int j=k;j<ArrA[i].length;j++)
						ArrA[i][j]=ArrA[i][j]-temp*ArrA[k][j];
					}
				}	
			}
			//回代
		public static void turnBack(double [][]ArrA,double[] ArrB){
				for(int i=ArrB.length-1;i>=0;i--){
					for(int j=ArrA.length-1;j>i;j--)
					  ArrB[i]=ArrB[i]-ArrA[i][j]*ArrB[j];
					ArrB[i]=ArrB[i]/ArrA[i][i];
					}
			}
			//输出解
		public static void printAnswer(double []ArrB,String[] ArrX){
				System.out.println("方程组的解：");
				for(int i=0;i<ArrB.length;i++){
						System.out.println(ArrX[i]+"="+ArrB[i]);
					}
			}
}