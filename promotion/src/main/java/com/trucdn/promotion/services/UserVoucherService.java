package com.trucdn.promotion.services;

import com.trucdn.promotion.dtos.VoucherRequest;
import com.trucdn.promotion.dtos.VoucherResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserVoucherService {

    VoucherResponse saveVoucher(VoucherRequest req);

}
