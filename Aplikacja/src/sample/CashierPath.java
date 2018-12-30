package sample;

public class CashierPath extends ClientPath{

    private String state;

    public CashierPath(int number, String date, int pathNumber, String state, String msg) {
        super(number, date, pathNumber, msg);
        this.state = state;
}

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
