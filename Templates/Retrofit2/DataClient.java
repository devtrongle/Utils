#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataClient {

    //@GET("/api/getData?funcName=" + FUNC_GET_CITIES)
    //Call<ResCity> getCities();

    //@FormUrlEncoded
    //@POST("/api/getParking")
    //Call<ResParking> getParking(@Field("funcName") String funcName,@Field("id") int id);

}
