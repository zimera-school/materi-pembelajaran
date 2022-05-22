# MapReduce - Join dan Counter

## Join

*Join* pada Hadoop digunakan untuk menggabungkan data sets. Ada 2 teknik penggabungan data:

1.  *Map Side*: data sets akan digabungkan sebelum opeasi *map*. Teknik ini biasanya digunakan untuk data yang tidak terlalu banyak. Syarat dari data ini adalah data sudah dipartisi menjadi beberapa bagian yang sama serta telah diurutkan sesuai kunci tertentu.
2.  *Reduce Side*: data sets akan digabungkan dalam operasi *reduce*, Berbeda dengan teknik *map side*, teknik ini biasana digunakan untuk data yang besar dengan bentuk terstruktur dan / atau sudah dipartisi.

Untuk melihat implementasinya, silahkan melihat ke *source code* yang ada pada [src/join](src/join)

## Counter

*Counter* merupakan suatu mekanisme di Hadoop untuk mengirimkan informasi statistik the pekerjaan yang dikerjakan oleh MapReduce. Ada 2 jenis *counter* untuk *MapReduce*. Pada dasarnya, ada 2 katogori besar:

1.  Builtin Hadoop, terdiri atas:
    * *MapReduce Task Counters* - mengumpulkan informasi terkait dengan *task* tertentu
    * *FileSystem Counters* - mengumpulkan informasi tentang jumlah *bytes* yang dibaca atau ditulis oleh task.
    * *FileInputFormat Counters* - mengumpulkan informasi tentang jumlaah *bytes* yang dibaca melalui FileInputFormat.
    * *FileOutputFormat Counters - mengumpulkan informasi tentang jumlah *bytes* yang ditulis melalui FileOutputFormat
    * *Job Counters - mengumpulkan informasi tentang JobTracker.
2.  *User Defined Counter*.


