package com.trucdn.promotion.services.impl;

import com.trucdn.promotion.dtos.EventResponse;
import com.trucdn.promotion.dtos.VoucherRequest;
import com.trucdn.promotion.dtos.VoucherResponse;
import com.trucdn.promotion.models.PromotionEvent;
import com.trucdn.promotion.models.UserVoucher;
import com.trucdn.promotion.repositories.PromotionEventRepository;
import com.trucdn.promotion.repositories.UserVoucherRepository;
import com.trucdn.promotion.services.UserVoucherService;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserVoucherServiceImpl implements UserVoucherService {

    @Autowired
    UserVoucherRepository userVoucherRepository;
    ModelMapper modelMapper = new ModelMapper();
    @Override
    public VoucherResponse saveVoucher(VoucherRequest req) {
        UserVoucher entity = modelMapper.map(req, UserVoucher.class);
        entity = userVoucherRepository.save(entity);
        return modelMapper.map(entity, VoucherResponse.class);
    }
}
