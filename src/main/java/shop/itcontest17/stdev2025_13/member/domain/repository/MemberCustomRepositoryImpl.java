package shop.itcontest17.stdev2025_13.member.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl {

    private final JPAQueryFactory queryFactory;
}
