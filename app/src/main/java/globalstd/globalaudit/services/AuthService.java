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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.GLOBAL_AUDIT_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        OdooService odooService = retrofit.create(OdooService.class);

        try {
            String response = odooService.authenticate(body.toString()).execute().body();
            JSONObject json = new JSONObject(response);
            JSONObject resultPart = json.getJSONObject("result");
            if (resultPart.isNull("company_id")) {
                throw new GlobalAuditException(GlobalAuditException.INVALID_CREDENTIALS);
            }
            this.sessionId = resultPart.getString("session_id");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void signUp() {

    }

    public void logout() {

    }
}
