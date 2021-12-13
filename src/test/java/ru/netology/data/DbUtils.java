package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {

    public static void clearTables() {
        var cleanPaymentEntity = "DELETE FROM payment_entity";
        var cleanOrderEntity = "DELETE FROM order_entity";
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            runner.update(conn, cleanPaymentEntity);
            runner.update(conn, cleanOrderEntity);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static String getPaymentStatus() {
        var statusSQL = "SELECT status FROM payment_entity";
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
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {

            result = runner.query(conn, query, new ScalarHandler<>());
            System.out.println(result);
            return result;
        }
    }
}
