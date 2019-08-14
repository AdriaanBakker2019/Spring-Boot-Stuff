package imdbapp.utils;

public class CountString {
    String name;
    int count;

    public CountString() {
        count = 0;
    }

    public void increment() {
        count++;
    }
    public CountString(String name) {
        super();
        this.name = name;
        count = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

