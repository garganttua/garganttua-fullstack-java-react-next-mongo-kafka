package com.garganttua.noghost.backend.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garganttua.events.spec.exceptions.GGEventsException;
import com.garganttua.events.spring.IGGEventsEngineSpringBean;

import jakarta.annotation.PostConstruct;

@Service
public class GarganttuaEventsRunner {
	
	@Autowired
	private IGGEventsEngineSpringBean engineService;
	
	@PostConstruct
	private void init() throws GGEventsException {
		this.engineService.init();
		this.engineService.start();
	}
}
