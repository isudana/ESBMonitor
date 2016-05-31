package org.wso2.esbMonitor.persistance;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.wso2.esbMonitor.network.PassThruHTTPBean;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinanjana on 30/05/2016.
 * This class persists events to
 * db.
 * uses hibernate for JPA
 */
public class PersistanceImpl {

    private static Logger logger = LogManager.getLogger(PersistanceImpl.class);
    private static ArrayList<PassThruHTTPBean> scheduledList
            = new ArrayList<>();

    public synchronized static void addNetworkEvent(PassThruHTTPBean passThruHTTPBean){
        logger.info("Adding event :{}"+ passThruHTTPBean.getActiveThreadCount() +"Size :{}" +scheduledList.size());
        scheduledList.add(passThruHTTPBean);
    }

    public synchronized static void addNetworkTrafficDetailsToDB(){

        try {
            if (scheduledList.size() > 0){
                logger.info("Started persisting");
                Session session = HibernateSessionCreator.getSession();
                Transaction tx;
                tx = session.beginTransaction();
                for(PassThruHTTPBean passThruHTTPBean : scheduledList){
                    session.save(passThruHTTPBean);
                }
                tx.commit();
//                org.hibernate.Query query = session.createSQLQuery("SELECT * FROM org.wso2.esbMonitor.network.PassThruHTTPBean c");
//                java.util.List list = query.list();
//                System.out.println(list);
//                logger.info(list.get(0));
                session.close();
                scheduledList.clear();
            }

        }catch (Exception e){
            logger.error("HibernateError" , e);
        }


    }
}
