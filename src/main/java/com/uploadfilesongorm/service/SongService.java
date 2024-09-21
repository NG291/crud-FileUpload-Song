package com.uploadfilesongorm.service;

import com.uploadfilesongorm.model.Song;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Service
public class SongService implements ISongService{
    private static EntityManager entityManager;
    private static SessionFactory sessionFactory;

    static{
        try {
            sessionFactory = new Configuration().configure("hibernate.conf.xml").buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        }catch(HibernateException e){
            e.printStackTrace();
        }
    }
    @Override
    public List<Song> findAll() {
        String queryStr = "Select s from Song AS s";
        TypedQuery<Song> query = entityManager.createQuery(queryStr,Song.class);
        return query.getResultList();
    }

    @Override
    public Song finById(Long id) {
        String queryStr = "Select s from Song AS s where s.id =:id";
        TypedQuery<Song> query = entityManager.createQuery(queryStr,Song.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Override
    public void save(Song song) {
        Transaction transaction = null;
        Song origin;

        if (song.getId() == null || song.getId() == 0) {
            origin = new Song();
        } else {
            origin = finById(song.getId());
        }

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            origin.setName(song.getName());
            origin.setSinger(song.getSinger());
            origin.setCategory(song.getCategory());
            origin.setPath(song.getPath());
            session.saveOrUpdate(origin);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void remove(Long id) {
     Song song = finById(id);
     if(song != null){
         Transaction transaction = null;
         try(Session session= sessionFactory.openSession()) {
             transaction= session.beginTransaction();
             session.remove(song);
             transaction.commit();
         }catch (Exception e){
             e.printStackTrace();
             if(transaction !=null){
                 transaction.rollback();
             }
         }
     }
    }
}
