package tn.esprit.javafxproject.services;

import java.sql.*;
import java.util.ArrayList;
import tn.esprit.javafxproject.models.Publication;

public class PublicationService implements ICrud<Publication> {

    private Connection connection;

    // Constructor to initialize the connection
    public PublicationService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Publication> getAll() throws SQLException {
        ArrayList<Publication> publications = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Publication");
            while (resultSet.next()) {
                Publication publication = new Publication();
                publication.setPublicationID(resultSet.getInt("PublicationID"));
                publication.setIdUser(resultSet.getInt("IdUser"));
                publication.setContent(resultSet.getString("Content"));
                publication.setTimestamp(resultSet.getTimestamp("Timestamp"));
                publication.setLikes(resultSet.getInt("Likes"));
                publication.setShares(resultSet.getInt("Shares"));
                publication.setAttachments(resultSet.getString("Attachments"));
                publications.add(publication);
            }
        }
        return publications;
    }

    @Override
    public boolean add(Publication publication) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Publication(IdUser, Content, Timestamp, Likes, Shares, Attachments) " +
                        "VALUES (?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setInt(1, publication.getIdUser());
            preparedStatement.setString(2, publication.getContent());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(4, publication.getLikes());
            preparedStatement.setInt(5, publication.getShares());
            preparedStatement.setString(6, publication.getAttachments());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean delete(Publication publication) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM Publication WHERE PublicationID = ?")) {
            preparedStatement.setInt(1, publication.getPublicationID());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM Publication WHERE PublicationID = ?")) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean update(Publication publication) throws SQLException {
        int idPublication =1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(

                "UPDATE Publication SET IdUser = ?, Content = ?, Timestamp = ?, Likes = ?, Shares = ?, Attachments = ? " +
                        "WHERE PublicationID =?")) {
            preparedStatement.setInt(1, publication.getIdUser());
            preparedStatement.setString(2, publication.getContent());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(4, publication.getLikes());
            preparedStatement.setInt(5, publication.getShares());
            preparedStatement.setString(6, publication.getAttachments());
            preparedStatement.setInt(7, idPublication);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }


}
