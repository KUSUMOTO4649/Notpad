package memotyou;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final TaskListDao dao;

    @Autowired
    HomeController(TaskListDao dao){
        this.dao = dao;
    }

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
}
//
//    @GetMapping("/update")
//    String update( @RequestParam("memo") String memo,
//        TaskItem item = new TaskItem(AI, keyword, pagenumber,Createddate, Updatedate);
//        this.dao.update(item);
//        return "redirect:/list";
//}
