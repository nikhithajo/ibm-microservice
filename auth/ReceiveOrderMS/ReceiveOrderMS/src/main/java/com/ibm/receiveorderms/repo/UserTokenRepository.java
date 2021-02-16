/**
 * 
 */
package com.ibm.receiveorderms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


import com.ibm.receiveorderms.model.UserToken;



@Component
public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {

}
