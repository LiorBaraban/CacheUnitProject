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

import com.hit.dm.DataModel;

public class CacheUnitController<T>{

	public CacheUnitController() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean delete(DataModel<T>[] dataModels) {
		//TODO
		System.out.println("in controller - delete");
		return false;
	}
	
	public DataModel<T>[] get(DataModel<T>[] dataModels){
		//TODO
		System.out.println("in controller - get");
		return null;
	}
	public boolean update(DataModel<T>[] dataModels) {
		//TODO
		System.out.println("in controller - update");
		return false;
	}
}
