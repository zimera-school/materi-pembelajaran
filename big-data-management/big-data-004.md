# HDFS - Hadoop Distributed File System

Sesi ini digunakan untuk memahami lapisan penyimpanan data secara terdistribusi menggunakan Hadoop yaitu HDFS. Pada bagian ini, pemahaman untuk mengakses HDFS akan didasarkan pada penggunakan CLI (*command line interface*) / shell maupun dari sisi pemrograman.

## Mengenal HDFS

HDFS merupakan *filesystem* terdistribusi yang memang dimaksudkan untuk digunakan pada *commodity hardware*. HDFS merupakan *filesystem* terdistribusi yang *fault-tolerant* (diperoleh dengan cara mendistribusikan block file ke berbagai node untuk mencegah *single point of failure*). Konsep tersebut dikenal dengan istilah **replikasi**.

## Arsitektur HDFS

Tim dari Apache Hadoop mendefinisikan [arsitektur HDFS](https://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-hdfs/HdfsDesign.html)  sebagai berikut:

![Arsitektur HDFS](images/arsitektur-hdfs.png).

Suatu *cluster* HDFS terdiri atas 2 bagian utama:

1.  *Namenode*: mengelola metadata dari *filesystem*, hanya ada 1 untuk setiap *cluster*
2.  *Datanodes*: mengelola dan menyimpan data sesungguhnya (dalam bentuk berbagai blok), setiap node biasanya berisi satu.

![Replikasi data di Hadoop](images/hadoop-block.png).

## Akses ke HDFS

Akses ke HDFS bisa dilakukan dengan menggunakan berbagai macam cara, baik secara pemrograman maupun secara shell. Bagian ini akan menjelaskan cara-cara tersebut.

### Akses Menggunakan *FS Shell*

Untuk mengakses filesystem HDFS, bisa digunakan `hadoop fs <args>` atau `hdfs dfs <args>`. 

```bash
$ hdfs dfs -help
Usage: hadoop fs [generic options]
	[-appendToFile <localsrc> ... <dst>]
	[-cat [-ignoreCrc] <src> ...]
	[-checksum <src> ...]
	[-chgrp [-R] GROUP PATH...]
	[-chmod [-R] <MODE[,MODE]... | OCTALMODE> PATH...]
	[-chown [-R] [OWNER][:[GROUP]] PATH...]
	[-copyFromLocal [-f] [-p] [-l] [-d] [-t <thread count>] <localsrc> ... <dst>]
	[-copyToLocal [-f] [-p] [-ignoreCrc] [-crc] <src> ... <localdst>]
	[-count [-q] [-h] [-v] [-t [<storage type>]] [-u] [-x] [-e] <path> ...]
	[-cp [-f] [-p | -p[topax]] [-d] <src> ... <dst>]
	[-createSnapshot <snapshotDir> [<snapshotName>]]
	[-deleteSnapshot <snapshotDir> <snapshotName>]
	[-df [-h] [<path> ...]]
	[-du [-s] [-h] [-v] [-x] <path> ...]
	[-expunge [-immediate]]
	[-find <path> ... <expression> ...]
	[-get [-f] [-p] [-ignoreCrc] [-crc] <src> ... <localdst>]
	[-getfacl [-R] <path>]
	[-getfattr [-R] {-n name | -d} [-e en] <path>]
	[-getmerge [-nl] [-skip-empty-file] <src> <localdst>]
	[-head <file>]
	[-help [cmd ...]]
	[-ls [-C] [-d] [-h] [-q] [-R] [-t] [-S] [-r] [-u] [-e] [<path> ...]]
	[-mkdir [-p] <path> ...]
	[-moveFromLocal <localsrc> ... <dst>]
	[-moveToLocal <src> <localdst>]
	[-mv <src> ... <dst>]
	[-put [-f] [-p] [-l] [-d] <localsrc> ... <dst>]
	[-renameSnapshot <snapshotDir> <oldName> <newName>]
	[-rm [-f] [-r|-R] [-skipTrash] [-safely] <src> ...]
	[-rmdir [--ignore-fail-on-non-empty] <dir> ...]
	[-setfacl [-R] [{-b|-k} {-m|-x <acl_spec>} <path>]|[--set <acl_spec> <path>]]
	[-setfattr {-n name [-v value] | -x name} <path>]
	[-setrep [-R] [-w] <rep> <path> ...]
	[-stat [format] <path> ...]
	[-tail [-f] [-s <sleep interval>] <file>]
	[-test -[defswrz] <path>]
	[-text [-ignoreCrc] <src> ...]
	[-touch [-a] [-m] [-t TIMESTAMP ] [-c] <path> ...]
	[-touchz <path> ...]
	[-truncate [-w] <length> <path> ...]
	[-usage [cmd ...]]

-appendToFile <localsrc> ... <dst> :
  Appends the contents of all the given local files to the given dst file. The dst
  file will be created if it does not exist. If <localSrc> is -, then the input is
  read from stdin.

-cat [-ignoreCrc] <src> ... :
  Fetch all files that match the file pattern <src> and display their content on
  stdout.

-checksum <src> ... :
  Dump checksum information for files that match the file pattern <src> to stdout.
  Note that this requires a round-trip to a datanode storing each block of the
  file, and thus is not efficient to run on a large number of files. The checksum
  of a file depends on its content, block size and the checksum algorithm and
  parameters used for creating the file.

-chgrp [-R] GROUP PATH... :
  This is equivalent to -chown ... :GROUP ...

-chmod [-R] <MODE[,MODE]... | OCTALMODE> PATH... :
  Changes permissions of a file. This works similar to the shell's chmod command
  with a few exceptions.

  -R           modifies the files recursively. This is the only option currently
               supported.
  <MODE>       Mode is the same as mode used for the shell's command. The only
               letters recognized are 'rwxXt', e.g. +t,a+r,g-w,+rwx,o=r.
  <OCTALMODE>  Mode specifed in 3 or 4 digits. If 4 digits, the first may be 1 or
               0 to turn the sticky bit on or off, respectively.  Unlike the
               shell command, it is not possible to specify only part of the
               mode, e.g. 754 is same as u=rwx,g=rx,o=r.

  If none of 'augo' is specified, 'a' is assumed and unlike the shell command, no
  umask is applied.

-chown [-R] [OWNER][:[GROUP]] PATH... :
  Changes owner and group of a file. This is similar to the shell's chown command
  with a few exceptions.

  -R  modifies the files recursively. This is the only option currently
      supported.

  If only the owner or group is specified, then only the owner or group is
  modified. The owner and group names may only consist of digits, alphabet, and
  any of [-_./@a-zA-Z0-9]. The names are case sensitive.

  WARNING: Avoid using '.' to separate user name and group though Linux allows it.
  If user names have dots in them and you are using local file system, you might
  see surprising results since the shell command 'chown' is used for local files.

-copyFromLocal [-f] [-p] [-l] [-d] [-t <thread count>] <localsrc> ... <dst> :
  Copy files from the local file system into fs. Copying fails if the file already
  exists, unless the -f flag is given.
  Flags:

  -p                 Preserves access and modification times, ownership and the
                     mode.
  -f                 Overwrites the destination if it already exists.
  -t <thread count>  Number of threads to be used, default is 1.
  -l                 Allow DataNode to lazily persist the file to disk. Forces
                     replication factor of 1. This flag will result in reduced
                     durability. Use with care.
  -d                 Skip creation of temporary file(<dst>._COPYING_).

-copyToLocal [-f] [-p] [-ignoreCrc] [-crc] <src> ... <localdst> :
  Identical to the -get command.

-count [-q] [-h] [-v] [-t [<storage type>]] [-u] [-x] [-e] <path> ... :
  Count the number of directories, files and bytes under the paths
  that match the specified file pattern.  The output columns are:
  DIR_COUNT FILE_COUNT CONTENT_SIZE PATHNAME
  or, with the -q option:
  QUOTA REM_QUOTA SPACE_QUOTA REM_SPACE_QUOTA
        DIR_COUNT FILE_COUNT CONTENT_SIZE PATHNAME
  The -h option shows file sizes in human readable format.
  The -v option displays a header line.
  The -x option excludes snapshots from being calculated.
  The -t option displays quota by storage types.
  It should be used with -q or -u option, otherwise it will be ignored.
  If a comma-separated list of storage types is given after the -t option,
  it displays the quota and usage for the specified types.
  Otherwise, it displays the quota and usage for all the storage
  types that support quota. The list of possible storage types(case insensitive):
  ram_disk, ssd, disk and archive.
  It can also pass the value '', 'all' or 'ALL' to specify all the storage types.
  The -u option shows the quota and
  the usage against the quota without the detailed content summary.The -e option
  shows the erasure coding policy.

-cp [-f] [-p | -p[topax]] [-d] <src> ... <dst> :
  Copy files that match the file pattern <src> to a destination.  When copying
  multiple files, the destination must be a directory. Passing -p preserves status
  [topax] (timestamps, ownership, permission, ACLs, XAttr). If -p is specified
  with no <arg>, then preserves timestamps, ownership, permission. If -pa is
  specified, then preserves permission also because ACL is a super-set of
  permission. Passing -f overwrites the destination if it already exists. raw
  namespace extended attributes are preserved if (1) they are supported (HDFS
  only) and, (2) all of the source and target pathnames are in the /.reserved/raw
  hierarchy. raw namespace xattr preservation is determined solely by the presence
  (or absence) of the /.reserved/raw prefix and not by the -p option. Passing -d
  will skip creation of temporary file(<dst>._COPYING_).

-createSnapshot <snapshotDir> [<snapshotName>] :
  Create a snapshot on a directory

-deleteSnapshot <snapshotDir> <snapshotName> :
  Delete a snapshot from a directory

-df [-h] [<path> ...] :
  Shows the capacity, free and used space of the filesystem. If the filesystem has
  multiple partitions, and no path to a particular partition is specified, then
  the status of the root partitions will be shown.

  -h  Formats the sizes of files in a human-readable fashion rather than a number
      of bytes.

-du [-s] [-h] [-v] [-x] <path> ... :
  Show the amount of space, in bytes, used by the files that match the specified
  file pattern. The following flags are optional:

  -s  Rather than showing the size of each individual file that matches the
      pattern, shows the total (summary) size.
  -h  Formats the sizes of files in a human-readable fashion rather than a number
      of bytes.
  -v  option displays a header line.
  -x  Excludes snapshots from being counted.

  Note that, even without the -s option, this only shows size summaries one level
  deep into a directory.

  The output is in the form
  	size	disk space consumed	name(full path)

-expunge [-immediate] :
  Delete files from the trash that are older than the retention threshold

-find <path> ... <expression> ... :
  Finds all files that match the specified expression and
  applies selected actions to them. If no <path> is specified
  then defaults to the current working directory. If no
  expression is specified then defaults to -print.

  The following primary expressions are recognised:
    -name pattern
    -iname pattern
      Evaluates as true if the basename of the file matches the
      pattern using standard file system globbing.
      If -iname is used then the match is case insensitive.

    -print
    -print0
      Always evaluates to true. Causes the current pathname to be
      written to standard output followed by a newline. If the -print0
      expression is used then an ASCII NULL character is appended rather
      than a newline.

  The following operators are recognised:
    expression -a expression
    expression -and expression
    expression expression
      Logical AND operator for joining two expressions. Returns
      true if both child expressions return true. Implied by the
      juxtaposition of two expressions and so does not need to be
      explicitly specified. The second expression will not be
      applied if the first fails.

-get [-f] [-p] [-ignoreCrc] [-crc] <src> ... <localdst> :
  Copy files that match the file pattern <src> to the local name.  <src> is kept.
  When copying multiple files, the destination must be a directory. Passing -f
  overwrites the destination if it already exists and -p preserves access and
  modification times, ownership and the mode.

-getfacl [-R] <path> :
  Displays the Access Control Lists (ACLs) of files and directories. If a
  directory has a default ACL, then getfacl also displays the default ACL.

  -R      List the ACLs of all files and directories recursively.
  <path>  File or directory to list.

-getfattr [-R] {-n name | -d} [-e en] <path> :
  Displays the extended attribute names and values (if any) for a file or
  directory.

  -R             Recursively list the attributes for all files and directories.
  -n name        Dump the named extended attribute value.
  -d             Dump all extended attribute values associated with pathname.
  -e <encoding>  Encode values after retrieving them.Valid encodings are "text",
                 "hex", and "base64". Values encoded as text strings are enclosed
                 in double quotes ("), and values encoded as hexadecimal and
                 base64 are prefixed with 0x and 0s, respectively.
  <path>         The file or directory.

-getmerge [-nl] [-skip-empty-file] <src> <localdst> :
  Get all the files in the directories that match the source file pattern and
  merge and sort them to only one file on local fs. <src> is kept.

  -nl               Add a newline character at the end of each file.
  -skip-empty-file  Do not add new line character for empty file.

-head <file> :
  Show the first 1KB of the file.

-help [cmd ...] :
  Displays help for given command or all commands if none is specified.

-ls [-C] [-d] [-h] [-q] [-R] [-t] [-S] [-r] [-u] [-e] [<path> ...] :
  List the contents that match the specified file pattern. If path is not
  specified, the contents of /user/<currentUser> will be listed. For a directory a
  list of its direct children is returned (unless -d option is specified).

  Directory entries are of the form:
  	permissions - userId groupId sizeOfDirectory(in bytes)
  modificationDate(yyyy-MM-dd HH:mm) directoryName

  and file entries are of the form:
  	permissions numberOfReplicas userId groupId sizeOfFile(in bytes)
  modificationDate(yyyy-MM-dd HH:mm) fileName

    -C  Display the paths of files and directories only.
    -d  Directories are listed as plain files.
    -h  Formats the sizes of files in a human-readable fashion
        rather than a number of bytes.
    -q  Print ? instead of non-printable characters.
    -R  Recursively list the contents of directories.
    -t  Sort files by modification time (most recent first).
    -S  Sort files by size.
    -r  Reverse the order of the sort.
    -u  Use time of last access instead of modification for
        display and sorting.
    -e  Display the erasure coding policy of files and directories.

-mkdir [-p] <path> ... :
  Create a directory in specified location.

  -p  Do not fail if the directory already exists

-moveFromLocal <localsrc> ... <dst> :
  Same as -put, except that the source is deleted after it's copied.

-moveToLocal <src> <localdst> :
  Not implemented yet

-mv <src> ... <dst> :
  Move files that match the specified file pattern <src> to a destination <dst>.
  When moving multiple files, the destination must be a directory.

-put [-f] [-p] [-l] [-d] <localsrc> ... <dst> :
  Copy files from the local file system into fs. Copying fails if the file already
  exists, unless the -f flag is given.
  Flags:

  -p  Preserves access and modification times, ownership and the mode.
  -f  Overwrites the destination if it already exists.
  -l  Allow DataNode to lazily persist the file to disk. Forces
         replication factor of 1. This flag will result in reduced
         durability. Use with care.

  -d  Skip creation of temporary file(<dst>._COPYING_).

-renameSnapshot <snapshotDir> <oldName> <newName> :
  Rename a snapshot from oldName to newName

-rm [-f] [-r|-R] [-skipTrash] [-safely] <src> ... :
  Delete all files that match the specified file pattern. Equivalent to the Unix
  command "rm <src>"

  -f          If the file does not exist, do not display a diagnostic message or
              modify the exit status to reflect an error.
  -[rR]       Recursively deletes directories.
  -skipTrash  option bypasses trash, if enabled, and immediately deletes <src>.
  -safely     option requires safety confirmation, if enabled, requires
              confirmation before deleting large directory with more than
              <hadoop.shell.delete.limit.num.files> files. Delay is expected when
              walking over large directory recursively to count the number of
              files to be deleted before the confirmation.

-rmdir [--ignore-fail-on-non-empty] <dir> ... :
  Removes the directory entry specified by each directory argument, provided it is
  empty.

-setfacl [-R] [{-b|-k} {-m|-x <acl_spec>} <path>]|[--set <acl_spec> <path>] :
  Sets Access Control Lists (ACLs) of files and directories.
  Options:

  -b          Remove all but the base ACL entries. The entries for user, group
              and others are retained for compatibility with permission bits.
  -k          Remove the default ACL.
  -R          Apply operations to all files and directories recursively.
  -m          Modify ACL. New entries are added to the ACL, and existing entries
              are retained.
  -x          Remove specified ACL entries. Other ACL entries are retained.
  --set       Fully replace the ACL, discarding all existing entries. The
              <acl_spec> must include entries for user, group, and others for
              compatibility with permission bits. If the ACL spec contains only
              access entries, then the existing default entries are retained. If
              the ACL spec contains only default entries, then the existing
              access entries are retained. If the ACL spec contains both access
              and default entries, then both are replaced.
  <acl_spec>  Comma separated list of ACL entries.
  <path>      File or directory to modify.

-setfattr {-n name [-v value] | -x name} <path> :
  Sets an extended attribute name and value for a file or directory.

  -n name   The extended attribute name.
  -v value  The extended attribute value. There are three different encoding
            methods for the value. If the argument is enclosed in double quotes,
            then the value is the string inside the quotes. If the argument is
            prefixed with 0x or 0X, then it is taken as a hexadecimal number. If
            the argument begins with 0s or 0S, then it is taken as a base64
            encoding.
  -x name   Remove the extended attribute.
  <path>    The file or directory.

-setrep [-R] [-w] <rep> <path> ... :
  Set the replication level of a file. If <path> is a directory then the command
  recursively changes the replication factor of all files under the directory tree
  rooted at <path>. The EC files will be ignored here.

  -w  It requests that the command waits for the replication to complete. This
      can potentially take a very long time.
  -R  It is accepted for backwards compatibility. It has no effect.

-stat [format] <path> ... :
  Print statistics about the file/directory at <path>
  in the specified format. Format accepts permissions in
  octal (%a) and symbolic (%A), filesize in
  bytes (%b), type (%F), group name of owner (%g),
  name (%n), block size (%o), replication (%r), user name
  of owner (%u), access date (%x, %X).
  modification date (%y, %Y).
  %x and %y show UTC date as "yyyy-MM-dd HH:mm:ss" and
  %X and %Y show milliseconds since January 1, 1970 UTC.
  If the format is not specified, %y is used by default.

-tail [-f] [-s <sleep interval>] <file> :
  Show the last 1KB of the file.

  -f  Shows appended data as the file grows.
  -s  With -f , defines the sleep interval between iterations in milliseconds.

-test -[defswrz] <path> :
  Answer various questions about <path>, with result via exit status.
    -d  return 0 if <path> is a directory.
    -e  return 0 if <path> exists.
    -f  return 0 if <path> is a file.
    -s  return 0 if file <path> is greater         than zero bytes in size.
    -w  return 0 if file <path> exists         and write permission is granted.
    -r  return 0 if file <path> exists         and read permission is granted.
    -z  return 0 if file <path> is         zero bytes in size, else return 1.

-text [-ignoreCrc] <src> ... :
  Takes a source file and outputs the file in text format.
  The allowed formats are zip and TextRecordInputStream and Avro.

-touch [-a] [-m] [-t TIMESTAMP ] [-c] <path> ... :
  Updates the access and modification times of the file specified by the <path> to
  the current time. If the file does not exist, then a zero length file is created
  at <path> with current time as the timestamp of that <path>.
  -a Change only the access time
  -m Change only the modification time
  -t TIMESTAMP Use specified timestamp (in format yyyyMMddHHmmss) instead of
  current time
  -c Do not create any files

-touchz <path> ... :
  Creates a file of zero length at <path> with current time as the timestamp of
  that <path>. An error is returned if the file exists with non-zero length

-truncate [-w] <length> <path> ... :
  Truncate all files that match the specified file pattern to the specified
  length.

  -w  Requests that the command wait for block recovery to complete, if
      necessary.

-usage [cmd ...] :
  Displays the usage for given command or all commands if none is specified.

Generic options supported are:
-conf <configuration file>        specify an application configuration file
-D <property=value>               define a value for a given property
-fs <file:///|hdfs://namenode:port> specify default filesystem URL to use, overrides 'fs.defaultFS' property from configurations.
-jt <local|resourcemanager:port>  specify a ResourceManager
-files <file1,...>                specify a comma-separated list of files to be copied to the map reduce cluster
-libjars <jar1,...>               specify a comma-separated list of jar files to be included in the classpath
-archives <archive1,...>          specify a comma-separated list of archives to be unarchived on the compute machines

The general command line syntax is:
command [genericOptions] [commandOptions]
$
```

Silahkan bereksperimen dengan menggunakan [perintah-perintah di manual dari FS Shell](https://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/FileSystemShell.html). 

### Akses DFSAdmin

Akses ini hanya bisa dilakukan oleh *superuser*. Untuk mengetahui *superuser* pada *cluster*
gunakan:

```
$ start-dfs.sh
Starting namenodes on [localhost]
Starting datanodes
Starting secondary namenodes [dellvuan]
$ ps -ef | grep "org.apache.hadoop.hdfs.server" | grep -v "grep" | cut -d' ' -f1
zaky
zaky
zaky
$
```

Argumen yang digunakan untuk mengakses fasilitas ini adalah *dfsadmin*:

```bash
$ hdfs dfsadmin -help
hdfs dfsadmin performs DFS administrative commands.
Note: Administrative commands can only be run with superuser permission.
The full syntax is:

hdfs dfsadmin
	[-report [-live] [-dead] [-decommissioning] [-enteringmaintenance] [-inmaintenance]]
	[-safemode <enter | leave | get | wait>]
	[-saveNamespace [-beforeShutdown]]
	[-rollEdits]
	[-restoreFailedStorage true|false|check]
	[-refreshNodes]
	[-setQuota <quota> <dirname>...<dirname>]
	[-clrQuota <dirname>...<dirname>]
	[-setSpaceQuota <quota> [-storageType <storagetype>] <dirname>...<dirname>]
	[-clrSpaceQuota [-storageType <storagetype>] <dirname>...<dirname>]
	[-finalizeUpgrade]
	[-rollingUpgrade [<query|prepare|finalize>]]
	[-upgrade <query | finalize>]
	[-refreshServiceAcl]
	[-refreshUserToGroupsMappings]
	[-refreshSuperUserGroupsConfiguration]
	[-refreshCallQueue]
	[-refresh <host:ipc_port> <key> [arg1..argn]
	[-reconfig <namenode|datanode> <host:ipc_port> <start|status|properties>]
	[-printTopology]
	[-refreshNamenodes datanode_host:ipc_port]
	[-getVolumeReport datanode_host:ipc_port]
	[-deleteBlockPool datanode_host:ipc_port blockpoolId [force]]
	[-setBalancerBandwidth <bandwidth in bytes per second>]
	[-getBalancerBandwidth <datanode_host:ipc_port>]
	[-fetchImage <local directory>]
	[-allowSnapshot <snapshotDir>]
	[-disallowSnapshot <snapshotDir>]
	[-shutdownDatanode <datanode_host:ipc_port> [upgrade]]
	[-evictWriters <datanode_host:ipc_port>]
	[-getDatanodeInfo <datanode_host:ipc_port>]
	[-metasave filename]
	[-triggerBlockReport [-incremental] <datanode_host:ipc_port>]
	[-listOpenFiles [-blockingDecommission] [-path <path>]]
	[-help [cmd]]

-report [-live] [-dead] [-decommissioning] [-enteringmaintenance] [-inmaintenance]:
	Reports basic filesystem information and statistics.
	The dfs usage can be different from "du" usage, because it
	measures raw space used by replication, checksums, snapshots
	and etc. on all the DNs.
	Optional flags may be used to filter the list of displayed DNs.

-safemode <enter|leave|get|wait|forceExit>:  Safe mode maintenance command.
		Safe mode is a Namenode state in which it
			1.  does not accept changes to the name space (read-only)
			2.  does not replicate or delete blocks.
		Safe mode is entered automatically at Namenode startup, and
		leaves safe mode automatically when the configured minimum
		percentage of blocks satisfies the minimum replication
		condition.  Safe mode can also be entered manually, but then
		it can only be turned off manually as well.

-saveNamespace [-beforeShutdown]:	Save current namespace into storage directories and reset edits
		 log. Requires safe mode.
		If the "beforeShutdown" option is given, the NameNode does a
		checkpoint if and only if there is no checkpoint done during
		a time window (a configurable number of checkpoint periods).
		This is usually used before shutting down the NameNode to
		prevent potential fsimage/editlog corruption.

-rollEdits:	Rolls the edit log.

-restoreFailedStorage:	Set/Unset/Check flag to attempt restore of failed storage replicas if they become available.

-refreshNodes: 	Updates the namenode with the set of datanodes allowed to connect to the namenode.

		Namenode re-reads datanode hostnames from the file defined by
		dfs.hosts, dfs.hosts.exclude configuration parameters.
		Hosts defined in dfs.hosts are the datanodes that are part of
		the cluster. If there are entries in dfs.hosts, only the hosts
		in it are allowed to register with the namenode.

		Entries in dfs.hosts.exclude are datanodes that need to be
		decommissioned. Datanodes complete decommissioning when
		all the replicas from them are replicated to other datanodes.
		Decommissioned nodes are not automatically shutdown and
		are not chosen for writing new replicas.

-finalizeUpgrade: Finalize upgrade of HDFS.
		Datanodes delete their previous version working directories,
		followed by Namenode doing the same.
		This completes the upgrade process.

-rollingUpgrade [<query|prepare|finalize>]:
     query: query the current rolling upgrade status.
   prepare: prepare a new rolling upgrade.
  finalize: finalize the current rolling upgrade.
-upgrade <query | finalize>:
     query: query the current upgrade status.
  finalize: finalize the upgrade of HDFS (equivalent to -finalizeUpgrade.
-metasave <filename>: 	Save Namenode's primary data structures
		to <filename> in the directory specified by hadoop.log.dir property.
		<filename> is overwritten if it exists.
		<filename> will contain one line for each of the following
			1. Datanodes heart beating with Namenode
			2. Blocks waiting to be replicated
			3. Blocks currrently being replicated
			4. Blocks waiting to be deleted

-setQuota <quota> <dirname>...<dirname>: Set the quota <quota> for each directory <dirName>.
		The directory quota is a long integer that puts a hard limit
		on the number of names in the directory tree
		For each directory, attempt to set the quota. An error will be reported if
		1. quota is not a positive integer, or
		2. User is not an administrator, or
		3. The directory does not exist or is a file.
		Note: A quota of 1 would force the directory to remain empty.

-clrQuota <dirname>...<dirname>: Clear the quota for each directory <dirName>.
		For each directory, attempt to clear the quota. An error will be reported if
		1. the directory does not exist or is a file, or
		2. user is not an administrator.
		It does not fault if the directory has no quota.
-setSpaceQuota <quota> [-storageType <storagetype>] <dirname>...<dirname>: Set the space quota <quota> for each directory <dirName>.
		The space quota is a long integer that puts a hard limit
		on the total size of all the files under the directory tree.
		The extra space required for replication is also counted. E.g.
		a 1GB file with replication of 3 consumes 3GB of the quota.

		Quota can also be specified with a binary prefix for terabytes,
		petabytes etc (e.g. 50t is 50TB, 5m is 5MB, 3p is 3PB).
		For each directory, attempt to set the quota. An error will be reported if
		1. quota is not a positive integer or zero, or
		2. user is not an administrator, or
		3. the directory does not exist or is a file.
		The storage type specific quota is set when -storageType option is specified.
		Available storageTypes are
		- RAM_DISK
		- DISK
		- SSD
		- ARCHIVE
-clrSpaceQuota [-storageType <storagetype>] <dirname>...<dirname>: Clear the space quota for each directory <dirName>.
		For each directory, attempt to clear the quota. An error will be reported if
		1. the directory does not exist or is a file, or
		2. user is not an administrator.
		It does not fault if the directory has no quota.
		The storage type specific quota is cleared when -storageType option is specified.
		Available storageTypes are
		- RAM_DISK
		- DISK
		- SSD
		- ARCHIVE
-refreshServiceAcl: Reload the service-level authorization policy file
		Namenode will reload the authorization policy file.

-refreshUserToGroupsMappings: Refresh user-to-groups mappings

-refreshSuperUserGroupsConfiguration: Refresh superuser proxy groups mappings

-refreshCallQueue: Reload the call queue from config

-refresh: Arguments are <hostname:ipc_port> <resource_identifier> [arg1..argn]
	Triggers a runtime-refresh of the resource specified by <resource_identifier> on <hostname:ipc_port>.
	All other args after are sent to the host.
	The ipc_port is determined by 'dfs.datanode.ipc.address',default is DFS_DATANODE_IPC_DEFAULT_PORT.

-reconfig <namenode|datanode> <host:ipc_port> <start|status|properties>:
	Starts or gets the status of a reconfiguration operation,
	or gets a list of reconfigurable properties.
	The second parameter specifies the node type

-printTopology: Print a tree of the racks and their
		nodes as reported by the Namenode

-refreshNamenodes: Takes a datanodehost:ipc_port as argument,For the given datanode
		reloads the configuration files,stops serving the removed
		block-pools and starts serving new block-pools.
		The ipc_port is determined by 'dfs.datanode.ipc.address',default is DFS_DATANODE_IPC_DEFAULT_PORT.

-deleteBlockPool: Arguments are datanodehost:ipc_port, blockpool id and an optional argument
		"force". If force is passed,block pool directory for
		the given blockpool id on the given datanode is deleted
		along with its contents,otherwise the directory is deleted
		only if it is empty.The command will fail if datanode is
		still serving the block pool.Refer to refreshNamenodes to
		shutdown a block pool service on a datanode.
		The ipc_port is determined by 'dfs.datanode.ipc.address',default is DFS_DATANODE_IPC_DEFAULT_PORT.

-setBalancerBandwidth <bandwidth>:
	Changes the network bandwidth used by each datanode during
	HDFS block balancing.

		<bandwidth> is the maximum number of bytes per second
		that will be used by each datanode. This value overrides
		the dfs.datanode.balance.bandwidthPerSec parameter.

		--- NOTE: The new value is not persistent on the DataNode.---

-getBalancerBandwidth <datanode_host:ipc_port>:
	Get the network bandwidth for the given datanode.
	This is the maximum network bandwidth used by the datanode
	during HDFS block balancing.

	--- NOTE: This value is not persistent on the DataNode.---

-fetchImage <local directory>:
	Downloads the most recent fsimage from the Name Node and saves it in	the specified local directory.

-allowSnapshot <snapshotDir>:
	Allow snapshots to be taken on a directory.

-disallowSnapshot <snapshotDir>:
	Do not allow snapshots to be taken on a directory any more.

-shutdownDatanode <datanode_host:ipc_port> [upgrade]
	Submit a shutdown request for the given datanode. If an optional
	"upgrade" argument is specified, clients accessing the datanode
	will be advised to wait for it to restart and the fast start-up
	mode will be enabled. When the restart does not happen in time,
	clients will timeout and ignore the datanode. In such case, the
	fast start-up mode will also be disabled.

-evictWriters <datanode_host:ipc_port>
	Make the datanode evict all clients that are writing a block.
	This is useful if decommissioning is hung due to slow writers.

-getDatanodeInfo <datanode_host:ipc_port>
	Get the information about the given datanode. This command can
	be used for checking if a datanode is alive.

-triggerBlockReport [-incremental] <datanode_host:ipc_port>
	Trigger a block report for the datanode.
	If 'incremental' is specified, it will be an incremental
	block report; otherwise, it will be a full block report.

-listOpenFiles [-blockingDecommission]
	List all open files currently managed by the NameNode along
	with client name and client machine accessing them.
	If 'blockingDecommission' option is specified, it will list the
	open files only that are blocking the ongoing Decommission.
-help [cmd]: 	Displays help for the given command or all commands if none
		is specified.


Generic options supported are:
-conf <configuration file>        specify an application configuration file
-D <property=value>               define a value for a given property
-fs <file:///|hdfs://namenode:port> specify default filesystem URL to use, overrides 'fs.defaultFS' property from configurations.
-jt <local|resourcemanager:port>  specify a ResourceManager
-files <file1,...>                specify a comma-separated list of files to be copied to the map reduce cluster
-libjars <jar1,...>               specify a comma-separated list of jar files to be included in the classpath
-archives <archive1,...>          specify a comma-separated list of archives to be unarchived on the compute machines

The general command line syntax is:
command [genericOptions] [commandOptions]
$
```

### Akses Menggunakan Web UI

Untuk mengakses *Namenode* menggunakan Web UI, akses ke http://localhost:9870/ menggunakan browser.

### Akses Menggunakan Java API

Developer bisa mengakses HDFS dengan menggunakan API Java yang disediakan oleh HDFS. Untuk memahami
hal ini, gunakan langkah-langkah seperti di bawah.

1.  Persiapkan HDFS, aktifkan HDFS jika belum diaktifkan.

```bash
$ start-dfs.sh
Starting namenodes on [localhost]
Starting datanodes
Starting secondary namenodes [dellvuan]
$
```

2.  Persiapkan file

```bash
$ hdfs dfs -copyFromLocal /etc/hostname
2020-03-06 06:14:40,969 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
$ hdfs dfs -ls
Found 3 items
-rw-r--r--   1 zaky supergroup          9 2020-03-06 06:14 hostname
drwxr-xr-x   - zaky supergroup          0 2020-03-06 00:45 input
drwxr-xr-x   - zaky supergroup          0 2020-03-06 01:10 output
$ hdfs dfs -cat hostname
2020-03-06 06:17:30,412 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
dellvuan
$
```

3.  Persiapkan kode sumber Java serta `build.gradle`.
4.  Kompilasi:
    * gradle build
5.  Jalankan:
    * gradle run

