package io.github.andyradionov.gelcitytour.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import static io.github.andyradionov.gelcitytour.data.SightContract.CONTENT_AUTHORITY;
import static io.github.andyradionov.gelcitytour.data.SightContract.PATH_SIGHTS;
import static io.github.andyradionov.gelcitytour.data.SightContract.SightEntry;

/**
 * @author Andrey Radionov
 */

public class SightProvider extends ContentProvider {

    private static final String TAG = SightProvider.class.getSimpleName();

    private static final int SIGHTS = 100;
    private static final int SIGHT_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_SIGHTS, SIGHTS);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_SIGHTS + "/#", SIGHT_ID);
    }

    private SightDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new SightDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case SIGHTS:
                cursor = database.query(SightEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case SIGHT_ID:
                selection = SightEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(SightEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SIGHTS:
                return SightEntry.CONTENT_LIST_TYPE;
            case SIGHT_ID:
                return SightEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SIGHTS:
                return insertSight(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertSight(Uri uri, ContentValues values) {
        String category = values.getAsString(SightEntry.COLUMN_SIGHT_CATEGORY);
        if (TextUtils.isEmpty(category)) {
            throw new IllegalArgumentException("Sight requires a category");
        }

        String name = values.getAsString(SightEntry.COLUMN_SIGHT_NAME);
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Sight requires a name");
        }

        String address = values.getAsString(SightEntry.COLUMN_SIGHT_ADDRESS);
        if (TextUtils.isEmpty(address)) {
            throw new IllegalArgumentException("Sight requires a address");
        }

        String description = values.getAsString(SightEntry.COLUMN_SIGHT_DESCRIPTION);
        if (TextUtils.isEmpty(description)) {
            throw new IllegalArgumentException("Sight requires a description");
        }

        String image = values.getAsString(SightEntry.COLUMN_SIGHT_IMAGE);
        if (TextUtils.isEmpty(image)) {
            throw new IllegalArgumentException("Sight requires a image");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(SightEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SIGHTS:
                return database.delete(SightEntry.TABLE_NAME, selection, selectionArgs);
            case SIGHT_ID:
                selection = SightEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(SightEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SIGHTS:
                return updateSight(uri, values, selection, selectionArgs);
            case SIGHT_ID:
                selection = SightEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateSight(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateSight(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(SightEntry.COLUMN_SIGHT_CATEGORY)) {
            String category = values.getAsString(SightEntry.COLUMN_SIGHT_CATEGORY);
            if (TextUtils.isEmpty(category)) {
                throw new IllegalArgumentException("Sight requires a category");
            }
        }

        if (values.containsKey(SightEntry.COLUMN_SIGHT_NAME)) {
            String name = values.getAsString(SightEntry.COLUMN_SIGHT_NAME);
            if (TextUtils.isEmpty(name)) {
                throw new IllegalArgumentException("Sight requires a name");
            }
        }

        if (values.containsKey(SightEntry.COLUMN_SIGHT_ADDRESS)) {
            String address = values.getAsString(SightEntry.COLUMN_SIGHT_ADDRESS);
            if (TextUtils.isEmpty(address)) {
                throw new IllegalArgumentException("Sight requires a address");
            }
        }

        if (values.containsKey(SightEntry.COLUMN_SIGHT_DESCRIPTION)) {
            String description = values.getAsString(SightEntry.COLUMN_SIGHT_DESCRIPTION);
            if (TextUtils.isEmpty(description)) {
                throw new IllegalArgumentException("Sight requires a description");
            }
        }

        if (values.containsKey(SightEntry.COLUMN_SIGHT_IMAGE)) {
            String image = values.getAsString(SightEntry.COLUMN_SIGHT_IMAGE);
            if (TextUtils.isEmpty(image)) {
                throw new IllegalArgumentException("Sight requires a image");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        return database.update(SightEntry.TABLE_NAME, values, selection, selectionArgs);
    }
}
