package SlangDictionaryHandling;

import FileHandling.*;
import SlangDictionaryHandling.SlangDictionary;

public class AppHandling {

    public static void main(String args[]) {

        SlangDictionary slangDic = new SlangDictionary();
        String file_name = "resources/slang.txt";
        slangDic.loadData(file_name);

    }
}
