package com.hit.dao;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import com.google.gson.Gson;
import com.hit.dm.DataModel;

public class DaoFileImp<T> implements IDao<Serializable, DataModel<T>> {

	private String filePath;
	private HashMap<Long, DataModel<T>> fileContent;
	// private FileInputStream fis;
	// private ObjectInputStream ois;
	// private FileOutputStream fos;
	// private ObjectOutputStream oos;

	public DaoFileImp(java.lang.String filePath) throws IOException {
		this.filePath = filePath;

		// "C:\\Users\\Lior\\eclipse-workspace\\CacheUnitProject\\DaoFile.txt"\

		// this.fos = new FileOutputStream(this.filePath);
		// this.oos = new ObjectOutputStream(this.fos);
		//
		// this.oos.writeObject(new HashMap<Long, DataModel<T>>());
		//
		// this.fis = new FileInputStream(this.filePath);
		// this.ois = new ObjectInputStream(this.fis);

		FileOutputStream fos = new FileOutputStream(this.filePath);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(new HashMap<Long, DataModel<T>>());
		os.close();
		fos.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(DataModel<T> entity) throws IOException {
		FileInputStream fis = new FileInputStream(this.filePath);
		ObjectInputStream is = new ObjectInputStream(fis);
		FileOutputStream fos = null;
		ObjectOutputStream os = null;	
		try {
			if (fis.available() != 0) {
				this.fileContent = (HashMap<Long, DataModel<T>>) is.readObject();
				if (this.fileContent != null) {
					for (Long key : this.fileContent.keySet()) {
						DataModel<T> value = (DataModel<T>) this.fileContent.get(key);
						if (entity == value) {
							this.fileContent.remove(key);
							break;
						}
					}
					os.writeObject(this.fileContent);
				} else {
					throw new NullPointerException("File reading returned null");
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
			if (fis != null) {
				fis.close();
			}
			if (os != null) {
				os.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel<T> find(Serializable id) throws java.lang.IllegalArgumentException, FileNotFoundException, IOException {
		DataModel<T> value = null;
		FileInputStream fis = new FileInputStream(this.filePath);
		ObjectInputStream is = new ObjectInputStream(fis);
		FileOutputStream fos = null;
		ObjectOutputStream os = null ;
//		FileOutputStream fos = new FileOutputStream(this.filePath);
//		ObjectOutputStream os = new ObjectOutputStream(fos);
		try {
			if (fis.available() != 0){
				this.fileContent = (HashMap<Long, DataModel<T>>) is.readObject();
			}
			else {
				this.fileContent = new HashMap<Long, DataModel<T>>();
			}
			is.close();
			fis.close();
			
			if (this.fileContent.containsKey(id)) {
				value = this.fileContent.get(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
			if (fis != null) {
				fis.close();
			}
			if (os != null) {
				os.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
//	public void save(DataModel<T> entity) throws IOException {
//		
//		FileInputStream fis = new FileInputStream(this.filePath);
////		FileOutputStream fos = new FileOutputStream(this.filePath);
////		ObjectInputStream is = null;
////		ObjectOutputStream os = null;
//		try {
////			is = new ObjectInputStream(fis);
//			Gson gs = new Gson();
//			if (fis.available() != 0) {
//				 byte[] arr = fis.readAllBytes();
//				 String s = new String(arr);
//				 this.fileContent = gs.fromJson(s, new HashMap<Long, DataModel<T>>().getClass());
//			}
//			else {
//				this.fileContent = new HashMap<Long, DataModel<T>>();
//			}
//
//			this.fileContent.put(entity.getDataModelId(), entity);
////			fos.write(gs.toJson(this.fileContent).getBytes());
//		}catch (EOFException e) {
//			e.printStackTrace();
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			fis.close();
////			fos.close();
//		}
//
//	}
	
public void save(DataModel<T> entity) throws IOException {
		
		FileInputStream fis = new FileInputStream(this.filePath);
		ObjectInputStream is = new ObjectInputStream(fis);
		FileOutputStream fos = null;
		ObjectOutputStream os = null;	
		try {
			if (fis.available() != 0) {
			 this.fileContent =	(HashMap<Long, DataModel<T>>) is.readObject();
			}
			else {
				this.fileContent = new HashMap<Long, DataModel<T>>();
			}
			is.close();
			fis.close();

			this.fileContent.put(entity.getDataModelId(), entity);
			fos = new FileOutputStream(this.filePath);
			os = new ObjectOutputStream(fos);
			os.writeObject(this.fileContent);
		}catch (EOFException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
			if (fis != null) {
				fis.close();
			}
			if (os != null) {
				os.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}

}
