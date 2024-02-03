package tn.esprit.javafxproject.services;

import tn.esprit.javafxproject.models.Reclamation;
import tn.esprit.javafxproject.models.Response;
import tn.esprit.javafxproject.utils.DbConnection;

import java.sql.*;
import java.util.ArrayList;

public class ReponseService implements ICrud<Response> {
    private Connection cnx;

    public ReponseService(){
        cnx = DbConnection.getInstance().getCnx();
    }

    @Override
    public boolean insert(Response rep) throws SQLException {
        String req = "INSERT INTO reponse (id_reclamation, description_reponse, status_reponse,iduser) " +
                "VALUES ('" + rep.getId_reclamation()+ "','" + rep.getDescription_reponse() + "','" + "Active"+"','"+ rep.getId_user() + "')";


        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }



    public boolean addrep(Response rep, Reclamation selectedReclamation) throws SQLException {
        // Assuming you have the necessary SQL query to insert the response
        String query = "INSERT INTO reponse (id_reclamation, description_reponse, status_reponse, iduser) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, selectedReclamation.getId_reclamation());
            preparedStatement.setString(2, rep.getDescription_reponse());
            preparedStatement.setString(3, "Active"); // Assuming 'Active' is a constant value
            preparedStatement.setInt(4, rep.getId_user());

            return preparedStatement.executeUpdate() == 1;
        }
    }

    /*@Override
    public boolean update(User user) throws SQLException {
        String req = "UPDATE user SET first_name = '"+user.getFirst_name()+
                "', last_name = '"+ user.getLast_name()+"', email = '"+user.getEmail()+"'"+
                "WHERE id = "+ user.getId();
        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }*/

    /*@Override
    public ArrayList<Reponse> getAll() {
        return null;
    }

     @Override
        public ArrayList<Reponse> getAll() {
            return null;
        }*/

    @Override
    public boolean add(Response rep) throws SQLException {
        String req ="INSERT INTO reponse (id_reclamation, description_reponse, status_reponse,id_user) " +
                "VALUES ('" + rep.getId_reclamation()+ "','" + rep.getDescription_reponse() + "','" + rep.getStatus_reponse()+"','"+ rep.getId_user() + "')";


        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean delete(Response reponse) throws SQLException {
        String req = "update reponse set status_reponse= '"+"active"+"' where id_reponse ="+reponse.getId_reponse()+";";
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String req = "update reponse set status_reclamation= '"+"inactive"+"' where id_reponse ="+id+";";
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }

/*    @Override
    public boolean delete(int id) throws SQLException {
        String req = "DELETE FROM user WHERE id = "+id;
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }*/

    @Override
    public boolean update(Response reponse) throws SQLException {
        String req = "UPDATE reclamation SET description_reclamation = '"+reponse.getDescription_reponse()+ "' WHERE id_reclamation = "+ reponse.getId_reponse();
        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    /*@Override
    public boolean delete(User user) throws SQLException {
        String req = "DELETE FROM user WHERE id = "+user.getId();
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }*/

    @Override
    public ArrayList<Response> getAll() throws SQLException {
        ArrayList<Response> reponses = new ArrayList<Response>();
        String req = "SELECT * FROM 'Reponse'";
        Statement st = cnx.createStatement();
        ResultSet resultSet = st.executeQuery(req);
        while(resultSet.next()){
            Response r = new Response();
            r.setId_reponse(resultSet.getInt(1));
            r.setId_reclamation(resultSet.getInt(2));
            r.setDescription_reponse(resultSet.getString(3));
            r.setStatus_reponse(resultSet.getString(5));
            r.setId_user(resultSet.getInt(6));


            reponses.add(r);
        }
        return reponses;
    }


    public ArrayList<Response> getResponsesForUser(int userId) throws SQLException {
        ArrayList<Response> userResponses = new ArrayList<>();

        String query = "SELECT * FROM reponse WHERE iduser = " + userId;

        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Response response = new Response();
                response.setId_reponse(resultSet.getInt(1));
                response.setId_reclamation(resultSet.getInt(2));
                response.setDescription_reponse(resultSet.getString(3));
                response.setStatus_reponse(resultSet.getString(4));
                response.setId_user(resultSet.getInt(5));

                userResponses.add(response);
            }
        }
        String reclamationQuery = "SELECT id_reclamation, description_reclamation FROM reclamation WHERE id_reclamation IN ("
                + "SELECT id_reclamation FROM reponse WHERE iduser = " + userId + ")";

        try (Statement reclamationStatement = cnx.createStatement();
             ResultSet reclamationResultSet = reclamationStatement.executeQuery(reclamationQuery)) {

            // Iterate through the results and set the description_reclamation for each corresponding response
            while (reclamationResultSet.next()) {
                int reclamationId = reclamationResultSet.getInt("id_reclamation");
                String descriptionReclamation = reclamationResultSet.getString("description_reclamation");

                // Find the corresponding response in the userResponses list
                for (Response response : userResponses) {
                    if (response.getId_reclamation() == reclamationId) {
                        response.setDescription_reclamation_response(descriptionReclamation);
                        break;  // No need to continue searching once found
                    }
                }
            }
        }

        return userResponses;
    }

}
