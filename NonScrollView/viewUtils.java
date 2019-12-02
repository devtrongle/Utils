

/*
 *  Date created: 11/06/2019
 *  Last updated: 11/06/2019
 *  Name project: 
 *  Description:
 *  Auth: trong.le@1byte.com
 */

import android.app.Application;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.OneByte.Affiligate.R;
import com.OneByte.Affiligate.view.BrowserActivity;

import java.util.Objects;

public class viewUtils {


    /**
     * Set transparent status bar
     * @param context context (AppCompatActivity)
     */
    public static void setTransparentStatusBar(AppCompatActivity context){
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * Set color status bar
     * @param context context
     * @param color color
     */
    public static void setColorStatusBar(AppCompatActivity context, int color){
        context.getWindow().setStatusBarColor(context.getResources().getColor(color));
    }

    /**
     * Set up toolbar
     * @param context context (AppCompatActivity)
     * @param colorButtonBack color of button back
     * @param title title of toolbar
     * @param colorStatusBar color of status bar
     */
    public static void setupToolbar(Context context,int idToolbar, int colorButtonBack, String title, int colorStatusBar){

        setColorStatusBar((AppCompatActivity)context,colorStatusBar);

        Toolbar toolbar = ((AppCompatActivity)context).findViewById(idToolbar);
        ((AppCompatActivity)context).setSupportActionBar(toolbar);

        Objects.requireNonNull(((AppCompatActivity)context).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setTint(context.getResources().getColor(colorButtonBack));
        toolbar.setNavigationOnClickListener(view -> ((AppCompatActivity)context).finish());

        if(title!=null)
            toolbar.setTitle(title);
    }

    /**
     *
     * @param context context
     * @param content content text
     */
    public static void copiedToClipboard(Context context, String content){
        ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm != null) {
            cm.setText(content);
        }
        Toast.makeText(context, context.getResources().getString(R.string.Copied_to_Clipboard), Toast.LENGTH_SHORT).show();
    }

}
