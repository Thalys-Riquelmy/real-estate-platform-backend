package com.imobiliaria_api.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtil {

    // Compactar imagem e retornar byte array
    public static byte[] compressImageToBytes(MultipartFile file, int maxWidth, int maxHeight, float quality) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        Thumbnails.of(file.getInputStream())
                .size(maxWidth, maxHeight)
                .outputQuality(quality)
                .outputFormat("jpg")
                .toOutputStream(outputStream);
        
        return outputStream.toByteArray();
    }
}