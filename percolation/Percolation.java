//@formatter:off
/* *****************************************************************************
 *  Name: John Binzer
 *  Date: 1/05/2020
 *  Description: Percolation class
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private boolean[][] full;
    private int openSites = 0;
    private int size;
    private WeightedQuickUnionUF uf;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // create a grid where True == open and False == blocked
        this.grid = new boolean[n][n];
        this.full = new boolean[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                this.grid[row][col] = false;
                this.full[row][col] = false;
            }
        }
        this.size = n;
        this.uf = new WeightedQuickUnionUF(n * n + 2); // we add two because of the virtual sites
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > this.size || col < 1 || col > this.size) { throw new IllegalArgumentException(); }
        if (!isOpen(row, col)) {
            this.grid[row - 1][col - 1] = true;
            this.openSites++;

            int arrayIndex = index(row, col);
            // need to build out the connections
            if (row - 1 > 0 && isOpen(row - 1, col)){ this.uf.union(arrayIndex, index(row - 1, col)); }
            if (row + 1 <= this.size && isOpen(row + 1, col)) { this.uf.union(arrayIndex, index(row + 1, col)); }
            if (col - 1 > 0 && isOpen(row, col - 1)) { this.uf.union(arrayIndex, index(row, col - 1)); }
            if (col + 1 <= this.size && isOpen(row, col + 1)) { this.uf.union(arrayIndex, index(row, col + 1)); }

            // virtual sites
            if (row == 1) { this.uf.union(0, arrayIndex); }
            if (row == this.size) { this.uf.union(arrayIndex, this.size * this.size + 1); }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // this should work for (1,1) indexing
        if (row < 1 || row > this.size || col < 1 || col > this.size) { throw new IllegalArgumentException(); }
        return this.grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // this should work for (1,1) indexing
        if (row < 1 || row > this.size || col < 1 || col > this.size) { throw new IllegalArgumentException(); }
        if (!this.full[row-1][col-1]) { this.full[row-1][col-1] = this.uf.connected(0, index(row, col)); }
        return this.full[row-1][col-1];
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


    /*****************************
     * My private helper methods *
     *****************************/
    private void prettyPrintGrid() {
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                System.out.print("[" + this.grid[row][col] + "]");
            }
            System.out.println();
        }
    }

    private int index(int row, int col) {
        return (row - 1) * this.size + col;
    }

    //my test stuff
    public static void main(String[] args) {
        Percolation foo = new Percolation(4);
        // System.out.println(foo.size);
        foo.prettyPrintGrid();
        System.out.println();
        foo.open(1, 1);
        foo.open(1, 3);
        foo.open(2, 2);
        foo.open(1, 2);
        foo.open(3, 2);
        foo.open(4, 2);
        foo.prettyPrintGrid();
        System.out.print(foo.percolates());
    }
}
