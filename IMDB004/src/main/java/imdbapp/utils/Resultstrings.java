package imdbapp.utils;

import java.util.ArrayList;
import java.util.List;

public class Resultstrings {
    List<String> resultlist = new ArrayList<>();


    public void add(String aString) {
        resultlist.add(aString);
    }

    public List<String> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<String> resultlist) {
        this.resultlist = resultlist;
    }
}
