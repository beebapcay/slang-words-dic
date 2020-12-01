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


}
