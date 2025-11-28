package util;

import java.util.Calendar;
import java.util.List;

public class Config {

  private static Config config;

  boolean overrideEnabled;

  int day;
  int year;

  boolean inputWeb;
  String webCookie;
  String url;

  String dir;

  private Config() {
    overrideEnabled = false;
    day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    year = Calendar.getInstance().get(Calendar.YEAR);

    inputWeb = true;
    webCookie = "";

    getConfig();

    url = "https://adventofcode.com/" + year + "/day/" + day + "/input";
    dir = "src/main/resources/input/" + year + "/day" + day + ".txt";
  }

  public static Config getInstance() {
    if (config == null) {
      config = new Config();
    }
    return config;
  }

  private void getConfig() {
    List<String> list = IO.readFileInput("config.properties");

    for (String line : list) {
      if (line.startsWith("#") || !line.contains(" = ")) {
        continue;
      }

      String[] keyValue = line.split(" = ");
      String key = keyValue[0];
      String value = keyValue[1];

      if (key.equals("input.web")) {
        inputWeb = Boolean.parseBoolean(value);
      } else if (key.equals("web.cookie")) {
        webCookie = value;
      } else if (key.equals("override.enabled")) {
        overrideEnabled = Boolean.parseBoolean(value);
      } else if (key.equals("override.day") && overrideEnabled) {
        day = Integer.parseInt(value);
      } else if (key.equals("override.year") && overrideEnabled) {
        year = Integer.parseInt(value);
      }
    }
  }

}
