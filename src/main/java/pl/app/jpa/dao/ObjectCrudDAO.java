package pl.app.jpa.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.app.jpa.util.HibernateUtil;

/**
 * CRUD operation
 * addObject (CREATE)
 * selectObject (READ)
 * updateObject (UPDATE)
 * removeObject (DELETE)
 */
public class ObjectCrudDAO {

    public Integer addObject(Object object) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        Integer objectId = null;

        try {
            tx = session.beginTransaction();
            objectId = (Integer) session.save(object);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return objectId;
    }
    
    

}
