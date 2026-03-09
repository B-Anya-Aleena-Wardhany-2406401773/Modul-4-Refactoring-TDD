# Reflection

Anya Aleena Wardhany

2406401773

<details>
<summary><b>Module 2</b></summary>

## Code Quality Issues
Missing Gradle Execution Permission
- Masalah: Docker tidak memiliki izin untuk eksekusi `./gradlew` karena perbedaan penanganan file permission antara 
sistem operasi lokal dan Linux di Docker
- Fix: menambahkan baris `RUN chmod +x ./gradlew` sebelum perintah build dijalankan


## CI/CD Implementation
Menurut saya, implementasi workflow pada project ini sudah memenuhi CI/CD
- **CI:** Setiap kali dilakukan `push` atau `pull request`, Github Actions menjalankan script verfikasi. Verifikasi 
tersebut mencakup build, unit test, code quality check with _SonarCloud_, laporan code coverage dari _Jacoco_. Hal 
ini sesuai dengan prinsip CI yang mana setiap perubahan kode secara kontinu dan diverifikasi secara otomatis untuk 
mendeteksi kesalahan secepat mungkin.
- **CD:** setiap perubahan yang telah divalidasi dan di-merge ke branch utama, `master`, akan memicu 
proses otomatisasi untuk mengirim aplikasinya langsung ke platform _Koyeb_ (dengan wrapper Docker). Hal ini sesuai 
dengan prinsip CD yang mana aplikasi selalu berada dalam kondisi siap pakai (deployed).
</details>

<details>
<summary><b>Module 3</b></summary>

## Applied SOLID Principles

#### SRP
Supaya setiap class hanya melakukan 1 responsibility, saya melakukan beberapa hal berikut:
- Menghapus inheritance `ProductController` ke `CarController` karena dua hal tersebut melakukan hal yang berbeda.
- Memindahkan pengecekan UUID dari `CarRepository` ke `CarServiceImpl` karena repository harusnya hanya melakukan
  penyimpanan Car, bukan logika lainnya.
#### OCP
Untuk memastikan kode dapat di extend tanpa dimodifikasi, saya melakukan hal berikut:
- Menghapus inheritance `ProductController` ke `CarController` agar nantinya fitur baru untuk Car dapat ditambahkan
  tanpa perlu melakukan override atau modifikasi kode di `ProductController`.
#### LSP
Supaya setiap subclass dapat menggantikan fungsi superclass-nya tanpa merusak fungsionalitas program, saya
melakukan hal berikut:
- Menghapus inheritance antara `CarController` dan `ProductController`. Hal ini dilakukan karena Car
  memiliki kebutuhan logika yang berbeda dengan Product, sehingga memaksa CarController menjadi subclass dari
  ProductController hanya akan menyebabkan pewarisan metode yang tidak relevan atau merusak perilaku kelas induk.
#### ISP
Kode sudah dipastikan tidak ada class yang implements interface tanpa membutuhkan fungsi-fungsinya, contohnya:
- Pemisahan interface `CarService` dan `ProductService` karena metode-metodenya tidak relevan.
#### DIP
Supaya tidak ada dependensi terhadap kelas konkrit subclass, saya melakukan hal berikut:
- Mengubah dependensi `CarController` terhadap `CarServiceImpl` menjadi dependen terhadap `CarService` karena
  `CarServiceImpl` merupakan kelas konkrit, sementara `CarService` interface.

## Advantages of Applying SOLID
**Maintainability**, kode menjadi mudah dipahami dengan penerapan SRP karena setiap class hanya melakukan 1 kegunaan
sehingga kodenya self-explanatory. Ditambah dengan penerapan ISP, setiap penerapan interface tidak erpaksa
menerapkan fungsi yang tidak dibutuhkan, sehingga kode lebih jelas. **Flexibility,** untuk menambah fitur atau
fungsi tidak akan merepotkan (seperti fix kode lain) dengan diterapkannya prinsip OCP. **Robustness**, dengan
penerapan LSP, bug saat kita menggunakan objek dari subclass bisa dicegah. **Testability**, penerapan DIP memudahkan
kita saat melakukan testing karena bisa membuat mock dari interface-nya sehingga tidak perlu menjalankan seluruh sistem

## Disadvantages of Not Applying SOLID
Tanpa penerapan prinsip SOLID, kode akan mengalami `Rigidity`, di mana perubahan kecil pada satu bagian dapat
menyebabkan error berantai karena modul-modul saling terikat secara kaku. Hal ini memicu `Fragility`, yaitu kondisi
di mana struktur kode gampang rusak dan memunculkan bug baru saat dilakukan modifikasi. Kurangnya penerapan SRP dan
LSP juga membuat kode sulit dipahami dan diuji karena adanya ketergantungan yang rumit antar kelas.
</details>