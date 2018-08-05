package com.markava.itcompany.dao;

import java.util.List;

import com.markava.itcompany.model.Sale;

public interface ISaleDao extends GenericModelDao<Sale> {
	public List<Sale> getSaleByItId(int id);
	void insert(Sale sale, List<Integer> itcId);

}
