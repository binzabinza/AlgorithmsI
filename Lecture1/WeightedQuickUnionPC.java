public class WeightedQuickUnionPC
{
    private int[] id;
    private int[] sz;

    public WeightedQuickUnionPC(int N){
        /* This constructor creates an array where the id of each object points to itself*/
        id = new int[N];
        sz = new int[N];
        for (int i=0; i < N; i++){
            id[i] = i;
            sz[i] = 1;
        }
    }
    
    private int root(int i){
        while (i != id[i]){
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p, int q){
        //Check whether p and q have the same root
        return root(p) == root(p);
    }

    public void union(int p, int q){
        int i = root(p);
        int j = root(q);
        if (i == j) { return; }
        if (sz[i]  < sz[j]){
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }

    public void print_id(){
        System.out.print("[");
        for (int i=0; i < id.length; i++){
            System.out.print(id[i]);
            if (i != id.length-1) System.out.print(", ");
        }
        System.out.println("]");
    }

    /* MAIN */
    public static void main(String[] args){
        WeightedQuickUnionPC q = new WeightedQuickUnionPC(10);
        q.print_id();
        q.union(3, 6);
        q.union(1, 5);
        q.union(3, 4);
        q.print_id();
    }
}