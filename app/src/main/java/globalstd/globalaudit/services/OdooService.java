package globalstd.globalaudit.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

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

    @POST("ga/api/logout")
    Call<Void> logout();

    @Headers({
            "Content-Type: application/json"
    })
    @GET("ga/api/settings/users")
    Call<String> getUsers(@Body String params);

    @POST("ga/api/settings/users")
    Call<String> createUser(@Body String params);

    @POST("ga/api/settings/users/{id}")
    Call<String> updateUser(@Body String params);
}
