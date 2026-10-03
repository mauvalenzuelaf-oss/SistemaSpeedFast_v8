package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión con la base de datos MySQL.
 */
public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://127.0.0.1:3306/speedfast_db";

    private static final String USUARIO =
            "root";

    private static final String CONTRASENA =
            "TU_CONTRASENA";

    /**
     * Obtiene una conexión con la base de datos.
     */
    public static Connection obtenerConexion()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                CONTRASENA
        );
    }
}