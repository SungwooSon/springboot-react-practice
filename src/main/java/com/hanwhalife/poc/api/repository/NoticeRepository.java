package com.hanwhalife.poc.api.repository;

import com.hanwhalife.poc.api.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>, JpaSpecificationExecutor<Notice>, NoticeRepositoryCustom {

    List<Notice> findByTitle(String title);

}
