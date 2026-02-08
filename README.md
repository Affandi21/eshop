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

---