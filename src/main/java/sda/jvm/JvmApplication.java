package sda.jvm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JvmApplication {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public JvmApplication() {

    log.info("ClassLoader of DataModel: {}", DataModel.class.getClassLoader());
    log.info("ClassLoader of String: {}", String.class.getClassLoader());

    DataModel dm = new DataModel(1, "");
  }

  public static void main(String[] args) {
    SpringApplication.run(JvmApplication.class, args);
  }
}
