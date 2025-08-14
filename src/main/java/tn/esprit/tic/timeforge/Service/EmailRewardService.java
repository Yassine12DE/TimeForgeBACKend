package tn.esprit.tic.timeforge.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.User;

@Service
public class EmailRewardService {

    @Autowired
    private JavaMailSender mailSender ;

    public  void SendRewardEmail (User user){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getUsername()); // hot l'email mtaa reciver
        message.setSubject(" Congratulations! You've been awarded!");
        message.setText("Dear " + user.getName() + ",\n\n" +
                "Congratulations! You've been selected as a GREAT Employee today ! for completing your task before the deadline" +
                "\n Keep up the great work!\n\n" +
                "Best regards," +
                "\nTimeForge Team");
        mailSender.send(message);
    }
}
