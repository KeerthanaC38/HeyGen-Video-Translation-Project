# Instructions for Using the Client Library

## Prerequisites
1. **Java Installation**: Ensure that Java is installed on your system.
    - Minimum required version: Java 8 (or specify your required version).
    - To verify, run:
      ```bash
      java -version
      ```
    - If Java is not installed, download and install it from [Java Downloads](https://www.java.com/en/download/).

2. **Client Library Jar**: Download the provided jar file (e.g., `translation-client-0.0.1-SNAPSHOT.jar`) and save it to a directory of your choice.

## Running the Jar File
1. Open a terminal (or command prompt) and navigate to the directory where the jar file is located. For example:
   ```bash
   cd /path/to/jar-directory
   ```
2. Run the jar file using the following command:
   ```bash
   java -jar translation-client-0.0.1-SNAPSHOT.jar
   ```
3. **Configuration Options**: If the jar requires configuration or parameters (e.g., a server URL or credentials), you can pass them as command-line arguments. 
   - For example:
   ``` bash
   java -jar translation-client-0.0.1-SNAPSHOT.jar --server.url=http://localhost:8080 --api.key=your-api-key
   ```

## Logs and Output
1. The jar file will output logs or results to the console.
2. If you need to save logs to a file, you can redirect the output:
   ```bash
   java -jar translation-client-0.0.1-SNAPSHOT.jar > output.log
   ```
   
## Trouble Shooting:
1. Ensure the jar file is not corrupted.
2. Verify that Java is correctly installed. 
3. Check that all required configurations are correctly provided. 
4. Contact the support team with details of the error.