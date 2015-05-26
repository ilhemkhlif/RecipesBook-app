package com.udacity.projects.recipesbook;



import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class Recipe_Provider extends ContentProvider {

    static final String AUTHORITY = "com.udacity.project.recipesbook";

    static final String URI = "content://" + AUTHORITY + "/Items";

    static final Uri CONTENT_URI = Uri.parse(URI);

    static final String ITEM_ID = "_id";
    static final String RECIPE_NAME = "_name";
    static final String DESC = "desc";
    static final String INGREDIENTS = "ingredients";
    static final String DATE = "dt";

    static final int IT = 1;
    static final int IT_ID = 2;


    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "Items", IT);
        uriMatcher.addURI(AUTHORITY, "Items/#", IT_ID);
    }

    static SQLiteDatabase db;
    static final String DATABASE_NAME = "MyDataBase";
    static final String RECIPES_TABLE_NAME = "MyRecipesBook";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + RECIPES_TABLE_NAME +
                    " ("+ITEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    " "+RECIPE_NAME+" TEXT NOT NULL, " +
                    " "+DESC+" TEXT NOT NULL, " +
                    " "+INGREDIENTS+" TEXT NOT NULL, " +
                    " "+DATE+" DATE NOT NULL);";


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + RECIPES_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db != null);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long rowID = db.insert(RECIPES_TABLE_NAME, "", values);

        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(RECIPES_TABLE_NAME);

        Cursor c = qb.query(db, projection, selection, selectionArgs,
                null, null, null);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;

        switch (uriMatcher.match(uri)) {
            case IT:
                count = db.delete(RECIPES_TABLE_NAME, selection, selectionArgs);
                break;
            case IT_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(RECIPES_TABLE_NAME, ITEM_ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count;

        switch (uriMatcher.match(uri)) {
            case IT:
                count = db.update(RECIPES_TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case IT_ID:
                count = db.update(RECIPES_TABLE_NAME, values, ITEM_ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


}