package com.ushi.ssm.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {
  @GetMapping("/healthz")
  public Map<String, Object> health() {
    return Map.of("ok", true);
  }
}
