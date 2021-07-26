package com.lquirin.appRencontre.dao;

import com.lquirin.appRencontre.model.TitreJeux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitreJeuxDao extends JpaRepository<TitreJeux, Integer> {
}
