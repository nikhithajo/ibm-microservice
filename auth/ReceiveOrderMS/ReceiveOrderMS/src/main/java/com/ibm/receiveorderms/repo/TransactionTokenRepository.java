/**
 * 
 */
package com.ibm.receiveorderms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.ibm.receiveorderms.model.TransactionToken;


@Component
public interface TransactionTokenRepository  extends JpaRepository<TransactionToken, Integer> {

}
