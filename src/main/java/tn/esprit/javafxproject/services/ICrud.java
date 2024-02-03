package tn.esprit.javafxproject.services;

import tn.esprit.javafxproject.models.Response;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ICrud <T> {
    ArrayList<T> getAll() throws SQLException;

    boolean insert(Response rep) throws SQLException;

    boolean add(T t) throws SQLException;
    boolean delete(T t) throws SQLException;
    boolean delete(int id) throws SQLException;
    boolean update(T t) throws SQLException;

}
