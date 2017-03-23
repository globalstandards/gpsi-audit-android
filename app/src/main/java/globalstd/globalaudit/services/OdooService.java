package globalstd.globalaudit.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by software on 21/03/17.
 */

public interface OdooService {
    @Headers({
            "Content-Type: application/json"
    })
    @POST("web/session/authenticate")
    Call<String> authenticate(@Body String params);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("ga/api/signup")
    Call<String> signUp(@Body String params);

    @GET("ga/api/logout")
    Call<Void> logout();

    @Headers({
            "Content-Type: application/json"
    })
    @GET("ga/api/settings/users")
    Call<String> getUsers(@Header("Cookie") String cookie, @Body String params);

    @POST("ga/api/settings/users")
    Call<String> createUser(@Body String params);

    @POST("ga/api/settings/users/{id}")
    Call<String> updateUser(@Body String params);
}
