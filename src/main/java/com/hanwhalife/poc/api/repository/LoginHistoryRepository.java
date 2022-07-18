package com.hanwhalife.poc.api.repository;

import com.hanwhalife.poc.api.domain.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
}
