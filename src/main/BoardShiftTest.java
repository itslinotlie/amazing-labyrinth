package main;

import java.util.*;
import java.io.*;

public class BoardShiftTest {
    static int a[][] = new int[9][9], mxn = 7;
    static char c, p; static int n, x, y, val;
    public static void main(String[] args) {
        //filling in unique values for each element in the array
        for (int i=1;i<=mxn;i++) {
            for (int j=1;j<=mxn;j++) {
                a[i][j] = i*mxn+j;
            }
        }
        //"free" point
        x = y = mxn + 1; val = 100; a[x][y] = val;
        printArray();

        Scanner in = new Scanner(System.in);
        p = in.next().charAt(0); c = in.next().charAt(0); n = in.nextInt();
        while(n!=0) {
            if(p=='-') {
                for (int i=0;i<mxn;i++) {
                    if(c=='R') a[n][i] = a[n][i+1];
                    else a[i][n] = a[i+1][n];
                }
                if(c=='R') {
                    a[n][mxn] = val;
                    x = n; y = 0; val = a[x][y];
                } else {
                    a[mxn][n] = val;
                    x = 0; y = n; val = a[x][y];
                }
            } else {
                for (int i=mxn+1;i>=2;i--) {
                    if(c=='R') a[n][i] = a[n][i-1];
                    else a[i][n] = a[i-1][n];
                }
                if(c=='R') {
                    a[n][1] = val;
                    x = n; y = mxn+1; val = a[x][y];
                } else {
                    a[1][n] = val;
                    x = mxn+1; y = n; val = a[x][y];
                }
            }
            printArray();
            System.out.printf("Point (%d, %d) = %d\n", x, y, val);
            p = in.next().charAt(0); c = in.next().charAt(0); n = in.nextInt();
        }
    }
    static void printArray() {
        for (int i=0;i<=mxn+1;i++) {
            String ret = "[";
            for (int j=0;j<=mxn+1;j++) {
                ret += String.format("%3d, ", a[i][j]);
            } System.out.println(ret.substring(0, ret.length()-2)+"]");
        }
    }
}
