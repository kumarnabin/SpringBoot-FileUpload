package com.example.FileUpload.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class FileUploadController {

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;
    String separator = File.separator;

    @PostMapping("/uploadFile")
    public ResponseEntity<Object> fileUpload(@RequestParam("File") MultipartFile file) {
        if (!new File(FILE_DIRECTORY).exists()) {
            if (new File(FILE_DIRECTORY).mkdir()) {
                System.out.println("Directory is created");
            } else {
                System.out.println("Directory is not created");
            }
        }
        String fileName = file.getOriginalFilename();
        if (Objects.requireNonNull(fileName).isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        } else {
            String extension = fileName.substring(fileName.lastIndexOf("."));
            //                method 1
            try (InputStream inputStream = file.getInputStream()) {
                Path uploadPath = Paths.get(FILE_DIRECTORY);
                Path filePath1 = uploadPath.resolve(System.currentTimeMillis() + extension);
                Files.copy(inputStream, filePath1, StandardCopyOption.REPLACE_EXISTING);
                return new ResponseEntity<>(filePath1.toString(), HttpStatus.OK);

            } catch (IOException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }


            //write file to the file system

//                method 2
//            try {
//                String filePath = FILE_DIRECTORY + separator + System.currentTimeMillis() + extension;
//                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
//                fileOutputStream.write(file.getBytes());
//                fileOutputStream.close();
//
//                return new ResponseEntity<>(filePath, HttpStatus.OK);
//            } catch (IOException ioe) {
//                throw new IOException("Could not save image file: " + fileName, ioe);
//            }
        }
    }

    @GetMapping("/getFiles")
    public ResponseEntity<Object> listFilesUsingFilesList() {
        Set<String> files = Stream.of(Objects.requireNonNull(new File("storage").listFiles()))
                .map(File::getName)
                .collect(Collectors.toSet());
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/view/{filename:.+}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getImageDynamicType(@PathVariable String filename) throws IOException {
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
//        get file from storage
        File file = new File(FILE_DIRECTORY + separator + filename);
        InputStream inputStream = Files.newInputStream(file.toPath());
        switch (extension) {
            case "pdf":
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/pdf"))
                        .body(new InputStreamResource(inputStream));
            case "txt":
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("text/plain"))
                        .body(new InputStreamResource(inputStream));

            case "png":
            case "jpg":
            case "jpeg":
            case "bmp":
            case "gif":
            case "tiff":
            case "svg":
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("image/" + extension))
                        .body(new InputStreamResource(inputStream));
            case "mp4":
            case "avi":
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("video/" + extension))
                        .body(new InputStreamResource(inputStream));
            default:
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .body(new InputStreamResource(inputStream));
        }

    }

}
