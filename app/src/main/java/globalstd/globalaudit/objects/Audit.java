package globalstd.globalaudit.objects;

/**
 * Created by Gabriel Vazquez on 01/03/2017.
 */

public class Audit {
    private int id;
    private String standard;
    private Data data;
    private AuditStatus status;
    private Auditor leaderAuditor;

    public Audit() {    }

    public Audit(int id, String standard,  Data data, AuditStatus status,  Auditor leaderAuditor) {
        this.id = id;
        this.standard=standard;
        this.data = data;
        this.status=status;
        this.leaderAuditor = leaderAuditor;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getStandard() {
        return standard;
    }
    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }

    public AuditStatus getStatus() {
        return status;
    }
    public void setStatus(AuditStatus status) {
        this.status = status;
    }

    public Auditor getLeaderAuditor() {
        return leaderAuditor;
    }
    public void setLeaderAuditor(Auditor leaderAuditor) {
        this.leaderAuditor = leaderAuditor;
    }
}
