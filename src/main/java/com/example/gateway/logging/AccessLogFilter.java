package com.example.gateway.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;

@Component
public class AccessLogFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger("ACCESS");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws ServletException, IOException {
        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper(response);
        long start = System.currentTimeMillis();
        try {
            chain.doFilter(req, res);
        } finally {
            long duration = System.currentTimeMillis() - start;
            String traceId = org.slf4j.MDC.get("traceId");
            log.info("HTTP {} {} -> status={} timeMs={} trace={}",
                request.getMethod(), request.getRequestURI(),
                res.getStatus(), duration, traceId);
            String auth = request.getHeader("Authorization");
            if (auth != null && auth.startsWith("Bearer ")) {
                String masked = auth.substring(0, 15) + "..." + auth.substring(auth.length() - 6);
                log.debug("Authorization: {}", masked);
            }
            res.copyBodyToResponse();
        }
    }
}