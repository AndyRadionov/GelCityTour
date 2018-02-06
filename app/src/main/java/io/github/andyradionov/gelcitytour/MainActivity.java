package io.github.andyradionov.gelcitytour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static io.github.andyradionov.gelcitytour.data.SightContract.SightEntry;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final String TAG = MainActivity.class.getSimpleName();


    public static final String[] MAIN_SIGHT_PROJECTION = {
            SightEntry._ID,
            SightEntry.COLUMN_SIGHT_CATEGORY,
            SightEntry.COLUMN_SIGHT_NAME,
            SightEntry.COLUMN_SIGHT_ADDRESS,
            SightEntry.COLUMN_SIGHT_DESCRIPTION,
            SightEntry.COLUMN_SIGHT_IMAGE
    };

    public static final int INDEX_SIGHT_ID = 0;
    public static final int INDEX_SIGHT_CATEGORY = 1;
    public static final int INDEX_SIGHT_NAME = 2;
    public static final int INDEX_SIGHT_ADDRESS = 3;
    public static final int INDEX_SIGHT_DESCRIPTION = 4;
    public static final int INDEX_SIGHT_IMAGE = 5;

    private ListView mCategoriesDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCategoriesDisplay = findViewById(R.id.lv_categories);

        String[] categories = getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> categoriesAdapter =
                new ArrayAdapter<>(this, R.layout.item_category_name, categories);

        mCategoriesDisplay.setAdapter(categoriesAdapter);

        mCategoriesDisplay.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent startCategories = new Intent(this, CategoriesActivity.class);
        startCategories.putExtra(CategoriesActivity.EXTRA_CATEGORY_ID, position);
        startActivity(startCategories);
    }
}
