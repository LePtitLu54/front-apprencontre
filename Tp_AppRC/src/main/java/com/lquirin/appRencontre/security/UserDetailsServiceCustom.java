package com.lquirin.appRencontre.security;


import com.lquirin.appRencontre.dao.UtilisateurDao;
import com.lquirin.appRencontre.model.Utilisateur;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UtilisateurDao utilisateurDao;

    public UserDetailsServiceCustom(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    @Override
    public UserDetails loadUserByUsername(String pseudoSaisi) throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurDao
                .trouverParPseudoAvecRoles(pseudoSaisi)
                .orElseThrow(() -> new UsernameNotFoundException(pseudoSaisi + " inconnu"));

        return new UserDetailsCustom(utilisateur);
    }
}
