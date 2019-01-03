package sample;

public class AuditorInspection {
    private int number;
    private String date;
    private String comment;

    public AuditorInspection(int number, String date, String comment) {
        this.number = number;
        this.date = date;
        this.comment = comment;
    }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }
}
