// { Driver Code Starts
// Initial Template for Java

import java.io.*;
import java.util.*;

  public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine().trim());
        while (tc-- > 0) {
            String[] inputLine;
            inputLine = br.readLine().trim().split(" ");
            int k = Integer.parseInt(inputLine[0]);
            inputLine = br.readLine().trim().split(" ");
            int n = Integer.parseInt(inputLine[0]);
            int[] arr = new int[n];
            inputLine = br.readLine().trim().split(" ");
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(inputLine[i]);
            }

            int ans = new Solution().getMinDiff(arr, n, k);
            System.out.println(ans);
        }
    }
}// } Driver Code Ends


// User function Template for Java

class Solution {
    int getMinDiff(int[] arr, int n, int k) {
        
        int low=0;
        int high=0;
        Tower[] tr = new Tower[2*n];
        int s=0;
        for(int i = 0;i<n;i++){
            if(k>arr[i]){
                low=arr[i]+k;
                tr[s++]=new Tower(low,low);
            }else{
                low = arr[i]-k;
                high = arr[i]+k;
                tr[s++]=new Tower(low,high);
                tr[s++]=new Tower(high,low);
            }
        }
        System.out.println();
        Arrays.stream(tr).forEach(e -> System.out.print(" "+e));
        mergeSort(tr,0,s-1);
        System.out.println();
        Arrays.stream(tr).forEach(e -> System.out.print(" "+e));
        
        return 0;
        
    }
    
    void mergeSort(Tower[] arr,int l,int r){
        if(l<r){
            int m = l + (r-l)/2;
            mergeSort(arr,l,m);
            mergeSort(arr,m+1,r);
            merge(arr,l,m,r);
        }
    }
    
    void merge(Tower[] arr, int l, int m, int r){
        Tower[] b = new Tower[r-l+1];
        int s = 0;
        int i = l;
        int j = m+1;
        while(i<=m && j<=r){
            if(arr[i].height<=arr[j].height)
                b[s++]=arr[i++];
            else
                b[s++]=arr[j++];
        }
        if(i>m)
            while(j<=r)
                b[s++]=arr[j++];
                
        if(j>r){
            while(i<=m)
                b[s++]=arr[i++];
        }
        for(int x=0;x<s;x++)
            arr[l++]=b[x];
    }
}

class Tower{
    int height;
    int compl;
    public Tower(int h, int c){
        this.height=h;
        this.compl=c;
    }
    public String toString(){
        return "["+height+","+compl+"]";
    }
}
