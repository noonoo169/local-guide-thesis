package com.example.localguidebe.service;

import com.example.localguidebe.dto.requestdto.AddGuideApplicationDTO;
import com.example.localguidebe.dto.requestdto.UpdateGuideApplicationStatus;
import com.example.localguidebe.entity.GuideApplication;
import com.example.localguidebe.enums.GuideApplicationStatus;
import java.util.List;

public interface GuideApplicationService {
  List<GuideApplication> findAll();

  GuideApplication findById(Long id);

  GuideApplication addGuideApplication(AddGuideApplicationDTO addGuideApplicationDTO);

  boolean updateGuideApplicationStatus(Long id, UpdateGuideApplicationStatus updateGuideApplicationStatus);
}
