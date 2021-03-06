package sap.project.service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sap.project.service.ClientService;
import sap.project.service.EmailSender;
import sap.project.service.StockService;
import sap.project.service.UserService;

import java.util.List;

@Service
public class EmailSenderImpl implements EmailSender {

    @Autowired
    private StockService stockService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private JavaMailSender javaMailSender;

        public void sendEmail(){
            List<Long> IDs = stockService.getIdLimitedQuantity();
            for(Long id: IDs){
                int quantity = stockService.getQuantityById(id);
                if( quantity <= 3 && quantity >= 0)
                    send(stockService.getDetailsById(id));
            }
        }


        private void send(String details) {

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(userService.getEmailFromAdminRole().toArray(new String[0]));

            String[] detailsHolder = details.split(",");

            msg.setSubject("Limited Quantity");
            msg.setText("Hello! Product {"+ detailsHolder[0] + "}" + " with ID:" + detailsHolder[1] + " has very low quantity!");

            javaMailSender.send(msg);
        }
}
