val tmp = java.io.File(".")
val tmpDir = createTempDir(prefix = "BASURA.", directory = tmp.absoluteFile)
Thread.sleep(10000)
tmpDir.deleteOnExit()

