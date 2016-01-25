package com.rawsanj.blogaggr.exception;

public class RssException extends Exception {

	public RssException(Throwable cause) {
		super(cause);
		System.out.println("RssException is Thrown");
	}
	
}
