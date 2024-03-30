package com.eightbits.commerce.retail;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Mojo(name = "download-gs1-schema", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GS1SchemaDownloader extends AbstractMojo {

    @Parameter(property = "gs1SchemaUrl", defaultValue = "http://www.gdsregistry.org/3.1/schemas/")
    private String gs1SchemaUrl;

    @Parameter(property = "outputDirectory", defaultValue = "target/generated-sources/xsd")
    private String outputDirectory;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Downloading GS1 schema...");
        getLog().info("URL: " + gs1SchemaUrl);
        try {
            downloadGS1Schema(gs1SchemaUrl);
        } catch (IOException e) {
            throw new MojoExecutionException("Error downloading GS1 schema", e);
        }
    }

    void downloadGS1Schema(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        links.stream().filter(this::isNotQueryParameter).filter(link -> !this.urlContainsLink(url, link)).forEach(link -> {
            String href = link.attr("href");
            if (href.endsWith(".xsd")) {
                this.downloadXsdFile(url + href);
            } else {
                try {
                    downloadGS1Schema(url + href);
                } catch (IOException e) {
                    getLog().error("Error reading:" + url + href, e);
                }
            }
        });
    }

    void downloadXsdFile(String url) {
        getLog().info("Downloading: " + url);
        try {
            byte[] content = Jsoup.connect(url).ignoreContentType(true).execute().bodyAsBytes();
            Path path = new File(outputDirectory, extractFilePath(url)).toPath();
            getLog().info("Writing file: " + path.toAbsolutePath());

            Files.createDirectories(path.getParent());
            Files.write(path, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            getLog().error("Error downloading: " + extractFileName(url), e);
        }
    }

    String extractFilePath(String url) {
        if (!url.startsWith(gs1SchemaUrl)) {
            throw new IllegalArgumentException("Invalid gs1 schema base URL");
        }
        return url.substring(gs1SchemaUrl.length());
    }

    String extractFileName(String url) {
        if (!url.startsWith(gs1SchemaUrl)) {
            throw new IllegalArgumentException("Invalid gs1 schema base URL");
        }
        return url.substring(url.lastIndexOf('/') + 1);
    }

    boolean urlContainsLink(String url, Element link) {
        return url.contains(link.attr("href"));
    }

    boolean isNotQueryParameter(Element link) {
        String href = link.attr("href");
        return !href.startsWith("?");
    }

    void countNumberOfDependencies() {
        getLog().info("Project: " + project.getName());
        int count = project.getDependencies().size();
        getLog().info("Number of project dependencies: " + count);
    }
}
