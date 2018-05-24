package offersApp.service.email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {
   private SpecificManager specificManager;
   private ManagerSelector managerSelector;

   @Autowired
    public EmailServiceImpl( ManagerSelector managerSelector) {
        this.managerSelector = managerSelector;
    }

    @Override
    public void sendEmail(String mailType, Object objectDto) {
       specificManager = managerSelector.selectManager(mailType);
       specificManager.manage(objectDto);
    }
}
