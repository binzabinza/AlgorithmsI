//@formatter:off
/* *****************************************************************************
 *  Name: John Binzer
 *  Date: 1/05/2020
 *  Description: Percolation class
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int openSites = 0;
    private int size;
    private WeightedQuickUnionUF uf;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        //create a grid where True == open and False == blocked
        this.grid = new boolean[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                this.grid[row][col] = false;
            }
        }
        this.size = n;
        this.uf = new WeightedQuickUnionUF(n * n + 2); //we add two because of the virtual sites
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        row -= 1;
        col -= 1;
        if (!isOpen(row + 1, col + 1)) {
            this.grid[row][col] = true;
            this.openSites++;

            int arrayIndex = index(row, col);
            //need to build out the connections
            int[] adj = adjacent(row, col);
            for (int i=0; i < adj.length; i++){
                if (adj[i] != -1){
                    // System.out.printf("(%d, %d)", arrayIndex, adj[i]);
                    this.uf.union(arrayIndex, adj[i]);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        row -=1;
        col -=1;
        if (row < 0 || row >= this.size || col < 0 || col >= this.size){
            return false;
        }
        return this.grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        row -=1;
        col -=1;
        return this.uf.connected(0, index(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // System.out.print(this.uf.connected(0, this.size*this.size + 1));
        return this.uf.connected(0, this.size*this.size + 1);
    }


    //My private methods
    private void prettyPrintGrid() {
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                System.out.print("[" + this.grid[row][col] + "]");
            }
            System.out.println();
        }
    }

    private int index(int row, int col) {
        return row * this.size + col + 1; //+1 because of the virtual site at zero
    }

    private int[] adjacent(int row, int col) {
        int[] result = new int[] { index(row - 1, col), index(row + 1, col), index(row, col + 1), index(row, col - 1) };
        //handle virtual connections
        for (int i = 0; i < result.length; i++) {
            if (result[i] <= 0) { result[i] = 0; } //top row, connection to virtual site
            else if (result[i] >= this.size * this.size + 1) { result[i] = this.size * this.size + 1; } //bottom row, connection to virtual site
        }

        if (!isOpen(row - 1 + 1, col + 1)) { result[0] = -1; }
        if (!isOpen(row + 1 + 1, col + 1)) { result[1] = -1; }
        if (!isOpen(row + 1, col + 1 + 1)) { result[2] = -1; }
        if (!isOpen(row + 1, col - 1 + 1)) { result[3] = -1; }
        if (row - 1 == 0) { result[0] = 0;}
        if (row + 1 == this.size) { result[1] = this.size * this.size + 1;}

        // for (int i=0; i<result.length; i++){
        //     System.out.print(" " + result[i]);
        // }
        // System.out.println();
        return result;
    }

    //my test stuff
    public static void main(String[] args) {
        Percolation foo = new Percolation(4);
        // System.out.println(foo.size);
        foo.prettyPrintGrid();
        foo.open(1, 1);
        foo.open(0, 2);
        foo.open(1, 3);
        foo.open(2, 2);
        foo.open(1, 2);
        foo.open(3, 2);
        foo.prettyPrintGrid();
        System.out.print(foo.percolates());
    }
}
