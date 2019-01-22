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

	public DaoFileImp(java.lang.String filePath) throws IOException {
		this.filePath = filePath;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(DataModel<T> entity) throws IOException {
		FileInputStream fis = null;
		ObjectInputStream is = null;
		FileOutputStream fos = null;
		ObjectOutputStream os = null;

		try {
			fis = new FileInputStream(this.filePath);
			is = new ObjectInputStream(fis);
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
	public DataModel<T> find(Serializable id)
			throws java.lang.IllegalArgumentException, FileNotFoundException, IOException {
		DataModel<T> value = null;
		FileInputStream fis = null;
		ObjectInputStream is = null;
		FileOutputStream fos = null;
		ObjectOutputStream os = null;
		try {
			fis = new FileInputStream(this.filePath);
			is = new ObjectInputStream(fis);
			if (fis.available() != 0) {
				this.fileContent = (HashMap<Long, DataModel<T>>) is.readObject();
			} else {
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
	public void save(DataModel<T> entity) throws IOException {

		FileInputStream fis = null;
		ObjectInputStream is = null;
		FileOutputStream fos = null;
		ObjectOutputStream os = null;
		try {
			fis = new FileInputStream(this.filePath);
			is = new ObjectInputStream(fis);
			if (fis.available() != 0) {
				this.fileContent = (HashMap<Long, DataModel<T>>) is.readObject();
			} else {
				this.fileContent = new HashMap<Long, DataModel<T>>();
			}
			is.close();
			fis.close();

			this.fileContent.put(entity.getDataModelId(), entity);
			fos = new FileOutputStream(this.filePath);
			os = new ObjectOutputStream(fos);
			os.writeObject(this.fileContent);
		} catch (EOFException e) {
			e.printStackTrace();
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
	}

}
