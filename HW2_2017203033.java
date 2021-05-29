//이름 : 이재하
//학번 : 2017203033
//Environment : java version "16.0.1"
//              IntelliJ IDEA Community Edition 2021.1.1
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HW2_2017203033 {
    public static long[][] dp;
    public static long numberOfPartition(int[] set, int n, int sum){
        if(sum%2==1) return 0; // print 0 if sum is odd
        dp=new long[n+1][sum/2+1];

        for(int i=0;i<=n;i++) // initialize first col 1
            dp[i][0]=1;
        for(int i=1;i<=sum/2;i++) // initialize first row 0
            dp[0][i]=0;

        for (int i=1;i<=n;i++){
            for(int j=1;j<=sum/2;j++){
                if(j<set[i-1]) //exclude
                    dp[i][j]=dp[i-1][j];
                else if(j>=set[i-1]) //include
                    dp[i][j]=dp[i-1][j] + dp[i-1][j-set[i-1]];
            }
        }
        return dp[n][sum/2]/2;
    }

    public static void printSubset(int[] set, int n, int sum) {
        List<Integer> subset = new ArrayList<Integer>(); //save a subset
        List<Integer> subset2 = new ArrayList<Integer>(); //save another subset
        int i = n;
        int j = sum / 2;
        while (i > 0 && j>=0) {
            if (dp[i - 1][j] != 0) {
                i--;
                subset2.add(set[i]);
            } else if (dp[i - 1][j - set[i - 1]] != 0) {
                i--;
                j -= set[i];
                subset.add(set[i]);
            }
        }
        Collections.sort(subset);
        Collections.sort(subset2);

        System.out.print("{");
        for (int a = 0; a < subset.size(); a++) {
            if(a==subset.size()-1) System.out.print(subset.get(a) + "}");
            else System.out.print(subset.get(a) + ",");
        }

        System.out.print(", {");
        for (int b = 0; b < subset2.size(); b++) {
            if(b==subset2.size()-1) System.out.println(subset2.get(b) + "}");
            else System.out.print(subset2.get(b) + ",");
        }
    }
    public static void main (String[]args){
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        while (!str.equals("EOI")) {
            int n = Integer.parseInt(str); //change input type string to int
            int[] set = new int[n]; //element set
            for (int i = 0; i < n; i++)
                set[i] = sc.nextInt();
            int sum = 0; //sum of element
            for (int i = 0; i < n; i++)
                sum += set[i];


            //print result
            long result = numberOfPartition(set, n, sum);
            if (result > (long) (Math.pow(2, 32) - 1) || result < 0) {
                System.out.println("NUMEROUS");
                printSubset(set, n, sum);
            }
            else if(result==0)
                System.out.println(result);
            else {
                System.out.println(result);
                printSubset(set, n, sum);
            }
            str = sc.next();
        }
    }
}