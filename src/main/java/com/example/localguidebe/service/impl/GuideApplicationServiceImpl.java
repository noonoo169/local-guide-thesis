package com.example.localguidebe.service.impl;

import com.example.localguidebe.dto.requestdto.AddGuideApplicationDTO;
import com.example.localguidebe.dto.requestdto.UpdateGuideApplicationStatus;
import com.example.localguidebe.entity.GuideApplication;
import com.example.localguidebe.entity.Role;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.enums.GuideApplicationStatus;
import com.example.localguidebe.enums.RolesEnum;
import com.example.localguidebe.repository.GuideApplicationRepository;
import com.example.localguidebe.service.GuideApplicationService;
import com.example.localguidebe.service.RoleService;
import com.example.localguidebe.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GuideApplicationServiceImpl implements GuideApplicationService {
  private final UserService userService;
  private final RoleService roleService;
  private final GuideApplicationRepository guideApplicationRepository;

  public GuideApplicationServiceImpl(
      UserService userService,
      RoleService roleService,
      GuideApplicationRepository guideApplicationRepository) {
    this.userService = userService;
    this.roleService = roleService;
    this.guideApplicationRepository = guideApplicationRepository;
  }

  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  public List<GuideApplication> findAll() {
    return guideApplicationRepository.findAll();
  }

  @Override
  public GuideApplication findById(Long id) {
    return guideApplicationRepository.findById(id).orElse(null);
  }

  @Override
  public GuideApplication addGuideApplication(AddGuideApplicationDTO addGuideApplicationDTO) {
    User user;
    if (addGuideApplicationDTO.userId() != null) {
      user = userService.findById(addGuideApplicationDTO.userId()).orElse(null);
      if (user == null) return null;
    } else {
      if (userService.findUserByEmail(addGuideApplicationDTO.email()) != null) return null;
      Set<Role> role = new HashSet<>();
      role.add(roleService.findByName(RolesEnum.TRAVELER));
      User newUser =
          User.builder()
              .email(addGuideApplicationDTO.email())
              .address(addGuideApplicationDTO.address())
              .fullName(addGuideApplicationDTO.fullName())
              .password(passwordEncoder.encode(addGuideApplicationDTO.password()))
              .phone(addGuideApplicationDTO.phone())
              .dateOfBirth(addGuideApplicationDTO.dateOfBirth())
              .roles(role)
              .build();
      user = userService.saveUser(newUser);
    }
    GuideApplication guideApplication =
        GuideApplication.builder()
            .isLicensedGuide(addGuideApplicationDTO.isLicensedGuide())
            .transportation(addGuideApplicationDTO.transportation())
            .yearsOfExperience(addGuideApplicationDTO.yearsOfExperience())
            .howGuideHearAboutUs(addGuideApplicationDTO.howGuideHearAboutUs())
            .status(GuideApplicationStatus.PENDING)
            .user(user)
            .build();
    return guideApplicationRepository.save(guideApplication);
  }

  @Override
  public boolean updateGuideApplicationStatus(
      Long id, UpdateGuideApplicationStatus updateGuideApplicationStatus) {
    GuideApplication guideApplication = guideApplicationRepository.findById(id).orElse(null);
    if (guideApplication == null) return false;

    guideApplication.setStatus(updateGuideApplicationStatus.status());
    if (updateGuideApplicationStatus.status().equals(GuideApplicationStatus.ACCEPTED))
      guideApplication.getUser().getRoles().add(roleService.findByName(RolesEnum.GUIDER));
    if (updateGuideApplicationStatus.status().equals(GuideApplicationStatus.DENIED)) {
      guideApplication.setReasonDeny(updateGuideApplicationStatus.reasonDeny());
    }
    guideApplicationRepository.save(guideApplication);
    return true;
  }
}
