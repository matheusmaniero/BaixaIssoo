package db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DB {
    private static Logger logger = LogManager.getLogger(DB.class);

    private static Connection conn = null;

    public static Connection getConnection(){
        if (conn == null){
            try{

                Properties props = loadProperties();

                String url = props.getProperty("dburl");
                System.out.println(url);
                conn = DriverManager.getConnection(url,props);

            } catch (SQLException e) {
                logger.error(e);
                throw new RuntimeException(e);
            }
        }
        return conn;
    }

    public static void closeConnection(){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(e);
                throw new RuntimeException(e);
            }
        }
    }

    private static Properties loadProperties() {

        try {

            InputStream in = DB.class.getClassLoader().getResourceAsStream("db.properties");
            Properties props = new Properties();
            props.load(in);

            for (Object prop : props.keySet()){
                String str = (String) props.get(prop);
                String env = resolveEnvVars(str);
                props.setProperty((String) prop,env);

            }

            return props;

        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }

    }

    public static void closeStatement(Statement st){
        if (st != null){
            try {
                st.close();
            } catch (SQLException e) {
                logger.error(e);
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeResultSet(ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error(e);
                throw new RuntimeException(e);
            }
        }
    }

    private static String resolveEnvVars(String input)    {
        if (null == input)
        {
            return null;
        }

        Pattern p = Pattern.compile("\\$\\{(\\w+)\\}|\\$(\\w+)");
        Matcher m = p.matcher(input); // get a matcher object
        StringBuffer sb = new StringBuffer();
        while(m.find()){
            String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
            String envVarValue = System.getenv(envVarName);
            m.appendReplacement(sb, null == envVarValue ? "" : envVarValue);
        }
        m.appendTail(sb);
        return sb.toString();
    }

}
