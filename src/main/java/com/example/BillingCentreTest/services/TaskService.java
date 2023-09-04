package com.example.BillingCentreTest.services;

import com.example.BillingCentreTest.models.Task;
import com.example.BillingCentreTest.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    /**
     * @param date - дата, для которой производится поиск задач
     * @return возвращает список объектов класса Task
     */
    public List<Task> findByDate(Date date) {
        return taskRepository.findByDate(date).stream()
                .sorted(Comparator.comparingInt(task -> task.getType().getValue()))
                .collect(Collectors.toList());
    }
    /*
     * При добавлении пагинации столкнулся со следующей проблемой:
     * В качестве типа используется Enum, с этим проблемы нет. Но сортировка проводится по
     * целочисленному значению енама, в виде как в закомментированном методе ниже выпадает ошибка
     * attribute is not joinable. А если вместо type.value писать просто type, то сортировка
     * соответственно проводится на элементах енама в алфавитном порядке

    public Page<Task> findByDate(Date date, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "type.value");

        Pageable pageableWithSort = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        );
        return taskRepository.findByDate(date, pageableWithSort);
    }

* */

    /**
     * @param id - ID, по которому производится поиск задачи
     * @return возвращает объект класса Task или Null, если задачи не существует
     */
    public Task findOne(long id) {
        Optional<Task> foundTask = taskRepository.findById(id);
        return foundTask.orElse(null);
    }

    /**
     * Т.к. возвращается список строк, а не объекты Task, то в названии метода не использовал слово find.
     *
     * @return возвращает список типов задач
     */
    public List<String> getTypes() {
        return taskRepository.getTypes();
    }

    /**
     * @param task - объект класса task
     */
    @Transactional
    public void save(Task task) {
        taskRepository.save(task);
    }

    /**
     * @param id - ID удаляемой задачи
     */
    @Transactional
    public void delete(long id) {
        taskRepository.deleteById(id);
    }
}