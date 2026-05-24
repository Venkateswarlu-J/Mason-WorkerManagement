package com.example.demo.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/send")
    public void sendContact(@RequestBody ContactRequest req) {
        System.out.println("REquest received and data ::"+req);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("venkateshjavvaji121@gmail.com");
        msg.setSubject("[Mason Site Feedback] " + req.getSubject());
        msg.setText(
                "From: " + req.getName() + " <" + req.getEmail() + ">\n\n"
                        + req.getMessage()
        );
        msg.setReplyTo(req.getEmail());
        mailSender.send(msg);
    }

    public static class ContactRequest {
        private String name, email, subject, message;
        // getters
        public String getName()    { return name; }
        public String getEmail()   { return email; }
        public String getSubject() { return subject; }
        public String getMessage() { return message; }

        @Override
        public String toString() {
            return "ContactRequest{" +
                    "name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", subject='" + subject + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}

/*
  application.properties config:
  ---------------------------------
  spring.mail.host=smtp.gmail.com
  spring.mail.port=587
  spring.mail.username=venkateshjavvaji121@gmail.com
  spring.mail.password=YOUR_APP_PASSWORD
  spring.mail.properties.mail.smtp.auth=true
  spring.mail.properties.mail.smtp.starttls.enable=true

  NOTE: Use a Gmail App Password, not your account password.
  Generate one at: https://myaccount.google.com/apppasswords
*/
