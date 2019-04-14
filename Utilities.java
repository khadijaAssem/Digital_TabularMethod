import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.*;
import java.util.stream.IntStream;

public class Utilities {
    public String decToBinary(int n,int N) {
        String ToBinary = "0";
        int Y = N;
        while (N != 0) {
            ToBinary = ToBinary + "0";
            N--;
        }
        StringBuilder str = new StringBuilder(ToBinary);
        while (n != 0) {
            if (n % 2 == 0)
                str.setCharAt(Y--, '0');
            else
                str.setCharAt(Y--, '1');
            n /= 2;
        }
        ToBinary = str.toString();
        return ToBinary;
    }
    public static int OnBits(int n){
        int OnBits=0;
        while (n!=0){
            OnBits+=n%2;
            n/=2;
        }
        return OnBits;
    }
    public static ArrayList B1(int n, ArrayList[] Tabular,ArrayList PrimeImplicants){
        ArrayList<String>[] tempArrList = new ArrayList [n];
        for (int i=0;i<n;i++)
            tempArrList[i] = new ArrayList<String>();
        ArrayList<Integer>[] X = new ArrayList [n+1];
        for (int i=0;i<=n;i++) {
            X[i] = new ArrayList<Integer>();
            for (int j=0;j<Tabular[i].size();j++)
                X[i].add(0);
        }
        for (int i=0;i<n;i++) {
            for (int j = 0; j < Tabular[i].size(); j++) {//m list size of i
                for (int k = 0; k < Tabular[i+1].size(); k++) {//k list size of j
                    if (Tabular[i].get(j).toString()!="BOO" && Tabular[i + 1].get(k).toString()!= "BOO") {
                        String tempElement = B2(Tabular[i].get(j).toString(), Tabular[i + 1].get(k).toString());
                        if (tempElement != null) {
                            X[i].set(j, -1);
                            X[i + 1].set(k, -1);
                            tempArrList[i].add(tempElement);
                        } else
                            tempArrList[i].add("BOO");
                    } else tempArrList[i].add("BOO");
                }
            }
        }
        for (int i=0;i<n;i++)
            removeDuplicates(tempArrList[i]);
        Boolean Check=Check(X,n,Tabular,PrimeImplicants);
        if (Check != true)
            B1(n-1,tempArrList,PrimeImplicants);
        removeDuplicates(PrimeImplicants);
        return PrimeImplicants;
    }
    public static String B2 (String one, String two){
        Character[] charObjectArray = one.chars().mapToObj(c -> (char)c).toArray(Character[]::new);
        Character[] charObjectArray2 = two.chars().mapToObj(c -> (char)c).toArray(Character[]::new);
        String tempElement="0";
        int diff=0;

        for (int i=0;i<one.length();i++) {
            if (charObjectArray[i] != charObjectArray2[i]) {
                diff++;
                tempElement = tempElement + 'x';
            } else tempElement = tempElement + charObjectArray[i];
            if (diff >1)
                break;
        }
        if (diff==1)
            return tempElement;
        else return null;
    }
    public static boolean Check (ArrayList[] X,int n,ArrayList[] Tabular,ArrayList PrimeImplicants){
        int Count=0,Zeroes=0;
        for (int i=0;i<=n;i++)
            for (int j=0;j<X[i].size();j++){
                Count++;
                if (X[i].get(j).equals(0)) {
                    Zeroes++;
                    if (Tabular[i].get(j).toString()!="BOO")
                        PrimeImplicants.add(Tabular[i].get(j).toString());
                }
            }
        if (Count==Zeroes)
            return true;
        return false;
    }
    public static ArrayList removeDuplicates(ArrayList list){
        Set set = new LinkedHashSet();// Create a new LinkedHashSet
        set.addAll(list);// Add the elements to set
        list.clear();// Clear the list
        list.addAll(set);// add the elements of set with no duplicates to the list
        return list;
    }
    public static ArrayList Table(String PI){
        ArrayList<Integer> PItable =new ArrayList();
        int Sum=0,n=PI.length(),j=0;
        PItable.add(Sum);
        Character[] Ch = PI.chars().mapToObj(c -> (char)c).toArray(Character[]::new);
        for (int i=n-1;i>=0;i--,j++)
            if (Ch[i]=='1')
                Sum+=(int)Math.pow(2,j);
            else if (Ch[i]=='x')
                PItable.add((int)Math.pow(2,j)) ;
        PItable.set(0,Sum);
        return PItable;
    }
    public static ArrayList printPowerSet(ArrayList set){
        int u=0;
        ArrayList<Integer>TEMP=new ArrayList();
        for(int i=0;i<Math.pow(2, set.size());i++) {
            String x="";
            int y=0;
            for(int z=0;z<set.size();z++)
                if ((i & (1 << z)) > 0) {
                    x=x+Integer.toString((int)set.get(z));
                    y=y+(int)set.get(z);
                }
            if((x!="")&&(x.contains(Integer.toString((int)set.get(0))))) {
                TEMP.add(u, y);
                u++;
            }
        }
        return TEMP;
    }
    public static int IndexOf(int arr[], int t) {
        return IntStream.range(0, arr.length).filter(i -> t == arr[i]).findFirst().orElse(-1);
    }
    public static void EssentialPI(int Row,int Column,ArrayList<ArrayList<Integer>> fillTable,int[] Minterms,ArrayList<ArrayList<Integer>> TablePI){
        for (int i=0;i<Row;i++)
            for (int j=0;j<fillTable.get(i).size();j++){
                int index=IndexOf(Minterms,fillTable.get(i).get(j));
                if(index!=-1)
                    TablePI.get(i).set(index,-1);
            }
        for (int i=0;i<Row;i++) {
            for (int j = 0; j < Column; j++)
                System.out.print(TablePI.get(i).get(j) + "          ");
            System.out.println();
        }
    }
    public static int Cost(String pi,MyInfo myinfo){
        Character[] Ch = pi.chars().mapToObj(c -> (char)c).toArray(Character[]::new);
        int Cost =0,N=pi.length(),j=0;
        for (int i=pi.length()-1;i>=N-myinfo.n;i--,j++)
            if (Ch[i] == '1')
                Cost += 1;
            else if (Ch[i] == '0')
                if ((myinfo.essentialCost[0][j]!= -1)||(myinfo.essentialCost[1][j]==0)) {
                    Cost += 2;
                    if(myinfo.essentialCost[0][j]== -1)
                        myinfo.essentialCost[1][j]=-1;
                }
                else Cost += 1;
        Cost++;
        return Cost;
    }
    public static MyInfo EssentialInTable(int Column,MyInfo myinfo){
        myinfo.flaf[0] = false;
        ArrayList<ArrayList<Integer>> Temp = new ArrayList<ArrayList<Integer>>();
        for (int i=0;i<Column;i++) {
            Set<Integer> set=new HashSet<Integer>();
            set.add(-1);
            if (myinfo.TablePI.size()==0)
                break;
            Temp = transpose(myinfo.TablePI);
            boolean flag=true;
            int Counter=0,index=-1;
            for(int j=0;j<Temp.get(i).size();j++){
                flag = set.add(Temp.get(i).get(j));
                if (!(flag)&&(Temp.get(i).get(j)==-1)){
                    index = j;
                    flag = true;
                    Counter++;
                }
                if (Counter>1) break;
            }
            if (Counter==1) {
                for (int k=0;k<Temp.size();k++)
                    if (myinfo.TablePI.get(index).get(k)==-1)
                        Collections.replaceAll(Temp.get(k), -1, 0);
                myinfo.TablePI=transpose(Temp);
                myinfo.TablePI.remove(index);
                myinfo.flaf[0]= true;
                myinfo.EssentialPI.add(myinfo.PrimeImplicants.get(index));
                CalcCost(myinfo,myinfo.PrimeImplicants.get(index));
                myinfo.PrimeImplicants.remove(index);
            }
        }int i;
        myinfo.Cost= new ArrayList<>();
        myinfo.CostPI= new ArrayList<>();
        for (int j=0;j<myinfo.n;j++) myinfo.essentialCost[1][0]=0;
        for (i = 0; i < myinfo.EssentialPI.size(); i++)
            myinfo.Cost.add(i, Cost(myinfo.EssentialPI.get(i), myinfo));
        for (int j=0;j<myinfo.PrimeImplicants.size();j++)
            myinfo.CostPI.add(j, Cost(myinfo.PrimeImplicants.get(j), myinfo));
        Temp=transpose(myinfo.TablePI);
        if (Temp==null){
            myinfo.flaf[0]=false;myinfo.flaf[1]=false;myinfo.flaf[2]=false;
            return myinfo;
        }
        myinfo=ColumnDominance(myinfo.TablePI.size(),Temp.size(),myinfo);
        Temp=transpose(myinfo.TablePI);
        myinfo=RowDominance(myinfo,myinfo.TablePI.size(),Temp.size());
        return myinfo;
    }
    public static void CalcCost(MyInfo myinfo,String PI){
        Character[] Ch = PI.chars().mapToObj(c -> (char)c).toArray(Character[]::new);
        int N=PI.length(),j=0;
        for (int i=PI.length()-1;i>=N-myinfo.n;i--,j++)
            if (Ch[i]=='0')
                myinfo.essentialCost[0][j]=-1;
    }
    public static MyInfo ColumnDominance(int Row,int Column,MyInfo myinfo){
        myinfo.flaf[0] = false;
        ArrayList<ArrayList<Integer>> Temp = new ArrayList<ArrayList<Integer>>();
        Temp = transpose(myinfo.TablePI);
        int Count=0,flag=0,j;ArrayList<Integer> Y= new ArrayList();
        for (int i=0;i<Column;i++){
            for ( j=0;j<Row;j++){
                if (myinfo.TablePI.get(j).get(i)==-1){
                    Count++;
                    myinfo.TablePI.get(j).set(i,-2);
                    if (myinfo.TablePI.get(j).indexOf(-1)!=-1) flag++; else break;
                    myinfo.TablePI.get(j).set(i,-1);
                }
            }
            if (Count==flag&&j==Row&&Count!=0) {System.out.println(i);myinfo.flaf[0] = true;Temp.remove(i);}
            Count=0;flag=0;;
        }
        myinfo.TablePI=transpose(Temp);
        return myinfo;
    }
    public static MyInfo RowDominance(MyInfo myinfo,int Row,int Column){
        myinfo.flaf[2] = false;
        ArrayList<ArrayList<Integer>> t= new ArrayList<ArrayList<Integer>>();
        t=transpose(myinfo.TablePI);
        int k,X;
        for (int i=0;i<Column;i++)
            if ((X=t.get(i).indexOf(-1))!=-1) {
                for (int j = 0; j < Row; j++)
                    if (j != X) {
                        for (k = i; k < Column; k++)
                            if (myinfo.TablePI.get(X).get(k) == myinfo.TablePI.get(j).get(k) || myinfo.TablePI.get(X).get(k) == 0) ;
                            else break;
                        if ((k == Column) && (myinfo.CostPI.get(X) >= myinfo.CostPI.get(j))) {
                            myinfo.EssentialPI.add(myinfo.PrimeImplicants.get(X));
                            CalcCost(myinfo, myinfo.PrimeImplicants.get(X));
                            Collections.replaceAll(myinfo.TablePI.get(j),-1,0);
                            myinfo.TablePI.remove(X);Row--;
                            myinfo.PrimeImplicants.remove(X);
                            myinfo.CostPI.remove(X);
                            Collections.replaceAll(myinfo.TablePI.get(X),-1,0);
                            myinfo.flaf[2] = true;
                        }
                    }
                t=transpose(myinfo.TablePI);
            }
        int i;
        myinfo.Cost= new ArrayList<>();
        myinfo.CostPI= new ArrayList<>();
        for (int j=0;j<myinfo.n;j++) myinfo.essentialCost[1][0]=0;
        for (i = 0; i < myinfo.EssentialPI.size(); i++)
            myinfo.Cost.add(i, Cost(myinfo.EssentialPI.get(i), myinfo));
        return myinfo;
    }
    public static ArrayList<ArrayList<Integer>> transpose(ArrayList<ArrayList<Integer>> matrixIn) {
        if (matrixIn.size()==0)
            return null;
        ArrayList<ArrayList<Integer>> matrixOut = new ArrayList<ArrayList<Integer>>();
        int noOfElementsInList = matrixIn.get(0).size();
        for (int i = 0; i < noOfElementsInList; i++) {
            ArrayList<Integer> col = new ArrayList<Integer>();
            for (ArrayList<Integer> row : matrixIn) {
                col.add(row.get(i));
            }
            matrixOut.add(col);
        }
        return matrixOut;
    }
    public static void Naming(String Str,MyInfo myinfo){
        Character[] Ch = Str.chars().mapToObj(c -> (char)c).toArray(Character[]::new);
        int N=Str.length(),A=65;
        for (int i=N-myinfo.n;i<Str.length();i++,A++)
            if (Ch[i]=='0')
                System.out.print(Character.toString ((char) A)+"'");
            else if (Ch[i]=='1')
                System.out.print(Character.toString((char) A));
        System.out.print(" ");
    }
}