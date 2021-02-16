/**
 * 
 */
package com.ibm.receiveorderms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.ibm.receiveorderms.model.ServiceToken;

@Component
public interface ServiceTokenRepository extends JpaRepository<ServiceToken, Integer> {

}
