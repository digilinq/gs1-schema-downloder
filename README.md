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
    <version>1.0.3-SNAPSHOT</version>
    <executions>
        <execution>
            <goals>
                <goal>download-gs1-schema</goal>
            </goals>
            <configuration>
                <gs1SchemaUrl>http://www.gdsregistry.org/3.1/schemas/</gs1SchemaUrl>
                <outputDirectory>${project.basedir}/src/main/resources/gs1-schema</outputDirectory>
            </configuration>
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

## Debugging Maven Plugin
```shell
mvnDebug com.eightbits.commerce.retail:gs1-schema-downloader:download-gs1-schema
```
If you are building custom plugins, you need to be able to debug them. It’s perhaps not always immediately clear how to do this. After all, developers don’t simply start a Mojo via the IDE; they have to connect to the actual mvn command in some way. Luckily Maven has a default solution for this, namely the mvnDebug command. This command suspends execution right after invocation so you can attach a remote debugger to a port. By default, that port is 8000, as shown the following output:

```text
Preparing to execute Maven in debug mode
Listening for transport dt_socket at address: 8000
``` 
