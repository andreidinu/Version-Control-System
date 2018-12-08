package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutOperation extends VcsOperation {
    public CheckoutOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Daca intalnim comanda "checkout" cu un singur parametru (numele branch-ului),
     * ne mutam pe acel branch, daca exista si daca nu avem operatii in faza de "stage"
     *
     * Daca intalnim comanda "checkout" cu doi paramatri (primul fiind "-c"),
     * ne mutam inapoi la acel commit, toate commit-urile date dupa el fiind sterse
     *
     * @param vcs the vcs
     * @return OK code -> daca operatia de checkout s-a realizat cu succes
     * @return ERROR code -> intoarce codul specific erorii intampinate
     */
    @Override
    public int execute(Vcs vcs) {
        Map<String, List<Commits>> commits = vcs.getCommits();

        // Nu sunt operatii in faza de staging, nu are rost sa facem commit nou
        // pentru ca ar fi identic
        if (!vcs.getStagedOperations().isEmpty()) {
            return ErrorCodeManager.VCS_STAGED_OP_CODE;
        }

        // Trateaza operatiile de forma "vcs checkout <<brach name>>"
        if (getOperationArgs().size() == 1) {
            if (!commits.containsKey(getOperationArgs().get(0))) {
                return ErrorCodeManager.VCS_BAD_CMD_CODE;
            } else {
                vcs.setCurrentBranch(getOperationArgs().get(0));
                return ErrorCodeManager.OK;
            }

        // Trateaza operatiile de forma "vcs checkout -c <<id>>"
        } else if (getOperationArgs().size() == 2) {
            getOperationArgs().remove(0); // Removes "-c"

            Integer id = getOperationArgs().get(0).charAt(0) - '0';

            if (!vcs.getIdExists().containsKey(id)) {
                return ErrorCodeManager.VCS_BAD_PATH_CODE;
            }

            vcs.setActiveSnapshot(vcs.getIdExists().get(id).getChildSnapshot());

            // Elimin cheile mai mari ca id-ul commit-ului la care revin
            vcs.getIdExists().keySet().removeIf(n -> (n > id));
        }

        return ErrorCodeManager.OK;
    }
}
