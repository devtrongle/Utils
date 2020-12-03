#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;


public class ${ClassName} {
    public static final String TAG =  ${ClassName}.class.getSimpleName();

    private final Context context;

    public static final String TABLE_NAME = "${TableName}";


    private ${DatabaseHelperName} mDatabase;

    private static ${ClassName} instance;

    public static ${ClassName} getInstance(Context context){
        if(instance == null)
            instance = new ${ClassName}(context);
        return instance;
    }

    private ${ClassName}(Context context) {
        this.context = context;
        this.mDatabase = ${DatabaseHelperName}.getInstance(context);
        if(!mDatabase.checkTableExist(TABLE_NAME))
            createTable();
    }

    private void createTable(){
        final String create_table = String.format("CREATE TABLE %s (  )", TABLE_NAME);
        mDatabase.getWritableDatabase().execSQL(create_table);
    }

    public void addOrUpdate() {
        
    }

    /**
     * Add 
     * @return status
     */
    public boolean add(){
        return true;
    }


    /**
     * Update 
     * @return status
     */
    public boolean update(){
        return true;
    }


    /**
     * Delete 
     * @return delete status
     */
    public boolean delete(){
        return true;
    }

    /**
     * Delete all data
     */
    public void deleteAll(){
        try{
            SQLiteDatabase db = mDatabase.getWritableDatabase();
            db.execSQL("DELETE FROM "+ TABLE_NAME);
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }

    /**
     * Check if an item exists
     * @return exist or not exist
     */
    public boolean checkItemExistByID(){
        SQLiteDatabase db = mDatabase.getReadableDatabase();
        String query = String.format("");
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }


    /**
     * 
     * @param id 
     * @return object
     */
    public Object getItemByID(final int id){
        String query = String.format("");
        SQLiteDatabase db = mDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null){
            if(cursor.moveToFirst()){
                
                cursor.close();
                return new Object();
            }
        }
        return null;
    }



    /**
     * 
     * @return ArrayList<Object>
     */
    public ArrayList<Object> getListItem(){
        ArrayList<Object> listItem = new ArrayList<>();

        String query = String.format("");

        SQLiteDatabase db = mDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor!=null){
            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                   
                    listItem.add(new Object());
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return listItem;
    }

}
