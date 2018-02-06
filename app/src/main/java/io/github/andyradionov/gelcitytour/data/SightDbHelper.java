/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.andyradionov.gelcitytour.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import static io.github.andyradionov.gelcitytour.data.SightContract.SightEntry;


/**
 * Database helper for Inventory app. Manages database creation and version management.
 */
public class SightDbHelper extends SQLiteOpenHelper {

    public static final String TAG = SightDbHelper.class.getSimpleName();

    private Context mContext;
    private static final String DATABASE_NAME = "gelcitytour.db";

    private static final int DATABASE_VERSION = 1;

    public SightDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_SIGHTS_TABLE = "CREATE TABLE " + SightEntry.TABLE_NAME + " ("
                + SightEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SightEntry.COLUMN_SIGHT_CATEGORY + " TEXT NOT NULL, "
                + SightEntry.COLUMN_SIGHT_NAME + " TEXT NOT NULL, "
                + SightEntry.COLUMN_SIGHT_ADDRESS + " TEXT NOT NULL, "
                + SightEntry.COLUMN_SIGHT_DESCRIPTION + " TEXT NOT NULL, "
                + SightEntry.COLUMN_SIGHT_IMAGE + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_SIGHTS_TABLE);

        populateDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }

    private void populateDB(SQLiteDatabase db) {
        Sight[] sights = getSightsFromJson();
        if (sights == null) {
            return;
        }

        String SQL_POPULATE_SIGHTS_TABLE = "INSERT INTO " + SightEntry.TABLE_NAME + " ('"
                + SightEntry.COLUMN_SIGHT_CATEGORY + "', '"
                + SightEntry.COLUMN_SIGHT_NAME + "', '"
                + SightEntry.COLUMN_SIGHT_ADDRESS + "', '"
                + SightEntry.COLUMN_SIGHT_DESCRIPTION + "', '"
                + SightEntry.COLUMN_SIGHT_IMAGE + "') VALUES ";

        StringBuilder queryBuilder = new StringBuilder(SQL_POPULATE_SIGHTS_TABLE);
        for (int i = 0; i < sights.length; i++) {
            queryBuilder
                    .append("('")
                    .append(sights[i].getCategory())
                    .append("', '")
                    .append(sights[i].getName())
                    .append("', '")
                    .append(sights[i].getAddress())
                    .append("', '")
                    .append(sights[i].getDescription())
                    .append("', '")
                    .append(sights[i].getImage());
            if (i < sights.length - 1) {
                queryBuilder.append("'), ");
            } else {
                queryBuilder.append("');");
            }
        }

        db.execSQL(queryBuilder.toString());
    }

    private Sight[] getSightsFromJson() {
        try {
            InputStream stream = mContext.getAssets().open("sights.json");

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            String sightsJson = new String(buffer);
            return new Gson().fromJson(sightsJson, Sight[].class);

        } catch (IOException e) {
            Log.d(TAG, "getSightsFromJson");
            return null;
        }
    }
}