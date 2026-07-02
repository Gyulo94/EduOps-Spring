package com.eduops.server.global.config;

import javax.net.ssl.SSLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.eduops.server.global.constants.Constants;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.RequiredArgsConstructor;
import reactor.netty.http.client.HttpClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final Constants constants;

    @Bean
    @SuppressWarnings("null")
    public WebClient emailWebClient(WebClient.Builder builder) throws SSLException {
        HttpClient httpClient = createInsecureHttpClient();

        return builder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(constants.getEmailUrl())
                .build();
    }

    private HttpClient createInsecureHttpClient() throws SSLException {
        SslContext sslContext = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

        return HttpClient.create()
                .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));
    }
}