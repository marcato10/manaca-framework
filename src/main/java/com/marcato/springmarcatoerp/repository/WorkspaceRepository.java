package com.marcato.springmarcatoerp.repository;

import com.marcato.springmarcatoerp.DTO.Views.ViewDTO;
import com.marcato.springmarcatoerp.entity.tables.Views;
import com.marcato.springmarcatoerp.entity.tables.Workspaces;
import com.marcato.springmarcatoerp.entity.tables.records.WorkspacesRecord;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.jooq.impl.DSL.*;

@Repository
public class WorkspaceRepository {
    private final DSLContext create;

    public WorkspaceRepository(DSLContext create) {
        this.create = create;
    }

    @Async("asyncVirtualThreadExecutor")
    public CompletableFuture<Optional<WorkspacesRecord>> getWorkspaceByUser(Integer userId){
        return CompletableFuture.completedFuture(create.selectFrom(Workspaces.WORKSPACES).where(Workspaces.WORKSPACES.USER_ID.eq(userId))
                .fetchOptional()
        );
    }

    @Async("asyncVirtualThreadExecutor")
    public CompletableFuture<Optional<Record2<UUID, ViewDTO[]>>> getWorkspaceAndViews(Integer userId){
        return CompletableFuture.completedFuture(
                create.select(
                        Workspaces.WORKSPACES.ID,
                                array(
                                        select(
                                                row(Views.VIEWS.VIEW_ID,
                                                Views.VIEWS.LABEL,
                                                Views.VIEWS.DATA,
                                                Views.VIEWS.ENTITY_KEY,
                                                Views.VIEWS.WORKSPACE_ID).mapping(ViewDTO.class,ViewDTO::new)).from(Views.VIEWS).where(Views.VIEWS.WORKSPACE_ID.eq(Workspaces.WORKSPACES.ID))
                                )
                        ).
                        from(Workspaces.WORKSPACES)
                        .where(Workspaces.WORKSPACES.USER_ID.eq(userId)).fetchOptional()
        );
    }

}
