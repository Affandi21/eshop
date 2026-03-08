# AdV Shop - EShop Project

## Reflection 1

### 1. Clean Code Principles Applied
Berdasarkan materi pengembangan aplikasi menggunakan Spring Boot, berikut adalah prinsip **Clean Code** yang telah saya terapkan:

* **Meaningful Names**: Saya menggunakan penamaan yang deskriptif dan mencerminkan tujuan variabel atau fungsi. Contohnya, penggunaan nama kelas `ProductRepository` dan `ProductServiceImpl` memudahkan pemahaman tanpa perlu komentar tambahan.
* **Single Responsibility Principle (SRP)**: Kode telah dipisahkan ke dalam paket-paket yang memiliki tanggung jawab spesifik:
    * **Controller**: Menangani *request* dan *response* HTTP.
    * **Service**: Mengimplementasikan logika bisnis.
    * **Repository**: Mengelola penyimpanan data (akses data).
    * **Model**: Representasi data/domain.
* **Functions should do one thing**: Setiap metode, seperti `create`, `findById`, atau `delete`, dibuat singkat dan fokus pada satu tugas spesifik.
* **Avoiding Boilerplate with Lombok**: Penggunaan anotasi `@Getter` dan `@Setter` dari Lombok membantu menjaga kode tetap bersih dengan menghilangkan kebutuhan untuk menulis metode getter dan setter secara manual.



### 2. Secure Coding Practices Applied
Keamanan kode adalah aspek penting yang saya perhatikan dalam modul ini:

* **Secure ID Generation**: Alih-alih menggunakan ID numerik yang berurutan (yang rentan terhadap serangan *ID Enumeration*), saya menggunakan `UUID.randomUUID().toString()` untuk menghasilkan ID yang unik dan tidak terduga.
* **Server-Side Redirection**: Setelah melakukan operasi POST (seperti *create* atau *edit*), saya menggunakan `redirect:` untuk mencegah pengiriman data ganda jika pengguna melakukan *refresh* pada browser.
* **Encapsulation**: Atribut pada kelas `Product` dibuat `private` untuk memastikan data hanya bisa diakses melalui metode yang diizinkan (Lombok generated methods).

### 3. Mistakes and Potential Improvements
Setelah meninjau kembali kode saya, ada beberapa hal yang dapat ditingkatkan di masa depan:

* **Input Validation**: Saat ini belum ada validasi pada data yang masuk. Jika pengguna memasukkan jumlah produk negatif atau nama kosong, sistem tetap menerimanya. Perbaikannya adalah menggunakan anotasi `@Valid` dan `@Min(0)` di model.
* **Error Handling**: Aplikasi saat ini akan menampilkan *Whitelabel Error Page* jika terjadi kesalahan. Seharusnya saya menambahkan *Global Exception Handler* (menggunakan `@ControllerAdvice`) untuk menangani error secara lebih elegan dan informatif bagi pengguna.
* **Unit Testing**: Kode saat ini masih minim pengujian. Menambahkan unit test untuk setiap layer (terutama Service dan Repository) sangat penting untuk memastikan fungsionalitas tetap terjaga saat ada perubahan di masa depan.


# Reflection 2

## 1. Unit Testing & Code Coverage

Setelah menulis unit test, saya merasa lebih percaya diri terhadap kode yang saya buat karena setiap perubahan kecil dapat langsung diverifikasi secara otomatis. Unit test membantu memastikan bahwa functionality yang sudah berjalan dengan baik tidak rusak ketika dilakukan perubahan atau penambahan fitur.

Tidak ada jumlah pasti mengenai berapa banyak unit test yang harus dibuat dalam satu class. Namun, unit test sebaiknya mencakup seluruh logic paths, termasuk:
- Positive scenarios (valid input),
- Negative scenarios (invalid input),
- Edge cases.

