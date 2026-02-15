package com.manser.pr.dao;

import com.manser.pr.domain.Task;
import com.manser.pr.domain.User;

import java.util.List;

public interface TaskDao extends CrudDao<Task>{

    public List<Task> findByOwnerId(Long ownerId);

    boolean existsByTitleAndOwner(String title, User owner);

    boolean existsByTitleAndOwnerExcludingId(String title, User owner, Long taskId);

    Long countByOwnerId(Long userId);

}
