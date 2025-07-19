package com.marcato.springmarcatoerp.service;

import com.marcato.springmarcatoerp.DTO.Views.ViewDTO;
import com.marcato.springmarcatoerp.DTO.Workspace.WorkspaceViewsDTO;
import com.marcato.springmarcatoerp.repository.WorkspaceRepository;
import org.jooq.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkspaceService
{

    private final WorkspaceRepository workspaceRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public List<WorkspaceViewsDTO>findWorkspaceViewsFromUser(Integer id) {
        Optional<Record2<UUID, ViewDTO[]>> workspaceResult = workspaceRepository.getWorkspaceAndViews(id).join();
        List<WorkspaceViewsDTO>workspaces = new ArrayList<>();
        if(workspaceResult.isEmpty()){
            return workspaces;
        }
        Map<UUID,ViewDTO>viewsMap = new HashMap<>();
        for(ViewDTO viewDTO : workspaceResult.get().component2()){
            viewsMap.put(viewDTO.viewID(),viewDTO);
        }
        workspaces.add(new WorkspaceViewsDTO(workspaceResult.get().component1(),viewsMap));
        return workspaces;
    }
}
