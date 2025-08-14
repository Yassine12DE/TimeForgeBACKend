package tn.esprit.tic.timeforge.Service;


import tn.esprit.tic.timeforge.Entity.Ennum.StatusTask;
import tn.esprit.tic.timeforge.Entity.Project;
import tn.esprit.tic.timeforge.Entity.Task;
import tn.esprit.tic.timeforge.Entity.User;

import java.util.List;
import java.util.Set;

public interface IntTaskService  {
    public Task addTask(Task task ,Long idprojet,Long employeeId,Long lead);
    public List<Project> getallprojetbyidLead(Long id);
    public String updateTaskStatus(Long id, StatusTask updatedTask);
    public List<Task> getAllTasksbyUser(Long id);
    void deleteTask (Long id  );
    public List<Project> getall();
    public Set<User> getAllUSER();
     Task updateTask(Long id, Task updatedTask);


    Task getTaskById(Long id);
    List <Task> getAllTasks();

}
