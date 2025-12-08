package library.model;

public enum Status {
    AVAILABLE("AVAILABLE"), // Доступна
    BORROWED("BORROWED"); // Выдана

    private final String dbValue;

    Status(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    // Метод для преобразования строки из БД в объект Status
    public static Status fromDbValue(String dbValue) {
        for (Status status : Status.values()) {
            if (status.dbValue.equalsIgnoreCase(dbValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status value: " + dbValue);
    }
}