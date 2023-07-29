package com.mahmoud.stc.service;

import com.mahmoud.stc.entity.Space;

import java.util.List;

public interface SpaceService {

    public List<Space> getAllSpaces();

    public Space getSpaceById(Long id);

    public Space createSpace(Space space);

    public Space updateSpace(Long id, Space space);

    public void deleteSpace(Long id);

}