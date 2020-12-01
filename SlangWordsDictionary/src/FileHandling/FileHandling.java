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
                System.out.println("FILE CREATED: " + fileObj.getName());
            else
                System.out.println("FILE: " + fileObj.getName() + " ALREADY EXISTS");
        } catch (IOException e) {
            System.out.println("AN ERROR OCCURRED");
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
            fileObj.write("\n");
            fileObj.close();
            //System.out.println("SUCCESSFULLY WROTE TO THE FILE");
        } catch (IOException e) {
            System.out.println("AN ERROR OCCURRED");
            e.printStackTrace();
        }
    }

    public static void writeToFileAppend(String file_name, String data) {
        try {
            FileWriter fileObj = new FileWriter(file_name, true);
            fileObj.write(data);
            fileObj.write("\n");
            fileObj.close();
            //System.out.println("SUCCESSFULLY WROTE TO THE FILE");
        } catch (IOException e) {
            System.out.println("AN ERROR OCCURRED");
            e.printStackTrace();
        }
    }

    public static void readFromFile(String file_name) {
        try {
            File fileObj = new File(file_name);
            Scanner myReader = new Scanner(fileObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("AN ERROR OCCURRED");
            e.printStackTrace();
        }
    }

    public static void deleteFile(String file_name) {
        File fileObj = new File(file_name);
        if (fileObj.delete())
            System.out.println("SUCCESSFULLY DELETED TO THE FILE");
        else
            System.out.println("AN ERROR OCCURRED");
    }

}
