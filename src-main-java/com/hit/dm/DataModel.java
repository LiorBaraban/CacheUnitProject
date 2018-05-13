package com.hit.dm;

import java.io.Serializable;

public class DataModel<T> implements Serializable{

	/**
	 * 
	 */
	private java.lang.Long dataModelId;
	private T content;

	public DataModel(java.lang.Long id, T content) {
		this.dataModelId = id;
		this.content = content;
	}

	public boolean equals(java.lang.Object obj) {
		boolean isEqual = false;
		if (this.dataModelId == ((DataModel<T>)obj).dataModelId && this.content == ((DataModel<T>)obj).content) {
			isEqual = true;
		}
		return isEqual;
	}

	public int hashCode() {
		return this.hashCode();
	}

	public T getContent() {
		return this.content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public java.lang.Long getDataModelId() {
		return this.dataModelId;
	}

	public void setDataModelId(java.lang.Long id) {
		this.dataModelId = id;
	}

	public java.lang.String toString() {
		return this.content.toString();
	}

}
