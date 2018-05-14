/**
 * Class 4
 * This is a separation layer between the upper networking layer
 * and the deeper CacheUnitService layer.
 * We need to implement an api here for calling the different methods
 * of CacheUnitService.
 * 
 * summary - Have CacheUnitService as a member and call it's methods
 * from different methods in here.
 * 
 * */
package com.hit.services;

import java.io.IOException;

import com.hit.dm.DataModel;

public class CacheUnitController<T> {

	private CacheUnitService<T> cacheUnitService;

	public CacheUnitController() throws IOException {
		// TODO Auto-generated constructor stub
		this.cacheUnitService = new CacheUnitService<T>();
	}

	public boolean delete(DataModel<T>[] dataModels) throws IllegalArgumentException, IOException {
		System.out.println("in controller - delete");
		return this.cacheUnitService.delete(dataModels);
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) throws IllegalArgumentException, IOException {
		System.out.println("in controller - get");
		return this.cacheUnitService.get(dataModels);
	}

	public boolean update(DataModel<T>[] dataModels) throws IllegalArgumentException, IOException {
		System.out.println("in controller - update");
		return this.cacheUnitService.update(dataModels);
	}
}
