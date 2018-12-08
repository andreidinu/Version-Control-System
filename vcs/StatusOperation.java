package vcs;

import utils.*;

import java.util.ArrayList;

public class StatusOperation extends VcsOperation {
    public StatusOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Ia fiecare element care este in faza "stage" si afiseaza mesajul corespunzator
     * in functie de comanda (touch, mkdir, etc)
     * @param vcs the vcs
     * @return OK code -> operatiune efectuata cu succes
     */
    @Override
    public int execute(Vcs vcs) {
        vcs.getOutputWriter().write("On branch: " + vcs.getCurrentBranch() + "\n");
        vcs.getOutputWriter().write("Staged changes:\n");

        for (String cmd : vcs.getStagedOperations()) {
            if (cmd.contains("touch")) {
                cmd = cmd.replace("touch ", "");
                vcs.getOutputWriter().write("\t" + "Created file " + cmd + "\n");
            } else if (cmd.contains("mkdir")) {
                cmd = cmd.replace("mkdir ", "");
                vcs.getOutputWriter().write("\t" + "Created directory " + cmd + "\n");
            } else if (cmd.contains("rm")) {
                cmd = cmd.replace("rm ", "");
                vcs.getOutputWriter().write("\t" + "Removed " + cmd + "\n");
            } else if (cmd.contains("rmdir")) {
                cmd = cmd.replace("rmdir", "");
                vcs.getOutputWriter().write("\t" + "Removed " + cmd + "\n");
            } else if (cmd.contains("writetofile")) {
                cmd = cmd.replace("writetofile ", "");
                int index = cmd.indexOf(' ');
                String file = cmd.substring(0, index);
                cmd = cmd.substring(index + 1);
                vcs.getOutputWriter().write("\t" + "Added " + "\"" + cmd + "\"" + " to file " + file + "\n");
            } else if (cmd.contains("cd")) {
                cmd = cmd.replace("cd ", "");
                vcs.getOutputWriter().write("\t" + "Changed directory to " + cmd);
            }
        }

        return ErrorCodeManager.OK;
    }
}
