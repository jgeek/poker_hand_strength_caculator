package sawmill;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class Main
{
    public static Map<Integer, ArrayList<ArrayList<Integer>>> map;
    public static int E;

    public static int profitOnBlockOfLength(int n){
        if (n == 1) {
            return -1;
        }
        if (n == 2) {
            return 3;
        }
        if (n == 3) {
            return 1;
        }
        if (n == 4) {
            return 6;
        }
        return 0;
    }

    // function to calculateProfit
    public static void calculateProfit( ArrayList<Integer> list,int E){
        int profit =0;
        int curr=0;

        for(int i:list){
            if(curr+i>4){
                // piece left after cutting once
                curr= curr+i-4;
                profit += profitOnBlockOfLength(i-curr);
                profit += profitOnBlockOfLength(curr);
            }else
            {
                profit+=profitOnBlockOfLength(i);
                curr+=i;
                if (curr == 4) {
                    curr = 0;
                }
            }
        }

        // map.putIfAbsent(profit, new ArrayList<ArrayList<Integer>>())

        if (map.get(profit) == null) {
            map.put(profit, new ArrayList<ArrayList<Integer>>());
        }
        ArrayList<Integer> sec_list = (ArrayList)list.clone();
        map.get(profit).add(sec_list);

    }

    // function to fing nextPermutation of the ArrayList
    public static boolean nextPermutation(ArrayList<Integer> list)
    {
        if (E <= 1) {
            return false;
        }

        int end = E-2;

        // find the pivot as per the algorithm
        while (end >= 0) {
            if (list.get(end+1)>list.get(end)) {
                break;
            }
            end--;
        }

        // if no greater element found permutation doesn't exist
        if (end < 0) {
            return false;
        }

        // iterating from last element
        int successor = E - 1;

        // Find the next greater element
        for (int i = E - 1; i > end; i--) {
            if (list.get(i) > list.get(end)) {
                successor = i;
                break;
            }
        }

        // Swap both
        int temp = list.get(successor);
        int endValue = list.get(end);
        int successorValue = list.get(successor);
        list.set(successor,endValue );
        list.set(end, temp);

        // finally reverse 
        int l =  end + 1;
        int r = E-1;

        while (l<r) {

                temp= list.get(l);
                int lValue = list.get(l);
                int rValue = list.get(r);

                list.set(l,rValue);
                list.set(r,lValue);

                l++;
                r--;
            }
        return true;
    }

        public static void main(String[] args) {
            Scanner sc=new Scanner(System.in);

           // accepting sawmills
                int Z;
                Z=sc.nextInt();
                int currcase =1;
                int profit=0;
                Map<Integer, ArrayList<ArrayList<Integer>>> answer=  new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
                int ii=0;

                while(Z!=0){

           Z--;
                   map = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();

                   //accepting number of tree trunks that are cut.
                    E=sc.nextInt();

                    ArrayList<Integer> list = new ArrayList<>();
                    int num;

                    for(int i=0; i<E; i++)
            {
                // accepting the length of the tree trunks. 
                num = sc.nextInt();
                list.add(num);
            }

            Collections.sort(list);
            calculateProfit(list,E);

            while(Main.nextPermutation(list)){
                calculateProfit(list,E);
            }

            // finding maximum profit from the map 
            Map.Entry<Integer, ArrayList<ArrayList<Integer>>> maxProfit = null;

            // Iterating the map
            for (Map.Entry<Integer, ArrayList<ArrayList<Integer>>> entry : map.entrySet()) {

                // storing max key in the maxProfit entry 
                if (maxProfit == null || entry.getKey().compareTo(maxProfit.getKey())> 0) {
                    maxProfit = entry;
                }
            }
            profit += maxProfit.getKey();

            answer.putIfAbsent(ii, new ArrayList<ArrayList<Integer>>());
                    for (ArrayList<Integer> lists : maxProfit.getValue()) {
                        answer.get(ii).add(lists);
                    }

            if(Z==0)
                    {
                        System.out.println("Case " + currcase);
                        System.out.println("Max Profit " + profit);
                        System.out.print("Order: ");

                        boolean f=true;

                        for (Map.Entry<Integer, ArrayList<ArrayList<Integer>>> entry : answer.entrySet()) {
                            if (!f) {
                                System.out.print(",");
                            }

                    f=false;
                            for (ArrayList<Integer> lists : entry.getValue()) {
                                System.out.print(lists);
                            }
                }

                System.out.println();

                answer = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
                        profit=0;
                        currcase++;
                        Z=sc.nextInt();

                    }
                    ii++;
                }
        }
}