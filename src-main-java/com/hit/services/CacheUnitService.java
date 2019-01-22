
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

	private CacheUnit<T> cacheUnit;
	private IAlgoCache<Long, DataModel<T>> algo;
	private IDao<Serializable, DataModel<T>> dao; 
	private int capacity;
	private int numOfRequests;
	private int numOfDataModels;
	
	public CacheUnitService() throws IOException {
		String filePath = "DaoFile.txt";
		// the above is a relative path to:
		//"C:\\Users\\Lior\\eclipse-workspace\\CacheUnitProject\\DaoFile.txt";

		this.capacity = 5;
		this.numOfRequests = 0;
		this.numOfDataModels = 0;
		
		this.algo = new LRUAlgoCacheImpl<Long, DataModel<T>>(this.capacity);
		this.dao = new DaoFileImp<T>(filePath);

		this.cacheUnit = new CacheUnit<>(algo, dao);
	}

	public boolean update(DataModel<T>[] dataModels) throws IllegalArgumentException, IOException {
		this.numOfRequests ++;
		Long[] ids = this.makeDataModelIdsArray(dataModels);

		DataModel<T>[] resultArray = this.cacheUnit.getDataModels(ids);
		try {
			if (resultArray != null) {
				this.numOfDataModels += resultArray.length;
				System.out.println("");
				System.out.println("before update");
				System.out.println("=============");
				for (int i = 0; i < resultArray.length; i++) {
					System.out.println(dataModels[i].getDataModelId() + " " + dataModels[i].getContent());
				}
				for (int i = 0; i < resultArray.length; i++) {
					resultArray[i].setContent(dataModels[i].getContent());
				}
				System.out.println("");
				System.out.println("after update:");
				System.out.println("=============");
				for (int i = 0; i < resultArray.length; i++) {
					System.out.println(resultArray[i].getDataModelId() + " " + resultArray[i].getContent());
				}
				return true;
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean delete(DataModel<T>[] dataModels) throws IllegalArgumentException, IOException {
		this.numOfRequests ++;
		Long[] ids = this.makeDataModelIdsArray(dataModels);

		DataModel<T>[] resultArray = this.cacheUnit.getDataModels(ids);
		try {
			if (resultArray != null) {
				System.out.println("");
				System.out.println("deleting the following DMs:");
				System.out.println("===========================");
				for (int i = 0; i < resultArray.length; i++) {
					System.out.println(resultArray[i].getDataModelId() + " " + resultArray[i].getContent());
					resultArray[i].setContent(null);
				}
				return true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) throws IllegalArgumentException, IOException {
		this.numOfRequests ++;
		Long[] ids = this.makeDataModelIdsArray(dataModels);

		DataModel<T>[] resultArray = this.cacheUnit.getDataModels(ids);
		this.numOfDataModels += resultArray.length;
		System.out.println("");
		System.out.println("retrieved the following DMs:");
		System.out.println("============================");
		for (int i = 0; i < resultArray.length; i++) {
			System.out.println(resultArray[i].getDataModelId() + " " + resultArray[i].getContent());
		}
		
		return resultArray;

	}

	private Long[] makeDataModelIdsArray(DataModel<T>[] dataModels) {

		Long[] ids = new Long[dataModels.length];

		for (int i = 0; i < dataModels.length; i++) {
			ids[i] = dataModels[i].getDataModelId();
		}

		return ids;

	}

	public String getStatistics() {
		
		String algoName = this.algo.getClass().getSimpleName(); 
		
		
				
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("Capacity: " + this.capacity);
		sb.append("<br>");
		sb.append("Algorithm: "+ algoName);
		sb.append("<br>");
		sb.append("Total number of requests: " + this.numOfRequests);
		sb.append("<br>");
		sb.append("Total number of DataModels (GET/DELETE/UPDATE requests): "+ this.numOfDataModels);
		sb.append("<br>");
		sb.append("Total number of DataModel swaps (from Cache to Disk): " + this.cacheUnit.getSwapCount());
		sb.append("</html>");
		
		// Note - the reason we used html syntax is because that's the only way the clients' label that displays the text can be multi - lined.
		// Otherwise, if we would use "\n" to seperate the lines, then only the first line (the text before the first \n) will be displayed.
		// for more information see https://stackoverflow.com/questions/685521/multiline-text-in-jlabel
		
		System.out.println("sb message - " + sb.toString());
		
		return sb.toString(); 		
	}


}
