package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.ToResultInSearchSuggestionDtoConverter;
import com.example.localguidebe.dto.responsedto.ResultInSearchSuggestionDTO;
import com.example.localguidebe.dto.responsedto.SearchSuggestionResponseDTO;
import com.example.localguidebe.entity.Role;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.enums.RolesEnum;
import com.example.localguidebe.repository.UserRepository;
import com.example.localguidebe.service.UserService;
import com.example.localguidebe.utils.AddressUtils;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;

  private final ToResultInSearchSuggestionDtoConverter toResultInSearchSuggestionDtoConverter;

  public UserServiceImpl(
      ToResultInSearchSuggestionDtoConverter toResultInSearchSuggestionDtoConverter) {
    this.toResultInSearchSuggestionDtoConverter = toResultInSearchSuggestionDtoConverter;
  }

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
  public Page<User> getGuides(
      Integer page,
      Integer limit,
      String sortBy,
      String order,
      Double ratingFilter,
      String searchValue) {
    Sort sort = order.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable paging = PageRequest.of(page, limit, sort);
    Specification<Object> specification =
        Specification.where(
                (root, query, criteriaBuilder) -> {
                  Join<User, Role> userRoleJoin = root.join("roles", JoinType.INNER);
                  return criteriaBuilder.equal(userRoleJoin.get("name"), RolesEnum.GUIDER);
                })
            .and(
                (root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                        criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("username")),
                            "%" + searchValue.toLowerCase() + "%"),
                        criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("address")),
                            "%" + searchValue.toLowerCase() + "%")));

    if (ratingFilter != null) {
      specification =
          specification.and(
              (root, query, criteriaBuilder) ->
                  criteriaBuilder.greaterThanOrEqualTo(root.get("overallRating"), ratingFilter));
    }
    return userRepository.findAll(specification, paging);
  }

  @Override
  public SearchSuggestionResponseDTO getGuidesAndGuideAddresses(String searchValue) {
    List<User> guiders = userRepository.findByRoles_Name(RolesEnum.GUIDER);
    List<String> addressesFiltered =
        guiders.stream()
            .map(guider -> AddressUtils.removeVietnameseAccents(guider.getAddress()))
            .distinct()
            .filter(
                address ->
                    AddressUtils.removeVietnameseAccents(address)
                        .toLowerCase()
                        .contains(AddressUtils.removeVietnameseAccents(searchValue).toLowerCase()))
            .toList();
    List<ResultInSearchSuggestionDTO> guidersFiltered =
        guiders.stream()
            .sorted(Comparator.comparing(User::getOverallRating).reversed())
            .filter(
                guider ->
                    AddressUtils.removeVietnameseAccents(guider.getUsername())
                        .toLowerCase()
                        .contains(AddressUtils.removeVietnameseAccents(searchValue).toLowerCase()))
            .map(toResultInSearchSuggestionDtoConverter::convert)
            .toList();
    return new SearchSuggestionResponseDTO(addressesFiltered, guidersFiltered);
  }
}
