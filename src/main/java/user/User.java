package user;

import java.sql.ResultSet;
import java.util.ArrayList;

import objects.UserObject;

public interface User {
    boolean addUser(UserObject item);
    boolean editUser(UserObject item);
    boolean delUser(int id);

    ArrayList<ResultSet> getUsers(String multiSelect);
    ArrayList<ResultSet> getUsers(UserObject similar, int at, byte total);
    ResultSet getUser(int id);
    ResultSet getUser(String Username, String Userpass);
}