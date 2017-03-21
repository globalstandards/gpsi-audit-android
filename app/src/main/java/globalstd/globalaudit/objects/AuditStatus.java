package globalstd.globalaudit.objects;

/**
 * Created by Gabriel Vazquez on 01/03/2017.
 */

public class AuditStatus {
    private int id;
    private String status;

    public AuditStatus() {   }

    public AuditStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
