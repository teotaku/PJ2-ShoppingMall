package supercoding.pj2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import supercoding.pj2.s3.S3Uploader;

import java.io.IOException;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class FileController {

    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {
        String imageUrl = s3Uploader.upload(file);
        return ResponseEntity.ok(imageUrl);
    }
}
