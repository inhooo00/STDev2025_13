package shop.itcontest17.stdev2025_13.member.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import shop.itcontest17.stdev2025_13.member.api.dto.response.MemberNameResDto;
import shop.itcontest17.stdev2025_13.member.domain.Member;
import shop.itcontest17.stdev2025_13.process.domain.Processes;

public interface MemberRepository extends
        JpaRepository<Member, Long>,
        JpaSpecificationExecutor<Member> {
    Optional<Member> findByEmail(String email);

    MemberNameResDto findNameByEmail(String email);
}