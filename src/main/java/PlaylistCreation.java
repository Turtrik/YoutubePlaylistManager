import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.jayway.jsonpath.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
//import java.lang.Math.*;

/**
 * Class for creating the two comparison playlists
 * 
 * @version 12/06/2020
 * @author Stephen
 */
public class PlaylistCreation {
    

    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @param id playlist id
     * @param pl Determines which playlist is being made
     * @throws java.security.GeneralSecurityException
     * @throws java.io.IOException
     * @throws com.google.api.client.googleapis.json.GoogleJsonResponseException
     */
    public static void plCreate(String id, Boolean pl)
        throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        
        //Making the file/file writer and checking for file creation
        File output = new File("oldList");
        File output2 = new File("newList");
        if (!pl) {
            output = output2;
        }
        
        try (PrintWriter wr = new PrintWriter(output)) {
            if (!output.exists()) {
                output.createNewFile();
            }
            
            //General variable declarations
            YouTube youtubeService = YoutubeAPI.youtubeService;
            int vidCount;
            
            //Getting json data for the playlist
            YouTube.Playlists.List request1 = youtubeService.playlists()
                    .list("contentDetails");
            PlaylistListResponse plResponse = request1.setId(id).execute();
            
            
            // Getting json data for the playlist items
            YouTube.PlaylistItems.List request2 = youtubeService.playlistItems()
                    .list("snippet");
            PlaylistItemListResponse pliResponse = request2.setMaxResults(50L)
                    .setPlaylistId(id).execute();
            ArrayList<String> page = JsonPath.read(pliResponse.toString(), "$..nextPageToken");
            wr.write(JSONParse.getTitles(pliResponse.toString()));
            
            //Gets number of items in desired playlist and computes how many pages
            //need to be called and calles said pages
            vidCount = JSONParse.getVidCount(plResponse.toString());
            for (int i = 0; i < -Math.floorDiv(-vidCount, 50) - 2; i++) {
                System.out.print(i + " " + id + "\n");
                PlaylistItemListResponse tpliResponse = request2.setPageToken(page.get(i))
                        .setMaxResults(50L)
                        .setPlaylistId(id).execute();
                page.add(JsonPath.read(tpliResponse.toString(), "$..nextPageToken").toString());
                wr.write(JSONParse.getTitles(tpliResponse.toString()));
            }
        }
    }
}
