package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;
import java.util.Map;

public class RollbackOperation extends VcsOperation {
    public RollbackOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Goleste operatiile aflate in faza de staging si se revine la configuratia
     * din ultimul commit (nu mai avem nimic in staging)
     * @param vcs the vcs
     * @return OK code -> s-a facut rollback cu succes
     */
    @Override
    public int execute (Vcs vcs) {
        vcs.getStagedOperations().clear();

        Map<Integer, Commits> ids = vcs.getIdExists();
        Integer latest = 1;
        for (Integer id : ids.keySet()) {
            if (latest < id) {
                latest = id;
            }
        }
        vcs.setActiveSnapshot(vcs.getIdExists().get(latest).getChildSnapshot());

        return ErrorCodeManager.OK;
    }
}
