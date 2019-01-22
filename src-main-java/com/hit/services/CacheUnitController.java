
package com.hit.services;

import java.io.IOException;

import com.hit.dm.DataModel;

public class CacheUnitController<T> {

	private CacheUnitService<T> cacheUnitService;

	public CacheUnitController() throws IOException {
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

	public String getStatistics() {
		System.out.println("in controller - getStatistics");
		return this.cacheUnitService.getStatistics();
	}
}
