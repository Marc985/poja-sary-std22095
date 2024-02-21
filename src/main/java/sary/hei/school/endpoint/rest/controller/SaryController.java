package sary.hei.school.endpoint.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sary.hei.school.file.BucketComponent;
import sary.hei.school.service.event.SaryService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

@RestController
public class SaryController {
    private static final String IMAGE_KEY = "image/";
    BucketComponent bucketComponent;
    @Autowired
    SaryService saryService;

    @GetMapping(value = "/black/{id}")
    public ResponseEntity<String> getBlackAndWhiteImage(@PathVariable int id) {
        String fileSuffix = ".png";
        int filePrefix = id;
        String bucketKey = IMAGE_KEY + filePrefix + fileSuffix;
        bucketComponent.download(bucketKey);
        return ResponseEntity.of(Optional.of(bucketComponent.presign(bucketKey, Duration.ofMinutes(2)).toString()));

    }


    @PutMapping("/black/{id}")
    public ResponseEntity<String> uploadImage(@RequestBody MultipartFile image, @PathVariable String id) throws IOException {
        return saryService.operateImage(image, id);
    }
}
