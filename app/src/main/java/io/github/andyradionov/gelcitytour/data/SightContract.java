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

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class SightContract {

    private SightContract() {
    }

    public static final String CONTENT_AUTHORITY = "io.github.andyradionov.gelcitytour";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_SIGHTS = "sights";

    public static final class SightEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SIGHTS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SIGHTS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SIGHTS;

        public final static String TABLE_NAME = "sights";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_SIGHT_CATEGORY = "category";

        public final static String COLUMN_SIGHT_NAME = "name";

        public final static String COLUMN_SIGHT_ADDRESS = "address";

        public final static String COLUMN_SIGHT_DESCRIPTION = "description";

        public final static String COLUMN_SIGHT_IMAGE = "image";
    }

}

