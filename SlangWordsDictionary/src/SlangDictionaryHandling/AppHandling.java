package SlangDictionaryHandling;

import java.util.Scanner;
import java.io.IOException;

import FileHandling.*;
import SlangDictionaryHandling.SlangDictionary;

public class AppHandling {

    //Clears Screen in java
    public static void clrscr(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("cls");
        } catch (IOException | InterruptedException ex) {}
    }

    public static boolean continueOption() {
        Scanner myReader = new Scanner(System.in);
        System.out.print("DO YOU WANT CONTINUE OPTION? (Y / N): ");
        String confirm = myReader.nextLine();
        if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes"))
            return true;
        else if (confirm.equalsIgnoreCase("n") || confirm.equalsIgnoreCase("no"))
            return false;
        else {
            System.out.println("PLEASE INPUT Y / N");
            return continueOption();
        }
    }

    public static void main(String args[]) {
        //Setup Data File
        String resources_root = "resources/";
        String file_data = resources_root + "slang.txt";
        String file_original  = resources_root + "slang-original.txt";
        String file_history = resources_root + "history.txt";

        //Create And Load Data To SlangDictionary: slangDic
        SlangDictionary slangDic = new SlangDictionary(file_data, file_original, file_history);
        slangDic.loadSlangWordData();

        Scanner myReader = new Scanner(System.in);
        boolean con = true;

        while (con) {
            clrscr();

            System.out.println("WELCOME TO SLANG WORD DICTIONARY");
            System.out.println();

            //Menu Option
            System.out.println("OPTION 00: EXIT APPLICATION");
            System.out.println("OPTION 01: FIND THE MEANING OF SLANG WORD");
            System.out.println("OPTION 02: FIND THE SLANG WORD BY MEANING DEFINITION");
            System.out.println("OPTION 03: SHOW HISTORY SLANG WORDS WERE SEARCHED");
            System.out.println("OPTION 04: ADD NEW SLANG WORD");
            System.out.println("OPTION 05: EDIT SLANG WORD");
            System.out.println("OPTION 06: DELETE SLANG WORD");
            System.out.println("OPTION 07: RESET TO DEFAULT DATA SLANG WORD");
            System.out.println("OPTION 08: GIVE RANDOM SLANG WORD");
            System.out.println("OPTION 09: GIVE QUIZ FIND MEANING OF SLANG WORD");
            System.out.println("OPTION 10: GIVE QUIZ FIND SLANG WORD BY MEANING DEFINITION");
            System.out.println();

            System.out.print("PRESS OPTION YOU WANT: ");
            int option = myReader.nextInt();
            myReader.nextLine();

            boolean op_con = true;
            switch (option) {
                case 0:
                    System.out.println("\n** EXIT APPLICATION **\n");
                    System.out.println("\nPRESS ENTER TO EXIT\n");
                    myReader.nextLine();
                    con = false;
                    break;
                case 1:
                    System.out.println("\n** FIND THE MEANING OF SLANG WORD **\n");
                    while (op_con) {
                        System.out.print("ENTER YOUR SLANG WORD: ");
                        String slangWord = myReader.nextLine();
                        slangDic.recordHistory(slangWord);
                        slangDic.searchDefinition(slangWord);
                        op_con = continueOption();
                    }
                    break;
                case 2:
                    System.out.println("\n** FIND THE SLANG WORD BY MEANING DEFINITION **\n");
                    op_con = true;
                    while (op_con) {
                        System.out.print("ENTER YOUR DEFINITION KEY WORD: ");
                        String slangWord = myReader.nextLine();
                        slangDic.searchSlangWord(slangWord);
                        op_con = continueOption();
                    }
                    break;
                case 3:
                    System.out.println("\n** SHOW HISTORY SLANG WORDS WERE SEARCHED **\n");
                    slangDic.showHistory();
                    System.out.println("PRESS ENTER TO RETURN MENU");
                    myReader.nextLine();
                    break;
                case 4:
                    System.out.println("\n** ADD NEW SLANG WORD **\n");
                    op_con = true;
                    while (op_con) {
                        System.out.print("ENTER NEW SLANG WORD KEY: ");
                        String newSlangWord = myReader.nextLine();
                        System.out.print("ENTER NEW SLANG DEFINITION: ");
                        String newSlangDefinition = myReader.nextLine();
                        slangDic.addSlangWord(newSlangWord, newSlangDefinition);
                        op_con = continueOption();
                    }
                    break;
                case 5:
                    System.out.println("\n** EDIT SLANG WORD **\n");
                    op_con = true;
                    while (op_con) {
                        System.out.print("ENTER SLANG WORD KEY: ");
                        String slangWord = myReader.nextLine();
                        slangDic.editSlangWord(slangWord);
                        op_con = continueOption();
                    }
                    break;
                case 6:
                    System.out.println("\n** DELETE SLANG WORD **\n");
                    op_con = true;
                    while (op_con) {
                        System.out.print("ENTER SLANG WORD KEY: ");
                        String slangWord = myReader.nextLine();
                        slangDic.deleteSlangWord(slangWord);
                        op_con = continueOption();
                    }
                    break;
                case 7:
                    System.out.println("\n** RESET TO DEFAULT DATA SLANG WORD **\n");
                    slangDic.resetToOriginalData();
                    System.out.println("PRESS ENTER TO RETURN MENU");
                    myReader.nextLine();
                    break;
                case 8:
                    System.out.println("\n** GIVE RANDOM SLANG WORD **\n");
                    op_con = true;
                    while (op_con) {
                        slangDic.randomShowSlangWord();
                        op_con = continueOption();
                    }
                    break;
                case 9:
                    System.out.println("\n** GIVE QUIZ FIND MEANING OF SLANG WORD **\n");
                    op_con = true;
                    while (op_con) {
                        slangDic.showQuizSlangWord(1);
                        op_con = continueOption();
                    }
                    break;
                case 10:
                    System.out.println("\n** GIVE QUIZ FIND SLANG WORD BY MEANING DEFINITION **\n");
                    op_con = true;
                    while (op_con) {
                        slangDic.showQuizSlangWord(2);
                        op_con = continueOption();
                    }
                    break;
                default:
                    System.out.println("\n** PLEASE INPUT VALID VALUE **\n");
                    con = true;
                    break;
            }
        }
    }
}
