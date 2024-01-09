package com.learning.todo.enumPackage;

public enum Priority {
    Low(1), Medium(2), Urgent(3);

    private final int value;
    Priority(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
