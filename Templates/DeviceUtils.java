package alo360.vn.aloloader.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

import static alo360.vn.aloloader.Utils.Functions.loadFileAsString;
import static alo360.vn.aloloader.Volley.Url_API.SPEED_TEST;
import static android.content.Context.ACTIVITY_SERVICE;

/*******************************************************************************
 * Date created: 2020/10/5
 * Last updated:  2020/10/5
 * Name of project: AloLoader
 * Description: DeviceUtils.java
 * Auth: James Ryan
 ******************************************************************************/

public class DeviceUtils {
    public static final String TAG = DeviceUtils.class.getSimpleName();

    private static DeviceUtils instance;
    private Context context;

    public static final int PORTRAIT = 0;
    public static final int LANDSCAPE = 1;

    public static final int WIDTH = 1;
    public static final int HEIGHT = 2;

    public static final int AVAIL = 0;
    public static final int TOTAL = 1;
    public SpUtils spUtils;

    public static DeviceUtils getInstance(Context context){
        if (instance == null) {
            instance = new DeviceUtils(context);
        }

        return instance;
    }


    private DeviceUtils(Context context){
        this.context = context;
        this.spUtils = SpUtils.getInstance(context);
    }


    /**
     * Get screen orientation
     * @return Screen orientation
     */
    public int getScreenOrientation(){
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return LANDSCAPE;
        } else {
            return PORTRAIT;
        }
    }


    /**
     * Get MAC address of the device
     * @return MAC address of the device
     */
    public String getMacAddress() {

        String macAddress = "000000000000";
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return macAddress;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                macAddress = res1.toString();
            }
        } catch (Exception ex) {
        }
        try {
            macAddress = loadFileAsString("/sys/class/net/eth0/address")
                    .toUpperCase().substring(0, 17);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return macAddress.toUpperCase().replace(":", "");

//        return "";
    }


    /**
     * Check the network connection speed
     * @param callback callback
     */
    public void connectionSpeedTest(@NonNull IResultSpeedTest callback){
        if(isNetworkAvailable()){
            new SpeedTestTask(callback).execute();
        }else{
            callback.onError(null,"Không có kết nối mạng!");
        }
    }

    class SpeedTestTask extends AsyncTask<Void, Void, String> {

        private IResultSpeedTest callback;

        public SpeedTestTask(IResultSpeedTest callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... params) {

            SpeedTestSocket speedTestSocket = new SpeedTestSocket();
            speedTestSocket.startDownload(SPEED_TEST);
            //add a listener to wait for SpeedTest completion and progress
            speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

                @Override
                public void onCompletion(SpeedTestReport report) {
                    // called when download/upload is complete
                    callback.SpeedTestReport(report);
                }

                @Override
                public void onError(SpeedTestError speedTestError, String errorMessage) {
                    // called when a download/upload error occur
                    callback.onError(speedTestError,errorMessage);
                }

                @Override
                public void onProgress(float percent, SpeedTestReport report) {
                    // called to notify download/upload progress
                    callback.onProgress(percent,report);
                }
            });

            return null;
        }
    }

    public interface IResultSpeedTest {
        void SpeedTestReport(SpeedTestReport report);
        void onError(SpeedTestError speedTestError, String errorMessage);
        void onProgress(float percent, SpeedTestReport report);
    }



    /**
     *
     * @param type avail or total
     * @return RAM size
     */
    public long getRAM(int type){
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        if(type == AVAIL){
            return mi.availMem;
        }else{
            return mi.totalMem;
        }
    }


    /**
     * Get CPU information
     * @return CPU information
     * @throws IOException
     */
    public Map<String, String> getCPUInfo () throws IOException {

        BufferedReader br = new BufferedReader (new FileReader("/proc/cpuinfo"));

        String str;

        Map<String, String> output = new HashMap<>();

        while ((str = br.readLine ()) != null) {

            String[] data = str.split (":");

            if (data.length > 1) {

                String key = data[0].trim ().replace (" ", "_");
                if (key.equals ("model_name")) key = "cpu_model";

                output.put (key, data[1].trim ());

            }

        }

        br.close ();

        return output;

    }


    /**
     *
     * @return status network
     */
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }

    /**
     * Get location of the device
     */
    public void getLocation() {
        if(checkPlayServices()){
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                spUtils.setValueString(SpUtils.LATITUDE, String.valueOf(location.getLatitude()));
                                spUtils.setValueString(SpUtils.LONGITUDE, String.valueOf(location.getLongitude()));
                            }
                        }
                    });
        }else{
            spUtils.setValueString(SpUtils.LATITUDE, String.valueOf(0));
            spUtils.setValueString(SpUtils.LONGITUDE, String.valueOf(0));
        }
    }

    /**
     * Check if the Play service is available
     * @return exits or not exist
     */
    public boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        return apiAvailability.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS;
    }

    /**
     * Get the screen width or height of the device
     * @param type Type width or height {@see DeviceUtils.WIDTH or DeviceUtils.HEIGHT}
     * @return Screen size by type
     */
    public int getScreenSize(int type) {
        return type == WIDTH ? Resources.getSystem().getDisplayMetrics().widthPixels :  Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    /**
     * Check for external memory
     * @return exists or not
     */
    public boolean isExternalStorage(){
        //Get all external storage
        File[] externalStorageFiles= ContextCompat.getExternalFilesDirs(context,null);
        return externalStorageFiles.length > 1;
    }


    /**
     * Get the device name
     * @return device name
     */
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        }
        return manufacturer + " " + model;
    }


    /**
     * Get available memory size
     * @return bytes
     */
    public long getAvailableMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * Get total memory size
     * @return bytes
     */
    public long getTotalMemorySize() {
        File external = Environment.getDataDirectory();
        return external.getTotalSpace();
    }

    /**
     *
     * @return status location
     */
    public boolean isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }


    /**
     * Get android API: 9 (Pie)
     * @return Android API
     */
    public String getAndroidAPI(){
        StringBuilder builder = new StringBuilder();
        builder.append(Build.VERSION.RELEASE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException | IllegalAccessException | NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                String name = fieldName;
                if(name.equals("R")) name = "R";
                if(name.equals("Q")) name = "Q";
                if(name.equals("P")) name = "Pie";
                if(name.equals("O")) name = "Oreo";
                if(name.equals("N")) name = "Nougat";
                if(name.equals("M")) name = "Marshmallow";

                if(name.startsWith("O_")) name = "Oreo++";
                if(name.startsWith("N_")) name = "Nougat++";

                builder.append(" (").append(name).append(")");
            }
        }


        return builder.toString();
    }


    /**
     * Get version name app (1.0.1 beta)
     * @return version name app
     */
    public String getVersionName(){
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        String myVersionName = "";
        try {
            myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {            e.printStackTrace();
        }
        return myVersionName;
    }

    /**
     * Get version code app (11)
     * @return version code app
     */
    public int getVersionCode(){
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        int verCode = 0;
        try {
            verCode = packageManager.getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * Compare 2 versions code
     * Returns true if the current version is the most recent
     * @param version version code new (19)
     * @return true if the current version is the most recent
     */
    public boolean versionCompare(String version){
        try{
            final int verCodeCurrent = getVersionCode();
            final int verCodeNew =  Integer.parseInt(version);
            return verCodeCurrent >= verCodeNew;
        }catch (Exception e){
            return false;
        }
    }
}
