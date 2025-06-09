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
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageReaderAi {

    @Value("${spring.ImageReaderAi.url}")
    private String invoke_url ;
    @Value("${spring.ImageReaderAi.apikey}")
    private String api_key ;

    public  String getImageData(String Image, String message) {
        try {
            String content = message + "<img src=\"" + Image + "\" />";

            Map<String, String> message1 = new HashMap<>();
            message1.put("role", "user");
            message1.put("content", content);

            JSONObject payload = new JSONObject();
            payload.put("model", "meta/llama-3.2-11b-vision-instruct");
            payload.put("messages", new JSONArray().put(new JSONObject(message1)));
            payload.put("max_tokens", 512);
            payload.put("temperature", 1.00);
            payload.put("top_p", 1.00);
            payload.put("stream", true);

            HttpURLConnection connection = (HttpURLConnection) new URL(invoke_url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", api_key);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = payload.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Error: Response Code " + responseCode);
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
                    String inputLine;
                    StringBuilder finalContent = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        if (inputLine.trim().startsWith("data: ")) {
                            String jsonPart = inputLine.substring(6).trim(); // remove "data: "
                            if (jsonPart.equals("[DONE]")) break;

                            JSONObject chunk = new JSONObject(jsonPart);
                            JSONArray choices = chunk.getJSONArray("choices");
                            for (int i = 0; i < choices.length(); i++) {
                                JSONObject delta = choices.getJSONObject(i).optJSONObject("delta");
                                if (delta != null && delta.has("content")) {
                                    finalContent.append(delta.getString("content"));
                                }
                            }
                        }
                    }
                    return finalContent.toString();
                }
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }
}
