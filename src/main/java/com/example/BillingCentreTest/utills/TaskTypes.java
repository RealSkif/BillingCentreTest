package com.example.BillingCentreTest.utills;

import lombok.Getter;
/*
* Уровни приоритета поместил в Enum, связал с интом, чтобы можно было сортировать приоритеты независимо от
* самой строки. В дальнейшем типы приоритетов можно будет легко расширить по необходимости
* */
@Getter
public enum TaskTypes {
    IMMEDIATE(3),
    IMPORTANT(2),
    NORMAL(1);

    private final int value;

    TaskTypes(int value) {
        this.value = value;
    }

}
