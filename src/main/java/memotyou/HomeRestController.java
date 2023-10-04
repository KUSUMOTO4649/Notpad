//package memotyou;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//import java.util.UUID;
//
//public class HomeRestController {
//    public final TaskListDao dao;
//@Autowired
//    HomeRestController(TaskListDao dao) {
//    this. dao = dao;
//}
//
//    @PostMapping("/rest_add")
//    List<HomeRestController.TaskItem>addItem(@RequestParam("task")String task,
//                                             @RequestParam("deadline")String deadLine){
//    String id = UUID.randomUUID().toString().substring(0,8);
//    HomeRestController.TaskItem item = new HomeRestController.TaskItem(id,task,deadLine,"",faler);
//    this.dao.add(item);
//    return this.dao.findAll();
//    }
//}
