package com.learning.todo.enumPackage;

public enum TaskStatus {
    Ongoing(1), Completed(2), Overdue(3);

    private final int value;

    TaskStatus(int no) {
        this.value = no;
    }

    public int getValue() {
        return value;
    }

    public static TaskStatus fromString(String num) {
        switch (num) {
            case "1":
                return Ongoing;
            case "2":
                return Completed;
            case "3":
                return Overdue;
            default:
                throw new IllegalArgumentException("Invalid TaskStatus value: " + num);
        }
    }
}

