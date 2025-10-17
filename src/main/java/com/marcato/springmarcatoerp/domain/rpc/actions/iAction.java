package com.marcato.springmarcatoerp.domain.rpc.actions;

import com.fasterxml.jackson.databind.JsonNode;

public interface iAction<P> {
    String getActionName();
    Object execute(P domainPojo,JsonNode node);
}