package com.eightbits.commerce.retail;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;

@Mojo(name = "download-gs1-schema", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GS1SchemaDownloader extends AbstractMojo {

    @Parameter(property = "download.url", defaultValue = "http://www.gdsregistry.org/3.1/schemas/")
    private String url;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Downloading GS1 schema...");
    }
}
