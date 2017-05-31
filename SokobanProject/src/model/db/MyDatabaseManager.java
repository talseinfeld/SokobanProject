package model.db;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.entities.EntityDB;

/**
 * 
 * @author Tal Sheinfeld
 * We use Singletone design in our Database Manager so we will be able to "protect"
 * the database.
 */
public class MyDatabaseManager implements DatabaseManager {
	
	private static MyDatabaseManager instance = new MyDatabaseManager();
	
	public static MyDatabaseManager getInstance() {
		return instance;
	}
	private SessionFactory factory;
	
	//Private c'tor for Singletone design pattern
	private MyDatabaseManager(){
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		Configuration configuration = new Configuration();
		configuration.configure();
		factory = configuration.buildSessionFactory();
	}
	
	@Override
	public void save(EntityDB entityDB) {
		Session session = null;
		Transaction tx = null;

		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(entityDB);
			tx.commit();			
		}
		catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			System.out.println(ex.getMessage());
		}
		finally {
			if (session != null)
				session.close();
		}		
	}
	
	@Override
	public List<EntityDB> getListByQueryString(String hql) {
		Session session = null;
		List<EntityDB> list = null;
		try {
			session = factory.openSession();
			@SuppressWarnings("unchecked")
			Query<EntityDB> query = session.createQuery(hql);
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}
	
	@Override
	public void close() {
		factory.close();
	}

	
	@Override
	public List<?> getListByQueryAndEntityProperties(String hql, EntityDB db) {
		Session session = null;
		List<EntityDB> list = null;
		try {
			session = factory.openSession();
			@SuppressWarnings("unchecked")
			Query<EntityDB> query = session.createQuery(hql).setProperties(db);
			list = query.list();
		} 
		catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	
}
