package com.milestone_5.SolutionService.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.milestone_5.SolutionService.entities.SokobanSolutionDb;

public class DbHandler {

private SessionFactory factory;
	
	public DbHandler() {
		Configuration configuration = new Configuration();
		configuration.configure();
		factory = configuration.buildSessionFactory();
	}
	
	public void addSolution(SokobanSolutionDb solution) {
		Session session = null;
		Transaction tx = null;
		
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			
			session.save(solution);
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
	
	public String getSolution(String name) {
		Session session = null;		
		String solution = null;
		try {
			session = factory.openSession();			
			SokobanSolutionDb sol = session.get(SokobanSolutionDb.class, name);			
			if (sol != null) {
				solution = sol.getSolution();
			}			
		}
		catch (HibernateException ex) {			
			System.out.println(ex.getMessage());
		}
		finally {
			if (session != null)
				session.close();
		}
		return solution;
	}
}
