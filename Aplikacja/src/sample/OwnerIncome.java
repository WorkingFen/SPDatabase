package sample;

public class OwnerIncome {
    private String objectName;
    private int income;
    private int expenses;

    public OwnerIncome(String objectName, int income, int expenses) {
        this.objectName = objectName;
        this.income = income;
        this.expenses = expenses;
    }

    public String getObjectName() { return objectName; }

    public void setObjectName(String objectName) { this.objectName = objectName; }

    public int getIncome() { return income; }

    public void setIncome(int income) { this.income = income; }

    public int getExpenses() { return expenses; }

    public void setExpenses(int expenses) { this.expenses = expenses; }
}
