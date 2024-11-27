package mark.demo.repo;

import mark.demo.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepo extends JpaRepository<Receipt, String> {
}
