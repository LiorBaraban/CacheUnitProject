package com.hit.memory;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
public class CacheUnit<T> {

	private IAlgoCache<Long, DataModel<T>> algoCache;
	private IDao<Serializable, DataModel<T>> dao;
	
	public CacheUnit(com.hit.algorithm.IAlgoCache<Long, DataModel<T>> algo, IDao<java.io.Serializable, DataModel<T>> dao){
		this.algoCache = algo;
		this.dao = dao;
	}
	
	@SuppressWarnings("unchecked")
	//throws classnotfoundexception, ioexceptions
	public DataModel<T>[] getDataModels(java.lang.Long[] ids) throws IllegalArgumentException, IOException{
		//DataModel<T>[] dataModels = (DataModel<T>[]) new Object[ids.length];
		//DataModel<T>[] dataModels = new Datamodel<T>[ids.length];
		DataModel<T> modelPrototype = new DataModel<T>(0L,null);
		DataModel<T>[] dataModels = (DataModel<T>[])Array.newInstance(modelPrototype.getClass(), ids.length);
		for(int i=0;i<ids.length;i++){
			Long id = ids[i];
			// get the datamodel from cache
			DataModel<T> dataModel = this.algoCache.getElement(id);
			if (dataModel == null) {
				dataModel = this.dao.find(id);
				DataModel<T> ejectedDataModel = this.algoCache.putElement(dataModel.getDataModelId(), dataModel);
				if (ejectedDataModel!= null) {
					//algo is full
					this.dao.save(ejectedDataModel);
				} 
			}
			dataModels[i] = dataModel;
		}
		return dataModels;
		
		// do if IAlgoCache not subset ids	// only touch datamodels in case don't exist
			// if cache is not null
				//retrieve the page by the dao // dm fault
			// else
				//do logic of full cache
		// end
		
		// return dataModels[] // from cache
	}
	
}
