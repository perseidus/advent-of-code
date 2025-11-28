package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class IO {

  public static List<String> getInput() {
    Config config = Config.getInstance();

    if (config.inputWeb) {
      return readUrlInput();
    } else {
      return readFileInput();
    }
  }

  public static List<String> readFileInput() {
    return readFileInput(Config.getInstance().dir);
  }

  public static List<String> readFileInput(String fileName) {
    List<String> lines = new LinkedList<>();

    try (FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr)) {

      String line = br.readLine();
      while (line != null) {
        lines.add(line);
        line = br.readLine();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return lines;
  }

  public static List<String> readUrlInput() {
    Config config = Config.getInstance();
    return readUrlInput(config.url, config.webCookie);
  }

  public static List<String> readUrlInput(String url, String cookie) {
    HttpClient client = HttpClient.newHttpClient();

    cookie = "session=" + cookie + ";";

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("Cookie", cookie)
        .build();

    HttpResponse<String> response;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }

    List<String> lines = new LinkedList<>();
    Collections.addAll(lines, response.body().split("\n"));

    saveInput(lines);

    return lines;
  }

  public static void saveInput(List<String> lines) {
    saveInput(Config.getInstance().dir, lines);
  }

  public static void saveInput(String fileName, List<String> lines) {
    Path path = Paths.get(fileName);
    try {
      Files.createDirectories(path.getParent());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try (FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw)) {

      for (String line : lines) {
        bw.write(line + "\n");
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    readUrlInput();
  }
}
