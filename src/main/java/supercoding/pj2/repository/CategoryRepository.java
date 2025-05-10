package supercoding.pj2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import supercoding.pj2.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

}
