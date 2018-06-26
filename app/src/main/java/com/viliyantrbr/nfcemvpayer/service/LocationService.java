package com.viliyantrbr.nfcemvpayer.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import com.viliyantrbr.nfcemvpayer.util.LogUtil;

public class LocationService extends Service {
    private static final String TAG = LocationService.class.getSimpleName();

    private LocationManager mLocationManager = null;

    private LocationListener mLocationListener = null;

    private String mNetworkProvider = null, mGpsProvider = null, mPassiveProvider = null;

    // Service binding
    private final IBinder mBinder = new LocationService.LocalBinder();

    class LocalBinder extends Binder {
        LocationService getService() {
            return LocationService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d(TAG, "\"" + TAG + "\": Service bind");

        return mBinder;
    }
    // - Service binding

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "\"" + TAG + "\": Service create");

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION)) {
            try {
                mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }

            if (mLocationManager != null) {
                // Location provider(s)
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_NETWORK)) {
                    mNetworkProvider = LocationManager.NETWORK_PROVIDER;
                }

                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)) {
                    mGpsProvider = LocationManager.GPS_PROVIDER;
                }

                mPassiveProvider = LocationManager.PASSIVE_PROVIDER;
                // - Location provider(s)

                try {
                    mLocationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            LogUtil.d(TAG, "Location change");

                            if (location != null) {
                                LogUtil.i(TAG, "Location change: \"" + location + "\"");

                                // Location change relative
                                LogUtil.i(TAG, "Location change provider: " + location.getProvider());

                                LogUtil.i(TAG, "Location change latitude: " + location.getLatitude());

                                LogUtil.i(TAG, "Location change longitude: " + location.getLongitude());

                                LogUtil.i(TAG, "Location change time: " + location.getTime());

                                LogUtil.i(TAG, "Location change elapsed realtime nanos: " + location.getElapsedRealtimeNanos());

                                LogUtil.i(TAG, "Location change extras: " + location.getExtras());

                                if (location.hasAccuracy()) {
                                    LogUtil.i(TAG, "Location change has accuracy: " + location.getAccuracy() + " m");
                                }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    if (location.hasVerticalAccuracy()) {
                                        LogUtil.i(TAG, "Location change has vertical accuracy: " + location.getVerticalAccuracyMeters() + " m");
                                    }
                                }

                                if (location.hasAltitude()) {
                                    LogUtil.i(TAG, "Location change has altitude: " + location.getAltitude() + " m");
                                }

                                if (location.hasBearing()) {
                                    LogUtil.i(TAG, "Location change has bearing: " + location.getBearing() + " deg");
                                }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    if (location.hasBearingAccuracy()) {
                                        LogUtil.i(TAG, "Location change has bearing accuracy: " + location.getBearingAccuracyDegrees() + " deg");
                                    }
                                }

                                if (location.hasSpeed()) {
                                    LogUtil.i(TAG, "Location change has speed: " + location.getSpeed() + " m/s");
                                }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    if (location.hasSpeedAccuracy()) {
                                        LogUtil.i(TAG, "Location change has speed accuracy: " + location.getSpeedAccuracyMetersPerSecond() + " m/s");
                                    }
                                }
                                // - Location change relative
                            }
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {
                            LogUtil.d(TAG, "Status change");

                            switch (i) {
                                case LocationProvider.OUT_OF_SERVICE:
                                    if (s != null) {
                                        LogUtil.i(TAG, "\"" + s + "\" provider status change: Out of service");
                                    } else {
                                        LogUtil.i(TAG, "Status change: Out of service");
                                    }

                                    break;
                                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                                    if (s != null) {
                                        LogUtil.i(TAG, "\"" + s + "\" provider status change: Temporarily unavailable");
                                    } else {
                                        LogUtil.i(TAG, "Status change: Temporarily unavailable");
                                    }

                                    break;
                                case LocationProvider.AVAILABLE:
                                    if (s != null) {
                                        LogUtil.i(TAG, "\"" + s + "\" provider status change: Available");
                                    } else {
                                        LogUtil.i(TAG, "Status change: Available");
                                    }

                                    break;
                            }

                            if (s != null && bundle != null) {
                                LogUtil.i(TAG, "Status change: \"" + s + "\"; \"" + bundle + "\"");

                                // Status change relative
                                // - Status change relative
                            }
                        }

                        @Override
                        public void onProviderEnabled(String s) {
                            LogUtil.d(TAG, "Provider enable");

                            if (s != null) {
                                LogUtil.i(TAG, "Provider enable: \"" + s + "\"");

                                // Provider enable relative
                                // - Provider enable relative
                            }
                        }

                        @Override
                        public void onProviderDisabled(String s) {
                            LogUtil.d(TAG, "Provider disable");

                            if (s != null) {
                                LogUtil.i(TAG, "Provider disable: \"" + s + "\"");

                                // Provider disable relative
                                // - Provider disable relative
                            }
                        }
                    };
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onCreate();
        LogUtil.d(TAG, "\"" + TAG + "\": Service destroy");

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION)) {
            if (mLocationManager != null) {
                if (mLocationListener != null) {
                    mLocationListener = null;
                }

                // Location provider(s)
                if (mPassiveProvider != null) {
                    mPassiveProvider = null;
                }

                if (mGpsProvider != null) {
                    mGpsProvider = null;
                }

                if (mNetworkProvider != null) {
                    mNetworkProvider = null;
                }
                // - Location provider(s)

                mLocationManager = null;
            }
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void requestSingleUpdate(@Nullable String provider) {
        if (mLocationManager == null || mLocationListener == null || provider == null) {
            LogUtil.w(TAG, "Cannot request single update with specified provider");

            return;
        }

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION)) {
            LogUtil.w(TAG, "Cannot request single update with specified provider, location feature is not available");

            return;
        }

        if (isProviderCompatible(provider)) {
            LogUtil.d(TAG, "Trying to request single update with specified provider...");
            try {
                mLocationManager.requestSingleUpdate(provider, mLocationListener, null);
            } catch (Exception e) {
                LogUtil.e(TAG, "Exception while trying to request single update with specified provider");

                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        } else {
            LogUtil.w(TAG, "Cannot request single update with specified provider, selected provider is not compatible");
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void requestLocationUpdates(@Nullable String provider, long minTime, float minDistance) {
        if (mLocationManager == null || mLocationListener == null || provider == null) {
            LogUtil.w(TAG, "Cannot request location updates with specified provider, time and distance");

            return;
        }

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION)) {
            LogUtil.w(TAG, "Cannot request location updates with specified provider, time and distance, location feature is not available");

            return;
        }

        if (isProviderCompatible(provider)) {
            LogUtil.d(TAG, "Trying to request location updates with specified provider, time and distance...");
            try {
                mLocationManager.requestLocationUpdates(provider, minTime, minDistance, mLocationListener);
            } catch (Exception e) {
                LogUtil.e(TAG, "Exception while trying to request location updates with specified provider, time and distance");

                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        } else {
            LogUtil.w(TAG, "Cannot request location updates with specified provider, time and distance, selected provider is not compatible");
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void requestSingleUpdate(@Nullable Criteria criteria, boolean enabledOnly) {
        if (mLocationManager == null || mLocationListener == null || criteria == null) {
            LogUtil.w(TAG, "Cannot request single update with criteria best provider");

            return;
        }

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION)) {
            LogUtil.w(TAG, "Cannot request single update with criteria best provider, location feature is not available");

            return;
        }

        String provider = mLocationManager.getBestProvider(criteria, enabledOnly);

        if (provider != null && (isProviderCompatible(provider))) {
            LogUtil.d(TAG, "Trying to request single update with criteria best provider...");
            try {
                mLocationManager.requestSingleUpdate(provider, mLocationListener, null);
            } catch (Exception e) {
                LogUtil.e(TAG, "Exception while trying to request single update with criteria best provider");

                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        } else {
            LogUtil.w(TAG, "Cannot request single update with criteria best provider, selected provider is not compatible");
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void requestLocationUpdates(@Nullable Criteria criteria, boolean enabledOnly, long minTime, float minDistance) {
        if (mLocationManager == null || mLocationListener == null || criteria == null) {
            LogUtil.w(TAG, "Cannot request location updates with criteria best provider, time and distance");

            return;
        }

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION)) {
            LogUtil.w(TAG, "Cannot request location updates with criteria best provider, time and distance, location feature is not available");

            return;
        }

        String provider = mLocationManager.getBestProvider(criteria, enabledOnly);

        if (provider != null && (isProviderCompatible(provider))) {
            LogUtil.d(TAG, "Trying to request location updates with criteria best provider, time and distance...");
            try {
                mLocationManager.requestLocationUpdates(provider, minTime, minDistance, mLocationListener);
            } catch (Exception e) {
                LogUtil.e(TAG, "Exception while trying to request location updates with criteria best provider, time and distance");

                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        } else {
            LogUtil.w(TAG, "Cannot request location updates with criteria best provider, time and distance, selected provider is not compatible");
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public Location getLastKnownLocation(@Nullable String provider) {
        if (mLocationManager == null || provider == null) {
            LogUtil.w(TAG, "Cannot get last known location");

            return null;
        }

        if (!isProviderCompatible(provider)) {
            LogUtil.w(TAG, "Cannot get last known location, selected provider is not compatible");

            return null;
        }

        Location lastKnownLocation = null;

        LogUtil.d(TAG, "Trying to get last known location...");
        try {
            lastKnownLocation = mLocationManager.getLastKnownLocation(provider);
        } catch (Exception e) {
            LogUtil.e(TAG, "Exception while trying to get last known location");

            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        return lastKnownLocation;
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void removeUpdates() {
        if (mLocationManager == null || mLocationListener == null) {
            LogUtil.w(TAG, "Cannot remove updates");

            return;
        }

        LogUtil.d(TAG, "Trying to remove updates...");
        try {
            mLocationManager.removeUpdates(mLocationListener);
        } catch (Exception e) {
            LogUtil.e(TAG, "Exception while trying to remove updates");

            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }
    }

    private boolean isProviderCompatible(@NonNull String provider) {
        // Returning result
        boolean result = false;
        // - Returning result

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION)) {
            if (provider.equals(LocationManager.NETWORK_PROVIDER) && getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_NETWORK)) {
                result = true;
            } else if (provider.equals(LocationManager.GPS_PROVIDER) && getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)) {
                result = true;
            } else if (provider.equals(LocationManager.PASSIVE_PROVIDER)) {
                result = true;
            }
        }

        return result;
    }
}
