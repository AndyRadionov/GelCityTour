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
package io.github.andyradionov.gelcitytour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class DetailsActivity extends AppCompatActivity {

    public static String EXTRA_SIGHT_NAME = "extra_sight_name";
    public static String EXTRA_SIGHT_ADDRESS = "extra_sight_address";
    public static String EXTRA_SIGHT_DESCRIPTION = "extra_sight_desc";
    public static String EXTRA_SIGHT_IMAGE = "extra_sight_image";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setUpViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpViews() {
        ImageView sightImage = findViewById(R.id.iv_details_image);
        TextView sightName = findViewById(R.id.tv_details_name);
        TextView sightAddress = findViewById(R.id.tv_details_address);
        TextView sightDescription = findViewById(R.id.tv_details_description);

        Intent startIntent = getIntent();
        String name = startIntent.getStringExtra(EXTRA_SIGHT_NAME);
        String address = startIntent.getStringExtra(EXTRA_SIGHT_ADDRESS);
        String description = startIntent.getStringExtra(EXTRA_SIGHT_DESCRIPTION);
        description = description.replace("\\n", System.getProperty("line.separator"));
        String image = startIntent.getStringExtra(EXTRA_SIGHT_IMAGE);
        int imageId = getResources().getIdentifier(image, "drawable", getPackageName());

        sightImage.setImageResource(imageId);
        sightName.setText(name);
        sightAddress.setText(address);
        sightDescription.setText(description);
    }
}
