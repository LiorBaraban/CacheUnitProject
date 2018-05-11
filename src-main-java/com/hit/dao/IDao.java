package com.hit.dao;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IDao<ID extends java.io.Serializable, T> {
	// saves a given entity
	void save(T entity) throws IOException;
	
	// deletes a given entity
	// implement throw when T is null
	void delete(T entity) throws java.lang.IllegalArgumentException, IOException;

	// retrieves an entity by its id or null if entity not found
	// implement throw when id is null
	T find(ID id) throws java.lang.IllegalArgumentException, FileNotFoundException, IOException;

}
