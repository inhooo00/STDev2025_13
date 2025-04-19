package shop.itcontest17.stdev2025_13.member.domain.repository;

import java.util.List;
import shop.itcontest17.stdev2025_13.member.domain.Member;

public interface MemberCustomRepository {
    List<Member> findTop10ByStreak();
}