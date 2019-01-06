package sample.Auditor;

public class AuditorTransaction {
    private int number;
    private String date;
    private String service;
    private int price;

    public AuditorTransaction(int number, String date, String service, int price) {
        this.number = number;
        this.date = date;
        this.service = service;
        this.price = price;
    }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getService() { return service; }

    public void setService(String service) { this.service = service; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }
}
