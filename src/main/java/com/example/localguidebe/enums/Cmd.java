package com.example.localguidebe.enums;

import lombok.Getter;

@Getter
public enum Cmd {
    /**
     * Extend this for other Cmd calls
     */
    DEPOSIT_ADDRESS("get_deposit_address"),
    get_callback_address("get_callback_address"),
    rates("rates "),
    create_withdrawal("create_withdrawal"),
    get_withdrawal_info("get_withdrawal_info");

    /* Name used on the enumeration */
    public final String cmd;

    Cmd(String cmd) {
        this.cmd = cmd;
    }
}
