package sample;

public class AuditorInspection {
    private int number;
    private String objectName;
    private String date;

    public AuditorInspection(int number, String objectName, String date) {
        this.number = number;
        this.objectName = objectName;
        this.date = date;
    }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public String getObjectName() { return objectName; }

    public void setObjectName(String objectName) { this.objectName = objectName; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}
