package memotyou;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service
public class TaskListDao {
    private final static String TABLE_NAME = "memotyou";
      private final JdbcTemplate jdbcTemplate;

    @Autowired
    TaskListDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int add(HomeController.TaskItem item){
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(TABLE_NAME);
        return insert.execute(param);
    }
    public static void main(String[] args) throws Exception{
        String messages = "Content-Type: application/json";
        //リクエスト用のJsonオブジェクトのリストを作成
//        var messages = new ArrayList<JSONObject>();
        messages.add(new JSONObject().put("role","user").put("content",""));
        messages.add(new JSONObject().put("role", "user").put("content",""));
        getOpenAIResponse(messages);
    }

    curl https://api.openai.com/v1/chat/completions  \
            -H "Content-Type: application/json" \
            -H "Authorization: Bearer ${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}";
            -d '{"model": "gpt-3.5-turbo","messages": [{"role": "system", "content": ""}, {"role": "user", "content": ""}]}'

    private static String getOpenAIResponse(ArrayList<JSONObject> messages) throws Exception {
        String apiKey = "Authorization: Bearer ${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}";
        String model = "gpt-3.5-turbo";

//        try {
            System.out.println(messages);
            String response = generateText(apiKey, model, messages);
            System.out.println(response);
            return response;
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//        return "";
    }

    public static String generateText(String apiKey, String model, ArrayList<JSONObject> messages) throws IOException, JSONException {
        String urlString = "https://api.openai.com/v1/chat/completions";
        URL url = new URL(urlString);
        // HTTPリクエストの作成
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        //JSONArrayでリクエストする。
        JSONArray messagesJsonArray = new JSONArray(messages);
        JSONObject requestBody = new JSONObject()
                .put("model",model)
                .put("messages", messagesJsonArray);

        String jsonInputString = requestBody.toString();
        connection.getOutputStream().write(jsonInputString.getBytes(StandardCharsets.UTF_8));

//        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
//            throw new IOException("HTTP error code: " + connection.getResponseCode());
//        }

        //受け取ったレスポンスを整形する。
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String response = in.lines().reduce((a ,b) -> a + b).get();
            in.close();
            JSONObject responseObject = new JSONObject(response);
            JSONArray choices = responseObject.getJSONArray("choices");
            JSONObject firstChoice = choices.getJSONObject(0);
            String message = firstChoice.getJSONObject("message").get("content").toString();

            return message.replace("\n","");
        }
    }
}