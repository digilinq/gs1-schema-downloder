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
import org.jsoup.select.Elements;

import java.io.IOException;

@Mojo(name = "download-gs1-schema", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GS1SchemaDownloader extends AbstractMojo {

    @Parameter(property = "gs1SchemaUrl", defaultValue = "http://www.gdsregistry.org/3.1/schemas/")
    private String url;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

//    @Parameter(defaultValue = "${project}", required = true, readonly = true)
//    MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Downloading GS1 schema...");
        getLog().info("URL: " + url);
        countNumberOfDependencies();
        try {
            downloadGS1Schema();
        } catch (IOException e) {
            throw new MojoExecutionException("Error downloading GS1 schema", e);
        }


//        try {
//            Process process = new ProcessBuilder("python", "download_gs1_schema.py").start();
//            process.waitFor();
//        } catch (IOException | InterruptedException e) {
//            throw new MojoExecutionException("Error running Python script", e);
//        }
    }

    private void downloadGS1Schema() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        links.forEach(link -> {
            String href = link.attr("href");
            getLog().info("Link: " + href);
            if (href.endsWith(".xsd")) {
                getLog().info("Downloading: " + href);
//                try {
//                    Jsoup.connect(url + href).ignoreContentType(true).execute().bodyAsBytes();
//                } catch (IOException e) {
//                    getLog().error("Error downloading: " + href, e);
//                }
            }
        });
    }

    private void countNumberOfDependencies() {
        getLog().info("Project: " + project.getName());
        int count = project.getDependencies().size();
        getLog().info("Number of project dependencies: " + count);
    }
}
