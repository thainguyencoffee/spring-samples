package com.manning.sbip.ch04.metrics;

import com.manning.sbip.ch04.service.CourseService;
import io.micrometer.core.instrument.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CourseTrackerMetricsConfiguration {

    @Bean
    public Counter createCourseCounter(MeterRegistry meterRegistry) {
        return Counter.builder("api.courses.created.count")
                .description("Total number of courses created")
                .register(meterRegistry);
    }

    @Bean
    public Gauge createCourseGauge(MeterRegistry meterRegistry, CourseService courseService) {
        return Gauge.builder("api.courses.created.gauge", courseService::count)
                .description("Total course available")
                .register(meterRegistry);
    }

    @Bean
    public Timer createCourseTimer(MeterRegistry meterRegistry) {
        return Timer.builder("api.courses.creation.time")
                .description("Course creation time")
                .register(meterRegistry);
    }

    @Bean
    public DistributionSummary createDistributionSummary(MeterRegistry meterRegistry) {
        return DistributionSummary
                .builder("api.courses.rating.distribution.summary")
                .description("Rating distribution summary")
                .register(meterRegistry);
    }
}
