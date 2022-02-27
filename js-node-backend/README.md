# Materi Pembelajaran "JavaScript - Node.js Programming for Backend"

Materi ini memerlukan prasyarat penguasaan [Pemrograman JavaScript](../js-programming/). Setelah memahami JavaScript, materi ini akan difokuskan pada sisi *backend*:

1. Membangun aplikasi Web menggunakan framework [Express](https://expressjs.com/).
2. Akses Basis Data
3. *Backend* Programming
4. *Testing* dan *Tooling*

## Web Framework: Express

Materi pada minggu ini mulai menekankan pada framework Express. Node.js menyediakan berbagai [fungsi pustaka standar default](https://nodejs.org/docs/latest/api/) antara lain HTTP server, tetapi biasanya masih memerlukan pustaka framework yang lebih terintegrasi. Minggu ini merupakan masa pembelajaran untuk mendalami Express.

[Minggu 02](isi/01.md)

*Hari 1: Mengenal Framework Express*

1. Pengenalan Express
2. Instalasi dan konfigurasi Express
3. Struktur dasar aplikasi dengan Express
4. Express *generator*
5. Memahami *routing* di Express
6. Mengelola *static files*.

*Hari 2: Express dan MVC*

1. Memahami pola MVC
2. Komponen MVC di Express

*Hari 3: Routing dan Handler pada Express*

1. Pengertian *routing*
2. *Routing* dan *handler*
3. *Routes* dan HTTP *methods*.
4. *Routes* dan *regular expression*
5. *Route* dan parameter dari *requests*
6. *Response methods*

*Hari 4: Penanganan Error dan Template Engine*

1. Error dan penanganannya
2. Memahami cara kerja *template engine*
3. Konfigurasi *template engine*
4. Lebih lanjut dengan Pug
5. Membuat *template engine* sendiri

*Hari 5: Middleware*

1. Pengertian *Middleware*
2. Mengepa perlu *middleware*
3. Tipe-tipe *middleware*

## Akses Basis Data

[Minggu 03](isi/02.md)

*Hari 1: SQL dan MariaDB*

1. Mengenal DBMS, SQL, dan MariaDB
2. Instalasi MariaDB
3. Konfigurasi MariaDB
4. Perancangan Database
5. SQL: DDL dan DML
6. Mengakses MariaDB dari Node.js

*Hari 2: Sequelize ORM*

1. ORM dan Sequelize
2. Instalasi Sequelize
3. Koneksi ke MariaDB
4. Tipe data
5. Model
6. Query
7. Transactions

*Hari 3: NoSQL dan MongoDB*

1. Mengenal NoSQL dan MongoDB
2. Instalasi MongoDB
3. Konfigurasi MongoDB
4. Mengenal shell mongo
5. Model data
6. Operasi CRUD 
7. Mengakses MongoDB dari Node.js

*Hari 4: Mongoose ODM**

1. ODM dan Mongoose
2. Instalasi Mongoose
3. Koneksi ke MongoDB
4. Schema
5. Model
6. *Docouments*
7. Query

*Hari 5: Integrasi SQL/ORM - NoSQL/ODM ke Express*

1. Integrasi MariaDB ke Express
2. Integrasi MongoDB ke Express 

## Lebih Lanjut dengan Node.js Sebagai Back End

[Minggu 04](isi/03.md)

*Hari 1: RESTful Endpoint* 

1. API dan Web API
2. Arsitektur REST
3. Implementasi RESTful API menggunakan Express

*Hari 2: GraphQL*

1. Gambaran umum GraphQL
2. GraphQL dan Web API lainnya
3. Implementasi GraphQL di Node.js

*Hari 3: WebSocket*

1. Gambaran umum WebSocket
2. Cara kerja WebSocket
3. Implementasi WebSocket di Node.js

*Hari 4: Microservices* 

1. Arsitektur software dan *microservices*
2. Implementasi di Node.js:
    * Tanpa framework
    * Menggunakan framework: Moleculer

## Testing dan Tooling

*Hari 5: Testing dan Tooling di Node.js*

1. Gambaran umum tentang software testing
2. Kategori level software testing:
    * Unit testing
    * Integration Testing
3. Unit testing pada Node.js
4. Integration testing pada Node.js
5. Berbagai peranti pengembangan dan tooling di Node.js:
    * Babeljs
    * PM2
