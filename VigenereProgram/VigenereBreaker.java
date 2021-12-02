import java.io.*;
import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder sb = new StringBuilder();
        for(int i = whichSlice; i < message.length(); i+= totalSlices){
           sb.append(message.charAt(i)); 
        }
        return sb.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cr = new CaesarCracker(mostCommon);
        String[] slices = new String[klength];
        for(int i = 0; i < klength; i++){
         slices[i] = sliceString(encrypted,i,klength);   
        }
        for(int j = 0; j < klength; j++){
         key[j] = cr.getKey(slices[j]);   
        }
        return key;
    }
     
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> dictionary = new HashSet<String>();
        for(String s : fr.lines()){
            if(s.length() != 0){
            s = s.toLowerCase();
            dictionary.add(s);
           }
        }
        return dictionary;
    }
    
    public int countWords(String message, HashSet<String> dictionary){
        String[] word = message.split("\\W+");
        int count = 0;
        for(String real : word){
           real = real.toLowerCase();
           if(dictionary.contains(real))
            count++;
        }
        return count;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary,
                                    char mostCommon){
       int klengthcopy = 1;
       int maxCount = 0;
       String decryption = "";
        char mostCommonChar = mostCommon;
        for(int klength = 1; klength < encrypted.length(); klength++){
        int[] key = tryKeyLength(encrypted,klength,mostCommon);
        VigenereCipher vc = new VigenereCipher(key);
        String answer = vc.decrypt(encrypted);
        int count = countWords(answer, dictionary);
        if(maxCount < count){
            maxCount = count;
            decryption = answer;
            klengthcopy = klength;
        }
       }
       System.out.println("key length used to encrypt the file is: " + klengthcopy);
       System.out.println("valid wors in the decrypted string: " + maxCount);
       return decryption;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary){
     HashMap<String, Integer> map = new HashMap<String, Integer>();
     int maxCount = 0;
     char mostCommon = 'e';
       for(String s : dictionary){
          for(int i = 0; i<s.length(); i++){
             String add = s.substring(i,i + 1);
              if(!map.containsKey(add))
                 map.put(add,1);
             else
                map.put(add, map.get(add) + 1);
            }
        }
      for(String mostC : map.keySet()){
          if( maxCount < map.get(mostC)){
             maxCount = map.get(mostC);
             mostCommon = mostC.charAt(0);
            }
            
        }
     return mostCommon;   
    }
    
    public void breakForAllLangs(String encrypted, 
                                    HashMap<String,HashSet<String>> languages){
        
     for(String lang : languages.keySet()){
       char mostCommon = mostCommonCharIn(languages.get(lang));
       String decry = breakForLanguage(encrypted,languages.get(lang),mostCommon);  
       System.out.println("decrption with language: " + lang);
       System.out.println(decry);
     }
    }
    
    public void breakVigenere () {
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        HashMap<String,HashSet<String>> languages;
        languages = new HashMap<String,HashSet<String>>();
        //FileResource fr2 = new FileResource("English.txt");
        DirectoryResource dr = new DirectoryResource();
        
        for(File f : dr.selectedFiles()){
          String fname = (f.getName());
          //fname = fname.substring(0,fname.length() - 4);
          FileResource langFr = new FileResource(f);
          languages.put(fname,readDictionary(langFr)); 
          System.out.println("processing " + fname 
                                + " language dictionary is done");
        }
        
        breakForAllLangs(encrypted,languages);
        //int klength = 5;
        //char mostCommon = 'e' ;
        //int[] key = tryKeyLength(encrypted,klength,mostCommon);
        
        //HashSet<String> dictionary = readDictionary(fr2);
        //VigenereCipher vc = new VigenereCipher(key);
        
        //String decryption = breakForLanguage(encrypted,dictionary);

        //String answer = vc.decrypt(encrypted);
        //System.out.println("the decrypted message is:\n\n ________\n ");
        //System.out.println(decryption);
    }
    
}
