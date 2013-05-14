package com.gzeport.app.gps.dwr;

import org.springframework.context.ApplicationEvent;

public class VehicleInfoEvent extends ApplicationEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 

	public VehicleInfoEvent(Object source) {
		super(source);
	}

}
