package com.company;

import java.io.*;

public class ReaderCSV {
    public static void main(String[] args)
    {
        String line = "";
        String splitBy = ",";
        try
        {
//parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("/home/ilja/Desktop/Teacher DB/Email Buffer - NEt.csv"));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] listCSV = line.split(splitBy);    // use comma as separator
                System.out.println("Email=" + listCSV[0]);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}