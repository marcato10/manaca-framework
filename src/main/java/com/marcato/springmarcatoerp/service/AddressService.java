package com.marcato.springmarcatoerp.service;

import com.fasterxml.uuid.Generators;
import com.marcato.springmarcatoerp.entity.tables.Address;
import com.marcato.springmarcatoerp.entity.tables.pojos.AddressDTO;
import com.marcato.springmarcatoerp.entity.tables.records.AddressRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class AddressService {

    private final DSLContext create;

    public AddressService(DSLContext create) {
        this.create = create;
    }

    public Future<Optional<AddressRecord>> getAddressByUUID(UUID uuid){
        return CompletableFuture.completedFuture(Optional.ofNullable(create.selectFrom(Address.ADDRESS).where(Address.ADDRESS.ID.eq(uuid)).fetchOne()));
    }

    public Future<List<AddressRecord>> getAddressesByCountry(String country){
        return CompletableFuture.completedFuture(create.selectFrom(Address.ADDRESS).where(Address.ADDRESS.COUNTRY.eq(country)).fetch());
    }

    public Future<Integer> registerAddress(AddressDTO addressDTO){
        addressDTO.setId(Generators.timeBasedEpochGenerator().generate());
        AddressRecord address = new AddressRecord(addressDTO);
        return CompletableFuture.completedFuture(address.store());
    }
}
