import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Class for preparing and comparing two playlists
 * 
 * @version 12/26/2020
 * @author Stephen
 */
public class PlaylistCompare {
    /**
     * 
     * @param yep Boolean to determine which playlist is being read
     * @return ArrayList made of String arrays with the title and id
     * @throws FileNotFoundException 
     */
    public static ArrayList<String[]> listCreator(boolean yep) throws FileNotFoundException {
        
        //if yep = true then grab the first playlist, second if not
        ArrayList<String[]> list = new ArrayList<String[]>();
        if (yep) {
            File plOne = new File("oldList");
            Scanner sc = new Scanner(plOne);
            String[] tempa;
            String temp;
            
            while (sc.hasNextLine()) {
                temp = sc.nextLine();
                tempa = temp.split("\t");
                list.add(tempa);
            }
            
            return list;
        } else {
            File plTwo = new File("newList");
            Scanner sc = new Scanner(plTwo);
            String[] tempa;
            String temp;
            
            while (sc.hasNextLine()) {
                temp = sc.nextLine();
                tempa = temp.split("\t");
                list.add(tempa);
            }
            return sorter(list);
        }
    }
    
    /**
     * Sorts the items in list through selection sort based on compareTo of
     * the ids
     * 
     * @param list ArrayList of String arrays made by idGrabber
     * @return list but sorted via selection sort
     */
    public static ArrayList<String[]> sorter(ArrayList<String[]> list) {
        int n = list.size();
        
        for (int i = 0; i < n; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j)[1].compareTo(list.get(minIndex)[1]) < 0) {
                    minIndex = j;
                }
            }
            
            String[] temp = list.get(minIndex);
            list.set(minIndex, list.get(i));
            list.set(i, temp);
        }
        return list;
    }
    
    /**
     * Binary search to see if an id exists in the new playlist
     * 
     * @param list the list of all the titles and ids
     * @param chk the string to be checked for in the list
     * @return the index of item being searched for or -1 if the item isn't 
     * found
     */
    public static int binarySearch(ArrayList<String[]> list, String chk) {
        //Creation of the left and right border
        int left = 0;
        int right = list.size();
        
        //Cycles through until the left border and the right border switch
        while (left <= right) {
            //Makes the middle between the left and right border
            int mid = left + (right - left) / 2;
            //Creates an int comparing the string given and the id in the mid
            int res = chk.compareTo(list.get(mid)[1]);
            
            //if the id and chk match return the index of mid
            if (res == 0) {
                return mid;
            }
            //if string > chk, move the left border to the right of mid
            if (res > 0) {
                left = mid + 1;
            } else {
                //move the right border to the left of mid
                right = mid - 1;
            }
        }
        //return -1 if chk can't be found
        return -1;
    }
    
    /**
     * Function to oversee comparing the newList to the oldList
     * 
     * @param oldList old version of the list
     * @param newList new version of the list
     * @return ArrayList of all of the songs missing in the newList
     */
    public static ArrayList<String[]> masterSearch(
            ArrayList<String[]> oldList, ArrayList<String[]> newList) {
        ArrayList<String[]> missingList = new ArrayList<>();
        
        //Searches through every item in oldList and comapres it to newList
        //looking for a match, if no match then it adds that item to missingList
        for (int i = 0; i < oldList.size(); i++) {
            String[] temp = oldList.get(i);
            int compare = binarySearch(newList, temp[1]);
            if (compare == -1) {
                missingList.add(temp);
            }
        }
        return missingList;
    }
}
