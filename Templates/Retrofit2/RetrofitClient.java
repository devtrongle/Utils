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
}
