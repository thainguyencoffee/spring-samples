package com.manning.sbip;

import com.manning.sbip.model.ReleaseItem;
import com.manning.sbip.model.ReleaseNote;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@SpringBootApplication
public class CourcesTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourcesTrackerApplication.class, args);
	}

	@Bean
	MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
		return registry -> registry.config().commonTags("application", "coursetracker");
	}

	@Bean(name = "releaseNotes")
	public Collection<ReleaseNote> loadReleaseNotes () {
		Set<ReleaseNote> releaseNotes = new LinkedHashSet<>();
		ReleaseNote releaseNote1 = ReleaseNote.builder()
				.version("v.1.2.1")
				.releaseDate(LocalDate.of(2019, 02, 21))
				.commitTag("a7d2ea3")
				.newReleases(Set.of(getReleaseItem("SBIP-125", "Support both kebab-case and camelCase as Spring init CLI Options #28138")))
				.bugFixes(Set.of(getReleaseItem("SBIP-126", "Profiles added using @ActiveProfiles have different precedence #28724")))
				.build();
		releaseNotes.addAll(Set.of(releaseNote1));
		return releaseNotes;
	}

	private ReleaseItem getReleaseItem(String itemId, String itemDescription) {
		return ReleaseItem
				.builder()
				.itemId(itemId)
				.itemDescription(itemDescription)
				.build();
	}

}
