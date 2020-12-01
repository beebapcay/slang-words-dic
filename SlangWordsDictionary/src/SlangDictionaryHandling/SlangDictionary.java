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

    //Option 02: Enter slangDefinition -> Find slangWord
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
}
