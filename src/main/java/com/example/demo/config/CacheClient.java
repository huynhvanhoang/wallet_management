package com.example.demo.config;

import com.example.demo.model.AccountBalance;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.Predicates;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CacheClient {

    private final HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
    private static final String ACCOUNT_BALANCE = "ACCOUNT_BALANCE";

    public List<AccountBalance> getListAccountBalanceCache(Long startDate, Long endDate){
        IMap<Long, AccountBalance> map = hazelcastInstance.getMap(ACCOUNT_BALANCE);
        EntryObject e = new PredicateBuilder().getEntryObject();
        PredicateBuilder predicate = e.get("created_at").greaterEqual(startDate)
                .and(e.get("created_at").lessEqual(endDate));
        Collection<AccountBalance> accountBalances = map.values( predicate );
        return accountBalances.stream().collect(Collectors.toList());
    }

    public AccountBalance putAccountBalanceCache(AccountBalance accountBalance){
        IMap<Long, AccountBalance> map = hazelcastInstance.getMap(ACCOUNT_BALANCE);
        return map.putIfAbsent(accountBalance.getCreated_at(), accountBalance);
    }


}
