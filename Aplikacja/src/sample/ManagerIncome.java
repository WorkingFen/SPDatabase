package sample;

public class ManagerIncome {
    private String month;
    private int income;

    public ManagerIncome(String month, int income) {
        this.month = month;
        this.income = income;
    }

    public String getMonth() { return month; }

    public void setMonth(String month) { this.month = month; }

    public int getIncome() { return income; }

    public void setIncome(int income) { this.income = income; }
}
