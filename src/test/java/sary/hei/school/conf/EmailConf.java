package sary.hei.school.conf;

import org.springframework.test.context.DynamicPropertyRegistry;
import sary.hei.school.PojaGenerated;

@PojaGenerated
public class EmailConf {

  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("aws.ses.source", () -> "dummy-ses-source");
  }
}
