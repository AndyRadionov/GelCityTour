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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static io.github.andyradionov.gelcitytour.SightsFragment.SIGHT_CATEGORY_KEY;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class SightsFragmentPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;
    private String[] tabTitles;

    public SightsFragmentPagerAdapter(String[] tabTitles, FragmentManager fm) {
        super(fm);
        this.tabTitles = tabTitles;
        fragments = new Fragment[tabTitles.length];
        for (int i = 0; i < fragments.length; i++) {
            fragments[i] = createFragment(i);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    private Fragment createFragment(int key) {
        SightsFragment sightsListFragment = new SightsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SIGHT_CATEGORY_KEY, key);
        sightsListFragment.setArguments(bundle);
        return sightsListFragment;
    }
}
