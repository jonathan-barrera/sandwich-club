package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // Declare and initialize strings for JSON keys
    private static final String JSON_NAME = "name";
    private static final String JSON_MAIN_NAME = "mainName";
    private static final String JSON_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String JSON_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_IMAGE = "image";
    private static final String JSON_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String jsonString) throws JSONException {
        if (jsonString != null && !jsonString.equals("")) {
            // Create JSON Object from the jsonString
            JSONObject sandwichJson = new JSONObject(jsonString);

            // Extract the details of the Sandwich from the JSONObject
            JSONObject name = sandwichJson.getJSONObject(JSON_NAME);

            // Extract main name
            String mainName;
            try {
                mainName = name.getString(JSON_MAIN_NAME);
            } catch (JSONException e) {
                // If there is no main name, there is no sandwich entry -> return early
                return null;
            }

            // Extract 'Also Known As' strings list
            List<String> alsoKnownAsListStrings = new ArrayList<>();
            try {
                JSONArray alsoKnownAsJsonArray = name.getJSONArray(JSON_ALSO_KNOWN_AS);
                for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                    alsoKnownAsListStrings.add(alsoKnownAsJsonArray.getString(i));
                }
            } catch (JSONException e) {
                alsoKnownAsListStrings = null;
            }

            // Extract the place of origin string
            String placeOfOrigin;
            try {
                placeOfOrigin = sandwichJson.getString(JSON_PLACE_OF_ORIGIN);
            } catch (JSONException e) {
                placeOfOrigin = null;
            }

            // Extract the sandwich description string
            String description;
            try {
                description = sandwichJson.getString(JSON_DESCRIPTION);
            } catch (JSONException e) {
                description = null;
            }

            // Extract the image resource link
            String imageLink;
            try {
                imageLink = sandwichJson.getString(JSON_IMAGE);
            } catch (JSONException e) {
                imageLink = null;
            }

            // Extract ingredients strings list
            List<String> ingredientsListStrings = new ArrayList<>();
            try {
                JSONArray ingredientsJsonArray = sandwichJson.getJSONArray(JSON_INGREDIENTS);
                for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                    ingredientsListStrings.add(ingredientsJsonArray.getString(i));
                }
            } catch (JSONException e) {
                ingredientsListStrings = null;
            }

            // Return the Sandwich object
            Sandwich sandwich = new Sandwich(mainName, alsoKnownAsListStrings, placeOfOrigin, description,
                    imageLink, ingredientsListStrings);

            return sandwich;
        } else {
            return null;
        }
    }
}
