package sample;

public class ManagerEmployee {
    private int id;
    private String name;
    private String surname;
    private String post;

    public ManagerEmployee(int id, String name, String surname, String post) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.post = post;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getPost() { return post; }

    public void setPost(String post) { this.post = post; }
}
