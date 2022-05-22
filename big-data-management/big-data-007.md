# Mengenal MongoDB

## Apa Itu MongoDB?

MongoDB adalah salah satu DBMS NoSQL yang termasuk dalam kategori *document-oriented* atau *document store*. NoSQL sering juga disebut dengan "Non SQL" atau "Non Relational", meskipun sering juga disebut "Not Only SQL" karena ada juga beberapa diantara DBMS tersebut yang mendukung SQL (contohnya: OrientDB) atau seringkali juga digunakan berbarengan dengan DBMS SQL. MongoDB dibuat oleh [MongoDB Inc.](https://www.mongodb.com/). 

## Dokumen Dalam *Document Store*

MongoDB digunakan untuk menyimpan data *semi-structured*. Dengan kemampuan ini, *document store* memungkinkan digunakan untuk menyimpan data secara lebih fleksibel, tidak *strict* seperti SQL yang memang sudah didefinisikan lebih dahulu setiap data yang bisa dikelola dalam bentuk tabel yang saling berhubungan dengan tabel-tabel lainnya.

## ACID vs BASE

DBMS SQL secara umum mendukung ACID, sementara NoSQL - *document store* "mengorbankan" C(*onsistency). 

ACID:

1.  Atomicity: setiap transaksi (yang terdiri atas satu atau lebih *statement*) diperlakukan sebagai satu unit dengan kemungkinan *rollback* (jika ada salah satu yang gagal) atau *commit* (jika semua berhasil).
2.  Consistency: setiap data yang ditulis ke basis data harus valid - sesuai dengan *rules* (contraints, cascades, triggers).
3.  Isolation: traksaksi dieksekusi secara konkuren.
4.  Durability: jika transaksi di-*commit*, maka data yang tertulis akan dituliskan ke *database* dan tetap dalam kondisi seperti itu.

BASE:

1.  Basically Available: baca tulis dasar tersedia sebanyak mungkin tanpa jaminan konsistensi.
2.  Soft State: pemakai *database* akan mengetahui *state* dari *database* dalam probablitas yang tidak mencapai total.
3.  Eventually Consistent: pada akhirnya data akan konsisten setelah beberapa saat (dengan asumsi sistem dan software berjalan dengan baik).

## Pemakai MongoDB

MongoDB digunakan oleh banyak organisasi, diantaranya adalah:

1.  Facebook
2.  Invision
3.  Ebay
4.  Adobe
5.  Google
6.  SquareSpace
7.  Coinbase
8.  Sega
9.  Intuit
10. EHarmony
11. Verizon
12. Goernmnent of UK
13. SAP


