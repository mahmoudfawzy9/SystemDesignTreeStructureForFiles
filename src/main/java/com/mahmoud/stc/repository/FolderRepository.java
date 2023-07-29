package com.mahmoud.stc.repository;

import com.mahmoud.stc.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
}
