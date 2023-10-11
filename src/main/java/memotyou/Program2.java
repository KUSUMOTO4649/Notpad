package memotyou;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
//import org.json.JSONObject;
//
//public class Program2 {
//    public static void main(String[] args) throws IOException, JSONException {
//        String url = "https://api.openai.com/v1/completions";
//        String apiKey = "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"; //APIKeyを入力する。
//        String prompt = "消費税のブログタイトル考えてください。";
//
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type", "application/json");
//        con.setRequestProperty("Authorization", "Bearer " + apiKey);
//
//        String postData = "{\"prompt\":\"" + prompt + "\",\"model\":\"text-davinci-003\",\"max_tokens\":60,\"temperature\":0.7,\"echo\":false}";
//        con.setDoOutput(true);
//        con.getOutputStream().write(postData.getBytes("UTF-8"));
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        String response = in.lines().reduce((a ,b) -> a + b).get();
//        in.close();
//        JSONObject responseObject = new JSONObject(response);
//        JSONArray choices = responseObject.getJSONArray("choices");
//        JSONObject firstChoice = choices.getJSONObject(0);
//        String text = firstChoice.getString("text").replace("\n", "");
//        System.out.println(text);
//    }
//}
//
