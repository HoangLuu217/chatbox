/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aichat.service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author Admin
 */
public class geminiService {

    private final String API_KEY = "AIzaSyDPkRgaVjVbBrvjbvg1KGHPD6BnEMCuwfU";
    private final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String callGeminiAPI(String context, String userMessage) throws Exception {
        try {
            JSONObject requestBody = new JSONObject();
            JSONArray contents = new JSONArray();
            JSONObject content = new JSONObject();
            JSONArray parts = new JSONArray();

            // Đơn giản hóa prompt để có câu trả lời ngắn gọn
            JSONObject contextPart = new JSONObject();
            contextPart.put("text", "Trả lời ngắn gọn, thân thiện. Chỉ sử dụng thông tin từ dữ liệu sau: "
                    + context + "\n\nKhách hàng: " + userMessage);
            parts.put(contextPart);

            content.put("parts", parts);
            contents.put(content);
            requestBody.put("contents", contents);

            // Create HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + API_KEY))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            // Send request and get response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Log response for debugging
            System.out.println("API Response Status: " + response.statusCode());
            System.out.println("API Response Body: " + response.body());

            // Check HTTP status code
            if (response.statusCode() != 200) {
                throw new Exception("API request failed with status code: " + response.statusCode());
            }

            // Parse response
            JSONObject jsonResponse = new JSONObject(response.body());

            // Check for errors first
            if (jsonResponse.has("error")) {
                JSONObject error = jsonResponse.getJSONObject("error");
                String errorMessage = error.has("message") ? error.getString("message") : "Unknown error";
                String errorCode = error.has("code") ? error.getString("code") : "Unknown code";
                throw new Exception("API Error [" + errorCode + "]: " + errorMessage);
            }

            // Get the response text from the correct path
            if (jsonResponse.has("candidates") && jsonResponse.getJSONArray("candidates").length() > 0) {
                JSONObject candidate = jsonResponse.getJSONArray("candidates").getJSONObject(0);
                if (candidate.has("content")) {
                    JSONObject content2 = candidate.getJSONObject("content");
                    if (content2.has("parts") && content2.getJSONArray("parts").length() > 0) {
                        return content2.getJSONArray("parts").getJSONObject(0).getString("text");
                    }
                }
            }

            // If we can't find the expected structure, try alternative paths
            if (jsonResponse.has("text")) {
                return jsonResponse.getString("text");
            }

            if (jsonResponse.has("response")) {
                return jsonResponse.getString("response");
            }
            return "Xin lỗi, tôi không thể xử lý yêu cầu của bạn lúc này.";

        } catch (Exception e) {
            System.err.println("Error calling Gemini API: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Không thể kết nối đến dịch vụ AI. Vui lòng kiểm tra kết nối và thử lại sau.");
        }
    }

//public String callGeminiAPI(String prompt) throws Exception {
//    HttpClient client = HttpClient.newHttpClient();
//
//    JSONObject requestBody = new JSONObject();
//    JSONArray contents = new JSONArray();
//    JSONObject content = new JSONObject();
//    JSONArray parts = new JSONArray();
//    JSONObject part = new JSONObject();
//
//    part.put("text", prompt);
//    parts.put(part);
//    content.put("parts", parts);
//    contents.put(content);
//    requestBody.put("contents", contents);
//
//    HttpRequest request = HttpRequest.newBuilder()
//            .uri(URI.create(API_URL + API_KEY)) // fix URL
//            .header("Content-Type", "application/json")
//            .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
//            .build();
//
//    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//    if (response.statusCode() != 200) {
//        //throw new RuntimeException("API call failed: " + response.body());
//    }
//
//    JSONObject jsonResponse = new JSONObject(response.body());
//    String answer = jsonResponse.getJSONArray("candidates")
//            .getJSONObject(0)
//            .getJSONObject("content")
//            .getJSONArray("parts")
//            .getJSONObject(0)
//            .getString("text");
//
//    // Optional: Giới hạn từ
//    String[] words = answer.split("\\s+");
//    if (words.length > 1000) {
//        StringBuilder limitedAnswer = new StringBuilder();
//        for (int i = 0; i < 1000; i++) {
//            limitedAnswer.append(words[i]).append(" ");
//        }
//        return limitedAnswer.toString().trim() + "...";
//    }
//
//    return answer;
//}
    
    public String callGeminiAPI(String prompt) {
    int maxRetries = 1;
    int attempts = 0;

    while (attempts <= maxRetries) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            JSONObject requestBody = new JSONObject();
            JSONArray contents = new JSONArray();
            JSONObject content = new JSONObject();
            JSONArray parts = new JSONArray();
            JSONObject part = new JSONObject();

            part.put("text", prompt);
            parts.put(part);
            content.put("parts", parts);
            contents.put(content);
            requestBody.put("contents", contents);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + API_KEY)) // fix URL
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("API call failed with status: " + response.statusCode());
            }

            JSONObject jsonResponse = new JSONObject(response.body());
            String answer = jsonResponse.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");

            // Optional: Giới hạn từ
            String[] words = answer.split("\\s+");
            if (words.length > 100) {
                StringBuilder limitedAnswer = new StringBuilder();
                for (int i = 0; i < 100; i++) {
                    limitedAnswer.append(words[i]).append(" ");
                }
                return limitedAnswer.toString().trim() + "...";
            }

            return answer;

        } catch (Exception ex) {
            System.err.println("Lỗi gọi API lần " + (attempts + 1) + ": " + ex.getMessage());
            attempts++;

            if (attempts > maxRetries) {
                return "Đã xảy ra lỗi khi kết nối tới AI. Vui lòng thử lại sau.";
            }

            try {
                Thread.sleep(1000); // Chờ 1 giây trước khi thử lại
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    return "Không xác định lỗi."; // fallback
}




    public static void main(String[] args) throws Exception {
        geminiService chat = new geminiService();

        String answer = chat.callGeminiAPI("database là gì");
        System.out.println(answer);
    }
}
