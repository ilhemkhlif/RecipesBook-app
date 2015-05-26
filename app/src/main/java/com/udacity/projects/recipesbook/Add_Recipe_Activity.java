package com.udacity.projects.recipesbook;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Add_Recipe_Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add);
    }



    public void onClickAddName(View view) {


        ContentValues values = new ContentValues();

        String recipe =  ((EditText)findViewById(R.id.txtTitle)).getText().toString().trim();
        values.put(Recipe_Provider.RECIPE_NAME,recipe);

        String desc =  ((EditText)findViewById(R.id.txtDesc)).getText().toString().trim();
        values.put(Recipe_Provider.DESC,desc);

        String ingredients = ((EditText)findViewById(R.id.txtIngredients)).getText().toString().trim();
        values.put(Recipe_Provider.INGREDIENTS,ingredients);


        values.put(Recipe_Provider.DATE, new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));

        getContentResolver().insert(
                Recipe_Provider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(),
                "Your recipe has been successfully added", Toast.LENGTH_LONG).show();
    }



    }


