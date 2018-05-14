package com.hit.memory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImp;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

public class CacheUnitTest {

	@Test
	public void cacheUnitTest() throws IOException, ClassNotFoundException {

		
		String filePath;
//		IDao<Serializable, DataModel<String>> dao;
//		IAlgoCache<Long, DataModel<String>> algo;
//		CacheUnit<String> cacheUnit;
//		
//		
		filePath = "C:\\Users\\Lior\\eclipse-workspace\\CacheUnitProject\\DaoFile.txt";
		
		FileInputStream fis = new FileInputStream(filePath);
		ObjectInputStream is = new ObjectInputStream(fis);
		HashMap<Long, DataModel<String>> fileContent = (HashMap<Long, DataModel<String>>) is.readObject();
//
//		
//		dao = new DaoFileImp<String>(filePath);
//		
//		DataModel<String> dm1 = new DataModel<String>(1L, "Hello");
//		DataModel<String> dm2 = new DataModel<String>(2L, "World");
//		DataModel<String> dm3 = new DataModel<String>(3L, "My");
//		DataModel<String> dm4 = new DataModel<String>(4L, "Name");
//		DataModel<String> dm5 = new DataModel<String>(5L, "Is");
//		DataModel<String> dm6 = new DataModel<String>(6L, "Lior");
//		DataModel<String> dm7 = new DataModel<String>(7L, "Baraban");
//		
//		dao.save(dm1);
//		dao.save(dm2);
//		dao.save(dm3);
//		dao.save(dm4);
//		dao.save(dm5);
//		dao.save(dm6);
//		dao.save(dm7);
//		
////		//instantiate algo with one element from the dao
//		algo = new LRUAlgoCacheImpl<Long, DataModel<String>>(5);
//		DataModel<String> dm10 = dao.find(1L);
//		algo.putElement(dm10.getDataModelId(), dm10);
//		
//		cacheUnit = new CacheUnit<String>(algo, dao);
//		
//		Long[] idsArray = {1L,2L};
//		
//		
//		DataModel<String>[] resultArr = cacheUnit.getDataModels(idsArray);
//		
//		for(DataModel<String> item : resultArr) {
//			System.out.println(item.getDataModelId() + " " + item.getContent());
//		}
	}
	
	
//
//	//members
//	String filePath;
//	IDao<Serializable, DataModel<String>> dao;
//	IAlgoCache<Long, DataModel<String>> algo;
//	
//	CacheUnit<String> cacheUnit;
//	
//	public CacheUnitTest() throws IOException{
//		this.filePath = "C:\\Users\\Lior\\eclipse-workspace\\CacheUnitProject\\DaoFile.txt";
//		this.setIDao();
//		this.setIAlgoCache();
//		this.setCacheUnit();
//	}
//
//	private void setIDao() throws IOException {
//		// instantiate dao with 7 elements
//		this.dao = new DaoFileImp<String>(this.filePath);
//		
//		DataModel<String> dm1 = new DataModel<String>(1L, "Hello");
//		DataModel<String> dm2 = new DataModel<String>(2L, "World");
//		DataModel<String> dm3 = new DataModel<String>(3L, "My");
//		DataModel<String> dm4 = new DataModel<String>(4L, "Name");
//		DataModel<String> dm5 = new DataModel<String>(5L, "Is");
//		DataModel<String> dm6 = new DataModel<String>(6L, "Lior");
//		DataModel<String> dm7 = new DataModel<String>(7L, "Baraban");
//		
//		this.dao.save(dm1);
//		this.dao.save(dm2);
//		this.dao.save(dm3);
//		this.dao.save(dm4);
//		this.dao.save(dm5);
//		this.dao.save(dm6);
//		this.dao.save(dm7);
//	}
//
//	private void setIAlgoCache() {
//		//instantiate algo with one element from the dao
//		this.algo = new LRUAlgoCacheImpl<Long, DataModel<String>>(5);
//		DataModel<String> dm1 = this.dao.find(1L);
//		this.algo.putElement(dm1.getDataModelId(), dm1);
//	}
//	
//
//	private void setCacheUnit() {
//		this.cacheUnit = new CacheUnit<String>(this.algo, this.dao);
//	}
//	
//	@Test
//	public void getDataModelsTest() {
//		//logic
//		Long[] idsArray = {1L,2L};
//		
//		System.out.println(this.cacheUnit.getDataModels(idsArray));
//		//Assert.assertEquals("Bravo", this.cache.getElement("b"));
//	}
}
