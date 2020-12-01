package SlangDictionaryHandling;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

import FileHandling.*;


public class SlangDictionary {

    private HashMap<String, ArrayList<String>> m_slangDic;

    public SlangDictionary() {
        m_slangDic = new HashMap<String, ArrayList<String>>();
    }

    //Load slangDic From file_name
    public void loadSlangWordData(String file_name) {
        SlangFileHandling.readFromFile(file_name, m_slangDic);
    }

    //Write Data To File slangWordDictionary Be Updated
    public void updateFileData(String file_name) {
        FileHandling.writeToFile(file_name, "Slag`Meaning");
        for (String slangWord : m_slangDic.keySet()) {
            for (String slangDefinition : m_slangDic.get(slangWord)) {
                String data = slangWord + "`" + slangDefinition;
                FileHandling.writeToFileAppend(file_name, data);
            }
        }
    }

    //Option 01: Enter slangWord -> Find slangDefinition. InCaseSensitive
    public void searchDefinition(String key) {
        for (String slangWord : m_slangDic.keySet()) {
            if (slangWord.equalsIgnoreCase(key)) {
                ArrayList<String> slangDefinitions = m_slangDic.get(key);
                System.out.println("THERE ARE " + slangDefinitions.size() + " MEANINGS OF " + key + " SLANG WORD");
                for (String slangDefinition : m_slangDic.get(key))
                    System.out.println("\t" + slangDefinition);
                return;
            }
        }
        System.out.println("DONT HAVE SLANG WORD " + key + " ON DATABASE");
    }

    //Option 02: Enter slangDefinition -> Find slangWord. InCaseSensitive
    public void searchSlangWord(String key) {
        ArrayList<String> slangWords = new ArrayList<>();
        for (String slangWord : m_slangDic.keySet()) {
            ArrayList<String> slangDefinitions = m_slangDic.get(slangWord);
            for (String slangDefinition : slangDefinitions) {
                if (slangDefinition.toLowerCase().contains(key.toLowerCase())) {
                    slangWords.add(slangWord);
                    break;
                }
            }
        }
        System.out.println("FOUND " + slangWords.size() + " SLANG WORDS FOR " + key + " KEY WORDS DEFINITION");
        for (String slangWord : slangWords)
            System.out.println("\t" + slangWord);
    }

    //Option 03: Show History slangWord Search
    public void recordHistory(String file_name, String data) {
        FileHandling.writeToFileAppend(file_name, data);
    }

    public void showHistory(String file_name) {
        System.out.println("THERE ARE HISTORIES SLANG WORD WERE SEARCHED");
        FileHandling.readFromFile(file_name);
    }

    //Option 04: Add New slangWord - slangDefinition. CaseSensitive
    public void addSlangWord(String newSlangWord, String newDefinition) {
       if (m_slangDic.containsKey(newSlangWord)) {
           ArrayList<String> slangDefinitions = m_slangDic.get(newSlangWord);
           System.out.println("ORIGINAL SLANG WORD " + newSlangWord + " HAS " + slangDefinitions.size() + " MEANINGS");
           for (String slangDefinition : slangDefinitions)
               System.out.println("\t" + slangDefinition);

           System.out.println("PRESS 1: TO OVERWRITE ITS MEANINGS");
           System.out.println("PRESS 2: TO ADD NEW MEANING OF THIS SLANG WORD");
           Scanner myReader = new Scanner(System.in);
           int choose = myReader.nextInt();

           if (choose == 1) {
               slangDefinitions.clear();
               slangDefinitions.add(newDefinition);
               System.out.println("SUCCESSFULLY OVERWRITE");
           }
           else if (choose == 2) {
               slangDefinitions.add(newDefinition);
               System.out.println("SUCCESSFULLY ADD NEW MEANING");
           }
           else {
               System.out.println("PLEASE PRESS ONLY 1 OR 2");
               addSlangWord(newSlangWord, newDefinition);
               return;
           }
           //Update File Data (Write Again)
           updateFileData("resources/slang.txt");
       }

       else {
           ArrayList<String> newDefinitions = new ArrayList<>();
           newDefinitions.add(newDefinition);
           m_slangDic.put(newSlangWord, newDefinitions);
           String data = newSlangWord + "`" + newDefinition;
           FileHandling.writeToFileAppend("resources/slang.txt", data);
           //Update File Data (Write Append)
           System.out.println("SUCCESSFULLY ADD NEW SLANG WORD");
       }
    }
}
