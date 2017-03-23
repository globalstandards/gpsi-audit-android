package globalstd.globalaudit.services;

import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import globalstd.globalaudit.Constants;
import globalstd.globalaudit.GlobalAuditException;

import globalstd.globalaudit.objects.Address;
import globalstd.globalaudit.objects.User;
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

    /**
     *
     * @param email Email del usuario
     * @param password Contraseña del usuario
     *
     * @throws GlobalAuditException con código de error INVALID_CREDENTIALS
     */
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
            throw new GlobalAuditException(GlobalAuditException.UNEXPECTED_ERROR);
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

    /**
     *
     * @param company Nombre de la compañia
     * @param username Nombre del usuario
     * @param email Email del usuario
     * @param password Contraseña
     *
     * @throws GlobalAuditException con código de error EMAIL_ALREADY_EXISTS
     * @throws GlobalAuditException con código de error COMPANY_NAME_ALREADY_EXISTS
     */
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
            throw new GlobalAuditException(GlobalAuditException.UNEXPECTED_ERROR);
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
     *
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
            throw new GlobalAuditException(GlobalAuditException.UNEXPECTED_ERROR);
        }

        ArrayList<User> users = new ArrayList<>();
        try {
            JSONObject response = new JSONObject(odooService.getUsers(this.cookie, body.toString()).execute().body());
            JSONObject result = response.getJSONObject("result");
            JSONArray records = result.getJSONArray("records");
            for (int i = 0; i < records.length(); ++i) {
                JSONObject item = records.getJSONObject(i);

                byte[] imageSmall = Base64.decode(item.getString("image_small"), Base64.NO_WRAP);

                Address address = new Address(
                        item.get("street") instanceof String ? item.getString("street") : null,
                        item.get("street2") instanceof String ? item.getString("street") : null,
                        item.get("city") instanceof String ? item.getString("city") : null,
                        item.get("state_id") instanceof JSONArray ? item.getJSONArray("state_id").getString(1) : null,
                        item.get("zip") instanceof String ? item.getString("zip") : null,
                        item.get("country_id") instanceof JSONArray ? item.getJSONArray("country_id").getString(1) : null
                );
                User user = new User(
                        item.getInt("id"),
                        item.getString("name"),
                        item.getString("login"),
                        imageSmall,
                        address,
                        "Role",
                        "Language");
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
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param user
     */
    public void updateUser(User user) {
        JSONObject body = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            params.put("name", user.getName());
            params.put("login", user.getEmail());
            params.put("street", user.getEmail());
            params.put("street2", user.getEmail());
            params.put("city", user.getEmail());
            params.put("state", user.getEmail());
            params.put("zip", user.getEmail());
            params.put("country", user.getEmail());
            params.put("phone", user.getEmail());
            params.put("mobile", user.getEmail());

            body.put("jsonrpc", "2.0");
            body.put("params", params);
        } catch (JSONException e) {
            throw new GlobalAuditException(GlobalAuditException.UNEXPECTED_ERROR);
        }

        try {
            Response<String> response = odooService.updateUser(user.getId(), this.cookie, body.toString()).execute();
            body = new JSONObject(response.body());
            JSONObject result = body.getJSONObject("result");
            if (result.has("error")) {
                JSONObject error = result.getJSONObject("error");
                int code = error.getInt("code");
                if (code == 3) throw new GlobalAuditException(GlobalAuditException.RECORD_NOT_FOUND);
            }
        } catch (IOException e) {
            throw new GlobalAuditException(GlobalAuditException.INTERNET_ERROR);
        } catch (JSONException e) {
            throw new GlobalAuditException(GlobalAuditException.SERVER_ERROR);
        }
    }
}
