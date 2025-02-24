package com.amcamp.cineAI.global.util;

import com.amcamp.cineAI.global.error.exception.CustomException;
import com.amcamp.cineAI.global.error.exception.ErrorCode;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Component
@RequiredArgsConstructor
public class FileUtils {

    private final S3Client s3Client;
    private final S3Presigner preSigner;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.s3.folder}")
    private String s3Folder;

    // 파일 업로드
    public String uploadFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String s3Key = s3Folder + storeFileName;
        try {
            PutObjectRequest putObjectRequest =
                    PutObjectRequest.builder().bucket(bucketName).key(s3Key).build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL);
        }

        return s3Key;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    public String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    private String getFileUrl(String fileName) {
        return "https://" + bucketName + ".s3.amazonaws.com/" + s3Folder + fileName;
    }

    public String generatePresignedUrl(String objectKey) {
        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder().bucket(bucketName).key(objectKey).build();

        GetObjectPresignRequest presignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10)) // Presigned URL 10분 유효
                        .getObjectRequest(getObjectRequest)
                        .build();
        PresignedGetObjectRequest presignedRequest = preSigner.presignGetObject(presignRequest);
        preSigner.close();

        return presignedRequest.url().toString();
    }

    public InputStream getImageInputStream(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        URLConnection urlConnection = url.openConnection();
        return urlConnection.getInputStream();
    }
}
