package com.garganttua.noghost.backend.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garganttua.events.spec.exceptions.GGEventsException;
import com.garganttua.events.spring.IGGEventsEngineSpringBean;

import jakarta.annotation.PostConstruct;

@Service
public class EventsConfiguration {

	@Autowired
	private IGGEventsEngineSpringBean engine;
	
	@PostConstruct
	private void init() throws GGEventsException {
		this.engine.init();		
		this.engine.start();
	}
}