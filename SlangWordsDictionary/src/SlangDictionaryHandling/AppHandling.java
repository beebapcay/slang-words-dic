package SlangDictionaryHandling;

import java.util.Scanner;

import FileHandling.*;
import SlangDictionaryHandling.SlangDictionary;

public class AppHandling {

    public static void main(String args[]) {

        String file_data = "resources/slang.txt";
        String file_original  = "resources/slang-original.txt";
        SlangDictionary slangDic = new SlangDictionary(file_data, file_original);
        slangDic.loadSlangWordData();

        Scanner myReader = new Scanner(System.in);
        System.out.print("Input: ");
        String key = myReader.next();

        slangDic.searchDefinition(key);
    }

}
