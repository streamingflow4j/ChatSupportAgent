package chat.support.agent.service;

import chat.support.agent.exceptions.StorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileStoreServiceTest {

    private FileStoreService fileStoreService;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileStoreService = new FileStoreService();
    }

    @Test
    void testStoreFile() throws IOException {
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("StreamingFlow4JAPI.txt");
        when(multipartFile.getInputStream()).thenReturn(mock(InputStream.class));
        String fileUrl = fileStoreService.getRootDocDir()+"/StreamingFlow4JAPI.txt";

        assertNotNull(fileUrl);
        assertTrue(fileUrl.contains(".txt"));
    }

    @Test
    void testStoreEmptyFile() {
        when(multipartFile.isEmpty()).thenReturn(true);

        StorageException exception = assertThrows(StorageException.class, () -> {
            fileStoreService.store(multipartFile);
        });

        assertEquals("Failed to store empty file.", exception.getMessage());
    }

    @Test
    void testLoadAllFiles() throws IOException {
        Path rootLocation = Paths.get(System.getProperty("user.dir") + "/target/classes/files");
        Files.createDirectories(rootLocation);
        File file = new File(rootLocation+"/test1.txt");
        if (!file.exists()) {
            Files.createFile(rootLocation.resolve("test1.txt"));
        }
        File file2 = new File(rootLocation+"/test1.txt");
        if (!file2.exists()) {
            Files.createFile(rootLocation.resolve("test2.txt"));
        }

        Stream<Path> files = fileStoreService.loadAll();

        assertNotNull(files);
        assertEquals(2, files.count());
    }

    @Test
    void testLoadFile() throws IOException {
        Path rootLocation = Paths.get(System.getProperty("user.dir") + "/target/classes/files");
        Files.createDirectories(rootLocation);
        File file = new File(rootLocation+"/StreamingFlow4JAPI.txt");
        if (!file.exists()) {
            Files.createFile(rootLocation.resolve("StreamingFlow4JAPI.txt"));
        }
        Resource resource = fileStoreService.load("StreamingFlow4JAPI.txt");

        assertNotNull(resource);
        assertTrue(resource.exists());
    }
}
