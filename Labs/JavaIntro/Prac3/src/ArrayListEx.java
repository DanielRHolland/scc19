/* source file that contains the main function that initializes a string
   List and calls a method (findMax()) of another class (MaxStr)
   to find the longest string in the array list
*/

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListEx
{
   public static void main(String[] args)
   {
     // Create a dynamic list of Strings (ArrayList<String>) and intialise it
      ArrayList<String> s = new ArrayList<String>();
      s.add("potato");
      s.add("cucumber");
      s.add("onion");
      s.add("cabbage");
      s.add("mint");

      //iterate through the List and print its String elements
      Iterator<String> itr = s.iterator();
      while(itr.hasNext()){
          System.out.println(itr.next());
      }

      // create a sort object
      MaxStr myMaxStr = new MaxStr();
      // call the findMax function to find the longest String
      String longestStr = myMaxStr.findMax(s);
      // print the mximum
      System.out.println("The longest String is: " + longestStr);

   }
}

