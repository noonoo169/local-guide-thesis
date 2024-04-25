package com.example.localguidebe.service;

import com.example.localguidebe.entity.Image;
import com.example.localguidebe.enums.AssociateName;

import java.util.List;

public interface ImageService {

    List<Image> getImageByAssociateIddAndAssociateName(Long associateId, AssociateName associateName);

}
