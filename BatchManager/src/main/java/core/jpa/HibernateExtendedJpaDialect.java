package core.jpa;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;

public class HibernateExtendedJpaDialect extends HibernateJpaDialect {

  private static final long serialVersionUID = 2574675264767527432L;
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  @Override
  public Object beginTransaction(EntityManager entityManager, final TransactionDefinition definition)
      throws PersistenceException, SQLException, TransactionException {
    Session session = (Session) entityManager.getDelegate();
    if (definition.getTimeout() != TransactionDefinition.TIMEOUT_DEFAULT) {
      getSession(entityManager).getTransaction().setTimeout(definition.getTimeout());
    }
    entityManager.getTransaction().begin();
    logger.debug("Transaction started");

    session.doWork(new Work() {

        public void execute(Connection connection) throws SQLException {
            logger.debug("The connection instance is {}", connection);
            logger.debug("The isolation level of the connection is {} and the isolation level set on the transaction is {}",
                    connection.getTransactionIsolation(), definition.getIsolationLevel());
            DataSourceUtils.prepareConnectionForTransaction(connection, definition);
        }
    });
    return prepareTransaction(entityManager, definition.isReadOnly(), definition.getName());
  }

}
