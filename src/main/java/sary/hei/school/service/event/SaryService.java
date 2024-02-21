package sary.hei.school.service.event;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sary.hei.school.file.BucketComponent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class SaryService {
    BucketComponent bucketComponent;
    private final  String IMAGE_KEY="/image";
    public ResponseEntity<String> operateImage(MultipartFile image, String id) throws IOException {
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
