package com.mahmoud.stc.repository;

import com.mahmoud.stc.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    boolean existsByFileUrl(String url);

    boolean existsByLocation(String uniqueLocation);

}
