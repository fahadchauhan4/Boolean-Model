package assignment;
import java.util.ArrayList;

public class term{
    public int docFreq = 0;
    public ArrayList<Integer> docNum;
    public ArrayList<ArrayList<Integer>> docPos;
    
    public term(){
        docNum = new ArrayList<Integer>();
        docPos = new ArrayList<ArrayList<Integer>>();
    }
}