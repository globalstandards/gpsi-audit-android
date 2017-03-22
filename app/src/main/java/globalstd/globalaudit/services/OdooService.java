package globalstd.globalaudit.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by software on 21/03/17.
 */

public interface OdooService {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("web/session/authenticate")
    Call<String> authenticate(@Body String params);
}
