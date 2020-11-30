package SlangDictionaryHandling;

import java.util.HashMap; // Import the HashMap for store SlangWord - Definition
import java.util.ArrayList; // Import the ArrayList for Definition of SlangWord


public class SlangDictionary {

    private HashMap<String, ArrayList<String>> m_slangDic;

    public SlangDictionary() {
        m_slangDic = new HashMap<String, ArrayList<String>>();
    }

}
