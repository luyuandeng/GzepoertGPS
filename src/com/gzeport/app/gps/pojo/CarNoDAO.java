package com.gzeport.app.gps.pojo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.apache.commons.dbcp.DelegatingConnection;
import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



public class CarNoDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(CarNoDAO.class);
	// property constants
	public static final String PLATE = "plate";

	protected void initDao() {
		// do nothing
	}

	public void save(CarNo transientInstance) {
		log.debug("saving CarNo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void delete(CarNo persistentInstance) {
		log.debug("deleting CarNo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CarNo findById(java.math.BigDecimal id) {
		log.debug("getting CarNo instance with id: " + id);
		try {
			CarNo instance = (CarNo) getHibernateTemplate().get(
					"com.scce.spring_mvc.pojo.CarNo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CarNo instance) {
		log.debug("finding CarNo instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	public List findMatchProperty(String propertyName, Object value) {
		log.debug("finding CarNo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CarNo as model where model."
					+ propertyName + " like ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CarNo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CarNo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserName(Object plate) {
		return findByProperty(PLATE, plate);
	}


	public List findAll() {
		log.debug("finding all CarNo instances");
		try {
			String queryString = "from CarNo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CarNo merge(CarNo detachedInstance) {
		log.debug("merging CarNo instance");
		try {
			CarNo result = (CarNo) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CarNo instance) {
		log.debug("attaching dirty CarNo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CarNo instance) {
		log.debug("attaching clean CarNo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CarNoDAO getFromApplicationContext(ApplicationContext ctx) {
		return (CarNoDAO) ctx.getBean("CarNoDAO");
	}
}