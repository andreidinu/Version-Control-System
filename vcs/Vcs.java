package vcs;

import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;
import utils.IDGenerator;
import utils.OutputWriter;
import utils.Visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Vcs implements Visitor {
    private final OutputWriter outputWriter;
    private FileSystemSnapshot activeSnapshot;
    private List<String> stagedOperations = new ArrayList<>();
    private String currentBranch = "";
    private Map<String, List<Commits>> commits = new HashMap<>();
    private Map<Integer, Commits> idExists = new HashMap<>();

    /**
     * Vcs constructor.
     *
     * @param outputWriter the output writer
     */
    public Vcs(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    /**
     * Does initialisations.
     */
    public void init() {
        this.activeSnapshot = new FileSystemSnapshot(outputWriter);

        //TODO other initialisations
        this.currentBranch = "master";
        String cmtMsg = "First commit";
        Commits cmt = new Commits(IDGenerator.generateCommitID(), cmtMsg, activeSnapshot.cloneFileSystem());

        List<Commits> cmtsList = new ArrayList<>();
        cmtsList.add(cmt);

        commits.put(this.currentBranch, cmtsList);
        idExists.put(cmt.getCommitId(), cmt);
    }

    /**
     * Visits a file system operation.
     *
     * @param fileSystemOperation the file system operation
     * @return the return code
     */
    public int visit(FileSystemOperation fileSystemOperation) {
        return fileSystemOperation.execute(this.activeSnapshot);
    }

    /**
     * Visits a vcs operation.
     *
     * @param vcsOperation the vcs operation
     * @return return code
     */
    @Override
    public int visit(VcsOperation vcsOperation) {
        return vcsOperation.execute(this);
    }

    //TODO methods through which vcs operations interact with this

    OutputWriter getOutputWriter() { return outputWriter; }
    public List<String> getStagedOperations() {
        return stagedOperations;
    }
    String getCurrentBranch() { return currentBranch; }
    void setCurrentBranch(String currentBranch) {
        this.currentBranch = currentBranch;
    }
    Map<Integer, Commits> getIdExists() { return idExists; }
    Map<String, List<Commits>> getCommits() { return commits; }
    FileSystemSnapshot getActiveSnapshot() { return activeSnapshot; }
    void setActiveSnapshot(FileSystemSnapshot snap) { this.activeSnapshot = snap; }
}
