package sample.Owner;

public class OwnerIncome {
    private String date;
    private int income;
    private int expenses;

    public OwnerIncome(String date, int income, int expenses) {
        this.date = date;
        this.income = income;
        this.expenses = expenses;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public int getIncome() { return income; }

    public void setIncome(int income) { this.income = income; }

    public int getExpenses() { return expenses; }

    public void setExpenses(int expenses) { this.expenses = expenses; }
}
