package globalstd.globalaudit.objects;

/**
 * Created by Gabriel Vazquez on 17/03/2017.
 */

public class Cars {
    private String ref;
    private Auditor auditor;
    private String date;
    private String status;

    public Cars(String ref, Auditor auditor, String date, String status) {
        this.ref = ref;
        this.auditor = auditor;
        this.date = date;
        this.status = status;
    }
    public Cars(String ref, String date, String status) {
        this.ref = ref;
        this.date = date;
        this.status = status;
    }

    public String getRef() {
        return ref;
    }
    public void setRef(String ref) {
        this.ref = ref;
    }

    public Auditor getAuditor() {
        return auditor;
    }
    public void setAuditor(Audit auditoror) {
        this.auditor = auditor;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
