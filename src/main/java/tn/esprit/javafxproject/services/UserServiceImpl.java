package tn.esprit.javafxproject.services;

import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.javafxproject.models.Role;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.utils.DbConnection;

import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.javafxproject.utils.Status;
import org.mindrot.jbcrypt.BCrypt;
public class UserServiceImpl implements ICrud <User> {

    private Connection cnx ;


    public UserServiceImpl() {
        cnx= DbConnection.getInstance().getCnx();
    }

    private String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);


        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }

        return password.toString();
    }
    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> Users = new ArrayList<User>();

        String req = "SELECT u.*, r.role FROM utilisateur u JOIN \"role\" r ON u.idrole = r.idrole";
        Statement st;
        try {
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                User u = new User();
                u.setIdUser(res.getInt(1));
                u.setNom(res.getString(2));
                u.setEmail(res.getString(3));
                u.setTelephone(res.getString(4));
                u.setAdresse(res.getString(5));
                Role role = new Role();
                role.setName(res.getString("role"));


                u.setRole(role);
                u.setStatus(res.getString(7));
                Users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Users;
    }


    public User getUser(int id) {
        User u = new User();

        String req = "SELECT u.*, r.role FROM utilisateur u JOIN \"role\" r ON u.idrole = r.idrole WHERE iduser =" + id + ";";
        Statement st;
        try {
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                u.setIdUser(res.getInt(1));
                u.setNom(res.getString(2));
                u.setEmail(res.getString(3));
                u.setTelephone(res.getString(4));
                u.setAdresse(res.getString(5));
                Role role = new Role();
                role.setName(res.getString("role"));
                u.setRole(role);
                u.setStatus(res.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return u;
    }


    @Override
    public boolean add(User user) {
        if (!emailExists(user.getEmail())){
        String req = "INSERT INTO utilisateur "
                + "( nom, email, telephone, adresse, idrole, status, password,image)\r\n"
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement pst = cnx.prepareStatement(req)) {

            pst.setString(1, user.getNom());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getTelephone());
            pst.setString(4, user.getAdresse());
            pst.setInt(5, 2);
            pst.setString(6, Status.VALID.toString());
            pst.setString(7, user.getPassword());
            pst.setBytes(8, user.getImage());

            int result = pst.executeUpdate();

           if (result > 0) {
                System.out.println("Ajout réussi");
                return true;
            } else  {
                System.out.println("Échec de l'ajout");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();}


        } else {System.out.println("Email existe déjà ");}
        return false;
    }



    @Override
    public boolean delete(User U ) {

        String req = "UPDATE utilisateur "

                + " SET status='supprimé' "
                + "WHERE iduser='" + U.getIdUser() + "';";
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
        String req = "UPDATE \"User\" 	SET "
                + "status='supprimé' "
                + "WHERE iduser ='" + id + "';";
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
    public boolean update(User U) {
        String req = "UPDATE utilisateur "
                + "SET nom='" + U.getNom() + "', "
                + "email='" + U.getEmail() + "', "
                + "telephone='" + U.getTelephone() + "', "
                + "adresse='" + U.getAdresse() + "', "

                + "status='" + U.getStatus() + "' "
                + "WHERE iduser='" + U.getIdUser() + "'";
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


    public boolean CreateAccount(User user) {

        String randomPassword = generateRandomPassword(8);
        user.setPassword(randomPassword);

      if (!emailExists(user.getEmail())){
        String req = "INSERT INTO utilisateur "
                + "( nom, email, telephone, adresse, idrole, status, password,image)\r\n"
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = cnx.prepareStatement(req)) {

            pst.setString(1, user.getNom());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getTelephone());
            pst.setString(4, user.getAdresse());
            pst.setInt(5,3);
            pst.setString(6, Status.VALID.toString());
            pst.setString(7, user.getPassword());
            pst.setBytes(8, user.getImage());

            int result = pst.executeUpdate();

            if (result > 0) {
                System.out.println("Ajout réussi avec mot de passe aléatoire : " + randomPassword);

                // Envoyer le mot de passe par e-mail
                sendPasswordByEmail(user.getEmail(), randomPassword);

                return true;
            } else {
                System.out.println("Échec de l'ajout");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();


        } } else
      {System.out.println("email existe");}
        return false;
    }


    private void sendPasswordByEmail(String userEmail, String password) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Session pour l'envoi d'e-mail
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("geeksjava44@gmail.com", "gnxe poeh fgby tbxt");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("khouloudmhamdi7@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject("Your New Password");

            String htmlContent = "<p>Dear User,</p>"
                    + "<p>Your new password is: <strong>" + password + "</strong></p>"
                    + "<p>Please keep this password secure and consider changing it after logging in.</p>"
                    + "<p>Best regards,<br/>Your Application Team</p>";

            message.setContent(htmlContent, "text/html; charset=utf-8");


            // Envoyer le message
            Transport.send(message);

            System.out.println("Mot de passe envoyé par e-mail à : " + userEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'envoi du mot de passe par e-mail");
        }
    }
    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean passwordHashMatches(String inputPassword, String storedPassword) {
        System.out.println("inputPassword: " + inputPassword);
        System.out.println("storedPassword: " + storedPassword);

        boolean result = BCrypt.checkpw(inputPassword, storedPassword);

        return result;
    }
    public boolean authenticate(String email, String password) {
        String query = "SELECT password FROM  utilisateur WHERE email = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");

                    if (passwordHashMatches(password, storedPassword)) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return false;
    }

    public boolean changePassword(String email, String oldPassword, String newPassword) {
        // Vérifier la correspondance de l'ancien mot de passe en utilisant la méthode authenticate
        if (authenticate(email, oldPassword)) {
          User u =new User();
            String newHashedPassword = u.hashPassword(newPassword);


            boolean updateSuccess = updatePasswordInDatabase(email, newHashedPassword);

            return updateSuccess;
        } else {

            return false;
        }
    }
    private boolean updatePasswordInDatabase(String userEmail, String newHashedPassword) {
        String updateQuery = "UPDATE utilisateur SET password = ? WHERE email = ?";

        try (PreparedStatement updateStatement = cnx.prepareStatement(updateQuery)) {
            updateStatement.setString(1, newHashedPassword);
            updateStatement.setString(2, userEmail);

            int rowsUpdated = updateStatement.executeUpdate();

            // Vérifier si la mise à jour a réussi
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour du mot de passe : " + e.getMessage());
            return false;
        }
    }
    public boolean ResetPassword(String userEmail) {

        String updateQuery = "UPDATE utilisateur SET password = ? WHERE email = ?";
        String randomPassword = generateRandomPassword(8);
        String randomPasswordHashed =BCrypt.hashpw(randomPassword, BCrypt.gensalt());

        try (PreparedStatement updateStatement = cnx.prepareStatement(updateQuery)) {
            updateStatement.setString(1, randomPasswordHashed);
            updateStatement.setString(2, userEmail);

            int rowsUpdated = updateStatement.executeUpdate();
            // Envoyer le mot de passe par e-mail
            sendPasswordByEmail(userEmail, randomPassword);
System.out.println("Update réussi ");
            // Vérifier si la mise à jour a réussi
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour du mot de passe : " + e.getMessage());
            return false;
        }
    }
}

