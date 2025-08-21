# Thousands of courses

Тестовое задание с 3-4 экранами приложения с курсами разработчиков.

APK: [releases](https://github.com/illarionov/test-thousands-of-courses/releases/tag/v0.1)

## Статус

* Отметили + многомодульность и - несответствие экранов макетам
* Прошёл техническое собеседование
* Сообщили, что вакансия закрыта, однако могут выйти на связь при появлении новых

### Стек

Kotlin, Retrofit, Coroutines, Flow, Hilt, MVVM/MVI, AdapterDelegates, XML (не Compose), Clean, многомодульность, 
Room, MockWebServer, Material 3, Coil, Navigation Component, EitherNet

### Комментарии

* Часть экранов не полностью соответствуют макетам. На XML их делать скучно, когда есть Compose. 

### Многомодульность

3 группы модулей: core, data, feature. 
* сore зависят только от core
* daa зависят только от data и core
* feature зависят только от data/core, не от feature.

shared-репозиторий в data, без явного деления на api-impl модули.


