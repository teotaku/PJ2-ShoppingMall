package supercoding.pj2.s3;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@ConditionalOnProperty(name = "cloud.aws.enabled", havingValue = "true", matchIfMissing = false)
public class S3Uploader {

    private final AwsS3Properties awsProps;
    private S3Client s3Client;

    @PostConstruct
    public void init() {
        this.s3Client = S3Client.builder()
                .region(Region.of(awsProps.getRegion().getStaticRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                awsProps.getCredentials().getAccessKey(),
                                awsProps.getCredentials().getSecretKey()
                        )
                ))
                .build();
    }

    public String upload(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        String safeFileName = UUID.randomUUID() + "_" + originalName;

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(awsProps.getS3().getBucket())
                .key(safeFileName)
                .contentType(file.getContentType())
                .build();

        try {
            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (Exception e) {
            System.err.println(" S3 업로드 실패: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("S3 파일 업로드 실패", e);
        }

        return "https://" + awsProps.getS3().getBucket()
                + ".s3." + awsProps.getRegion().getStaticRegion()
                + ".amazonaws.com/" + safeFileName;
    }
}
