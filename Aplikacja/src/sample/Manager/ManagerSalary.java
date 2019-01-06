package sample.Manager;

public class ManagerSalary {
    private String month;
    private int salary;

    public ManagerSalary(String month, int salary) {
        this.month = month;
        this.salary = salary;
    }

    public String getMonth() { return month; }

    public void setMonth(String month) { this.month = month; }

    public int getSalary() { return salary; }

    public void setSalary(int salary) { this.salary = salary; }
}
