package memotyou;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.search.domains.Member;
import com.example.search.mappers.MemberMapper;
@Service
public class TaskListDao {
    private final static String TABLE_NAME = "memotyou";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    TaskListDao(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    public int add(HomeController.TaskItem item){
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(TABLE_NAME);
        return insert.execute(param);
    }


    @Controller
    public class ChatGptRequestDAO {

        private final ChatGptRequestDAO chatgptRequestdao;

        @Autowired
        public ChatGptRequestDAO(TaskListDao taskListdao, ChatGptRequestDAO chatgptRequestdao) {
            this.chatgptRequestdao = chatgptRequestdao;
            this.memberMapper = memberMapper;
        }

        public TaskListDao(TaskListDao taskListdao) {
            this.taskListdao = taskListdao;
        }

        public ChatGptRequestDAO(ChatGptRequestDAO chatgptRequestdao) {
            this.chatgptRequestdao = chatgptRequestdao;
        }

        @GetMapping("/api/members")
        @ResponseBody
        public List<Member> all() {
            List<Member> members = memberMapper.all();
            return members;
        }

        @GetMapping("/api/members/{words}")
        @ResponseBody
        public List<Member> find(@PathVariable String words) {
            List<Member> members = memberMapper.findByNameLike(words);
            return members;
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException, JSONException {

        //リクエスト用のJsonオブジェクトのリストを作成
        var messages = new ArrayList<JSONObject>();
        messages.add(new JSONObject().put("role", "system").put("content", "筋肉トレーニング"));
        messages.add(new JSONObject().put("role", "user").put("content", "ブログタイトルを考えてください"));
        getOpenAIResponse(messages);
    }

    private static String getOpenAIResponse(ArrayList<JSONObject> messages) throws IOException {
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {
            System.out.println(messages);
            String response = generateText(apiKey, model, messages);
            System.out.println(response);
            return response;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return "";
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

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + connection.getResponseCode());
        }

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