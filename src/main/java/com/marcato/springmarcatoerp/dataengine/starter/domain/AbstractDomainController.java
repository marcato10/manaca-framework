package com.marcato.springmarcatoerp.dataengine.starter.domain;

import com.marcato.springmarcatoerp.DTO.API.UpdatePayload;
import com.marcato.springmarcatoerp.dataengine.core.iEditableDomain;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public abstract class AbstractDomainController<P,R extends Record> {
    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(AbstractDomainController.class);

    protected abstract iEditableDomain<P,R> getDomain();
    protected abstract DSLContext getDslContext();

    @MessageMapping(".ping")
    public Mono<String> ping() {
        logger.info("📨 Received ping request");
        return Mono.fromCallable(()->"pong");
    }

    @MessageMapping(".create")
    public Mono<P> create(@Payload P pojo) {
        return Mono.fromCallable(() -> getDomain().create(pojo))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(result -> logger.debug("✨ Created: {}", result))
                .doOnError(error -> logger.error("❌ Error creating: {}", error.getMessage()));
    }

    @MessageMapping(".read")
    public Mono<P> read(@DestinationVariable String id) {
        return Mono.fromCallable(() -> getDomain().read(id))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(result -> logger.debug("📖 Read: {}", id))
                .doOnError(error -> logger.error("❌ Error reading {}: {}", id, error.getMessage()));
    }

    @MessageMapping(".update")
    public Mono<P> update(@Payload UpdatePayload<P> payload) {
        return Mono.fromCallable(() -> getDomain().update(payload.id(), payload.data()))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(result -> logger.debug("📝 Updated: {}", payload.id()))
                .doOnError(error -> logger.error("❌ Error updating {}: {}", payload.id(), error.getMessage()));
    }

    @MessageMapping(".delete")
    public Mono<String> delete(@Payload String id) {
        return Mono.fromCallable(() -> getDomain().delete(id))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(result -> logger.debug("🗑️ Deleted: {}", id))
                .doOnError(error -> logger.error("❌ Error deleting {}: {}", id, error.getMessage()));
    }

    @MessageMapping(".list")
    public Flux<P> list() {
        logger.debug("Receiving request");
        return Flux.defer(()->{
            List<P> items = getDomain().getList();
            return Flux.fromIterable(items);
        }).subscribeOn(Schedulers.boundedElastic())
                .doOnComplete(() -> logger.debug("📋 Listed all items"))
                .doOnError(error -> logger.error("❌ Error listing: {}", error.getMessage()));
    }

}
