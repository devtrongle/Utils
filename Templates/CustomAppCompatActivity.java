#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public abstract class CustomAppCompatActivity extends AppCompatActivity {

    public static class ToastStatus{
        public static final int STATUS_SUCCESS = 1;
        public static final int STATUS_DEFAULT = 0;
        public static final int STATUS_FAIL = 2;
    }
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        initVariables();
        main(savedInstanceState);
        initActon();
    }


    /**
     * Initialize the root layout
     * @return root view
     */
    protected abstract View initLayout();

    /**
     * Variable initialization
     */
    protected abstract void initVariables();

    /**
     * The main function contains the functions of the application
     * @param savedInstanceState if the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.
     *     <b><i>Note: Otherwise it is null.</i></b>
     */
    protected abstract void main(@Nullable Bundle savedInstanceState);

    /**
     * Contains key press or interactive functions
     */
    protected abstract void initActon();
    
    /**
     * Cài đặt màu cho text trên status bar
     * @param isResetDefault nếu false set màu đen cho text ngược lại reset lại mặc định trong style.xml
     */
    public void setBlackTextColorStatusBar(boolean isResetDefault){
        //Cài đặt màu đen cho chữ trên status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(isResetDefault){
                getWindow().getDecorView().setSystemUiVisibility(0);
            }else{
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * init toolbar
     * @param toolbar toolbar view
     */
    public void initToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    /**
     * init toolbar
     * @param toolbar toolbar view
     * @param resId navigation icon
     */
    public void initToolbar(Toolbar toolbar, @DrawableRes int resId){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(resId);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
    
     /**
     * Show the view for the specified duration.
     * @param message message
     * @param gravity Set the location at which the notification should appear on the screen.
     * @see android.view.Gravity
     */
    public void showCustomToast(String message, int gravity){
        Toast toast = new Toast(this);
        RelativeLayout rlMain = initCustomToastView();
        TextView tvContent = (TextView) rlMain.getChildAt(0);
        if(tvContent != null){
            tvContent.setText(message);
            tvContent.setTextColor(Color.WHITE);
        }
        toast.setView(rlMain);
        toast.setGravity(gravity,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();

    }

    /**
     * Khởi tạo giao diện cho toast
     * @return view
     */
    private RelativeLayout initCustomToastView(){
        RelativeLayout rlMain = new RelativeLayout(this);

        TextView tvContent = new TextView(this);
        tvContent.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvContent.setTextSize(15);

        RelativeLayout.LayoutParams lpContent = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpContent.setMargins(28,22,28,22);
        lpContent.addRule(RelativeLayout.CENTER_IN_PARENT);
        rlMain.addView(tvContent,lpContent);

        RelativeLayout.LayoutParams lpMain = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpMain.setMargins(2,2,2,2);
        rlMain.setLayoutParams(lpMain);


        int startColor = Color.rgb(15,32,39);
        int centerColor = Color.rgb(32,58,67);
        int endColor = Color.rgb(44,83,100);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColors(new int[]{startColor,centerColor,endColor});
        gradientDrawable.setAlpha(204);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setStroke(1, Color.WHITE);
        gradientDrawable.setCornerRadius(18);

        rlMain.setBackground(gradientDrawable);

        return rlMain;
    }

    /**
     * Show the view for the specified duration.
     * @param resID the resource identifier of the string resource to be displayed
     * @param gravity Set the location at which the notification should appear on the screen.
     * @see android.view.Gravity
     */
    public void showCustomToast(@StringRes int resID, int gravity){
        Toast toast = new Toast(this);
        RelativeLayout rlMain = initCustomToastView();
        TextView tvContent = (TextView) rlMain.getChildAt(0);
        if(tvContent != null){
            tvContent.setText(resID);
            tvContent.setTextColor(Color.WHITE);
        }
        toast.setView(rlMain);
        toast.setGravity(gravity,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Show the view for the specified duration.
     * @param message message
     * @param gravity Set the location at which the notification should appear on the screen.
     *                @see android.view.Gravity
     * @param status set the status for the toast
     *               @see alo360.com.guarantee.views.CustomAppCompatActivity.ToastStatus
     *
     */
    public void showCustomToast(String message, int gravity, int status){
        Toast toast = new Toast(this);
        RelativeLayout rlMain = initCustomToastView();
        TextView tvContent = (TextView) rlMain.getChildAt(0);
        if(tvContent != null){
            tvContent.setText(message);
            int iconID = -1;
            switch (status){
                case ToastStatus.STATUS_DEFAULT:
                    tvContent.setTextColor(Color.WHITE);
                    break;
                case ToastStatus.STATUS_FAIL:
                    tvContent.setTextColor(Color.RED);
                    iconID = R.drawable.ic_baseline_error_outline_18;
                    break;
                case ToastStatus.STATUS_SUCCESS:
                    tvContent.setTextColor(Color.GREEN);
                    iconID = R.drawable.ic_baseline_check_circle_outline_18;
                    break;
            }

            if(iconID != -1){
                tvContent.setCompoundDrawablesWithIntrinsicBounds(iconID,0,0,0);
                tvContent.setCompoundDrawablePadding(10);
            }
        }
        toast.setView(rlMain);
        toast.setGravity(gravity,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
