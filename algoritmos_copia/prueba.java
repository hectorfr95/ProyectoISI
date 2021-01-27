@Override
public byte[] getCommitContent(String projectRoot, String filepath, String commitHash)
    throws UnableToGetCommitContentException {
  if (filepath.isEmpty()) {
    return new byte[0];
  }
  try (Git git = Git.open(new java.io.File(projectRoot))) {
    ObjectId commitId = git.getRepository().resolve(commitHash);
    return BlobUtils.getRawContent(git.getRepository(), commitId, filepath);
  } catch (IOException e) {
    throw new UnableToGetCommitContentException(e.getMessage());
  }
}
