package de.haw.mps.banking.model;

import de.haw.mps.banking.entity.AccountMovementEntity;
import de.haw.mps.persistence.AbstractModel;

import java.util.Calendar;

public class AccountMovementModel extends AbstractModel<AccountMovementEntity> {

    public AccountMovementModel() {
        super(AccountMovementEntity.class);
    }

    public AccountMovementEntity create(int amount, String message) {
        AccountMovementEntity entity = new AccountMovementEntity();
        entity.setAmount(amount);
        entity.setMessage(message);
        entity.setBookingDate(Calendar.getInstance());

        return entity;
    }
}
