package assignment;
import java.io.*;
import java.util.*;

public class QuerySearch{
    
    public ArrayList<Integer> searchInverted(String query, invertedIndex ii){
        ArrayList<Integer> res = new ArrayList<Integer>();
        String[] array = query.split(" ");
        if(array.length>0){
            int start=2;
            if(array[0].equals("not")){
                res = ii.Map.get(array[1]);
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    for ( int i=1; i<ii.Map.size(); ++i ) {
                        if ( !res.contains(i) ) temp.add(i);
                    }
                res = temp;
                start=3;
            }
            else{
                res = ii.Map.get(array[0]);
            }
            for(int i=start; i<array.length; i+=2){
                if(array[i-1].equals("and")){
                    if(!ii.Map.containsKey(array[i-2])) return (new ArrayList<Integer>());
                    if(!(array[i].equals("not"))){
                        if ( ii.Map.containsKey(array[i]) ){
                            ArrayList<Integer> temp = new ArrayList<Integer>();
                            ArrayList<Integer> temp2 = new ArrayList<Integer>();
                            temp.addAll(ii.Map.get(array[i]));
                            for(int z=0; z<res.size(); z++){
                                for(int y=0; y<temp.size(); y++){
                                    if(res.get(z)==temp.get(y)){
                                        temp2.add(res.get(z));
                                    }
                                }
                            }
                            res=temp2;
                            res=temp2;
                        }
                        else return (new ArrayList<Integer>());
                    }
                    else{
                        ArrayList<Integer> tmp = new ArrayList<Integer>();
                        for ( int j=0; j<ii.Map.size(); ++j ) tmp.add(j+1);
                        if ( ii.Map.containsKey(array[i+1]) ) {
                            tmp.removeAll(ii.Map.get(array[i+1]));
                            res.retainAll(tmp);
                            i = i+1;
                        }
                    }
                }
                else if(array[i-1].equals("or")){
                    if(!(array[i].equals("not"))){
                        if(ii.Map.containsKey(array[i])){
                            ArrayList<Integer> temp = new ArrayList<Integer>();
                            temp.addAll(ii.Map.get(array[i]));
                            temp.addAll(res);
                            res=temp;
                        }
                    }
                    else{
                        ArrayList<Integer> tmp = new ArrayList<Integer>();
                        for (int j=0; j<ii.Map.size(); j++) tmp.add(j+1);
                        if (ii.Map.containsKey(array[i+1])){
                            tmp.removeAll(ii.Map.get(array[i+1]));
                            res.addAll(tmp);
                            i = i+1;
                        }
                    }
                }
            }
        }
        res = removeDuplicate(res);
        return res;
    }
    public ArrayList<Integer> SearchSingle ( String query, invertedIndex ii) {
        String[] array = query.split(" ");
        
        ArrayList<Integer> list = new ArrayList<Integer>();
        if ( array.length > 0 ) {
            list = ii.Map.get(array[0]);
            if ( array.length > 1 ) {
                for ( int i=1; i<array.length; ++i ) {
                    if ( ii.Map.containsKey(array[i]) ) list.retainAll(ii.Map.get(array[i]));
                    else return (new ArrayList<Integer>());
                }
            }
        }
        return list;
    }
    private ArrayList<Integer> removeDuplicate( ArrayList<Integer> list ){
        ArrayList<Integer> unique = new ArrayList<Integer>();
        for (int i=0; i<list.size(); i++ ){
            if ( !unique.contains(list.get(i)) ) unique.add(list.get(i));
        }
        return unique;
    }
    public ArrayList<Integer> searchProximity(term p1, term p2, int k){
        ArrayList<Integer> res = new ArrayList<Integer>();
        int a,b, dist;
        for(int i=0; i<p1.docNum.size(); i++){
            for(int j=0; j<p2.docNum.size(); j++){
                System.out.println("doc p1 size: " + p1.docNum.get(i));
                System.out.println("doc p2 size: " + p2.docNum.get(j));
                if(p1.docNum.get(i) == p2.docNum.get(j)){
                    int m=0, n=0;
                    while(m < p1.docPos.get(i).size() && n < p2.docPos.get(j).size()){
                            a = p1.docPos.get(i).get(m);
                            b = p2.docPos.get(j).get(n);
                            if(a<b){
                                dist = b-a;
                                
                                if(dist <= k){
                                    res.add(p1.docNum.get(i));
                                    System.out.println("\nResult: " + p1.docNum.get(i));
                                    n++;
                                    break;
                                }
                                m++;
                            }
                            else if(a>b){
                                n++;
                            }
                            else{
                                n++;
                            }
                    }
                }
            }
        }
        
        res = removeDuplicate(res);
        return res;
    }
}
