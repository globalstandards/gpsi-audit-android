package globalstd.globalaudit.services;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
                .baseUrl("http://192.168.99.100:8079/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        OdooService odooService = retrofit.create(OdooService.class);

        try {
            String response = odooService.authenticate(body.toString()).execute().body();
            JSONObject json = new JSONObject(response);
            json.getJSONObject("result");
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
