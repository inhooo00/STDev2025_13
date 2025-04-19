package shop.itcontest17.stdev2025_13.process.domain.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.itcontest17.stdev2025_13.member.api.dto.response.EmotionCountResDto;
import shop.itcontest17.stdev2025_13.member.api.dto.response.ArchiveResDto;
import shop.itcontest17.stdev2025_13.member.domain.Member;
import shop.itcontest17.stdev2025_13.process.domain.Processes;

public interface ProcessesRepository extends JpaRepository<Processes, Long>, ProcessesCustomRepository {
    @Query("SELECT new shop.itcontest17.stdev2025_13.member.api.dto.response.EmotionCountResDto(p.emotion, COUNT(p)) " +
            "FROM Processes p " +
            "WHERE p.member.email = :email " +
            "GROUP BY p.emotion " +
            "ORDER BY COUNT(p) DESC")
    List<EmotionCountResDto> findTop5EmotionsByEmail(@Param("email") String email, Pageable pageable);

    @Query("SELECT new shop.itcontest17.stdev2025_13.member.api.dto.response.ArchiveResDto(p.createdAt,p.summaryTitle) "
            +
            "FROM Processes p " +
            "WHERE p.member.email = :email AND p.emotion = :emotion")
    List<ArchiveResDto> findSummaryTitleByEmailAndEmotion(@Param("email") String email, @Param("emotion") String emotion);

    Processes findBySummaryTitle(String summaryTitle);

    List<Processes> findAllByMember(Member member);
}
