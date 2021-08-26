import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

/**
 * Main class of PlaylistManager
 * 
 * @version 12/26/2020
 * @author Stephen
 */
public class PlaylistManager {
    
    /**
     * Call function to create two json files of playlists and call a comparison of them
     *
     * @param args command like arguments
     * @throws java.security.GeneralSecurityException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        //Playlist id to send in for creation
        //String id = "PLgRYhOYRHKBpCLw_7T-wCYFCS_-6yI4mu"; //Little boy
        String id = "PLgRYhOYRHKBpZVx_-PT9YR5F8CL9YTMmz"; //Fat Man
        String id2 = "PLgRYhOYRHKBr-2JI7fA42TawN_ezXMkDZ"; //Little Boy 2
        
        //Gets permission for youtube to access shit
        YoutubeAPI.allowAccess();
        
        //Calls creation of first playlist
        PlaylistCreation.plCreate(id, true);
        PlaylistCreation.plCreate(id2, false);
        
        //Calls method to transform json into a format thats easier to sort through
        ArrayList<String[]> oldList = PlaylistCompare.listCreator(true);
        ArrayList<String[]> newList = PlaylistCompare.listCreator(false);
        
        //Test compare
        ArrayList<String[]> missingList = PlaylistCompare.masterSearch(oldList, newList);
        for (int i = 0; i < missingList.size(); i++) {
            System.out.println(missingList.get(i)[0]);
        }
        
    }
}
