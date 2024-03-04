package com.trucdn.user.repositories;

import com.trucdn.user.helpers.RefreshableCRUDRepository;
import com.trucdn.user.models.UserInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;


@Repository
public interface UserRepository extends RefreshableCRUDRepository<UserInfo, Long> {

   UserInfo findByUsername(String username);
   UserInfo findFirstById(Long id);

   UserInfo findByEmail(String email);

   @Query("SELECT u FROM UserInfo u WHERE u.email = :email or u.username = :username or u.phoneNumber = :phoneNumber ")
   List<UserInfo> findUserByMultiParam(@Param("email") String email, @Param("username") String username, @Param("phoneNumber") String phoneNumber);

   @Modifying
   @Query("update UserInfo u set u.latestLogin = :now WHERE u.email = :email or u.username = :username or u.phoneNumber = :phoneNumber ")
   void updateLatestLogin(@Param(value = "now") Date now, @Param("email") String email, @Param("username") String username, @Param("phoneNumber") String phoneNumber);

   @Modifying
   @Query("update UserInfo u set u.latestLogin = :now WHERE u.id = :id ")
   void updateLatestLogin(@Param(value = "now") Date now, @Param("id") Long id);
}
