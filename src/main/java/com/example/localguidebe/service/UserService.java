package com.example.localguidebe.service;

import com.example.localguidebe.dto.responsedto.SearchSuggestionResponseDTO;
import com.example.localguidebe.entity.Role;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.enums.RolesEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    User findUserByEmail(String email);
    User saveUser(User user);
    List<User> getAllUser();

    Page<User> getGuides(Integer page,
                         Integer limit,
                         String sortBy,
                         String order,
                         Double ratingFilter,
                         String searchName);

    SearchSuggestionResponseDTO getGuidesAndGuideAddresses(String searchValue);
}
