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
