package vcs;

import filesystem.FileSystemSnapshot;

class Commits {
    private Integer id;
    private String commitMsg;
    private FileSystemSnapshot childSnapshot;

    /**
     * Creeaza obiecte de tip commit
     * @param id -> id-ul commit-ului
     * @param commitMsg -> mesajul cu care s-a dat commit
     * @param childSnapshot -> sistemul de fisiere asociat commit-ului
     *                      in momentul in care s-a dat commit
     */
    Commits(Integer id, String commitMsg, FileSystemSnapshot childSnapshot) {
        this.id = id;
        this.commitMsg = commitMsg;
        this.childSnapshot = childSnapshot;
    }

    Integer getCommitId() { return id; }
    String getCommitMsg() { return commitMsg; }
    FileSystemSnapshot getChildSnapshot() { return childSnapshot; }
}
