package com.kubik.controller;

import com.kubik.entity.Task;
import com.kubik.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;

    @GetMapping("/")
    public String tasks(Model model,
                            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit){

        List<Task> tasks = service.getAll((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);
        int count = (int)Math.ceil(1.0 * service.getAllCount() / limit);
        model.addAttribute("current_page", page);
        if(count > 1){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, count).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }

        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @PostMapping("/{id}")
    public String edit(Model model,
                     @PathVariable Integer id,
                     @RequestBody TaskInfo info){
        if (id == null || id<= 0){
            throw new RuntimeException("Invalid id");
        }

        Task task = service.saveOrUpdate(id, info.getDescription(), info.getStatus());
        return tasks(model, 1,10);
    }

    @PostMapping("/")
    public String add(Model model,
                     @RequestBody TaskInfo info){

        Task task = service.create(info.getDescription(), info.getStatus());
        return tasks(model, 1,10);
    }

    @DeleteMapping("/{id}")
    public String delete(Model model,
                     @PathVariable Integer id){
        if (id == null || id<= 0){
            throw new RuntimeException("Invalid id");
        }
        service.delete(id);
        return "tasks";
    }
}
