package com.udacity.projects.recipesbook;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class Home_Activity extends Fragment implements LoaderCallbacks<Cursor> {

    private ListView myList;
    private SimpleCursorAdapter adapt;
    private View rootView;
    private String[] projection;
    private String[] uiBindFrom;
    private int[] uiBindTo;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_home, container, false);

        projection = new String[] {Recipe_Provider.ITEM_ID, Recipe_Provider.RECIPE_NAME, Recipe_Provider.DESC,
                Recipe_Provider.INGREDIENTS, Recipe_Provider.DATE};

        uiBindFrom = new String[]{Recipe_Provider.RECIPE_NAME, Recipe_Provider.DESC, Recipe_Provider.INGREDIENTS, Recipe_Provider.DATE};

        uiBindTo = new int[] {R.id.textViewTitle, R.id.textViewDesc, R.id.textViewIngredients, R.id.textViewDate};

        myList = (ListView) rootView.findViewById(R.id.listView);

        return rootView;
    }

    @Override
    public void onStart() {

        super.onStart();

        Cursor cursor = getActivity().getContentResolver().query(Recipe_Provider.CONTENT_URI, projection,
                null, null, null);

        SimpleCursorAdapter adap = new SimpleCursorAdapter(
                getActivity(), R.layout.list_recipes,
                cursor, uiBindFrom, uiBindTo,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        myList.setAdapter(adap);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(getActivity(),
                Recipe_Provider.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


        adapt.swapCursor(data);

    }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        adapt.swapCursor(null);

    }



}

