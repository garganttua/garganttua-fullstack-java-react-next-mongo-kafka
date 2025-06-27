package com.garganttua.noghost.backend.events;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.garganttua.events.context.json.sources.file.GGEventsContextJsonFileSource;
import com.garganttua.events.context.json.sources.file.GGEventsContextJsonResourceFileSource;
import com.garganttua.events.spec.interfaces.IGGEventsContextSource;

@Configuration
public class EventsContexts {
	
	@Value("${com.garganttua.noghost.backend.events.context.directory}")
	public String eventsContextDirectory;

	@Bean 	
	@Profile("!embeddedContext")
	public IGGEventsContextSource contextAPI() {
		GGEventsContextJsonFileSource source = new GGEventsContextJsonFileSource(this.eventsContextDirectory+File.separator+"api-events.json");
		return source;
	}
	
	@Bean 	
	@Profile("embeddedContext")
	public IGGEventsContextSource embeddedContextAPI() {
		GGEventsContextJsonResourceFileSource source = new GGEventsContextJsonResourceFileSource("api-events.json");
		return source;
	}
	
}
