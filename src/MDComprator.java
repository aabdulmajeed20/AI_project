import java.util.Comparator;

public class MDComprator implements Comparator<Node>{
	
	@Override
    public int compare(Node x, Node y)
    {
        
        if (x.h_md() < y.h_md())
        {
            return -1;
        }
        if (x.h_md() > y.h_md())
        {
            return 1;
        }
        return 0;
    }

}
