package SlangDictionaryHandling;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

import FileHandling.*;


public class SlangDictionary {

    private HashMap<String, ArrayList<String>> m_slangDic;
    private String file_data;

    public SlangDictionary() {
        m_slangDic = new HashMap<String, ArrayList<String>>();
        file_data = "";
    }

    public SlangDictionary(String file_data) {
        m_slangDic = new HashMap<String, ArrayList<String>>();
        this.file_data = file_data;
    }

    //Load slangDic From file_name
    public void loadSlangWordData() {
        SlangFileHandling.readFromFile(file_data, m_slangDic);
    }

    //Write Data To File slangWordDictionary Be Updated
    public void updateFileData() {
        FileHandling.writeToFile(file_data, "Slag`Meaning");
        for (String slangWord : m_slangDic.keySet()) {
            for (String slangDefinition : m_slangDic.get(slangWord)) {
                String data = slangWord + "`" + slangDefinition;
                FileHandling.writeToFileAppend(file_data, data);
            }
        }
    }

    //Option 01: Enter slangWord -> Find slangDefinition. InCaseSensitive
    public void searchDefinition(String key) {
        for (String slangWord : m_slangDic.keySet()) {
            if (slangWord.equalsIgnoreCase(key)) {
                ArrayList<String> slangDefinitions = m_slangDic.get(key);
                System.out.println("THERE ARE " + slangDefinitions.size() + " MEANINGS OF " + key + " SLANG WORD");
                for (int i = 0; i < slangDefinitions.size(); i++)
                    System.out.println((i + 1) + ".\t" + slangDefinitions.get(i));
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
        for (int i = 0; i < slangWords.size(); i++)
            System.out.println((i + 1) + ".\t" + slangWords.get(i));
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
           for (int i = 0; i < slangDefinitions.size(); i++)
               System.out.println((i + 1) + ".\t" + slangDefinitions.get(i));

           System.out.println("PRESS 1: TO OVERWRITE ITS MEANINGS");
           System.out.println("PRESS 2: TO ADD NEW MEANING OF THIS SLANG WORD");
           Scanner myReader = new Scanner(System.in);
           int choose = myReader.nextInt();
           myReader.nextLine();

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
           updateFileData();
       }
       else {
           ArrayList<String> newDefinitions = new ArrayList<>();
           newDefinitions.add(newDefinition);
           m_slangDic.put(newSlangWord, newDefinitions);
           String data = newSlangWord + "`" + newDefinition;
           FileHandling.writeToFileAppend(file_data, data);
           //Update File Data (Write Append)
           System.out.println("SUCCESSFULLY ADD NEW SLANG WORD");
       }
    }

    //Option 05: Edit slangWord
    public void editSlangWord(String slangWord) {
        if (m_slangDic.containsKey(slangWord)) {
            ArrayList<String> slangDefinitions = m_slangDic.get(slangWord);
            System.out.println("ORIGINAL SLANG WORD " + slangWord + " HAS " + slangDefinitions.size() + " MEANINGS");
            for (int i = 0; i < slangDefinitions.size(); i++)
                System.out.println((i + 1) + ".\t" + slangDefinitions.get(i));

            System.out.println("PRESS 1: TO EDIT SLANG WORD");
            System.out.println("PRESS 2: TO EDIT MEANING");
            Scanner myReader = new Scanner(System.in);
            int choose = myReader.nextInt();
            myReader.nextLine();

            if (choose == 1) {
                System.out.print("INPUT NEW SLANG WORD: ");
                String newSlangWord = myReader.nextLine();
                System.out.print("CONFIRM TO CHANGE (Y / N):");
                String confirm = myReader.nextLine();
                System.out.println(confirm.toLowerCase());
                if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
                    changeSlangWordKey(slangWord, newSlangWord);
                    updateFileData();
                    System.out.println("SUCCESSFULLY CHANGE SLANG WORD");
                }
                else System.out.println("PLEASE INPUT Y / N");
            }
            else if (choose == 2) {
                if (slangDefinitions.size() == 1)
                    System.out.println("PRESS 1 TO EDIT DEFINITION");
                else System.out.println("PRESS FROM 1 TO " + slangDefinitions.size() + " TO EDIT DEFINITION");
                System.out.println("PRESS " + (slangDefinitions.size() + 1) + " TO ADD NEW DEFINITION");
                int num = myReader.nextInt() - 1;
                myReader.nextLine();
                if (num < slangDefinitions.size()) {
                    System.out.println("INPUT NEW DEFINITION: ");
                    String newSlangDefinition= myReader.nextLine();
                    System.out.println("CONFIRM TO CHANGE (Y / N):");
                    String confirm = myReader.nextLine();
                    if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
                        changeDefinition(slangWord, num, newSlangDefinition);
                        updateFileData();
                        System.out.println("SUCCESSFULLY CHANGE SLANG DEFINITION");
                    }
                    else System.out.println("PLEASE INPUT Y / N");
                }
                else if (num == slangDefinitions.size()) {
                    System.out.println("INPUT NEW DEFINITION: ");
                    String newSlangDefinition = myReader.nextLine();
                    System.out.println("CONFIRM TO ADD (Y / N):");
                    String confirm = myReader.nextLine();
                    if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
                        addNewDefinition(slangWord, newSlangDefinition);
                        updateFileData();
                        System.out.println("SUCCESSFULLY ADD NEW DEFINITION");
                    }
                    else System.out.println("PLEASE INPUT Y / N");
                }

            }
            else {
                System.out.println("PLEASE PRESS ONLY 1 OR 2");
                editSlangWord(slangWord);
                return;
            }
        }
        else
            System.out.println("DONT HAVE SLANG WORD " + slangWord + " ON DATABASE");
    }

    public void changeSlangWordKey(String oldSlangWordKey, String newSlangWordkey) {
        ArrayList<String> slangDefinitions= new ArrayList<>(m_slangDic.get(oldSlangWordKey));
        m_slangDic.remove(oldSlangWordKey);
        m_slangDic.put(newSlangWordkey, slangDefinitions);
    }

    public void changeDefinition(String slangWord, int indexOfOldDefinition, String newSlangDefinition) {
        ArrayList<String> slangDefinitions = m_slangDic.get(slangWord);
        slangDefinitions.remove(indexOfOldDefinition);
        slangDefinitions.add(newSlangDefinition);
    }

    public void addNewDefinition(String slangWord, String newSlangDefinition) {
        ArrayList<String> slangDefinitions = m_slangDic.get(slangWord);
        slangDefinitions.add(newSlangDefinition);
    }

    
}
