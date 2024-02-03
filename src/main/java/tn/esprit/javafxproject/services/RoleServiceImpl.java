package tn.esprit.javafxproject.services;


import tn.esprit.javafxproject.models.Role;
import tn.esprit.javafxproject.models.User;

import java.sql.*;
import java.util.ArrayList;

import tn.esprit.javafxproject.utils.DbConnection;

import tn.esprit.javafxproject.utils.DbConnection;
import tn.esprit.javafxproject.utils.Status;

public class RoleServiceImpl implements ICrud <Role>  {

    private Connection cnx ;

    public RoleServiceImpl ()
    {
        cnx=DbConnection.getInstance().getCnx();
    }
    @Override
    public ArrayList getAll() {
        ArrayList<Role> roles = new ArrayList<Role>();

        String req = "SELECT * from role ";
        Statement st;
        try {
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Role r = new Role();

                r.setIdRole(res.getInt(1));
                r.setName(res.getString(2));

                r.setStatus(res.getString(3));
                roles.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    @Override
    public boolean add(Role r) {

        String req = "INSERT INTO role (name, status) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, r.getName());
            preparedStatement.setString(2, Status.VALID.toString());

            int er = preparedStatement.executeUpdate();

            // Retrieve the generated ID (if needed)
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    r.setIdRole(generatedKeys.getInt(1));
                }
            }

            System.out.println("Ajout réussi");

            return er == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Role r) {
        String req = "UPDATE \"role\" "

                + " SET status='SUPPRIMER' "
                + "WHERE idrole='" + r.getIdRole() + "';";
        Statement st;
        int er = 0;
        try {
            st = cnx.createStatement();
            er= st.executeUpdate(req);
        } catch (SQLException e) {

            e.printStackTrace();
        }


        return er == -1;
    }

    @Override
    public boolean delete(int id) {
        String req = "UPDATE \"role\" 	SET "
                + "status='SUPPRIMER' "
                + "WHERE idrole ='" + id + "';";
        Statement st;
        int er = 0;
        try {
            st = cnx.createStatement();
            er= st.executeUpdate(req);
        } catch (SQLException e) {

            e.printStackTrace();
        }


        return er == -1;
    }

    @Override
    public boolean update(Role r) {
        String req = "UPDATE \"role\" "
                + "SET role='" + r.getName() + "', "

                + "status='" + r.getStatus() + "' "
                + "WHERE iduser='" + r.getIdRole() + "'";
        Statement st;
        int er = 0;
        try {
            st = cnx.createStatement();
            er = st.executeUpdate(req);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return er != 0;
    }


}
