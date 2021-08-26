import com.jayway.jsonpath.*;
import java.util.ArrayList;

/**
 * Class to easily pull information from strings in a json format
 * 
 * @version 11/26/2020
 * @author Stephen
 */
public class JSONParse {
    
    /**
     * Gets all the video titles and ids from the json data and formats it into a single string
     * 
     * @param s json data
     * @return a string comprised of all of the video titles and ids
     */
    public static String getTitles(String s) {
        String ret = "";
        ArrayList<String> titles = JsonPath.read(s, "$..title");
        ArrayList<String> ids = JsonPath.read(s, "$..videoId");
        for (int i = 0; i < titles.size(); i++) {
            ret += titles.get(i) + "\t" + ids.get(i) + "\n";
        }
        return ret;
    }
    
    /**
     * Gets the number of videos in a playlist
     * 
     * @param s json data
     * @return a int of the number of videos in a playlist
     */
    public static int getVidCount(String s) {
        ArrayList<Integer> count = JsonPath.read(s, "$..itemCount");
        return count.get(0);
    }
}
