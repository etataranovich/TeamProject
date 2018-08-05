package com.markava.itcompany.dao;

import java.util.List;

import com.markava.itcompany.model.Office;

public interface IOfficeDao extends GenericModelDao<Office> {
	public List<Office> getOfficeByItId(int id);
	void insert(Office office, List<Integer> itcId);

}
