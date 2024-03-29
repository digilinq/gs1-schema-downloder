# GS1 Schema Downloader Maven Plugin 


## GS1 

[GS1 Company Prefix](https://www.gs1.org/standards/id-keys/company-prefix) 
is a unique number assigned by GS1 to identify a company. The GS1 Company Prefix provides a way to uniquely identify a company in the GS1 system. 
The GS1 Company Prefix is used to create Global Trade Item Numbers (GTINs) and GS1 Identification Keys such as Global Location Numbers (GLNs) 
and Serial Shipping Container Codes (SSCCs).

## Run the plugin locally
```shell
mvn com.eightbits.commerce.retail:gs1-schema-downloader:download-gs1-schema
```
### Configuration
```shell
mvn com.eightbits.commerce.retail:gs1-schema-downloader:download-gs1-schema -Dgs1SchemaUrl=https://www.gs1.org/voc/ -DoutputDirectory=target/generated-resources/gs1-schema
```

## Run the plugin in a project
```xml
<plugin>
    <groupId>com.eightbits.commerce.retail</groupId>
    <artifactId>gs1-schema-downloader</artifactId>
    <version>1.0-SNAPSHOT</version>
    <executions>
        <execution>
            <id>download-gs1-schema</id>
            <phase>generate-resources</phase>
            <goals>
                <goal>download-gs1-schema</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Configuration
```xml
<configuration>
    <gs1SchemaUrl>https://www.gs1.org/voc/</gs1SchemaUrl>
    <outputDirectory>${project.build.directory}/generated-resources/gs1-schema</outputDirectory>
</configuration>
```

## Output
The plugin will download the GS1 schema from the provided URL and save it in the output directory. The schema will be saved in the following format:
```
outputDirectory
├── gs1-schema.json
``` 
