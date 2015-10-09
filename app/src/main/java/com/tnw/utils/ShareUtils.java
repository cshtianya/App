package com.tnw.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ShareUtils implements IShareUtils {

	private static SharedPreferences sharedPreferences;

	private static ShareUtils _shareUtils ;

	public static ShareUtils getInstance(Context context) {

		if (sharedPreferences == null)
			sharedPreferences = context.getSharedPreferences(SHARENAME,
					Context.MODE_PRIVATE);

		if (_shareUtils == null)
			return new ShareUtils();

		return _shareUtils;
	}

	/**
	 * Set a {@link String} value in the preferences editor, to be written back
	 * once {@link Editor#commit()} is called.
	 * 
	 * @param key
	 *            The name of the preference to modify.
	 * @param value
	 *            The new value for the preference.
	 * 
	 * @return Returns a reference to the same Editor object, so you can chain
	 *         put calls together.
	 */
	public Editor setStringValues(String key, String value) {
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
		return editor;
	}

	/**
	 * Set a {@link Boolean} value in the preferences editor,to be written back
	 * once once {@link Editor#commit()} is
	 * called.
	 * 
	 * @param key
	 *            The name of the preference to modify.
	 * @param value
	 *            The new value for the preference.
	 * 
	 * @return Returns a reference to the same Editor object, so you can chain
	 *         put calls together.
	 */
	public Editor setBooleanValues(String key, Boolean value) {
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
		return editor;
	}

	/**
	 * Set a {@link Float} value in the preferences editor,to be written back
	 * once {@link Editor#commit()} is called.
	 * 
	 * @param key
	 *            The name of the preference to modify.
	 * @param value
	 *            The new value for the preference.
	 * @return Returns a reference to the same Editor object,so you can chain
	 *         put calls together.
	 */
	public Editor setFloatValues(String key, float value) {
		Editor editor = sharedPreferences.edit();
		editor.putFloat(key, value);
		editor.commit();
		return editor;
	}

	/**
	 * Set a {@link Integer} value in the preferences editor,to be written back
	 * once {@link Editor#commit()} is called.
	 * 
	 * @param key
	 *            The name of the preference to modify.
	 * @param value
	 *            The new value for the preference.
	 * @return Returns a reference to the same Editor object,so you can chain
	 *         put calls together.
	 */
	public Editor setIntValues(String key, int value) {
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
		return editor;
	}

	/**
	 * Set a {@link Long} value in the preferences editor,to be written back
	 * once {@link Editor#commit()} is called.
	 * 
	 * @param key
	 *            The name of the preference to modify.
	 * @param value
	 *            The new value for the preference.
	 * @return Returns a reference to the same Editor object,so you can chain
	 *         put calls together.
	 */
	public Editor setLongValues(String key, int value) {
		Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
		return editor;
	}

	/**
	 * Retrieve a {@link String} value from the preferences.
	 * 
	 * @param key
	 *            The name of the preference to retrieve. <br>
	 *            empty to return if this preference does not exist.
	 * 
	 * @return Returns the preference value if it exists, or empty.
	 * 
	 */
	public String getStringValues(String key) {
		return sharedPreferences.getString(key, "");
	}

	/**
	 * Retrieve a {@link Boolean} value from the preference.
	 * 
	 * @param key
	 *            The name of the preference to retrieve. <br>
	 *            false to return if this preference does not exist.
	 * @return Returns the preference value if it exists,or default
	 *         Value(false).
	 */
	public boolean getBooleanValues(String key) {
		return sharedPreferences.getBoolean(key, false);
	}

	/**
	 * Retrieve a {@link Float} value from the preference.
	 * 
	 * @param key
	 *            The name of the preference to retrieve. <br>
	 *            -1 to return if this preference does not exist.
	 * @return Returns the preference value if it exists, or default value(-1)
	 */
	public float getFloatValues(String key) {
		return sharedPreferences.getFloat(key, -1);
	}

	/**
	 * Retrieve a {@link Integer} value from the preference.
	 * 
	 * @param key
	 *            The name of the preference to retrieve. <br>
	 *            -1 to return if this preference does not exist.
	 * @return Returns the preference value if it exists, or default value(-1)
	 */
	public int getIntValues(String key) {
		return sharedPreferences.getInt(key, -1);
	}

	/**
	 * Retrieve a {@link Long} value from the preference.
	 * 
	 * @param key
	 *            The name of the preference to retrieve. <br>
	 *            -1 to return if this preference does not exist.
	 * @return Returns the preference value if it exists, or default value(-1)
	 */
	public Long getLongValues(String key) {
		return sharedPreferences.getLong(key, -1);
	}

	/**
	 * Mark in the editor that a preference value should be removed, which will
	 * be done in the actual preferences once
	 * {@link Editor#commit()} is called.
	 * 
	 * <p>
	 * Note that when committing back to the preferences, all removals are done
	 * first, regardless of whether you called remove before or after put
	 * methods on this editor.
	 * 
	 * @param key
	 *            The name of the preference to remove.
	 * 
	 * @return Returns true if the preference value were successfully removed to
	 *         persistent storage,otherwise false;
	 */
	public boolean removeShareValues(String key) {
		Editor editor = sharedPreferences.edit();
		editor.remove(key);
		return editor.commit();
	}

	/**
	 * Mark in the editor to remove all values from the preferences. Once commit
	 * is called, the only remaining preferences will be any that you have
	 * defined in this editor. Note that when committing back to the
	 * preferences, the clear is done first, regardless of whether you called
	 * clear before or after put methods on this editor.
	 */
	public void clearAll(Context context) {
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
		System.gc();
	}

}
