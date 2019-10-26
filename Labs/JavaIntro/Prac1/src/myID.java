package src;

/**
 * A simple program that prints a greeting message
 * File: myID.java
 */

import java.io.*;
import java.util.*;

public class myID
{
   public static void main(String[] args)
   {
//      List<String> dataTypes = Arrays.asList("Name", "DoB", "Age");
//      List<UserDatum> userData = new ArrayList<>();
//      IoMan ioMan = new IoMan();
//
//      for (String dataType : dataTypes) {
//          userData.add(ioMan.requestUserDatum(dataType));
//      }
//      ioMan.selectFromMenu(userData);
       try {
           Scanner sfile = new Scanner(new File("/home/dan/Desktop/uni/scc19/JavaIntro/Prac1/module.txt"));
           while (sfile.hasNext()) {
            System.out.println(sfile.nextLine());
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }

   }
}

