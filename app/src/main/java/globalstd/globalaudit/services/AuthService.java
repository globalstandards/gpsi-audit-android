package globalstd.globalaudit.services;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import globalstd.globalaudit.Constants;
import globalstd.globalaudit.GlobalAuditException;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by software on 21/03/17.
 */

public class AuthService {
    private String sessionId;
    private OdooService odooService;

    public AuthService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.GLOBAL_AUDIT_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        this.odooService = retrofit.create(OdooService.class);
    }

    public void signIn(String email, String password) {
        JSONObject body = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            params.put("db", "demo");
            params.put("login", email);
            params.put("password", password);

            body.put("jsonrpc", "2.0");
            body.put("method", "call");
            body.put("params", params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String response = odooService.authenticate(body.toString()).execute().body();
            JSONObject json = new JSONObject(response);
            JSONObject resultPart = json.getJSONObject("result");
            if (resultPart.isNull("company_id")) {
                throw new GlobalAuditException(GlobalAuditException.INVALID_CREDENTIALS);
            }
            this.sessionId = resultPart.getString("session_id");
        } catch (IOException e) {
            throw new GlobalAuditException(GlobalAuditException.INTERNET_ERROR);
        } catch (JSONException e) {
            throw new GlobalAuditException(GlobalAuditException.SERVER_ERROR);
        }
    }

    public void signUp() {

    }

    public void logout() {

    }

    /**
     * Regresa un diccionario con la lista de usuarios asociados al usuario autenticado.
     * El formato del diccionario es similar al siguiente:<br/>
     * <pre>
     * {
     *     "length": XX,      // número de elementos en la lista
     *     "records": [],     // registros
     * }
     * </pre>
     *
     * @param limit Máximo número de registros a regresar.
     * @param offset Número de registros a omitir.
     * @return Diccionario con lista de registros solicitados.
     */
    public JSONObject getUsers(int limit, int offset) {
        JSONObject body = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            params.put("offset", offset);
            params.put("limit", limit);

            body.put("jsonrpc", "2.0");
            body.put("params", params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject result = null;
        try {
            JSONObject response = new JSONObject(odooService.authenticate(body.toString()).execute().body());
            result = response.getJSONObject("result");
        } catch (IOException e) {
            throw new GlobalAuditException(GlobalAuditException.INTERNET_ERROR);
        } catch (JSONException e) {
            throw new GlobalAuditException(GlobalAuditException.SERVER_ERROR);
        }
        return result;
    }
}
