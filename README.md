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

