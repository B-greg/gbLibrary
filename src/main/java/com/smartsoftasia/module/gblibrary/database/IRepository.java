package com.smartsoftasia.module.gblibrary.database;

import java.util.List;

public interface IRepository<T> {
	

	public List<T> GetAll();
	public T GetById(int id);
	
	public void Add(T entite);
	public void Update(T entite);
	public void Delete(T entite);
	public void DeleteAll();
	public void refresh(T entite);
    public void CreateOrUpdate(T entite);
	
}
