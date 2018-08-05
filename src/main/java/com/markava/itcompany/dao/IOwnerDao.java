package com.markava.itcompany.dao;

import java.util.List;

import com.markava.itcompany.model.Owner;


public interface IOwnerDao extends GenericModelDao<Owner> {
	public Owner getOwnerByItId(int id);
	void insert(Owner owner, List<Integer> itcId);

}
