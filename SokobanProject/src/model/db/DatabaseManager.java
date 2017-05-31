package model.db;

import java.util.List;

import model.entities.EntityDB;
public interface DatabaseManager {
	
	public void save(EntityDB entityDB);
	public void close();
	public List<?> getListByQueryAndEntityProperties(String hql, EntityDB db);
	public List<?> getListByQueryString(String hql);

}
