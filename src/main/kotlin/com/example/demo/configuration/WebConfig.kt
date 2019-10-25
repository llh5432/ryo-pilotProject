package com.example.demo.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.config.WebFluxConfigurerComposite
import org.springframework.web.reactive.function.client.WebClient

@Configuration //configuration 어노테이션을 선언하면
class WebConfig {

    @Bean // 이 프로젝트의 전역에 이 함수를 선언함, spring 기능
    fun corsConfig(): WebFluxConfigurer {
        return object : WebFluxConfigurerComposite() {

            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("*")
                // allowedOrigins : 요청을 보내는 페이지의 출처(*, 도메인)
                // allowedMethods : 요청을 허용하는 메소드 (GET, POST, HEAD..)
            }
        }
    }
}