package com.mahmoud.stc.service.Impl;

import com.mahmoud.stc.entity.Space;
import com.mahmoud.stc.exception.SpaceNotFoundException;
import com.mahmoud.stc.repository.SpaceRepository;
import com.mahmoud.stc.service.SpaceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository spaceRepository;

    public SpaceServiceImpl(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    @Override
    public List<Space> getAllSpaces() {
        return spaceRepository.findAll();
    }

    @Override
    public Space getSpaceById(Long id) {
        return spaceRepository.findById(id)
                .orElseThrow(() -> new SpaceNotFoundException("Space not found with id " + id));
    }

    @Override
    public Space createSpace(Space space) {
        return spaceRepository.save(space);
    }

    @Override
    public Space updateSpace(Long id, Space space) {
        Space existingSpace = getSpaceById(id);
        existingSpace.setName(space.getName());
        existingSpace.setPermissionGroup(space.getPermissionGroup());
        return spaceRepository.save(existingSpace);    }

    @Override
    public void deleteSpace(Long id) {
        Space space = getSpaceById(id);
        if(space !=null)
        spaceRepository.delete(space);
    }
}
