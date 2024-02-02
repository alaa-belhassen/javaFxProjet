package tn.esprit.javafxproject.services;


import tn.esprit.javafxproject.models.Role;
import tn.esprit.javafxproject.models.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import tn.esprit.javafxproject.utils.DbConnection;

import tn.esprit.javafxproject.utils.DbConnection;
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
        String req = "INSERT INTO role "
                + "	(idrole, role, status)\r\n"
                + "	VALUES ('"+ r.getIdRole()+"','"+r.getName()+"','"+r.getStatus()+"'  );";
        Statement st;
        int er=0;
        try {
            st = cnx.createStatement();
            er= st.executeUpdate(req);
            System.out.println(" Ajout reussi ");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return er == -1;
    }

    @Override
    public boolean delete(Role r) {
        String req = "UPDATE \"role\" "

                + " SET status='supprim�' "
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
                + "status='supprim�' "
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
