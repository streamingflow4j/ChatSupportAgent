package chat.support.agent.service;

import chat.support.agent.exceptions.StorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
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
        when(multipartFile.getOriginalFilename()).thenReturn("test.txt");
        when(multipartFile.getInputStream()).thenReturn(mock(InputStream.class));

        String fileUrl = fileStoreService.store(multipartFile);

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
        Path rootLocation = Paths.get(fileStoreService.getRootDocDir());
        Files.createDirectories(rootLocation);
        Files.createFile(rootLocation.resolve("test1.txt"));
        Files.createFile(rootLocation.resolve("test2.txt"));

        Stream<Path> files = fileStoreService.loadAll();

        assertNotNull(files);
        assertEquals(2, files.count());
    }

    @Test
    void testLoadFile() throws IOException {
        Path rootLocation = Paths.get(fileStoreService.getRootDocDir());
        Files.createDirectories(rootLocation);
        Files.createFile(rootLocation.resolve("test.txt"));

        Resource resource = fileStoreService.load("test.txt");

        assertNotNull(resource);
        assertTrue(resource.exists());
    }
}
