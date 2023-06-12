package baemin.baeminjpa.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        Info info = new Info().version("v1.0.0").title("배달의 민족-JPA API")
                .description("JDBC to JPA 변환");
        return new OpenAPI().info(info);
    }
}
