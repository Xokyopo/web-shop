#language: ru
Функционал: Присутствует главная страница админки
  Открываем главную страницу сайта
  Нажимаем на ссылку "Admin panel"
  Должно открыться окно ввода учетных данных "/login"
  Вводим Имя в поле "login"
  Вводим Пароль в поле "password"
  Нажимаем кнопку "login"
  Переходим на главную страницу админки "/admin"
  Проверяем есть ли на ней приветственное сообщение

  Структура сценария: Переход на главную страницу админки
    Пусть откроется страница "http://localhost:8080/"
    Тогда нажмем на ссылку "body > header > div.header-area > div > div > div.col-md-4 > ul > li > a"
    И откроется страница "http://localhost:8080/login"
    Затем вводим "<name>" в поле "#login_field"
    * вводим "<password>" в поле "#password_field"
    * нажмем кнопку "body > div > div > form > div.modal-footer > button.btn.btn-success"
    И откроется страница "http://localhost:8080/admin"
    Также доступен элемент "body > div > main > div.overflow-auto.p-3.flex-grow-1 > div > div"

    Примеры:
      | name  | password |
      | admin | admin    |
