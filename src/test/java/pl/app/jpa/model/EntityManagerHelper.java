package pl.app.jpa.model;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EntityManagerHelper implements TestRule {

    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public EntityManagerHelper(String unitName) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unitName);
        this.entityManager = entityManagerFactory.createEntityManager();
        this.entityTransaction = entityManager.getTransaction();
    }

    public static EntityManagerHelper withUnit(String unitName) {
        return new EntityManagerHelper(unitName);
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public EntityTransaction getEntityTransaction() {
        return this.entityTransaction;
    }

    public void begin() {
        this.entityTransaction.begin();
    }

    public void commit() {
        this.entityTransaction.commit();
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                base.evaluate();
                entityManager.clear();
            }
        };
    }
}
