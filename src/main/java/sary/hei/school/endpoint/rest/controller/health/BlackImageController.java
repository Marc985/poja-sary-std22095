package sary.hei.school.endpoint.rest.controller.health;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sary.hei.school.file.BucketComponent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Optional;

@RestController
public class BlackImageController {
    private static final String IMAGE_KEY = "image/";
    BucketComponent bucketComponent;
    @GetMapping(value = "/image/{id}")
    public ResponseEntity<String> getBlackAndWhiteImage(@PathVariable int id) {
            String fileSuffix=".png";
           int filePrefix=id;
           String bucketKey=IMAGE_KEY+filePrefix+fileSuffix;
            bucketComponent.download(bucketKey);
            return ResponseEntity.of(Optional.of(bucketComponent.presign(bucketKey, Duration.ofMinutes(2)).toString()));

    }



    @PutMapping("/black/{id}")
    public ResponseEntity<String> uploadImage(@RequestBody MultipartFile image,@PathVariable int id) throws IOException {
        BufferedImage originalImage = ImageIO.read(image.getInputStream());
        BufferedImage blackAndWhiteImage = convertToBlackAndWhite(originalImage);
        String  formatName="png";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(blackAndWhiteImage, formatName, baos);
        ByteArrayResource ressource=new ByteArrayResource(baos.toByteArray());

        File file=ressource.getFile();
        String suffix=".png";
        String bucketKey=(IMAGE_KEY+id).toString();
        bucketComponent.upload(file,bucketKey);
        return ResponseEntity.ok("file uploaded succefully");
    }
    private BufferedImage convertToBlackAndWhite(BufferedImage originalImage) {
        BufferedImage blackAndWhiteImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D graphics = blackAndWhiteImage.createGraphics();
        graphics.drawImage(originalImage,  0,  0, null);
        graphics.dispose();
        return blackAndWhiteImage;
    }
}
