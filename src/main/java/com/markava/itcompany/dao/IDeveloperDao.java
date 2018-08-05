package com.markava.itcompany.dao;

import java.util.List;

import com.markava.itcompany.model.Developer;

public interface IDeveloperDao extends GenericModelDao<Developer>{
	public List<Developer> getDeveloperByItId(int id);

	void insert(Developer developer, List<Integer> itcId);

}
