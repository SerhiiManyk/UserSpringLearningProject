package com.manser.pr.service;

import com.manser.pr.domain.SortField;
import com.manser.pr.domain.SortOrder;
import com.manser.pr.domain.User;

import java.util.List;

public interface UserService extends CrudService<User>{

    User loginUser(String email, String password);

    User getByEmail(String email);

    void checkEmailUnique(User user);

    List<User> getAllSorted(SortField sortField, SortOrder sortOrder);

    List<User> getSearchResult(SortField sortField, String searchValue);

     User getCurrentUser();
}
