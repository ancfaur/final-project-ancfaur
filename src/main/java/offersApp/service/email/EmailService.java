package offersApp.service.email;

public interface EmailService<T> {
   void sendEmail(String mailType, T t);
}
