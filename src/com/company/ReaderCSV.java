package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ReaderCSV {
    public static void main(String args[]) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("/home/ilja/Desktop/Teacher DB/Email Buffer - NEt.csv"));
        sc.useDelimiter(",");   //sets the delimiter pattern
        while (sc.hasNext())  //returns a boolean value
        {
            System.out.print(sc.next());  //find and returns the next complete token from this scanner
        }
        sc.close();  //closes the scanner
    }
}