package com.pvs.perfectresume.repository;

import com.pvs.perfectresume.model.Language;
import com.pvs.perfectresume.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Language findByLanguageIdAndUser(Integer languageId, User user);
}
