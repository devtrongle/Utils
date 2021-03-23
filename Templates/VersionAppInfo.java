package alo360.vn.aloloader.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import java.util.List;

public class VersionAppInfo {
    public static final String TAG = VersionAppInfo.class.getSimpleName();

    private final Context context;

    private PackageInfo mPackageInfo;
    private PackageManager mPackageManager;

    private String mCurrentPackageName;

    public VersionAppInfo setPackageName(String packageName){
        this.mCurrentPackageName = packageName;
        this.mPackageManager = context.getPackageManager();
        try {
            this.mPackageInfo = context.getPackageManager().getPackageInfo(mCurrentPackageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }

    public VersionAppInfo(Context context){
        this.context = context;
    }

    public VersionAppInfo(Context context, String packageName){
        this.context = context;
        setPackageName(packageName);
    }

    private ResolveInfo getResolveInfo(){
        //Check null package name
        if(mCurrentPackageName == null) return null;
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = mPackageManager.queryIntentActivities(mainIntent, 0);
        for(ResolveInfo ri : pkgAppsList) {
            if(ri.activityInfo.packageName.equals(mCurrentPackageName)){
                return ri;
            }
        }
        return null;
    }

    public int getVersionCode(){
        if(mPackageInfo == null) return -1;
        return mPackageInfo.versionCode;
    }

    public String getVersionName(){
        if(mPackageInfo == null) return null;
        return mPackageInfo.versionName;
    }
  
   public boolean compareVersion(int versionNew){
        return getVersionCode() < versionNew;
    }

    public String getVersionNameAndCode(){
        if(getVersionName() == null) return null;
        return getVersionName() + " ("+getVersionCode()+")";
    }

    public String getAppName(){
        ResolveInfo mInfo = getResolveInfo();
        if(mInfo == null) return null;
        return mInfo.loadLabel(mPackageManager).toString();
    }

    public Drawable getIcon(){
        ResolveInfo mInfo = getResolveInfo();
        if(mInfo == null) return null;
        return mInfo.activityInfo.loadIcon(mPackageManager);
    }

    /**
     * Kiểm tra ứng dụng có tồn tại hay chưa
     * @return true là đã được cài đặt
     */
    public boolean isAppInstalled(){
        //Check null package name
        if(mCurrentPackageName == null) return false;

        try {
            context.getPackageManager().getPackageInfo(mCurrentPackageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
