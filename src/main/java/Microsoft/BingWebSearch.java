package Microsoft;

//プロジェクトの作成と依存関係のインポート
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


//BingWebSearch クラスの宣言
public class BingWebSearch {
    // The code in the following sections goes here.

//    変数の定義
// Enter a valid subscription key.
static String subscriptionKey = "\thttps://japaneast.api.cognitive.microsoft.com";

    /*
     * If you encounter unexpected authorization errors, double-check these values
     * against the endpoint for your Bing Web search instance in your Azure
     * dashboard.
     */
    static String host = "https://api.cognitive.microsoft.com";
    static String path = "/bing/v7.0/search";
    static String searchTerm = "Microsoft Cognitive Services";

//    要求の構築
    public SearchResults SearchWeb (String searchQuery) throws Exception {
        // Construct the URL.
        URL url = new URL(host + path + "?q=" +  URLEncoder.encode(searchQuery, "UTF-8"));

        // Open the connection.
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);

        // Receive the JSON response body.
        InputStream stream = connection.getInputStream();
        String response = new Scanner(stream).useDelimiter("\\A").next();

        // Construct the result object.
        SearchResults results = new SearchResults(new HashMap<String, String>(), response);

        // Extract Bing-related HTTP headers.
        Map<String, List<String>> headers = connection.getHeaderFields();
        for (Object header : ((Map<?, ?>) headers).keySet()) {
            if (header == null) continue;      // may have null key
            if (header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")){
                results.relevantHeaders.put(header, headers.get(header).get(0));
            }
        }
        stream.close();
        return results;
    }
    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser() {
            @Override
            public Map<String, Object> parseMap(String json) throws JsonParseException {
                return null;
            }

            @Override
            public java.util.List<Object> parseList(String json) throws JsonParseException {
                return null;
            }
        };
        JsonObject json = parser.parse(json_text).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}






//@Controller
//public class HomeController {
//    public static void main(String[] args){}
//    private final TaskListDao dao;
//    @Autowired
//    HomeController(TaskListDao dao){
//        this.dao = dao;
//    }
//
//    private RestTemplate restTemplate;
//
//    record TaskItem(String id, String AI, String keyword) {}
//    private List<TaskItem> taskItems = new ArrayList<>();
//
//
////    @RequestMapping(value = "/memotyou")
//////    @ResponseBody
////    String home() {
////        return """
////                <html>
////                    <head>
////                    <title>メモ帳</title>
////                    </head>
////                    <body>
////                        <h1>メモ帳</h1>
////                        It works!<br>
////                        現在の時刻は%sです。
////                    </body>
////                </html>
////                """.formatted(LocalDateTime.now());
////    }
////
////
////    @RequestMapping("/time")
////    String home(@org.jetbrains.annotations.NotNull Model model){
////        model.addAttribute("time",LocalDateTime.now());
////        return"home";
////    }
//    @GetMapping("/add")
//    String addItemd(@RequestParam("AI") String AI,
//                    @RequestParam("keyword") String keyword) {
//        String id = UUID.randomUUID().toString().substring(0, 8);
//        TaskItem item = new TaskItem(id, AI, keyword);
//        dao.add(item);
//        return "home";
//    }
//
//    @GetMapping("/restlist")
//    String listItems() {
//        String result = taskItems.stream()
//                .map(TaskItem::toString)
//                .collect(Collectors.joining(","));
//        return result;
//    }
//    @ResponseBody
//    @GetMapping("/test")
//    public String test() throws Exception {
//        //リクエスト用のJsonオブジェクトのリストを作成
//        var messages = new ArrayList<JSONObject>();
//        messages.add(new JSONObject().put("role","user").put("content","明日の天気を教えてください。"));
////        messages.add(new JSONObject().put("role", "user").put("content",""));
//        getBingAIResponse(messages);
//        return "home";
//    }
//
//    private void getBingAIResponse(ArrayList<JSONObject> messages) {
//    }
//
//
//    private static final String apiKey = "f12d7e83be5343c485a238aeb0d4e12d";
//        private static final String bingEndpoint = "https://api.bing.microsoft.com/";
//
//        @GetMapping("/bingRequestDAO")
//        public ResponseEntity<String> bing(@RequestParam("prompt") String prompt) {
//            try {RestTemplate restTemplate = new RestTemplate();
//
//                HttpHeaders headers = new HttpHeaders();
//                headers.set("Authorization", "Bearer " + apiKey);
//                headers.set("Content-Type", "application/json");
//
//                String requestBody = "{\"prompt\": \"" + prompt + "\"}";
//
//                HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
//
//                ResponseEntity<String> response = restTemplate.postForEntity(bingEndpoint, request, String.class);
//
//                return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//        }
//    }








//
//    @GetMapping("/update")
//    String update( @RequestParam("memo") String memo,
//        TaskItem item = new TaskItem(AI, keyword, pagenumber,Createddate, Updatedate);
//        this.dao.update(item);
//        return "redirect:/list";
//}
