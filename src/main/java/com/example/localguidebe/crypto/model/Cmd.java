package com.example.localguidebe.crypto.model;

import lombok.Getter;

@Getter
public enum Cmd {
    /**
     * Extend this for other Cmd calls
     */
    create_transaction("create_transaction"),
    DEPOSIT_ADDRESS("get_deposit_address"),
    get_callback_address("get_callback_address"),
    rates("rates ");

    /* Name used on the enumeration */
    public final String cmd;

    Cmd(String cmd) {
        this.cmd = cmd;
    }
}
