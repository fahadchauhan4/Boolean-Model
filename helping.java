package assignment;
import java.io.*;
import java.util.*;

public class helping {
    
    public Boolean checkStopword(String key)throws Exception{
        FileReader f=new FileReader("..//Stopword-List.txt");
        BufferedReader b=new BufferedReader(f);
        String line;

        while((line = b.readLine()) != null){
            if(key.equals(line)){
                return true;
            }
        }
        return false;
    }
    
    public String print(ArrayList<Integer> list){
        String str="";
        if(list == null){
            str = "Query doesn't exist";
            return str;
        }
        str += String.valueOf(list.size()) + " Documents Found" + "\n\n";
        for(int i=0; i<list.size(); i++){
            str = str + String.valueOf(list.get(i)) + "\n";
            
        }
        return str;
    }
}
