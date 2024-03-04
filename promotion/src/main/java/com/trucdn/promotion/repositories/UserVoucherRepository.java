package com.trucdn.promotion.repositories;

import com.trucdn.promotion.models.PromotionEvent;
import com.trucdn.promotion.models.UserVoucher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface UserVoucherRepository extends CrudRepository<UserVoucher, Long> {
}
