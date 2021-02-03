package com.czy.business_base.sp;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

public class SpHelpUtils {
    /**
     * 简单存储工具类
     */
    private static SpUtils sDefaultSpUtils;

    /**
     * Set the default instance of {@link SpUtils}.
     *
     * @param spUtils The default instance of {@link SpUtils}.
     */
    public static void setDefaultSpUtils(final SpUtils spUtils) {
        sDefaultSpUtils = spUtils;
    }

    /**
     * Put the string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final String value) {
        put(key, value, getDefaultSpUtils());
    }

    /**
     * 提交map,一次性commit,减少频繁读写IO
     *
     * @param hashmap 存储集合
     */
    public static void put(@NotNull final Map<String, Object> hashmap) {
        put(hashmap, getDefaultSpUtils());
    }


    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final String value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSpUtils());
    }

    /**
     * 提交map,一次性commit,减少频繁读写IO
     *
     * @param hashmap  存储集合
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(final Map<String, Object> hashmap, final boolean isCommit) {
        put(hashmap, isCommit, getDefaultSpUtils());
    }


    /**
     * Return the string value in sp.
     *
     * @param key The key of sp.
     * @return the string value if sp exists or {@code ""} otherwise
     */
    public static String getString(@NonNull final String key) {
        return getString(key, getDefaultSpUtils());
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or {@code defaultValue} otherwise
     */
    public static String getString(@NonNull final String key, final String defaultValue) {
        return getString(key, defaultValue, getDefaultSpUtils());
    }


    /**
     * Put the int value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final int value) {
        put(key, value, getDefaultSpUtils());
    }

    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final int value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSpUtils());
    }

    /**
     * Return the int value in sp.
     *
     * @param key The key of sp.
     * @return the int value if sp exists or {@code -1} otherwise
     */
    public static int getInt(@NonNull final String key) {
        return getInt(key, getDefaultSpUtils());
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the int value if sp exists or {@code defaultValue} otherwise
     */
    public static int getInt(@NonNull final String key, final int defaultValue) {
        return getInt(key, defaultValue, getDefaultSpUtils());
    }

    /**
     * Put the long value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final long value) {
        put(key, value, getDefaultSpUtils());
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final long value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSpUtils());
    }

    /**
     * Return the long value in sp.
     *
     * @param key The key of sp.
     * @return the long value if sp exists or {@code -1} otherwise
     */
    public static long getLong(@NonNull final String key) {
        return getLong(key, getDefaultSpUtils());
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the long value if sp exists or {@code defaultValue} otherwise
     */
    public static long getLong(@NonNull final String key, final long defaultValue) {
        return getLong(key, defaultValue, getDefaultSpUtils());
    }

    /**
     * Put the float value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final float value) {
        put(key, value, getDefaultSpUtils());
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final float value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSpUtils());
    }

    /**
     * Return the float value in sp.
     *
     * @param key The key of sp.
     * @return the float value if sp exists or {@code -1f} otherwise
     */
    public static float getFloat(@NonNull final String key) {
        return getFloat(key, getDefaultSpUtils());
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the float value if sp exists or {@code defaultValue} otherwise
     */
    public static float getFloat(@NonNull final String key, final float defaultValue) {
        return getFloat(key, defaultValue, getDefaultSpUtils());
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final boolean value) {
        put(key, value, getDefaultSpUtils());
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final boolean value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSpUtils());
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key The key of sp.
     * @return the boolean value if sp exists or {@code false} otherwise
     */
    public static boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, getDefaultSpUtils());
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the boolean value if sp exists or {@code defaultValue} otherwise
     */
    public static boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return getBoolean(key, defaultValue, getDefaultSpUtils());
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final Set<String> value) {
        put(key, value, getDefaultSpUtils());
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key,
                           final Set<String> value,
                           final boolean isCommit) {
        put(key, value, isCommit, getDefaultSpUtils());
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key The key of sp.
     * @return the set of string value if sp exists
     * or {@code Collections.<String>emptySet()} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, getDefaultSpUtils());
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key,
                                           final Set<String> defaultValue) {
        return getStringSet(key, defaultValue, getDefaultSpUtils());
    }

    /**
     * Return all values in sp.
     *
     * @return all values in sp
     */
    public static Map<String, ?> getAll() {
        return getAll(getDefaultSpUtils());
    }

    /**
     * Return whether the sp contains the preference.
     *
     * @param key The key of sp.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean contains(@NonNull final String key) {
        return contains(key, getDefaultSpUtils());
    }

    /**
     * Remove the preference in sp.
     *
     * @param key The key of sp.
     */
    public static void remove(@NonNull final String key) {
        remove(key, getDefaultSpUtils());
    }

    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void remove(@NonNull final String key, final boolean isCommit) {
        remove(key, isCommit, getDefaultSpUtils());
    }

    /**
     * Remove all preferences in sp.
     */
    public static void clear() {
        clear(getDefaultSpUtils());
    }

    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void clear(final boolean isCommit) {
        clear(isCommit, getDefaultSpUtils());
    }

    ///
    // dividing line
    ///

    /**
     * Put the string value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final String key, final String value, @NonNull final SpUtils spUtils) {
        spUtils.put(key, value);
    }

    /**
     * 一次性提交
     *
     * @param map 存储集合
     * @param spUtils The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final Map<String, Object> map, @NonNull final SpUtils spUtils) {
        spUtils.put(map);
    }

    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final String key,
                           final String value,
                           final boolean isCommit,
                           @NonNull final SpUtils spUtils) {
        spUtils.put(key, value, isCommit);
    }

    /**
     * 一次性提交
     *
     * @param map  存储集合
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final Map<String, Object> map, final boolean isCommit, @NonNull final SpUtils spUtils) {
        spUtils.put(map, isCommit);
    }

    /**
     * Return the string value in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SpUtils}.
     * @return the string value if sp exists or {@code ""} otherwise
     */
    public static String getString(@NonNull final String key, @NonNull final SpUtils spUtils) {
        return spUtils.getString(key);
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of {@link SpUtils}.
     * @return the string value if sp exists or {@code defaultValue} otherwise
     */
    public static String getString(@NonNull final String key,
                                   final String defaultValue,
                                   @NonNull final SpUtils spUtils) {
        return spUtils.getString(key, defaultValue);
    }


    /**
     * Put the int value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final String key, final int value, @NonNull final SpUtils spUtils) {
        spUtils.put(key, value);
    }

    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final String key,
                           final int value,
                           final boolean isCommit,
                           @NonNull final SpUtils spUtils) {
        spUtils.put(key, value, isCommit);
    }

    /**
     * Return the int value in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SpUtils}.
     * @return the int value if sp exists or {@code -1} otherwise
     */
    public static int getInt(@NonNull final String key, @NonNull final SpUtils spUtils) {
        return spUtils.getInt(key);
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of {@link SpUtils}.
     * @return the int value if sp exists or {@code defaultValue} otherwise
     */
    public static int getInt(@NonNull final String key, final int defaultValue, @NonNull final SpUtils spUtils) {
        return spUtils.getInt(key, defaultValue);
    }

    /**
     * Put the long value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final String key, final long value, @NonNull final SpUtils spUtils) {
        spUtils.put(key, value);
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final String key,
                           final long value,
                           final boolean isCommit,
                           @NonNull final SpUtils spUtils) {
        spUtils.put(key, value, isCommit);
    }

    /**
     * Return the long value in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SpUtils}.
     * @return the long value if sp exists or {@code -1} otherwise
     */
    private static long getLong(@NonNull final String key, @NonNull final SpUtils spUtils) {
        return spUtils.getLong(key);
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of {@link SpUtils}.
     * @return the long value if sp exists or {@code defaultValue} otherwise
     */
    private static long getLong(@NonNull final String key, final long defaultValue, @NonNull final SpUtils spUtils) {
        return spUtils.getLong(key, defaultValue);
    }

    /**
     * Put the float value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final String key, final float value, @NonNull final SpUtils spUtils) {
        spUtils.put(key, value);
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final String key,
                           final float value,
                           final boolean isCommit,
                           @NonNull final SpUtils spUtils) {
        spUtils.put(key, value, isCommit);
    }

    /**
     * Return the float value in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SpUtils}.
     * @return the float value if sp exists or {@code -1f} otherwise
     */
    private static float getFloat(@NonNull final String key, @NonNull final SpUtils spUtils) {
        return spUtils.getFloat(key);
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of {@link SpUtils}.
     * @return the float value if sp exists or {@code defaultValue} otherwise
     */
    private static float getFloat(@NonNull final String key, final float defaultValue, @NonNull final SpUtils spUtils) {
        return spUtils.getFloat(key, defaultValue);
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final String key, final boolean value, @NonNull final SpUtils spUtils) {
        spUtils.put(key, value);
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final String key,
                           final boolean value,
                           final boolean isCommit,
                           @NonNull final SpUtils spUtils) {
        spUtils.put(key, value, isCommit);
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SpUtils}.
     * @return the boolean value if sp exists or {@code false} otherwise
     */
    public static boolean getBoolean(@NonNull final String key, @NonNull final SpUtils spUtils) {
        return spUtils.getBoolean(key);
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of {@link SpUtils}.
     * @return the boolean value if sp exists or {@code defaultValue} otherwise
     */
    public static boolean getBoolean(@NonNull final String key,
                                     final boolean defaultValue,
                                     @NonNull final SpUtils spUtils) {
        return spUtils.getBoolean(key, defaultValue);
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final String key, final Set<String> value, @NonNull final SpUtils spUtils) {
        spUtils.put(key, value);
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SpUtils}.
     */
    public static void put(@NonNull final String key,
                           final Set<String> value,
                           final boolean isCommit,
                           @NonNull final SpUtils spUtils) {
        spUtils.put(key, value, isCommit);
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SpUtils}.
     * @return the set of string value if sp exists
     * or {@code Collections.<String>emptySet()} otherwise
     */
    private static Set<String> getStringSet(@NonNull final String key, @NonNull final SpUtils spUtils) {
        return spUtils.getStringSet(key);
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of {@link SpUtils}.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    private static Set<String> getStringSet(@NonNull final String key,
                                            final Set<String> defaultValue,
                                            @NonNull final SpUtils spUtils) {
        return spUtils.getStringSet(key, defaultValue);
    }

    /**
     * Return all values in sp.
     *
     * @param spUtils The instance of {@link SpUtils}.
     * @return all values in sp
     */
    private static Map<String, ?> getAll(@NonNull final SpUtils spUtils) {
        return spUtils.getAll();
    }

    /**
     * Return whether the sp contains the preference.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SpUtils}.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean contains(@NonNull final String key, @NonNull final SpUtils spUtils) {
        return spUtils.contains(key);
    }

    /**
     * Remove the preference in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SpUtils}.
     */
    public static void remove(@NonNull final String key, @NonNull final SpUtils spUtils) {
        spUtils.remove(key);
    }

    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SpUtils}.
     */
    public static void remove(@NonNull final String key, final boolean isCommit, @NonNull final SpUtils spUtils) {
        spUtils.remove(key, isCommit);
    }

    /**
     * Remove all preferences in sp.
     *
     * @param spUtils The instance of {@link SpUtils}.
     */
    public static void clear(@NonNull final SpUtils spUtils) {
        spUtils.clear();
    }

    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SpUtils}.
     */
    public static void clear(final boolean isCommit, @NonNull final SpUtils spUtils) {
        spUtils.clear(isCommit);
    }

    /**
     * 获取简单存储工具类 SpUtils
     *
     * @return SpUtils
     */
    private static SpUtils getDefaultSpUtils() {
        return sDefaultSpUtils != null ? sDefaultSpUtils : SpUtils.getInstance(getApplicationByReflect());
    }

    /**
     * 如果你没有初始化该工具类，那么我会通过反射获取当前的applicationContext()
     *
     * @return Application
     */
    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }
}