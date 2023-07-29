package com.mahmoud.stc.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "space")
public class Space extends Item {
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Folder> folders = new HashSet<>();

    // getters and setters

    public Set<Folder> getFolders() {
        return folders;
    }

    public void setFolders(Set<Folder> folders) {
        this.folders = folders;
    }
}

