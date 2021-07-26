package com.lquirin.appRencontre.dao;

import com.lquirin.appRencontre.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusDao extends JpaRepository<Status, Integer> {
}
