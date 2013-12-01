package iven.alg;

import java.util.Arrays;


public class MyALG {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Comparable[] a = {1,3,3,2,5};
        System.out.printf("==== before sort %s ====%n ", Arrays.asList(a));
        bubbleSort(a);
        System.out.printf("==== after sort %s ====%n%n", Arrays.asList(a));
        
        Comparable[] b = {1,3,3,2,5};
        System.out.printf("==== before sort %s ====%n ", Arrays.asList(b));
        mergeSort(b);
        System.out.printf("==== after sort %s ====%n%n", Arrays.asList(b));
        
        Comparable[] c = {1,3,3,2,5};
        System.out.printf("==== before sort %s ====%n ", Arrays.asList(c));
        insertionSort(c);
        System.out.printf("==== after sort %s ====%n%n", Arrays.asList(c));
    }

    
    public static<T extends Comparable<? super T>> void insertionSort(T[] data) {
        for (int index=1; index<data.length; index++) {
            T key = data[index]; 
            int pos = index;
            while (pos > 0 && data[pos-1].compareTo(key) > 0) {
                data[pos] = data[pos-1];
                pos--;
            }
            data[pos] = key;
        }
    }
    
    public static<T extends Comparable<? super T>> void bubbleSort(T[] data) {
        for (int i=data.length-1; i>0; i--) {
            for (int j=0; j<i; j++) {
                if (data[j].compareTo(data[j+1]) > 0) {
                    T tmp = data[j+1];
                    data[j+1] = data[j];
                    data[j] = tmp;
                 }
            }
        }
    }
    
    public static<T extends Comparable<? super T>> void mergeSort(T[] data) {
        T[] tmp = (T[]) new Comparable[data.length];
        sort(data, tmp, 0, data.length - 1);
    }
    
    private static<T extends Comparable<? super T>> void sort(T[] data, T[] tmp, int start, int end) {
        if (start < end) {
            int mid = ( start + end ) / 2;
            sort(data, tmp, start, mid);
            sort(data, tmp, mid + 1, end);
            merge(data, tmp, start, mid+1, end);
        }
    }
    
    private static<T extends Comparable<? super T>> void merge(T[] data, T[] tmp, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int leftPos2 = leftPos;
        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            if (data[leftPos].compareTo(data[rightPos]) > 0) {
                tmp[tmpPos++] = data[rightPos++];
            } else {
                tmp[tmpPos++] = data[leftPos++];
            }
        }
        
        while (leftPos <= leftEnd) {
            tmp[tmpPos++] = data[leftPos++];
        }
        
        while (rightPos <= rightEnd) {
            tmp[tmpPos++] = data[rightPos++];
        }
        
        for (; rightEnd >= leftPos2; rightEnd--) {
            data[rightEnd] = tmp[rightEnd];
        }
    }
}
