for (slave in hudson.model.Hudson.instance.slaves) {
  computer = slave.computer
  nodeName = slave.name

  println "Checking the status of " + nodeName

  if (computer.isOffline()) {
    println "Node " + nodeName + " is offline"

    offlineBecauseDiskSpace = computer.getOfflineCause() instanceof hudson.node_monitors.DiskSpaceMonitorDescriptor$DiskSpace
    if (!offlineBecauseDiskSpace) { continue; }

    println "Offline due to disk space"

    workspaceRoot = slave.getWorkspaceRoot();
    println "Deleting contents of workspace directory: " +  workspaceRoot

    for (directory in workspaceRoot.listDirectories()) {
      println "Deleting " + directory 
      directory.deleteRecursive()
   }
   workspaceRoot.deleteContents()

   println "Putting " + computer + " back online"
   computer.setTemporarilyOffline(false, null)
  }
}
