package trb.tii.efe.tools;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.text.*;
import java.lang.*;

public class SendMail {

    String mailTo = new String();
    String mailCC = new String();
    String mailFrom = new String();
    String mailSMTP = new String();
    String mailBCC = new String();
    String subject = new String();
    String text = new String();


    // Constructor method for sendmail class
    // Sets:  to, from, cc, and stmp host
    public SendMail(String to, String from, String cc, String stmp) {

        mailTo = to;
        mailCC = cc;
        mailFrom = from;
        mailSMTP = stmp;

        Logger.log ("Sendmail to:   " + mailTo);
        Logger.log ("Sendmail cc:   " + mailCC);
        Logger.log ("Sendmail from: " + mailFrom);
        Logger.log ("Sendmail stmp: " + mailSMTP);
    }


    private boolean send() {

        boolean status;

        try {
            // Create the JavaMail session
            java.util.Properties properties = System.getProperties();
            properties.put("mail.smtp.host", mailSMTP);
            properties.put("file.encoding", "Cp1252");
            Session session = Session.getInstance(properties, null);

            // Construct the message
            MimeMessage message = new MimeMessage(session);

            // Set the from address
            Address fromAddress = new InternetAddress(mailFrom);
            message.setFrom(fromAddress);

            // Parse and set the recipient addresses
            Address[] toAddresses = InternetAddress.parse(mailTo);
            message.setRecipients(Message.RecipientType.TO,toAddresses);

            Address[] ccAddresses = InternetAddress.parse(mailCC);
            message.setRecipients(Message.RecipientType.CC,ccAddresses);

            Address[] bccAddresses = InternetAddress.parse(mailBCC);
            message.setRecipients(Message.RecipientType.BCC,bccAddresses);

            // Set the subject and text
            message.setSubject(subject);
            message.setText(text);
            Logger.log("SendMail.send: text is " + text);

            logHeaderInfo(message);
            Transport.send(message);

            status = true;
            return status;
        }

        catch(Exception e) {
            status = false;
            //log error
            Logger.log("Error sending mail message " + e.toString());
            return status;
        }
    }


    private void logHeaderInfo(MimeMessage message) {

        try {

            Header header;
            Enumeration enumVar = message.getAllHeaders();
            Logger.log("Mail header info: " + System.getProperty("line.separator"));

            while(enumVar.hasMoreElements())  {
                header = (Header)enumVar.nextElement();
                Logger.log("name : " + header.getName() + " value : " + header.getValue() + System.getProperty("line.separator"));
            }
        }
        catch(Exception e) {
            Logger.log("Error logging header info " + e.toString());
        }
    }


    public boolean sendUpdateMessage(CCI_Ad ad) {

        boolean status;

        //construct body portion of message
        text = constructMessage(ad);

        //construct subject
        subject = new String("TI Classified ad update");

        //sent message
        status = send();

        //check to see if email was sent succssfully
        //and log the results.
        if (status)
            Logger.log("Successfully sent email message");
        else
            Logger.log("Email message unsuccessfully sent");

        return status;
    }

    private String constructMessage(CCI_Ad ad) {

        String eol = System.getProperty("line.separator");

        //construct the string that will separate the various portion of the message
        String portionSeparator = new String(eol + "-------------------" + eol);

        //get today's date and construct date portion of message
        Calendar today = Calendar.getInstance();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String headerPortion =  new String(
                "Publication:  " + ad.getPublication() + eol +
                "Ad ID:        " + ad.getAdID() + eol +
                "Vertical:     " + ad.getVertical() + eol +
                "Type:         " + ad.getTableName() + eol +
                "Date Updated: " + dateFormat.format(today.getTime()) + eol);

        // construct new ad portion of the message
        String adPortion = new String(
                "Updated Ad Fields:    " + portionSeparator +
                "Class:                " + ad.getClassID() + eol +
                "Title:                " + ad.getTitle() + eol +
                "Upsell:               " + ad.getUpsell() + eol +
                "Start Date:           " + ad.getStartDate() + eol +
                "End Date:             " + ad.getStopDate() + eol +
                "Print Description:    " + ad.getPrintDesc() + eol +
                "Enhanced Description: " + ad.getEnhancedDesc());

        String fullMessage = new String(headerPortion + eol + adPortion);

        return fullMessage;
    }

}


