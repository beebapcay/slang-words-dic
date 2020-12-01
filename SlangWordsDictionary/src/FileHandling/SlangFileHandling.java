package FileHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

import FileHandling.FileHandling;


public class SlangFileHandling extends FileHandling{

    public static void readFromFile(String file_name, HashMap<String, ArrayList<String>> slangDic) {
        try {
            File fileObj = new File(file_name);
            Scanner myReader = new Scanner(fileObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                StringBuilder newSlangWord = new StringBuilder("");
                StringBuilder newSlangDefinition = new StringBuilder("");
                convertToSlangWord(line, newSlangWord, newSlangDefinition);
                if (newSlangWord.toString() != "") {
                    if (slangDic.containsKey(newSlangWord)) {
                        ArrayList<String> slangDefinitions = slangDic.get(newSlangWord);
                        if (!slangDefinitions.contains(newSlangDefinition))
                            slangDefinitions.add(newSlangDefinition.toString());
                    }
                    else {
                        ArrayList<String> newSlangDefinitions = new ArrayList<String>();
                        newSlangDefinitions.add(newSlangDefinition.toString());
                        slangDic.put(newSlangWord.toString(), newSlangDefinitions);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void convertToSlangWord(String line, StringBuilder slangWord, StringBuilder slangDefinition) {
        int idx = line.indexOf("`");
        if (idx != -1) {
            slangWord.replace(0, slangWord.length(), line.substring(0, idx));
            slangDefinition.replace(0, slangWord.length(), line.substring(idx + 1));
        }
    }

}
