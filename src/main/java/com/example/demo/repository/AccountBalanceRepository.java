package com.example.demo.repository;

import com.example.demo.model.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {

    @Query(value = "select * from balance_history order by id desc limit 1", nativeQuery = true)
    AccountBalance findTopByOrderByIdDesc();

    @Query(value = "select * from balance_history where created_at >= ?1 and created_at <= ?2", nativeQuery = true)
    List<AccountBalance> findAllByRangeTime(long startDate, long endDate);

}
