package com.commerce.library.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUpload {
    private final String UpLoad = "/Users/cui/IdeaProjects/Ecommerce/Admin/src/main/resources/static/img/upLoda";

    public boolean uploadImage(MultipartFile img){
        boolean isUpload = false;
        try {
            Path path = Paths.get(UpLoad+ File.separator, img.getOriginalFilename());
            Files.copy(img.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            isUpload =true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpload;
    }

    public boolean checkExisted(MultipartFile imageProduct){
        boolean isExisted = false;
        try {
            File file =new File(UpLoad + "\\" + imageProduct.getOriginalFilename());
            isExisted = file.exists();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isExisted;
    }
}









