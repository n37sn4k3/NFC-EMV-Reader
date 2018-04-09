package com.viliyantrbr.nfcemvpayer.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.viliyantrbr.nfcemvpayer.envr.MainEnvr;

public class LogUtil {
    private static final String TAG = LogUtil.class.getName();

    // Verbose
    public static void v(@Nullable String tag, @NonNull String msg) {
        if (!MainEnvr.LOG_V) {
            return;
        }

        if (tag != null) {
            try {
                Log.v(tag, msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        } else {
            try {
                Log.v(TAG, msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        }
    }

    public static void v(@Nullable String tag, @NonNull String msg, @NonNull Throwable tr) {
        if (!MainEnvr.LOG_V) {
            return;
        }

        if (tag != null) {
            try {
                Log.v(tag, msg, tr);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        } else {
            try {
                Log.v(TAG, msg, tr);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        }
    }
    // - Verbose

    // Debug
    public static void d(@Nullable String tag, @NonNull String msg) {
        if (!MainEnvr.LOG_D) {
            return;
        }

        if (tag != null) {
            try {
                Log.d(tag, msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        } else {
            try {
                Log.d(TAG, msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        }
    }

    public static void d(@Nullable String tag, @NonNull String msg, @NonNull Throwable tr) {
        if (!MainEnvr.LOG_D) {
            return;
        }

        if (tag != null) {
            try {
                Log.d(tag, msg, tr);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        } else {
            try {
                Log.d(TAG, msg, tr);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        }
    }
    // - Debug

    // Info
    public static void i(@Nullable String tag, @NonNull String msg) {
        if (!MainEnvr.LOG_I) {
            return;
        }

        if (tag != null) {
            try {
                Log.i(tag, msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        } else {
            try {
                Log.i(TAG, msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        }
    }

    public static void i(@Nullable String tag, @NonNull String msg, @NonNull Throwable tr) {
        if (!MainEnvr.LOG_I) {
            return;
        }

        if (tag != null) {
            try {
                Log.i(tag, msg, tr);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        } else {
            try {
                Log.i(TAG, msg, tr);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        }
    }
    // - Info

    // Warn
    public static void w(@Nullable String tag, @NonNull String msg) {
        if (!MainEnvr.LOG_W) {
            return;
        }

        if (tag != null) {
            try {
                Log.w(tag, msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        } else {
            try {
                Log.w(TAG, msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        }
    }

    public static void w(@Nullable String tag, @NonNull String msg, @NonNull Throwable tr) {
        if (!MainEnvr.LOG_W) {
            return;
        }

        if (tag != null) {
            try {
                Log.w(tag, msg, tr);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        } else {
            try {
                Log.w(TAG, msg, tr);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        }
    }
    // - Warn

    // Error
    public static void e(@Nullable String tag, @NonNull String msg) {
        if (!MainEnvr.LOG_E) {
            return;
        }

        if (tag != null) {
            try {
                Log.w(tag, msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        } else {
            try {
                Log.w(TAG, msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        }
    }

    public static void e(@Nullable String tag, @NonNull String msg, @NonNull Throwable tr) {
        if (!MainEnvr.LOG_E) {
            return;
        }

        if (tag != null) {
            try {
                Log.e(tag, msg, tr);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        } else {
            try {
                Log.e(TAG, msg, tr);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }
        }
    }
    // - Error

    // What a Terrible Failure
    public static void wtf(@Nullable String tag, @NonNull String msg) {
        if (!MainEnvr.LOG_WTF) {
            return;
        }

        if (tag != null) {
            try {
                Log.wtf(tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Log.wtf(TAG, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void wtf(@Nullable String tag, @NonNull String msg, @NonNull Throwable tr) {
        if (!MainEnvr.LOG_WTF) {
            return;
        }

        if (tag != null) {
            try {
                Log.wtf(tag, msg, tr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Log.wtf(TAG, msg, tr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // - What a Terrible Failure

}
