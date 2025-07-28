package com.marcato.springmarcatoerp.repository;

import com.marcato.springmarcatoerp.DTO.Views.ViewDTO;
import com.marcato.springmarcatoerp.jooq.tables.View;
import com.marcato.springmarcatoerp.jooq.tables.Workspace;
import com.marcato.springmarcatoerp.jooq.tables.records.WorkspaceRecord;
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
    public CompletableFuture<Optional<WorkspaceRecord>> getWorkspaceByUser(Integer userId){
        return CompletableFuture.completedFuture(create.selectFrom(Workspace.WORKSPACE).where(Workspace.WORKSPACE.USER_ERP_ID.eq(userId))
                .fetchOptional()
        );
    }

    @Async("asyncVirtualThreadExecutor")
    public CompletableFuture<Optional<Record2<UUID, ViewDTO[]>>> getWorkspaceAndViews(Integer userId){
        return CompletableFuture.completedFuture(
                create.select(
                        Workspace.WORKSPACE.ID,
                                array(
                                        select(
                                                row(View.VIEW.VIEW_ID,
                                                View.VIEW.LABEL,
                                                View.VIEW.DATA,
                                                View.VIEW.ENTITY_KEY,
                                                View.VIEW.WORKSPACE_ID).mapping(ViewDTO.class,ViewDTO::new)).from(View.VIEW).where(View.VIEW.WORKSPACE_ID.eq(Workspace.WORKSPACE.ID))
                                )
                        ).
                        from(Workspace.WORKSPACE)
                        .where(Workspace.WORKSPACE.USER_ERP_ID.eq(userId)).fetchOptional()
        );
    }

}
