package com.sendemail;
import java.sql.*;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class SendEmail {
	public static void main(String[] args) throws Exception {
		String url = "jdbc:mysql://localhost:3306/university";
        String uname = "root";
        String pass = "1234";
        String query = "SELECT * FROM engineering_students";
		
		
   	  
   	     String sendMessage;
       try {
           // This will load the MySQL driver, each DB has its own driver
           Class.forName("com.mysql.cj.jdbc.Driver");
           // Setup the connection with the DB
           Connection connect = DriverManager
                   .getConnection(url, uname, pass);

           // Statements allow to issue SQL queries to the database
           Statement statement = connect.createStatement();
           // Result set get the result of the SQL query
           ResultSet resultSet = statement
                   .executeQuery(query);
           sendMessage= writeResultSet(resultSet);
          System.out.println("message"+sendMessage);

           

       } catch (Exception e) {
           throw e;
       } finally {
          
       }
       
       String to = "tarunkumargudla1@gmail.com";

       // Sender's email ID needs to be mentioned
       String from = "tarunautomationanywhere@gmail.com";

       // Assuming you are sending email from through gmails smtp
       String host = "smtp.gmail.com";

       // Get system properties
       Properties properties = System.getProperties();

       // Setup mail server
       properties.put("mail.smtp.host", host);
       properties.put("mail.smtp.port", "587");
       properties.put("mail.smtp.starttls.enable", "true");
       properties.put("mail.smtp.auth", "true");

       // Get the Session object.// and pass username and password
       Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

           protected PasswordAuthentication getPasswordAuthentication() {

               return new PasswordAuthentication("tarunautomationanywhere@gmail.com", "zejijlycjllootef");

           }

       });

       // Used to debug SMTP issues
       session.setDebug(true);

       try {
           // Create a default MimeMessage object.
           MimeMessage message = new MimeMessage(session);

           // Set From: header field of the header.
           message.setFrom(new InternetAddress(from));

           // Set To: header field of the header.
           message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

           // Set Subject: header field
           message.setSubject("This is the Subject Line!");

           // Now set the actual message
           //  message.setText(sendMessage);
         
           message.setContent(sendMessage,"text/html");

           System.out.println("sending...");
           // Send message
           Transport.send(message);
           System.out.println("Sent message successfully....");
       } catch (MessagingException mex) {
           mex.printStackTrace();
       }

   }


   public static String writeResultSet(ResultSet resultSet) throws SQLException {
       // ResultSet is initially before the first data set
   	
   	StringBuffer stBuffer=new StringBuffer();
   	stBuffer.append("<table><tr><th>Student_ID</th><th>Department</th><th>last_name</th><th>gender</th></tr>");
       while (resultSet.next()) {
           // It is possible to get the columns via name
           // also possible to get the columns via the column number
           // which starts at 1
           // e.g. resultSet.getSTring(2);
    	stBuffer.append("<tr>");
       	stBuffer.append("<td>" +resultSet.getString("Student_ID")+"</td>");
       	stBuffer.append("<td>" +resultSet.getString("Department")+"</td>");
       	stBuffer.append("<td>" +resultSet.getString("FIRST_NAME")+"</td>");
       	stBuffer.append("<td>" +resultSet.getString("LAST_NAME")+"</td>");
       	stBuffer.append("<td>" +resultSet.getString("PassOutYear")+"</td>");
       	stBuffer.append("<td>" +resultSet.getString("UniversityRank")+"</td>");
       	stBuffer.append("</tr>");
       }
       stBuffer.append("</table>");
   	return stBuffer.toString();
   }

   

}
