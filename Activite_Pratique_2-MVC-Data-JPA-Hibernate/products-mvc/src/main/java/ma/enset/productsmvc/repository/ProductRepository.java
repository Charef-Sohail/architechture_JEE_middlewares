package ma.enset.productsmvc.repository;
import ma.enset.productsmvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
