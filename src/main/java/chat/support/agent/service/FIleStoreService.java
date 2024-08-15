package chat.support.agent.service;


import chat.support.agent.exceptions.StorageException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FIleStoreService {

    private Path rootLocation;
    private  ApplicationContext applicationContext = new ClassPathXmlApplicationContext();

    public FIleStoreService(){
        rootLocation = Paths.get(getRootDocDir());
    }
    public String getRootDocDir() {
        try {
            String fileResource2 = applicationContext.getResource("classpath:files").getFile().getCanonicalPath();
            String path = fileResource2.replace("\\", "\\\\");
            return path;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String uploadedFileName = UUID.randomUUID().toString() + "." + extension;
            Path destinationFile = rootLocation.resolve(Paths.get(uploadedFileName))
                    .normalize().toAbsolutePath();
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);

                final String baseUrl =
                        ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

                final StringBuilder imageStringBuilder = new StringBuilder(baseUrl);
                imageStringBuilder.append("/fileUpload/files/");
                imageStringBuilder.append(uploadedFileName);

                return imageStringBuilder.toString();
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    public Stream<Path> loadAll() {
        // load all the files
        try {
            return Files.walk(this.rootLocation, 1)
                    // ignore the root path
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
          //  throw new StorageException("Failed to read stored files", e);
        }

        return null;
    }

    public Resource load(String filename) {
        try {
            // read the file based on the filename
            Path file = rootLocation.resolve(filename);
            // get resource from path
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
