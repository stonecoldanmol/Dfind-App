package com.example.dfind;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser
{
    private HashMap<String,String> getSingleNearbyPlace(JSONObject googlePlaceJSON)
    {

        HashMap<String,String> googlePlaceMap=new HashMap<>();
        String NameOfPlace="-NA-";
        String vicinity="-NA-";
        String latitude="";
        String longitude="";
        String reference="";


        try
        {
            if (!googlePlaceJSON.isNull("name"))
            {
                NameOfPlace=googlePlaceJSON.getString("name");
            }
            if (!googlePlaceJSON.isNull("vicinity"))
            {
                vicinity=googlePlaceJSON.getString("vicinity");
            }

            latitude =googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude =googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference=googlePlaceJSON.getString("reference");


            googlePlaceMap.put("place_name",NameOfPlace);
            googlePlaceMap.put("vicinity",vicinity);
            googlePlaceMap.put("lat",latitude);
            googlePlaceMap.put("lng",longitude);
            googlePlaceMap.put("reference",reference);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return googlePlaceMap;
    }

    private List<HashMap<String,String>> getAllNearbyPlaces(JSONArray jsonArray)
    {
        int counter =jsonArray.length();

        List<HashMap<String,String>> NearByPlacesList=new ArrayList<>();

        HashMap<String,String> NearbyPlaceMap=null;

        for (int i=0;i<counter;i++)
        {
            try
            {
                NearbyPlaceMap=getSingleNearbyPlace((JSONObject)jsonArray.get(i));
                NearByPlacesList.add(NearbyPlaceMap);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

    return NearByPlacesList;
    }

    public List<HashMap<String,String>> parse(String JSONdata)
    {
        JSONArray jsonArray=null;
        JSONObject jsonObject;


        try {
            jsonObject=new JSONObject(JSONdata);
            jsonArray=jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAllNearbyPlaces(jsonArray);
    }










}
