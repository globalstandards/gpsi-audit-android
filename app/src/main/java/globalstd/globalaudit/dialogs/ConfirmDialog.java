package globalstd.globalaudit.dialogs;

/**
 * Created by Gabriel Vazquez on 23/03/2017.
 */

public class ConfirmDialog {
    String title;
    String message;
    String positive;
    String negative;

    public ConfirmDialog(String title, String message, String positive, String negative) {
        this.title=title;
        this.message=message;
        this.positive=positive;
        this.negative=negative;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getPositive() {
        return positive;
    }
    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getNegative() {
        return negative;
    }
    public void setNegative(String negative) {
        this.negative = negative;
    }
}

