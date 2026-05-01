package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class FileController {


    @Value("${UPLOAD_DESTINATION:uploaded}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Get the strict absolute path to your project's "uploaded" folder
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

            // 2. Create the directory if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 3. Resolve the exact file destination path
            Path dest = uploadPath.resolve(file.getOriginalFilename());

            // 4. Copy the file using NIO (This completely ignores the Tomcat temp folder)
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("Fichier uploadé avec succès vers : " + dest.toString());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }
}
