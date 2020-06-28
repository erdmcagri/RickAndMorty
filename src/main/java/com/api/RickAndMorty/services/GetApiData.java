package com.api.RickAndMorty.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;

/**
 * JavaDoc
 *
 * @author erdmcagri
 * @version 1.0.0
 * @since 27/06/2020
 */
@Service
public class GetApiData {

    static HashMap<String, String> firstApi = new HashMap<String, String>();
    private RestTemplate restTemplate = null;

    static HashMap<String, JSONObject> characterInfo = new HashMap<>();
    static HashMap<String, JSONArray> characterResults = new HashMap<>();
    static JSONObject character = new JSONObject();
    static JSONObject locations = new JSONObject();
    static JSONObject episodes = new JSONObject();

    @PostConstruct
    private void postConstruct() {
        getApiDataFromLink();
    }

    private void getApiDataFromLink() {
        final String uri = "https://rickandmortyapi.com/api/";
        restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(uri, String.class);
        JSONObject result = new JSONObject(res);
        Iterator<String> keys = result.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            firstApi.put(key, result.get(key).toString());
        }
        getAllDataFromURL();
    }

    /**
     *
     *
     * @return print characters
     */
    public static String printCharacter() {
        return character.toString();
    }

    /**
     *
     *
     * @return print locations
     */
    public static String printLocations() {
        return locations.toString();
    }

    /**
     *
     *
     * @return print episodes
     */
    public static String printEpisodes() {
        return episodes.toString();
    }

    private void getAllDataFromURL() {
        firstApi.entrySet().forEach(entry -> {
            String e = entry.getKey();
            if (e.toLowerCase().equals("characters")) {
                getAllData(entry.getValue(), character);
            } else if (e.toLowerCase().equals("locations")) {
                getAllData(entry.getValue(), locations);
            } else if (e.toLowerCase().equals("episodes")) {
                getAllData(entry.getValue(), episodes);
            } else {
                //handle exception
            }
        });
    }

    /**
     * get all data from given links
     *
     * @param uri        - data endpoints
     * @param jsonObject - holds data
     *
     */
    private void getAllData(String uri, JSONObject jsonObject) {
        restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(uri, String.class);
        JSONObject result = new JSONObject(res);
        JSONObject a = (JSONObject) result.get("info");
        int pageNumber = (int) a.get("pages");

        for (int i = 1; i <= pageNumber; i++) {

            if (i == 1) {
                JSONArray ja = (JSONArray) result.get("results");

                for (int j = 0; j < ja.length(); j++) {
                    JSONObject jo = (JSONObject) ja.get(j);
                    String id = jo.get("id").toString();
                    jsonObject.put(id, jo);
                }
            } else {
                JSONArray ja = (JSONArray) getData(i, uri);
                for (int j = 0; j < ja.length(); j++) {
                    JSONObject jo = (JSONObject) ja.get(j);
                    String id = jo.get("id").toString();
                    jsonObject.put(id, jo);
                }

            }
        }
    }

    /**
     * gets data from pages
     *
     * @param uri  - data endpoints
     * @param page - page number
     * @return JSONArray - all jsonobject data puts jsonarray
     */
    public JSONArray getData(int page, String uri) {
        JSONArray ja = null;
        restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(uri + "?page=" + page, String.class);
        JSONObject result = new JSONObject(res);
        Iterator<String> keys = result.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();

            if (key.toLowerCase().equals("results")) {
                ja = (JSONArray) result.get(key);
            }
        }
        return ja;
    }

}