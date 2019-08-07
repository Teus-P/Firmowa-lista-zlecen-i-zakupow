package pl.app.entity.manager;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.app.entity.Tabela;
import pl.app.hibernate.HibernateUtil;

public class TabelaManager {

    public Integer addItem(Tabela tabela) {

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        Transaction tx = null;
        Integer productID = null;

        try {
            tx = session.beginTransaction();
            productID = (Integer) session.save(tabela);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return productID;
    }


}
