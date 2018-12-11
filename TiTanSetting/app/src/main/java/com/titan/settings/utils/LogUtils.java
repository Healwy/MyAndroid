package com.titan.settings.utils;

import android.util.Log;

public class LogUtils {
    private static final String LOG_TAG_API = "Settings_Api";

    private static final String LOG_TAG = "Settings_Tv";

    private static boolean mIsShowLog = true;

    public static void setShowLog(boolean isShowLog) {
        mIsShowLog = isShowLog;
    }

    public static void api() {
        outputLog(Log.INFO, LOG_TAG_API, null, null);
    }

    public static void api(String message) {
        outputLog(Log.INFO, LOG_TAG_API, message, null);
    }

    public static void v() {
        outputLog(Log.VERBOSE, LOG_TAG, null, null);
    }

    public static void v(String message, Object... args) {
        outputLog(Log.VERBOSE, LOG_TAG, maybeFormat(message, args), null);
    }

    public static void v(Throwable throwable, String message, Object... args) {
        outputLog(Log.VERBOSE, LOG_TAG, maybeFormat(message, args), throwable);
    }

    public static void d() {
        outputLog(Log.DEBUG, LOG_TAG, null, null);
    }

    public static void d(String message, Object... args) {
        outputLog(Log.DEBUG, LOG_TAG, maybeFormat(message, args), null);
    }

    public static void d(Throwable throwable, String message, Object... args) {
        outputLog(Log.DEBUG, LOG_TAG, maybeFormat(message, args), throwable);
    }

    public static void i() {
        outputLog(Log.INFO, LOG_TAG, null, null);
    }

    public static void i(String message, Object... args) {
        outputLog(Log.INFO, LOG_TAG, maybeFormat(message, args), null);
    }

    public static void i(Throwable throwable, String message, Object... args) {
        outputLog(Log.INFO, LOG_TAG, maybeFormat(message, args), throwable);
    }

    public static void w() {
        outputLog(Log.WARN, LOG_TAG, null, null);
    }

    public static void w(String message, Object... args) {
        outputLog(Log.WARN, LOG_TAG, maybeFormat(message, args), null);
    }

    public static void w(Throwable throwable, String message, Object... args) {
        outputLog(Log.WARN, LOG_TAG, maybeFormat(message, args), throwable);
    }

    public static void e() {
        outputLog(Log.ERROR, LOG_TAG, null, null);
    }

    public static void e(String message, Object... args) {
        outputLog(Log.ERROR, LOG_TAG, maybeFormat(message, args), null);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        outputLog(Log.ERROR, LOG_TAG, maybeFormat(message, args), throwable);
    }

    private static void outputLog(int type, String tag, String message, Throwable throwable) {
        if (!mIsShowLog) {
            return;
        }

        if (message == null) {
            message = getStackTraceInfo();
        } else {
            message = getStackTraceInfo() + message;
        }

        switch (type) {
            case Log.VERBOSE:
                if (throwable == null) {
                    Log.v(tag, message);
                } else {
                    Log.v(tag, message, throwable);
                }
                break;

            case Log.DEBUG:
                if (throwable == null) {
                    Log.d(tag, message);
                } else {
                    Log.d(tag, message, throwable);
                }
                break;

            case Log.INFO:
                if (throwable == null) {
                    Log.i(tag, message);
                } else {
                    Log.i(tag, message, throwable);
                }
                break;

            case Log.WARN:
                if (throwable == null) {
                    Log.w(tag, message);
                } else {
                    Log.w(tag, message, throwable);
                }
                break;

            case Log.ERROR:
                if (throwable == null) {
                    Log.e(tag, message);
                } else {
                    Log.e(tag, message, throwable);
                }
        }
    }

    static String getStackTraceInfo() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[5];

        String fullName = element.getClassName();
        String className = fullName.substring(fullName.lastIndexOf(".") + 1);
        String methodName = element.getMethodName();
        int lineNumber = element.getLineNumber();

        return "<<" + className + "#" + methodName + ":" + lineNumber + ">> ";
    }

    private static String maybeFormat(String message, Object... args) {
        // If no varargs are supplied, treat it as a request to log the string without formatting.
        return args.length == 0 ? message : String.format(message, args);
    }

}


