package com.example.localguidebe.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.localguidebe.enums.FolderName;
import jakarta.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class CloudinaryUtil {
  @Resource private Cloudinary cloudinary;

  public String uploadFile(String base64String, FolderName folderName) throws IOException {
    if (base64String.startsWith("data:")) {
      base64String = base64String.substring(base64String.indexOf(",") + 1);
    }

    byte[] decodedBytes = Base64.getDecoder().decode(base64String);

    ByteArrayResource resource =
        new ByteArrayResource(decodedBytes) {
          @Override
          public String getFilename() {
            return "file"; // Or you can give a dynamic file name if you have it
          }
        };

    MultipartFile multipartFile =
        new MultipartFile() {
          @Override
          public String getName() {
            return "file";
          }

          @Override
          public String getOriginalFilename() {
            return "file";
          }

          @Override
          public String getContentType() {
            return "application/octet-stream"; // You might need to change this based on your use
            // case
          }

          @Override
          public boolean isEmpty() {
            return decodedBytes.length == 0;
          }

          @Override
          public long getSize() {
            return decodedBytes.length;
          }

          @Override
          public byte[] getBytes() throws IOException {
            return decodedBytes;
          }

          @Override
          public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(decodedBytes);
          }

          @Override
          public void transferTo(java.io.File dest) throws IOException, IllegalStateException {}
        };

    try {
      HashMap<Object, Object> options = new HashMap<>();
      options.put("folder", folderName.toString());
      Map<?, ?> uploadedFile = cloudinary.uploader().upload(multipartFile.getBytes(), options);
      String publicId = (String) uploadedFile.get("public_id");
      return cloudinary.url().secure(true).generate(publicId);
    } catch (IOException e) {
      log.error("Cannot upload file now - {}", e.getMessage());
      return "Cannot upload file now";
    }
  }

  public boolean deleteFile(String url, FolderName folderName) {
    try {
      String folder = folderName.name().toLowerCase() + "/";
      String publicId = url.substring(url.indexOf(folder), url.lastIndexOf("."));
      cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public List<String> refreshImageData(String folderPath) {
    try {
      List<File> imageFiles = getAllImagesFromFolders(folderPath);
      log.info(imageFiles.toString());
      List<String> urls = new ArrayList<>();
      int count = 187;
      for (File imageFile : imageFiles) {
        String base64String = convertToBase64(imageFile);
        String url = uploadFile(base64String, FolderName.province);
        String insertValue =
            "("
                + count++
                + ", "
                + "'province', "
                + "'"
                + url
                + "', "
                + "NULL, "
                + imageFile.getName().split("\\.")[1].trim()
                + ")";
        urls.add(insertValue);
      }
      return urls;
    } catch (IOException e) {
      return Collections.singletonList(e.getMessage());
    }
  }

  public static List<File> getAllImagesFromFolders(String folderPath) throws IOException {
    List<File> imageFiles = new ArrayList<>();

    Files.walk(Paths.get(folderPath))
        .filter(Files::isRegularFile)
        .map(Path::toFile)
        .forEach(imageFiles::add);
    imageFiles.sort(Comparator.comparing(file -> file.getName().split("\\.")[1].trim()));

    return imageFiles;
  }

  public static String convertToBase64(File file) throws IOException {
    byte[] fileContent = Files.readAllBytes(file.toPath());
    return Base64.getEncoder().encodeToString(fileContent);
  }
}
