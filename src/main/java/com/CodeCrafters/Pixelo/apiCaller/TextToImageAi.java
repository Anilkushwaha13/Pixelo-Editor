package com.CodeCrafters.Pixelo.apiCaller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class TextToImageAi {

    @Value("${spring.textToImageAi.url}")
    private String invoke_url ;
    @Value("${spring.textToImageAi.apikey}")
    private String api_key ;

    public  List<String> getImage2(String prompt) {
        try {
            List<String> imgdata = new ArrayList<>(1);

            Random rand = new Random();

            Map<String, Object> content = new HashMap<>();
            content.put("text", prompt);
            content.put("weight", 1.0);  // send as a double

            Map<String, Object> content2 = new HashMap<>();
            content2.put("text", "");
            content2.put("weight", -1.0);  // send as a double

            JSONObject playload = new JSONObject();
            playload.put("text_prompts", new JSONArray().put(new JSONObject(content)).put(new JSONObject(content2)));
            playload.put("seed", rand.nextInt(10000));
            playload.put("sampler", "K_DPM_2_ANCESTRAL");
            playload.put("cfg_scale", 5);
            playload.put("steps", 50);

            HttpURLConnection connection = (HttpURLConnection) new URL(invoke_url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", api_key);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json"); // Add content type
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = playload.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int resposeCode = connection.getResponseCode();
            if (resposeCode != 200) {
                System.out.println("Error: Response Code" + resposeCode);

                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String errorLine;
                    StringBuilder errorResponse = new StringBuilder();
                    while ((errorLine = errorReader.readLine()) != null) {
                        errorResponse.append(errorLine);
                    }
                    System.out.println("Error response: " + errorResponse);
                }
                return null;
            } else {

                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    System.out.println(response);
                    JSONObject data = new JSONObject(response.toString());
                    if (data.has("artifacts")) {
                        JSONArray artifacts = data.getJSONArray("artifacts");
                        for (int idx = 0; idx < artifacts.length(); idx++) {
                            JSONObject imageSDATA = artifacts.getJSONObject(idx);
                            imgdata.add(imageSDATA.getString("base64"));
                        }
                    } else {
                        System.out.println("Error:No artifats was found ");
                    }
                }
                return imgdata;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
