#language: ru
Функционал: Добавление нового пользователя
  Открываем главную страницу админки
  Должно открыться окно ввода учетных данных "/login"
  Вводим Имя в поле "login"
  Вводим Пароль в поле "password"
  Нажимаем кнопку "login"
  Переходим на главную страницу админки "/admin"
  Наводим курсор мыши на боковую панель
  Открываем список сущностей
  Ждем 1 секунду для ожидания конца анимации
  Выбираем Пользователей
  Проверяем что перешли на страницу с пользователя
  Нажимаем на кнопку создания нового пользователя
  вводим имя в строку имя
  вводим пароль в строку пароль
  выбираем роль пользователь
  Нажимаем кнопку сохранить
  Переходим на страницу со списком пользователей


  Структура сценария: Добавление нового пользователя
    Пусть откроется страница "http://localhost:8080/admin"
    Но откроется страница "http://localhost:8080/login"
    Затем вводим "<name>" в поле "#login_field"
    * вводим "<password>" в поле "#password_field"
    * нажмем кнопку "body > div > div > form > div.modal-footer > button.btn.btn-success"
    И откроется страница "http://localhost:8080/admin"
    Тогда наведем курсор мыши на "body > div > main > div.left-menu.bg-success"
    Также нажмем кнопку "body > div > main > div.left-menu.bg-success > nav > ul > li:nth-child(2) > label"
    И ждем "1" секунд
    И нажмем на ссылку "body > div > main > div.left-menu.bg-success > nav > ul > li:nth-child(2) > ul > li:nth-child(6) > a"
    Тогда откроется страница "http://localhost:8080/admin/entities/user/showAll"
    Затем нажмем кнопку "body > div > main > div.overflow-auto.p-3.flex-grow-1 > div > div > div.row.justify-content-center > a"
    И вводим "имя" в поле "#name"
    * вводим "пароль" в поле "#password"
    * нажмем кнопку "#roles1"
    Затем нажмем кнопку "body > div > main > div.overflow-auto.p-3.flex-grow-1 > div > div > form > div.row.justify-content-end > button"
    Тогда откроется страница "http://localhost:8080/admin/entities/user/showAll"



    Примеры:
      | name  | password |
      | admin | admin    |
