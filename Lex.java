//-----------------------------------------------------------------------------
//  Lex.java
//  An integer queue ADT
//  Tarik Zeid
//  Cruzid: tzeid
//  Id number: 1499437
//  PA1
//-----------------------------------------------------------------------------

import java.util.Scanner;
import java.io.*;

public class Lex {
   public static void main(String[] args) throws IOException {
     
     PrintWriter outFile = null; 
     Scanner inFile = null;
     String[] token = null;
     int line = -1;
      
      // If two arguments aren't passed than the program will exit
      if(args.length != 2) {
         System.err.println("Usage: infile outfile");
         System.exit(1);
      }

      //Gets file
      inFile = new Scanner(new File(args[0]));

      // initializes a counter to zero (counts line number)
      int counter = 0;
      
      //if there exists a line increments line
      while(inFile.hasNextLine()) {
         ++counter;
         inFile.nextLine();
      }
      //closes file
      inFile.close();
      inFile = null;

      // initializes scanner object an tokenization 
      List list = new List();
      outFile = new PrintWriter(new FileWriter(args[1]));
      inFile = new Scanner(new File(args[0]));
      token = new String[counter];

      // create array of strings
      while(inFile.hasNextLine()) {
         token[++line] = inFile.nextLine();
      }
     
      // Puts first line in list
      list.append(0);

      // Insertion Sort excluding last element 
      for(int j = 1; j < token.length; ++j) {
         String tmp = token[j];
         int i = j - 1;
         list.moveBack();
         // compare corresponding strings
         while(i >= 0 && tmp.compareTo(token[list.get()]) <= 0) {
            --i;
            list.movePrev();
         }
         //check if fell off list
         if(list.index() >= 0)
            list.insertAfter(j);
         else
            list.prepend(j);
      }

      
      list.moveFront();
     //while off the side of the list 
      while(list.index() >= 0) {
         outFile.println(token[list.get()]);
         list.moveNext();
      }
      inFile.close();
      outFile.close();
   }
}