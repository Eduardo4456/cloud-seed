package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.CarePlantModel;

public interface CareRepository extends JpaRepository<CarePlantModel, Long>{

}
