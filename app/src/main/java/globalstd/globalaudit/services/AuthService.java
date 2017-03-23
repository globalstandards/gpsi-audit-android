package globalstd.globalaudit.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import globalstd.globalaudit.Constants;
import globalstd.globalaudit.GlobalAuditException;
import globalstd.globalaudit.models.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by software on 21/03/17.
 */

public class AuthService {
    private String sessionId;
    private String cookie;
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
            this.cookie = "session_id=" + this.sessionId + ";";
        } catch (IOException e) {
            throw new GlobalAuditException(GlobalAuditException.INTERNET_ERROR);
        } catch (JSONException e) {
            throw new GlobalAuditException(GlobalAuditException.SERVER_ERROR);
        }
    }

    public void signUp(String company, String username, String email, String password) {
        JSONObject body = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            params.put("company", company);
            params.put("username", username);
            params.put("email", email);
            params.put("password", password);

            body.put("jsonrpc", "2.0");
            body.put("params", params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Response<String> response = odooService.signUp(body.toString()).execute();
            body = new JSONObject(response.body());
            JSONObject result = body.getJSONObject("result");
            if (result.has("error")) {
                JSONObject error = result.getJSONObject("error");
                int code = error.getInt("code");
                if (code == 1) throw new GlobalAuditException(GlobalAuditException.EMAIL_ALREADY_EXISTS);
                else if (code == 2) throw new GlobalAuditException(GlobalAuditException.COMPANY_NAME_ALREADY_EXISTS);
            }
        } catch (IOException e) {
            throw new GlobalAuditException(GlobalAuditException.INTERNET_ERROR);
        } catch (JSONException e) {
            throw new GlobalAuditException(GlobalAuditException.SERVER_ERROR);
        }
    }

    public void logout() {
        try {
            odooService.logout().execute();
        } catch (IOException e) {
            throw new GlobalAuditException(GlobalAuditException.INTERNET_ERROR);
        }

        this.sessionId = null;
        this.cookie = null;
    }

    /**
     *
     * @param limit Máximo número de registros a regresar.
     * @param offset Número de registros a omitir.
     * @return Lista de registros solicitados.
     */
    public List<User> getUsers(int limit, int offset) {
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

        ArrayList<User> users = new ArrayList<>();
        try {
            JSONObject response = new JSONObject(odooService.getUsers(this.cookie, body.toString()).execute().body());
            JSONObject result = response.getJSONObject("result");
            JSONArray records = response.getJSONArray("records");
            for (int i = 0; i < records.length(); ++i) {
                JSONObject item = records.getJSONObject(i);
                User user = new User();
                user.id = item.getInt("id");
                user.name = item.getString("name");
                user.street1 = item.getString("street1");
                users.add(user);
            }
        } catch (IOException e) {
            throw new GlobalAuditException(GlobalAuditException.INTERNET_ERROR);
        } catch (JSONException e) {
            throw new GlobalAuditException(GlobalAuditException.SERVER_ERROR);
        }
        return users;
    }

    public void createUser(User user) {

    }

    public void updateUser(User user) {

    }
}
