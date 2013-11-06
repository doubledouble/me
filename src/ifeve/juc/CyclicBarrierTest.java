package ifeve.juc;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int row = 10000;
        int column = 50;
        int searchNumber = 5;
        int allow_threads = 10;
        int interval = row / allow_threads;
        int mod = row % allow_threads;
        MatrixMock matrix = new MatrixMock(row, column, searchNumber);
        Results results = new Results(row);
        CyclicBarrier barrier = new CyclicBarrier(allow_threads, new Group(results));
        
        for (int i = 0 ; i < allow_threads; i++) {
            int fromRow = i * interval;
            int toRow = (i + 1) * interval;
            if (i == (allow_threads - 1) ) {
                toRow += mod;
            }
            new Thread(new Searcher(matrix, fromRow, toRow, barrier, results, searchNumber),
                    "Searcher-" + fromRow + "-" + toRow ).start();
        }
        System.out.println("==== matrix.counter ====" + matrix.getCounter());
        
    }

}

class MatrixMock {
    
    private int row;
    private int column;
    private int[][] data;
    
    private int counter;

    public MatrixMock(int row, int column, int number) {
        this.row = row;
        this.column = column;
        this.data = new int[row][column];
        Random random = new Random();
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                data[i][j] = random.nextInt(10) + 1; // [1 , 10]
                if (data[i][j] == number) 
                    counter++;
            }
        }
        System.out.println("==== the matrix has built ====");
    }
    
    public int[] getRow(int row) {
        if (row >= 0 && row <= this.row) {
            return data[row];
        }
        return null;
    }
    
    public int getCounter() {
        return this.counter;
    }

    public int getColumn() {
        return column;
    }
    
}
class Results {
    
    private int[] data;
    
    public Results(int row) {
        this.data = new int[row];
    }
    
    public void setData(int row, int counter) {
        this.data[row] = counter;
    }
    
    public int[] getData() {
        return this.data;
    }
    
}

class Searcher implements Runnable {

    private MatrixMock matrix;
    private int fromRow;
    private int toRow;
    private final CyclicBarrier barrier;
    private Results results;
    private int searchNumber;
    
    public Searcher(MatrixMock matrix, int fromRow, int toRow,
            CyclicBarrier barrier, Results results, int searchNumber) {
        this.matrix = matrix;
        this.fromRow = fromRow;
        this.toRow = toRow;
        this.barrier = barrier; 
        this.results = results;
        this.searchNumber = searchNumber;
    }


    @Override
    public void run() {
        int column = matrix.getColumn();
        for (int i = this.fromRow; i < this.toRow; i++) {
            int[] row = matrix.getRow(i);
            int counter = 0;
            for (int j = 0; j < column; j++) {
                if (row[j] == this.searchNumber) {
                    counter++;
                }
                results.setData(i, counter);
            }
        }
        System.out.printf("==== from %d to %d end ====%n", this.fromRow, this.toRow);
        
        try {
//            System.out.printf("==== before waitting %d====%n", barrier.getNumberWaiting());
            barrier.await(); 
//            System.out.printf("==== after waitting %d====%n", barrier.getNumberWaiting());
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
    
}

class Group implements Runnable {
    
    private Results results;
    public Group(Results results) {
        this.results = results;
    }

    @Override
    public void run() {
        int counter = 0;
        int[] data = results.getData();
        for (int count : data) {
            counter += count; 
        }
        System.out.println("==== the final results ====" + counter);
    }
}