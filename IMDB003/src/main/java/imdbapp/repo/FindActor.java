package imdbapp.repo;

public class FindActor {
    private String name;
    private String hasBirthDate = "Yes";

    public FindActor(String name, String hasBirthDate) {
        this.name = name;
        this.hasBirthDate = hasBirthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHasBirthDate() {
        return hasBirthDate;
    }

    public void setHasBirthDate(String hasBirthDate) {
        this.hasBirthDate = hasBirthDate;
    }
}
