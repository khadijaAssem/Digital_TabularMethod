import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

public class TabularMethod {
    public static void main(String[] args) {
        Utilities Use = new Utilities();
        MyInfo Info = new MyInfo();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of variables please :)");
        int n=input.nextInt(); //no. of variables
        Info.n=n;
        int Min = (int)Math.pow(2, n); //max. no of minerms
        //System.out.println(Min);
        System.out.println("Enter the number of minterms please :)");
        int M =input.nextInt();//no. of minterms
        System.out.println("Enter the number of don't cares");
        int B= input.nextInt();
        int[] Minterms = new int[M]; //collecting minterms
        int [] Mindontcare = new int[M+B];
        System.out.println("Enter the minterms :)");
        for (int i = 0; i < M; i++) { //reading minterms
            Minterms[i] = input.nextInt();
            Mindontcare[i]=Minterms[i];
            if (Minterms[i]>=Min) {
                System.out.println("Enter a valid input");
                i--;
            }
        }
        System.out.println("Enter the don't care :)");
        for (int i = M; i < M+B; i++) { //reading DONT CARE
            Mindontcare[i] = input.nextInt();
            if (Mindontcare[i]>=Min) {
                System.out.println("Enter a valid input");
                i--;
            }
        }
        System.out.println("     ---------------------------");
        Arrays.sort(Minterms);//Sort minterms
        Arrays.sort(Mindontcare);//Sort minterms
        //Change to binary strings
        String[] MinTerms = new String[M+B];
        for (int i = 0; i < M+B; i++)
            MinTerms[i] = Use.decToBinary(Mindontcare[i], M+B);
        ArrayList<String>[] Tabular = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++)
            Tabular[i] = new ArrayList<String>();
        for (int i = 0; i < M+B; i++)
            Tabular[Use.OnBits(Mindontcare[i])].add(MinTerms[i]);
        //Print grouped binary strings
        System.out.println("\nBinary table");
        for (int i = 0; i <= n; i++)
            System.out.println(Tabular[i]);
        System.out.println("     ---------------------------");
        System.out.println("");
        ArrayList<String> PrimeImplicants = new ArrayList();
        PrimeImplicants = Use.B1(n, Tabular, PrimeImplicants);
        System.out.println(PrimeImplicants);
        System.out.println("     ---------------------------");
        System.out.println("");
        Info.essentialCost =new int[2][n];
        ArrayList<ArrayList<Integer>> PItable = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < PrimeImplicants.size(); i++) {
            PItable.add(Use.Table(PrimeImplicants.get(i)));
            Info.CostPI.add(Use.Cost(PrimeImplicants.get(i),Info));
        }
        Info.PrimeImplicants=PrimeImplicants;
        System.out.println(PItable);
        System.out.println("     ---------------------------");
        System.out.println("");
        ArrayList<ArrayList<Integer>> fillTable = new ArrayList<ArrayList<Integer>>();
        for (int i=0;i<PItable.size();i++)
            fillTable.add(Use.printPowerSet(PItable.get(i)));
        System.out.println(fillTable);
        System.out.println();

        for (int i=0;i<Minterms.length;i++)
            System.out.print(Minterms[i]+"          ");
        System.out.println();

        int C1=fillTable.size(),C2=Minterms.length;
        ArrayList<ArrayList<Integer> > TablePI = new ArrayList<ArrayList<Integer>>(C1);
        for (int i=0;i<C1;i++) {
            TablePI.add(new ArrayList());
            for (int j = 0; j < C2; j++)
                TablePI.get(i).add(0);
        }

        Use.EssentialPI(C1,C2,fillTable,Minterms,TablePI);
        Info.TablePI=TablePI;
        Info=Use.EssentialInTable(Use.transpose(Info.TablePI).size(),Info);
       // System.out.println(Info.EssentialPI);
        while ((Info.flaf[0]==true||Info.flaf[1]==true||Info.flaf[2]==true)&&(Info.TablePI.size()!=0)&&(Use.transpose(Info.TablePI).size()!=0)){
            Info=Use.EssentialInTable(Use.transpose(Info.TablePI).size(),Info);
        }
        System.out.println(Info.EssentialPI);
        for (int i=0;i<Info.EssentialPI.size();i++) {
            Use.Naming(Info.EssentialPI.get(i), Info);
            if (i!=Info.EssentialPI.size()-1)
                System.out.print("+");
            System.out.print(" ");
        }/*3
        ArrayList<ArrayList<Integer>> pet= new ArrayList<ArrayList<Integer>>();
        if (Info.TablePI.contains(-1)){
            for (int i=0;i<Use.transpose(Info.TablePI).size();i++)
                for (int j=0;j<TablePI.size();j++)
                    if (Info.TablePI.get(j).get(i)==-1) {
                        pet.add(i, new ArrayList<>());
                        pet.get(i).add(j);
                    }
        }*/
    }
}