package ua.com.todolisttesttask.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.todolisttesttask.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUser_Id(Long userId, Pageable pageable);

    Optional<Task> findByIdAndUser_Id(Long taskId, Long userId);

    void deleteByIdAndUser_Id(Long taskId, Long userId);
}
