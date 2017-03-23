package globalstd.globalaudit.objects;

/**
 * Created by Gabriel Vazquez on 14/03/2017.
 */

public class User {
    private int id;
    private String name;
    private String email;
    private byte[] imageSmall;
    private Address address;
    private String role;
    private String lenguaje;

    public User(int id, String name, String email, byte[] imageSmall, Address address, String role, String lenguaje) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageSmall = imageSmall;
        this.role = role;
        this.lenguaje = lenguaje;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImageSmall() {
        return imageSmall;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }
}
