#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public abstract class CustomAppCompatActivity extends AppCompatActivity {

    
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
    
}
