# Koller Android
Ez a forráskód kettő alkalmazást is éltet. Egyrész a Koller Diákoknakot ([`student`](https://github.com/KollerSystems/Koller-Android/tree/master/student)), másrészt pedig a Koller Tanároknakot ([`teacher`](https://github.com/KollerSystems/Koller-Android/tree/master/teacher)). Ezt a kettőt köti össze egy közös könyvtár ([`shared`](https://github.com/KollerSystems/Koller-Android/tree/master/shared)).

A ketté szedést azért éreztem szükségesnek, hisz így a lehető legkevesebb tárhelyet foglalja az adott alkalmazás a felhasználó készülékén, hatékonyabban fut az applikáció, és a fejlesztásái folyamatot is átláthatóbbá kényszeríti.

## Amire törekszem
Célom, hogy két olyan alkalmazást készítsek, melyek amellett, hogy letisztultak és átláthatóak, következetesek, legyenek a lehető legnyitottabbak is. Ez alatt azt értem, hogy ami nem titok, azt az alkalmazás se titkolja el, mégiscsak egy közösségi applikáció is. Tekintve azonban azokra, akik jogosan nem akarnak mindent közzé tenni, az elérhetőségek megadása és publikussá tevése természetesen opcionális, mint még sok más.

Az alkalmzás természetesen támogatja a kis, nagy (tablet) és döntött képernyőket is.

## Legfontosabb funkciói az alkalmazásoknak

### Közös elemek
<img src="https://github.com/KollerSystems/Koller-Android/assets/40640441/4ea38bcc-8ab5-4a62-b0f0-d7522a38c33c" alt="list" width="200" align="right"/>
<img src="https://github.com/KollerSystems/Koller-Android/assets/40640441/90d0c55c-fa7f-4627-a475-ebfe8e09dd8a" alt="login" width="200" align="right"/>

Az alábbiak mind a kettő alkalmazásban megtalálhatóak:
- Felhasználók
- Helyiségek
- Szakkörök
- Alapprogramok
- Teendők
- Menza étlap
- Tájékoztatás saját ki- és belépésekről
- Keresés, rendezés és szűrés

### Koller Diákoknak
Ez az alkalmazás kifejezetten diákoknak készült. Olyan funkciók találhatóak meg benne, ami egy kollégista diák napjait könnyebbítheti meg:
- Opcionális figyelmeztetések visszaérkezéssel kapcsolatban
- Tájékoztatás és opcionális értesítések a következőkkel kapcsolatban:
  - Szobarend
  - Kimenők
  - Figyelmeztetések
  - Dícséretek

### Koller Tanároknak
<img src="https://github.com/KollerSystems/Koller-Android/assets/40640441/64b399fa-d16b-4219-9e04-d6d820ec2c66" alt="edit" width="200" align="right"/>
<img src="https://github.com/KollerSystems/Koller-Android/assets/40640441/13d809e9-e4af-4510-800f-6ffcc62ccff0" alt="room" width="200" align="right"/>

Ez az alkalmazás kifejezetten tanároknak készült. Olyan funkciók találhatóak meg benne (jogosutlságtól függően), ami egy kollégiumban dolgozó tanár napjait megkönnyebítheti, és amivel a kollégiumut kezelni tudja:
- Diákok kezelése:
  - Kimenők
  - Dícséretek / Figyelmeztetők
  - Személyes adatok
  - Kollégiumi adatok
  - Felvétel
  - Eltávolítás
- Szobák kezelése:
  - Szobarend
  - Jelenlét
  - Lakók
  - Hozzáadás
  - Eltávolítás
- Szakkörök:
  - Aktuális adatok
  - Általános adatok
  - Hozzáadás
  - Eltávolítás
- Alapprogramok:
  - Adott aktuális adatok
  - Általános adatok
  - Hozzáadás
  - Eltávolítás
