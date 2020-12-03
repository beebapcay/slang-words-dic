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
    private String file_history;

    public SlangDictionary() {
        m_slangDic = new HashMap<String, ArrayList<String>>();
        file_data = "";
        file_original = "";
        file_history = "";
    }

    public SlangDictionary(String file_data, String file_original, String file_history) {
        m_slangDic = new HashMap<String, ArrayList<String>>();
        this.file_data = file_data;
        this.file_original = file_original;
        this.file_history = file_history;
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

    //Confirm Option
    public static boolean confirmOption() {
        Scanner myReader = new Scanner(System.in);
        System.out.print("CONFIRM TO DO (Y / N):");
        String confirm = myReader.nextLine();
        if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes"))
            return true;
        else if (confirm.equalsIgnoreCase("n") || confirm.equalsIgnoreCase("no"))
            return false;
        else {
            System.out.println("PLEASE INPUT Y / N");
            System.out.println("PRESS ENTER CONFIRM AGAIN");
            myReader.nextLine();
            return confirmOption();
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
    public void recordHistory(String data) {
        FileHandling.writeToFileAppend(file_history, data);
    }

    public void showHistory() {
        System.out.println("THERE ARE HISTORIES SLANG WORD WERE SEARCHED");
        FileHandling.readFromFile(file_history);
    }

    //Option 04: Add New slangWord - slangDefinition. CaseSensitive
    public void addSlangWord(String newSlangWord, String newDefinition) {

        //If slangDictionary Contains newSlangWord -> Overwrite Or Add New Meaning
       if (m_slangDic.containsKey(newSlangWord)) {
           ArrayList<String> slangDefinitions = m_slangDic.get(newSlangWord);
           System.out.println("ORIGINAL SLANG WORD " + newSlangWord + " HAS " + slangDefinitions.size() + " MEANINGS");
           for (int i = 0; i < slangDefinitions.size(); i++)
               System.out.println((i + 1) + ".\t" + slangDefinitions.get(i));

           System.out.println("PRESS 0: TO EXIT ADD THIS SLANG WORD");
           System.out.println("PRESS 1: TO OVERWRITE ITS MEANINGS");
           System.out.println("PRESS 2: TO ADD NEW MEANING OF THIS SLANG WORD");

           System.out.print("PRESS: ");
           Scanner myReader = new Scanner(System.in);
           int choose = myReader.nextInt();
           myReader.nextLine();

           if (choose == 0) {
               System.out.println("PRESS ENTER TO EXIT ADD THIS SLANG WORD");
               myReader.nextLine();
           }
           else if (choose == 1) {
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
               System.out.println("PLEASE ENTER TO INPUT AGAIN");
               myReader.nextLine();
               addSlangWord(newSlangWord, newDefinition);
               return;
           }
           //Update File Data (Write Again)
           updateFileData();
       }
       //If slangDictionary Does Not Contain newSlangWord -> Append
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

    public void editSlangWord(String slangWord) {
        if (m_slangDic.containsKey(slangWord)) {
            ArrayList<String> slangDefinitions = m_slangDic.get(slangWord);
            System.out.println("ORIGINAL SLANG WORD " + slangWord + " HAS " + slangDefinitions.size() + " MEANINGS");
            for (int i = 0; i < slangDefinitions.size(); i++)
                System.out.println((i + 1) + ".\t" + slangDefinitions.get(i));

            System.out.println("PRESS 0: TO EXIT EDIT THIS SLANG WORD");
            System.out.println("PRESS 1: TO EDIT SLANG WORD");
            System.out.println("PRESS 2: TO EDIT MEANING");
            Scanner myReader = new Scanner(System.in);
            System.out.print("PRESS: ");
            int choose = myReader.nextInt();
            myReader.nextLine();

            if (choose == 0) {
                System.out.println("PRESS ENTER TO EXIT EDIT THIS SLANG WORD");
                myReader.nextLine();
            }
            else if (choose == 1) {
                System.out.print("INPUT NEW SLANG WORD KEY: ");
                String newSlangWord = myReader.nextLine();
                boolean confirm = confirmOption();
                if (confirm) {
                    changeSlangWordKey(slangWord, newSlangWord);
                    updateFileData();
                    System.out.println("SUCCESSFULLY CHANGE SLANG WORD");
                }
                else {
                    System.out.println("YOU DONT WANT TO CHANGE. PRESS ENTER");
                    myReader.nextLine();
                    editSlangWord(slangWord);
                }
            }
            else if (choose == 2) {
                if (slangDefinitions.size() == 1)
                    System.out.println("PRESS 1 TO EDIT DEFINITION");
                else System.out.println("PRESS FROM 1 TO " + slangDefinitions.size() + " TO EDIT CORRESPONDING DEFINITION");
                System.out.println("PRESS " + (slangDefinitions.size() + 1) + " TO ADD NEW DEFINITION");
                System.out.print("PRESS: ");
                int num = myReader.nextInt() - 1;
                myReader.nextLine();

                if (num < slangDefinitions.size()) {
                    System.out.println("INPUT NEW DEFINITION: ");
                    String newSlangDefinition= myReader.nextLine();
                    boolean confirm = confirmOption();
                    if (confirm) {
                        changeDefinition(slangWord, num, newSlangDefinition);
                        updateFileData();
                        System.out.println("SUCCESSFULLY CHANGE SLANG DEFINITION");
                    }
                    else {
                        System.out.println("YOU DONT WANT TO CHANGE. PRESS ENTER");
                        myReader.nextLine();
                        editSlangWord(slangWord);
                    }
                }
                else if (num == slangDefinitions.size()) {
                    System.out.println("INPUT NEW DEFINITION: ");
                    String newSlangDefinition = myReader.nextLine();
                    boolean confirm = confirmOption();
                    if (confirm) {
                        addNewDefinition(slangWord, newSlangDefinition);
                        updateFileData();
                        System.out.println("SUCCESSFULLY ADD NEW DEFINITION");
                    }
                    else {
                        System.out.println("YOU DONT WANT TO CHANGE. PRESS ENTER");
                        myReader.nextLine();
                        editSlangWord(slangWord);
                    }
                }
                else {
                    System.out.println("INVALID INPUT");
                }
            }
            else {
                System.out.println("PLEASE PRESS VALID VALUE");
                System.out.println("PLEASE ENTER TO INPUT AGAIN");
                myReader.nextLine();
                editSlangWord(slangWord);
                return;
            }
        }
        else
            System.out.println("DONT HAVE SLANG WORD " + slangWord + " ON DATABASE");
    }

    //Option 06: Delete slangWord
    public void deleteSlangWord(String slangWord) {
        if (m_slangDic.containsKey(slangWord)) {
            ArrayList<String> slangDefinitions = m_slangDic.get(slangWord);
            System.out.println("ORIGINAL SLANG WORD " + slangWord + " HAS " + slangDefinitions.size() + " MEANINGS");
            for (int i = 0; i < slangDefinitions.size(); i++)
                System.out.println((i + 1) + ".\t" + slangDefinitions.get(i));

            System.out.println("PRESS 0: TO EXIT DELETE THIS SLANG WORD");
            System.out.println("PRESS 1: TO DELETE SLANG WORD");
            System.out.println("PRESS 2: TO DELETE MEANING");
            Scanner myReader = new Scanner(System.in);
            System.out.print("PRESS: ");
            int choose = myReader.nextInt();
            myReader.nextLine();

            if (choose == 0) {
                System.out.println("PRESS ENTER TO EXIT DELETE THIS SLANG WORD");
                myReader.nextLine();
            }
            else if (choose == 1) {
                boolean confirm = confirmOption();
                if (confirm) {
                    m_slangDic.remove(slangWord);
                    updateFileData();
                    System.out.println("SUCCESSFULLY DELETE SLANG WORD");
                }
                else {
                    System.out.println("YOU DONT WANT TO DELETE. PRESS ENTER");
                    myReader.nextLine();
                    deleteSlangWord(slangWord);
                }
            }
            else if (choose == 2) {
                if (slangDefinitions.size() == 1)
                    System.out.println("PRESS 1 TO DELETE DEFINITION");
                else System.out.println("PRESS FROM 1 TO " + slangDefinitions.size() + " TO DELETE CORRESPONDING DEFINITION");
                System.out.print("PRESS: ");
                int num = myReader.nextInt() - 1;
                myReader.nextLine();
                if (num < slangDefinitions.size()) {
                    boolean confirm = confirmOption();
                    if (confirm) {
                        if (num == slangDefinitions.size())
                            m_slangDic.remove(slangWord);
                        else slangDefinitions.remove(num);
                        updateFileData();
                        System.out.println("SUCCESSFULLY CHANGE SLANG DEFINITION");
                    }
                    else {
                        System.out.println("YOU DONT WANT TO DELETE. PRESS ENTER");
                        myReader.nextLine();
                        deleteSlangWord(slangWord);
                    }
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
        FileHandling.writeToFile(file_history, "");
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

        int numAnswer = 4;
        ArrayList<String> listAnswer = new ArrayList<>();

        if (type == 1) {
            listAnswer.add(slangDefinitionAnswer);
            for (int i = 1; i < numAnswer; i++)
                listAnswer.add(getRandomSlangDefinition(getRandomSlangWord()));
        }
        else {
            listAnswer.add(slangWordAnswer);
            for (int i = 1; i < numAnswer; i++)
                listAnswer.add(getRandomSlangWord());
        }
        Collections.shuffle(listAnswer);

        if (type == 1)
            System.out.println("SLANG WORD: " + slangWordAnswer + " MEAN?");
        else System.out.println("DEFINITION: " + slangDefinitionAnswer + " FOR SLANG WORD?");

        System.out.println("CHOOSE CORRECT ANSWER NUMBER ANSWER BELOW");
        showQuizAnswer(listAnswer);

        boolean con = true;
        while (con) {
            boolean correct;
            if (type == 1) correct = checkAnswer(listAnswer.indexOf(slangDefinitionAnswer));
            else correct = checkAnswer(listAnswer.indexOf(slangWordAnswer));
            if (!correct) {
                System.out.print("ARE YOU WANT ANSWER AGAIN (Y / N): ");
                Scanner myReader = new Scanner(System.in);
                String confirm = myReader.nextLine();
                if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes"))
                    con = true;
                else if (confirm.equalsIgnoreCase("n") || confirm.equalsIgnoreCase("no"))
                    con = false;
                else {
                    System.out.println("PLEASE PRESS Y / N");
                    System.out.println("PRESS ENTER TO INPUT AGAIN");
                    con = true;
                }
            }
            else con = false;
        }
    }
}
