package geym.zbase.ch10.brkparent;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;
@Slf4j
public class MySQLDriver implements Driver {
    static {
        log.info("MySQLDriver load, cl{}",MySQLDriver.class.getClassLoader());

    }
    public MySQLDriver(){
        log.info("MySQLDriver init");
    }
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        return null;
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return false;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
