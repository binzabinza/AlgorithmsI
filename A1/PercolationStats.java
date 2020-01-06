/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials, size;
    private int[] numSites;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = n * n;
        this.trials = trials;
        this.numSites = new int[trials];

        for (int trial = 0; trial < trials; trial++) {
            Percolation perc = new Percolation(n);
            int[] vals = StdRandom.permutation(n * n);
            int count = 0;
            while (!perc.percolates()) {
                int ind = vals[count];
                int row = ind / n + 1;
                int col = ind % n + 1;
                perc.open(row, col);
                count++;
            }
            this.numSites[trial] = count;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.numSites) / this.size;

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.numSites) / this.size;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(this.trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(this.trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int testCase = Integer.parseInt(args[1]);
        PercolationStats tester = new PercolationStats(n, testCase);
        System.out.format("mean                    = %f\n", tester.mean());
        System.out.format("stddev                  = %f\n", tester.stddev());
        System.out.format("95%% confidence interval = [%f, %f]\n", tester.confidenceLo(),
                          tester.confidenceHi());
    }
}
