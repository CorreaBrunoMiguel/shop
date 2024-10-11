package org.correa.dreamshops.service.image;

import org.correa.dreamshops.dto.ImageDto;
import org.correa.dreamshops.model.Image;
import org.correa.dreamshops.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IIMageService {

    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageI);

}
