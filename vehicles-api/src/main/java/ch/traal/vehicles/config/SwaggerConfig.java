package ch.traal.vehicles.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  
  
  /* member variables */
  
  
  /* constructors */
  
  
  /* methods */
  @Bean
  public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2)
              .select()
              .apis(RequestHandlerSelectors.any())
              .paths(PathSelectors.any())
              .build()
              .useDefaultResponseMessages(false);
  }

  private ApiInfo apiInfo() {
      return new ApiInfo(
              "Vehicles API",
              "This API manages vehicles with a maps and pricing service.",
              "1.0",
              "http://www.udacity.com/tos",
              new Contact("Traal Devel", "www.traal.ch", "devel@traal.ch"),
              "License of API", "http://www.udacity.com/license", Collections.emptyList());
  }

}
