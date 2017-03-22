package globalstd.globalaudit;

/**
 * Created by software on 22/03/17.
 */

public class GlobalAuditException extends RuntimeException {
    /**
     * Credenciales invalidas al iniciar sesión
     */
    public static final int INVALID_CREDENTIALS = 1;

    /**
     * Error en el servidor
     */
    public static final int SERVER_ERROR = 2;

    /**
     * Error con internet.
     */
    public static final int INTERNET_ERROR = 3;

    /**
     * Email actualmente existe.
     */
    public static final int EMAIL_ALREADY_EXIST = 4;


    private int code;

    public GlobalAuditException(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
