package vcs;

import filesystem.FileSystemSnapshot;
import utils.ErrorCodeManager;
import utils.IDGenerator;
import utils.OperationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BranchOperation extends VcsOperation{
    public BranchOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     *
     * @param vcs the vcs
     * @return OK code -> daca branch-ul s-a creeat cu succes
     * @return ERROR code -> daca exista deja un branch cu acest nume
     */
    @Override
    public int execute(Vcs vcs) {
        String branch = getOperationArgs().get(0);

        // Deja exista un branch cu acest nume
        if (vcs.getCommits().containsKey(branch)) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        // Nu exista un branch cu acest nume, salvam configuratia curenta a sistemului de fisiere,
        // mapam branch-ul si ii creeam o lista de commit-uri
        } else {
            String commit = "First commit";
            FileSystemSnapshot child = vcs.getActiveSnapshot();
            Map<Integer, Commits> ids = vcs.getIdExists();

            Commits cmt = new Commits(IDGenerator.generateCommitID(), commit, child);
            List<Commits> cmtsList = new ArrayList<>();
            cmtsList.add(cmt);
            vcs.getCommits().put(branch, cmtsList);
            ids.put(cmt.getCommitId(), cmt);

            return ErrorCodeManager.OK;
        }
    }
}
