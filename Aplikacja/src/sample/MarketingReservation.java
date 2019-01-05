package sample;

public class MarketingReservation {
    private String date;
    private String type;
    private String name;
    private String surname;

    public MarketingReservation(String date, String type, String name, String surname) {
        this.date = date;
        this.type = type;
        this.name = name;
        this.surname = surname;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }
}
