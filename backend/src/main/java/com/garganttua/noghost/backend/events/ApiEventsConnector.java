package com.garganttua.noghost.backend.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garganttua.events.spec.annotations.GGEventsConnector;
import com.garganttua.events.spec.exceptions.GGEventsConnectorException;
import com.garganttua.events.spec.exceptions.GGEventsException;
import com.garganttua.events.spec.exceptions.GGEventsHandlingException;
import com.garganttua.events.spec.interfaces.IGGEventsConnector;
import com.garganttua.events.spec.interfaces.IGGEventsEngine;
import com.garganttua.events.spec.interfaces.IGGEventsMessageHandler;
import com.garganttua.events.spec.interfaces.IGGEventsObjectRegistryHub;
import com.garganttua.events.spec.interfaces.context.IGGEventsContextSubscription;
import com.garganttua.events.spec.objects.GGEventsConfigurationDecoder;
import com.garganttua.events.spec.objects.GGEventsConnectorConsumerRegistrationRequest;
import com.garganttua.events.spec.objects.GGEventsConnectorProducerRegistrationRequest;
import com.garganttua.events.spec.objects.GGEventsContextObjDescriptor;
import com.garganttua.events.spec.objects.GGEventsExchange;
import com.garganttua.events.spec.objects.GGEventsJourneyStep;
import com.garganttua.events.spec.objects.GGEventsMessage;

import lombok.Getter;
import lombok.Setter;

@GGEventsConnector(type = "aec", version = "1.0")
public class ApiEventsConnector implements IGGEventsConnector {

	@Getter
	@Setter
	private String name;
	private ExecutorService poolExecutor;
	private String configuration;
	private String tenantId;
	private String clusterId;
	private String assetId;
	private IGGEventsObjectRegistryHub objectRegistries;
	private IGGEventsEngine engine;

	private Map<String, Map<IGGEventsContextSubscription, IGGEventsMessageHandler>> handlers = new HashMap<String, Map<IGGEventsContextSubscription, IGGEventsMessageHandler>>();
	private List<String> providers__;

	@Override
	public boolean handle(GGEventsExchange exchange) {
		return true;
	}

	@Override
	public void applyConfiguration() throws GGEventsException {
	}

	@Override
	public String getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(String configuration, String tenantId, String clusterId, String assetId,
			IGGEventsObjectRegistryHub objectRegistries, IGGEventsEngine engine) throws GGEventsException {
		this.configuration = configuration;
		this.tenantId = tenantId;
		this.clusterId = clusterId;
		this.assetId = assetId;
		this.objectRegistries = objectRegistries;
		this.engine = engine;
		
		Map<String, List<String>> __configuration__ = GGEventsConfigurationDecoder.getConfigurationFromString(this.configuration);
		__configuration__.forEach((k, v) -> {
			if (k.equals("events_providers")) {
				this.providers__ = List.of( v.get(0).split(";") );
			}
		});

		if( this.providers__ != null ) {
			this.providers__.forEach(s -> {
				try {
					this.handlers.put("/"+s.split(":")[1].toLowerCase(), new HashMap<IGGEventsContextSubscription, IGGEventsMessageHandler>());
					EventsPublisher binder = (EventsPublisher) this.objectRegistries.getObject(s);
					binder.setConnector(this);
				} catch (GGEventsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
	}

	@Override
	public GGEventsContextObjDescriptor getDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerConsumer(GGEventsConnectorConsumerRegistrationRequest request) {
		
		Map<IGGEventsContextSubscription, IGGEventsMessageHandler> subscriptions = this.handlers.get(request.subscription().getTopic());
		if( subscriptions != null ) {
			subscriptions.put(request.subscription(), request.messageHandler());
		}
		
	}

	@Override
	public void setPoolExecutor(ExecutorService arg0) {
		this.poolExecutor = arg0;
	}

	@Override
	public void start() throws GGEventsConnectorException {

	}

	@Override
	public void stop() throws GGEventsConnectorException {

	}

	public void handleEvent(String tenantId, String topic, byte[] event) {
		ObjectMapper mapper = new ObjectMapper();
		Map<IGGEventsContextSubscription, IGGEventsMessageHandler> sub = this.handlers.get(topic);
		if( sub != null ) {
			sub.forEach((subscription, handler) -> {

				GGEventsMessage m = new GGEventsMessage(
						new HashMap<String, String>(), 
						UUID.randomUUID().toString(), 
						UUID.randomUUID().toString(), 
						new ArrayList<GGEventsJourneyStep>(), 
						tenantId, 
						event, 
						"application/json", 
						null, 
						null, 
						"1.0");
				
				try {
					byte[] messageBytes = mapper.writeValueAsBytes(m);

					this.poolExecutor.execute(new Runnable() {
						
						@Override
						public void run() {
							try {
								handler.handle(GGEventsExchange.emptyExchange(name, topic, subscription.getDataflow(), messageBytes));
							} catch (GGEventsHandlingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					
					
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}	
			});
		}
	}

	@Override
	public void registerProducer(GGEventsConnectorProducerRegistrationRequest request) {
		// TODO Auto-generated method stub
		
	}
}
