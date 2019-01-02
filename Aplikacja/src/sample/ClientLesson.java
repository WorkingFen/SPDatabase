package sample;

public class ClientLesson extends CashierLesson{
    private String poolName;

    public ClientLesson(String date, String enrolled, String rescuer, String poolName, String msg) {
        super(date, enrolled, rescuer, msg);

        this.poolName = poolName;
    }

    public String getPoolName() { return poolName; }

    public void setPoolName(String poolName) { this.poolName = poolName; }
}
