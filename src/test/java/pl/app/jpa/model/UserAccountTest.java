package pl.app.jpa.model;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class UserAccountTest {

    @Rule
    public EntityManagerHelper provider = EntityManagerHelper.withUnit("integration-test");

    @Test
    public void testSavingNewUserAccount() {
        this.provider.begin();
        this.provider.getEntityManager().persist(new UserAccount("test", "test", "test", "test", 12345678908L, "dsada@example.com", "+48000000000"));
        this.provider.getEntityManager().persist(new UserAccount("test1", "test1", "test1", "test1", 12345678901L, "dsada1@example.com", "+48000000001"));
        this.provider.getEntityManager().persist(new UserAccount("test2", "test2", "test2", "test2", 12345678902L, "dsada2@example.com", "+48000000002"));

        List<UserAccount> resultList = this.provider.getEntityManager().createNamedQuery("UserAccount.getAll", UserAccount.class).getResultList();

        assertEquals(3, resultList.size());
        for (UserAccount resultUser : resultList) {
            assertNotNull(resultUser.getFirstName());
        }
        this.provider.commit();

    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testUserAccountUniqueConstraint() {

        UserAccount userAccount1 = new UserAccount("test", "test", "test", "test", 12345678908L, "dsada@example.com", "+48000000000");
        UserAccount userAccount2 = new UserAccount("test", "test", "test", "test", 12345678902L, "dsada@example.com", "+48000000000");

        this.provider.begin();

        this.provider.getEntityManager().persist(userAccount1);
        this.provider.getEntityManager().persist(userAccount2);

        this.provider.commit();
    }

}
