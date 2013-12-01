package iven.alg;

import java.util.Arrays;

/**
 * from wiki http://zh.wikipedia.org/wiki/插入排序
 *  http://zh.wikipedia.org/wiki/归并排序
**/
public class ALG {

    /**
     * @param args
     */
    public static void main(String[] args) {
            Comparable[] c = {1,5,3,0,9,8,3};  
            System.out.println("before sort: " + Arrays.asList(c));
            insertionSort(c);
            System.out.println("after sort: " + Arrays.asList(c));
            System.out.println("=====================================");
            Comparable[] d = {1,5,3,0,9,8,3};  
            System.out.println("before sort: " + Arrays.asList(d));
            mergeSort(d);
            System.out.println("after sort: " + Arrays.asList(d));
            System.out.println("=====================================");
            
            Comparable[] e = {3,2,4,9,1,5,7,6,8,3};
            System.out.println("before sort: " + Arrays.asList(e));
            bubbleSort(e);
            System.out.println("after sort: " + Arrays.asList(e));
    }

    public static <T extends Comparable<? super T>> void insertionSort(T[] data) {
        InsertionSort.sort(data);  
    }
    
    public static <T extends Comparable<? super T>> void mergeSort(T[] data) {
        MergeSort.sort(data);
    }
    
    public static <T extends Comparable<? super T>> void bubbleSort(T[] data) {
        BubbleSort.sort(data);
    }
}

class InsertionSort {
    public static<T extends Comparable<? super T>> void sort(T[] data) {
        for (int index=1; index<data.length; index++) {
            T key = data[index];
            int pos = index;
            // shift larger values to the right
            while ( pos > 0 && data[pos-1].compareTo(key) > 0 ) {
                data[pos] = data[pos-1];
                pos--;
            }
            data[pos] = key;
        }
    }
}

class MergeSort {
    
    public static <T extends Comparable<? super T>> void sort(T[] arr) {
        T[] tmpArr = (T[]) new Comparable[arr.length];
        sort(arr, tmpArr, 0, arr.length - 1);
    }
    
    /**
     * internal method to make a recursive call to merge. <br />
     * 
     * @param arr an array of Comparable items. <br />
     * @param tmpArr temp array to placed the merged result. <br />
     * @param left left-most index of the subarray. <br />
     * @param right right-most index of the subarray. <br />
     */
    private static <T extends Comparable<? super T>> void sort(T[] arr, T[] tmpArr, int start, int end) { 
        if (start < end) {
            int mid = (start + end) / 2; 
            System.out.printf("sort (%d-%d, %d-%d) %s\n", start, mid, mid + 1, end, Arrays.asList(arr));
            sort(arr, tmpArr, start, mid);
            sort(arr, tmpArr, mid + 1, end);
            merge(arr, tmpArr, start, mid + 1, end);
            System.out.printf("merge (%d-%d, %d-%d) %s\n", start, mid, mid + 1, end, Arrays.asList(arr));
        }
    }
    
    /**
     * internal method to merge the sorted halves of a subarray. <br />
     * 
     * @param arr an array of Comparable items. <br />
     * @param tmpArr temp array to placed the merged result. <br />
     * @param leftPos left-most index of the subarray. <br />
     * @param rightPos right start index of the subarray. <br />
     * @param endPos right-most index of the subarray. <br />
     */
    private static <T extends Comparable<? super T>> void merge(T[] arr, T[] tmpArr, int lPos, int rPos, int rEnd) {
        int lEnd = rPos - 1;
        int tPos = lPos;
        int leftTmp = lPos;
        
        while (lPos <= lEnd && rPos <= rEnd) {
            if (arr[lPos].compareTo(arr[rPos]) <= 0) {
                tmpArr[tPos++] = arr[lPos++];
            } else {
                tmpArr[tPos++] = arr[rPos++];
            }
        }
        
        //copy the rest element of the left half subarray
        while (lPos <= lEnd) {
            tmpArr[tPos++] = arr[lPos++];
        }
        
        //copy the rest element of the right half subarray.(only one loop will be execute)
        while (rPos <= rEnd) {
            tmpArr[tPos++] = arr[rPos++];
        }
        
        //copy the tmpArr back cause we need to change the arr array items.
        for (; rEnd >= leftTmp; rEnd--) {
            arr[rEnd] = tmpArr[rEnd];
        }
        
    }
    
    
}

class BubbleSort {
    
    public static<T extends Comparable<? super T>>  void sort(T[] data) {
        for (int i = data.length - 1; i > 0; i-- ) {
            for (int j=0; j<i; j++) {
                if (data[j].compareTo(data[j+1]) > 0) {
                    T tmp = data[j+1];
                    data[j+1] = data[j];
                    data[j] = tmp;
                }
            }
        }
    }
}
