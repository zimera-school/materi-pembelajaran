# Instalasi dan Konfigurasi Apache Hadoop

## Persiapan

Untuk keperluan instalasi, siapkan direktori baru yang kosong. Kita akan menggunakan direktori ini sebagai dasar dari semua software yang kita gunakan:

```bash
$ mkdir -p $HOME/software/bigdata
$ mkdir -p $HOME/master
$ mkdir -p $HOME/env
$ mkdir -p $HOME/training
```

* Baris 1 digunakan sebagai tempat instalasi semua software yang digunakan
* Baris 2 digunakan untuu menyimpan semua master software
* Baris 3 digunakan untuk menyimpan semua file-file yang mengatur *environment variables*
* Baris 4 digunakan untuk menyimpan semua file yang dibuat saat training.

**Catatan:** Perintah-perintah yang dikerjakan di shell / CLI dilakukan pada kondisi keaktifan user tertentu. Jika diawali dengan **$**, maka perintah tersebut dikerjakan oleh user biasa. Jika diawali dengan **#**, maka perintah tersebut dikerjakan oleh *superuser* / *root*.

Setelah membuat direktori, masukkan semua file master di direktori `$HOME/master` kemudian masuk ke direktori tempat software akan di-install:

```bash
$ cd $HOME/software/bigdata
```

Pada posisi ini, semua direktori sudah tersedia dan semua software yang dibutuhkan sudah berada di `$HOME/master`. 

## Install Software yang Diperlukan

Ada 2 software yang harus diinstall untuk keperluan Hadoop ini. Gunakan perintah berikut untuk
instalasi:

```bash
$ sudo apt install ssh pdsh
```

## Install JDK

```bash
$ tar -xvf ~/master/jdk-8u241-linux-x64.tar.gz 
...
...
...
...
jdk1.8.0_241/include/jawt.h
jdk1.8.0_241/include/classfile_constants.h
jdk1.8.0_241/include/linux/
jdk1.8.0_241/include/linux/jni_md.h
jdk1.8.0_241/include/linux/jawt_md.h
jdk1.8.0_241/include/jdwpTransport.h
jdk1.8.0_241/include/jvmti.h
jdk1.8.0_241/include/jvmticmlr.h
$
```

Setelah itu, atur *environment variables* dengan membuat suatu file di `$HOME/env`:

```bash
export JAVA_HOME=$HOME/software/bigdata/jdk1.8.0_241

export PATH=$JAVA_HOME/bin:$PATH
export MANPATH=$JAVA_HOME/man:$MANPATH

export LD_LIBRARY_PATH=$JAVA_HOME/jre/lib/amd64:$JAVA_HOME/jre/lib/amd64/jli:$JAVA_HOME/jre/lib/amd64/server:$LD_LIBRARY_PATH
```

Simpan dengan nama file `jdk8`. Setelah itu uji instalasi ini:

```bash
$ source ~/env/jdk8
$ java -version
java version "1.8.0_241"
Java(TM) SE Runtime Environment (build 1.8.0_241-b07)
Java HotSpot(TM) 64-Bit Server VM (build 25.241-b07, mixed mode)
$ javac -version
javac 1.8.0_241
$ 
```

## Install Apache Hadoop

Seperti halnya instalasi JDK, instalasi Hadoop dilakukan dengan mengekstrak kemudian mengatur *environment variables*.

```
$ tar -xvf ~/master/hadoop-3.2.1.tar.g
...
...
...
...
...
hadoop-3.2.1/sbin/stop-balancer.sh
hadoop-3.2.1/sbin/workers.sh
hadoop-3.2.1/sbin/start-balancer.sh
hadoop-3.2.1/sbin/start-all.cmd
hadoop-3.2.1/sbin/hadoop-daemon.sh
hadoop-3.2.1/sbin/stop-yarn.sh
hadoop-3.2.1/sbin/start-all.sh
$ 
```

Setelah itu, buat file yang berisi *environment variables* dengan nama `$HOME/env/hadoop` sebagai
berikut:

```bash
export PATH=$HOME/software/bigdata/hadoop-3.2.1/bin:$HOME/software/bigdata/hadoop-3.2.1/sbin:$PATH
export PDSH_RCMD_TYPE=ssh
```

Setelah itu, uji instalasi dengan perintah berikut ini:

```bash
$ source env/hadoop
```
Setelah itu, uji hasil instalasi:

```
$ hadoop
Usage: hadoop [OPTIONS] SUBCOMMAND [SUBCOMMAND OPTIONS]
 or    hadoop [OPTIONS] CLASSNAME [CLASSNAME OPTIONS]
  where CLASSNAME is a user-provided Java class

  OPTIONS is none or any of:

buildpaths                       attempt to add class files from build tree
--config dir                     Hadoop config directory
--debug                          turn on shell script debug mode
--help                           usage information
hostnames list[,of,host,names]   hosts to use in slave mode
hosts filename                   list of hosts to use in slave mode
loglevel level                   set the log4j level for this command
workers                          turn on worker mode

  SUBCOMMAND is one of:


    Admin Commands:

daemonlog     get/set the log level for each daemon

    Client Commands:

archive       create a Hadoop archive
checknative   check native Hadoop and compression libraries availability
classpath     prints the class path needed to get the Hadoop jar and the required libraries
conftest      validate configuration XML files
credential    interact with credential providers
distch        distributed metadata changer
distcp        copy file or directories recursively
dtutil        operations related to delegation tokens
envvars       display computed Hadoop environment variables
fs            run a generic filesystem user client
gridmix       submit a mix of synthetic job, modeling a profiled from production load
jar <jar>     run a jar file. NOTE: please use "yarn jar" to launch YARN applications, not this command.
jnipath       prints the java.library.path
kdiag         Diagnose Kerberos Problems
kerbname      show auth_to_local principal conversion
key           manage keys via the KeyProvider
rumenfolder   scale a rumen input trace
rumentrace    convert logs into a rumen trace
s3guard       manage metadata on S3
trace         view and modify Hadoop tracing settings
version       print the version

    Daemon Commands:

kms           run KMS, the Key Management Server

SUBCOMMAND may print help when invoked w/o parameters or with -h.
```

JIka berhasil sampai disini, berarti instalasi sudah dilaksanakan dengan baik.

## Mode Operasi Hadoop

Hadoop bisa dijelankan dalam 3 mode:

1.  *Standalone*
2.  *Pseudo-Distributed*
3.  *Fully-Distributed*

### Standalone

Pada mode ini, Hadoop biasanya hanya digunakan untuk proses kecil dan untuk keperluan eksperimen serta *debugging* saja. Dengan menggunakan mode ini, Hadoop (yang dibuat menggunakan Java) akan dijalankan dalam 1 proses Java saja.

