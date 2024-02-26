package com.example.localguidebe.service.impl;

import com.example.localguidebe.entity.Image;
import com.example.localguidebe.enums.AssociateName;
import com.example.localguidebe.repository.ImageRepository;
import com.example.localguidebe.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private ImageRepository imageRepository;
    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }

    public List<Image> getImageByAssociateIddAndAssociateName(Long associateId, AssociateName associateName){

      return  imageRepository.getImageByAssociateIddAndAssociateName(associateId,associateName);
    }

}
