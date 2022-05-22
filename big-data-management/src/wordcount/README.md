# Tentang Source Code

Direktori ini berisi source code *Word Count*, diambil dari [Tutorial
MapReduce](https://hadoop.apache.org/docs/current/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html)
oleh tim Hadoop. Penambahan yang dilakukan adalah pada `build.gradle`. 

Untuk mengkompilasi:

```bash
$ gradle build
```

Untuk menjalankan, gunakan file jar dari:

```bash
ls -la build/libs/
total 12
drwxr-xr-x 2 bpdp bpdp 4096 Mar  6 07:14 ./
drwxr-xr-x 8 bpdp bpdp 4096 Mar  6 07:12 ../
-rw-r--r-- 1 bpdp bpdp 3326 Mar  6 07:13 word-count-0.1.0.jar
```
