package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public class InvalidVcsOperation extends VcsOperation {
    public InvalidVcsOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * S-a incercat o operatie vcs invalida
     * @param vcs the vcs
     * @return ERROR code specific cand se incearca o operatie vcs invalid
     */
    @Override
    public int execute(Vcs vcs) {
        return ErrorCodeManager.VCS_BAD_CMD_CODE;
    }
}
