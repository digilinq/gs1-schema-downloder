package com.eightbits.commerce.retail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GS1SchemaDownloaderTest {

    private final String GS1_SCHEMA_URL = "http://www.gdsregistry.org/3.1/schemas/";
    private final String OUTPUT_DIRECTORY = "target/generated-sources/xsd";

    private GS1SchemaDownloader gs1SchemaDownloader;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        gs1SchemaDownloader = new GS1SchemaDownloader();

        Field gs1SchemaUrl = GS1SchemaDownloader.class.getDeclaredField("gs1SchemaUrl");
        gs1SchemaUrl.setAccessible(true);
        gs1SchemaUrl.set(gs1SchemaDownloader, GS1_SCHEMA_URL);

        Field outputDirectory = GS1SchemaDownloader.class.getDeclaredField("outputDirectory");
        outputDirectory.setAccessible(true);
        outputDirectory.set(gs1SchemaDownloader, OUTPUT_DIRECTORY);
    }

    @Tag(TestProfiles.STORAGE_REQUIRED)
    @Test
    void should_download_gs1_schema() {
        assertDoesNotThrow(gs1SchemaDownloader::execute);
    }

    @Tag(TestProfiles.STORAGE_REQUIRED)
    @Test
    void should_download_recursively() {
        assertDoesNotThrow(() -> gs1SchemaDownloader.downloadGS1Schema(GS1_SCHEMA_URL));
    }

    @Test
    void should_extract_file_path() {
        String url = "http://www.gdsregistry.org/3.1/schemas/epcglobal/epcis/1_2/Query.xsd";
        String filePath = gs1SchemaDownloader.extractFilePath(url);
        assertEquals("epcglobal/epcis/1_2/Query.xsd", filePath);
    }

    @Test
    void should_extract_file_name() {
        String url = "http://www.gdsregistry.org/3.1/schemas/epcglobal/epcis/1_2/Query.xsd";
        String fileName = gs1SchemaDownloader.extractFileName(url);
        assertEquals("Query.xsd", fileName);
    }
}

