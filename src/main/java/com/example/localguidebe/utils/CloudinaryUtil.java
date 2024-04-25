package com.example.localguidebe.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.localguidebe.enums.FolderName;
import jakarta.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CloudinaryUtil {
  private static final Logger logger = LoggerFactory.getLogger(CloudinaryUtil.class);
  @Resource private Cloudinary cloudinary;

  public String uploadFile(MultipartFile file, FolderName folderName) {
    try {
      HashMap<Object, Object> options = new HashMap<>();
      options.put("folder", folderName.toString());
      Map<?, ?> uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
      String publicId = (String) uploadedFile.get("public_id");
      return cloudinary.url().secure(true).generate(publicId);
    } catch (IOException e) {
      logger.error("Cannot upload file now - {}", e.getMessage());
      return "Cannot upload file now";
    }
  }

  private static String getPublicIdFile(String url) {
    int lastSlashIndex = url.lastIndexOf('/');
    if (lastSlashIndex >= 0) {
      int secondLastSlashIndex = url.lastIndexOf('/', lastSlashIndex - 1);
      if (secondLastSlashIndex >= 0) {
        return url.substring(secondLastSlashIndex + 1);
      }
    }
    return null;
  }

  public String deleteFile(String url) {
    try {
      String publicId = getPublicIdFile(url);
      cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
      return "Delete file succeeded.";
    } catch (Exception e) {
      logger.error("Cannot upload image now - {}", e.getMessage());
      return "Cannot delete file now.";
    }
  }
}
