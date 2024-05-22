package com.example.localguidebe.controller;

import com.example.localguidebe.dto.imagerefresh.RefreshImageDataDTO;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.utils.CloudinaryUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refresh-image-data")
public class RefreshImageDataController {
    private final CloudinaryUtil cloudinaryUtil;

    public RefreshImageDataController(CloudinaryUtil cloudinaryUtil) {
        this.cloudinaryUtil = cloudinaryUtil;
    }

    @PostMapping("")
    public ResponseEntity<Result> getAddress(@RequestBody RefreshImageDataDTO absolutePath) {

        return new ResponseEntity<>(
                new Result(
                        false,
                        HttpStatus.OK.value(),
                        "Province not found",
                        cloudinaryUtil.refreshImageData(absolutePath.absolutePath())),
                HttpStatus.OK);
    }
}
