# Thousands of courses

Тестовое задание с 3-4 экранами приложения с курсами разработчиков.

APK: [В releases](http://github.com/illarionov/test-thousands-of-courses/releases/tag/untagged-9c27dba646738a961796)

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


