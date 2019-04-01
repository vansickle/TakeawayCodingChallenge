package com.samokha.takeaway.eventservice;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
	}
}