Untuk menguji mode ini, kita akan menggunakan contoh yang sudah dibuat oleh Hadoop untuk MapReduce (ada di file `$HOME/software/bigdata/hadoop-3.2.1/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.1.jar` untuk proses grep (mengambil teks sesuai dengan pola tertentu). Semua aktivitas di Standalone ini dikerjakan di `$HOME/training/001-standalone`, buat direktorinya jika belum ada:

```bash
$ mkdir $HOME/training/001-standalone
```
Kerjakan perintah-perintah berikut ini:

```bash
$ cd $HOME/training/001-standalone
$ mkdir input
$ cp $HOME/software/bigdata/hadoop-3.2.1/etc/hadoop/*.xml input/
$ ls -la input/
total 60
drwxr-xr-x  2 zaky zaky  4096 Mar  5 23:03 .
drwxr-xr-x 11 zaky zaky  4096 Mar  5 23:02 ..
-rw-r--r--  1 zaky zaky  8260 Mar  5 23:03 capacity-scheduler.xml
-rw-r--r--  1 zaky zaky   774 Mar  5 23:03 core-site.xml
-rw-r--r--  1 zaky zaky 11392 Mar  5 23:03 hadoop-policy.xml
-rw-r--r--  1 zaky zaky   775 Mar  5 23:03 hdfs-site.xml
-rw-r--r--  1 zaky zaky   620 Mar  5 23:03 httpfs-site.xml
-rw-r--r--  1 zaky zaky  3518 Mar  5 23:03 kms-acls.xml
-rw-r--r--  1 zaky zaky   682 Mar  5 23:03 kms-site.xml
-rw-r--r--  1 zaky zaky   758 Mar  5 23:03 mapred-site.xml
-rw-r--r--  1 zaky zaky   690 Mar  5 23:03 yarn-site.xml
$ 
```

Kerjakan proses MapReduce standalone berikut:

```bash
$ hadoop jar  ~/software/bigdata/hadoop-3.2.1/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.1.jar grep input output 'dfs[a-z.]+'
2020-03-05 23:03:48,272 INFO impl.MetricsConfig: Loaded properties from hadoop-metrics2.properties
2020-03-05 23:03:48,320 INFO impl.MetricsSystemImpl: Scheduled Metric snapshot period at 10 second(s).
2020-03-05 23:03:48,321 INFO impl.MetricsSystemImpl: JobTracker metrics system started
2020-03-05 23:03:48,534 INFO input.FileInputFormat: Total input files to process : 9
2020-03-05 23:03:48,551 INFO mapreduce.JobSubmitter: number of splits:9
2020-03-05 23:03:48,638 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_local2085274023_0001
2020-03-05 23:03:48,638 INFO mapreduce.JobSubmitter: Executing with tokens: []
2020-03-05 23:03:48,734 INFO mapreduce.Job: The url to track the job: http://localhost:8080/
2020-03-05 23:03:48,735 INFO mapreduce.Job: Running job: job_local2085274023_0001
2020-03-05 23:03:48,735 INFO mapred.LocalJobRunner: OutputCommitter set in config null
2020-03-05 23:03:48,740 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:48,740 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:48,741 INFO mapred.LocalJobRunner: OutputCommitter is org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter
2020-03-05 23:03:48,764 INFO mapred.LocalJobRunner: Waiting for map tasks
2020-03-05 23:03:48,764 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000000_0
2020-03-05 23:03:48,779 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:48,779 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:48,792 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:48,796 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/hadoop-policy.xml:0+11392
2020-03-05 23:03:48,843 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:48,843 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:48,843 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:48,843 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:48,843 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:48,846 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:48,857 INFO mapred.LocalJobRunner:
2020-03-05 23:03:48,858 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:48,858 INFO mapred.MapTask: Spilling map output
2020-03-05 23:03:48,858 INFO mapred.MapTask: bufstart = 0; bufend = 17; bufvoid = 104857600
2020-03-05 23:03:48,858 INFO mapred.MapTask: kvstart = 26214396(104857584); kvend = 26214396(104857584); length = 1/6553600
2020-03-05 23:03:48,868 INFO mapred.MapTask: Finished spill 0
2020-03-05 23:03:48,875 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000000_0 is done. And is in the process of committing
2020-03-05 23:03:48,877 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:48,877 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000000_0' done.
2020-03-05 23:03:48,882 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000000_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=329310
		FILE: Number of bytes written=841095
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=265
		Map output records=1
		Map output bytes=17
		Map output materialized bytes=25
		Input split bytes=134
		Combine input records=1
		Combine output records=1
		Spilled Records=1
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=260046848
	File Input Format Counters
		Bytes Read=11392
2020-03-05 23:03:48,882 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000000_0
2020-03-05 23:03:48,883 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000001_0
2020-03-05 23:03:48,883 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:48,883 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:48,884 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:48,885 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/capacity-scheduler.xml:0+8260
2020-03-05 23:03:48,932 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:48,932 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:48,932 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:48,932 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:48,932 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:48,932 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:48,936 INFO mapred.LocalJobRunner:
2020-03-05 23:03:48,937 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:48,939 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000001_0 is done. And is in the process of committing
2020-03-05 23:03:48,940 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:48,940 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000001_0' done.
2020-03-05 23:03:48,941 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000001_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=338782
		FILE: Number of bytes written=841133
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=220
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=139
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=365428736
	File Input Format Counters
		Bytes Read=8260
2020-03-05 23:03:48,941 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000001_0
2020-03-05 23:03:48,941 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000002_0
2020-03-05 23:03:48,942 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:48,942 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:48,942 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:48,943 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/kms-acls.xml:0+3518
2020-03-05 23:03:48,999 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:48,999 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:48,999 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:48,999 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:48,999 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,000 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,002 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,002 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,004 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000002_0 is done. And is in the process of committing
2020-03-05 23:03:49,005 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,005 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000002_0' done.
2020-03-05 23:03:49,006 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000002_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=343512
		FILE: Number of bytes written=841171
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=135
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=129
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=470810624
	File Input Format Counters
		Bytes Read=3518
2020-03-05 23:03:49,006 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000002_0
2020-03-05 23:03:49,006 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000003_0
2020-03-05 23:03:49,007 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,007 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,007 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,008 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/hdfs-site.xml:0+775
2020-03-05 23:03:49,061 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,061 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,061 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,061 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,061 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,061 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,062 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,062 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,065 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000003_0 is done. And is in the process of committing
2020-03-05 23:03:49,066 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,066 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000003_0' done.
2020-03-05 23:03:49,067 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000003_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=345499
		FILE: Number of bytes written=841209
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=21
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=130
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=576192512
	File Input Format Counters
		Bytes Read=775
2020-03-05 23:03:49,067 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000003_0
2020-03-05 23:03:49,067 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000004_0
2020-03-05 23:03:49,068 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,068 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,068 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,070 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/core-site.xml:0+774
2020-03-05 23:03:49,114 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,114 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,114 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,114 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,114 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,115 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,116 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,116 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,196 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000004_0 is done. And is in the process of committing
2020-03-05 23:03:49,197 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,197 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000004_0' done.
2020-03-05 23:03:49,197 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000004_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=346973
		FILE: Number of bytes written=841247
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=20
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=130
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=681574400
	File Input Format Counters
		Bytes Read=774
2020-03-05 23:03:49,197 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000004_0
2020-03-05 23:03:49,198 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000005_0
2020-03-05 23:03:49,199 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,199 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,199 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,201 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/mapred-site.xml:0+758
2020-03-05 23:03:49,255 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,255 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,255 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,255 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,255 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,256 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,258 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,259 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,261 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000005_0 is done. And is in the process of committing
2020-03-05 23:03:49,262 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,262 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000005_0' done.
2020-03-05 23:03:49,262 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000005_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=348431
		FILE: Number of bytes written=841285
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=21
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=132
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=786956288
	File Input Format Counters
		Bytes Read=758
2020-03-05 23:03:49,262 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000005_0
2020-03-05 23:03:49,262 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000006_0
2020-03-05 23:03:49,263 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,263 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,264 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,265 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/yarn-site.xml:0+690
2020-03-05 23:03:49,316 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,316 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,316 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,316 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,316 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,316 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,318 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,318 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,320 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000006_0 is done. And is in the process of committing
2020-03-05 23:03:49,321 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,321 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000006_0' done.
2020-03-05 23:03:49,322 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000006_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=349821
		FILE: Number of bytes written=841323
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=19
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=130
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=892338176
	File Input Format Counters
		Bytes Read=690
2020-03-05 23:03:49,322 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000006_0
2020-03-05 23:03:49,322 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000007_0
2020-03-05 23:03:49,323 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,323 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,323 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,324 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/kms-site.xml:0+682
2020-03-05 23:03:49,368 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,368 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,368 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,368 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,368 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,369 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,370 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,370 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,372 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000007_0 is done. And is in the process of committing
2020-03-05 23:03:49,374 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,375 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000007_0' done.
2020-03-05 23:03:49,376 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000007_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=351203
		FILE: Number of bytes written=841361
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=20
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=129
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=997720064
	File Input Format Counters
		Bytes Read=682
2020-03-05 23:03:49,376 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000007_0
2020-03-05 23:03:49,376 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000008_0
2020-03-05 23:03:49,377 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,377 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,378 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,379 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/httpfs-site.xml:0+620
2020-03-05 23:03:49,441 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,441 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,441 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,441 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,441 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,442 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,443 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,443 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,445 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000008_0 is done. And is in the process of committing
2020-03-05 23:03:49,446 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,446 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000008_0' done.
2020-03-05 23:03:49,446 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000008_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=352011
		FILE: Number of bytes written=841399
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=17
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=132
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=1103101952
	File Input Format Counters
		Bytes Read=620
2020-03-05 23:03:49,446 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000008_0
2020-03-05 23:03:49,446 INFO mapred.LocalJobRunner: map task executor complete.
2020-03-05 23:03:49,448 INFO mapred.LocalJobRunner: Waiting for reduce tasks
2020-03-05 23:03:49,448 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_r_000000_0
2020-03-05 23:03:49,454 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,454 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,454 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,457 INFO mapred.ReduceTask: Using ShuffleConsumerPlugin: org.apache.hadoop.mapreduce.task.reduce.Shuffle@62e1023a
2020-03-05 23:03:49,459 WARN impl.MetricsSystemImpl: JobTracker metrics system already initialized!
2020-03-05 23:03:49,473 INFO reduce.MergeManagerImpl: MergerManager: memoryLimit=1271293568, maxSingleShuffleLimit=317823392, mergeThreshold=839053760, ioSortFactor=10, memToMemMergeOutputsThreshold=10
2020-03-05 23:03:49,476 INFO reduce.EventFetcher: attempt_local2085274023_0001_r_000000_0 Thread started: EventFetcher for fetching Map Completion Events
2020-03-05 23:03:49,497 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000003_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,500 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000003_0
2020-03-05 23:03:49,502 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 1, commitMemory -> 0, usedMemory ->2
2020-03-05 23:03:49,504 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000006_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,504 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000006_0
2020-03-05 23:03:49,504 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 2, commitMemory -> 2, usedMemory ->4
2020-03-05 23:03:49,505 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000002_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,506 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000002_0
2020-03-05 23:03:49,506 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 3, commitMemory -> 4, usedMemory ->6
2020-03-05 23:03:49,507 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000005_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,507 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000005_0
2020-03-05 23:03:49,507 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 4, commitMemory -> 6, usedMemory ->8
2020-03-05 23:03:49,508 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000008_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,508 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000008_0
2020-03-05 23:03:49,509 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 5, commitMemory -> 8, usedMemory ->10
2020-03-05 23:03:49,510 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000001_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,510 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000001_0
2020-03-05 23:03:49,510 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 6, commitMemory -> 10, usedMemory ->12
2020-03-05 23:03:49,511 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000004_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,511 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000004_0
2020-03-05 23:03:49,511 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 7, commitMemory -> 12, usedMemory ->14
2020-03-05 23:03:49,512 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000007_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,512 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000007_0
2020-03-05 23:03:49,512 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 8, commitMemory -> 14, usedMemory ->16
2020-03-05 23:03:49,513 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000000_0 decomp: 21 len: 25 to MEMORY
2020-03-05 23:03:49,513 INFO reduce.InMemoryMapOutput: Read 21 bytes from map-output for attempt_local2085274023_0001_m_000000_0
2020-03-05 23:03:49,513 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 21, inMemoryMapOutputs.size() -> 9, commitMemory -> 16, usedMemory ->37
2020-03-05 23:03:49,514 INFO reduce.EventFetcher: EventFetcher is interrupted.. Returning
2020-03-05 23:03:49,514 INFO mapred.LocalJobRunner: 9 / 9 copied.
2020-03-05 23:03:49,514 INFO reduce.MergeManagerImpl: finalMerge called with 9 in-memory map-outputs and 0 on-disk map-outputs
2020-03-05 23:03:49,519 INFO mapred.Merger: Merging 9 sorted segments
2020-03-05 23:03:49,519 INFO mapred.Merger: Down to the last merge-pass, with 1 segments left of total size: 10 bytes
2020-03-05 23:03:49,519 INFO reduce.MergeManagerImpl: Merged 9 segments, 37 bytes to disk to satisfy reduce memory limit
2020-03-05 23:03:49,519 INFO reduce.MergeManagerImpl: Merging 1 files, 25 bytes from disk
2020-03-05 23:03:49,520 INFO reduce.MergeManagerImpl: Merging 0 segments, 0 bytes from memory into reduce
2020-03-05 23:03:49,520 INFO mapred.Merger: Merging 1 sorted segments
2020-03-05 23:03:49,520 INFO mapred.Merger: Down to the last merge-pass, with 1 segments left of total size: 10 bytes
2020-03-05 23:03:49,520 INFO mapred.LocalJobRunner: 9 / 9 copied.
2020-03-05 23:03:49,530 INFO Configuration.deprecation: mapred.skip.on is deprecated. Instead, use mapreduce.job.skiprecords
2020-03-05 23:03:49,531 INFO mapred.Task: Task:attempt_local2085274023_0001_r_000000_0 is done. And is in the process of committing
2020-03-05 23:03:49,531 INFO mapred.LocalJobRunner: 9 / 9 copied.
2020-03-05 23:03:49,531 INFO mapred.Task: Task attempt_local2085274023_0001_r_000000_0 is allowed to commit now
2020-03-05 23:03:49,533 INFO output.FileOutputCommitter: Saved output of task 'attempt_local2085274023_0001_r_000000_0' to file:/home/zaky/software/bigdata/hadoop-3.2.1/grep-temp-2147140595
2020-03-05 23:03:49,533 INFO mapred.LocalJobRunner: reduce > reduce
2020-03-05 23:03:49,533 INFO mapred.Task: Task 'attempt_local2085274023_0001_r_000000_0' done.
2020-03-05 23:03:49,534 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_r_000000_0: Counters: 24
	File System Counters
		FILE: Number of bytes read=352397
		FILE: Number of bytes written=841547
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Combine input records=0
		Combine output records=0
		Reduce input groups=1
		Reduce shuffle bytes=73
		Reduce input records=1
		Reduce output records=1
		Spilled Records=1
		Shuffled Maps =9
		Failed Shuffles=0
		Merged Map outputs=9
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=1103101952
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Output Format Counters
		Bytes Written=123
2020-03-05 23:03:49,534 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_r_000000_0
2020-03-05 23:03:49,534 INFO mapred.LocalJobRunner: reduce task executor complete.
2020-03-05 23:03:49,739 INFO mapreduce.Job: Job job_local2085274023_0001 running in uber mode : false
2020-03-05 23:03:49,742 INFO mapreduce.Job:  map 100% reduce 100%
2020-03-05 23:03:49,744 INFO mapreduce.Job: Job job_local2085274023_0001 completed successfully
2020-03-05 23:03:49,768 INFO mapreduce.Job: Counters: 30
	File System Counters
		FILE: Number of bytes read=3457939
		FILE: Number of bytes written=8412770
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=738
		Map output records=1
		Map output bytes=17
		Map output materialized bytes=73
		Input split bytes=1185
		Combine input records=1
		Combine output records=1
		Reduce input groups=1
		Reduce shuffle bytes=73
		Reduce input records=1
		Reduce output records=1
		Spilled Records=2
		Shuffled Maps =9
		Failed Shuffles=0
		Merged Map outputs=9
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=7237271552
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters
		Bytes Read=27469
	File Output Format Counters
		Bytes Written=123
2020-03-05 23:03:49,869 WARN impl.MetricsSystemImpl: JobTracker metrics system already initialized!
2020-03-05 23:03:49,876 INFO input.FileInputFormat: Total input files to process : 1
2020-03-05 23:03:49,877 INFO mapreduce.JobSubmitter: number of splits:1
2020-03-05 23:03:49,888 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_local703196677_0002
2020-03-05 23:03:49,889 INFO mapreduce.JobSubmitter: Executing with tokens: []
2020-03-05 23:03:49,936 INFO mapreduce.Job: The url to track the job: http://localhost:8080/
2020-03-05 23:03:49,936 INFO mapreduce.Job: Running job: job_local703196677_0002
2020-03-05 23:03:49,936 INFO mapred.LocalJobRunner: OutputCommitter set in config null
2020-03-05 23:03:49,937 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,937 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,937 INFO mapred.LocalJobRunner: OutputCommitter is org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter
2020-03-05 23:03:49,938 INFO mapred.LocalJobRunner: Waiting for map tasks
2020-03-05 23:03:49,938 INFO mapred.LocalJobRunner: Starting task: attempt_local703196677_0002_m_000000_0
2020-03-05 23:03:49,939 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,939 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,939 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,940 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/grep-temp-2147140595/part-r-00000:0+111
2020-03-05 23:03:49,959 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,959 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,959 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,959 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,959 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,960 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,965 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,965 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,965 INFO mapred.MapTask: Spilling map output
2020-03-05 23:03:49,965 INFO mapred.MapTask: bufstart = 0; bufend = 17; bufvoid = 104857600
2020-03-05 23:03:49,965 INFO mapred.MapTask: kvstart = 26214396(104857584); kvend = 26214396(104857584); length = 1/6553600
2020-03-05 23:03:49,965 INFO mapred.MapTask: Finished spill 0
2020-03-05 23:03:49,966 INFO mapred.Task: Task:attempt_local703196677_0002_m_000000_0 is done. And is in the process of committing
2020-03-05 23:03:49,967 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,967 INFO mapred.Task: Task 'attempt_local703196677_0002_m_000000_0' done.
2020-03-05 23:03:49,968 INFO mapred.Task: Final Counters for attempt_local703196677_0002_m_000000_0: Counters: 17
	File System Counters
		FILE: Number of bytes read=669252
		FILE: Number of bytes written=1677613
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=1
		Map output records=1
		Map output bytes=17
		Map output materialized bytes=25
		Input split bytes=144
		Combine input records=0
		Spilled Records=1
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=315621376
	File Input Format Counters
		Bytes Read=123
2020-03-05 23:03:49,968 INFO mapred.LocalJobRunner: Finishing task: attempt_local703196677_0002_m_000000_0
2020-03-05 23:03:49,968 INFO mapred.LocalJobRunner: map task executor complete.
2020-03-05 23:03:49,968 INFO mapred.LocalJobRunner: Waiting for reduce tasks
2020-03-05 23:03:49,969 INFO mapred.LocalJobRunner: Starting task: attempt_local703196677_0002_r_000000_0
2020-03-05 23:03:49,969 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,969 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,970 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,970 INFO mapred.ReduceTask: Using ShuffleConsumerPlugin: org.apache.hadoop.mapreduce.task.reduce.Shuffle@3caac82b
2020-03-05 23:03:49,970 WARN impl.MetricsSystemImpl: JobTracker metrics system already initialized!
2020-03-05 23:03:49,971 INFO reduce.MergeManagerImpl: MergerManager: memoryLimit=1271293568, maxSingleShuffleLimit=317823392, mergeThreshold=839053760, ioSortFactor=10, memToMemMergeOutputsThreshold=10
2020-03-05 23:03:49,971 INFO reduce.EventFetcher: attempt_local703196677_0002_r_000000_0 Thread started: EventFetcher for fetching Map Completion Events
2020-03-05 23:03:49,972 INFO reduce.LocalFetcher: localfetcher#2 about to shuffle output of map attempt_local703196677_0002_m_000000_0 decomp: 21 len: 25 to MEMORY
2020-03-05 23:03:49,973 INFO reduce.InMemoryMapOutput: Read 21 bytes from map-output for attempt_local703196677_0002_m_000000_0
2020-03-05 23:03:49,973 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 21, inMemoryMapOutputs.size() -> 1, commitMemory -> 0, usedMemory ->21
2020-03-05 23:03:49,973 INFO reduce.EventFetcher: EventFetcher is interrupted.. Returning
2020-03-05 23:03:49,973 INFO mapred.LocalJobRunner: 1 / 1 copied.
2020-03-05 23:03:49,973 INFO reduce.MergeManagerImpl: finalMerge called with 1 in-memory map-outputs and 0 on-disk map-outputs
2020-03-05 23:03:49,974 INFO mapred.Merger: Merging 1 sorted segments
2020-03-05 23:03:49,974 INFO mapred.Merger: Down to the last merge-pass, with 1 segments left of total size: 11 bytes
2020-03-05 23:03:49,974 INFO reduce.MergeManagerImpl: Merged 1 segments, 21 bytes to disk to satisfy reduce memory limit
2020-03-05 23:03:49,974 INFO reduce.MergeManagerImpl: Merging 1 files, 25 bytes from disk
2020-03-05 23:03:49,974 INFO reduce.MergeManagerImpl: Merging 0 segments, 0 bytes from memory into reduce
2020-03-05 23:03:49,974 INFO mapred.Merger: Merging 1 sorted segments
2020-03-05 23:03:49,975 INFO mapred.Merger: Down to the last merge-pass, with 1 segments left of total size: 11 bytes
2020-03-05 23:03:49,975 INFO mapred.LocalJobRunner: 1 / 1 copied.
2020-03-05 23:03:49,977 INFO mapred.Task: Task:attempt_local703196677_0002_r_000000_0 is done. And is in the process of committing
2020-03-05 23:03:49,978 INFO mapred.LocalJobRunner: 1 / 1 copied.
2020-03-05 23:03:49,978 INFO mapred.Task: Task attempt_local703196677_0002_r_000000_0 is allowed to commit now
2020-03-05 23:03:49,979 INFO output.FileOutputCommitter: Saved output of task 'attempt_local703196677_0002_r_000000_0' to file:/home/zaky/software/bigdata/hadoop-3.2.1/output
2020-03-05 23:03:49,979 INFO mapred.LocalJobRunner: reduce > reduce
2020-03-05 23:03:49,979 INFO mapred.Task: Task 'attempt_local703196677_0002_r_000000_0' done.
2020-03-05 23:03:49,979 INFO mapred.Task: Final Counters for attempt_local703196677_0002_r_000000_0: Counters: 24
	File System Counters
		FILE: Number of bytes read=669334
		FILE: Number of bytes written=1677661
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Combine input records=0
		Combine output records=0
		Reduce input groups=1
		Reduce shuffle bytes=25
		Reduce input records=1
		Reduce output records=1
		Spilled Records=1
		Shuffled Maps =1
		Failed Shuffles=0
		Merged Map outputs=1
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=315621376
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Output Format Counters
		Bytes Written=23
2020-03-05 23:03:49,979 INFO mapred.LocalJobRunner: Finishing task: attempt_local703196677_0002_r_000000_0
2020-03-05 23:03:49,979 INFO mapred.LocalJobRunner: reduce task executor complete.
2020-03-05 23:03:50,937 INFO mapreduce.Job: Job job_local703196677_0002 running in uber mode : false
2020-03-05 23:03:50,937 INFO mapreduce.Job:  map 100% reduce 100%
2020-03-05 23:03:50,938 INFO mapreduce.Job: Job job_local703196677_0002 completed successfully
2020-03-05 23:03:50,941 INFO mapreduce.Job: Counters: 30
	File System Counters
		FILE: Number of bytes read=1338586
		FILE: Number of bytes written=3355274
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=1
		Map output records=1
		Map output bytes=17
		Map output materialized bytes=25
		Input split bytes=144
		Combine input records=0
		Combine output records=0
		Reduce input groups=1
		Reduce shuffle bytes=25
		Reduce input records=1
		Reduce output records=1
		Spilled Records=2
		Shuffled Maps =1
		Failed Shuffles=0
		Merged Map outputs=1
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=631242752
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters
		Bytes Read=123
	File Output Format Counters
		Bytes Written=23
$ 
```

Hasil akan diletakkan pada direktori `output`:

```bash
$ ls -la output/
total 20
drwxr-xr-x  2 zaky zaky 4096 Mar  5 23:03 .
drwxr-xr-x 11 zaky zaky 4096 Mar  5 23:03 ..
-rw-r--r--  1 zaky zaky   11 Mar  5 23:03 part-r-00000
-rw-r--r--  1 zaky zaky   12 Mar  5 23:03 .part-r-00000.crc
-rw-r--r--  1 zaky zaky    0 Mar  5 23:03 _SUCCESS
-rw-r--r--  1 zaky zaky    8 Mar  5 23:03 ._SUCCESS.crc
$  cat output/part-r-00000
1	dfsadmin
$
```

### Pseudo-Distributed

Pada mode ini, Hadoop akan dijalankan pada *single-node* dan setiap daemon dari Hadoop akan berjalan
pada proses Java yang berbeda.

#### Atur *passphrase* untuk ssh

Periksa apakah perlu passphrase:

```bash
$ ssh localhost
The authenticity of host 'localhost (::1)' can't be established.
ECDSA key fingerprint is SHA256:T+8K43amEV3rl1ah5wzuhXvUEJksYP+NAVrVHBBPkSo.
Are you sure you want to continue connecting (yes/no/[fingerprint])? yes
Warning: Permanently added 'localhost' (ECDSA) to the list of known hosts.
zaky@localhost's password:
```

Jika terdapat pertanyaan password, maka berarti passphrase harus diatur:

```bash
$ ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
Generating public/private rsa key pair.
Your identification has been saved in /home/zaky/.ssh/id_rsa
Your public key has been saved in /home/zaky/.ssh/id_rsa.pub
The key fingerprint is:
SHA256:JjZ/blCj8gzJmJZAmD+AAExpq4sYItor+cIbsANC9pc zaky@dellvuan
The key's randomart image is:
+---[RSA 3072]----+
|O+.              |
|=+.              |
|.*.              |
|o.=   .   o      |
|=  + E+.So .     |
|O.  *.==o        |
|X* .   =...      |
|Ooo     oo.      |
| =+.     ..      |
+----[SHA256]-----+
$ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
$ chmod 0600 ~/.ssh/authorized_keys
$ ssh localhost
Linux dellvuan 5.4.0-4-amd64 #1 SMP Debian 5.4.19-1 (2020-02-13) x86_64

The programs included with the Devuan GNU/Linux system are free software;
the exact distribution terms for each program are described in the
individual files in /usr/share/doc/*/copyright.

Devuan GNU/Linux comes with ABSOLUTELY NO WARRANTY, to the extent
permitted by applicable law.
Last login: Thu Mar  5 23:35:57 2020 from ::1
zaky@dellvuan:~$ 
```

#### Konfigurasi Hadoop

Ubah 3 file konfigurasi:

`$HADOOP_HOME/etc/hadoop/core-site.xml`:
```bash
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://localhost:9000</value>
    </property>
</configuration>
```

`$HADOOP_HOME/etc/hadoop/hdfs-site.xml`:
```bash
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
</configuration>
```

`$HADOOP_HOME/etc/hadoop/hadoop-env.sh`
```bash
export JAVA_HOME=/home/zaky/software/bigdata/jdk1.8.0_241
```

### Proses MapReduce

Pada posisi ini, kita bisa membuat pseudo-distributed mode, baik untuk MapReduce yang berjalan di
lokal maupun menggunakan YARN. 

#### Jika menggunakan lokal

1.  Format filesystem:

```bash
$ hdfs namenode -format
2020-03-05 23:52:29,899 INFO namenode.NameNode: STARTUP_MSG:
/************************************************************
STARTUP_MSG: Starting NameNode
STARTUP_MSG:   host = dellvuan.bpdp.name/127.0.1.1
STARTUP_MSG:   args = [-format]
STARTUP_MSG:   version = 3.2.1
STARTUP_MSG:   classpath = .......... <muncul daftar classpath>
STARTUP_MSG:   build = https://gitbox.apache.org/repos/asf/hadoop.git -r b3cbbb467e22ea829b3808f4b7b01d07e0bf3842; compiled by 'rohithsharmaks' on 2019-09-10T15:56Z
STARTUP_MSG:   java = 1.8.0_241
************************************************************/
2020-03-05 23:52:29,915 INFO namenode.NameNode: registered UNIX signal handlers for [TERM, HUP, INT]
2020-03-05 23:52:29,990 INFO namenode.NameNode: createNameNode [-format]
Formatting using clusterid: CID-c227118e-a2b5-4f44-be22-dd950742e96d
2020-03-05 23:52:30,328 INFO namenode.FSEditLog: Edit logging is async:true
2020-03-05 23:52:30,338 INFO namenode.FSNamesystem: KeyProvider: null
2020-03-05 23:52:30,340 INFO namenode.FSNamesystem: fsLock is fair: true
2020-03-05 23:52:30,340 INFO namenode.FSNamesystem: Detailed lock hold time metrics enabled: false
2020-03-05 23:52:30,343 INFO namenode.FSNamesystem: fsOwner             = zaky (auth:SIMPLE)
2020-03-05 23:52:30,344 INFO namenode.FSNamesystem: supergroup          = supergroup
2020-03-05 23:52:30,344 INFO namenode.FSNamesystem: isPermissionEnabled = true
2020-03-05 23:52:30,344 INFO namenode.FSNamesystem: HA Enabled: false
2020-03-05 23:52:30,386 INFO common.Util: dfs.datanode.fileio.profiling.sampling.percentage set to 0. Disabling file IO profiling
2020-03-05 23:52:30,394 INFO blockmanagement.DatanodeManager: dfs.block.invalidate.limit: configured=1000, counted=60, effected=1000
2020-03-05 23:52:30,395 INFO blockmanagement.DatanodeManager: dfs.namenode.datanode.registration.ip-hostname-check=true
2020-03-05 23:52:30,398 INFO blockmanagement.BlockManager: dfs.namenode.startup.delay.block.deletion.sec is set to 000:00:00:00.000
2020-03-05 23:52:30,398 INFO blockmanagement.BlockManager: The block deletion will start around 2020 Mar 05 23:52:30
2020-03-05 23:52:30,399 INFO util.GSet: Computing capacity for map BlocksMap
2020-03-05 23:52:30,399 INFO util.GSet: VM type       = 64-bit
2020-03-05 23:52:30,400 INFO util.GSet: 2.0% max memory 1.7 GB = 34.6 MB
2020-03-05 23:52:30,400 INFO util.GSet: capacity      = 2^22 = 4194304 entries
2020-03-05 23:52:30,407 INFO blockmanagement.BlockManager: Storage policy satisfier is disabled
2020-03-05 23:52:30,407 INFO blockmanagement.BlockManager: dfs.block.access.token.enable = false
2020-03-05 23:52:30,413 INFO Configuration.deprecation: No unit for dfs.namenode.safemode.extension(30000) assuming MILLISECONDS
2020-03-05 23:52:30,413 INFO blockmanagement.BlockManagerSafeMode: dfs.namenode.safemode.threshold-pct = 0.9990000128746033
2020-03-05 23:52:30,413 INFO blockmanagement.BlockManagerSafeMode: dfs.namenode.safemode.min.datanodes = 0
2020-03-05 23:52:30,413 INFO blockmanagement.BlockManagerSafeMode: dfs.namenode.safemode.extension = 30000
2020-03-05 23:52:30,414 INFO blockmanagement.BlockManager: defaultReplication         = 1
2020-03-05 23:52:30,414 INFO blockmanagement.BlockManager: maxReplication             = 512
2020-03-05 23:52:30,414 INFO blockmanagement.BlockManager: minReplication             = 1
2020-03-05 23:52:30,414 INFO blockmanagement.BlockManager: maxReplicationStreams      = 2
2020-03-05 23:52:30,414 INFO blockmanagement.BlockManager: redundancyRecheckInterval  = 3000ms
2020-03-05 23:52:30,414 INFO blockmanagement.BlockManager: encryptDataTransfer        = false
2020-03-05 23:52:30,414 INFO blockmanagement.BlockManager: maxNumBlocksToLog          = 1000
2020-03-05 23:52:30,455 INFO namenode.FSDirectory: GLOBAL serial map: bits=29 maxEntries=536870911
2020-03-05 23:52:30,455 INFO namenode.FSDirectory: USER serial map: bits=24 maxEntries=16777215
2020-03-05 23:52:30,455 INFO namenode.FSDirectory: GROUP serial map: bits=24 maxEntries=16777215
2020-03-05 23:52:30,455 INFO namenode.FSDirectory: XATTR serial map: bits=24 maxEntries=16777215
2020-03-05 23:52:30,486 INFO util.GSet: Computing capacity for map INodeMap
2020-03-05 23:52:30,486 INFO util.GSet: VM type       = 64-bit
2020-03-05 23:52:30,486 INFO util.GSet: 1.0% max memory 1.7 GB = 17.3 MB
2020-03-05 23:52:30,486 INFO util.GSet: capacity      = 2^21 = 2097152 entries
2020-03-05 23:52:30,488 INFO namenode.FSDirectory: ACLs enabled? false
2020-03-05 23:52:30,488 INFO namenode.FSDirectory: POSIX ACL inheritance enabled? true
2020-03-05 23:52:30,488 INFO namenode.FSDirectory: XAttrs enabled? true
2020-03-05 23:52:30,488 INFO namenode.NameNode: Caching file names occurring more than 10 times
2020-03-05 23:52:30,492 INFO snapshot.SnapshotManager: Loaded config captureOpenFiles: false, skipCaptureAccessTimeOnlyChange: false, snapshotDiffAllowSnapRootDescendant: true, maxSnapshotLimit: 65536
2020-03-05 23:52:30,493 INFO snapshot.SnapshotManager: SkipList is disabled
2020-03-05 23:52:30,496 INFO util.GSet: Computing capacity for map cachedBlocks
2020-03-05 23:52:30,496 INFO util.GSet: VM type       = 64-bit
2020-03-05 23:52:30,497 INFO util.GSet: 0.25% max memory 1.7 GB = 4.3 MB
2020-03-05 23:52:30,497 INFO util.GSet: capacity      = 2^19 = 524288 entries
2020-03-05 23:52:30,504 INFO metrics.TopMetrics: NNTop conf: dfs.namenode.top.window.num.buckets = 10
2020-03-05 23:52:30,504 INFO metrics.TopMetrics: NNTop conf: dfs.namenode.top.num.users = 10
2020-03-05 23:52:30,504 INFO metrics.TopMetrics: NNTop conf: dfs.namenode.top.windows.minutes = 1,5,25
2020-03-05 23:52:30,508 INFO namenode.FSNamesystem: Retry cache on namenode is enabled
2020-03-05 23:52:30,508 INFO namenode.FSNamesystem: Retry cache will use 0.03 of total heap and retry cache entry expiry time is 600000 millis
2020-03-05 23:52:30,509 INFO util.GSet: Computing capacity for map NameNodeRetryCache
2020-03-05 23:52:30,509 INFO util.GSet: VM type       = 64-bit
2020-03-05 23:52:30,509 INFO util.GSet: 0.029999999329447746% max memory 1.7 GB = 532.1 KB
2020-03-05 23:52:30,509 INFO util.GSet: capacity      = 2^16 = 65536 entries
2020-03-05 23:52:30,528 INFO namenode.FSImage: Allocated new BlockPoolId: BP-1709828598-127.0.1.1-1583427150522
2020-03-05 23:52:30,677 INFO common.Storage: Storage directory /tmp/hadoop-zaky/dfs/name has been successfully formatted.
2020-03-05 23:52:30,738 INFO namenode.FSImageFormatProtobuf: Saving image file /tmp/hadoop-zaky/dfs/name/current/fsimage.ckpt_0000000000000000000 using no compression
2020-03-05 23:52:30,805 INFO namenode.FSImageFormatProtobuf: Image file /tmp/hadoop-zaky/dfs/name/current/fsimage.ckpt_0000000000000000000 of size 399 bytes saved in 0 seconds .
2020-03-05 23:52:30,852 INFO namenode.NNStorageRetentionManager: Going to retain 1 images with txid >= 0
2020-03-05 23:52:30,856 INFO namenode.FSImage: FSImageSaver clean checkpoint: txid=0 when meet shutdown.
2020-03-05 23:52:30,857 INFO namenode.NameNode: SHUTDOWN_MSG:
/************************************************************
SHUTDOWN_MSG: Shutting down NameNode at dellvuan.bpdp.name/127.0.1.1
************************************************************/

```

2.  Jalankan *daemon* untuk NameNode dan DataNode

```bash
$ start-dfs.sh
Starting namenodes on [localhost]
Starting datanodes
Starting secondary namenodes [dellvuan]
$
```

Setelah daemon berjalan, antarmuka Web untuk memantau HDFS bisa dilihat di http://localhost:9870.

3.  Buat direktori yang diperluakn di HDFS:

```bash
$ hdfs dfs -mkdir /user
$ hdfs dfs -mkdir /user/zaky
$ hdfs dfs -mkdir input
```

4. Copy semua file-file yang diperlukan

```bash
$ hdfs dfs -put ~/software/bigdata/hadoop-3.2.1/etc/hadoop/*.xml input
2020-03-06 00:45:38,954 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 00:45:39,559 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 00:45:39,626 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 00:45:39,703 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 00:45:39,782 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 00:45:39,858 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 00:45:39,926 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 00:45:40,004 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 00:45:40,082 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
$ hdfs dfs -ls
Found 1 items
drwxr-xr-x   - zaky supergroup          0 2020-03-06 00:45 input
$
```

5.  Kerjakan proses MapReduce 

```bash
hadoop jar ~/software/bigdata/hadoop-3.2.1/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.1.jar grep input output 'dfs[a-z.]+'
2020-03-06 00:50:11,243 INFO impl.MetricsConfig: Loaded properties from hadoop-metrics2.properties
2020-03-06 00:50:11,288 INFO impl.MetricsSystemImpl: Scheduled Metric snapshot period at 10 second(s).
2020-03-06 00:50:11,288 INFO impl.MetricsSystemImpl: JobTracker metrics system started
2020-03-06 00:50:11,582 INFO input.FileInputFormat: Total input files to process : 9
2020-03-06 00:50:11,597 INFO mapreduce.JobSubmitter: number of splits:9
2020-03-06 00:50:11,673 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_local233675510_0001
2020-03-06 00:50:11,673 INFO mapreduce.JobSubmitter: Executing with tokens: []
2020-03-06 00:50:11,747 INFO mapreduce.Job: The url to track the job: http://localhost:8080/
2020-03-06 00:50:11,748 INFO mapreduce.Job: Running job: job_local233675510_0001
2020-03-06 00:50:11,749 INFO mapred.LocalJobRunner: OutputCommitter set in config null
2020-03-06 00:50:11,755 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:11,755 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:11,756 INFO mapred.LocalJobRunner: OutputCommitter is org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter
2020-03-06 00:50:11,804 INFO mapred.LocalJobRunner: Waiting for map tasks
2020-03-06 00:50:11,804 INFO mapred.LocalJobRunner: Starting task: attempt_local233675510_0001_m_000000_0
2020-03-06 00:50:11,822 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:11,822 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:11,832 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-06 00:50:11,834 INFO mapred.MapTask: Processing split: hdfs://localhost:9000/user/zaky/input/hadoop-policy.xml:0+11392
2020-03-06 00:50:11,885 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-06 00:50:11,885 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-06 00:50:11,885 INFO mapred.MapTask: soft limit at 83886080
2020-03-06 00:50:11,885 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-06 00:50:11,885 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-06 00:50:11,888 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-06 00:50:11,910 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 00:50:11,988 INFO mapred.LocalJobRunner:
2020-03-06 00:50:11,989 INFO mapred.MapTask: Starting flush of map output
2020-03-06 00:50:11,989 INFO mapred.MapTask: Spilling map output
2020-03-06 00:50:11,989 INFO mapred.MapTask: bufstart = 0; bufend = 17; bufvoid = 104857600
2020-03-06 00:50:11,989 INFO mapred.MapTask: kvstart = 26214396(104857584); kvend = 26214396(104857584); length = 1/6553600
2020-03-06 00:50:11,998 INFO mapred.MapTask: Finished spill 0
2020-03-06 00:50:12,006 INFO mapred.Task: Task:attempt_local233675510_0001_m_000000_0 is done. And is in the process of committing
2020-03-06 00:50:12,009 INFO mapred.LocalJobRunner: map
2020-03-06 00:50:12,009 INFO mapred.Task: Task 'attempt_local233675510_0001_m_000000_0' done.
2020-03-06 00:50:12,014 INFO mapred.Task: Final Counters for attempt_local233675510_0001_m_000000_0: Counters: 24
	File System Counters
		FILE: Number of bytes read=317871
		FILE: Number of bytes written=838971
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=11392
		HDFS: Number of bytes written=0
		HDFS: Number of read operations=5
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=1
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Map input records=265
		Map output records=1
		Map output bytes=17
		Map output materialized bytes=25
		Input split bytes=120
		Combine input records=1
		Combine output records=1
		Spilled Records=1
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=289406976
	File Input Format Counters
		Bytes Read=11392
2020-03-06 00:50:12,014 INFO mapred.LocalJobRunner: Finishing task: attempt_local233675510_0001_m_000000_0
2020-03-06 00:50:12,015 INFO mapred.LocalJobRunner: Starting task: attempt_local233675510_0001_m_000001_0
2020-03-06 00:50:12,016 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:12,016 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:12,017 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-06 00:50:12,017 INFO mapred.MapTask: Processing split: hdfs://localhost:9000/user/zaky/input/capacity-scheduler.xml:0+8260
2020-03-06 00:50:12,059 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-06 00:50:12,059 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-06 00:50:12,059 INFO mapred.MapTask: soft limit at 83886080
2020-03-06 00:50:12,059 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-06 00:50:12,059 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-06 00:50:12,060 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-06 00:50:12,068 INFO mapred.LocalJobRunner:
2020-03-06 00:50:12,068 INFO mapred.MapTask: Starting flush of map output
2020-03-06 00:50:12,070 INFO mapred.Task: Task:attempt_local233675510_0001_m_000001_0 is done. And is in the process of committing
2020-03-06 00:50:12,072 INFO mapred.LocalJobRunner: map
2020-03-06 00:50:12,072 INFO mapred.Task: Task 'attempt_local233675510_0001_m_000001_0' done.
2020-03-06 00:50:12,072 INFO mapred.Task: Final Counters for attempt_local233675510_0001_m_000001_0: Counters: 24
	File System Counters
		FILE: Number of bytes read=318957
		FILE: Number of bytes written=839009
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=19652
		HDFS: Number of bytes written=0
		HDFS: Number of read operations=7
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=1
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Map input records=220
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=125
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=394788864
	File Input Format Counters
		Bytes Read=8260
2020-03-06 00:50:12,073 INFO mapred.LocalJobRunner: Finishing task: attempt_local233675510_0001_m_000001_0
2020-03-06 00:50:12,073 INFO mapred.LocalJobRunner: Starting task: attempt_local233675510_0001_m_000002_0
2020-03-06 00:50:12,073 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:12,073 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:12,074 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-06 00:50:12,075 INFO mapred.MapTask: Processing split: hdfs://localhost:9000/user/zaky/input/kms-acls.xml:0+3518
2020-03-06 00:50:12,133 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-06 00:50:12,133 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-06 00:50:12,133 INFO mapred.MapTask: soft limit at 83886080
2020-03-06 00:50:12,133 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-06 00:50:12,133 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-06 00:50:12,134 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-06 00:50:12,139 INFO mapred.LocalJobRunner:
2020-03-06 00:50:12,139 INFO mapred.MapTask: Starting flush of map output
2020-03-06 00:50:12,141 INFO mapred.Task: Task:attempt_local233675510_0001_m_000002_0 is done. And is in the process of committing
2020-03-06 00:50:12,143 INFO mapred.LocalJobRunner: map
2020-03-06 00:50:12,143 INFO mapred.Task: Task 'attempt_local233675510_0001_m_000002_0' done.
2020-03-06 00:50:12,144 INFO mapred.Task: Final Counters for attempt_local233675510_0001_m_000002_0: Counters: 24
	File System Counters
		FILE: Number of bytes read=320043
		FILE: Number of bytes written=839047
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=23170
		HDFS: Number of bytes written=0
		HDFS: Number of read operations=9
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=1
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Map input records=135
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=115
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=500170752
	File Input Format Counters
		Bytes Read=3518
2020-03-06 00:50:12,144 INFO mapred.LocalJobRunner: Finishing task: attempt_local233675510_0001_m_000002_0
2020-03-06 00:50:12,144 INFO mapred.LocalJobRunner: Starting task: attempt_local233675510_0001_m_000003_0
2020-03-06 00:50:12,146 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:12,146 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:12,146 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-06 00:50:12,147 INFO mapred.MapTask: Processing split: hdfs://localhost:9000/user/zaky/input/core-site.xml:0+884
2020-03-06 00:50:12,209 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-06 00:50:12,209 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-06 00:50:12,209 INFO mapred.MapTask: soft limit at 83886080
2020-03-06 00:50:12,209 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-06 00:50:12,209 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-06 00:50:12,210 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-06 00:50:12,213 INFO mapred.LocalJobRunner:
2020-03-06 00:50:12,213 INFO mapred.MapTask: Starting flush of map output
2020-03-06 00:50:12,215 INFO mapred.Task: Task:attempt_local233675510_0001_m_000003_0 is done. And is in the process of committing
2020-03-06 00:50:12,217 INFO mapred.LocalJobRunner: map
2020-03-06 00:50:12,217 INFO mapred.Task: Task 'attempt_local233675510_0001_m_000003_0' done.
2020-03-06 00:50:12,218 INFO mapred.Task: Final Counters for attempt_local233675510_0001_m_000003_0: Counters: 24
	File System Counters
		FILE: Number of bytes read=321129
		FILE: Number of bytes written=839085
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=24054
		HDFS: Number of bytes written=0
		HDFS: Number of read operations=11
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=1
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Map input records=24
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=116
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=605552640
	File Input Format Counters
		Bytes Read=884
2020-03-06 00:50:12,218 INFO mapred.LocalJobRunner: Finishing task: attempt_local233675510_0001_m_000003_0
2020-03-06 00:50:12,218 INFO mapred.LocalJobRunner: Starting task: attempt_local233675510_0001_m_000004_0
2020-03-06 00:50:12,219 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:12,219 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:12,219 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-06 00:50:12,220 INFO mapred.MapTask: Processing split: hdfs://localhost:9000/user/zaky/input/hdfs-site.xml:0+867
2020-03-06 00:50:12,256 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-06 00:50:12,257 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-06 00:50:12,257 INFO mapred.MapTask: soft limit at 83886080
2020-03-06 00:50:12,257 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-06 00:50:12,257 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-06 00:50:12,258 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-06 00:50:12,263 INFO mapred.LocalJobRunner:
2020-03-06 00:50:12,263 INFO mapred.MapTask: Starting flush of map output
2020-03-06 00:50:12,263 INFO mapred.MapTask: Spilling map output
2020-03-06 00:50:12,263 INFO mapred.MapTask: bufstart = 0; bufend = 24; bufvoid = 104857600
2020-03-06 00:50:12,263 INFO mapred.MapTask: kvstart = 26214396(104857584); kvend = 26214396(104857584); length = 1/6553600
2020-03-06 00:50:12,264 INFO mapred.MapTask: Finished spill 0
2020-03-06 00:50:12,265 INFO mapred.Task: Task:attempt_local233675510_0001_m_000004_0 is done. And is in the process of committing
2020-03-06 00:50:12,268 INFO mapred.LocalJobRunner: map
2020-03-06 00:50:12,268 INFO mapred.Task: Task 'attempt_local233675510_0001_m_000004_0' done.
2020-03-06 00:50:12,269 INFO mapred.Task: Final Counters for attempt_local233675510_0001_m_000004_0: Counters: 24
	File System Counters
		FILE: Number of bytes read=322215
		FILE: Number of bytes written=839149
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=24921
		HDFS: Number of bytes written=0
		HDFS: Number of read operations=13
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=1
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Map input records=24
		Map output records=1
		Map output bytes=24
		Map output materialized bytes=32
		Input split bytes=116
		Combine input records=1
		Combine output records=1
		Spilled Records=1
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=710934528
	File Input Format Counters
		Bytes Read=867
2020-03-06 00:50:12,269 INFO mapred.LocalJobRunner: Finishing task: attempt_local233675510_0001_m_000004_0
2020-03-06 00:50:12,269 INFO mapred.LocalJobRunner: Starting task: attempt_local233675510_0001_m_000005_0
2020-03-06 00:50:12,270 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:12,270 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:12,270 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-06 00:50:12,272 INFO mapred.MapTask: Processing split: hdfs://localhost:9000/user/zaky/input/mapred-site.xml:0+758
2020-03-06 00:50:12,336 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-06 00:50:12,336 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-06 00:50:12,336 INFO mapred.MapTask: soft limit at 83886080
2020-03-06 00:50:12,336 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-06 00:50:12,336 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-06 00:50:12,337 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-06 00:50:12,342 INFO mapred.LocalJobRunner:
2020-03-06 00:50:12,343 INFO mapred.MapTask: Starting flush of map output
2020-03-06 00:50:12,344 INFO mapred.Task: Task:attempt_local233675510_0001_m_000005_0 is done. And is in the process of committing
2020-03-06 00:50:12,346 INFO mapred.LocalJobRunner: map
2020-03-06 00:50:12,347 INFO mapred.Task: Task 'attempt_local233675510_0001_m_000005_0' done.
2020-03-06 00:50:12,347 INFO mapred.Task: Final Counters for attempt_local233675510_0001_m_000005_0: Counters: 24
	File System Counters
		FILE: Number of bytes read=322789
		FILE: Number of bytes written=839187
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=25679
		HDFS: Number of bytes written=0
		HDFS: Number of read operations=15
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=1
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Map input records=21
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=118
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=816316416
	File Input Format Counters
		Bytes Read=758
2020-03-06 00:50:12,347 INFO mapred.LocalJobRunner: Finishing task: attempt_local233675510_0001_m_000005_0
2020-03-06 00:50:12,347 INFO mapred.LocalJobRunner: Starting task: attempt_local233675510_0001_m_000006_0
2020-03-06 00:50:12,348 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:12,348 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:12,348 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-06 00:50:12,358 INFO mapred.MapTask: Processing split: hdfs://localhost:9000/user/zaky/input/yarn-site.xml:0+690
2020-03-06 00:50:12,366 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-06 00:50:12,366 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-06 00:50:12,366 INFO mapred.MapTask: soft limit at 83886080
2020-03-06 00:50:12,366 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-06 00:50:12,366 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-06 00:50:12,367 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-06 00:50:12,370 INFO mapred.LocalJobRunner:
2020-03-06 00:50:12,370 INFO mapred.MapTask: Starting flush of map output
2020-03-06 00:50:12,371 INFO mapred.Task: Task:attempt_local233675510_0001_m_000006_0 is done. And is in the process of committing
2020-03-06 00:50:12,374 INFO mapred.LocalJobRunner: map
2020-03-06 00:50:12,374 INFO mapred.Task: Task 'attempt_local233675510_0001_m_000006_0' done.
2020-03-06 00:50:12,374 INFO mapred.Task: Final Counters for attempt_local233675510_0001_m_000006_0: Counters: 24
	File System Counters
		FILE: Number of bytes read=323363
		FILE: Number of bytes written=839225
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=26369
		HDFS: Number of bytes written=0
		HDFS: Number of read operations=17
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=1
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Map input records=19
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=116
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=9
		Total committed heap usage (bytes)=816316416
	File Input Format Counters
		Bytes Read=690
2020-03-06 00:50:12,374 INFO mapred.LocalJobRunner: Finishing task: attempt_local233675510_0001_m_000006_0
2020-03-06 00:50:12,374 INFO mapred.LocalJobRunner: Starting task: attempt_local233675510_0001_m_000007_0
2020-03-06 00:50:12,375 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:12,375 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:12,375 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-06 00:50:12,376 INFO mapred.MapTask: Processing split: hdfs://localhost:9000/user/zaky/input/kms-site.xml:0+682
2020-03-06 00:50:12,437 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-06 00:50:12,437 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-06 00:50:12,437 INFO mapred.MapTask: soft limit at 83886080
2020-03-06 00:50:12,437 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-06 00:50:12,437 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-06 00:50:12,437 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-06 00:50:12,441 INFO mapred.LocalJobRunner:
2020-03-06 00:50:12,441 INFO mapred.MapTask: Starting flush of map output
2020-03-06 00:50:12,442 INFO mapred.Task: Task:attempt_local233675510_0001_m_000007_0 is done. And is in the process of committing
2020-03-06 00:50:12,444 INFO mapred.LocalJobRunner: map
2020-03-06 00:50:12,444 INFO mapred.Task: Task 'attempt_local233675510_0001_m_000007_0' done.
2020-03-06 00:50:12,444 INFO mapred.Task: Final Counters for attempt_local233675510_0001_m_000007_0: Counters: 24
	File System Counters
		FILE: Number of bytes read=323937
		FILE: Number of bytes written=839263
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=27051
		HDFS: Number of bytes written=0
		HDFS: Number of read operations=19
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=1
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Map input records=20
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=115
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=921698304
	File Input Format Counters
		Bytes Read=682
2020-03-06 00:50:12,444 INFO mapred.LocalJobRunner: Finishing task: attempt_local233675510_0001_m_000007_0
2020-03-06 00:50:12,444 INFO mapred.LocalJobRunner: Starting task: attempt_local233675510_0001_m_000008_0
2020-03-06 00:50:12,445 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:12,445 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:12,445 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-06 00:50:12,446 INFO mapred.MapTask: Processing split: hdfs://localhost:9000/user/zaky/input/httpfs-site.xml:0+620
2020-03-06 00:50:12,493 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-06 00:50:12,493 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-06 00:50:12,493 INFO mapred.MapTask: soft limit at 83886080
2020-03-06 00:50:12,493 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-06 00:50:12,493 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-06 00:50:12,493 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-06 00:50:12,496 INFO mapred.LocalJobRunner:
2020-03-06 00:50:12,496 INFO mapred.MapTask: Starting flush of map output
2020-03-06 00:50:12,498 INFO mapred.Task: Task:attempt_local233675510_0001_m_000008_0 is done. And is in the process of committing
2020-03-06 00:50:12,499 INFO mapred.LocalJobRunner: map
2020-03-06 00:50:12,499 INFO mapred.Task: Task 'attempt_local233675510_0001_m_000008_0' done.
2020-03-06 00:50:12,500 INFO mapred.Task: Final Counters for attempt_local233675510_0001_m_000008_0: Counters: 24
	File System Counters
		FILE: Number of bytes read=324511
		FILE: Number of bytes written=839301
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=27671
		HDFS: Number of bytes written=0
		HDFS: Number of read operations=21
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=1
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Map input records=17
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=118
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=1027080192
	File Input Format Counters
		Bytes Read=620
2020-03-06 00:50:12,500 INFO mapred.LocalJobRunner: Finishing task: attempt_local233675510_0001_m_000008_0
2020-03-06 00:50:12,500 INFO mapred.LocalJobRunner: map task executor complete.
2020-03-06 00:50:12,502 INFO mapred.LocalJobRunner: Waiting for reduce tasks
2020-03-06 00:50:12,502 INFO mapred.LocalJobRunner: Starting task: attempt_local233675510_0001_r_000000_0
2020-03-06 00:50:12,507 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:12,507 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:12,507 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-06 00:50:12,510 INFO mapred.ReduceTask: Using ShuffleConsumerPlugin: org.apache.hadoop.mapreduce.task.reduce.Shuffle@393973b2
2020-03-06 00:50:12,511 WARN impl.MetricsSystemImpl: JobTracker metrics system already initialized!
2020-03-06 00:50:12,524 INFO reduce.MergeManagerImpl: MergerManager: memoryLimit=1271293568, maxSingleShuffleLimit=317823392, mergeThreshold=839053760, ioSortFactor=10, memToMemMergeOutputsThreshold=10
2020-03-06 00:50:12,526 INFO reduce.EventFetcher: attempt_local233675510_0001_r_000000_0 Thread started: EventFetcher for fetching Map Completion Events
2020-03-06 00:50:12,545 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local233675510_0001_m_000001_0 decomp: 2 len: 6 to MEMORY
2020-03-06 00:50:12,548 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local233675510_0001_m_000001_0
2020-03-06 00:50:12,549 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 1, commitMemory -> 0, usedMemory ->2
2020-03-06 00:50:12,551 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local233675510_0001_m_000008_0 decomp: 2 len: 6 to MEMORY
2020-03-06 00:50:12,551 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local233675510_0001_m_000008_0
2020-03-06 00:50:12,551 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 2, commitMemory -> 2, usedMemory ->4
2020-03-06 00:50:12,552 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local233675510_0001_m_000002_0 decomp: 2 len: 6 to MEMORY
2020-03-06 00:50:12,552 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local233675510_0001_m_000002_0
2020-03-06 00:50:12,552 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 3, commitMemory -> 4, usedMemory ->6
2020-03-06 00:50:12,553 WARN io.ReadaheadPool: Failed readahead on ifile
EBADF: Bad file descriptor
	at org.apache.hadoop.io.nativeio.NativeIO$POSIX.posix_fadvise(Native Method)
	at org.apache.hadoop.io.nativeio.NativeIO$POSIX.posixFadviseIfPossible(NativeIO.java:271)
	at org.apache.hadoop.io.nativeio.NativeIO$POSIX$CacheManipulator.posixFadviseIfPossible(NativeIO.java:148)
	at org.apache.hadoop.io.ReadaheadPool$ReadaheadRequestImpl.run(ReadaheadPool.java:209)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
2020-03-06 00:50:12,553 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local233675510_0001_m_000005_0 decomp: 2 len: 6 to MEMORY
2020-03-06 00:50:12,555 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local233675510_0001_m_000005_0
2020-03-06 00:50:12,555 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 4, commitMemory -> 6, usedMemory ->8
2020-03-06 00:50:12,555 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local233675510_0001_m_000006_0 decomp: 2 len: 6 to MEMORY
2020-03-06 00:50:12,556 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local233675510_0001_m_000006_0
2020-03-06 00:50:12,556 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 5, commitMemory -> 8, usedMemory ->10
2020-03-06 00:50:12,556 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local233675510_0001_m_000000_0 decomp: 21 len: 25 to MEMORY
2020-03-06 00:50:12,557 INFO reduce.InMemoryMapOutput: Read 21 bytes from map-output for attempt_local233675510_0001_m_000000_0
2020-03-06 00:50:12,557 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 21, inMemoryMapOutputs.size() -> 6, commitMemory -> 10, usedMemory ->31
2020-03-06 00:50:12,557 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local233675510_0001_m_000003_0 decomp: 2 len: 6 to MEMORY
2020-03-06 00:50:12,558 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local233675510_0001_m_000003_0
2020-03-06 00:50:12,558 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 7, commitMemory -> 31, usedMemory ->33
2020-03-06 00:50:12,558 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local233675510_0001_m_000004_0 decomp: 28 len: 32 to MEMORY
2020-03-06 00:50:12,558 INFO reduce.InMemoryMapOutput: Read 28 bytes from map-output for attempt_local233675510_0001_m_000004_0
2020-03-06 00:50:12,559 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 28, inMemoryMapOutputs.size() -> 8, commitMemory -> 33, usedMemory ->61
2020-03-06 00:50:12,559 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local233675510_0001_m_000007_0 decomp: 2 len: 6 to MEMORY
2020-03-06 00:50:12,559 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local233675510_0001_m_000007_0
2020-03-06 00:50:12,559 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 9, commitMemory -> 61, usedMemory ->63
2020-03-06 00:50:12,560 INFO reduce.EventFetcher: EventFetcher is interrupted.. Returning
2020-03-06 00:50:12,560 INFO mapred.LocalJobRunner: 9 / 9 copied.
2020-03-06 00:50:12,560 INFO reduce.MergeManagerImpl: finalMerge called with 9 in-memory map-outputs and 0 on-disk map-outputs
2020-03-06 00:50:12,571 INFO mapred.Merger: Merging 9 sorted segments
2020-03-06 00:50:12,571 INFO mapred.Merger: Down to the last merge-pass, with 2 segments left of total size: 20 bytes
2020-03-06 00:50:12,572 INFO reduce.MergeManagerImpl: Merged 9 segments, 63 bytes to disk to satisfy reduce memory limit
2020-03-06 00:50:12,572 INFO reduce.MergeManagerImpl: Merging 1 files, 51 bytes from disk
2020-03-06 00:50:12,573 INFO reduce.MergeManagerImpl: Merging 0 segments, 0 bytes from memory into reduce
2020-03-06 00:50:12,573 INFO mapred.Merger: Merging 1 sorted segments
2020-03-06 00:50:12,573 INFO mapred.Merger: Down to the last merge-pass, with 1 segments left of total size: 29 bytes
2020-03-06 00:50:12,573 INFO mapred.LocalJobRunner: 9 / 9 copied.
2020-03-06 00:50:12,751 INFO mapreduce.Job: Job job_local233675510_0001 running in uber mode : false
2020-03-06 00:50:12,754 INFO mapreduce.Job:  map 100% reduce 0%
2020-03-06 00:50:13,092 INFO Configuration.deprecation: mapred.skip.on is deprecated. Instead, use mapreduce.job.skiprecords
2020-03-06 00:50:13,174 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 00:50:13,225 INFO mapred.Task: Task:attempt_local233675510_0001_r_000000_0 is done. And is in the process of committing
2020-03-06 00:50:13,226 INFO mapred.LocalJobRunner: 9 / 9 copied.
2020-03-06 00:50:13,227 INFO mapred.Task: Task attempt_local233675510_0001_r_000000_0 is allowed to commit now
2020-03-06 00:50:13,250 INFO output.FileOutputCommitter: Saved output of task 'attempt_local233675510_0001_r_000000_0' to hdfs://localhost:9000/user/zaky/grep-temp-1221451494
2020-03-06 00:50:13,251 INFO mapred.LocalJobRunner: reduce > reduce
2020-03-06 00:50:13,251 INFO mapred.Task: Task 'attempt_local233675510_0001_r_000000_0' done.
2020-03-06 00:50:13,252 INFO mapred.Task: Final Counters for attempt_local233675510_0001_r_000000_0: Counters: 30
	File System Counters
		FILE: Number of bytes read=324949
		FILE: Number of bytes written=839352
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=27671
		HDFS: Number of bytes written=143
		HDFS: Number of read operations=26
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=3
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Combine input records=0
		Combine output records=0
		Reduce input groups=2
		Reduce shuffle bytes=99
		Reduce input records=2
		Reduce output records=2
		Spilled Records=2
		Shuffled Maps =9
		Failed Shuffles=0
		Merged Map outputs=9
		GC time elapsed (ms)=6
		Total committed heap usage (bytes)=1109917696
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Output Format Counters
		Bytes Written=143
2020-03-06 00:50:13,252 INFO mapred.LocalJobRunner: Finishing task: attempt_local233675510_0001_r_000000_0
2020-03-06 00:50:13,253 INFO mapred.LocalJobRunner: reduce task executor complete.
2020-03-06 00:50:13,756 INFO mapreduce.Job:  map 100% reduce 100%
2020-03-06 00:50:13,757 INFO mapreduce.Job: Job job_local233675510_0001 completed successfully
2020-03-06 00:50:13,768 INFO mapreduce.Job: Counters: 36
	File System Counters
		FILE: Number of bytes read=3219764
		FILE: Number of bytes written=8391589
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=237630
		HDFS: Number of bytes written=143
		HDFS: Number of read operations=143
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=12
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Map input records=745
		Map output records=2
		Map output bytes=41
		Map output materialized bytes=99
		Input split bytes=1059
		Combine input records=2
		Combine output records=2
		Reduce input groups=2
		Reduce shuffle bytes=99
		Reduce input records=2
		Reduce output records=2
		Spilled Records=4
		Shuffled Maps =9
		Failed Shuffles=0
		Merged Map outputs=9
		GC time elapsed (ms)=15
		Total committed heap usage (bytes)=7192182784
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters
		Bytes Read=27671
	File Output Format Counters
		Bytes Written=143
2020-03-06 00:50:13,778 WARN impl.MetricsSystemImpl: JobTracker metrics system already initialized!
2020-03-06 00:50:13,788 INFO input.FileInputFormat: Total input files to process : 1
2020-03-06 00:50:13,789 INFO mapreduce.JobSubmitter: number of splits:1
2020-03-06 00:50:13,799 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_local546282602_0002
2020-03-06 00:50:13,799 INFO mapreduce.JobSubmitter: Executing with tokens: []
2020-03-06 00:50:13,840 INFO mapreduce.Job: The url to track the job: http://localhost:8080/
2020-03-06 00:50:13,840 INFO mapreduce.Job: Running job: job_local546282602_0002
2020-03-06 00:50:13,840 INFO mapred.LocalJobRunner: OutputCommitter set in config null
2020-03-06 00:50:13,840 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:13,840 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:13,841 INFO mapred.LocalJobRunner: OutputCommitter is org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter
2020-03-06 00:50:13,903 INFO mapred.LocalJobRunner: Waiting for map tasks
2020-03-06 00:50:13,903 INFO mapred.LocalJobRunner: Starting task: attempt_local546282602_0002_m_000000_0
2020-03-06 00:50:13,905 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:13,905 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:13,906 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-06 00:50:13,908 INFO mapred.MapTask: Processing split: hdfs://localhost:9000/user/zaky/grep-temp-1221451494/part-r-00000:0+143
2020-03-06 00:50:13,945 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-06 00:50:13,945 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-06 00:50:13,945 INFO mapred.MapTask: soft limit at 83886080
2020-03-06 00:50:13,945 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-06 00:50:13,945 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-06 00:50:13,946 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-06 00:50:13,956 INFO mapred.LocalJobRunner:
2020-03-06 00:50:13,956 INFO mapred.MapTask: Starting flush of map output
2020-03-06 00:50:13,956 INFO mapred.MapTask: Spilling map output
2020-03-06 00:50:13,956 INFO mapred.MapTask: bufstart = 0; bufend = 41; bufvoid = 104857600
2020-03-06 00:50:13,956 INFO mapred.MapTask: kvstart = 26214396(104857584); kvend = 26214392(104857568); length = 5/6553600
2020-03-06 00:50:13,957 INFO mapred.MapTask: Finished spill 0
2020-03-06 00:50:13,958 INFO mapred.Task: Task:attempt_local546282602_0002_m_000000_0 is done. And is in the process of committing
2020-03-06 00:50:13,960 INFO mapred.LocalJobRunner: map
2020-03-06 00:50:13,960 INFO mapred.Task: Task 'attempt_local546282602_0002_m_000000_0' done.
2020-03-06 00:50:13,960 INFO mapred.Task: Final Counters for attempt_local546282602_0002_m_000000_0: Counters: 23
	File System Counters
		FILE: Number of bytes read=641677
		FILE: Number of bytes written=1675362
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=27814
		HDFS: Number of bytes written=143
		HDFS: Number of read operations=32
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=6
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Map input records=2
		Map output records=2
		Map output bytes=41
		Map output materialized bytes=51
		Input split bytes=130
		Combine input records=0
		Spilled Records=2
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=1109917696
	File Input Format Counters
		Bytes Read=143
2020-03-06 00:50:13,960 INFO mapred.LocalJobRunner: Finishing task: attempt_local546282602_0002_m_000000_0
2020-03-06 00:50:13,960 INFO mapred.LocalJobRunner: map task executor complete.
2020-03-06 00:50:13,961 INFO mapred.LocalJobRunner: Waiting for reduce tasks
2020-03-06 00:50:13,961 INFO mapred.LocalJobRunner: Starting task: attempt_local546282602_0002_r_000000_0
2020-03-06 00:50:13,962 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-06 00:50:13,962 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-06 00:50:13,962 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-06 00:50:13,963 INFO mapred.ReduceTask: Using ShuffleConsumerPlugin: org.apache.hadoop.mapreduce.task.reduce.Shuffle@4c0ee6d7
2020-03-06 00:50:13,963 WARN impl.MetricsSystemImpl: JobTracker metrics system already initialized!
2020-03-06 00:50:13,965 INFO reduce.MergeManagerImpl: MergerManager: memoryLimit=1271293568, maxSingleShuffleLimit=317823392, mergeThreshold=839053760, ioSortFactor=10, memToMemMergeOutputsThreshold=10
2020-03-06 00:50:13,966 INFO reduce.EventFetcher: attempt_local546282602_0002_r_000000_0 Thread started: EventFetcher for fetching Map Completion Events
2020-03-06 00:50:13,967 INFO reduce.LocalFetcher: localfetcher#2 about to shuffle output of map attempt_local546282602_0002_m_000000_0 decomp: 47 len: 51 to MEMORY
2020-03-06 00:50:13,967 INFO reduce.InMemoryMapOutput: Read 47 bytes from map-output for attempt_local546282602_0002_m_000000_0
2020-03-06 00:50:13,967 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 47, inMemoryMapOutputs.size() -> 1, commitMemory -> 0, usedMemory ->47
2020-03-06 00:50:13,968 INFO reduce.EventFetcher: EventFetcher is interrupted.. Returning
2020-03-06 00:50:13,968 INFO mapred.LocalJobRunner: 1 / 1 copied.
2020-03-06 00:50:13,968 INFO reduce.MergeManagerImpl: finalMerge called with 1 in-memory map-outputs and 0 on-disk map-outputs
2020-03-06 00:50:13,969 INFO mapred.Merger: Merging 1 sorted segments
2020-03-06 00:50:13,969 INFO mapred.Merger: Down to the last merge-pass, with 1 segments left of total size: 37 bytes
2020-03-06 00:50:13,969 INFO reduce.MergeManagerImpl: Merged 1 segments, 47 bytes to disk to satisfy reduce memory limit
2020-03-06 00:50:13,969 INFO reduce.MergeManagerImpl: Merging 1 files, 51 bytes from disk
2020-03-06 00:50:13,969 INFO reduce.MergeManagerImpl: Merging 0 segments, 0 bytes from memory into reduce
2020-03-06 00:50:13,969 INFO mapred.Merger: Merging 1 sorted segments
2020-03-06 00:50:13,969 INFO mapred.Merger: Down to the last merge-pass, with 1 segments left of total size: 37 bytes
2020-03-06 00:50:13,970 INFO mapred.LocalJobRunner: 1 / 1 copied.
2020-03-06 00:50:14,001 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 00:50:14,012 INFO mapred.Task: Task:attempt_local546282602_0002_r_000000_0 is done. And is in the process of committing
2020-03-06 00:50:14,013 INFO mapred.LocalJobRunner: 1 / 1 copied.
2020-03-06 00:50:14,014 INFO mapred.Task: Task attempt_local546282602_0002_r_000000_0 is allowed to commit now
2020-03-06 00:50:14,023 INFO output.FileOutputCommitter: Saved output of task 'attempt_local546282602_0002_r_000000_0' to hdfs://localhost:9000/user/zaky/output
2020-03-06 00:50:14,023 INFO mapred.LocalJobRunner: reduce > reduce
2020-03-06 00:50:14,023 INFO mapred.Task: Task 'attempt_local546282602_0002_r_000000_0' done.
2020-03-06 00:50:14,024 INFO mapred.Task: Final Counters for attempt_local546282602_0002_r_000000_0: Counters: 30
	File System Counters
		FILE: Number of bytes read=641811
		FILE: Number of bytes written=1675413
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=27814
		HDFS: Number of bytes written=172
		HDFS: Number of read operations=37
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=8
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Combine input records=0
		Combine output records=0
		Reduce input groups=1
		Reduce shuffle bytes=51
		Reduce input records=2
		Reduce output records=2
		Spilled Records=2
		Shuffled Maps =1
		Failed Shuffles=0
		Merged Map outputs=1
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=1109917696
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Output Format Counters
		Bytes Written=29
2020-03-06 00:50:14,024 INFO mapred.LocalJobRunner: Finishing task: attempt_local546282602_0002_r_000000_0
2020-03-06 00:50:14,024 INFO mapred.LocalJobRunner: reduce task executor complete.
2020-03-06 00:50:14,841 INFO mapreduce.Job: Job job_local546282602_0002 running in uber mode : false
2020-03-06 00:50:14,841 INFO mapreduce.Job:  map 100% reduce 100%
2020-03-06 00:50:14,842 INFO mapreduce.Job: Job job_local546282602_0002 completed successfully
2020-03-06 00:50:14,846 INFO mapreduce.Job: Counters: 36
	File System Counters
		FILE: Number of bytes read=1283488
		FILE: Number of bytes written=3350775
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=55628
		HDFS: Number of bytes written=315
		HDFS: Number of read operations=69
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=14
		HDFS: Number of bytes read erasure-coded=0
	Map-Reduce Framework
		Map input records=2
		Map output records=2
		Map output bytes=41
		Map output materialized bytes=51
		Input split bytes=130
		Combine input records=0
		Combine output records=0
		Reduce input groups=1
		Reduce shuffle bytes=51
		Reduce input records=2
		Reduce output records=2
		Spilled Records=4
		Shuffled Maps =1
		Failed Shuffles=0
		Merged Map outputs=1
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=2219835392
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters
		Bytes Read=143
	File Output Format Counters
		Bytes Written=29
```

Hasilnya bisa dilihat:

```bash
$ hdfs dfs  -ls
Found 2 items
drwxr-xr-x   - zaky supergroup          0 2020-03-06 00:45 input
drwxr-xr-x   - zaky supergroup          0 2020-03-06 00:50 output
$ hdfs dfs -ls output
Found 2 items
-rw-r--r--   1 zaky supergroup          0 2020-03-06 00:50 output/_SUCCESS
-rw-r--r--   1 zaky supergroup         29 2020-03-06 00:50 output/part-r-00000
$ hdfs dfs -cat output/part-r-00000
2020-03-06 00:53:34,533 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
1	dfsadmin
1	dfs.replication
$
```

#### Jika Menggunakan YARN

Hapus direktori output di HDFS:

```bash
$ hdfs dfs -rm output/*
Deleted output/_SUCCESS
Deleted output/part-r-00000
$ hdfs dfs -rmdir output
$ hhdfs dfs -ls
Found 1 items
drwxr-xr-x   - zaky supergroup          0 2020-03-06 00:45 input
```

Setelah itu ubah 2 file konfugurasi:

`$HADOOP_HOME/etc/hadoop/mapred-site.xml`
```bash
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
    <property>
        <name>mapreduce.application.classpath</name>
        <value>$HADOOP_MAPRED_HOME/share/hadoop/mapreduce/*:$HADOOP_MAPRED_HOME/share/hadoop/mapreduce/lib/*</value>
    </property>
</configuration>
```

`$HADOOP_HOME/etc/hadoop/yarn-site.xml`
```bash
<configuration>
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <property>
        <name>yarn.nodemanager.env-whitelist</name>
        <value>JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PREPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME</value>
    </property>
</configuration>
```

Setelah itu, aktifkan YARN:

```bash
$ start-yarn.sh
Starting resourcemanager
Starting nodemanagers
$
```

Setelah pengaktifan, YARN bisa dipantau melalui Web di http://localhost:8088/

Menjalan MapReduce pada YARN:

```bash
$ hadoop jar ~/software/bigdata/hadoop-3.2.1/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.1.jar grep input output 'dfs[a-z.]+'
2020-03-06 01:10:00,890 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032
2020-03-06 01:10:01,311 INFO mapreduce.JobResourceUploader: Disabling Erasure Coding for path: /tmp/hadoop-yarn/staging/zaky/.staging/job_1583431675456_0001
2020-03-06 01:10:01,407 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 01:10:01,521 INFO input.FileInputFormat: Total input files to process : 9
2020-03-06 01:10:01,601 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 01:10:01,666 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 01:10:01,677 INFO mapreduce.JobSubmitter: number of splits:9
2020-03-06 01:10:01,822 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 01:10:01,832 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1583431675456_0001
2020-03-06 01:10:01,833 INFO mapreduce.JobSubmitter: Executing with tokens: []
2020-03-06 01:10:01,964 INFO conf.Configuration: resource-types.xml not found
2020-03-06 01:10:01,965 INFO resource.ResourceUtils: Unable to find 'resource-types.xml'.
2020-03-06 01:10:02,391 INFO impl.YarnClientImpl: Submitted application application_1583431675456_0001
2020-03-06 01:10:02,461 INFO mapreduce.Job: The url to track the job: http://dellvuan.bpdp.name:8088/proxy/application_1583431675456_0001/
2020-03-06 01:10:02,462 INFO mapreduce.Job: Running job: job_1583431675456_0001
2020-03-06 01:10:08,541 INFO mapreduce.Job: Job job_1583431675456_0001 running in uber mode : false
2020-03-06 01:10:08,543 INFO mapreduce.Job:  map 0% reduce 0%
2020-03-06 01:10:15,662 INFO mapreduce.Job:  map 33% reduce 0%
2020-03-06 01:10:16,700 INFO mapreduce.Job:  map 67% reduce 0%
2020-03-06 01:10:20,723 INFO mapreduce.Job:  map 100% reduce 0%
2020-03-06 01:10:21,733 INFO mapreduce.Job:  map 100% reduce 100%
2020-03-06 01:10:22,752 INFO mapreduce.Job: Job job_1583431675456_0001 completed successfully
2020-03-06 01:10:22,913 INFO mapreduce.Job: Counters: 54
	File System Counters
		FILE: Number of bytes read=51
		FILE: Number of bytes written=2261535
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=28730
		HDFS: Number of bytes written=143
		HDFS: Number of read operations=32
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=2
		HDFS: Number of bytes read erasure-coded=0
	Job Counters
		Launched map tasks=9
		Launched reduce tasks=1
		Data-local map tasks=9
		Total time spent by all maps in occupied slots (ms)=41135
		Total time spent by all reduces in occupied slots (ms)=2711
		Total time spent by all map tasks (ms)=41135
		Total time spent by all reduce tasks (ms)=2711
		Total vcore-milliseconds taken by all map tasks=41135
		Total vcore-milliseconds taken by all reduce tasks=2711
		Total megabyte-milliseconds taken by all map tasks=42122240
		Total megabyte-milliseconds taken by all reduce tasks=2776064
	Map-Reduce Framework
		Map input records=745
		Map output records=2
		Map output bytes=41
		Map output materialized bytes=99
		Input split bytes=1059
		Combine input records=2
		Combine output records=2
		Reduce input groups=2
		Reduce shuffle bytes=99
		Reduce input records=2
		Reduce output records=2
		Spilled Records=4
		Shuffled Maps =9
		Failed Shuffles=0
		Merged Map outputs=9
		GC time elapsed (ms)=1555
		CPU time spent (ms)=7300
		Physical memory (bytes) snapshot=3009871872
		Virtual memory (bytes) snapshot=25759334400
		Total committed heap usage (bytes)=2691170304
		Peak Map Physical memory (bytes)=329400320
		Peak Map Virtual memory (bytes)=2580549632
		Peak Reduce Physical memory (bytes)=237305856
		Peak Reduce Virtual memory (bytes)=2579529728
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters
		Bytes Read=27671
	File Output Format Counters
		Bytes Written=143
2020-03-06 01:10:22,937 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032
2020-03-06 01:10:23,000 INFO mapreduce.JobResourceUploader: Disabling Erasure Coding for path: /tmp/hadoop-yarn/staging/zaky/.staging/job_1583431675456_0002
2020-03-06 01:10:23,046 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 01:10:23,100 INFO input.FileInputFormat: Total input files to process : 1
2020-03-06 01:10:23,157 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 01:10:23,223 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 01:10:23,245 INFO mapreduce.JobSubmitter: number of splits:1
2020-03-06 01:10:23,311 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 01:10:23,322 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1583431675456_0002
2020-03-06 01:10:23,322 INFO mapreduce.JobSubmitter: Executing with tokens: []
2020-03-06 01:10:23,341 INFO impl.YarnClientImpl: Submitted application application_1583431675456_0002
2020-03-06 01:10:23,346 INFO mapreduce.Job: The url to track the job: http://dellvuan.bpdp.name:8088/proxy/application_1583431675456_0002/
2020-03-06 01:10:23,346 INFO mapreduce.Job: Running job: job_1583431675456_0002
2020-03-06 01:10:33,451 INFO mapreduce.Job: Job job_1583431675456_0002 running in uber mode : false
2020-03-06 01:10:33,452 INFO mapreduce.Job:  map 0% reduce 0%
2020-03-06 01:10:37,486 INFO mapreduce.Job:  map 100% reduce 0%
2020-03-06 01:10:41,509 INFO mapreduce.Job:  map 100% reduce 100%
2020-03-06 01:10:41,523 INFO mapreduce.Job: Job job_1583431675456_0002 completed successfully
2020-03-06 01:10:41,556 INFO mapreduce.Job: Counters: 54
	File System Counters
		FILE: Number of bytes read=51
		FILE: Number of bytes written=451189
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=272
		HDFS: Number of bytes written=29
		HDFS: Number of read operations=9
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=2
		HDFS: Number of bytes read erasure-coded=0
	Job Counters
		Launched map tasks=1
		Launched reduce tasks=1
		Data-local map tasks=1
		Total time spent by all maps in occupied slots (ms)=1585
		Total time spent by all reduces in occupied slots (ms)=1756
		Total time spent by all map tasks (ms)=1585
		Total time spent by all reduce tasks (ms)=1756
		Total vcore-milliseconds taken by all map tasks=1585
		Total vcore-milliseconds taken by all reduce tasks=1756
		Total megabyte-milliseconds taken by all map tasks=1623040
		Total megabyte-milliseconds taken by all reduce tasks=1798144
	Map-Reduce Framework
		Map input records=2
		Map output records=2
		Map output bytes=41
		Map output materialized bytes=51
		Input split bytes=129
		Combine input records=0
		Combine output records=0
		Reduce input groups=1
		Reduce shuffle bytes=51
		Reduce input records=2
		Reduce output records=2
		Spilled Records=4
		Shuffled Maps =1
		Failed Shuffles=0
		Merged Map outputs=1
		GC time elapsed (ms)=75
		CPU time spent (ms)=1160
		Physical memory (bytes) snapshot=529399808
		Virtual memory (bytes) snapshot=5157638144
		Total committed heap usage (bytes)=452984832
		Peak Map Physical memory (bytes)=291647488
		Peak Map Virtual memory (bytes)=2575876096
		Peak Reduce Physical memory (bytes)=237752320
		Peak Reduce Virtual memory (bytes)=2581762048
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters
		Bytes Read=143
	File Output Format Counters
		Bytes Written=29
```

Hasilnya adalah sebagai berikut:

```bash
$ hdfs dfs -ls output/
Found 2 items
-rw-r--r--   1 zaky supergroup          0 2020-03-06 01:10 output/_SUCCESS
-rw-r--r--   1 zaky supergroup         29 2020-03-06 01:10 output/part-r-00000
$ hdfs dfs -cat output/part-r-00000
2020-03-06 01:12:18,825 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
1	dfsadmin
1	dfs.replication
$
```

