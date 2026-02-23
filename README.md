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

