package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserReaderCSV {


    public static void main(String[] args) throws IOException {
        // open file input streams
        UserReaderCSV userReaderCSV = new UserReaderCSV();
        BufferedReader reader = new BufferedReader(new FileReader(
                userReaderCSV.inputReciever()));

        // read file line by line
        String line = null;
        Scanner scanner = null;
        int index = 0;

        List<EmailUser> empList = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            EmailUser emp = new EmailUser();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();
                if (index == 0)
                    emp.setEmail(data);
                else
                    break;
                index++;
            }
            index = 0;
            empList.add(emp);
        }

        //close reader
        reader.close();

        System.out.println("List size :: " + empList.size());

    }
    public String inputReciever(){
        System.out.print("Enter a fileAddress : ");
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        System.out.println(inputString);
        return inputString;
    }

}
