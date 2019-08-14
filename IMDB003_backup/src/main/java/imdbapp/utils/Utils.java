package imdbapp.utils;

import imdbapp.repo.Film;
import imdbapp.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Utils {

    /*****************************************************************************
     *
     * Try to typecast a person
     *
     * Parameters: Connection conn - current connection, String aNcode - the key for this actor
     *
     * Find the genres associated with the person
     * i.e. the genres that are associated with titles in which the person has a role
     * (a record linking this person to a role in the title is found in table title_principals)
     * Assumption is that each title is associated with at most 3 different genres
     *
     * The algorithm keeps a list of genre together with an occurrence count for each genre
     *
     * The person is typecasted to a certain genre if
     *    1. more than one title has been found for this person
     *    2. one of the genres found has a higher occurrence count than any other genre
     *    3. this count is equal or larger than the count of titles for this person
     *
     *  If this is the case, the person is typecasted to the genre found in 2.
     *
     *****************************************************************************/
    public static String getTypecast(List<Film> titleSet) throws Exception {

        Set<CountString> countStringSet = new HashSet<CountString>();

        for (Film title: titleSet) {
            String genres = title.getGenres();

            // genres string is a comma-separated value list
            // extract those genre values and check if in the countStringSet
            // if not found, add the genre value
            // if found, increase the counter for this genre value

            if (genres != null)
                if (!genres.isEmpty()) {
                    String [] arrOfGenres = genres.split(",");

                    for (int i = 1; i<= arrOfGenres.length; i++) {
                        String genre = arrOfGenres[i-1];
                        //answer += genre + "--";

                        boolean found = false;
                        for (CountString cs: countStringSet) {
                            if (cs.getName().equals(genre)) {
                                cs.increment();
                                found = true;
                            }
                        }
                        if (!found) countStringSet.add(new CountString(genre));
                    }
                } // if ! isEmpty
        }




        // Find the maximum number of occurrences of any found genre in the countStringSet
        int maxOccurs = 0;
        for (CountString cs: countStringSet) {
            if (cs.getCount() > maxOccurs)
                maxOccurs = cs.getCount();
        }

        boolean unique = true;
        boolean found = false;
        String genre = null;

        // Check if there is only one genre that has this maximum number of occurrences
        for (CountString cs: countStringSet) {
            if (cs.getCount() == maxOccurs) {
                if (!found) {
                    found = true;
                    genre = cs.getName();
                } else {
                    unique = false;
                }
            }
        }
        if ((unique) && maxOccurs > (titleSet.size() % 2)) {
            return   genre;
        } else {
            return "this actor could not be typecasted";
        }
    }

}
