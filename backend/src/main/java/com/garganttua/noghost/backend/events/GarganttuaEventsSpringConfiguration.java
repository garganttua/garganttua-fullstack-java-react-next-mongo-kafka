package com.garganttua.noghost.backend.events;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GarganttuaEventsSpringConfiguration {
	
	@Bean 
	@Qualifier(value = "packages")
	public String packages() {
		return "com.garganttua";
	}
	
}