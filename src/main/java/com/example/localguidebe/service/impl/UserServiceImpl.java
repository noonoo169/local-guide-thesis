package com.example.localguidebe.service.impl;

import com.example.localguidebe.entity.Role;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.enums.RolesEnum;
import com.example.localguidebe.repository.UserRepository;
import com.example.localguidebe.service.UserService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return this.userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getAllUser() {
       return this.userRepository.findAll();

    }

    @Override
    public Page<User> getGuides(Integer page, Integer limit, String sortBy, String order, Double ratingFilter, String searchName) {
        Sort sort = order.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable paging = PageRequest.of(page, limit, sort);
        Specification<User> specification = Specification.where((root, query, criteriaBuilder) -> {
            Join<User, Role> userRoleJoin = root.join("roles", JoinType.INNER);
            return criteriaBuilder.equal(userRoleJoin.get("name"), RolesEnum.GUIDER);
        });

        if (ratingFilter != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("overallRating"), ratingFilter));
        }

        if (searchName != null && !searchName.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + searchName.toLowerCase() + "%"));
        }
        return userRepository.findAll(specification, paging);
    }
}
