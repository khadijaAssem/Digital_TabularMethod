import java.util.ArrayList;

public class MyInfo {
    Utilities Use = new Utilities();
    int n;
    public ArrayList<String> PrimeImplicants = new ArrayList();//Prime implicants Binary
    public boolean[] flaf = new boolean[3];//States whether Column of row dominants or essential rows are present
    public int[][] essentialCost = new int[2][n];//Contains the cost of main variables whether the are essential or not
    public ArrayList<Integer> Cost = new ArrayList();//Cost of essentials
    public ArrayList<Integer> CostPI = new ArrayList();//Cost of the rest of minterms
    public ArrayList<String> EssentialPI=new ArrayList<String>();//Essential PI's
    ArrayList<ArrayList<Integer> > TablePI = new ArrayList<ArrayList<Integer>>();//Prime implicants table
}

