package de.haw.mps.banking.model;

import de.haw.mps.banking.entity.AccountMovementEntity;
import de.haw.mps.persistence.AbstractModel;

public class AccountMovementModel extends AbstractModel<AccountMovementEntity> {

    public AccountMovementModel() {
        super(AccountMovementEntity.class);
    }
}
