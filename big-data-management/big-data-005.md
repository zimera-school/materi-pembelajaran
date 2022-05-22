# Pengenalan MapReduce

Bagian ini akan menjelaskan pengenalan dari model pemrograman di Hadoop untuk mengelola *data sets*
yang besar secara parallel. Pengenalan akan dilakukan menggunakan contoh kasus *word count* untuk
menghitung jumlah kata dalam beberapa file.

## Memahami MapReduce

MapReduce sebenarnya merupakan model pemrograman yang dikhususkan untuk mengelola dan memproses
*data sets* yang sangat besar secara parallel pada suatu *cluster* dari *commodity hardware*.
Implementasi dari MapReduce merupakan framework yang digunakan untuk mengelola model pemrograman
tersebut secara *reliable* dan *fault-tolerant*. Apache Hadoop merupakan salah satu framework yang
menyediakan implementasi model pemrograman tersebut.

Silahkan membaca [paper MapReduce](https://static.googleusercontent.com/media/research.google.com/es/us/archive/mapreduce-osdi04.pdf).

## Model Pemrograman MapReduce

*Catatan*: gambar dan penjelasan diambil dari [edureka!](https://www.edureka.co/blog/mapreduce-tutorial/).
 
Dalam kasus *word count*, kita mempunyai satu atau lebih file yang terdiri atas kata-kata:

```
Dear, Bear, River, Car, Car, River, Deer, Car and Bear
```

Proses untuk MapReduce tersebut bisa digambarkan sebagai berikut:

![Proses MapReduce](images/map-reduce.png)

## Membuat MapReduce untuk WordCount

### Persiapan

Untuk persiapan, kita akan membuat direktori serta file input yang diperlukan. Ada 2 input file yang
diletakkan di `hdfs://localhost/user/zaky/wordcount/input`. File-file tersebut dibuat dan diletakkan
di `/tmp` terlebih dahulu:

```bash
$ cat /tmp/file01.txt
Hello World Bye World
$ cat /tmp/file02.txt
Hello Hadoop Goodbye Hadoop
```

```bash
$ hdfs dfs -mkdir wordcount
$ hdfs dfs -mkdir wordcount/input
$ hdfs dfs -ls wordcount
Found 1 items
drwxr-xr-x   - zaky supergroup          0 2020-03-06 06:56 wordcount/input
$ hdfs dfs -copyFromLocal /tmp/file0* wordcount/input
2020-03-06 07:16:06,751 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 07:16:06,981 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
$ hdfs dfs -ls wordcount/input
Found 2 items
-rw-r--r--   1 zaky supergroup         22 2020-03-06 07:16 wordcount/input/file01.txt
-rw-r--r--   1 zaky supergroup         28 2020-03-06 07:16 wordcount/input/file02.txt
$
```

### Kompilasi

Dengan menggunakan *Gradle*, kompilasi kode sumber Java yang terdapat di direktori `src/wordcount` sehingga terbentuk file `word-count-0.1.0.jar`.

```bash
$ gradle build
```

Hasilnya adalah sebagai berikut:

```bash
ls -la build/libs/
total 12
drwxr-xr-x 2 zaky zaky 4096 Mar  6 07:17 .
drwxr-xr-x 5 zaky zaky 4096 Mar  6 07:16 ..
-rw-r--r-- 1 zaky zaky 3326 Mar  6 07:17 word-count-0.1.0.jar
$
```

### Jalankan WordCount

WordCount berjalan dengan menggunakan YARN untuk mengelola *resources*, jadi YARN harus dijalankan
lebih dahulu.

```bash
$ start-yarn.sh
Starting resourcemanager
Starting nodemanagers
$ 
```

Setelah itu, jalankan WordCount:

```bash
$ hadoop jar word-count-0.1.0.jar WordCount /user/zaky/wordcount/input /user/zaky/wordcount/output
2020-03-06 07:36:19,251 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032
2020-03-06 07:36:19,609 WARN mapreduce.JobResourceUploader: Hadoop command-line option parsing not performed. Implement the Tool interface and execute your application with ToolRunner to remedy this.
2020-03-06 07:36:19,647 INFO mapreduce.JobResourceUploader: Disabling Erasure Coding for path: /tmp/hadoop-yarn/staging/zaky/.staging/job_1583453976411_0004
2020-03-06 07:36:19,744 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 07:36:19,863 INFO input.FileInputFormat: Total input files to process : 2
2020-03-06 07:36:19,924 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 07:36:19,980 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 07:36:20,002 INFO mapreduce.JobSubmitter: number of splits:2
2020-03-06 07:36:20,157 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-03-06 07:36:20,168 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1583453976411_0004
2020-03-06 07:36:20,168 INFO mapreduce.JobSubmitter: Executing with tokens: []
2020-03-06 07:36:20,293 INFO conf.Configuration: resource-types.xml not found
2020-03-06 07:36:20,294 INFO resource.ResourceUtils: Unable to find 'resource-types.xml'.
2020-03-06 07:36:20,340 INFO impl.YarnClientImpl: Submitted application application_1583453976411_0004
2020-03-06 07:36:20,378 INFO mapreduce.Job: The url to track the job: http://dellvuan.bpdp.name:8088/proxy/application_1583453976411_0004/
2020-03-06 07:36:20,379 INFO mapreduce.Job: Running job: job_1583453976411_0004
2020-03-06 07:36:25,489 INFO mapreduce.Job: Job job_1583453976411_0004 running in uber mode : false
2020-03-06 07:36:25,491 INFO mapreduce.Job:  map 0% reduce 0%
2020-03-06 07:36:29,580 INFO mapreduce.Job:  map 50% reduce 0%
2020-03-06 07:36:30,592 INFO mapreduce.Job:  map 100% reduce 0%
2020-03-06 07:36:34,626 INFO mapreduce.Job:  map 100% reduce 100%
2020-03-06 07:36:35,652 INFO mapreduce.Job: Job job_1583453976411_0004 completed successfully
2020-03-06 07:36:35,720 INFO mapreduce.Job: Counters: 54
	File System Counters
		FILE: Number of bytes read=79
		FILE: Number of bytes written=676624
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=296
		HDFS: Number of bytes written=41
		HDFS: Number of read operations=11
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=2
		HDFS: Number of bytes read erasure-coded=0
	Job Counters 
		Launched map tasks=2
		Launched reduce tasks=1
		Data-local map tasks=2
		Total time spent by all maps in occupied slots (ms)=5189
		Total time spent by all reduces in occupied slots (ms)=1800
		Total time spent by all map tasks (ms)=5189
		Total time spent by all reduce tasks (ms)=1800
		Total vcore-milliseconds taken by all map tasks=5189
		Total vcore-milliseconds taken by all reduce tasks=1800
		Total megabyte-milliseconds taken by all map tasks=5313536
		Total megabyte-milliseconds taken by all reduce tasks=1843200
	Map-Reduce Framework
		Map input records=2
		Map output records=8
		Map output bytes=82
		Map output materialized bytes=85
		Input split bytes=246
		Combine input records=8
		Combine output records=6
		Reduce input groups=5
		Reduce shuffle bytes=85
		Reduce input records=6
		Reduce output records=5
		Spilled Records=12
		Shuffled Maps =2
		Failed Shuffles=0
		Merged Map outputs=2
		GC time elapsed (ms)=141
		CPU time spent (ms)=1700
		Physical memory (bytes) snapshot=857673728
		Virtual memory (bytes) snapshot=7731277824
		Total committed heap usage (bytes)=734527488
		Peak Map Physical memory (bytes)=332787712
		Peak Map Virtual memory (bytes)=2574209024
		Peak Reduce Physical memory (bytes)=196820992
		Peak Reduce Virtual memory (bytes)=2583433216
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters 
		Bytes Read=50
	File Output Format Counters 
		Bytes Written=41
$ hdfs dfs -ls wordcount/output
Found 2 items
-rw-r--r--   1 zaky supergroup          0 2020-03-06 07:36 wordcount/output/_SUCCESS
-rw-r--r--   1 zaky supergroup         41 2020-03-06 07:36 wordcount/output/part-r-00000
$ hdfs -cat wordcount/output/part-r-00000
2020-03-06 07:36:54,934 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
Bye	1
Goodbye	1
Hadoop	2
Hello	2
World	2
$
```

