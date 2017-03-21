package globalstd.globalaudit.objects;

/**
 * Created by Gabriel Vazquez on 21/03/2017.
 */

public class Plan {
    private String date;
    private String time;
    private Auditor auditor;
    private String process;

    public Plan(String date, String time, Auditor auditor, String process) {
        this.date = date;
        this.time = time;
        this.auditor = auditor;
        this.process = process;
    }

    public Plan() {    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public Auditor getAuditor() {
        return auditor;
    }
    public void setAuditor(Auditor auditor) {
        this.auditor = auditor;
    }

    public String getProcess() {
        return process;
    }
    public void setProcess(String process) {
        this.process = process;
    }
}
