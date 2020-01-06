public class QuickFindUF
{
    private int[] id;

    public QuickFindUF(int N){
        /* This constructor creates an array where the id of each object points to itself*/
        id = new int[N];
        for (int i=0; i < N; i++){
            id[i] = i;
        }
    }
    
    public boolean connected(int p, int q){
        //Check whether p and q have the same root
        return id[p] == id[q];
    }

    public void union(int p, int q){
        int pid = id[p];
        int qid = id[q];
        for (int i=0; i < id.length; i++){
            if (id[i] == pid){
                id[i] = qid;
            }
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
        QuickFindUF q = new QuickFindUF(10);
        q.print_id();
        q.union(3, 6);
        q.union(1, 5);
        q.union(3, 4);
        q.print_id();
    }
}