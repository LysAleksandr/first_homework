package com.mipt.lysaleksandr.hometask_3.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FavoritesService {

    private static final String FAVORITES_SESSION_ATTR = "favoriteTaskIds";

    public void addFavorite(Long taskId, HttpSession session) {
        Set<Long> favorites = getFavoritesSet(session);
        favorites.add(taskId);
        session.setAttribute(FAVORITES_SESSION_ATTR, favorites);
    }

    public void removeFavorite(Long taskId, HttpSession session) {
        Set<Long> favorites = getFavoritesSet(session);
        favorites.remove(taskId);
        session.setAttribute(FAVORITES_SESSION_ATTR, favorites);
    }

    public Set<Long> getFavoriteIds(HttpSession session) {
        return getFavoritesSet(session);
    }

    @SuppressWarnings("unchecked")
    private Set<Long> getFavoritesSet(HttpSession session) {
        Object attr = session.getAttribute(FAVORITES_SESSION_ATTR);
        if (attr == null) {
            return new HashSet<>();
        }
        return (Set<Long>) attr;
    }
}