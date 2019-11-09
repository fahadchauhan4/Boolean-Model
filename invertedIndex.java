package assignment;
import java.io.*;
import java.util.*;

public class invertedIndex{
    TreeMap<String,ArrayList<Integer>> Map;
    invertedIndex(){
        Map = new TreeMap<String,ArrayList<Integer>>();
    }
    
    public invertedIndex createIndex(helping h)throws Exception{
        String line;
        
        File folder = new File("..//ShortStories");
        File[] listOfFiles = folder.listFiles();
        BufferedReader br = null;
        
        int k=0;
        for (File file : listOfFiles){
            String name = file.getName().replaceFirst("[.][^.]+$", "");

            k = Integer.parseInt(name);
            if (file.isFile()){
                System.out.println(file.getPath());
                
                InputStream inputStream = new FileInputStream(file.getPath());
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                br = new BufferedReader(streamReader);

                while((line = br.readLine()) != null){
                    String[] words = line.split(" ");

                    for(int j=0; j<words.length; j++){
                        int c=0;
                        String key = words[j].toLowerCase();
                        key = key.replaceAll("[^A-Za-z0-9 ]","");
                        boolean check;
                        check = h.checkStopword(key);
                        if(check == false){
                            if(Map.get(key)==null){
                                ArrayList<Integer> a = new ArrayList<Integer>();
                                a.add(k);
                                Map.put(key, a);
                            }
                            else{
                                ArrayList<Integer> a = new ArrayList<Integer>();
                                a=Map.get(key);
                                for(int z = 0; z<a.size() ;z++){
                                    if(k == a.get(z)){
                                        c=1;
                                    }
                                }
                                if(c==0){
                                    a.add(k);
                                    Map.put(key,a);
                                }
                            }
                        }
                    }
                }
                br.close();
            }
        }
        return this;
    }
    
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
    
    public void writeIndexToFile ( ) throws IOException {
        File file = new File("..//inverted-Index.txt");
        
        if ( !file.exists() ) 
            file.createNewFile();
        
        FileWriter f = new FileWriter(file);
        BufferedWriter b = new BufferedWriter(f);
        
        int j=0;
        for ( Map.Entry<String, ArrayList<Integer>> entry : Map.entrySet() ){
            if ( j>=1 ) {
                b.write(entry.getKey() + " " + entry.getValue().size() + " -> ");
                for ( int i=0; i<entry.getValue().size(); ++i )
                    b.write(entry.getValue().get(i) + " ");
                b.newLine();
            } else ++j;
        }
        
        b.flush();
        b.close();
        f.close();
    }
    
    public invertedIndex loadIndex(helping h, invertedIndex ii) throws FileNotFoundException, IOException{
        File file = new File("..//inverted-Index.txt");
        FileReader fr=new FileReader(file);
        BufferedReader br=new BufferedReader(fr);    
  
        int i;
        String line;
        ArrayList<Integer> list;
        while((line = br.readLine()) != null){ 
            list = new ArrayList<Integer>();
            String[] array = line.split(" ");
            
            for(int j=3; j<array.length; j++){
                i = Integer.parseInt(array[j]);
                list.add(i);
            }
            ii.Map.put(array[0], list);
        }
        br.close();    
        fr.close();    
        return this;
    }
}