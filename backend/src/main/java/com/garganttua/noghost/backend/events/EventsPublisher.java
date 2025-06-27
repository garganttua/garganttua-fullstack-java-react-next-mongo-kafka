package com.garganttua.noghost.backend.events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garganttua.api.spec.domain.IGGAPIDomain;
import com.garganttua.api.spec.engine.IGGAPIEngine;
import com.garganttua.api.spec.event.IGGAPIEvent;
import com.garganttua.api.spec.event.IGGAPIEventPublisher;

@Service(value = "EventsPublisher")
public class EventsPublisher implements IGGAPIEventPublisher {

	@Value("${com.garganttua.noghost.backend.superTenantId:0}")
	private static final String SUPER_TENANT_ID = "0";
	
	private ApiEventsConnector connector;

	@Override
	public void publishEvent(IGGAPIEvent event) {
		ObjectMapper mapper = new ObjectMapper();
		byte[] event__;
		try {
			event__ = mapper.writeValueAsBytes(event);
			this.connector.handleEvent(event.getTenantId()==null?SUPER_TENANT_ID:event.getTenantId(), "/eventspublisher", event__);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setConnector(ApiEventsConnector apiEventsConnector) {
		this.connector = apiEventsConnector;	
	}

	@Override
	public void setEngine(IGGAPIEngine engine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDomain(IGGAPIDomain domain) {
		// TODO Auto-generated method stub
		
	}

}
