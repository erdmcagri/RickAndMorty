package com.api.RickAndMorty.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;

@Service
public class CharacterService {

    @Autowired(required = true)
    GetApiData getApiData;

    private RestTemplate restTemplate = null;

    public String getAllData() {
        return getApiData.printCharacter();
    }

    public String findById(String id) {
        JSONObject jo = null;
        Iterator<String> keys = getApiData.character.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();

            if (key.toLowerCase().equals(id)) {
                jo = (JSONObject) getApiData.character.get(key);
                break;
            }
        }
        return jo.toString();
    }


    public String getDataWithPageable(int page) {
        final String uri = "https://rickandmortyapi.com/api/character";
        JSONArray ja = null;
        restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(uri + "?page=" + page, String.class);
        JSONObject result = new JSONObject(res);
        Iterator<String> keys = result.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if (key.toLowerCase().equals("results")) {
                ja = (JSONArray) result.get(key);
                break;
            }
        }

        JSONObject jsonObject = new JSONObject();;
        for (int j = 0; j < ja.length(); j++) {
            JSONObject jo = (JSONObject) ja.get(j);
            String id = jo.get("id").toString();
            jsonObject.put(id, jo);
        }

        if (jsonObject == null || jsonObject.isEmpty()) {
            return null;
        } else {
            return jsonObject.toString();
        }
    }

}
