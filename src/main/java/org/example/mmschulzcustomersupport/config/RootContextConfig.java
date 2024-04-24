package org.example.mmschulzcustomersupport.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = "org.example.mmschulzcustomersupport.site",
excludeFilters = @ComponentScan.Filter(Controller.class))
public class RootContextConfig {
}
