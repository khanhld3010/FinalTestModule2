import repository.PhoneRepo;
import repository.PhoneRepositoryImpl;
import service.PhoneService;
import service.PhoneServiceImpl;
import view.PhoneView;

public class Main {
    static void main(String[] args) {
        PhoneRepo phoneRepository = new PhoneRepositoryImpl();
        PhoneService phoneService = new PhoneServiceImpl(phoneRepository);
        PhoneView app = new PhoneView(phoneService);
        app.start();
    }
}
