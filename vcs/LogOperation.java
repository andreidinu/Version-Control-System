package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;
import java.util.List;

public class LogOperation extends VcsOperation {
    public LogOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Se listeaza toate commit-urile de pe branch-ul current (id + commit message)
     * @param vcs the vcs
     * @return OK code -> cand se termina listarea log-ului
     */
    @Override
    public int execute(Vcs vcs) {
        String crtBranch = vcs.getCurrentBranch();
        List<Commits> commitsList = vcs.getCommits().get(crtBranch);

        int size = commitsList.size();
        for (int i = 0; i < size - 1; ++i) {
            vcs.getOutputWriter().write("Commit id: " + commitsList.get(i).getCommitId() + "\n");
            vcs.getOutputWriter().write("Message: " + commitsList.get(i).getCommitMsg() + "\n\n");
        }

        vcs.getOutputWriter().write("Commit id: " + commitsList.get(size - 1).getCommitId() + "\n");
        vcs.getOutputWriter().write("Message: " + commitsList.get(size - 1).getCommitMsg() + "\n");

        return ErrorCodeManager.OK;
    }
}
