package sample.Owner;

public class OwnerEmployee {
    private int id;
    private String name;
    private String surname;
    private int perk;

    public OwnerEmployee(int id, String name, String surname, int perk) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.perk = perk;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public int getPerk() { return perk; }

    public void setPerk(int perk) { this.perk = perk; }
}
