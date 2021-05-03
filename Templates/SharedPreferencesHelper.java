#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ${ClassName}
{
	private static ${ClassName} instance;
	private final SharedPreferences mSP;
	public static final String DATA_APP = "DataApp";


	/**
	 * @param context A Context of the application package implementing this class.
	 */
	private ${ClassName}(@NonNull Context context) {
		this.mSP = context.getSharedPreferences(DATA_APP, Context.MODE_PRIVATE);
	}


	public static ${ClassName} getInstance(@NonNull Context context) {
		if(instance == null)
			instance = new SPUtils(context);
		return instance;
	}


	/**
	 * The method of saving data,
	 * we need to get the specific type of data to be saved,
	 * and then call different saving methods according to the type
	 *
	 * @param key
	 * @param object
	 */
	public void put(@NonNull String key, @NonNull Object object)
	{
		SharedPreferences.Editor editor = mSP.edit();
		if (object instanceof String)
		{
			editor.putString(key, (String) object);
		} else if (object instanceof Integer)
		{
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean)
		{
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float)
		{
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long)
		{
			editor.putLong(key, (Long) object);
		} else
		{
			editor.putString(key, object.toString());
		}

		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * Get the method to save the data,
	 * we get the specific type of the saved data according to the default value,
	 * and then call the relative method to get the value
	 *
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public Object get(@NonNull String key, @NonNull Object defaultObject)
	{
		if (defaultObject instanceof String)
		{
			return mSP.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer)
		{
			return mSP.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean)
		{
			return mSP.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float)
		{
			return mSP.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long)
		{
			return mSP.getLong(key, (Long) defaultObject);
		}

		return null;
	}

	/**
	 * Remove the value corresponding to a key value
	 * @param key
	 */
	public void remove(@NonNull String key)
	{
		SharedPreferences.Editor editor = mSP.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * Clear all data
	 */
	public void clear()
	{
		SharedPreferences.Editor editor = mSP.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * Query whether a key already exists
	 *
	 * @param key
	 * @return
	 */
	public boolean contains(@NonNull String key)
	{
		return mSP.contains(key);
	}

	/**
	 * Return all key-value pairs
	 * @return
	 */
	public Map<String, ?> getAll()
	{
		return mSP.getAll();
	}

	/**
	 * Create a compatible class that solves the SharedPreferencesCompat.apply method
	 * 
	 * @author zhy
	 * 
	 */
	private static class SharedPreferencesCompat
	{
		private static final Method sApplyMethod = findApplyMethod();

		/**
		 * Reflection find apply method
		 * 
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod()
		{
			try
			{
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e)
			{
			}

			return null;
		}

		/**
		 * If found, use apply to execute, otherwise use commit
		 * 
		 * @param editor
		 */
		public static void apply(@NonNull SharedPreferences.Editor editor)
		{
			try
			{
				if (sApplyMethod != null)
				{
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e)
			{
			} catch (IllegalAccessException e)
			{
			} catch (InvocationTargetException e)
			{
			}
			editor.commit();
		}
	}

}
