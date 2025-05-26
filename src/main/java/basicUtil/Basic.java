package basicUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface Basic extends ShareControl {
    boolean add(PreparedStatement preStmt);
    boolean edit(PreparedStatement preStmt);
    boolean del(PreparedStatement preStmt);

    ArrayList<ResultSet> gets(String multiSelect);
    ResultSet get(String sql, int value);
    ResultSet get(String sql, String name, String pass);
}
