# Reflection - Module 4

Anya Aleena Wardhany

2406401773

## Is this TDD flow useful?
Setelah dievaluasi, saya merasa alur TDD yang diterapkan dalam tutorial ini sangat berguna. Tes yang dibuat berhasil
menjelaskan fungsi sistem yang diharapkan, bukan hanya mengetes detail implementasi kode. Dengan dibuatnya tes
sebelum kode implementasi, saat membuat implementasi jadi terasa lebih yakin karena ada batasan dan patokannya. TDD
juga mencegah kode implementasi yang belum tentu akan digunakan agar tidak menimbulkan YAGNI (You're Not Gonna Need It).

## F.I.R.S.T Principle
Menurut saya test-nya sudah sesuai F.I.R.S.T principle,dengan jabaran sebagai berikut:
- ***F*ast**: Tes berjalan cepat karena menggunakan mocking (Mockito) pada `OrderServiceTest`, sehingga
  tidak perlu menunggu inisialisasi seluruh Spring
- ***I*ndependent**: Penggunaan `@BeforeEach` pada `OrderTest` dan `OrderServiceTest` memastikan data diatur ulang
  sebelum setiap tes dijalankan, sehingga hasil satu tes tidak mempengaruhi tes lainnya
- ***R*epeatable**: Tes dapat dijalankan berkali-kali di lingkungan mana pun dengan hasil yang konsisten karena
  tidak bergantung pada database
- ***S*elf-Validating**: Semua tes bersifat pass/fail yang menggunakan assertion seperti `assertEquals`,
  `assertNull`, dan `assertThrows`, sehingga tidak perlu cek manual
- ***T*imely**: Tes ditulis sebelum kode implementasi dibuat (TDD), memastikan kode yang dibuat memang dapat
  diuji sejak awal
