package com.markava.itcompany.dao;

import java.util.List;

public interface GenericModelDao<T> {
	
	    public void insert(T entity);
	    public void update(T entity);
	    public void delete(int id);
	    public T get(int id);
	    public List<T> get();
	    
	}


