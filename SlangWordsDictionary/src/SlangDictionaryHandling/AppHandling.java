package SlangDictionaryHandling;

import java.util.Scanner;

import FileHandling.*;
import SlangDictionaryHandling.SlangDictionary;

public class AppHandling {

    public static void main(String args[]) {

        SlangDictionary slangDic = new SlangDictionary();
        String file_name = "resources/slang.txt";
        slangDic.loadSlangWordData(file_name);


        Scanner myReader = new Scanner(System.in);

        System.out.print("Input: ");
        String key = myReader.next();

        slangDic.searchDefinition(key);
    }
}
