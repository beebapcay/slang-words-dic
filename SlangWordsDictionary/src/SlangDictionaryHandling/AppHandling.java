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
        String key= myReader.next();
        System.out.println(key);

        slangDic.searchDefinition(key);
        slangDic.recordHistory("resources/history.txt", key);
        slangDic.showHistory("resources/history.txt");
    }
}
