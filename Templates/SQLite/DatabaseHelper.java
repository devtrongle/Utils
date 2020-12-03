#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ${ClassName} extends SQLiteOpenHelper {
    private final String TAG = ${ClassName}.class.getSimpleName();
    
    private static final String DATABASE_NAME = "DATABASE_HELPER";
    public static final int DATABASE_VERSION = 1;

    private static ${ClassName} instance;

    public static ${ClassName} getInstance(Context context){
        if(instance == null)
            instance = new ${ClassName}(context);
        return instance;
    }

    private ${ClassName}(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Check whether the table exists in the database or not
     * @param tableName The name of the table will be checked
     * @return table exists or not
     */
    public boolean checkTableExist(String tableName){
        SQLiteDatabase db = this.getReadableDatabase();

        if (tableName == null || db == null || !db.isOpen())
            return false;

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }

        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }
}
