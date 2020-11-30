package FileHandling;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;


public class FileHandling {

    public static void createFile(String file_name) {
        try {
            File fileObj = new File(file_name);
            if (fileObj.createNewFile())
                System.out.println("File created: " + fileObj.getName());
            else
                System.out.println("File: " + fileObj.getName() + " already exists");
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void getInformation(String file_name) {
        File fileObj = new File(file_name);
        if (fileObj.exists()) {
            System.out.println("File name: " + fileObj.getName());
            System.out.println("Absolute path: " + fileObj.getAbsolutePath());
            System.out.println("Writeable: " + fileObj.canWrite());
            System.out.println("Readable: "+ fileObj.canRead());
            System.out.println("File size in bytes: " + fileObj.length());
        }
        else
            System.out.println("The file does not exist");
    }

    public static void writeToFile(String file_name, String data) {
        try {
            FileWriter fileObj = new FileWriter(file_name);
            fileObj.write(data);
            fileObj.close();
            System.out.println("Successfully wrote to the file");
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void readToFile(String file_name) {
        try {
            File fileObj = new File(file_name);
            Scanner myReader = new Scanner(fileObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }
}
