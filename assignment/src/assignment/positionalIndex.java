package assignment;
import java.io.*;
import java.util.*;

public class positionalIndex {
    public TreeMap<String, term> Map = new TreeMap<String, term>();
    
    public positionalIndex createIndex(helping h)throws Exception{
        String line;
        
        File folder = new File("..//ShortStories");
        File[] listOfFiles = folder.listFiles();
        BufferedReader br = null;
        
        int k=0,pos=0;
        for (File file : listOfFiles){
            pos=0;
            String name = file.getName().replaceFirst("[.][^.]+$", "");

            k = Integer.parseInt(name);
            if (file.isFile()){
                System.out.println(file.getPath());
                
                InputStream inputStream = new FileInputStream(file.getPath());
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                //Instantiate the BufferedReader Class
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
                            pos++;
                            if(Map.get(key)==null){
                                term t = new term();
                                t.docFreq++;
                                t.docNum.add(k);
                                t.docPos.add(new ArrayList<Integer>());
                                t.docPos.get(0).add(pos);
                                this.Map.put(key, t);
                            }
                            else{
                                term t = this.Map.get(key);
                                t.docFreq++;
                                if(t.docNum.contains(k)){
                                    for(int i=0; i<t.docNum.size(); i++){
                                        if(t.docNum.get(i)==k){
                                            t.docPos.get(i).add(pos);
                                            this.Map.put(key, t);
                                            break;
                                        }
                                    }
                                }
                                else{
                                    t.docNum.add(k);
                                    t.docPos.add(new ArrayList<Integer>());
                                    t.docPos.get(t.docNum.size()-1).add(pos);
                                    this.Map.put(key, t);
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
    
    public void writeIndexToFile ( ) throws IOException {
        File file = new File("..//positional-Index.txt");
        
        if ( !file.exists() ) 
            file.createNewFile();
        
        FileWriter f = new FileWriter(file);
        BufferedWriter b = new BufferedWriter(f);
        
        int j=0;
        for ( Map.Entry<String, term> entry : Map.entrySet() ){
            if ( j>=1 ){
                b.write(entry.getKey() + " , " + entry.getValue().docFreq+ " :");
                b.newLine();
                for ( int i=0; i<entry.getValue().docNum.size(); i++ ){
                    b.write(entry.getValue().docNum.get(i) + " : ");
                    for ( int l=0; l<entry.getValue().docPos.get(i).size(); l++ ){
                        b.write(entry.getValue().docPos.get(i).get(l) + " ");
                    }
                    b.newLine();
                }
                b.newLine();
            } else ++j;
        }
        b.flush();
        b.close();
        f.close();
    }
    
    public positionalIndex loadIndex(helping h, positionalIndex p) throws FileNotFoundException, IOException{
        File file = new File("..//positional-Index.txt");
        FileReader fr=new FileReader(file);
        BufferedReader br=new BufferedReader(fr);    
  
        int i,size;
        String line;
        ArrayList<Integer> list;
        term t = new term();
        while((line = br.readLine()) != null){
            list = new ArrayList<Integer>();
            String[] array = line.split(" ");
            String key = array[0];
            size=Integer.parseInt(array[2]);
            t.docFreq=size;

            while(true){
                line = br.readLine();
                if(line == null) break;
                if(line.equals("")) break;
                String[] array2 = line.split(" ");
                int z = Integer.parseInt(array2[0]);
                t.docNum.add(z);
                
                for(int j=2; j<array2.length; j++){
                    i = Integer.parseInt(array2[j]);

                    t.docPos.add(new ArrayList<Integer>());
                    t.docPos.get(t.docNum.size()-1).add(i);
                }
            }

            p.Map.put(key, t);
            t = new term();
        }
        br.close();
        fr.close();
        return this;
    }
}
