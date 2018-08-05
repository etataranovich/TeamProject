package com.markava.itcompany.dao;

import java.util.List;

import com.markava.itcompany.model.Economist;

public interface IEconomistDao extends GenericModelDao<Economist> {
	
	public List<Economist> getEconomistByItId(int id);
	void insert(Economist economist, List<Integer> itcId);

	
}
