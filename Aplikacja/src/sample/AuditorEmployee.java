package sample;

public class AuditorEmployee {
    private int number;
    private String firstName;
    private String lastName;
    private String post;
    private int perk;

    public AuditorEmployee(int number, String firstName, String lastName, String post, int perk) {
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
        this.post = post;
        this.perk = perk;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPost() { return post; }

    public void setPost(String post) { this.post = post; }

    public int getPerk() { return perk; }

    public void setPerk(int perk) { this.perk = perk; }
}
