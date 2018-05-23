package offersApp.service.imageStorage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    //create upload directory
    void init();

    String store(MultipartFile file);

    Path getAbsolutePath(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

}