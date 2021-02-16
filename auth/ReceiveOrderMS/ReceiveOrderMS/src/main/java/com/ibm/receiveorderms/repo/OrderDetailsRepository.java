package com.ibm.receiveorderms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.ibm.receiveorderms.model.OrderDetails;

@Component
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> { 

}
