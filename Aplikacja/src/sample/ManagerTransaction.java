package sample;

public class ManagerTransaction {
    private int number;
    private String date;
    private int price;

    public ManagerTransaction(int number, String date, int price) {
        this.number = number;
        this.date = date;
        this.price = price;
    }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }
}
