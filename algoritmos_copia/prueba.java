File repoDir = new File("test-git");
// open the repository
Repository repository = new Repository(repoDir);
// find the HEAD
ObjectId lastCommitId = repository.resolve(Constants.HEAD);
// now we have to get the commit
RevWalk revWalk = new RevWalk(repository);
RevCommit commit = revWalk.parseCommit(lastCommitId);
// and using commit's tree find the path
RevTree tree = commit.getTree();
TreeWalk treeWalk = new TreeWalk(repository);
treeWalk.addTree(tree);
treeWalk.setRecursive(true);
treeWalk.setFilter(PathFilter.create(path));
if (!treeWalk.next()) {
  return null;
}
ObjectId objectId = treeWalk.getObjectId(0);
ObjectLoader loader = repository.open(objectId);

private String fetchBlob(String revSpec, String path) throws MissingObjectException, IncorrectObjectTypeException,
        IOException {

    // Resolve the revision specification
    final ObjectId id = this.repo.resolve(revSpec);

    // Makes it simpler to release the allocated resources in one go
    ObjectReader reader = this.repo.newObjectReader();

    try {
        // Get the commit object for that revision
        RevWalk walk = new RevWalk(reader);
        RevCommit commit = walk.parseCommit(id);

        // Get the revision's file tree
        RevTree tree = commit.getTree();
        // .. and narrow it down to the single file's path
        TreeWalk treewalk = TreeWalk.forPath(reader, path, tree);

        if (treewalk != null) {
            // use the blob id to read the file's data
            byte[] data = reader.open(treewalk.getObjectId(0)).getBytes();
            return new String(data, "utf-8");
        } else {
            return "";
        }
    } finally {
        reader.close();
    }
}
