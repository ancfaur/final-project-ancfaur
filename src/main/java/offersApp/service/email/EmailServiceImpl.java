package offersApp.service.email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl<T> implements EmailService<T> {
   private SpecificManager specificManager;
   private ManagerSelector managerSelector;

   @Autowired
    public EmailServiceImpl( ManagerSelector managerSelector) {
        this.managerSelector = managerSelector;
    }

    @Override
    public void sendEmail(String mailType, T t) {
       specificManager = managerSelector.selectManager(mailType);
        specificManager.manage(t);
    }
}
