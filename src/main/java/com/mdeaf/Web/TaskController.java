package com.mdeaf.Web;

import com.mdeaf.Entities.AppTask;
import com.mdeaf.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    //pour retourner la liste des taches
    @GetMapping("/tasks")
    public List<AppTask> listTasks(){
        return taskRepository.findAll();

    }
    //pour enregistrer une tache
    @PostMapping("/tasks")
    public AppTask save(@RequestBody AppTask appTask){
        return taskRepository.save(appTask);
    }
}
