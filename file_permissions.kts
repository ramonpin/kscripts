import kotlin.io.createTempDir
import java.io.File
import java.nio.file.*
import java.nio.file.attribute.PosixFilePermission as PFP
import java.nio.file.attribute.*

val ownerPermissions = arrayOf(PFP.OWNER_READ, PFP.OWNER_WRITE, PFP.OWNER_EXECUTE)
val groupPermissions = arrayOf(PFP.GROUP_READ, PFP.GROUP_WRITE, PFP.GROUP_EXECUTE)
val group = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByGroupName("geadatal")

val dir = createTempDir(directory = File("."))
Files.setPosixFilePermissions(dir.toPath(), setOf(*ownerPermissions, *groupPermissions))
Files.getFileAttributeView(dir.toPath(), PosixFileAttributeView::class.java, LinkOption.NOFOLLOW_LINKS).setGroup(group)

