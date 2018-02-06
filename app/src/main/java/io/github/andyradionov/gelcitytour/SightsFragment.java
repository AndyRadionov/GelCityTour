package io.github.andyradionov.gelcitytour;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.andyradionov.gelcitytour.data.Sight;
import io.github.andyradionov.gelcitytour.data.SightContract;
import io.github.andyradionov.gelcitytour.data.SightDbHelper;

import static io.github.andyradionov.gelcitytour.MainActivity.MAIN_SIGHT_PROJECTION;

/**
 * A simple {@link Fragment} subclass.
 */
public class SightsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        SightAdapter.OnSightItemClickListener {

    public static final String SIGHT_CATEGORY_KEY = "sight_category_key";
    private static final String CATEGORIES_LIST_KEY = "categories_list_key";

    private static final int ID_SIGHT_LOADER = 42;
    private RecyclerView mSightsList;
    private SightAdapter mSightAdapter;
    private Activity mContext;

    public SightsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sights, container, false);
        mContext = getActivity();

        Bundle args = getArguments();
        String[] categories = mContext.getResources().getStringArray(R.array.categories);
        args.putStringArray(CATEGORIES_LIST_KEY, categories);

        mSightsList = rootView.findViewById(R.id.sights_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false);
        mSightsList.setLayoutManager(layoutManager);

        mSightAdapter = new SightAdapter(getActivity(), this);
        mSightsList.setAdapter(mSightAdapter);

        getLoaderManager().initLoader(ID_SIGHT_LOADER, args, this);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        if (loaderId != ID_SIGHT_LOADER) {
            throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }

        final String sortOrder = SightContract.SightEntry._ID + " ASC";

        int categoryKey = args.getInt(SIGHT_CATEGORY_KEY);
        String[] categories = args.getStringArray(CATEGORIES_LIST_KEY);
        final String currentCategory = categories[categoryKey];
        return new CursorLoader(mContext,
                SightContract.SightEntry.CONTENT_URI,
                MAIN_SIGHT_PROJECTION,
                "category=?",
                new String[]{currentCategory},
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mSightAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mSightAdapter.swapCursor(null);
    }

    @Override
    public void onSightItemClick(Sight sight) {
        Intent startSightDetail = new Intent(mContext, DetailsActivity.class);
        startSightDetail.putExtra(DetailsActivity.EXTRA_SIGHT_NAME, sight.getName());
        startSightDetail.putExtra(DetailsActivity.EXTRA_SIGHT_ADDRESS, sight.getAddress());
        startSightDetail.putExtra(DetailsActivity.EXTRA_SIGHT_DESCRIPTION, sight.getDescription());
        startSightDetail.putExtra(DetailsActivity.EXTRA_SIGHT_IMAGE, sight.getImage());

        startActivity(startSightDetail);
    }
}
