package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
      private static String url = System.getProperty("datasource.url");
      private static String user = "app";
      private static String password = "pass";

    public static void clearTables() {
        var cleanPaymentEntity = "DELETE FROM payment_entity";
        var cleanOrderEntity = "DELETE FROM order_entity";
        var cleanCreditEntity = "DELETE FROM credit_request_entity";
        var runner = new QueryRunner();

        try (var conn = DriverManager.getConnection
                (url, user, password)) {

            runner.update(conn, cleanPaymentEntity);
            runner.update(conn, cleanOrderEntity);
            runner.update(conn, cleanCreditEntity);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

        public static String getOrderStatus () {
            var statusSQL = "SELECT status FROM order_entity";
            try {
                return getStatus(statusSQL);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            return statusSQL;
        }

        public static String getCreditStatus () {
            var statusSQL = "SELECT status FROM credit_entity";
            try {
                return getStatus(statusSQL);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            return statusSQL;
        }

    private static String getStatus(String query) throws SQLException {
        String result = "";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection
                (url, user, password)) {

            result = runner.query(conn, query, new ScalarHandler<>());
            System.out.println(result);
            return result;
        }
    }
}

