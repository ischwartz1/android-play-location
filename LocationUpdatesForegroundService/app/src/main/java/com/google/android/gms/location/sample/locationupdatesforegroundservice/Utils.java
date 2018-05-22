/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.location.sample.locationupdatesforegroundservice;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.content.Context;

import java.text.DateFormat;
import java.util.Date;

import static android.content.Context.VIBRATOR_SERVICE;

class Utils {

    static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_locaction_updates";
static boolean called= false;
    /**
     * Returns true if requesting location updates, otherwise returns false.
     *
     * @param context The {@link Context}.
     */
    static boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false);
    }

    /**
     * Stores the location updates state in SharedPreferences.
     * @param requestingLocationUpdates The location updates state.
     */
    static void setRequestingLocationUpdates(Context context, boolean requestingLocationUpdates) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
                .apply();
    }

    /**
     * Returns the {@code location} object as a human readable string.
     * @param location  The {@link Location}.
     */
    static String getLocationText(Location location) {
        Location gate= new Location("");
        //gate.setLatitude(32.07818);
        //gate.setLongitude(34.87329);
        gate.setLatitude(32.0742314);
        gate.setLongitude(34.8768817);
        return location == null ? "Unknown location" :
                "(" + location.getLatitude() + ", " + location.getLongitude() + ")"+location.distanceTo(gate);
    }

    static String getLocationTitle(Context context) {

        return context.getString(R.string.location_updated,
                DateFormat.getDateTimeInstance().format(new Date()));
    }


    static void onLocationChanged(Location location,Context context) {
        long[] pattern = {0, 100, 1000, 300, 200, 100, 500, 200, 100};
        Vibrator vibrator;
         //vibrator = Andriod.os.Vibrator.android.os.Vibrator
        vibrator = (Vibrator)context.getSystemService(VIBRATOR_SERVICE);
        double longtitude=location.getLongitude();
        double latitude=location.getLatitude();

        Location gate= new Location("");

//        gate.setLatitude(32.07818);
//        gate.setLongitude(34.87329);
        gate.setLatitude(32.0742314);
        gate.setLongitude(34.8768817);
//        textView.setText("Longtitude: "+longtitude +"\n"+"Latitude: "+ latitude);
//
//        distance.setText(Float.toString(location.distanceTo(gate) ) );

        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:0549235850"));
        callIntent.setData(Uri.parse("tel:0549434500"));

        if (ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (location.distanceTo(gate) <70  && called ==false    ) {
             context.startActivity(callIntent);
            //distance.setText("called gate" );
            //  vibrator.vibrate(500);
            //VibrationEffect effect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);

            //vibrator.vibrate(effect);
            vibrator.vibrate(pattern, -1);
            called=true;
        }
        if (location.distanceTo(gate) >70  && called ==true   ) {

           // VibrationEffect effect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(pattern, -1);
           // vibrator.vibrate(effect);
            called=false;
        }

    }



}
