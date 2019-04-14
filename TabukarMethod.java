/*import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.*;

public class TabukarMethod {
    public static void main(String[] args){
        //Collecting information
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of variables please :)");
        int n= input.nextInt(); //no. of variables
        int Min = Pow(2,n); //max. no of minerms
        //System.out.println(Min);
        System.out.println("Enter the number of minterms please :)");
        int M = input.nextInt();//no. of minterms
        int [] Minterms =new int[M]; //collecting minterms
        for (int i=0;i<M;i++) { //reading minterms
            System.out.println("Enter the minterm :)");
            Minterms[i] = input.nextInt();
        }
        //Let's play
        Arrays.sort(Minterms);

        for (int i=0;i<M;i++)
            System.out.println(Minterms[i]);
        System.out.println();

        int [][][] Table = new int [Min][Min][n]; //Tabular method
        for (int i=0;i<Min;i++){ //Setting table to -1
            for (int j=0;j<Min;j++){
                for (int k=0;k<n;k++)
                    Table[i][j][k]=-1;
            }
        }
        //Grouping according to binary
        /*int [] Count = new int [n];
        for (int i=0;i<M;i++){
            Table[OnBits(Minterms[i])][Count[OnBits(Minterms[i])]++][0]=Minterms[i];
        }
        //Printing
        for (int i=0;i<Min;i++){
            for (int j=0;j<Min;j++){
               // if (Table[i][j][0]!=-1)
                    System.out.print(Table[i][j][0] +  " ");
            }
            System.out.println();
        }*/
        /*for (int i=0;i<Min;i++){
            for (int j=0;j<Min;j++){
                for (int k=0;k<n;k++)
                    
            }
        }*/
    //}/*/*
   /* public static int Pow(int base,int n){
        if (n==1)
            return 2;
        return 2*Pow(base,n-1);
    }
    public static int OnBits(int n){
        int OnBits=0;
        while (n!=0){
            OnBits+=n%2;
            n/=2;
        }
        return OnBits;
    }/*
    public static void Remove_Repettioin(int [] Arr){
        int n= Arr.length;
        for (int i=0;i<n;i++){

        }
    }*/
//}
