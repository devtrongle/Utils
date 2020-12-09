#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

public class APIUtils {
    public static final String TAG = APIUtils.class.getSimpleName();

    public static final String Base_Url = "${URL_API}";

    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
