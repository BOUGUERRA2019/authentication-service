package com.mdeaf.Repository;

import com.mdeaf.Entities.AppTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository  extends JpaRepository<AppTask, Long> {
}
