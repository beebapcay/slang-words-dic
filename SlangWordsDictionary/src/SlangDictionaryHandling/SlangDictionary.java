package SlangDictionaryHandling;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.Collections;

import FileHandling.*;


public class SlangDictionary {

    private HashMap<String, ArrayList<String>> m_slangDic;
    private String file_data;
    private String file_original;

    public SlangDictionary() {
        m_slangDic = new HashMap<String, ArrayList<String>>();
        file_data = "";
        file_original = "";
    }

    public SlangDictionary(String file_data, String file_original) {
        m_slangDic = new HashMap<String, ArrayList<String>>();
        this.file_data = file_data;
        this.file_original = file_original;
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
                else {
                    System.out.println("INVALID INPUT");
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

    //Option 06: Delete slangWord
    public void deleteSlangWord(String slangWord) {
        if (m_slangDic.containsKey(slangWord)) {
            ArrayList<String> slangDefinitions = m_slangDic.get(slangWord);
            System.out.println("ORIGINAL SLANG WORD " + slangWord + " HAS " + slangDefinitions.size() + " MEANINGS");
            for (int i = 0; i < slangDefinitions.size(); i++)
                System.out.println((i + 1) + ".\t" + slangDefinitions.get(i));

            System.out.println("PRESS 1: TO DELETE SLANG WORD");
            System.out.println("PRESS 2: TO DELETE MEANING");
            Scanner myReader = new Scanner(System.in);
            int choose = myReader.nextInt();
            myReader.nextLine();

            if (choose == 1) {
                System.out.print("CONFIRM TO DELETE (Y / N):");
                String confirm = myReader.nextLine();
                if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
                    m_slangDic.remove(slangWord);
                    updateFileData();
                    System.out.println("SUCCESSFULLY DELETE SLANG WORD");
                }
                else System.out.println("PLEASE INPUT Y / N");
            }
            else if (choose == 2) {
                if (slangDefinitions.size() == 1)
                    System.out.println("PRESS 1 TO DELETE DEFINITION");
                else System.out.println("PRESS FROM 1 TO " + slangDefinitions.size() + " TO DELETE DEFINITION");
                int num = myReader.nextInt() - 1;
                myReader.nextLine();
                if (num < slangDefinitions.size()) {
                    System.out.println("CONFIRM TO DELETE (Y / N):");
                    String confirm = myReader.nextLine();
                    if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
                        slangDefinitions.remove(num);
                        updateFileData();
                        System.out.println("SUCCESSFULLY CHANGE SLANG DEFINITION");
                    }
                    else System.out.println("PLEASE INPUT Y / N");
                }
                else  {
                    System.out.println("INVALID INPUT");
                }
            }
            else {
                System.out.println("PLEASE PRESS ONLY 1 OR 2");
                deleteSlangWord(slangWord);
                return;
            }
        }
        else System.out.println("DONT HAVE SLANG WORD " + slangWord + " ON DATABASE");
    }

    //Option 07: Reset To Original Database
    public void resetToOriginalData() {
        m_slangDic.clear();
        SlangFileHandling.readFromFile(file_original, m_slangDic);
        updateFileData();
        System.out.println("SUCCESSFULLY RESET ORIGINAL DATABASE");
    }

    //Option 08: Show Random slangWord
    public String getRandomSlangWord() {
        int randomInt = new Random().nextInt(m_slangDic.size());

        Object slangWord = m_slangDic.keySet().toArray()[randomInt];
        return slangWord.toString();
    }

    public String getRandomSlangDefinition(String slangWord) {
        ArrayList<String> slangDefinitions = m_slangDic.get(slangWord);
        return slangDefinitions.get(new Random().nextInt(slangDefinitions.size()));
    }

    public void randomShowSlangWord() {

        String slangWord = getRandomSlangWord();
        ArrayList<String> slangDefinitions = m_slangDic.get(slangWord);

        System.out.println("SLANG WORD: " + slangWord);
        System.out.println("THERE ARE " + slangDefinitions.size() + " MEANINGS OF THIS SLANG WORD");
        for (int i = 0; i < slangDefinitions.size(); i++)
            System.out.println((i + 1) + ".\t" + slangDefinitions.get(i));
    }

    //Option 09 + 10: Quiz slangWord <-> slangDefinition
    public void showQuizAnswer(ArrayList<String> listAnswer) {
        for (int i = 0; i < listAnswer.size(); i++)
            System.out.println((i + 1) + ".\t" + listAnswer.get(i));
    }

    public boolean checkAnswer(int correctAnswer) {
        Scanner myReader = new Scanner(System.in);
        System.out.print("PLEASE PRESS NUMBER OF CORRECT ANSWER: ");
        int answer = myReader.nextInt() - 1;
        myReader.nextLine();
        if (answer == correctAnswer) {
            System.out.println("CONGRATULATIONS! CORRECT ANSWER");
            return true;
        }
        System.out.println("WRONG ANSWER");
        return false;
    }

    //1: slangWord -> slangDefinition
    //2: slangDefinition -> slangWord
    public void showQuizSlangWord(int type) {
        if (type != 1 && type != 2) return;

        String slangWordAnswer = getRandomSlangWord();
        String slangDefinitionAnswer = getRandomSlangDefinition(slangWordAnswer);

        ArrayList<String> listAnswer = new ArrayList<>();

        if (type == 1) {
            listAnswer.add(slangDefinitionAnswer);
            for (int i = 1; i <= 3; i++)
                listAnswer.add(getRandomSlangDefinition(getRandomSlangWord()));
        }
        else {
            listAnswer.add(slangWordAnswer);
            for (int i = 1; i <= 3; i++)
                listAnswer.add(getRandomSlangWord());
        }
        Collections.shuffle(listAnswer);

        if (type == 1)
            System.out.println("SLANG WORD: " + slangWordAnswer + " MEAN?");
        else System.out.println("DEFINITION: " + slangDefinitionAnswer + " FOR SLANG WORD?");
        System.out.println("CHOOSE CORRECT ANSWER NUMBER ANSWER");
        showQuizAnswer(listAnswer);
        boolean con = true;
        while (con) {
            boolean correct;
            if (type == 1) correct = checkAnswer(listAnswer.indexOf(slangDefinitionAnswer));
            else correct = checkAnswer(listAnswer.indexOf(slangWordAnswer));
            if (!correct) {
                System.out.println("ARE YOU WANT ANSWER AGAIN (Y / N): ");
                Scanner myReader = new Scanner(System.in);
                String confirm = myReader.nextLine();
                if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes"))
                    con = true;
                else if (confirm.equalsIgnoreCase("n") || confirm.equalsIgnoreCase("no"))
                    con = false;
                else System.out.println("PLEASE INPUT Y / N");
            }
            else con = false;
        }
    }
}
