package com.boletos.apirest.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.boletos.apirest"))
				.paths(PathSelectors.regex("/api.*"))
				.build()
				.apiInfo(metaInfo());
	}

	private ApiInfo metaInfo() {
		String title = "Boletos API REST";
		String description = "API REST de emissão de boletos";
		String version = "1.0";
		String termsOfServiceUrl = "Terms od Service";
		String name = "Silvio";
		String url = "google.com";
		String email = "silvio@teste.com";
		Contact contact = new Contact(name, url, email);
		String license = "Apache License Version 2.0";
		String licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0";
		@SuppressWarnings("rawtypes")
		List<VendorExtension> vendorExtensions = new ArrayList<VendorExtension>();
		
		ApiInfo apiInfo = new ApiInfo(title, description, version, termsOfServiceUrl, contact, license, licenseUrl, vendorExtensions);
		return apiInfo;
	}
}
