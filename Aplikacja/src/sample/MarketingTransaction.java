package sample;

public class MarketingTransaction {
    private String date;
    private int value;
    private String name;
    private String surname;

    public MarketingTransaction(String date, int value, String name, String surname) {
        this.date = date;
        this.value = value;
        this.name = name;
        this.surname = surname;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }
}
