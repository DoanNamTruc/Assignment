package com.trucdn.promotion.repositories;

import com.trucdn.promotion.common.EventStatus;
import com.trucdn.promotion.models.PromotionEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PromotionEventRepository extends CrudRepository<PromotionEvent, Long> {
   @Query("SELECT u FROM PromotionEvent u WHERE u.status = :status")
   List<PromotionEvent> findPromotionsByStatusAndStartDate(@Param("status") EventStatus status);
}
