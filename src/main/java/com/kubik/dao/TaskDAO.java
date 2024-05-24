package com.kubik.dao;

import com.kubik.entity.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TaskDAO {
    private final SessionFactory sessionFactory;

    public TaskDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Task> getAll(int offset, int limit){
        Query<Task> query = getSession().createQuery("from Task", Task.class);
        query.setMaxResults(limit);
        query.setFirstResult(offset);
        return query.getResultList();
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public long getAllCount(){
        Query<Long> query = getSession().createQuery("select count(t) from Task t ", Long.class);
        return query.uniqueResult();
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public Task getById(int id){
        Query<Task> query = getSession().createQuery("from Task where id = :ID ", Task.class);
        query.setParameter("ID", id);
        return query.uniqueResult();
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(Task task){
        getSession().persist(task);
    }
    @Transactional( propagation = Propagation.REQUIRED)
    public void delete(Task task){
        getSession().remove(task);
    }
    @Transactional( propagation = Propagation.REQUIRED)
    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}
