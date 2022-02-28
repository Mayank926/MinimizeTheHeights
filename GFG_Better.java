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
                tr[s++]=new Tower(low,low,i,false);
            }else{
                low = arr[i]-k;
                high = arr[i]+k;
                tr[s++]=new Tower(low,high,i,false);
                tr[s++]=new Tower(high,low,i,false);
            }
        }
        /*
        System.out.println();
        Arrays.stream(tr).forEach(e -> System.out.print(" "+e));
        */
        mergeSort(tr,0,s-1);
        /*
        System.out.println();
        Arrays.stream(tr).forEach(e -> System.out.print(" "+e));
        */
        int minDiff = Integer.MAX_VALUE;
        //System.out.println("s is "+s);
        //System.out.println(" i running from 0 to "+(s-n));
        for(int i =0;i<=s-n;i++){
            resetVisibility(tr,s-1);
            int elc = 0;
            int j=i;
            //System.out.println("j from "+j+" till "+(s-1));
            while(elc!=n && j<=s-1){
                //System.out.println(" j "+j+" elc "+elc+" checking "+tr[j]);
                if(!tr[j].visited){
                    tr[j].visited=true;
                    if(tr[j].height!=tr[j].compl){
                        //System.out.println("Setting visited true for "+tr[j].compl);
                        setComplementVisited(tr,j,s-1,tr[j].compl,tr[j].idx);
                    }
                    elc++;
                }
                j++;
            }
            //System.out.println("Done with while i "+i+" j "+j+" elc "+elc);
            //System.out.println();
        //Arrays.stream(tr).forEach(e -> System.out.print(" "+e));
            if(elc==n && (tr[j-1].height-tr[i].height<minDiff))
                minDiff=tr[j-1].height-tr[i].height;
            //System.out.println("current min Diff "+minDiff);
        }
        return minDiff;
    }
    
    void setComplementVisited(Tower[] arr, int beg, int end, int key, int idx){
        if(beg>end)
            return;
        int mid= beg + (end-beg)/2;
        if(arr[mid].height==key){
            if(arr[mid].idx==idx){
                //System.out.println("Setting visited true for "+arr[mid]);
                arr[mid].visited=true;
            }else if (arr[mid].idx>idx){
                while(arr[mid].idx!=idx)
                    mid--;
                //System.out.println("Setting visited true for "+arr[mid]);
                arr[mid].visited=true;
            }else{
                while(arr[mid].idx!=idx)
                    mid++;
                //System.out.println("Setting visited true for "+arr[mid]);
                arr[mid].visited=true;
            }
            return;
        }
        if (key>arr[mid].height)
            setComplementVisited(arr,mid+1,end,key,idx);
        else
            setComplementVisited(arr,beg,mid-1,key,idx);
        return;
    }
    
    void resetVisibility(Tower[] arr,int end){
        for(int i =0;i<=end;i++)
            arr[i].visited=false;
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
    int idx;
    int compl;
    boolean visited;
    public Tower(int h, int compl,int c,boolean v){
        this.height=h;
        this.idx=c;
        this.compl=compl;
        this.visited=v;
    }
    //public String toString(){
      //  return "["+height+","+compl+","+idx+","+visited+"]";
    //}
}
