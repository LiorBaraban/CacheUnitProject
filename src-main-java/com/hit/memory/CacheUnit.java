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
	
	private int swapCounter;
	
	public CacheUnit(com.hit.algorithm.IAlgoCache<Long, DataModel<T>> algo, IDao<java.io.Serializable, DataModel<T>> dao){
		this.algoCache = algo;
		this.dao = dao;
		this.swapCounter = 0;
	}
	
	@SuppressWarnings("unchecked")
	public DataModel<T>[] getDataModels(java.lang.Long[] ids) throws IllegalArgumentException, IOException{
		ArrayList<DataModel<T>> dataModels = new ArrayList<DataModel<T>>();
		for(int i=0;i<ids.length;i++){
			Long id = ids[i];
			// get the data model from cache
			DataModel<T> dataModel = this.algoCache.getElement(id);
			if (dataModel == null) {
				dataModel = this.dao.find(id);
				DataModel<T> ejectedDataModel = this.algoCache.putElement(dataModel.getDataModelId(), dataModel);
				if (ejectedDataModel!= null) {
					// algo is full
					this.dao.save(ejectedDataModel);
					this.swapCounter ++;
				} 
			}
			dataModels.add(dataModel);
		}
		DataModel<T>[] array = new DataModel[dataModels.size()];
		return dataModels.toArray(array);
	}
	
	public int getSwapCount() {
		return this.swapCounter;
	}
}