Untuk mengevaluasi kecukupan unit test, kita dapat menggunakan **Code Coverage**, yaitu metrik yang mengukur persentase code lines atau logic branches yang dieksekusi oleh unit test. Semakin tinggi nilai code coverage, semakin banyak bagian code yang telah diuji.

Meskipun mencapai **100% code coverage** merupakan pencapaian yang baik, hal tersebut **tidak menjamin kode bebas dari bug**. Code coverage hanya memastikan bahwa code telah dieksekusi oleh test, bukan bahwa seluruh business logic telah diverifikasi dengan benar. Bug tetap dapat muncul jika:
- Test logic itu sendiri keliru,
- Ada real-world scenarios yang belum terpikirkan oleh developer,
- Assertion pada test kurang spesifik terhadap expected behavior.

Oleh karena itu, code coverage sebaiknya digunakan sebagai supporting metric, bukan satu-satunya indikator kualitas test.


## 2. Functional Testing Clean Code Issues

Jika saya membuat functional test suite baru dengan menyalin prosedur setup dan instance variables yang sama dari test class sebelumnya, maka kualitas code akan menurun karena melanggar prinsip **DRY (Don't Repeat Yourself)**.

Masalah utama yang muncul adalah **Code Duplication**, di mana code yang identik (seperti konfigurasi `baseUrl` dan anotasi `@LocalServerPort`) tersebar di banyak test classes. Jika terjadi perubahan konfigurasi di masa depan, saya harus mengubahnya di setiap file secara manual, yang akan:
- Menurunkan maintainability,
- Meningkatkan risiko human error,
- Menyebabkan rigidity pada struktur code.

Untuk meningkatkan kualitas code, solusi yang disarankan adalah melakukan **refactoring** dengan membuat sebuah **Base Functional Test Class**.  
Base class ini akan menampung:
- Common setup procedures,
- Shared instance variables.

Dengan pendekatan ini, test class baru (seperti `ProductListCountFunctionalTest`) cukup menggunakan **inheritance** dari base class tersebut. Hasilnya, code menjadi:
- Cleaner,
- More modular,
- Easier to maintain dan scalable di masa depan.

---

# Reflection 3

## 1. Peningkatan Pengujian dan Ketahanan Kode

Dalam tahap pengembangan lanjutan aplikasi ini, saya berfokus pada peningkatan kualitas kode melalui penambahan unit test yang lebih menyeluruh. Pengujian tidak hanya dilakukan untuk memastikan fitur berjalan sesuai kebutuhan, tetapi juga untuk menjaga ketahanan kode (*code resilience*) terhadap berbagai kondisi input. Salah satu implementasi yang saya lakukan adalah memastikan setiap produk memiliki ID unik menggunakan UUID yang dihasilkan secara otomatis saat data disimpan ke repositori. Pendekatan ini bertujuan untuk mencegah terjadinya duplikasi data dan menjaga integritas penyimpanan informasi produk.

Selain itu, saya menambahkan pengujian terhadap kondisi batas (*edge cases*), seperti kuantitas produk bernilai nol dan penggunaan nama produk dengan karakter khusus atau panjang tertentu. Pengujian ini membantu memastikan bahwa aplikasi tetap stabil dan tidak mengalami kegagalan ketika menerima input yang tidak umum. Dengan menambahkan variasi skenario pengujian, cakupan pengujian (*code coverage*) menjadi lebih luas sehingga meningkatkan tingkat kepercayaan terhadap konsistensi logika bisnis. Melalui proses ini, saya memahami bahwa unit test tidak hanya berfungsi sebagai alat verifikasi, tetapi juga sebagai bentuk dokumentasi perilaku sistem yang memudahkan proses pemeliharaan kode di masa depan.

## 2. Peningkatan Antarmuka Pengguna dan Pengalaman Pengguna

Selain melakukan peningkatan pada kualitas kode, saya juga mengembangkan antarmuka pengguna untuk meningkatkan pengalaman penggunaan aplikasi. Saya menerapkan desain bertema **Premium White** dengan memanfaatkan framework **Bootstrap 5** serta ikon dari **Font Awesome** untuk menciptakan tampilan yang lebih modern dan terstruktur. Penggunaan kartu (*cards*) pada formulir membantu memisahkan elemen visual sehingga informasi lebih mudah dipahami oleh pengguna.

Desain antarmuka ini bertujuan untuk menciptakan hierarki visual yang jelas, sehingga pengguna dapat membedakan fungsi **Create**, **Edit**, dan **Delete** secara lebih intuitif. Selain aspek tampilan, saya juga memastikan bahwa antarmuka bersifat responsif sehingga aplikasi tetap nyaman digunakan pada berbagai ukuran perangkat. Melalui pengembangan ini, saya menyadari bahwa kualitas perangkat lunak tidak hanya ditentukan oleh stabilitas logika program, tetapi juga oleh kemudahan interaksi pengguna terhadap sistem. Secara keseluruhan, proses ini memberikan pemahaman bahwa pengujian yang baik dan desain antarmuka yang terstruktur merupakan dua aspek yang saling melengkapi dalam menghasilkan aplikasi yang andal dan mudah digunakan.


---


#  Reflection - Module 2 CI/CD

## 1. Code Quality Issues dan Strategi Perbaikannya

Selama mengerjakan modul ini, terdapat beberapa *code quality issues* yang terdeteksi melalui SonarCloud, dan telah diperbaiki untuk meningkatkan kualitas kode.

Salah satu masalah utama adalah penggunaan **field injection (`@Autowired`)** pada controller dan service. Pendekatan ini dianggap kurang baik karena membuat dependency kurang jelas dan menyulitkan pengujian. Untuk mengatasinya, dilakukan refactor menjadi **constructor injection**, sehingga dependency lebih eksplisit, immutable, dan lebih mudah untuk di-*mock* saat testing.

Selain itu, terdapat penggunaan **reflection pada unit test** untuk mengisi field private. Cara ini tidak direkomendasikan karena menurunkan maintainability dan membuat test lebih kompleks. Solusinya adalah dengan menyesuaikan class agar menggunakan constructor injection, sehingga dependency bisa langsung diberikan saat pembuatan object tanpa reflection.

Masalah lain yang ditemukan adalah adanya **duplikasi string**, seperti `"redirect:/product/list"` yang digunakan di beberapa tempat. Hal ini diperbaiki dengan menjaga konsistensi penulisan dan menghindari duplikasi yang tidak perlu.

Selain itu, terdapat beberapa *minor issues* seperti:

* Test yang tidak memiliki assertion
* Import yang tidak digunakan
* Penggunaan `throws Exception` yang tidak diperlukan

Semua masalah tersebut diperbaiki dengan membersihkan kode dan memastikan setiap test memiliki assertion yang jelas.

Melalui perbaikan ini, nilai **Maintainability di SonarCloud meningkat hingga mencapai grade A**, dan *Quality Gate* berhasil **PASSED**.

---

## 2. Analisis Implementasi CI/CD

Implementasi yang dilakukan sudah memenuhi konsep **Continuous Integration (CI)**. Setiap perubahan yang di-*push* ke repository akan secara otomatis menjalankan GitHub Actions untuk menjalankan unit test dan analisis kualitas kode menggunakan SonarCloud. Hal ini memastikan bahwa setiap perubahan langsung divalidasi dan kesalahan dapat terdeteksi lebih awal.

Selain itu, implementasi ini juga memenuhi konsep **Continuous Deployment (CD)**. Aplikasi secara otomatis di-*deploy* ke platform PaaS (Koyeb) setiap kali terdapat perubahan pada branch utama. Dengan demikian, proses deployment tidak perlu dilakukan secara manual dan aplikasi selalu berada dalam kondisi terbaru.

Pipeline yang dibuat juga sudah mencakup beberapa tahap penting, yaitu:

* **Testing** (unit & functional test)
* **Code quality analysis (SonarCloud)**
* **Deployment ke PaaS**

Integrasi ketiga tahap ini menunjukkan bahwa pipeline sudah berjalan secara otomatis dan berkelanjutan, sesuai dengan prinsip CI/CD.

---

## Kesimpulan

Secara keseluruhan, implementasi CI/CD pada proyek ini telah berhasil:

* Menjalankan proses integrasi secara otomatis (CI)
* Melakukan analisis kualitas kode menggunakan SonarCloud
* Melakukan deployment otomatis ke PaaS (CD)

Dengan pipeline ini, kualitas kode lebih terjaga, proses development menjadi lebih efisien, dan aplikasi dapat terus di-*deploy* secara konsisten.

---

# Reflection: SOLID Principles

## 1. Single Responsibility Principle (SRP)

Saya telah mengimplementasikan SRP setelah melakukan refactoring pada kode awal. SRP menyatakan bahwa sebuah class seharusnya hanya memiliki satu alasan untuk berubah, yang berarti class tersebut hanya menangani satu tanggung jawab utama.

Pada implementasi sebelumnya, kode saya belum mematuhi SRP karena `CarController` digabungkan di dalam file `ProductController.java`. Hal ini menyebabkan satu file memiliki dua tanggung jawab sekaligus, yaitu mengelola HTTP request untuk entitas product dan juga car. Kondisi ini cukup berbahaya karena setiap perubahan kecil pada salah satu entitas bisa berdampak ke bagian lain yang sebenarnya tidak berkaitan.

Untuk memperbaiki hal tersebut, saya memisahkan `CarController` ke dalam file tersendiri (`CarController.java`). Setelah dipisah:
- `ProductController` hanya menangani seluruh alur terkait product
- `CarController` hanya fokus menangani fitur yang berhubungan dengan car

Dengan perubahan ini, setiap controller sekarang benar-benar memiliki satu tanggung jawab yang jelas. Dampaknya, kode menjadi lebih mudah dibaca, lebih terstruktur, dan perubahan di satu bagian tidak lagi berisiko merusak bagian lain.

---

## 2. Open-Closed Principle (OCP)

Saya juga telah memodifikasi kode agar lebih sesuai dengan prinsip OCP, terutama pada bagian repository.

OCP menyatakan bahwa suatu entitas software harus terbuka untuk pengembangan, tetapi tertutup untuk modifikasi. Artinya, kita sebaiknya bisa menambahkan fitur baru tanpa perlu mengubah kode yang sudah ada.

Sebelumnya, implementasi method `update` pada `CarRepository` masih kurang optimal karena melakukan update atribut satu per satu menggunakan setter. Hal ini menjadi masalah karena jika ada penambahan atribut baru pada model `Car`, maka saya harus kembali membuka dan memodifikasi kode repository tersebut.

Untuk mengatasi hal ini, saya mengubah pendekatan update menjadi langsung mengganti objek lama dengan objek baru di dalam list. Dengan cara ini, repository tidak lagi perlu mengetahui detail atribut dari `Car`. Jika di masa depan ada penambahan field baru, repository tidak perlu diubah sama sekali.

Perubahan ini membuat kode menjadi lebih fleksibel dan sesuai dengan prinsip OCP karena behavior dapat diperluas tanpa harus mengubah implementasi yang sudah ada.

---

## 3. Liskov Substitution Principle (LSP)

Saya juga melakukan perbaikan untuk memenuhi prinsip LSP.

LSP menyatakan bahwa subclass harus bisa menggantikan superclass tanpa mengubah perilaku program. Dengan kata lain, hubungan inheritance harus benar-benar merepresentasikan relasi yang valid secara konsep.

Sebelumnya, `CarController` dibuat dengan cara mewarisi (`extends`) `ProductController`. Ini sebenarnya tidak tepat karena `CarController` bukanlah bentuk khusus dari `ProductController`. Akibatnya, terjadi efek samping pada routing, di mana endpoint milik product bisa ikut terpengaruh oleh mapping dari car.

Contohnya, method yang seharusnya meng-handle product malah ikut menggunakan base path `/car`, sehingga membuat routing menjadi tidak konsisten dan berpotensi menimbulkan bug.

Untuk memperbaiki hal ini, saya menghapus inheritance tersebut dan membuat `CarController` berdiri sendiri sebagai controller yang independen. Dengan begitu, tidak ada lagi behavior yang “terwarisi secara tidak relevan”, dan sistem menjadi lebih konsisten.

---

## 4. Interface Segregation Principle (ISP)

Untuk prinsip ISP, struktur kode sebenarnya sudah cukup baik sejak awal dan tidak memerlukan perubahan besar.

ISP menyatakan bahwa sebuah class tidak boleh dipaksa untuk mengimplementasikan method yang tidak dibutuhkan. Oleh karena itu, interface sebaiknya dibuat kecil dan spesifik sesuai kebutuhan.

Dalam proyek ini, sudah ada pemisahan interface seperti `ProductService` dan `CarService`. Hal ini jauh lebih baik dibandingkan membuat satu interface besar yang berisi semua method dari berbagai entitas.

Dengan pemisahan ini:
- `CarController` hanya berinteraksi dengan method yang relevan untuk car
- Tidak ada method yang “terpaksa diimplementasikan” tapi sebenarnya tidak digunakan

Hal ini membuat desain lebih bersih dan lebih mudah dikembangkan ke depannya.

---

## 5. Dependency Inversion Principle (DIP)

Saya juga telah menerapkan prinsip DIP pada kode.

DIP menyatakan bahwa modul tingkat tinggi tidak boleh bergantung langsung pada modul tingkat rendah, melainkan keduanya harus bergantung pada abstraksi.

Sebelumnya, `CarController` langsung bergantung pada `CarServiceImpl`, yang merupakan implementasi konkret. Ini menyebabkan coupling yang tinggi karena controller jadi tahu detail implementasi service.

Untuk memperbaiki hal ini, saya mengubah dependency menjadi menggunakan interface, yaitu `CarService`. Dengan perubahan ini, controller hanya bergantung pada kontrak (abstraction), bukan pada implementasi.

Dampaknya:
- Controller menjadi lebih fleksibel
- Implementasi service bisa diganti tanpa mengubah controller
- Sangat memudahkan proses testing karena bisa menggunakan mock object

---

## Advantages of Applying SOLID Principles

Menerapkan SOLID memberikan banyak keuntungan yang terasa langsung dalam pengembangan proyek ini.

Yang paling terasa adalah peningkatan maintainability. Dengan SRP, setiap bagian kode memiliki tanggung jawab yang jelas, sehingga ketika ada perubahan atau bug, saya bisa langsung fokus ke satu file tanpa perlu khawatir merusak bagian lain.

Selain itu, fleksibilitas sistem juga meningkat. Contohnya pada OCP, perubahan model tidak lagi memaksa saya untuk mengubah repository. Hal ini membuat sistem lebih siap untuk berkembang tanpa harus sering melakukan refactoring besar.

Dari sisi testing, penerapan DIP sangat membantu. Saya bisa melakukan unit testing dengan lebih mudah karena dependency bisa diganti dengan mock. Ini membuat test lebih cepat dan tidak bergantung pada implementasi nyata.

Secara keseluruhan, struktur kode menjadi jauh lebih rapi, terorganisir, dan mudah dipahami, terutama ketika proyek mulai berkembang menjadi lebih besar.

---

## Disadvantages of Not Applying SOLID Principles

Jika prinsip SOLID tidak diterapkan, maka akan muncul banyak masalah dalam jangka panjang.

Salah satu yang paling terasa adalah code menjadi fragile. Sebelum menerapkan LSP, inheritance yang tidak tepat menyebabkan routing menjadi kacau. Perubahan kecil bisa berdampak besar ke bagian lain yang tidak seharusnya terpengaruh.

Selain itu, tanpa DIP, coupling antar class menjadi sangat tinggi. Perubahan pada satu bagian bisa memaksa perubahan di banyak bagian lain. Hal ini membuat development menjadi lambat dan berisiko.

Tanpa SRP, file seperti controller bisa menjadi sangat besar dan sulit dikelola. Jika proyek terus berkembang, hal ini bisa berubah menjadi spaghetti code yang sulit dipahami dan sulit dikerjakan secara tim.

Secara keseluruhan, tidak menerapkan SOLID akan membuat sistem menjadi kaku, sulit dikembangkan, dan rawan error.


---


# Reflection Module 4

## 1. Reflection on the Use of Test Driven Development (TDD)

Dalam tutorial ini saya mengikuti alur **Test Driven Development (TDD)** yang terdiri dari tiga tahap utama, yaitu **RED, GREEN, dan REFACTOR**. Pendekatan ini mengharuskan saya untuk menulis unit test terlebih dahulu sebelum mengimplementasikan logika program.

Pada tahap **RED**, saya membuat unit test untuk beberapa komponen utama dalam sistem Order, yaitu **Order model**, **OrderRepository**, dan **OrderServiceImpl**. Contohnya, pada `OrderTest` saya membuat test untuk berbagai kondisi seperti pembuatan order dengan produk kosong, pembuatan order dengan status default, penggunaan status yang valid maupun tidak valid, serta perubahan status order menggunakan method `setStatus`. Pada tahap ini, sebagian besar test memang gagal karena implementasi kode belum dibuat. Hal ini membantu saya memahami dengan lebih jelas perilaku sistem yang diharapkan sebelum menulis implementasi.

Selanjutnya pada tahap **GREEN**, saya mulai mengimplementasikan kode agar seluruh test dapat berjalan dengan sukses. Misalnya, saya mengimplementasikan class `OrderRepository` dengan method seperti `save`, `findById`, dan `findAllByAuthor` untuk mengelola data order yang disimpan dalam sebuah list. Selain itu, saya juga mengimplementasikan class `OrderServiceImpl` yang berfungsi sebagai service layer untuk menangani logika bisnis seperti membuat order baru (`createOrder`), memperbarui status order (`updateStatus`), mencari order berdasarkan ID (`findById`), dan mencari order berdasarkan author (`findAllByAuthor`). Pada tahap ini, setiap kali saya menambahkan implementasi method, saya menjalankan kembali unit test untuk memastikan bahwa kode yang dibuat sudah memenuhi perilaku yang diharapkan oleh test.

Pada tahap **REFACTOR**, saya melakukan perbaikan struktur kode tanpa mengubah perilaku sistem. Salah satu refactoring yang dilakukan adalah mengganti penggunaan string status secara langsung dengan menggunakan enum `OrderStatus`. Sebelumnya, validasi status dilakukan menggunakan array string yang berisi daftar status yang valid. Setelah refactoring, validasi status dilakukan menggunakan method `OrderStatus.contains()`. Perubahan ini membuat kode menjadi lebih aman, lebih mudah dipelihara, dan mengurangi kemungkinan kesalahan akibat penggunaan string yang tidak konsisten.

Selain itu, pada bagian service testing saya menggunakan **Mockito** untuk melakukan mocking terhadap dependency `OrderRepository`. Dengan menggunakan anotasi seperti `@Mock` dan `@InjectMocks`, saya dapat menguji perilaku `OrderServiceImpl` secara terisolasi tanpa harus bergantung pada implementasi repository yang sebenarnya. Hal ini sangat membantu dalam memastikan bahwa unit test benar-benar hanya menguji logika pada service layer.

Secara keseluruhan, pendekatan TDD ini cukup membantu dalam proses pengembangan karena memberikan struktur kerja yang jelas. Dengan menulis test terlebih dahulu, saya dapat memahami kebutuhan sistem dengan lebih baik sebelum mengimplementasikan kode. Selain itu, setiap perubahan kode dapat langsung divalidasi menggunakan test yang sudah ada sehingga potensi bug dapat dideteksi lebih awal.

Namun, saya masih perlu meningkatkan kemampuan dalam menulis unit test yang lebih efektif, terutama dalam penggunaan framework seperti Mockito. Dengan memahami teknik mocking lebih dalam, saya dapat membuat test yang lebih terisolasi dan lebih mudah dipelihara di masa depan.

---

## 2. Reflection on the F.I.R.S.T Principles in Unit Testing

Dalam tutorial ini saya membuat beberapa unit test seperti `OrderTest`, `OrderRepositoryTest`, dan `OrderServiceTest` untuk menguji berbagai skenario pada sistem Order. Jika dikaitkan dengan prinsip **F.I.R.S.T (Fast, Independent, Repeatable, Self-validating, Timely)**, sebagian besar test yang dibuat sudah cukup memenuhi prinsip tersebut.

### Fast

Unit test yang dibuat berjalan dengan cepat karena tidak bergantung pada database atau sistem eksternal. Contohnya, pada `OrderServiceTest` saya menggunakan **Mockito** untuk melakukan mocking pada `OrderRepository`. Dengan cara ini, test hanya memverifikasi logika pada `OrderServiceImpl` tanpa harus benar-benar menyimpan data ke database.

### Independent

Setiap test dibuat independen dengan menggunakan method `setup()` untuk menginisialisasi data sebelum test dijalankan. Hal ini memastikan bahwa setiap test tidak bergantung pada hasil test lainnya dan dapat dijalankan secara terpisah tanpa mempengaruhi satu sama lain.

### Repeatable

Karena test tidak bergantung pada kondisi eksternal seperti database atau jaringan, test dapat dijalankan berulang kali dengan hasil yang konsisten. Hal ini mempermudah proses debugging karena hasil test selalu dapat direproduksi.

### Self-validating

Setiap unit test menggunakan assertion seperti `assertEquals`, `assertNull`, `assertTrue`, dan `assertThrows` untuk memverifikasi hasil yang diharapkan. Contohnya pada test `updateStatus`, saya memverifikasi bahwa status order benar-benar berubah menjadi `SUCCESS`, serta memastikan bahwa exception akan dilempar jika status yang diberikan tidak valid.

### Timely

Test ditulis sebelum implementasi kode dilakukan, sesuai dengan prinsip utama TDD. Dengan menulis test terlebih dahulu, saya dapat menentukan perilaku yang diharapkan dari setiap class seperti `Order`, `OrderRepository`, dan `OrderServiceImpl` sebelum menulis implementasi kodenya.

---

## Conclusion

Secara keseluruhan, penerapan Test Driven Development dalam tutorial ini membantu saya memahami bagaimana menulis kode yang lebih terstruktur, teruji, dan mudah dipelihara. Dengan membuat unit test terlebih dahulu, saya dapat memastikan bahwa setiap fitur yang diimplementasikan sudah memenuhi perilaku yang diharapkan.

Penggunaan Mockito juga sangat membantu dalam menguji komponen seperti `OrderServiceImpl` secara terisolasi dari dependency lainnya. Selain itu, refactoring menggunakan enum `OrderStatus` membuat kode menjadi lebih aman dan lebih mudah dipahami.

Ke depannya, saya ingin meningkatkan kualitas unit test yang saya buat dengan menambahkan lebih banyak skenario pengujian serta memperdalam penggunaan mocking dan testing framework agar proses pengembangan perangkat lunak menjadi lebih efektif dan lebih robust.