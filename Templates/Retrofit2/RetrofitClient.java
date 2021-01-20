#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String TAG = RetrofitClient.class.getSimpleName();
    private final Context context;
    private final DataClient mDataClient;

    @SuppressLint("StaticFieldLeak")
    private static RetrofitClient instance;

   private RetrofitClient(Context context){
        this.context = context;
        final String url = SpHelper.getInstance(context).getValueString(SpHelper.KeyList.URL_SERVER);
        OkHttpClient builder = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(builder)
                .build();
        mDataClient = mRetrofit.create(DataClient.class);
    }

    public static RetrofitClient getInstance(Context context){
        if(instance == null){
            instance = new RetrofitClient(context);
        }
        return instance;
    }
    
     /**
     * Hàm rút gọn thao tác request lên api bằng retrofit
     * @param call interface của retrofit 
     *             @see  alo360.com.guarantee.retrofit2.DataClient
     * @param callback hàm trả về kết quả sau khi request lên server
     * @param <T> Custom object
     */
    private  <T> void requestAPI(@NonNull Call<T> call, @NonNull ICallbackRequestAPI<T> callback){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.body() != null) {
                    callback.onResponse(response.body());
                } else {
                    callback.onFailure(context.getString(R.string.Cannot_download_data_from_server));
                    Log.e(TAG, "Method [requestAPI]: response.body() == NULL");
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
                Log.e(TAG, "Method [requestAPI]: "+t.getMessage());
            }
        });
    }

    /**
     * Interface trả về kết quả sau khi request
     * @param <T> custom object
     */
    private interface ICallbackRequestAPI <T>{
        /**
         * Nếu thành công trả về trong đây
         * @param data dữ liệu sau khi thành công
         */
        void onResponse(@NonNull T data);

        /**
         * Thất bại trả về tin nhắn trong đây
         * @param message tin nhắn thất bại
         */
        void onFailure(String message);
    }


}
