package com.ensah.core.dao;

import java.util.List;

public interface IGenericJPA<T, ID> {
	public List<T> getEntitiesByColValue(Class<T> boClass, String colName, String colVal);
	public T getEntityByColValue(Class<T> boClass, String colName, String colVal);
}
