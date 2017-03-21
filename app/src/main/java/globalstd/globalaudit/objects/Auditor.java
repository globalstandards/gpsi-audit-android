package globalstd.globalaudit.objects;

/**
 * Created by Gabriel Vazquez on 01/03/2017.
 */

public class Auditor {
    private int id;
    private String name;
    private String lastName;
    private String position;

    public Auditor() {    }

    public Auditor(int id, String name, String lastName, String position) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.position = position;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
}
