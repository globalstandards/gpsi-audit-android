package globalstd.globalaudit.objects;

/**
 * Created by Gabriel Vazquez on 01/03/2017.
 */

public class Data {
    private String data;
    private String hour;

    public Data() {    }

    public Data(String data, String hour) {
        this.data = data;
        this.hour = hour;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
}
