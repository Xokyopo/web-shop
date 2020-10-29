package ru.geekbrains.coursework.webshop.app.domain.entities;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.coursework.webshop.app.dao.HDDRepository;

import javax.persistence.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.UUID;

@Entity(name = "images")
public class Image extends AEntity {
    private String type;
    private String path;
    private long size;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "BLOB")
    private byte[] data;

    public Image() {
    }

    public Image(MultipartFile multipartFile) {
        this.setAll(multipartFile);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setAll(MultipartFile multipartFile) {
        //todo data byte limit is (2^16 = 65 536)
        try {
            if (this.data != null && !Arrays.equals(this.data, multipartFile.getBytes())) {
                this.deleteFileBeforeRemove();
            }

            this.setName(multipartFile.getOriginalFilename());
            this.type = multipartFile.getContentType();
            this.size = multipartFile.getSize();
            //65500 max blob size in MySql
            this.path = (multipartFile.getBytes().length > 65500) ? UUID.randomUUID().toString() : null;
            this.data = multipartFile.getBytes();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String toString() {
        return "Image{" +
                "type = '" + type + '\'' +
                ", path = '" + path + '\'' +
                ", size = " + size +
                ", data.length =" + (data == null ? "0" : data.length) +
                "} " + super.toString();
    }

    @PrePersist
    @PreUpdate
    public void saveBigFileData() {
        if (StringUtils.isNotBlank(this.path)) {
            HDDRepository.getInstance().saveToFile(this.path, this.data);
            this.data = null;
        }
    }

    @PreRemove
    public void deleteFileBeforeRemove() {
        if (StringUtils.isNotBlank(this.path)) {
            HDDRepository.getInstance().delete(this.path);
        }
    }

    @PostLoad
    public void loadBigFileData() {
        if (StringUtils.isNotBlank(this.path)) {
            this.data = HDDRepository.getInstance().loadFromFile(this.path);
        }
    }
}
