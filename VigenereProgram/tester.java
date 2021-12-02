import java.util.*;
import edu.duke.*;
/**
 * Write a description of tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class tester {
    public void testCaesarCipher(){
     CaesarCipher cc = new CaesarCipher(3); 
     FileResource fr = new FileResource("titus-small.txt");
     String input = fr.asString();
     String enc = cc.encrypt(input);
     System.out.println(input + " is encryptes as: \n" + enc); 
     String decry = cc.decrypt(enc);
     System.out.println(enc + " is decrypted as: \n" + decry);
    }
    
    public void testSliceString(){
     VigenereBreaker vb = new VigenereBreaker();
     String message = "abcdefghijklm";
     int whichSlice = 4;
     int totalSlices = 5;
     String everyTotalSlices = vb.sliceString(message,whichSlice,totalSlices);
     System.out.println("totalSlices with " + whichSlice + " which slice and "
                    + totalSlices + " total slices is: " + everyTotalSlices);
    }
    
    public void testTryKeyLength(){
     VigenereBreaker vb = new VigenereBreaker();
     FileResource fr = new FileResource("secretmessage1.txt");
     String encrypted = fr.asString();
     //String k = "flute";
     int klength = 38;
     char mostCommon = 'e';
     int[] key = vb.tryKeyLength(encrypted,klength,mostCommon);
     System.out.print("{");
     for(int i = 0; i < key.length; i++){
         System.out.print(key[i] + ", ");
        }
     System.out.print("}\n");
    }
    
    public void testBreakVigenere(){
       VigenereBreaker vb = new VigenereBreaker();
       vb.breakVigenere();
    }
}
