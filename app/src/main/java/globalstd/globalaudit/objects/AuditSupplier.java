package globalstd.globalaudit.objects;

/**
 * Created by Gabriel Vazquez on 01/03/2017.
 */

public class AuditSupplier extends Audit{
    private String supplier;

    public AuditSupplier() {    }

    public AuditSupplier(int id, String standard, Data data, AuditStatus status, Auditor leaderAuditor) {
        super(id, standard, data, status, leaderAuditor);
    }

    public AuditSupplier(int id, String standard, Data data, AuditStatus status, Auditor leaderAuditor, String supplier) {
        super(id, standard, data, status, leaderAuditor);
        this.supplier = supplier;
    }

    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
