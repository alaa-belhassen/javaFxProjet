package tn.esprit.javafxproject.services;

import tn.esprit.javafxproject.models.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.javafxproject.models.Publication;

public class CommentService {
    private Publication publication;
    private Connection connection;  // Non-static Connection field

    // Constructor to initialize the connection
    public CommentService(Connection connection) {
        this.connection = connection;
    }

    public boolean addComment(Comment comment) {
        String sql = "INSERT INTO public.commentaire(publicationid, iduser, content, timestamp, likes, attachments, parentcommentid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, comment.getPublicationId());
            preparedStatement.setInt(2, comment.getUserId());
            preparedStatement.setString(3, comment.getContent());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(comment.getTimestamp()));
            preparedStatement.setInt(5, comment.getLikes());
            preparedStatement.setString(6, comment.getAttachments());
            preparedStatement.setObject(7, comment.getParentCommentId());

            preparedStatement.executeUpdate();
            return true;  // Return true to indicate success

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception based on your application's needs
        }
        return false;  // Return false to indicate failure
    }

    public List<Comment> getCommentsForPublication(int publicationId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM public.commentaire WHERE publicationid = ? ORDER BY timestamp DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, publicationId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment comment = new Comment(1, 2, 0, resultSet.getString("content"), null, 0, null);
                comment.setCommentId(resultSet.getInt("commentid"));
                comment.setPublicationId(resultSet.getInt("publicationid"));
                comment.setUserId(resultSet.getInt("iduser"));
                comment.setContent(resultSet.getString("content"));
                comment.setTimestamp(resultSet.getTimestamp("timestamp").toLocalDateTime());
                comment.setLikes(resultSet.getInt("likes"));
                comment.setAttachments(resultSet.getString("attachments"));
                comment.setParentCommentId((Integer) resultSet.getObject("parentcommentid"));

                comments.add(comment);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception based on your application's needs
        }

        return comments;
    }
}
