package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Find the TextViews to populate the information with
        TextView placeOfOriginTV = findViewById(R.id.origin_tv);
        TextView alsoKnownAsTV = findViewById(R.id.also_known_tv);
        TextView ingredientsTV = findViewById(R.id.ingredients_tv);
        TextView descriptionTV = findViewById(R.id.description_tv);

        // Extract the information from the Sandwich object
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        String description = sandwich.getDescription();

        // Extract the ingredients from the list of Strings and put into one string
        List<String> ingredientsList = sandwich.getIngredients();
        StringBuilder ingredientsBuilder = new StringBuilder();
        if (ingredientsList != null && ingredientsList.size() > 0) {
            for (int i = 0; i < ingredientsList.size() - 1; i ++) {
                String ingredient = ingredientsList.get(i);
                ingredientsBuilder.append(ingredient + "\n");
            }
            ingredientsBuilder.append(ingredientsList.get(ingredientsList.size()-1));
        }
        String ingredients = ingredientsBuilder.toString();

        // Extract the list of 'also known as' names and put into one string
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        StringBuilder alsoKnownAsBuilder = new StringBuilder();
        if (alsoKnownAsList != null && alsoKnownAsList.size() > 0) {
            for (int i = 0; i < alsoKnownAsList.size() -1; i++) {
                String alsoKnownAs = alsoKnownAsList.get(i);
                alsoKnownAsBuilder.append(alsoKnownAs + "\n");
            }
            alsoKnownAsBuilder.append(alsoKnownAsList.get(alsoKnownAsList.size()-1));
        }
        String alsoKnownAs = alsoKnownAsBuilder.toString();


        // Populate the views with the sandwich information
        placeOfOriginTV.setText(placeOfOrigin);
        descriptionTV.setText(description);
        alsoKnownAsTV.setText(alsoKnownAs);
        ingredientsTV.setText(ingredients);
    }
}
