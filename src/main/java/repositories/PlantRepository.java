package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import model.PlantModel;

public interface PlantRepository extends JpaRepository<PlantModel, Long> {}