package vcs;

import filesystem.FileSystemSnapshot;
import utils.ErrorCodeManager;
import utils.IDGenerator;
import utils.OperationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommitOperation extends VcsOperation {
    public CommitOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Se salveaza modificarile aduse sistemului de fisiere, se goleste staging-ul
     * si se genereaza un nou commit insotit de un mesaj care descrie schimbarile aduse
     * @param vcs the vcs
     * @return OK code -> commit-ul s-a facut cu succes
     * @return ERROR code -> nu avem operatii in faza de staging deci commit-ul nu ar
     *                     aduce nicio modificare => nu are rost sa facem commit
     */
    @Override
    public int execute(Vcs vcs) {
        // Verific daca am operatii in staging
        if(vcs.getStagedOperations().isEmpty()) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        // Creez un commit nou care va fi un obiect ce va avea un id generat automat,
        // un commit message si configuratia actuala a sistemului de fisiere, apoi
        // golesc staging-ul
        } else {
            String crtBranch = vcs.getCurrentBranch();
            Map<String, List<Commits>> commits = vcs.getCommits();
            Map<Integer, Commits> ids = vcs.getIdExists();

            String cmtMsg = "";
            // Daca branch-ul nu e mapat, il punem in Map
            if (!commits.containsKey(crtBranch)) {
                cmtMsg = "First commit";
                FileSystemSnapshot child = vcs.getActiveSnapshot().cloneFileSystem();
                Commits cmt = new Commits(IDGenerator.generateCommitID(), cmtMsg, child);
                List<Commits> commitsList = new ArrayList<>();
                commitsList.add(cmt);
                commits.put(crtBranch, commitsList);
                ids.put(cmt.getCommitId(), cmt);
                vcs.getStagedOperations().clear();

                return ErrorCodeManager.OK;
            }

            FileSystemSnapshot child = vcs.getActiveSnapshot().cloneFileSystem();
            cmtMsg = String.join(" ", getOperationArgs());
            cmtMsg = cmtMsg.replace("-m ", "");
            Commits cmt = new Commits(IDGenerator.generateCommitID(), cmtMsg, child);

            commits.get(crtBranch).add(cmt);
            ids.put(cmt.getCommitId(), cmt);
            vcs.getStagedOperations().clear();

            return ErrorCodeManager.OK;
        }
    }
}
