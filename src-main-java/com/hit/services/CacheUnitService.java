/**
 * Class 5 - This class only workds with the Request object
 * There are no networking/ communication objects in it at all
 * (no socket / inputstreams/outpustreams)
 * This class should take the request and work with its content
 * and pass it to the memory management unit 
 * 
 * members - memory unit from ex 2
 **/
package com.hit.services;

import java.io.IOException;
import java.io.Serializable;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImp;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

public class CacheUnitService<T> {
	
	static final String FILE_PATH = "C:\\User\\Lior\\eclipse-workspace\\CacheUnitProject\\DaoFile.txt";
	
	private CacheUnit<T> cacheUnit;
	
	public CacheUnitService() throws IOException {
		
		// TODO Change hard coded LRU to optional to by choice
		// TODO Change hard coded capacity to by choice 
		
		IAlgoCache<Long, DataModel<T>> algo = new LRUAlgoCacheImpl<Long, DataModel<T>>(5);
		IDao<Serializable, DataModel<T>> dao = new DaoFileImp<T>(FILE_PATH);
		
		this.cacheUnit = new CacheUnit<T>(algo, dao);
	}
	
	public boolean update(DataModel<T>[] dataModels) {
		//TODO
	}
	
	public boolean delete(DataModel<T>[] dataModels) {
		//TODO
	}
	
	public DataModel<T>[] get(DataModel<T>[] dataModels){
		//TODO
	}
	
}
