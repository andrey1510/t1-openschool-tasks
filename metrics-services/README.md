__README__
==========

Данное приложение представляет собой систему мониторига своей работы и состоит из двух сервисов - **metrics-producer**, который собирает метрики и отправляет их с помощью Apache Kafka, и **metrics-consumer**, который получает, обрабатывает и хранит метрики.
Использованный стек: Java SE 17, Spring Boot 3.3.0, Apache Kafka, Spring Actuator, JPA, Maven, Lombok, REST-архитектура, Swagger, PostgreSQL.

Установка и запуск:
-----------------------------------

Нужно склонировать репозиторий. Затем запустить Kafka, два сервиса: **metrics-consumer** (и его базу PostgreSQL) и **metrics-producer**, согласно инструкциям ниже.

**Вариант 1 - запуск в Docker**

В директории ```t1-openschool-tasks\metrics-services``` запустить файл **docker-compose.yml** (). Его следует запускать с помощью IDE, или выполнив команды ```docker-compose build``` и ```docker-compose up -d``` в директории, где он находится.
В результате будут созданы и запущены контейнеры:
1. kafka-metrics - сервер Kafka;
2. zookeeper - Zookeeper для сервера Kafka;
3. kafka-ui - UI для сервер Kafka;
4. metrics-producer - сервис metric-producer;
5. metrics-consumer - сервис metric-consumer;
6. metrics-db - база PostgreSQL для сервиса metric-consumer.

**Вариант 2 - запуск в IDE**

Нужно создать базу PostgreSQL и сервер Kafka:
1. запустить **docker-compose.yml** как указано выше, затем остановить контейнеры metrics-producer и metrics-consumer; 
2. или создать на Вашем сервере PostreSQL соответствующую базу PostreSQL с параметрами:

   хост, порт и база: ```localhost:26262/metricsbase```

      имя пользователя: ```user```

      пароль: ```password```

   и создать на Вашем сервере Kafka брокера Kafka с параметрами:

      хост и порт: ```localhost:29292```

После этого запустить исходный код приложений **metrics-consumer** и **metrics-producer** в IDE.


**Вариант 3 - установка в jar-файл**

Создать базу PostreSQL и сервер Kafka, как указано выше.

На устройстве должна быть установлена Java. В директории ```t1-openschool-tasks\metrics-services\metrics-consumer``` склонированного репозитория откройте GitBash и выполните команду:

```./mvnw clean package```

В директории ```t1-openschool-tasks\metrics-services\metrics-consumer\target``` появится jar файл. Откройте GitBash в этой директории и выполните команду для запуска приложения:

```java -jar [имя jar файла]```

Пример:

```java -jar metrics-consumer-1.0.0-SNAPSHOT.jar```

Повторите процедуру со вторым сервисом в директории ```t1-openschool-tasks\metrics-services\metrics-producer```.

Работа с приложением:
---------------------------------------

После запуска сервисы приложения будут доступны по адресам:
**metrics-producer** - http://localhost:8865
**metrics-consumer** - http://localhost:8866

Приложение поддерживает Swagger. Получить доступ к Swagger UI можно по адресам:
**metrics-producer** - http://localhost:8865/swagger-ui.html.
**metrics-consumer** - http://localhost:8866/swagger-ui.html.

Документация содержится в Swagger.

Если Kafka устанавливалась с помощью docker-compose из этого репозитория, можно работать с сервером Kafka через Kafka-UI по адресу:
http://localhost:18181/

Функционал приложения:
------------------------------------------

Сервис **metrics-producer** собирает следующие метрики своей работы:

1. jvm.memory.used,
2. jvm.memory.committed,
3. process.uptime,
4. process.cpu.time

Затем он отправляет их с помощью Apache Kafka в сервис metrics-consumer.

Через Apache Kafka отправляется следующее сообщение: присвоенная группа метрик (например, jvm-metrics) в качестве key, все данные конкретной снятой метрики с качестве value, тайштамп снятия метрики в header.

Сервис **metrics-consumer** получает метрики, обрабатывает их и сохраняет их данные в своей базе. В настоящее время принимаются и обрабатываются одинаково все метрики. Поскольку название группы метрик (сейчас имеется две группы - jvm-metrics и process-metrics) указывается в сообщении Kafka в качестве ключа, в дальнейшем при необходимости можно предусмотреть различную обработку разных групп.

### Контроллер MetricsProducerController сервиса metrics-producer

Он содержит:

**Метод sendMetrics()** - принимает запросы POST на:

```http://localhost:8865/api/metrics-sender/metrics```

Метод 1 раз собирает и отправляет все используемые метрики. Он выдаст названия отправленных метрик.

### Контроллер MetricsConsumerController сервиса metrics-consumer

Он содержит все методы, с помощью которых осуществляются операции с метриками в базе:

**Метод getMetricsTypes** - принимает запросы GET на:

```http://localhost:8866/api/metrics-receiver/metrics```

Он возвращает JSON с кодом ```200``` и листом идентификаторов (имен) залогированных в базе метрик. Каждая запись представляет собой одну метрику, и содержит следующие элементы:

1. идентификатор метрики (имен),
2. описание метрики.

В случае, если в базе нет метрик, об этом будет выдано сообщение с кодом ```404```.

**Метод getMetricsByName** - принимает запросы GET на:

GET http://localhost:8866/api/metrics-receiver/metrics{{id}}

Нужно указать идентификатор (имя) метрики, например: jvm.memory.used
Метод возвращает JSON с кодом ```200``` и листом залогированных данных метрик, отфильтрованных по идентификатору (имени) метрики. Каждая запись представляет собой одно снятие метрики, и содержит следующие элементы:

1. таймштамп снятия метрики,
2. численное значение снятой метрики,
3. единица измерения метрики.

В случае, если в базе не найдено данных метрик с указанным идентификатором, об этом будет выдано сообщение с кодом ```404```.
