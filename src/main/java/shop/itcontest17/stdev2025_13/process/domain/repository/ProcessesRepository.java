package shop.itcontest17.stdev2025_13.process.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itcontest17.stdev2025_13.process.domain.Processes;

public interface ProcessesRepository extends JpaRepository<Processes, Long>, ProcessesCustomRepository {
}
