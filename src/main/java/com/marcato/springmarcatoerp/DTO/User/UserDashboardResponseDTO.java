package com.marcato.springmarcatoerp.DTO.User;

import com.marcato.springmarcatoerp.DTO.Workspace.WorkspaceViewsDTO;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class UserDashboardResponseDTO extends RepresentationModel<UserDashboardResponseDTO> {
    final UserDTO user;
    final List<WorkspaceViewsDTO> workspaces;

    public UserDashboardResponseDTO(UserDTO user, List<WorkspaceViewsDTO> workspaces) {
        this.user = user;
        this.workspaces = workspaces;
    }

    
}
