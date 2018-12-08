package utils;

public enum OperationType {
    // FileSystem commands
    CAT,
    CHANGEDIR,
    LIST,
    MAKEDIR,
    REMOVE,
    TOUCH,
    WRITETOFILE,
    PRINT,
    FILESYSTEM_INVALID_OPERATION,
    // vcs commands
    STATUS,
    BRANCH,
    COMMIT,
    CHECKOUT,
    LOG,
    ROLLBACK,
    VCS_INVALID_OPERATION
}
