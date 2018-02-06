package io.github.andyradionov.gelcitytour;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class CategoriesActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY_ID = "extra_category_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        ViewPager viewPager = findViewById(R.id.viewpager);

        String[] sights = getResources().getStringArray(R.array.categories);
        SightsFragmentPagerAdapter adapter = new SightsFragmentPagerAdapter(sights,
                getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent startIntent = getIntent();
        int categoryId = startIntent.getIntExtra(EXTRA_CATEGORY_ID, 0);
        TabLayout.Tab tab = tabLayout.getTabAt(categoryId);
        tab.select();
    }
}
