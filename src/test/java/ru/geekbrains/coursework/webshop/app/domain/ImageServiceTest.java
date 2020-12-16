package ru.geekbrains.coursework.webshop.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.coursework.webshop.app.CallCurrentMethodException;
import ru.geekbrains.coursework.webshop.app.dao.ImageRepository;
import ru.geekbrains.coursework.webshop.app.domain.entities.Image;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImageServiceTest {
    private ImageRepository imageRepository;
    private ImageService imageService;

    @BeforeEach
    public void init() {
        this.imageRepository = Mockito.mock(ImageRepository.class);
        this.imageService = new ImageService();
        this.imageService.init(this.imageRepository);
    }

    @Test
    public void save_ShouldThrowException_WhenBothParameterNull() {
        Class<IllegalArgumentException> expected = IllegalArgumentException.class;
        Executable actual = () -> this.imageService.save(null, null);
        assertThrows(expected, actual);
    }

    @Test
    public void save_ShouldThrowException_WhenFirsParameterNull() {
        Class<IllegalArgumentException> expected = IllegalArgumentException.class;
        Executable actual = () -> this.imageService.save(null, new ArrayList<>());
        assertThrows(expected, actual);
    }

    @Test
    public void save_ShouldThrowException_WhenSecondParameterNull() {
        Class<IllegalArgumentException> expected = IllegalArgumentException.class;
        Executable actual = () -> this.imageService.save(new Image(), null);
        assertThrows(expected, actual);
    }

    @Test
    public void save_ShouldExecuteSave_WhenEntityHaveNonZeroIdAndMultipartFilesNotEmpty() {
        Image image = Mockito.mock(Image.class);
        Mockito.when(image.getId()).thenReturn(1L);
        Mockito.doNothing().when(image).setAll(Mockito.any(MultipartFile.class));
        Mockito.when(this.imageRepository.save(Mockito.any(Image.class))).thenThrow(
                new CallCurrentMethodException("save_ShouldExecuteSave_WhenEntityHaveNonZeroIdAndMultipartFilesNotEmpty"));

        Class<CallCurrentMethodException> expected = CallCurrentMethodException.class;
        Executable actual = () -> this.imageService.save(image, List.of(new MockMultipartFile("name", new byte[1])));
        assertThrows(expected, actual);
    }

    @Test
    public void save_ShouldExecuteSaveAll_WhenEntityHaveZeroIdAndMultipartFilesNotEmpty() {
        Image image = Mockito.mock(Image.class);
        Mockito.when(image.getId()).thenReturn(0L);
        Mockito.doNothing().when(image).setAll(Mockito.any(MultipartFile.class));
        Mockito.when(this.imageRepository.saveAll(Mockito.anyList())).thenThrow(
                new CallCurrentMethodException("save_ShouldExecuteSave_WhenEntityHaveNonZeroIdAndMultipartFilesNotEmpty"));

        Class<CallCurrentMethodException> expected = CallCurrentMethodException.class;
        Executable actual = () -> this.imageService.save(image, List.of(new MockMultipartFile("name", new byte[1])));
        assertThrows(expected, actual);
    }

    @Test
    public void save_ShouldNotThrowException_WhenEmptyEntityAndMultipartFilesIsEmpty() {
        String exceptionMessage = "save_ShouldNotThrowException_WhenEmptyEntityAndMultipartFilesIsEmpty";

        Mockito.when(this.imageRepository.saveAll(Mockito.anyList())).thenThrow(new CallCurrentMethodException(exceptionMessage));
        Mockito.when(this.imageRepository.save(Mockito.any(Image.class))).thenThrow(new CallCurrentMethodException(exceptionMessage));

        Executable actual = () -> this.imageService.save(new Image(), new ArrayList<>());
        assertDoesNotThrow(actual);
    }

    @Test
    public void save_ShouldNotThrowException_WhenEntityHaveNonZeroIdAndMultipartFilesIsEmpty() {
        Image image = new Image();
        image.setId(1);
        String exceptionMessage = "save_ShouldNotThrowException_WhenEntityHaveNonZeroIdAndMultipartFilesIsEmpty";

        Mockito.when(this.imageRepository.saveAll(Mockito.anyList())).thenThrow(new CallCurrentMethodException(exceptionMessage));
        Mockito.when(this.imageRepository.save(Mockito.any(Image.class))).thenThrow(new CallCurrentMethodException(exceptionMessage));

        Executable actual = () -> this.imageService.save(image, new ArrayList<>());
        assertDoesNotThrow(actual);
    }
}
