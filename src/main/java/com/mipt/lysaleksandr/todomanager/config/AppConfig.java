package com.mipt.lysaleksandr.todomanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;
import java.util.UUID;

@Configuration
public class AppConfig {

    @Bean
    @RequestScope
    public RequestScopedBean requestScopedBean() {
        return new RequestScopedBean(UUID.randomUUID().toString(), System.currentTimeMillis());
    }

    public static class RequestScopedBean {

        private final String requestId;
        private final long startTime;

        public RequestScopedBean(String requestId, long startTime) {
            this.requestId = requestId;
            this.startTime = startTime;
        }

        public String getRequestId() {
            return requestId;
        }

        public long getStartTime() {
            return startTime;
        }
    }

    @Bean
    @Scope("prototype")
    public PrototypeScopedBean prototypeScopedBean() {
        return new PrototypeScopedBean();
    }

    public static class PrototypeScopedBean {

        public String generateId() {
            return UUID.randomUUID().toString();
        }
    }
}