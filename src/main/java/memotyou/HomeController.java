package memotyou;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
//@RestController
@Controller
public class HomeController {
    private final TaskListDao dao;

    @Autowired
    HomeController(TaskListDao dao){
        this.dao = dao;
    }

    private RestTemplate restTemplate;

    record TaskItem(String id, String AI, String keyword) {}
    private List<TaskItem> taskItems = new ArrayList<>();


    @RequestMapping(value = "/memotyou")
    @ResponseBody
    String hello() {
        return """
                <html>
                    <head>
                    <title>メモ帳</title>
                    </head>
                    <body>
                        <h1>メモ帳</h1>
                        It works!<br>
                        現在の時刻は%sです。
                    </body>
                </html>
                """.formatted(LocalDateTime.now());
    }

    @GetMapping("/add")
    String addItemd(@RequestParam("AI") String AI,
                    @RequestParam("keyword") String keyword) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        TaskItem item = new TaskItem(id, AI, keyword);
        dao.add(item);
        return "home";
    }

    @GetMapping("/restlist")
    String listItems() {
        String result = taskItems.stream()
                .map(TaskItem::toString)
                .collect(Collectors.joining(","));
        return result;
    }

    @GetMapping("/test")
    public String test() {
        return "home";
    }


        private static final String apiKey = "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";
        private static final String chatGptEndpoint = "https://api.openai.com/v1/engines/davinci-codex/completions";

        @GetMapping("/ChatGptRequestDAO")
        public ResponseEntity<String> chatGpt(@RequestParam("prompt") String prompt) {
            try {RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + apiKey);
                headers.set("Content-Type", "application/json");

                String requestBody = "{\"prompt\": \"" + prompt + "\"}";

                HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

                ResponseEntity<String> response = restTemplate.postForEntity(chatGptEndpoint, request, String.class);

                return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }








//
//    @GetMapping("/update")
//    String update( @RequestParam("memo") String memo,
//        TaskItem item = new TaskItem(AI, keyword, pagenumber,Createddate, Updatedate);
//        this.dao.update(item);
//        return "redirect:/list";
//}
