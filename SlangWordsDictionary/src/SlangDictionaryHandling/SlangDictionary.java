package SlangDictionaryHandling;

import java.util.HashMap;
import java.util.ArrayList;

import FileHandling.*;


public class SlangDictionary {

    private HashMap<String, ArrayList<String>> m_slangDic;

    public SlangDictionary() {
        m_slangDic = new HashMap<String, ArrayList<String>>();
    }

    public void loadData(String file_name) {
        SlangFileHandling.readFromFile(file_name, m_slangDic);
    }

    //Option 01: Enter slangWord -> Find slangDefinition
    public void searchDefinition(String slangWord) {
        if (m_slangDic.containsKey(slangWord)) {
            System.out.println("Meaning of " + slangWord + " is:");
            for (String slangDefinition : m_slangDic.get(slangWord)) {
                System.out.println(slangDefinition);
            }
        }
        else System.out.println("Dont have slang word " + slangWord + " on database");
    }
}
