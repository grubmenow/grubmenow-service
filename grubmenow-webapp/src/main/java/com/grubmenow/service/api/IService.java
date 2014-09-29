package com.grubmenow.service.api;

public interface IService <T, R> {

	public R executeService(T request);
	
	public Class<T> getRequestClass();
}
