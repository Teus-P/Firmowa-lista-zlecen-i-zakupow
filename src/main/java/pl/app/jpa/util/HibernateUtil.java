package pl.app.jpa.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import java.io.File;
import java.util.EnumSet;

/**
 * Wczytanie konfiguracji Hibernate
 * Stworzenie Session i SessionFactory
 */
public class HibernateUtil {

    private static ThreadLocal<Session> threadLocal = new ThreadLocal<>();
    private static SessionFactory factory = null;

    static {

        factory = createSessionFactory();

        // Generowanie DLL
        //createSchema();
    }

    private HibernateUtil() {
    }

    public static Session getSession() {
        Session session = null;
        if (threadLocal.get() == null) {
            //create Hibernate session
            session = factory.openSession();
            threadLocal.set(session);
        } else {
            session = threadLocal.get();
        }
        return session;
    }


    private static SessionFactory createSessionFactory() {
        return getMetadataSources().buildMetadata().getSessionFactoryBuilder().build();
    }


    private static MetadataSources getMetadataSources() {
        return new MetadataSources(configureServiceRegistry());
    }


    private static ServiceRegistry configureServiceRegistry() {
        return new StandardServiceRegistryBuilder().configure("/cfg/hibernate.cfg.xml").build();
    }


    /**
     * generuj DDL
     * ONLY DEVELOP USAGE xddd
     */
    private static void createSchema() {
        SchemaExport schemaExport = new SchemaExport();
        MetadataImplementor metadataImplementor = (MetadataImplementor) getMetadataSources().getMetadataBuilder().build();

        File file = new File(".");
        String path = file.getAbsolutePath() + "/src/main/resources/scripts/dll_MySql.sql";

        if (new File(path).exists())
            new File(path).delete();


        schemaExport.setOutputFile(path);
        schemaExport.setFormat(true);
        schemaExport.setDelimiter(";");


        EnumSet<TargetType> types = EnumSet.of(TargetType.SCRIPT);
        schemaExport.execute(types, SchemaExport.Action.CREATE, metadataImplementor);

    }


    /**
     * zamykanie sesji
     */
    public static void closeSession() {
        Session session = null;
        if (threadLocal.get() != null) {
            session = threadLocal.get();
            session.close();
            threadLocal.remove();
        }
    }


    public static void closeSessionFactory() {
        factory.close();
    }


}