package sample.Marketing;

public class MarketingClient {
    private int id;
    private String name;
    private String surname;
    private String position;

    public MarketingClient(int id, String name, String surname, String position) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.position = position;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getPosition() { return position; }

    public void setPosition(String position) { this.position = position; }
}
